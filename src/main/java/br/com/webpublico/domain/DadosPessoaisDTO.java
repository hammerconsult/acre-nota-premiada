package br.com.webpublico.domain;

import br.com.webpublico.DateUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DadosPessoaisDTO implements BatchPreparedStatementSetter, RowMapper<DadosPessoaisDTO> {

    private Long id;
    private String cpfCnpj;
    private String inscricaoMunicipal;
    private String inscricaoEstadualRg;
    private String nomeRazaoSocial;
    private String nomeFantasia;
    private String apelido;
    private String email;
    private String telefone;
    private String celular;
    private String cep;
    private String numero;
    private String logradouro;
    private String bairro;
    private String complemento;
    private String codigoMunicipio;
    private String municipio;
    private String codigoPais;
    private String pais;
    private String numeroIdentificacao;
    private String uf;
    private Date dataNascimento;
    private Boolean alteracao;

    public DadosPessoaisDTO() {
        this.alteracao = Boolean.FALSE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getInscricaoEstadualRg() {
        return inscricaoEstadualRg;
    }

    public void setInscricaoEstadualRg(String inscricaoEstadualRg) {
        this.inscricaoEstadualRg = inscricaoEstadualRg;
    }

    public String getNomeRazaoSocial() {
        return nomeRazaoSocial;
    }

    public void setNomeRazaoSocial(String nomeRazaoSocial) {
        this.nomeRazaoSocial = nomeRazaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNumeroIdentificacao() {
        return numeroIdentificacao;
    }

    public void setNumeroIdentificacao(String numeroIdentificacao) {
        this.numeroIdentificacao = numeroIdentificacao;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Boolean alteracao) {
        this.alteracao = alteracao;
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        if (!alteracao) {
            ps.setLong(1, getId());
            ps.setString(2, getCpfCnpj());
            ps.setString(3, getNomeRazaoSocial());
            ps.setString(4, getInscricaoEstadualRg());
            ps.setString(5, getApelido());
            ps.setString(6, getEmail());
            ps.setString(7, getTelefone());
            ps.setString(8, getCelular());
            ps.setString(9, getCep());
            ps.setString(10, getBairro());
            ps.setString(11, getLogradouro());
            ps.setString(12, getNumero());
            ps.setString(13, getComplemento());
            ps.setString(14, getMunicipio());
            ps.setString(15, getUf());
            ps.setDate(16, DateUtils.toSQLDate(getDataNascimento()));
        } else {
            ps.setString(1, getCpfCnpj());
            ps.setString(2, getNomeRazaoSocial());
            ps.setString(3, getInscricaoEstadualRg());
            ps.setString(4, getApelido());
            ps.setString(5, getEmail());
            ps.setString(6, getTelefone());
            ps.setString(7, getCelular());
            ps.setString(8, getCep());
            ps.setString(9, getBairro());
            ps.setString(10, getLogradouro());
            ps.setString(11, getNumero());
            ps.setString(12, getComplemento());
            ps.setString(13, getMunicipio());
            ps.setString(14, getUf());
            ps.setDate(15, DateUtils.toSQLDate(getDataNascimento()));
            ps.setLong(16, getId());
        }
    }

    @Override
    public int getBatchSize() {
        return 1;
    }

    @Override
    public DadosPessoaisDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        DadosPessoaisDTO dto = new DadosPessoaisDTO();
        dto.setCpfCnpj(resultSet.getString("CPFCNPJ"));
        dto.setNomeRazaoSocial(resultSet.getString("NOMERAZAOSOCIAL"));
        dto.setNomeFantasia(resultSet.getString("NOMEFANTASIA"));
        dto.setEmail(resultSet.getString("EMAIL"));
        dto.setInscricaoEstadualRg(resultSet.getString("INSCRICAOESTADUALRG"));
        dto.setCep(resultSet.getString("CEP"));
        dto.setBairro(resultSet.getString("BAIRRO"));
        dto.setLogradouro(resultSet.getString("LOGRADOURO"));
        dto.setNumero(resultSet.getString("NUMERO"));
        dto.setComplemento(resultSet.getString("COMPLEMENTO"));
        dto.setMunicipio(resultSet.getString("MUNICIPIO"));
        dto.setUf(resultSet.getString("UF"));
        dto.setDataNascimento(resultSet.getDate("DATANASCIMENTO"));
        return dto;
    }
}
