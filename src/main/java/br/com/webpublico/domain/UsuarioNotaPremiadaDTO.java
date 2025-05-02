package br.com.webpublico.domain;

import br.com.webpublico.DateUtils;
import br.com.webpublico.exception.OperacaoNaoPermitidaException;
import br.com.webpublico.util.Util;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioNotaPremiadaDTO implements Serializable, RowMapper<UsuarioNotaPremiadaDTO>, BatchPreparedStatementSetter {

    private Long id;
    private DadosPessoaisDTO dadosPessoais;
    private String login;
    private String password;
    private String dicaSenha;
    private String imagem;
    private Boolean ativo;
    private String resetKey;
    private Date resetDate;
    private List<String> roles;
    private Boolean participandoPrograma;

    //utilizada apenas para cadastro
    private String senha;
    private String confirmarSenha;
    private Boolean alteracao;

    public UsuarioNotaPremiadaDTO() {
        this.ativo = Boolean.TRUE;
        this.roles = Lists.newArrayList();
        this.alteracao = Boolean.FALSE;
        this.participandoPrograma = Boolean.TRUE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DadosPessoaisDTO getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoaisDTO dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDicaSenha() {
        return dicaSenha;
    }

    public void setDicaSenha(String dicaSenha) {
        this.dicaSenha = dicaSenha;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmarSenha() {
        return confirmarSenha;
    }

    public void setConfirmarSenha(String confirmarSenha) {
        this.confirmarSenha = confirmarSenha;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Date getResetDate() {
        return resetDate;
    }

    public void setResetDate(Date resetDate) {
        this.resetDate = resetDate;
    }

    public Boolean getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Boolean alteracao) {
        this.alteracao = alteracao;
    }

    public Boolean getParticipandoPrograma() {
        return participandoPrograma;
    }

    public void setParticipandoPrograma(Boolean participandoPrograma) {
        this.participandoPrograma = participandoPrograma;
    }

    public void validar() {
        OperacaoNaoPermitidaException e = new OperacaoNaoPermitidaException();
        if (Strings.isNullOrEmpty(dadosPessoais.getCpfCnpj())) {
            e.adicionarMensagem("O campo CPF deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getNomeRazaoSocial())) {
            e.adicionarMensagem("O campo Nome deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getEmail())) {
            e.adicionarMensagem("O campo E-mail deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getTelefone())) {
            e.adicionarMensagem("O campo Telefone deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getCep())) {
            e.adicionarMensagem("O campo CEP deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getBairro())) {
            e.adicionarMensagem("O campo Bairro deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getLogradouro())) {
            e.adicionarMensagem("O campo Logradouro deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getNumero())) {
            e.adicionarMensagem("O campo Número deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getMunicipio())) {
            e.adicionarMensagem("O campo Cidade deve ser informado.");
        }
        if (Strings.isNullOrEmpty(dadosPessoais.getUf())) {
            e.adicionarMensagem("O campo UF deve ser informado.");
        }
        if (id == null) {
            if (Strings.isNullOrEmpty(senha)) {
                e.adicionarMensagem("O campo Senha deve ser informado.");
            }
            if (Strings.isNullOrEmpty(confirmarSenha)) {
                e.adicionarMensagem("O campo Confirmar Senha deve ser informado.");
            }
            if (!Strings.isNullOrEmpty(senha) &&
                    !Strings.isNullOrEmpty(confirmarSenha) &&
                    !senha.equals(confirmarSenha)) {
                e.adicionarMensagem("A confirmação da senha não confere.");
            }
        }
        e.lancarExcecao();
    }

    @Override
    public void setValues(PreparedStatement ps, int i) throws SQLException {
        if (!alteracao) {
            ps.setLong(1, getId());
            ps.setLong(2, getDadosPessoais().getId());
            ps.setString(3, getLogin());
            ps.setString(4, getPassword());
            ps.setBoolean(5, getAtivo());
            Util.setClob(ps, 6, getImagem());
            ps.setString(7, getDicaSenha());
            ps.setString(8, getResetKey());
            ps.setDate(9, DateUtils.toSQLDate(getResetDate()));
            ps.setBoolean(10, getParticipandoPrograma());
        } else {
            Util.setClob(ps, 1, getImagem());
            ps.setString(2, getResetKey());
            ps.setDate(3, DateUtils.toSQLDate(getResetDate()));
            ps.setString(4, getPassword());
            ps.setBoolean(5, getParticipandoPrograma());
            ps.setLong(6, getId());
        }
    }

    @Override
    public int getBatchSize() {
        return 1;
    }

    @Override
    public UsuarioNotaPremiadaDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        UsuarioNotaPremiadaDTO dto = new UsuarioNotaPremiadaDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setLogin(resultSet.getString("LOGIN"));
        dto.setPassword(resultSet.getString("PASSWORD"));
        dto.setAtivo(resultSet.getBoolean("ATIVO"));
        dto.setDicaSenha(resultSet.getString("DICASENHA"));
        dto.setImagem(Util.fromClob(resultSet.getClob("IMAGEM")));
        dto.setResetKey(resultSet.getString("RESETKEY"));
        dto.setResetDate(resultSet.getDate("RESETDATE"));
        dto.setDadosPessoais(new DadosPessoaisDTO());
        dto.getDadosPessoais().setId(resultSet.getLong("ID_DADOSPESSOAIS"));
        dto.getDadosPessoais().setCpfCnpj(resultSet.getString("CPFCNPJ"));
        dto.getDadosPessoais().setNomeRazaoSocial(resultSet.getString("NOMERAZAOSOCIAL"));
        dto.getDadosPessoais().setInscricaoEstadualRg(resultSet.getString("INSCRICAOESTADUALRG"));
        dto.getDadosPessoais().setApelido(resultSet.getString("APELIDO"));
        dto.getDadosPessoais().setTelefone(resultSet.getString("TELEFONE"));
        dto.getDadosPessoais().setCelular(resultSet.getString("CELULAR"));
        dto.getDadosPessoais().setCep(resultSet.getString("CEP"));
        dto.getDadosPessoais().setBairro(resultSet.getString("BAIRRO"));
        dto.getDadosPessoais().setLogradouro(resultSet.getString("LOGRADOURO"));
        dto.getDadosPessoais().setNumero(resultSet.getString("NUMERO"));
        dto.getDadosPessoais().setComplemento(resultSet.getString("COMPLEMENTO"));
        dto.getDadosPessoais().setMunicipio(resultSet.getString("MUNICIPIO"));
        dto.getDadosPessoais().setUf(resultSet.getString("UF"));
        dto.getDadosPessoais().setEmail(resultSet.getString("EMAIL"));
        dto.getDadosPessoais().setDataNascimento(resultSet.getDate("DATANASCIMENTO"));
        dto.setRoles(new ArrayList<>());
        String permissoes = resultSet.getString("PERMISSOES");
        if (!Strings.isNullOrEmpty(permissoes)) {
            for (String permissao : permissoes.split(",")) {
                dto.getRoles().add(permissao);
            }
        }
        if (resultSet.getBoolean("TERMO_ASSINADO")) {
            dto.getRoles().add("ROLE_TERMO_USO");
        }
        return dto;
    }
}
