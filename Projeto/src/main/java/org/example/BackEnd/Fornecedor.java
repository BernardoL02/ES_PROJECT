package org.example.BackEnd;

import java.io.Serializable;

public class Fornecedor implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String distribuidora;

    public Fornecedor(String nome, String distribuidora) {
        this.nome = nome;
        this.distribuidora = distribuidora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(String distribuidora) {
        this.distribuidora = distribuidora;
    }

    @Override
    public String toString() {
        return "Fornecedor{" +
                "nome='" + nome + '\'' +
                ", distribuidora='" + distribuidora + '\'' +
                '}';
    }
}
