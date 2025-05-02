package br.com.webpublico.repository;

import br.com.webpublico.DateUtils;
import br.com.webpublico.domain.BilheteDTO;
import br.com.webpublico.domain.SorteioDTO;
import br.com.webpublico.repository.mapper.IntegerMapper;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class BilheteJDBCRepository implements Serializable {

    private static final String ORDER_BY = " order by case when c.numero >= s.numerosorteado then 0 else 1 end, c.numero ";
    private static final String SELECT = " select " +
            "       row_number() over ( " + ORDER_BY + " ) as linha,  " +
            "       c.id, " +
            "       c.numero as numero, " +
            "       coalesce(nf.numero, sd.numero) as numero_nota_fiscal, " +
            "       coalesce(nf.emissao, sd.emissao) as emissao_nota_fiscal, " +
            "       dpp.cpfcnpj as cpfcnpj_prestador, " +
            "       dpp.nomerazaosocial as nomerazaosocial_prestador, " +
            "       dpp.bairro as bairro_prestador, " +
            "       dpt.cpfcnpj as cpfcnpj_tomador, " +
            "       dpt.nomerazaosocial as nomerazaosocial_tomador, " +
            "       dpt.bairro as bairro_tomador, " +
            "       servico.nome as descricao_servico, " +
            "       dec.totalnota as total_nota_fiscal, " +
            "       p.id as id_premio, " +
            "       p.sequencia as sequencia_premio, " +
            "       p.descricao as descricao_premio, " +
            "       p.quantidade as quantidade_premio, " +
            "       p.valor as valor_premio ";
    private static final String FROM = "     from cupomcampanhanfse c " +
            "  inner join usuarionotapremiada unp on unp.id = c.usuario_id " +
            "  inner join declaracaoprestacaoservico dec on dec.id = c.declaracao_id " +
            "  inner join itemdeclaracaoservico ids on ids.declaracaoprestacaoservico_id = dec.id " +
            "  inner join servico on servico.id = ids.SERVICO_ID " +
            "  left join notafiscal nf on nf.declaracaoprestacaoservico_id = dec.id " +
            "  left join servicodeclarado sd on sd.declaracaoprestacaoservico_id = dec.id " +
            "  inner join dadospessoaisnfse dpp on dpp.id = dec.dadospessoaisprestador_id " +
            "  inner join dadospessoaisnfse dpt on dpt.id = unp.dadospessoais_id " +
            "  left join premiosorteionfse p on p.id = c.premio_id " +
            "  left join sorteionfse s on s.id = p.sorteio_id ";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Page<BilheteDTO> buscarBilhetesPorSorteioAndUsuario(Pageable pageable,
                                                               SorteioDTO sorteio,
                                                               String login,
                                                               Boolean premiado) {

        String sql = SELECT + FROM +
                "  inner join termousonotapremiada tunp on tunp.usuario_id = unp.id" +
                "  inner join termouso t on t.id = tunp.termouso_id " +
                " where t.id = (select max(ult_t.id) " +
                "                  from termouso ult_t " +
                "               where ult_t.sistema = :sistema " +
                "                 and ult_t.iniciovigencia <= :fim_emissao) " +
                "    and tunp.dataaceite <= :fim_emissao " +
                "    and unp.participandoprograma = 1 " +
                "    and unp.ativo = 1 " +
                "    and c.campanha_id = :id_campanha " +
                "    and coalesce(nf.emissao, sd.emissao) between :inicio_emissao and :fim_emissao " +
                "    and (c.premio_id is null or s.id = :id_sorteio) ";
        if (!Strings.isNullOrEmpty(login)) {
            sql += " and unp.login = :login ";
        }
        if (premiado != null) {
            sql += " and case when c.premio_id is not null then 1 else 0 end = :premiado ";
        }
        sql += ORDER_BY;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("sistema", "NOTA_PREMIADA");
        parameters.addValue("id_campanha", sorteio.getIdCampanha());
        parameters.addValue("inicio_emissao", DateUtils.dataSemHorario(sorteio.getInicioEmissaoNotaFiscal()));
        parameters.addValue("fim_emissao", DateUtils.dataSemHorario(sorteio.getFimEmissaoNotaFiscal()));
        parameters.addValue("id_sorteio", sorteio.getId());
        if (!Strings.isNullOrEmpty(login)) {
            parameters.addValue("login", login);
        }
        if (premiado != null) {
            parameters.addValue("premiado", premiado);
        }
        List<BilheteDTO> cupons;
        if (pageable != null) {
            parameters.addValue("offset", pageable.getOffset() + 1);
            parameters.addValue("limit", (pageable.getPageNumber() + 1) * pageable.getPageSize());
            sql = " select * from ( " + sql + ") dados where dados.linha between :offset and :limit ";
            cupons = namedParameterJdbcTemplate.query(sql,
                    parameters,
                    new BilheteDTO());
        } else {
            cupons = namedParameterJdbcTemplate.query(sql,
                    parameters,
                    new BilheteDTO());
        }
        Integer count = 0;
        List<Integer> query = namedParameterJdbcTemplate.query(" select count(1) as value from ( " + sql + " ) dados ",
                parameters, new IntegerMapper());
        if (query != null && !query.isEmpty()) {
            count = query.stream().findFirst().get();
        }
        return new PageImpl<>(cupons, pageable, count);
    }

    public List<BilheteDTO> buscarBilhetesSorteio(SorteioDTO sorteio) throws Exception {
        String sql = SELECT + FROM +
                "  inner join termousonotapremiada tunp on tunp.usuario_id = unp.id" +
                "  inner join termouso t on t.id = tunp.termouso_id " +
                " where t.id = (select max(ult_t.id) " +
                "                  from termouso ult_t " +
                "               where ult_t.sistema = :sistema " +
                "                 and ult_t.iniciovigencia <= :fim_emissao) " +
                "    and tunp.dataaceite <= :fim_emissao " +
                "    and unp.participandoprograma = 1 " +
                "    and unp.ativo = 1 " +
                "    and c.campanha_id = :id_campanha " +
                "    and coalesce(nf.emissao, sd.emissao) between :inicio_emissao and :fim_emissao " +
                "    and (c.premio_id is null or s.numero >= :numero_sorteio) ";
        sql += ORDER_BY;

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("sistema", "NOTA_PREMIADA");
        parameters.addValue("id_campanha", sorteio.getIdCampanha());
        parameters.addValue("inicio_emissao", DateUtils.dataSemHorario(sorteio.getInicioEmissaoNotaFiscal()));
        parameters.addValue("fim_emissao", DateUtils.dataSemHorario(sorteio.getFimEmissaoNotaFiscal()));
        parameters.addValue("numero_sorteio", sorteio.getNumero());
        return namedParameterJdbcTemplate.query(sql,
                parameters,
                new BilheteDTO());
    }

    public BilheteDTO findById(Long id) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        String sql = SELECT + FROM +
                " where c.id = :id ";
        List<BilheteDTO> query = namedParameterJdbcTemplate.query(sql, parameterSource, new BilheteDTO());
        if (query != null && !query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }
}
