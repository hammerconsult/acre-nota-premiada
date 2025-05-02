package br.com.webpublico.service.util;

public class ValueResource {

    private Object value;

    public ValueResource(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
