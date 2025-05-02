package br.com.webpublico.service;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InformacaoGeralDTO implements Serializable, RowMapper<InformacaoGeralDTO> {

    private Long quantidadeUsuarios;
    private Long quantidadePrestadores;
    private Long quantidadeNotasEmitidas;
    private BigDecimal totalServicos;

    public InformacaoGeralDTO() {
        quantidadeUsuarios = 0L;
        quantidadePrestadores = 0L;
        quantidadeNotasEmitidas = 0L;
        totalServicos = BigDecimal.ZERO;
    }

    public Long getQuantidadeUsuarios() {
        return quantidadeUsuarios;
    }

    public void setQuantidadeUsuarios(Long quantidadeUsuarios) {
        this.quantidadeUsuarios = quantidadeUsuarios;
    }

    public Long getQuantidadePrestadores() {
        return quantidadePrestadores;
    }

    public void setQuantidadePrestadores(Long quantidadePrestadores) {
        this.quantidadePrestadores = quantidadePrestadores;
    }

    public Long getQuantidadeNotasEmitidas() {
        return quantidadeNotasEmitidas;
    }

    public void setQuantidadeNotasEmitidas(Long quantidadeNotasEmitidas) {
        this.quantidadeNotasEmitidas = quantidadeNotasEmitidas;
    }

    public BigDecimal getTotalServicos() {
        return totalServicos;
    }

    public void setTotalServicos(BigDecimal totalServicos) {
        this.totalServicos = totalServicos;
    }

    @Override
    public InformacaoGeralDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        InformacaoGeralDTO dto = new InformacaoGeralDTO();
        dto.setQuantidadeUsuarios(resultSet.getLong("quantidade_usuarios"));
        dto.setQuantidadePrestadores(resultSet.getLong("quantidade_prestadores"));
        dto.setQuantidadeNotasEmitidas(resultSet.getLong("quantidade_notas_emitidas"));
        dto.setTotalServicos(resultSet.getBigDecimal("total_servicos"));
        return dto;
    }
}
