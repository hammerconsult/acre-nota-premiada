package br.com.webpublico.domain;

public class ImagemUsuarioDTO {

    private Long id;
    private String conteudo;
    private PessoaDTO pessoa;

    public ImagemUsuarioDTO(String conteudo, PessoaDTO pessoa, Long id) {
        this.conteudo = conteudo;
        this.pessoa = pessoa;
        this.id = id;
    }

    public ImagemUsuarioDTO() {

    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
