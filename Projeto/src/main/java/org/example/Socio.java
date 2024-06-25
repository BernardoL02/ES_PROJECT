package org.example;

public class Socio {
    private String nome;
    private String nif;
    private String morada;
    private String telefone;
    private String email;
    private TipoDeSocio tipoDeSocio;
    private boolean pago;

    // Construtor
    public Socio(String nome, String nif, String morada, String telefone, String email, TipoDeSocio tipoDeSocio) {
        this.nome = nome;
        this.nif = nif;
        this.morada = morada;
        this.telefone = telefone;
        this.email = email;
        this.tipoDeSocio = tipoDeSocio;
        this.pago = true;
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

    public String getMorada() {
        return morada;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public void setMorada(String morada) {
        this.morada = morada;
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

    @Override
    public String toString() {
        return "Socio{" +
                "nome='" + nome + '\'' +
                ", nif='" + nif + '\'' +
                ", morada='" + morada + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", tipoDeSocio=" + tipoDeSocio +
                '}';
    }
}
