package org.example.BackEnd;

import java.io.Serializable;

public class Socio implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String nif;
    private String endereco;
    private String telefone;
    private String email;
    private TipoDeSocio tipoDeSocio;
    private Cota cota;

    public Socio(String nome, String nif, String endereco, String telefone, String email, TipoDeSocio tipoDeSocio, Cota cota) {
        this.nome = nome;
        this.nif = nif;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.tipoDeSocio = tipoDeSocio;
        this.cota = cota;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoDeSocio getTipoDeSocio() {
        return tipoDeSocio;
    }

    public void setTipoDeSocio(TipoDeSocio tipoDeSocio) {
        this.tipoDeSocio = tipoDeSocio;
    }

    public Cota getCota() {
        return cota;
    }

    public void setCota(Cota cota) {
        this.cota = cota;
    }

    @Override
    public String toString() {
        return "Socio{" +
                "nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", tipoDeSocio=" + tipoDeSocio +
                ", cota=" + cota +
                '}';
    }
}
