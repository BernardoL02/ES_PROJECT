package org.example.FrontEnd.Livro;

import org.example.BackEnd.*;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ConfirmaReservaTest {

    private Socio socio;
    private Livro livro;
    private ConfirmaReserva confirmaReserva;

    @BeforeEach
    public void setUp() {
        Cota cota = new Cota(30.0, true);
        socio = new Socio("Socio Teste", "123456789", "Endereço Teste", "912345678", "socio@example.com", TipoDeSocio.Academico, cota);
        livro = new Livro("Livro Teste", "ISBN123", "1ª Edição", "Gênero Teste", "SubGênero Teste", "Localização Teste",
                "Autor Teste", "Editora Teste", 2021, 5, new Fornecedor("Fornecedor Teste", "123456789"));

        // Certifica-se de que a lista de reservas está vazia antes de cada teste
        ArrayList<Reserva> reservas = new ArrayList<>();
        GerirRequisitar.salvarReservas(reservas);

        confirmaReserva = new ConfirmaReserva(socio, livro);
    }

    @Test
    public void testInicializacao() {
        assertNotNull(confirmaReserva, "ConfirmaReserva não deve ser nulo");
    }

    @Test
    public void testCancelarButtonAction() {
        JButton buttonCancelar = (JButton) findButtonByText(confirmaReserva, "Cancelar");
        assertNotNull(buttonCancelar, "Botão 'Cancelar' não deve ser nulo");

        // Simula resposta do diálogo personalizado como "Sim"
        CustomPopUP.setUserResponse(JOptionPane.YES_OPTION);

        ActionEvent actionEvent = new ActionEvent(buttonCancelar, ActionEvent.ACTION_PERFORMED, null);
        buttonCancelar.getActionListeners()[0].actionPerformed(actionEvent);

        // Verifica se a janela foi fechada e uma nova instância de RequisicoesPorSocio foi criada
        assertFalse(confirmaReserva.isDisplayable(), "A janela ConfirmaReserva deve ser fechada");

        // Limpa a resposta simulada
        CustomPopUP.clearUserResponse();
    }

    @Test
    public void testGuardarButtonAction() {
        JButton buttonGuardar = (JButton) findButtonByText(confirmaReserva, "Guardar");
        assertNotNull(buttonGuardar, "Botão 'Guardar' não deve ser nulo");

        // Simula resposta do diálogo personalizado como "Sim"
        CustomPopUP.setUserResponse(JOptionPane.YES_OPTION);

        ActionEvent actionEvent = new ActionEvent(buttonGuardar, ActionEvent.ACTION_PERFORMED, null);
        buttonGuardar.getActionListeners()[0].actionPerformed(actionEvent);

        // Verifica se a reserva foi adicionada corretamente
        ArrayList<Reserva> reservas = GerirRequisitar.carregarReservas();
        assertEquals(1, reservas.size(), "Deve haver uma reserva na lista");
        Reserva reserva = reservas.get(0);
        assertEquals(socio.getNif(), reserva.getSocio().getNif(), "O NIF do sócio deve ser o esperado");
        assertEquals(livro.getIsbn(), reserva.getLivro().getIsbn(), "O ISBN do livro deve ser o esperado");

        // Limpa a resposta simulada
        CustomPopUP.clearUserResponse();
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
