package org.example.BackEnd;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SocioTest {

    private Socio socio;
    private Cota cota;
    private Configuracoes configuracoes;

    @BeforeEach
    public void setUp() {
        configuracoes = new Configuracoes();
        cota = new Cota(100.0, true);
        socio = new Socio("Nome Teste", "123456789", "Endereço Teste", "987654321", "email@teste.com", TipoDeSocio.Academico, cota);
    }

    @Test
    public void testSocioInicializacao() {
        assertEquals("Nome Teste", socio.getNome());
        assertEquals("123456789", socio.getNif());
        assertEquals("Endereço Teste", socio.getEndereco());
        assertEquals("987654321", socio.getTelefone());
        assertEquals("email@teste.com", socio.getEmail());
        assertEquals(TipoDeSocio.Academico, socio.getTipoDeSocio());
        assertEquals(cota, socio.getCota());
    }

    @Test
    public void testAlterarNome() {
        socio.setNome("Novo Nome");
        assertEquals("Novo Nome", socio.getNome());
    }

    @Test
    public void testAlterarEndereco() {
        socio.setEndereco("Novo Endereço");
        assertEquals("Novo Endereço", socio.getEndereco());
    }

    @Test
    public void testAlterarTelefone() {
        socio.setTelefone("123123123");
        assertEquals("123123123", socio.getTelefone());
    }

    @Test
    public void testAlterarEmail() {
        socio.setEmail("novoemail@teste.com");
        assertEquals("novoemail@teste.com", socio.getEmail());
    }

    @Test
    public void testAlterarTipoDeSocio() {
        socio.setTipoDeSocio(TipoDeSocio.Leitor);
        assertEquals(TipoDeSocio.Leitor, socio.getTipoDeSocio());
    }

    @Test
    public void testAlterarCota() {
        Cota novaCota = new Cota(200.0, false);
        socio.setCota(novaCota);
        assertEquals(novaCota, socio.getCota());
    }
}
