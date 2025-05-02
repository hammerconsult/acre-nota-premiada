package br.com.webpublico.service;

import br.com.webpublico.domain.FaleConoscoNfseDTO;
import br.com.webpublico.domain.SituacaoFaleConoscoNfseDTO;
import br.com.webpublico.repository.FaleConoscoJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FaleConoscoService extends AbstractWPService<FaleConoscoNfseDTO> {

    @Autowired
    FaleConoscoJDBCRepository faleConoscoJDBCRepository;
    @Autowired
    NotificacaoService notificacaoService;
    @Autowired
    DadosPessoaisService dadosPessoaisService;

    RestTemplate restTemplate = new RestTemplate();

    public FaleConoscoNfseDTO inserir(FaleConoscoNfseDTO dto) {
        dto.setDataEnvio(new Date());
        dto.setSituacao(SituacaoFaleConoscoNfseDTO.ABERTO);
        dadosPessoaisService.salvar(dto.getDadosReclamante());
        if (dto.getDadosReclamado() != null) {
            dadosPessoaisService.salvar(dto.getDadosReclamado());
        }
        dto = faleConoscoJDBCRepository.inserir(dto);
        notificar(dto);
        return dto;
    }

    public List<FaleConoscoNfseDTO> buscarReclamacoesPorCpf(String cpf) {
        List<FaleConoscoNfseDTO> registros = faleConoscoJDBCRepository.buscarReclamacoesPorCpf(cpf);
        if (registros != null) {
            for (FaleConoscoNfseDTO registro : registros) {
                registro.setDadosReclamante(dadosPessoaisService.findDadosPessoaisNfseById(registro.getDadosReclamante().getId()));
                if (registro.getDadosReclamado() != null) {
                    registro.setDadosReclamado(dadosPessoaisService.findDadosPessoaisNfseById(registro.getDadosReclamado().getId()));
                }
            }
        }
        return registros;
    }

    private void notificar(FaleConoscoNfseDTO dto) {
        NotificacaoDTO notificacao = new NotificacaoDTO();
        notificacao.setDescricao(dto.getAssunto() + ": " + dto.getDadosReclamante().getNomeRazaoSocial());
        notificacao.setGravidade(NotificacaoDTO.GravidadeNfseDTO.ERRO);
        notificacao.setTitulo("Reclamação Nota Premiada");
        notificacao.setTipoNotificacao(NotificacaoDTO.TipoNotificacaoNfseDTO.RECLAMACAO_NOTA_PREMIADA);
        notificacao.setLink("/nfse/fale-conosco/ver/" + dto.getId() + "/");

        notificacaoService.inserir(notificacao);
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public String getDefaltSearchFields() {
        return null;
    }

    @Override
    public ParameterizedTypeReference<List<FaleConoscoNfseDTO>> getResponseTypeList() {
        return null;
    }

    @Override
    public ParameterizedTypeReference<FaleConoscoNfseDTO> getResponseType() {
        return null;
    }
}
