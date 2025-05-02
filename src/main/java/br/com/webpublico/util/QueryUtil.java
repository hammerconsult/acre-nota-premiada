package br.com.webpublico.util;

import br.com.webpublico.domain.Operador;
import br.com.webpublico.domain.ParametroQuery;
import br.com.webpublico.domain.ParametroQueryCampo;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryUtil {
    public static Map<String, Object> montarParametroString(StringBuilder sqlBuilder,
                                                            List<ParametroQuery> parametros) throws Exception {
        String juncao = " where ";
        AtomicInteger index = new AtomicInteger();
        Map<String, Object> parameters = Maps.newHashMap();
        if (parametros != null && !parametros.isEmpty()) {
            sqlBuilder.append(juncao);
            juncao = " ";
            for (ParametroQuery parametro : parametros) {
                sqlBuilder.append(juncao).append(" ( ");

                String juncaoCampo = " ";
                for (ParametroQueryCampo parametroQueryCampo : parametro.getParametroQueryCampos()) {
                    if (!Strings.isNullOrEmpty(parametroQueryCampo.getExists())) {
                        sqlBuilder.append(juncaoCampo).append(" ").append(parametroQueryCampo.getExists()).append(" ");
                    } else {
                        sqlBuilder.append(juncaoCampo).append(parametroQueryCampo.getCampo()).append(" ")
                                .append(parametroQueryCampo.getOperador().getOperador()).append(" ");

                        if (parametroQueryCampo.getValor() != null) {
                            parameters.put("index_" + index.get(), parametroQueryCampo.getValor());
                            if (Operador.IN.equals(parametroQueryCampo.getOperador())) {
                                sqlBuilder.append("(:index_" + index.getAndAdd(1) + ")");
                            } else {
                                sqlBuilder.append(":index_" + index.getAndAdd(1));
                            }
                        }
                    }
                    juncaoCampo = parametro.getJuncao();
                }
                sqlBuilder.append(" ) ");
                juncao = " and ";
            }
        }
        return parameters;
    }
}
