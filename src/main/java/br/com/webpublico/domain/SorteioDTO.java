package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SorteioDTO implements Serializable, RowMapper<SorteioDTO> {
    private Long id;
    private Long idCampanha;
    private SituacaoSorteioDTO situacao;
    private Integer numero;
    private String descricao;
    private Date inicioEmissaoNotaFiscal;
    private Date fimEmissaoNotaFiscal;
    private Date dataSorteio;
    private Date dataDivulgacaoSorteio;
    private Date dataDivulgacaoCupom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(Long idCampanha) {
        this.idCampanha = idCampanha;
    }

    public SituacaoSorteioDTO getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoSorteioDTO situacao) {
        this.situacao = situacao;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getInicioEmissaoNotaFiscal() {
        return inicioEmissaoNotaFiscal;
    }

    public void setInicioEmissaoNotaFiscal(Date inicioEmissaoNotaFiscal) {
        this.inicioEmissaoNotaFiscal = inicioEmissaoNotaFiscal;
    }

    public Date getFimEmissaoNotaFiscal() {
        return fimEmissaoNotaFiscal;
    }

    public void setFimEmissaoNotaFiscal(Date fimEmissaoNotaFiscal) {
        this.fimEmissaoNotaFiscal = fimEmissaoNotaFiscal;
    }

    public Date getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(Date dataSorteio) {
        this.dataSorteio = dataSorteio;
    }

    public Date getDataDivulgacaoSorteio() {
        return dataDivulgacaoSorteio;
    }

    public void setDataDivulgacaoSorteio(Date dataDivulgacaoSorteio) {
        this.dataDivulgacaoSorteio = dataDivulgacaoSorteio;
    }

    public Date getDataDivulgacaoCupom() {
        return dataDivulgacaoCupom;
    }

    public void setDataDivulgacaoCupom(Date dataDivulgacaoCupom) {
        this.dataDivulgacaoCupom = dataDivulgacaoCupom;
    }

    @Override
    public SorteioDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        SorteioDTO dto = new SorteioDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setIdCampanha(resultSet.getLong("CAMPANHA_ID"));
        dto.setSituacao(SituacaoSorteioDTO.valueOf(resultSet.getString("SITUACAO")));
        dto.setNumero(resultSet.getInt("NUMERO"));
        dto.setDescricao(resultSet.getString("DESCRICAO"));
        dto.setInicioEmissaoNotaFiscal(resultSet.getDate("INICIO_EMISSAO_NOTA_FISCAL"));
        dto.setFimEmissaoNotaFiscal(resultSet.getDate("FIM_EMISSAO_NOTA_FISCAL"));
        dto.setDataSorteio(resultSet.getDate("DATA_SORTEIO"));
        dto.setDataDivulgacaoSorteio(resultSet.getDate("DATA_DIVULGACAO_SORTEIO"));
        dto.setDataDivulgacaoCupom(resultSet.getDate("DATA_DIVULGACAO_CUPOM"));
        return dto;
    }
}
