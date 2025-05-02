package br.com.webpublico.service;

import br.com.webpublico.DateUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificacaoDTO implements BatchPreparedStatementSetter {
    private Long id;
    private String titulo;
    private String descricao;
    private String link;
    private GravidadeNfseDTO gravidade;
    private TipoNotificacaoNfseDTO tipoNotificacao;
    private Date criadoEm;

    public NotificacaoDTO() {
        this.criadoEm = new Date();
    }

    public NotificacaoDTO(String titulo, String descricao, String link,
                          GravidadeNfseDTO gravidade, TipoNotificacaoNfseDTO tipoNotificacao) {
        this();
        this.titulo = titulo;
        this.descricao = descricao;
        this.link = link;
        this.gravidade = gravidade;
        this.tipoNotificacao = tipoNotificacao;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        AtomicInteger parameterIndex = new AtomicInteger(0);
        ps.setLong(parameterIndex.addAndGet(1), getId());
        ps.setString(parameterIndex.addAndGet(1), getTitulo());
        ps.setString(parameterIndex.addAndGet(1), getDescricao());
        ps.setString(parameterIndex.addAndGet(1), getLink());
        ps.setString(parameterIndex.addAndGet(1), getGravidade().name());
        ps.setBoolean(parameterIndex.addAndGet(1), Boolean.FALSE);
        ps.setString(parameterIndex.addAndGet(1), getTipoNotificacao().name());
        ps.setDate(parameterIndex.addAndGet(1), DateUtils.toSQLDate(getCriadoEm()));
    }

    @Override
    public int getBatchSize() {
        return 1;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public GravidadeNfseDTO getGravidade() {
        return gravidade;
    }

    public void setGravidade(GravidadeNfseDTO gravidade) {
        this.gravidade = gravidade;
    }

    public TipoNotificacaoNfseDTO getTipoNotificacao() {
        return tipoNotificacao;
    }

    public void setTipoNotificacao(TipoNotificacaoNfseDTO tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public Date getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(Date criadoEm) {
        this.criadoEm = criadoEm;
    }

    public enum GravidadeNfseDTO {
        ERRO("error"),
        ATENCAO("war"),
        INFORMACAO("info");
        private String descricao;

        GravidadeNfseDTO(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {

            return descricao;
        }
    }

    public enum TipoNotificacaoNfseDTO {
        CANCELAMENTO_NFS_ELETRONICA("Cancelamento de Nota Fiscal Eletrônica"),
        RECLAMACAO_NOTA_PREMIADA("Reclamações Portal da Nota Premiada"),
        FALE_CONOSCO_PORTAL_NFSE("Fale Conosco (Nfs-e)");

        private String descricao;

        TipoNotificacaoNfseDTO(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

}
