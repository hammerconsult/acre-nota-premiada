package br.com.webpublico.domain;

import br.com.webpublico.DateUtils;
import br.com.webpublico.util.Util;
import com.google.common.base.Strings;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;

public class FaleConoscoNfseDTO implements BatchPreparedStatementSetter, RowMapper<FaleConoscoNfseDTO> {

    private Long id;
    private SituacaoFaleConoscoNfseDTO situacao;
    private TipoFaleConoscoNfseDTO tipo;
    private String assunto;
    private Date dataEnvio;
    private DadosPessoaisDTO dadosReclamante;
    private DadosPessoaisDTO dadosReclamado;
    private Date dataServico;
    private String descricaoServico;
    private Long numeroNotaServico;
    private BigDecimal valorServico;
    private String mensagem;
    private String resposta;

    public FaleConoscoNfseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacaoFaleConoscoNfseDTO getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoFaleConoscoNfseDTO situacao) {
        this.situacao = situacao;
    }

    public TipoFaleConoscoNfseDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoFaleConoscoNfseDTO tipo) {
        this.tipo = tipo;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public DadosPessoaisDTO getDadosReclamante() {
        return dadosReclamante;
    }

    public void setDadosReclamante(DadosPessoaisDTO dadosReclamante) {
        this.dadosReclamante = dadosReclamante;
    }

    public DadosPessoaisDTO getDadosReclamado() {
        return dadosReclamado;
    }

    public void setDadosReclamado(DadosPessoaisDTO dadosReclamado) {
        this.dadosReclamado = dadosReclamado;
    }

    public Date getDataServico() {
        return dataServico;
    }

    public void setDataServico(Date dataServico) {
        this.dataServico = dataServico;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public Long getNumeroNotaServico() {
        return numeroNotaServico;
    }

    public void setNumeroNotaServico(Long numeroNotaServico) {
        this.numeroNotaServico = numeroNotaServico;
    }

    public BigDecimal getValorServico() {
        return valorServico;
    }

    public void setValorServico(BigDecimal valorServico) {
        this.valorServico = valorServico;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        ps.setLong(1, getId());
        ps.setString(2, "NOTA_PREMIADA");
        ps.setString(3, getSituacao().name());
        ps.setString(4, getTipo().name());
        ps.setString(5, getAssunto());
        if (getDadosReclamante() != null) {
            ps.setLong(6, getDadosReclamante().getId());
        } else {
            ps.setNull(6, Types.NULL);
        }
        if (getDadosReclamado() != null) {
            ps.setLong(7, getDadosReclamado().getId());
        } else {
            ps.setNull(7, Types.NULL);
        }
        ps.setDate(8, DateUtils.toSQLDate(getDataEnvio()));
        ps.setString(9, getMensagem());
        if (getDataServico() != null) {
            ps.setDate(10, DateUtils.toSQLDate(getDataServico()));
        } else {
            ps.setNull(10, Types.NULL);
        }
        if (!Strings.isNullOrEmpty(getDescricaoServico())) {
            ps.setString(11, getDescricaoServico());
        } else {
            ps.setNull(11, Types.NULL);
        }
        if (getValorServico() != null) {
            ps.setBigDecimal(12, getValorServico());
        } else {
            ps.setNull(12, Types.NULL);
        }
        if (getNumeroNotaServico() != null) {
            ps.setLong(13, getNumeroNotaServico());
        } else {
            ps.setNull(13, Types.NULL);
        }
    }

    @Override
    public FaleConoscoNfseDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        FaleConoscoNfseDTO dto = new FaleConoscoNfseDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setSituacao(SituacaoFaleConoscoNfseDTO.valueOf(resultSet.getString("SITUACAO")));
        dto.setTipo(TipoFaleConoscoNfseDTO.valueOf(resultSet.getString("TIPO")));
        dto.setAssunto(resultSet.getString("ASSUNTO"));
        dto.setDadosReclamante(new DadosPessoaisDTO());
        dto.getDadosReclamante().setId(resultSet.getLong("DADOSRECLAMANTE_ID"));
        if (resultSet.getLong("DADOSRECLAMADO_ID") != 0) {
            dto.setDadosReclamado(new DadosPessoaisDTO());
            dto.getDadosReclamado().setId(resultSet.getLong("DADOSRECLAMADO_ID"));
        }
        dto.setDataEnvio(resultSet.getDate("DATAENVIO"));
        dto.setMensagem(resultSet.getString("MENSAGEM"));
        dto.setDataServico(resultSet.getDate("DATASERVICO"));
        dto.setDescricaoServico(resultSet.getString("DESCRICAOSERVICO"));
        dto.setValorServico(resultSet.getBigDecimal("VALORSERVICO"));
        dto.setNumeroNotaServico(resultSet.getLong("NUMERONOTASERVICO"));
        dto.setResposta(resultSet.getString("RESPOSTA"));
        return dto;
    }

    @Override
    public int getBatchSize() {
        return 1;
    }
}
