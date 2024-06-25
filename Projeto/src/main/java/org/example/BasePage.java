package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BasePage extends JFrame {
    protected JPanel headerPanel;
    protected JButton configButton;
    protected JButton backButton;

    public BasePage(String title, String foto, ActionListener backAction, boolean showConfigIcon) {
        // Configurações do JFrame
        setTitle(title);
        setSize(950, 650);
        setMinimumSize(new Dimension(950, 650)); // Define a resolução mínima
        setMaximumSize(new Dimension(950, 650)); // Define a resolução máxima
        setResizable(false); // Desativa o redimensionamento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Barra branca no topo
        JPanel topBar = new JPanel();
        topBar.setPreferredSize(new Dimension(getWidth(), 15));
        topBar.setBackground(Color.WHITE);
        add(topBar, BorderLayout.NORTH);

        // Barra de cabeçalho com imagem
        headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon headerImage = new ImageIcon(getClass().getResource(foto));
                Image image = headerImage.getImage();
                int imgWidth = getWidth();
                int imgHeight = (int) ((double) headerImage.getIconHeight() / headerImage.getIconWidth() * imgWidth);

                if (imgHeight > getHeight()) {
                    imgHeight = getHeight();
                    imgWidth = (int) ((double) headerImage.getIconWidth() / headerImage.getIconHeight() * imgHeight);
                }

                int x = (getWidth() - imgWidth) / 2;
                int y = 0; // Ajusta a posição y para começar após a barra branca

                // Preencher áreas adicionais com a cor de preenchimento
                g.setColor(new Color(0x6EC2FF)); // Cor azul clara
                g.fillRect(0, y, getWidth(), getHeight() - y);

                g.drawImage(image, x, y, imgWidth, imgHeight, this);

                // Preencher a parte inferior com a cor branca
                g.setColor(Color.WHITE);
                g.fillRect(0, y + imgHeight, getWidth(), getHeight() - (y + imgHeight));
            }
        };
        headerPanel.setPreferredSize(new Dimension(getWidth(), 100));
        headerPanel.setLayout(null);

        // Botão de configuração (opcional)
        if (showConfigIcon) {
            ImageIcon configIcon = new ImageIcon(getClass().getResource("/config.png"));
            Image configImage = configIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            configButton = new JButton(new ImageIcon(configImage));
            configButton.setBounds(getWidth() - 80, 10, 30, 30); // Ajustar conforme necessário
            configButton.setBorder(null);
            configButton.setContentAreaFilled(false);
            configButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Define cursor de mão
            configButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new Configuracoes();
                    dispose();
                }
            });
            headerPanel.add(configButton);
        }

        // Botão de voltar
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/back.png"));
        Image backImage = backIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        backButton = new JButton(new ImageIcon(backImage));
        backButton.setBounds(10, 10, 30, 30); // Ajustar conforme necessário
        backButton.setBorder(null);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Define cursor de mão
        backButton.addActionListener(backAction);

        headerPanel.add(backButton);

        // Adiciona o headerPanel ao frame
        add(headerPanel, BorderLayout.NORTH);

        // Atualiza a posição dos botões ao redimensionar a janela
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                if (showConfigIcon) {
                    configButton.setBounds(getWidth() - 80, 15, 50, 50);
                }
                backButton.setBounds(10, 15, 50, 50);
            }
        });
    }
}
