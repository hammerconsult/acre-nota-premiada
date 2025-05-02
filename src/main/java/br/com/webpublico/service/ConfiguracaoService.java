package br.com.webpublico.service;

import br.com.webpublico.FileUtils;
import br.com.webpublico.config.Constants;
import br.com.webpublico.domain.ConfiguracaoDTO;
import br.com.webpublico.repository.ConfiguracaoJDBCRepository;
import br.com.webpublico.util.Util;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ConfiguracaoService {

    private final Logger log = LoggerFactory.getLogger(ConfiguracaoService.class);

    private LocalTime ultimaSincronizacao;
    private ConfiguracaoDTO configuracao;
    @Autowired
    private MunicipioService cidadeService;
    @Autowired
    private DetentorArquivoComposicaoService detentorArquivoComposicaoService;
    @Autowired
    private ConfiguracaoJDBCRepository configuracaoJDBCRepository;
    @Inject
    private Environment env;

    public ConfiguracaoDTO getConfiguracao() {
        if (configuracao == null ||
                ultimaSincronizacao == null ||
                ultimaSincronizacao.isBefore(LocalTime.now().minusMinutes(10))) {
            configuracao = configuracaoJDBCRepository.find();

            if (configuracao.getMunicipio() != null) {
                configuracao.setMunicipio(cidadeService.findById(configuracao.getMunicipio().getId()));
            }

            if (configuracao.getArquivoBrasao() != null) {

                configuracao.setArquivoBrasao(detentorArquivoComposicaoService.findById(
                        configuracao.getArquivoBrasao().getId()));

                if (configuracao.getArquivoBrasao().
                        getArquivo() != null) {
                    byte[] dados = Util.getByteArrayDosDados(configuracao.getArquivoBrasao().
                            getArquivo().getArquivo().getPartes());
                    configuracao.setLogoMunicipio("data:image/png;base64," +
                            FileUtils.getBase64Encode(dados));
                }
            }
            configuracao.setProducao(env.acceptsProfiles(Constants.SPRING_PROFILE_PRODUCTION));
            ultimaSincronizacao = LocalTime.now();
        }
        return configuracao;
    }
}
