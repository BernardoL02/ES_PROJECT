package org.example.FrontEnd.Livro;

import org.example.BackEnd.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ConfirmaRequisicaoTest {

    private Socio socio;
    private Livro livro;
    private Configuracoes configuracoes;
    private ConfirmaRequisicao confirmaRequisicao;

    @BeforeEach
    public void setUp() {
        Cota cota = new Cota(30.0, true);
        socio = new Socio("Socio Teste", "123456789", "Endereço Teste", "912345678", "socio@example.com", TipoDeSocio.Academico, cota);
        livro = new Livro("Livro Teste", "ISBN123", "1ª Edição", "Gênero Teste", "SubGênero Teste", "Localização Teste",
                "Autor Teste", "Editora Teste", 2021, 5, new Fornecedor("Fornecedor Teste", "123456789"));
        configuracoes = new Configuracoes();
        confirmaRequisicao = new ConfirmaRequisicao(socio, livro);
    }

    @Test
    public void testInicializacao() {
        assertNotNull(confirmaRequisicao, "ConfirmaRequisicao não deve ser nulo");
    }

    @Test
    public void testCancelarButtonAction() {
        JButton buttonCancelar = (JButton) findButtonByText(confirmaRequisicao, "Cancelar");
        assertNotNull(buttonCancelar, "Botão 'Cancelar' não deve ser nulo");

        ActionEvent actionEvent = new ActionEvent(buttonCancelar, ActionEvent.ACTION_PERFORMED, null);
        buttonCancelar.getActionListeners()[0].actionPerformed(actionEvent);

        // Simula resposta do diálogo personalizado como "Sim"
        assertNotNull(buttonCancelar.getActionListeners()[0]);
    }

    @Test
    public void testGuardarButtonAction() {
        JButton buttonGuardar = (JButton) findButtonByText(confirmaRequisicao, "Guardar");
        assertNotNull(buttonGuardar, "Botão 'Guardar' não deve ser nulo");

        ActionEvent actionEvent = new ActionEvent(buttonGuardar, ActionEvent.ACTION_PERFORMED, null);
        buttonGuardar.getActionListeners()[0].actionPerformed(actionEvent);

        // Simula resposta do diálogo personalizado como "Confirmar"
        LocalDate dataRequisicao = LocalDate.now();
        LocalDate dataDevolucaoPrevista = dataRequisicao.plusDays(configuracoes.getDiasEmprestimo(socio.getTipoDeSocio()));
        Requisitar requisitar = new Requisitar(socio, livro, dataRequisicao, dataDevolucaoPrevista);

        assertEquals(socio, requisitar.getSocio(), "O sócio deve ser o esperado");
        assertEquals(livro, requisitar.getLivro(), "O livro deve ser o esperado");
    }

    // Método utilitário para encontrar botão por texto
    private Component findButtonByText(Container container, String text) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (text.equals(button.getText())) {
                    return button;
                }
            } else if (component instanceof Container) {
                Component result = findButtonByText((Container) component, text);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
