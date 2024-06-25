package org.example.FrontEnd.Socio;

import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.BiblioLiz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarSocio extends BasePage {

    public AdicionarSocio(){
        super("Adicionar Socio", "/HeaderRegistarSocio.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/DadosSocio.png"));
                Image image = backgroundImage.getImage();
                int margin_W = 80;
                int margin_H = 20;
                int imgWidth = getWidth() - 2 * margin_W;
                int imgHeight = getHeight() - 2 * margin_H;
                g.drawImage(image, margin_W, margin_H, imgWidth, imgHeight, this);

            }
        };
        backgroundPanel.setBackground(Color.WHITE); // Define o fundo branco para o painel
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(null); // Definindo layout absoluto para posicionamento personalizado

        JTextField Nome = new JTextField();
        Nome.setBounds(120, 130, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(Nome);

        JTextField NIF = new JTextField();
        NIF.setBounds(520, 130, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(NIF);

        JTextField Morada = new JTextField();
        Morada.setBounds(120, 250, 170, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(Morada);

        JTextField Codigo = new JTextField();
        Codigo.setBounds(300, 250, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(Codigo);

        JTextField Postal = new JTextField();
        Postal.setBounds(385, 250, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(Postal);

        JTextField Telemovel = new JTextField();
        Telemovel.setBounds(520, 250, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(Telemovel);

        JTextField Email = new JTextField();
        Email.setBounds(120, 370, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(Email);

        String[] opcoes = {"Entusiasta", "Académico", "Leitor"};
        JComboBox<String> comboBox = new JComboBox<>(opcoes);
        comboBox.setBounds(520, 370, 200, 30);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setOpaque(true);
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Verifica se o item está selecionado e ajusta as cores
                if (isSelected) {
                    setBackground(Color.WHITE); // Cor de fundo quando selecionado
                    setForeground(Color.WHITE); // Cor do texto quando selecionado
                } else {
                    setForeground(Color.BLACK); // Cor do texto quando não está selecionado
                }

                return this;
            }
        });

        backgroundPanel.add(comboBox);

        wrapperPanel.add(backgroundPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);
        setVisible(true);
    }

}