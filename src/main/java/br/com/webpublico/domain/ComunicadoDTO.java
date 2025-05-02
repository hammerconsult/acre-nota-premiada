package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ComunicadoDTO implements RowMapper<ComunicadoDTO> {

    private Long id;
    private String titulo;
    private Date inicio;
    private Date fim;
    private Long idDetentor;

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

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Long getIdDetentor() {
        return idDetentor;
    }

    public void setIdDetentor(Long idDetentor) {
        this.idDetentor = idDetentor;
    }

    @Override
    public ComunicadoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        ComunicadoDTO dto = new ComunicadoDTO();
        dto.setId(resultSet.getLong("id"));
        dto.setTitulo(resultSet.getString("titulo"));
        dto.setInicio(resultSet.getDate("inicio"));
        dto.setFim(resultSet.getDate("fim"));
        dto.setIdDetentor(resultSet.getLong("detentorarquivocomposicao_id"));
        return dto;
    }
}
