package br.com.webpublico.service;

import br.com.webpublico.StringUtils;
import br.com.webpublico.domain.ConfiguracaoDTO;
import br.com.webpublico.domain.TemplateDTO;
import br.com.webpublico.domain.TermoUsoDTO;
import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import br.com.webpublico.exception.OperacaoNaoPermitidaException;
import br.com.webpublico.repository.TemplateJDBCRepository;
import br.com.webpublico.repository.UsuarioNotaPremiadaJDBCRepository;
import br.com.webpublico.security.SecurityUtils;
import br.com.webpublico.service.util.RandomUtil;
import br.com.webpublico.util.trocatag.TipoTemplate;
import br.com.webpublico.util.trocatag.TrocaTagEmailSolicitacaoAlteracaoSenha;
import br.com.webpublico.web.rest.dto.TrocarSenhaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service
public class UsuarioNotaPremiadaService {

    @Autowired
    UsuarioNotaPremiadaJDBCRepository usuarioNotaPremiadaJDBCRepository;
    @Autowired
    TemplateJDBCRepository templateJDBCRepository;
    @Autowired
    DadosPessoaisService dadosPessoaisService;
    @Inject
    PasswordEncoder passwordEncoder;
    @Autowired
    ConfiguracaoService configuracaoService;
    @Autowired
    EmailService emailService;
    @Autowired
    private TermoUsoService termoUsoService;

    public UsuarioNotaPremiadaDTO registrarUsuario(UsuarioNotaPremiadaDTO usuario) {
        validarDadosUsuario(usuario);
        usuario.setLogin(StringUtils.getApenasNumeros(usuario.getDadosPessoais().getCpfCnpj()));
        usuario.setPassword(passwordEncoder.encode(usuario.getSenha()));
        usuario.setDadosPessoais(dadosPessoaisService.salvar(usuario.getDadosPessoais()));
        usuario = usuarioNotaPremiadaJDBCRepository.inserir(usuario);
        TermoUsoDTO termoUso = termoUsoService.buscarTermoUsoVigente();
        termoUsoService.aceitarTermoUso(termoUso, usuario);
        return usuario;
    }

    private void validarDadosUsuario(UsuarioNotaPremiadaDTO usuario) {
        usuario.validar();
        UsuarioNotaPremiadaDTO byLogin = usuarioNotaPremiadaJDBCRepository.findByLogin(usuario.getLogin());
        if (byLogin != null && (usuario.getId() == null || !usuario.getId().equals(byLogin.getId()))) {
            throw new OperacaoNaoPermitidaException("Usuário já registrado, caso tenha esquecido sua senha utilize a opção 'Esqueceu a senha?'");
        }
    }


    public void alterarUsuario(UsuarioNotaPremiadaDTO usuario) {
        validarDadosUsuario(usuario);
        dadosPessoaisService.salvar(usuario.getDadosPessoais());
        usuarioNotaPremiadaJDBCRepository.update(usuario);
    }


    public UsuarioNotaPremiadaDTO getUserWithAuthorities() {
        return findByLogin(SecurityUtils.getCurrentLogin());
    }

    public UsuarioNotaPremiadaDTO findByLogin(String login) {
        return usuarioNotaPremiadaJDBCRepository.findByLogin(login);
    }

    public UsuarioNotaPremiadaDTO findByKey(String key) {
        return usuarioNotaPremiadaJDBCRepository.findByKey(key);
    }

    public void trocarSenha(TrocarSenhaDTO trocarSenhaDTO) {
        UsuarioNotaPremiadaDTO usuario = getUserWithAuthorities();

        if (!passwordEncoder.matches(trocarSenhaDTO.getOldPassword(), usuario.getPassword())) {
            throw new OperacaoNaoPermitidaException("A Senha Atual está incorreta.");
        }

        usuario.setPassword(passwordEncoder.encode(trocarSenhaDTO.getNewPassword()));

        usuarioNotaPremiadaJDBCRepository.update(usuario);
    }

    public void requestPasswordReset(String cpf) {
        UsuarioNotaPremiadaDTO usuario = findByLogin(cpf);
        if (usuario == null) {
            throw new OperacaoNaoPermitidaException("Usuário não encontrado para o CPF " + cpf);
        }
        usuario.setResetKey(RandomUtil.generateResetKey());
        usuario.setResetDate(new Date());
        usuario = usuarioNotaPremiadaJDBCRepository.update(usuario);
        enviarEmailResetarSenha(usuario);
    }

    public void enviarEmailResetarSenha(UsuarioNotaPremiadaDTO usuario) {
        ConfiguracaoDTO configuracao = configuracaoService.getConfiguracao();
        TemplateDTO template = templateJDBCRepository.findByTipo(TipoTemplate.SOLICITACAO_ALTERACAO_SENHA_NOTA_PREMIADA);
        if (template != null) {
            String conteudo = new TrocaTagEmailSolicitacaoAlteracaoSenha(configuracao, usuario)
                    .trocarTags(template.getConteudo());
            emailService.enviarEmail(usuario.getDadosPessoais().getEmail(),
                    "Alteração de senha para acesso ao sistema Nota Premiada", conteudo);
        } else {
            throw new OperacaoNaoPermitidaException("Configuração de template para envio de e-mail não encontrada. " +
                                    "Por favor contate o suporte.");
        }
    }

    public void completePasswordReset(String key, String newPassword) {
        UsuarioNotaPremiadaDTO usuario = findByKey(key);
        if (usuario == null) {
            throw new OperacaoNaoPermitidaException("Usuário não encontrado");
        }
        usuario.setPassword(passwordEncoder.encode(newPassword));
        usuario.setResetKey(null);
        usuario.setResetDate(null);
        usuario = usuarioNotaPremiadaJDBCRepository.update(usuario);
    }
}
