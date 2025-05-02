package br.com.webpublico.config;

public abstract class ConnectionUtil {

    private static final String DEFAULT_VALUE_TRIBUARIO = "http://localhost:8080/spring/tributario";
    private static final String DEFAULT_VALUE_NFSE = "http://localhost:8080/spring/nfse";
    private static String VALOR_CALCULADO_TRIBUTARIO = null;
    private static String VALOR_CALCULADO_NFSE = null;

    public static String getURLTributario() {
        if (VALOR_CALCULADO_TRIBUTARIO == null) {
            VALOR_CALCULADO_TRIBUTARIO = calcularURLTributario();
        }
        return VALOR_CALCULADO_TRIBUTARIO;
    }

    public static String getURLNfse() {
        if (VALOR_CALCULADO_NFSE == null) {
            VALOR_CALCULADO_NFSE = calcularURLNfse();
        }
        return VALOR_CALCULADO_NFSE;
    }

    private static String calcularURLTributario() {
        final String url = System.getenv("TRIBUTARIO_ADDRESS");
        System.out.println("URL::::" + url);
        return (url == null || url.trim().isEmpty() ? DEFAULT_VALUE_TRIBUARIO : url);
    }

    private static String calcularURLNfse() {
        final String url = System.getenv("NFSE_ADDRESS");
        System.out.println("URL::::" + url);
        return (url == null || url.trim().isEmpty() ? DEFAULT_VALUE_NFSE : url);
    }


}
