package br.com.webpublico.util.trocatag;

import br.com.webpublico.domain.ConfiguracaoDTO;
import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;

public class TrocaTagEmailSolicitacaoAlteracaoSenha extends TrocaTag {

    private UsuarioNotaPremiadaDTO usuario;
    private ConfiguracaoDTO configuracao;

    public TrocaTagEmailSolicitacaoAlteracaoSenha(ConfiguracaoDTO configuracao, UsuarioNotaPremiadaDTO usuario) {
        super(TipoTemplate.SOLICITACAO_ALTERACAO_SENHA_NOTA_PREMIADA);
        super.addFieldsConfiguracao(configuracao);
        super.addFieldsUser(usuario);
        addicionarField(new Field(TagComum.LINK, configuracao.getUrlSistema() + "/#/reset/finish?key=" +
                usuario.getResetKey()));
    }
}
