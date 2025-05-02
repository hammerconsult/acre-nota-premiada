package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SorteioUsuarioPremioDTO implements Serializable, RowMapper<SorteioUsuarioPremioDTO> {

    private Long idPremio;
    private String premio;
    private Date dataSorteio;
    private Long numeroSorteado;
    private SituacaoPremio situacaoPremio;
    private Integer quantidadeCupons;
    private Integer quantidadeCuponsPremiados;
    private SituacaoUsuario situacaoUsuario;

    public Long getIdPremio() {
        return idPremio;
    }

    public void setIdPremio(Long idPremio) {
        this.idPremio = idPremio;
    }

    public String getPremio() {
        return premio;
    }

    public void setPremio(String premio) {
        this.premio = premio;
    }

    public Date getDataSorteio() {
        return dataSorteio;
    }

    public void setDataSorteio(Date dataSorteio) {
        this.dataSorteio = dataSorteio;
    }

    public Long getNumeroSorteado() {
        return numeroSorteado;
    }

    public void setNumeroSorteado(Long numeroSorteado) {
        this.numeroSorteado = numeroSorteado;
    }

    public SituacaoPremio getSituacaoPremio() {
        return situacaoPremio;
    }

    public void setSituacaoPremio(SituacaoPremio situacaoPremio) {
        this.situacaoPremio = situacaoPremio;
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

    public SituacaoUsuario getSituacaoUsuario() {
        return situacaoUsuario;
    }

    public void setSituacaoUsuario(SituacaoUsuario situacaoUsuario) {
        this.situacaoUsuario = situacaoUsuario;
    }

    @Override
    public SorteioUsuarioPremioDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        SorteioUsuarioPremioDTO dto = new SorteioUsuarioPremioDTO();
        dto.setIdPremio(resultSet.getLong("IDPREMIO"));
        dto.setPremio(resultSet.getString("PREMIO"));
        dto.setDataSorteio(resultSet.getDate("DATASORTEIO"));
        dto.setQuantidadeCupons(resultSet.getInt("QTD_CUPONS"));
        dto.setQuantidadeCuponsPremiados(resultSet.getInt("QTD_CUPONS_PREMIADOS"));
        dto.setNumeroSorteado(resultSet.getLong("NUMERO_SORTEADO"));
        if (dto.getQuantidadeCuponsPremiados() != null && dto.getQuantidadeCuponsPremiados() > 0) {
            dto.setSituacaoUsuario(SorteioUsuarioPremioDTO.SituacaoUsuario.CUPOM_PREMIADO);
        } else if (dto.getNumeroSorteado() != null && dto.getNumeroSorteado() > 0) {
            dto.setSituacaoUsuario(SorteioUsuarioPremioDTO.SituacaoUsuario.NAO_FOI_DESSA_VEZ);
        } else {
            dto.setSituacaoUsuario(SorteioUsuarioPremioDTO.SituacaoUsuario.PROXIMO_SORTEIO);
        }
        if (dto.getNumeroSorteado() != null && dto.getNumeroSorteado() > 0) {
            dto.setSituacaoPremio(SorteioUsuarioPremioDTO.SituacaoPremio.SORTEIO_REALIZADO);
        } else if (resultSet.getBoolean("CUPOM_GERADO")) {
            dto.setSituacaoPremio(SituacaoPremio.CUPONS_PUBLICADOS);
        } else {
            dto.setSituacaoPremio(SituacaoPremio.AGUARDANDO_PUBLICACAO_CUPONS);
        }
        return dto;
    }

    public enum SituacaoUsuario {
        NAO_FOI_DESSA_VEZ,
        CUPOM_PREMIADO,
        PROXIMO_SORTEIO
    }

    public enum SituacaoPremio {
        SORTEIO_REALIZADO,
        CUPONS_PUBLICADOS,
        AGUARDANDO_PUBLICACAO_CUPONS
    }
}
