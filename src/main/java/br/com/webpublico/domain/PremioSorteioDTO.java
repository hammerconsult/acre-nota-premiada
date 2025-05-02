package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PremioSorteioDTO implements Serializable, RowMapper<PremioSorteioDTO> {
    private Long id;
    private SorteioDTO sorteio;
    private Integer sequencia;
    private String descricao;
    private Integer quantidade;
    private BigDecimal valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SorteioDTO getSorteio() {
        return sorteio;
    }

    public void setSorteio(SorteioDTO sorteio) {
        this.sorteio = sorteio;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public PremioSorteioDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        PremioSorteioDTO dto = new PremioSorteioDTO();
        dto.setId(resultSet.getLong("id"));
        dto.setSorteio(new SorteioDTO());
        dto.getSorteio().setId(resultSet.getLong("sorteio_id"));
        dto.setSequencia(resultSet.getInt("sequencia"));
        dto.setDescricao(resultSet.getString("descricao"));
        dto.setQuantidade(resultSet.getInt("quantidade"));
        dto.setValor(resultSet.getBigDecimal("valor"));
        return dto;
    }
}
