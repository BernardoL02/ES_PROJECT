package org.example.BackEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroTest {

    private Livro livro;
    private Fornecedor fornecedor;

    @BeforeEach
    public void setUp() {
        fornecedor = new Fornecedor("Fornecedor Teste", "123456789");
        livro = new Livro("Título Teste", "ISBN001", "1ª Edição", "Gênero Teste", "SubGênero Teste", "Localização Teste",
                "Autor Teste", "Editora Teste", 2021, 5, fornecedor);
    }

    @Test
    public void testLivroInicializacao() {
        assertEquals("Título Teste", livro.getTitulo());
        assertEquals("ISBN001", livro.getIsbn());
        assertEquals("1ª Edição", livro.getEdicao());
        assertEquals("Gênero Teste", livro.getGenero());
        assertEquals("SubGênero Teste", livro.getSubGenero());
        assertEquals("Localização Teste", livro.getLocalizacao());
        assertEquals("Autor Teste", livro.getAutor());
        assertEquals("Editora Teste", livro.getEditora());
        assertEquals(2021, livro.getAno());
        assertEquals(5, livro.getQuantidade());
        assertEquals(fornecedor, livro.getFornecedor());
    }

    @Test
    public void testAtualizarQuantidade() {
        livro.setQuantidade(10);
        assertEquals(10, livro.getQuantidade());
    }

    @Test
    public void testAlterarEdicao() {
        livro.setEdicao("2ª Edição");
        assertEquals("2ª Edição", livro.getEdicao());
    }

    @Test
    public void testAlterarFornecedor() {
        Fornecedor novoFornecedor = new Fornecedor("Novo Fornecedor", "987654321");
        livro.setFornecedor(novoFornecedor);
        assertEquals(novoFornecedor, livro.getFornecedor());
    }
}
