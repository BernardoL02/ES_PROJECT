package org.example.BackEnd;

import java.io.Serializable;

public class Cota implements Serializable {
    private static final long serialVersionUID = 1L;

    private double valor;
    private boolean pago;

    public Cota(double valor, boolean pago) {
        this.valor = valor;
        this.pago = pago;
    }

    // Getters e Setters
    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    @Override
    public String toString() {
        return "Cota{" +
                "valor=" + valor +
                ", pago=" + pago +
                '}';
    }
}
