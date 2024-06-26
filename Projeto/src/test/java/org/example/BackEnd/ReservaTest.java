package org.example.BackEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ReservaTest {

    private Reserva reserva;
    private Socio socio;
    private Livro livro;
    private LocalDate dataReserva;

    @BeforeEach
    public void setUp() {
        Cota cota = new Cota(30.0, true);
        socio = new Socio("Socio Teste", "123456789", "Endereço Teste", "912345678", "socio@example.com", TipoDeSocio.Academico, cota);
        livro = new Livro("Livro Teste", "ISBN123", "1ª Edição", "Gênero Teste", "SubGênero Teste", "Localização Teste",
                "Autor Teste", "Editora Teste", 2021, 5, new Fornecedor("Fornecedor Teste", "123456789"));
        dataReserva = LocalDate.now();

        reserva = new Reserva(socio, livro, dataReserva);
    }

    @Test
    public void testReservaInicializacao() {
        assertEquals(socio, reserva.getSocio(), "O sócio deve ser o esperado");
        assertEquals(livro, reserva.getLivro(), "O livro deve ser o esperado");
        assertEquals(dataReserva, reserva.getDataReserva(), "A data de reserva deve ser a esperada");
    }

    @Test
    public void testSetDataReserva() {
        LocalDate novaDataReserva = LocalDate.now().plusDays(1);
        reserva.setDataReserva(novaDataReserva);
        assertEquals(novaDataReserva, reserva.getDataReserva(), "A data de reserva deve ser a esperada");
    }
}
