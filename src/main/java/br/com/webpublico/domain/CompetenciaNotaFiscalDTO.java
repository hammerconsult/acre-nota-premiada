package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompetenciaNotaFiscalDTO implements Serializable, RowMapper<CompetenciaNotaFiscalDTO> {

    private Integer ano;
    private Integer mes;
    private Integer quantidade;
    private BigDecimal totalServicos;

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getTotalServicos() {
        return totalServicos;
    }

    public void setTotalServicos(BigDecimal totalServicos) {
        this.totalServicos = totalServicos;
    }

    @Override
    public CompetenciaNotaFiscalDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        CompetenciaNotaFiscalDTO competencia = new CompetenciaNotaFiscalDTO();
        competencia.setAno(resultSet.getInt("ANO"));
        competencia.setMes(resultSet.getInt("MES"));
        competencia.setQuantidade(resultSet.getInt("QUANTIDADE"));
        competencia.setTotalServicos(resultSet.getBigDecimal("TOTAL_SERVICOS"));
        return competencia;
    }
}
