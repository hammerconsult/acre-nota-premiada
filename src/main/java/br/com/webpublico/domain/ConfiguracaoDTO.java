package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracaoDTO implements Serializable, RowMapper<ConfiguracaoDTO> {

    private Long id;
    private MunicipioDTO municipio;
    private String logoMunicipio;
    private String secretaria;
    private String departamento;
    private String endereco;
    private String tituloNotaPremiada;
    private String imagemNotaPremiada;
    private String tituloAtendimento;
    private String enderecoAtendimento;
    private String horarioAtendimento;
    private String telefoneAtendimento;
    private Boolean producao;
    private DetentorArquivoComposicaoDTO arquivoBrasao;
    private String urlSistema;

    public ConfiguracaoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MunicipioDTO getMunicipio() {
        return municipio;
    }

    public void setMunicipio(MunicipioDTO municipio) {
        this.municipio = municipio;
    }

    public String getLogoMunicipio() {
        return logoMunicipio;
    }

    public void setLogoMunicipio(String logoMunicipio) {
        this.logoMunicipio = logoMunicipio;
    }

    public String getSecretaria() {
        return secretaria;
    }

    public void setSecretaria(String secretaria) {
        this.secretaria = secretaria;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTituloNotaPremiada() {
        return tituloNotaPremiada;
    }

    public void setTituloNotaPremiada(String tituloNotaPremiada) {
        this.tituloNotaPremiada = tituloNotaPremiada;
    }

    public String getImagemNotaPremiada() {
        return imagemNotaPremiada;
    }

    public void setImagemNotaPremiada(String imagemNotaPremiada) {
        this.imagemNotaPremiada = imagemNotaPremiada;
    }

    public String getTituloAtendimento() {
        return tituloAtendimento;
    }

    public void setTituloAtendimento(String tituloAtendimento) {
        this.tituloAtendimento = tituloAtendimento;
    }

    public String getEnderecoAtendimento() {
        return enderecoAtendimento;
    }

    public void setEnderecoAtendimento(String enderecoAtendimento) {
        this.enderecoAtendimento = enderecoAtendimento;
    }

    public String getHorarioAtendimento() {
        return horarioAtendimento;
    }

    public void setHorarioAtendimento(String horarioAtendimento) {
        this.horarioAtendimento = horarioAtendimento;
    }

    public String getTelefoneAtendimento() {
        return telefoneAtendimento;
    }

    public void setTelefoneAtendimento(String telefoneAtendimento) {
        this.telefoneAtendimento = telefoneAtendimento;
    }

    public Boolean getProducao() {
        return producao;
    }

    public void setProducao(Boolean producao) {
        this.producao = producao;
    }

    public DetentorArquivoComposicaoDTO getArquivoBrasao() {
        return arquivoBrasao;
    }

    public void setArquivoBrasao(DetentorArquivoComposicaoDTO arquivoBrasao) {
        this.arquivoBrasao = arquivoBrasao;
    }

    public String getUrlSistema() {
        return urlSistema;
    }

    public void setUrlSistema(String urlSistema) {
        this.urlSistema = urlSistema;
    }

    @Override
    public ConfiguracaoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        ConfiguracaoDTO dto = new ConfiguracaoDTO();
        dto.setId(resultSet.getLong("ID"));
        if (resultSet.getLong("CIDADE_ID") != 0) {
            dto.setMunicipio(new MunicipioDTO());
            dto.getMunicipio().setId(resultSet.getLong("CIDADE_ID"));
        }
        dto.setSecretaria(resultSet.getString("SECRETARIA"));
        dto.setDepartamento(resultSet.getString("DEPARTAMENTO"));
        dto.setEndereco(resultSet.getString("ENDERECO"));
        if (resultSet.getLong("ARQUIVOBRASAO_ID") != 0) {
            dto.setArquivoBrasao(new DetentorArquivoComposicaoDTO());
            dto.getArquivoBrasao().setId(resultSet.getLong("ARQUIVOBRASAO_ID"));
        }
        dto.setUrlSistema(resultSet.getString("URL_SISTEMA"));
        dto.setTituloNotaPremiada(resultSet.getString("TITULOPORTALNOTAPREMIADA"));
        dto.setTituloAtendimento(resultSet.getString("TITULOATENDIMENTO"));
        dto.setEnderecoAtendimento(resultSet.getString("ENDERECOATENDIMENTO"));
        dto.setHorarioAtendimento(resultSet.getString("HORARIOATENDIMENTO"));
        dto.setTelefoneAtendimento(resultSet.getString("TELEFONEATENDIMENTO"));
        return dto;
    }
}
