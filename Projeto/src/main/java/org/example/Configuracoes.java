package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.NumberFormatter;

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
                int margin_W = 100;
                int margin_H = 80; // Aumenta a margem superior
                int imgWidth = getWidth() - 2 * margin_W;
                int imgHeight = getHeight() - margin_H;

                // Ajusta a posição vertical da imagem para movê-la para baixo
                int startY = getHeight() - imgHeight - margin_H; // Posiciona a partir da margem inferior

                g.drawImage(image, margin_W, startY, imgWidth, imgHeight, this);
            }
        };
        backgroundPanel.setBackground(Color.WHITE); // Define o fundo branco para o painel
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(null); // Definindo layout absoluto para posicionamento personalizado

        // Configurando o JTextField para aceitar apenas números inteiros
        NumberFormatter formatter = new NumberFormatter();
        formatter.setValueClass(Integer.class); // Define o tipo de valor como Integer
        formatter.setCommitsOnValidEdit(true); // Submete o valor quando válido
        formatter.setMaximum(999); // Valor maximo permitido (zero)

        // Adicionando caixas de texto ao painel de fundo
        JTextField diasLeitor = new JFormattedTextField(formatter);
        diasLeitor.setBounds(260, 123, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        diasLeitor.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(diasLeitor);

        JTextField diasAcademico = new JFormattedTextField(formatter);
        diasAcademico.setBounds(260, 160, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        diasAcademico.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(diasAcademico);

        JTextField diasEntusiasta = new JFormattedTextField(formatter);
        diasEntusiasta.setBounds(260, 198, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        diasEntusiasta.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(diasEntusiasta);

        //Cotas
        JTextField cotasLeitor = new JFormattedTextField(formatter);
        cotasLeitor.setBounds(455, 123, 90, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        cotasLeitor.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(cotasLeitor);

        JTextField cotasAcademico = new JFormattedTextField(formatter);
        cotasAcademico.setBounds(455, 160, 90, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        cotasAcademico.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(cotasAcademico);

        JTextField cotasEntusiasta = new JFormattedTextField(formatter);
        cotasEntusiasta.setBounds(455, 198, 90, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        cotasEntusiasta.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(cotasEntusiasta);

        //Outros
        JTextField maxLivros = new JFormattedTextField(formatter);
        maxLivros.setBounds(145, 290, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        maxLivros.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(maxLivros);

        JTextField danificacaoLivro = new JFormattedTextField(formatter);
        danificacaoLivro.setBounds(370, 290, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        danificacaoLivro.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(danificacaoLivro);

        JTextField multa = new JFormattedTextField(formatter);
        multa.setBounds(145, 375, 60, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        multa.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(multa);

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
