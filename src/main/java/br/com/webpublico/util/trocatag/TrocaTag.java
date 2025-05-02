package br.com.webpublico.util.trocatag;


import br.com.webpublico.domain.ConfiguracaoDTO;
import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import com.google.common.collect.Lists;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;

public abstract class TrocaTag<T extends TipoTemplate> {
    protected final static DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd/MM/yyyy");
    protected final static DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
    private final Logger logger = LoggerFactory.getLogger(TrocaTag.class);
    private List<Field> fields = Lists.newArrayList();
    private T tipoTemplate;

    TrocaTag(T tipoTemplate) {
        this.tipoTemplate = tipoTemplate;
    }

    public List<Field> getFields() {
        return fields;
    }

    void addicionarField(Field field) {
        fields.add(field);
    }

    public T getTipoTemplate() {
        return tipoTemplate;
    }

    public String trocarTags(String conteudo) {
        try {
            Properties p = new Properties();
            p.setProperty("resource.loader", "string");
            p.setProperty("string.resource.loader.class",
                    "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
            VelocityEngine ve = new VelocityEngine();
            ve.init(p);

            VelocityContext context = new VelocityContext();
            for (Field field : fields) {
                if (field.getValue() != null) {
                    context.put(field.getTag().name(), field.getValue());
                } else {
                    context.put(field.getTag().name(), "");
                }
            }
            StringWriter writer = new StringWriter();
            ve.evaluate(context, writer, "str", conteudo);
            return writer.toString();
        } catch (Exception e) {
            logger.debug("Exceção ao trocar as tags de {}", tipoTemplate);
            return conteudo;
        }
    }

    public void addFieldsUser(UsuarioNotaPremiadaDTO user) {
        addicionarField(new Field(TagUsuario.CPF_CNPJ_USUARIO, user.getDadosPessoais().getCpfCnpj()));
        addicionarField(new Field(TagUsuario.NOME_RAZAOSOCIAL_USUARIO, user.getDadosPessoais().getNomeRazaoSocial()));
    }

    public void addFieldsConfiguracao(ConfiguracaoDTO configuracao) {
        addicionarField(new Field(TagComum.MUNICIPIO, configuracao.getMunicipio().getNome()));
        addicionarField(new Field(TagComum.ESTADO, configuracao.getMunicipio().getEstado()));
    }
}
