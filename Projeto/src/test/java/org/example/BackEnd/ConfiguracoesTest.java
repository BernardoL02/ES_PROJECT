package org.example.BackEnd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class ConfiguracoesTest {

    private Configuracoes configuracoes;

    @BeforeEach
    public void setUp() throws IOException {
        // Remover o arquivo de configuração antes de cada teste para garantir que o teste comece do zero
        File configFile = new File("config.properties");
        if (configFile.exists()) {
            Files.delete(configFile.toPath());
        }
        configuracoes = new Configuracoes();
    }

    @Test
    public void testConfiguracoesInicializacao() {
        assertEquals("90", configuracoes.getProperty("diasLeitor"));
        assertEquals("30", configuracoes.getProperty("diasAcademico"));
        assertEquals("15", configuracoes.getProperty("diasEntusiasta"));
        assertEquals("100.0", configuracoes.getProperty("cotasLeitor"));
        assertEquals("30.0", configuracoes.getProperty("cotasAcademico"));
        assertEquals("12.5", configuracoes.getProperty("cotasEntusiasta"));
        assertEquals("5", configuracoes.getProperty("maxLivros"));
        assertEquals("20.0", configuracoes.getProperty("danificacaoLivro"));
        assertEquals("0.2", configuracoes.getProperty("multa"));
    }

    @Test
    public void testAlterarPropriedade() {
        configuracoes.setProperty("diasLeitor", "60");
        assertEquals("60", configuracoes.getProperty("diasLeitor"));
    }

    @Test
    public void testGetValorCota() {
        assertEquals(100.0, configuracoes.getValorCota(TipoDeSocio.Leitor));
        assertEquals(30.0, configuracoes.getValorCota(TipoDeSocio.Academico));
        assertEquals(12.5, configuracoes.getValorCota(TipoDeSocio.Entusiasta));
    }

    @Test
    public void testGetDiasEmprestimo() {
        assertEquals(90, configuracoes.getDiasEmprestimo(TipoDeSocio.Leitor));
        assertEquals(30, configuracoes.getDiasEmprestimo(TipoDeSocio.Academico));
        assertEquals(15, configuracoes.getDiasEmprestimo(TipoDeSocio.Entusiasta));
    }

    @Test
    public void testSalvarPropriedades() {
        configuracoes.setProperty("diasLeitor", "60");
        configuracoes.saveProperties();

        // Criar uma nova instância para verificar se as propriedades foram salvas corretamente
        Configuracoes novaConfiguracoes = new Configuracoes();
        assertEquals("60", novaConfiguracoes.getProperty("diasLeitor"));
    }

    @Test
    public void testPropriedadeInvalida() {
        configuracoes.setProperty("cotasLeitor", "valorInvalido");
        assertEquals(0.0, configuracoes.getValorCota(TipoDeSocio.Leitor)); // Espera-se 0.0 devido a valor inválido
    }
}
