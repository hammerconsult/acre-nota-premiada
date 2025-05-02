package br.com.webpublico.domain;

import br.com.webpublico.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class BilheteDTO implements Serializable, RowMapper<BilheteDTO> {

    private Long id;
    private String numero;
    private Long numeroNotaFiscal;
    private Date emissaoNotaFiscal;
    private String cpfCnpjPrestador;
    private String nomeRazaoSocialPrestador;
    private String bairroPrestador;
    private String cpfCnpjTomador;
    private String cpfCnpjTomadorParcial;
    private String nomeRazaoSocialTomador;
    private String bairroTomador;
    private String descricaoServico;
    private BigDecimal totalNotaFiscal;
    private Boolean premiado;
    private PremioSorteioDTO premiacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getNumeroNotaFiscal() {
        return numeroNotaFiscal;
    }

    public void setNumeroNotaFiscal(Long numeroNotaFiscal) {
        this.numeroNotaFiscal = numeroNotaFiscal;
    }

    public Date getEmissaoNotaFiscal() {
        return emissaoNotaFiscal;
    }

    public void setEmissaoNotaFiscal(Date emissaoNotaFiscal) {
        this.emissaoNotaFiscal = emissaoNotaFiscal;
    }

    public String getCpfCnpjPrestador() {
        return cpfCnpjPrestador;
    }

    public void setCpfCnpjPrestador(String cpfCnpjPrestador) {
        this.cpfCnpjPrestador = cpfCnpjPrestador;
    }

    public String getNomeRazaoSocialPrestador() {
        return nomeRazaoSocialPrestador;
    }

    public void setNomeRazaoSocialPrestador(String nomeRazaoSocialPrestador) {
        this.nomeRazaoSocialPrestador = nomeRazaoSocialPrestador;
    }

    public String getBairroPrestador() {
        return bairroPrestador;
    }

    public void setBairroPrestador(String bairroPrestador) {
        this.bairroPrestador = bairroPrestador;
    }

    public String getCpfCnpjTomador() {
        return cpfCnpjTomador;
    }

    public void setCpfCnpjTomador(String cpfCnpjTomador) {
        this.cpfCnpjTomador = cpfCnpjTomador;
    }

    public String getCpfCnpjTomadorParcial() {
        return cpfCnpjTomadorParcial;
    }

    public void setCpfCnpjTomadorParcial(String cpfCnpjTomadorParcial) {
        this.cpfCnpjTomadorParcial = cpfCnpjTomadorParcial;
    }

    public String getNomeRazaoSocialTomador() {
        return nomeRazaoSocialTomador;
    }

    public void setNomeRazaoSocialTomador(String nomeRazaoSocialTomador) {
        this.nomeRazaoSocialTomador = nomeRazaoSocialTomador;
    }

    public String getBairroTomador() {
        return bairroTomador;
    }

    public void setBairroTomador(String bairroTomador) {
        this.bairroTomador = bairroTomador;
    }

    public String getDescricaoServico() {
        return descricaoServico;
    }

    public void setDescricaoServico(String descricaoServico) {
        this.descricaoServico = descricaoServico;
    }

    public BigDecimal getTotalNotaFiscal() {
        return totalNotaFiscal;
    }

    public void setTotalNotaFiscal(BigDecimal totalNotaFiscal) {
        this.totalNotaFiscal = totalNotaFiscal;
    }

    public Boolean getPremiado() {
        return premiado;
    }

    public void setPremiado(Boolean premiado) {
        this.premiado = premiado;
    }

    public PremioSorteioDTO getPremiacao() {
        return premiacao;
    }

    public void setPremiacao(PremioSorteioDTO premiacao) {
        this.premiacao = premiacao;
    }

    @Override
    public BilheteDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        BilheteDTO dto = new BilheteDTO();
        dto.setId(resultSet.getLong("id"));
        dto.setNumero(resultSet.getString("numero"));
        dto.setNumeroNotaFiscal(resultSet.getLong("numero_nota_fiscal"));
        dto.setEmissaoNotaFiscal(resultSet.getDate("emissao_nota_fiscal"));
        dto.setCpfCnpjPrestador(resultSet.getString("cpfcnpj_prestador"));
        dto.setNomeRazaoSocialPrestador(resultSet.getString("nomerazaosocial_prestador"));
        dto.setBairroPrestador(resultSet.getString("bairro_prestador"));
        dto.setCpfCnpjTomador(resultSet.getString("cpfcnpj_tomador"));
        dto.setCpfCnpjTomadorParcial(StringUtils.mascaraCpfParcial(dto.getCpfCnpjTomador()));
        dto.setNomeRazaoSocialTomador(resultSet.getString("nomerazaosocial_tomador"));
        dto.setBairroTomador(resultSet.getString("bairro_tomador"));
        dto.setDescricaoServico(resultSet.getString("descricao_servico"));
        dto.setTotalNotaFiscal(resultSet.getBigDecimal("total_nota_fiscal"));
        dto.setPremiado(resultSet.getLong("id_premio") != 0);
        if (resultSet.getLong("id_premio") != 0) {
            dto.setPremiacao(new PremioSorteioDTO());
            dto.getPremiacao().setId(resultSet.getLong("id_premio"));
            dto.getPremiacao().setSequencia(resultSet.getInt("sequencia_premio"));
            dto.getPremiacao().setDescricao(resultSet.getString("descricao_premio"));
            dto.getPremiacao().setQuantidade(resultSet.getInt("quantidade_premio"));
            dto.getPremiacao().setValor(resultSet.getBigDecimal("valor_premio"));
        }
        return dto;
    }
}
