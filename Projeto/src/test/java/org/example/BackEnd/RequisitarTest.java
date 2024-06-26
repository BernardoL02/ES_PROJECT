package org.example.BackEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RequisitarTest {

    private Requisitar requisitar;
    private Socio socio;
    private Livro livro;
    private LocalDate dataRequisicao;
    private LocalDate dataDevolucaoPrevista;

    @BeforeEach
    public void setUp() {
        Cota cota = new Cota(30.0, true);
        socio = new Socio("Socio Teste", "123456789", "Endereço Teste", "912345678", "socio@example.com", TipoDeSocio.Academico, cota);
        livro = new Livro("Livro Teste", "ISBN123", "1ª Edição", "Gênero Teste", "SubGênero Teste", "Localização Teste",
                "Autor Teste", "Editora Teste", 2021, 5, new Fornecedor("Fornecedor Teste", "123456789"));
        dataRequisicao = LocalDate.now();
        dataDevolucaoPrevista = dataRequisicao.plusDays(30);

        requisitar = new Requisitar(socio, livro, dataRequisicao, dataDevolucaoPrevista);
    }

    @Test
    public void testRequisitarInicializacao() {
        assertEquals(socio, requisitar.getSocio(), "O sócio deve ser o esperado");
        assertEquals(livro, requisitar.getLivro(), "O livro deve ser o esperado");
        assertEquals(dataRequisicao, requisitar.getDataRequisicao(), "A data de requisição deve ser a esperada");
        assertEquals(dataDevolucaoPrevista, requisitar.getDataDevolucaoPrevista(), "A data de devolução prevista deve ser a esperada");
        assertNull(requisitar.getDataDevolucao(), "A data de devolução deve ser nula inicialmente");
    }

    @Test
    public void testSetDataDevolucao() {
        LocalDate dataDevolucao = LocalDate.now().plusDays(15);
        requisitar.setDataDevolucao(dataDevolucao);
        assertEquals(dataDevolucao, requisitar.getDataDevolucao(), "A data de devolução deve ser a esperada");
    }
}
