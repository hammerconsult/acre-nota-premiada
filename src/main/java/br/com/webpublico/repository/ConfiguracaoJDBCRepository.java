package br.com.webpublico.repository;

import br.com.webpublico.domain.ConfiguracaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class ConfiguracaoJDBCRepository implements Serializable {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ConfiguracaoDTO find() {
        List<ConfiguracaoDTO> query = jdbcTemplate.query(" SELECT C.ID AS ID, " +
                        " C.CIDADE_ID AS CIDADE_ID," +
                        " CR.SECRETARIA AS SECRETARIA, " +
                        " CR.DEPARTAMENTO AS DEPARTAMENTO, " +
                        " CR.ENDERECO AS ENDERECO,  " +
                        " CR.ARQUIVOBRASAO_ID AS ARQUIVOBRASAO_ID," +
                        " (SELECT VALOR FROM CONFIGURACAONFSEPARAMETROS " +
                        "  WHERE TIPOPARAMETRO = 'URL_APLICACAO_NOTA_PREMIADA') AS URL_SISTEMA," +
                        " CNP.TITULOPORTALNOTAPREMIADA," +
                        " CNP.TITULOATENDIMENTO," +
                        " CNP.ENDERECOATENDIMENTO," +
                        " CNP.HORARIOATENDIMENTO," +
                        " CNP.TELEFONEATENDIMENTO    " +
                        "  FROM CONFIGURACAONFSE C " +
                        " LEFT JOIN CONFIGURACAONFSERELATORIO CR ON CR.ID = C.CONFIGURACAONFSERELATORIO_ID" +
                        " LEFT JOIN CONFIGURACAONOTAPREMIADA CNP ON CNP.ID = C.CONFIGURACAONOTAPREMIADA_ID ",
                new ConfiguracaoDTO());
        if (query != null && !query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }
}
