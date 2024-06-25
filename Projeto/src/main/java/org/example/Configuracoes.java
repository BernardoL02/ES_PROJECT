package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Configuracoes extends BasePage {
    public Configuracoes() {
        super("Configuracoes", "/HeaderConfiguracoes.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação personalizada ao clicar no botão de voltar
                // Exemplo: Voltar para a página inicial
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false); // Passe "true" para exibir o ícone de configuração, "false" para ocultá-lo

        // Painel principal para centralizar verticalmente
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel para imagem de fundo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/DadosConfiguracoes.png"));
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

        // Adicionando caixas de texto ao painel de fundo
        JTextField diasLeitor = new JTextField();
        diasLeitor.setBounds(250, 155, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(diasLeitor);

        JTextField diasAcademico = new JTextField();
        diasAcademico.setBounds(250, 195, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(diasAcademico);

        JTextField diasEntusiasta = new JTextField();
        diasEntusiasta.setBounds(250, 235, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        backgroundPanel.add(diasEntusiasta);






        wrapperPanel.add(backgroundPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Configuracoes::new);
    }
}
