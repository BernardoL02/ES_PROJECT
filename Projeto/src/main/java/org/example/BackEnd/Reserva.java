package org.example.BackEnd;

import java.io.Serializable;
import java.time.LocalDate;

public class Reserva implements Serializable {
    private static final long serialVersionUID = 1L;

    private Socio socio;
    private Livro livro;
    private LocalDate dataReserva;

    public Reserva(Socio socio, Livro livro, LocalDate dataReserva) {
        this.socio = socio;
        this.livro = livro;
        this.dataReserva = dataReserva;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "socio=" + socio +
                ", livro=" + livro +
                ", dataReserva=" + dataReserva +
                '}';
    }
}
