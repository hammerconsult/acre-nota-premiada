package br.com.webpublico.repository;

import br.com.webpublico.domain.MunicipioDTO;
import br.com.webpublico.repository.mapper.MunicipioMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class MunicipioJDBCRepository implements Serializable {

    private static final String SQL_FIELDS = " SELECT C.ID AS ID," +
            " C.NOME AS NOME, C.CODIGO AS CODIGO, UF.UF AS ESTADO ";
    private static final String SQL_FROM = "   FROM CIDADE C" +
            " INNER JOIN UF UF ON UF.ID = C.UF_ID ";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public MunicipioDTO findById(Long id) {
        List<MunicipioDTO> query = jdbcTemplate.query(SQL_FIELDS + SQL_FROM +
                        " WHERE C.ID = ? ", new Object[]{id},
                new MunicipioMapper());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }

    public MunicipioDTO findByCodigoIBGE(String codigoIBGE) {
        List<MunicipioDTO> query = jdbcTemplate.query(SQL_FIELDS + SQL_FROM +
                        " WHERE C.CODIGOIBGE = ? ", new Object[]{codigoIBGE},
                new MunicipioMapper());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }

    public MunicipioDTO buscarPorNomeAndEstado(String nome, String estado) {
        if (StringUtils.isEmpty(nome) || StringUtils.isEmpty(estado)) {
            return null;
        }
        String hql = "select c from Cidade c" +
                " inner join c.uf uf " +
                " where lower(c.nome) like :cidade " +
                " and lower(uf.uf) like :uf";

        List<MunicipioDTO> query = jdbcTemplate.query(SQL_FIELDS + SQL_FROM +
                        " where lower(c.nome) like ? and lower(uf.uf) like ? ",
                new Object[]{"%" + nome.toLowerCase().trim() + "%", "%" + estado.toLowerCase().trim() + "%"},
                new MunicipioMapper());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }
}
