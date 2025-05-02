package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SorteioUsuarioDTO implements Serializable, RowMapper<SorteioUsuarioDTO> {

    private SorteioDTO sorteio;
    private Integer quantidadeCupons;
    private Integer quantidadeCuponsPremiados;

    public SorteioDTO getSorteio() {
        return sorteio;
    }

    public void setSorteio(SorteioDTO sorteio) {
        this.sorteio = sorteio;
    }

    public Integer getQuantidadeCupons() {
        return quantidadeCupons;
    }

    public void setQuantidadeCupons(Integer quantidadeCupons) {
        this.quantidadeCupons = quantidadeCupons;
    }

    public Integer getQuantidadeCuponsPremiados() {
        return quantidadeCuponsPremiados;
    }

    public void setQuantidadeCuponsPremiados(Integer quantidadeCuponsPremiados) {
        this.quantidadeCuponsPremiados = quantidadeCuponsPremiados;
    }

    @Override
    public SorteioUsuarioDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        SorteioUsuarioDTO dto = new SorteioUsuarioDTO();
        dto.setSorteio(new SorteioDTO());
        dto.getSorteio().setId(resultSet.getLong("ID"));
        dto.getSorteio().setSituacao(SituacaoSorteioDTO.valueOf(resultSet.getString("SITUACAO")));
        dto.getSorteio().setNumero(resultSet.getInt("NUMERO"));
        dto.getSorteio().setDescricao(resultSet.getString("DESCRICAO"));
        dto.getSorteio().setInicioEmissaoNotaFiscal(resultSet.getDate("INICIO_EMISSAO_NOTA_FISCAL"));
        dto.getSorteio().setFimEmissaoNotaFiscal(resultSet.getDate("FIM_EMISSAO_NOTA_FISCAL"));
        dto.getSorteio().setDataSorteio(resultSet.getDate("DATA_SORTEIO"));
        dto.setQuantidadeCupons(resultSet.getInt("QUANTIDADE_CUPONS"));
        dto.setQuantidadeCuponsPremiados(resultSet.getInt("QUANTIDADE_CUPONS_PREMIADOS"));
        return dto;
    }
}
