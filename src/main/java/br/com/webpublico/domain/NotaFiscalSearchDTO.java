package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class NotaFiscalSearchDTO implements Serializable, RowMapper<NotaFiscalSearchDTO> {

    private Long id;
    private Long numero;
    private Date emissao;
    private String nomeTomador;
    private String nomePrestador;
    private String cpfCnpjTomador;
    private String cpfCnpjPrestador;
    private String situacao;
    private BigDecimal totalServicos;

    public NotaFiscalSearchDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public String getNomeTomador() {
        return nomeTomador;
    }

    public void setNomeTomador(String nomeTomador) {
        this.nomeTomador = nomeTomador;
    }

    public String getNomePrestador() {
        return nomePrestador;
    }

    public void setNomePrestador(String nomePrestador) {
        this.nomePrestador = nomePrestador;
    }

    public String getCpfCnpjTomador() {
        return cpfCnpjTomador;
    }

    public void setCpfCnpjTomador(String cpfCnpjTomador) {
        this.cpfCnpjTomador = cpfCnpjTomador;
    }

    public String getCpfCnpjPrestador() {
        return cpfCnpjPrestador;
    }

    public void setCpfCnpjPrestador(String cpfCnpjPrestador) {
        this.cpfCnpjPrestador = cpfCnpjPrestador;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public BigDecimal getTotalServicos() {
        return totalServicos;
    }

    public void setTotalServicos(BigDecimal totalServicos) {
        this.totalServicos = totalServicos;
    }

    @Override
    public NotaFiscalSearchDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        NotaFiscalSearchDTO dto = new NotaFiscalSearchDTO();
        dto.setId(resultSet.getLong("id_nota"));
        dto.setNumero(resultSet.getLong("numero_nota"));
        dto.setEmissao(resultSet.getDate("emissao_nota"));
        dto.setNomeTomador(resultSet.getString("nome_tomador"));
        dto.setNomePrestador(resultSet.getString("nome_prestador"));
        dto.setCpfCnpjTomador(resultSet.getString("cpfcnpj_tomador"));
        dto.setCpfCnpjPrestador(resultSet.getString("cpfcnpj_prestador"));
        dto.setSituacao(resultSet.getString("situacao"));
        dto.setTotalServicos(resultSet.getBigDecimal("totalservicos"));
        return dto;
    }
}
