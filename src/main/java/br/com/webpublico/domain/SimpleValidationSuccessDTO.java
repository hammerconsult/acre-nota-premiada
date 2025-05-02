package br.com.webpublico.domain;


public class SimpleValidationSuccessDTO {

    private String title;
    private String message;

    public SimpleValidationSuccessDTO() {
    }

    public String getMessage() {
        return message;
    }

    public SimpleValidationSuccessDTO(String title, String message) {
        this.message = message;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
