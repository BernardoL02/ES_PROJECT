package org.example;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarLivro extends BasePage {
    public EditarLivro() {
        super("Gerir Livros", "/HeaderEditarLivro.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação personalizada ao clicar no botão de voltar
                // Exemplo: Voltar para a página inicial
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, true); // Passe "true" para exibir o ícone de configuração, "false" para ocultá-lo




        // Painel principal para centralizar verticalmente
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel para imagem de fundo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/DadosLivro.png"));
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

        // Adicionando caixas de texto ao painel de fundo
        JTextField fieldTitulo = new JTextField();
        fieldTitulo.setBounds(160, 90, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldTitulo);

        JTextField fieldISBN = new JTextField();
        fieldISBN.setBounds(160, 165, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldISBN.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldISBN);

        JTextField fieldEdicao = new JTextField();
        fieldEdicao.setBounds(160, 240, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldEdicao.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEdicao);

        JTextField fieldGenero = new JTextField();
        fieldGenero.setBounds(160, 315, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldGenero.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldGenero);

        JTextField fieldSubGenero = new JTextField();
        fieldSubGenero.setBounds(160, 390, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldSubGenero.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldSubGenero);

        //Coluna 2
        JTextField fieldLocalizacao = new JTextField();
        fieldLocalizacao.setBounds(510, 90, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldLocalizacao.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldLocalizacao);

        JTextField fieldAutor = new JTextField();
        fieldAutor.setBounds(510, 165, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldAutor.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldAutor);

        JTextField fieldEditora = new JTextField();
        fieldEditora.setBounds(510, 240, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldEditora.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEditora);

        JTextField fieldAno = new JTextField();
        fieldAno.setBounds(510, 315, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldAno.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldAno);

        JTextField fieldQuantidade = new JTextField();
        fieldQuantidade.setBounds(510, 390, 250, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldQuantidade);

        wrapperPanel.add(backgroundPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditarLivro::new);
    }
}