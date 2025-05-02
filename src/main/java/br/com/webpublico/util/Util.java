package br.com.webpublico.util;

import br.com.webpublico.domain.ArquivoParteDTO;
import com.google.common.base.Strings;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.List;

/**
 * Created by Rodolfo on 22/12/2016.
 */
public class Util {

    public static String getBaseUrl(HttpServletRequest request) {
        String baseUrl = request.getScheme() + // "http"
                "://" +                                // "://"
                request.getServerName() +              // "myhost"
                ":" +                                  // ":"
                request.getServerPort();               // "80"

        return baseUrl;
    }

    public static String formatXml(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);

            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static Enum[] getValuesForEnum(Class clazz) {
        return (Enum[]) clazz.getEnumConstants();
    }

    public static boolean isFisica(String cpfCnpj) {
        return cpfCnpj.replaceAll("\\D+", "").length() == 11;
    }

    public static boolean isJuridica(String cpfCnpj) {
        return cpfCnpj.replaceAll("\\D+", "").length() == 14;
    }

    public static byte[] getByteArrayDosDados(List<ArquivoParteDTO> partes) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            for (ArquivoParteDTO a : partes) {
                buffer.write(a.getDados());
            }
            return buffer.toByteArray();
        } catch (Exception ex) {
            System.out.println("Erro ao recuperar o arquivo " + ex.getMessage());
        }
        return null;
    }

    public static String removeZerosEsquerda(String texto) {
        if (texto == null) {
            return "";
        }
        while (texto.startsWith("0")) {
            texto = texto.substring(1, texto.length());
        }
        return texto;
    }

    public static String fromClob(Clob clob) {
        try {
            if (clob != null) {
                Reader reader = clob.getCharacterStream();
                StringBuffer buffer = new StringBuffer();
                int ch;
                while ((ch = reader.read()) != -1) {
                    buffer.append("" + (char) ch);
                }
                reader.close();
                return buffer.toString();
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void setClob(PreparedStatement ps, int index, String content) {
        try {
            if (!Strings.isNullOrEmpty(content)) {
                Reader reader = new StringReader(content);
                ps.setClob(index, reader);
                reader.close();
            } else {
                ps.setNull(index, Types.NULL);
            }
        } catch (Exception e) {
        }
    }

    public static InputStream base64ToInputStream(String conteudo) {
        String data = "";

        try {
            data = conteudo.split("base64,")[1];
        } catch (ArrayIndexOutOfBoundsException var4) {
            data = conteudo;
        }

        Base64 decoder = new Base64();
        byte[] imgBytes = Base64.decode(data);
        return new ByteArrayInputStream(imgBytes);
    }
}
