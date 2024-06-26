package org.example.BackEnd;

import java.io.Serializable;
import java.time.LocalDate;

public class Requisitar implements Serializable {
    private static final long serialVersionUID = 1L;

    private Socio socio;
    private Livro livro;
    private LocalDate dataRequisicao;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucao;

    public Requisitar(Socio socio, Livro livro, LocalDate dataRequisicao, LocalDate dataDevolucaoPrevista) {
        this.socio = socio;
        this.livro = livro;
        this.dataRequisicao = dataRequisicao;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Socio getSocio() {
        return socio;
    }

    public Livro getLivro() {
        return livro;
    }

    public LocalDate getDataRequisicao() {
        return dataRequisicao;
    }

    public LocalDate getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
