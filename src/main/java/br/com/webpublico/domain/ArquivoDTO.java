package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class ArquivoDTO implements Serializable, RowMapper<ArquivoDTO> {

    private Long id;
    private String descricao;
    private String nome;
    private String mimeType;
    private Long tamanho;
    private Date dataUpload;
    private List<ArquivoParteDTO> partes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public List<ArquivoParteDTO> getPartes() {
        return partes;
    }

    public void setPartes(List<ArquivoParteDTO> partes) {
        this.partes = partes;
    }

    public Date getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(Date dataUpload) {
        this.dataUpload = dataUpload;
    }

    @Override
    public ArquivoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArquivoDTO dto = new ArquivoDTO();
        dto.setId(rs.getLong("ID"));
        dto.setDescricao(rs.getString("DESCRICAO"));
        dto.setNome(rs.getString("NOME"));
        dto.setDataUpload(rs.getDate("DATAUPLOAD"));
        return dto;
    }
}
