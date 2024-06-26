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
        dialog.setSize(500, 260);
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

        RoundButton autorButton = new RoundButton("     Autor      ");
        autorButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza na horizontal
        autorButton.setBackground(new Color(0x99D4FF));
        autorButton.setForeground(Color.BLACK);
        autorButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        autorButton.setPreferredSize(new Dimension(200, 40)); // Define tamanho preferido

        RoundButton generoButton = new RoundButton("    Género    ");
        generoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        generoButton.setBackground(new Color(0x99D4FF));
        generoButton.setForeground(Color.BLACK);
        generoButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        generoButton.setPreferredSize(new Dimension(200, 40)); // Define tamanho preferido

        RoundButton subGeneroButton = new RoundButton("Sub-Género");
        subGeneroButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza na horizontal
        subGeneroButton.setBackground(new Color(0x99D4FF));
        subGeneroButton.setForeground(Color.BLACK);
        subGeneroButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        subGeneroButton.setPreferredSize(new Dimension(200, 40)); // Define tamanho preferido

        buttonPanel.add(anoButton);
        buttonPanel.add(Box.createVerticalStrut(5)); // Espaçamento entre os botões
        buttonPanel.add(autorButton);
        buttonPanel.add(Box.createVerticalStrut(5)); // Espaçamento entre os botões
        buttonPanel.add(generoButton);
        buttonPanel.add(Box.createVerticalStrut(5)); // Espaçamento entre os botões
        buttonPanel.add(subGeneroButton);

        dialog.add(buttonPanel, BorderLayout.CENTER);

        ActionListener openPaginaEstatistica = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PaginaEstatistica(); // Abre a página estatística
                dialog.dispose(); // Fecha o popup
            }
        };

        anoButton.addActionListener(openPaginaEstatistica);
        autorButton.addActionListener(openPaginaEstatistica);
        generoButton.addActionListener(openPaginaEstatistica);
        subGeneroButton.addActionListener(openPaginaEstatistica);

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return 0;
    }
}