package org.example;

public class Livro {
    private String titulo;
    private String isbn;
    private String edicao;
    private String genero;
    private String subGenero;
    private String localizacao;
    private String autor;
    private String editora;
    private int ano;
    private String quantidade;

    // Construtor
    public Livro(String titulo, String isbn, String edicao, String genero, String subGenero, String localizacao, String autor, String editora, int ano, String quantidade) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.edicao = edicao;
        this.genero = genero;
        this.subGenero = subGenero;
        this.localizacao = localizacao;
        this.autor = autor;
        this.editora = editora;
        this.ano = ano;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSubGenero() {
        return subGenero;
    }

    public void setSubGenero(String subGenero) {
        this.subGenero = subGenero;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", isbn='" + isbn + '\'' +
                ", edicao='" + edicao + '\'' +
                ", genero='" + genero + '\'' +
                ", subGenero='" + subGenero + '\'' +
                ", localizacao='" + localizacao + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                ", ano=" + ano +
                ", quantidade='" + quantidade + '\'' +
                '}';
    }
}
