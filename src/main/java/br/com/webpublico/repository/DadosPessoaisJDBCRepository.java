package br.com.webpublico.repository;

import br.com.webpublico.StringUtils;
import br.com.webpublico.domain.DadosPessoaisDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public class DadosPessoaisJDBCRepository implements Serializable {

    private static final String SELECT = " SELECT ID, INSCRICAOMUNICIPAL," +
            " INSCRICAOESTADUALRG, NOMERAZAOSOCIAL, NOMEFANTASIA, EMAIL, TELEFONE, " +
            " CELULAR, CEP, NUMERO, LOGRADOURO, BAIRRO, COMPLEMENTO, PESSOA_ID, MUNICIPIO," +
            " UF, APELIDO, PAIS, NUMEROIDENTIFICACAO, CODIGOMUNICIPIO, CODIGOPAIS, " +
            " CPFCNPJ, TIPOISSQN, REGIMETRIBUTARIO, DATANASCIMENTO ";
    private static final String FROM = " FROM DADOSPESSOAISNFSE  ";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private IdJDBCRepository idJDBCRepository;

    public DadosPessoaisDTO inserir(DadosPessoaisDTO dto) {
        if (dto != null) {
            dto.setId(idJDBCRepository.getId());
            jdbcTemplate.batchUpdate("INSERT INTO DADOSPESSOAISNFSE  " +
                    "(ID, CPFCNPJ, NOMERAZAOSOCIAL, INSCRICAOESTADUALRG, APELIDO, EMAIL," +
                    "TELEFONE, CELULAR, CEP, BAIRRO, " +
                    "LOGRADOURO, NUMERO, COMPLEMENTO, MUNICIPIO, UF, DATANASCIMENTO)  " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", dto);
        }
        return dto;
    }

    public DadosPessoaisDTO update(DadosPessoaisDTO dto) {
        if (dto != null) {
            dto.setAlteracao(Boolean.TRUE);
            jdbcTemplate.batchUpdate(" UPDATE DADOSPESSOAISNFSE " +
                    " SET CPFCNPJ = ?, NOMERAZAOSOCIAL = ?, INSCRICAOESTADUALRG = ?, APELIDO = ?, EMAIL = ?," +
                    "TELEFONE = ?, CELULAR = ?, CEP = ?, BAIRRO = ?, " +
                    "LOGRADOURO = ?, NUMERO = ?, COMPLEMENTO = ?, MUNICIPIO = ?, UF = ?, DATANASCIMENTO = ? " +
                    "WHERE ID = ? ", dto);
        }
        return dto;
    }

    public DadosPessoaisDTO findPessoaByCpfCnpj(String cpfCnpj) {
        List<DadosPessoaisDTO> query = jdbcTemplate.query("   SELECT P.ID ID,   " +
                        "       COALESCE(PF.CPF, PJ.CNPJ) AS CPFCNPJ,   " +
                        "       COALESCE(PF.NOME, PJ.RAZAOSOCIAL) AS NOMERAZAOSOCIAL,   " +
                        "       PJ.NOMEFANTASIA AS NOMEFANTASIA,   " +
                        "       P.EMAIL AS EMAIL,   " +
                        "       COALESCE((SELECT RG.NUMERO FROM DOCUMENTOPESSOAL DP   " +
                        "                                          INNER JOIN RG ON RG.ID = DP.ID   " +
                        "                                          WHERE DP.PESSOAFISICA_ID = PF.ID   " +
                        "                                            AND RG.NUMERO != NULL   " +
                        "                                            AND ROWNUM = 1), PJ.INSCRICAOESTADUAL) AS INSCRICAOESTADUALRG, " +
                        "       EC.CEP AS CEP, " +
                        "       EC.LOGRADOURO AS LOGRADOURO, " +
                        "       EC.NUMERO AS NUMERO, " +
                        "       EC.COMPLEMENTO AS COMPLEMENTO, " +
                        "       EC.BAIRRO AS BAIRRO, " +
                        "       EC.LOCALIDADE AS MUNICIPIO, " +
                        "       EC.UF AS UF, " +
                        "       PF.DATANASCIMENTO   " +
                        "   FROM PESSOA P  " +
                        "  LEFT JOIN PESSOAFISICA PF ON PF.ID = P.ID  " +
                        "  LEFT JOIN PESSOAJURIDICA PJ ON PJ.ID = P.ID  " +
                        "  LEFT JOIN ENDERECOCORREIO EC ON EC.ID = P.ENDERECOPRINCIPAL_ID " +
                        " WHERE REPLACE(REPLACE(REPLACE(COALESCE(PF.CPF, PJ.CNPJ), '.'), '-'), '/') = " +
                        " REPLACE(REPLACE(REPLACE(?, '.'), '-'), '/')  ",
                new Object[]{cpfCnpj}, new DadosPessoaisDTO());
        return Optional.ofNullable(query).map(result ->
                result.stream().findFirst().isPresent() ? result.stream().findFirst().get() : null)
                .orElse(null);
    }

    public DadosPessoaisDTO findDadosPessoaisNfseById(Long id) {
        List<DadosPessoaisDTO> query = jdbcTemplate.query("   SELECT " +
                        "       DP.ID,   " +
                        "       DP.CPFCNPJ,   " +
                        "       DP.NOMERAZAOSOCIAL,   " +
                        "       DP.NOMEFANTASIA,   " +
                        "       DP.EMAIL,   " +
                        "       DP.INSCRICAOESTADUALRG, " +
                        "       DP.CEP, " +
                        "       DP.LOGRADOURO, " +
                        "       DP.NUMERO, " +
                        "       DP.COMPLEMENTO, " +
                        "       DP.BAIRRO, " +
                        "       DP.MUNICIPIO, " +
                        "       DP.UF, " +
                        "       DP.DATANASCIMENTO   " +
                        "   FROM DADOSPESSOAISNFSE DP  " +
                        " WHERE DP.ID = ? ",
                new Object[]{id}, new DadosPessoaisDTO());
        return Optional.ofNullable(query).map(result ->
                        result.stream().findFirst().isPresent() ? result.stream().findFirst().get() : null)
                .orElse(null);
    }
}
