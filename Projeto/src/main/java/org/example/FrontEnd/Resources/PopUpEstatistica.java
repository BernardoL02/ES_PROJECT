package org.example.FrontEnd.Resources;

import org.example.PaginaEstatistica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PopUpEstatistica {
    public static int showCustomConfirmDialog() {
        JFrame parent = new JFrame();
        JDialog dialog = new JDialog(parent, "FiltroEstatistica", true);
        dialog.setSize(500, 150);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Layout vertical

        // Adicionando espaço entre os botões
        buttonPanel.add(Box.createVerticalStrut(20));

        RoundButton anoButton = new RoundButton("       Ano       ");
        anoButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza na horizontal
        anoButton.setBackground(new Color(0x99D4FF));
        anoButton.setForeground(Color.BLACK);
        anoButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        anoButton.setPreferredSize(new Dimension(200, 40)); // Define tamanho preferido

        RoundButton generoButton = new RoundButton("    Ranking    ");
        generoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generoButton.setBackground(new Color(0x99D4FF));
        generoButton.setForeground(Color.BLACK);
        generoButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        generoButton.setPreferredSize(new Dimension(200, 40)); // Define tamanho preferido

        buttonPanel.add(anoButton);
        buttonPanel.add(Box.createVerticalStrut(5)); // Espaçamento entre os botões
        buttonPanel.add(generoButton);
        buttonPanel.add(Box.createVerticalStrut(5)); // Espaçamento entre os botões

        dialog.add(buttonPanel, BorderLayout.CENTER);

        // Variável para armazenar a resposta do usuário
        final int[] response = {-1}; // -1 indica que nenhum botão foi clicado

        anoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response[0] = 0; // Valor para o botão "Ano"
                dialog.dispose(); // Fecha o popup
            }
        });

        generoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response[0] = 1; // Valor para o botão "Ranking"
                dialog.dispose(); // Fecha o popup
            }
        });

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return response[0]; // Retorna o valor do botão clicado
    }
}
