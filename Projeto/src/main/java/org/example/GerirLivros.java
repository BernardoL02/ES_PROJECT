package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GerirLivros extends JFrame {
    public GerirLivros() {
        // Configurações do JFrame
        setTitle("Gerir Livros");
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
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon headerImage = new ImageIcon(getClass().getResource("/HeaderGerirLivros.png"));
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

        // Botão de configuração
        ImageIcon configIcon = new ImageIcon(getClass().getResource("/config.png"));
        Image configImage = configIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton configButton = new JButton(new ImageIcon(configImage));
        configButton.setBounds(getWidth() - 80, 10, 30, 30); // Ajustar conforme necessário
        configButton.setBorder(null);
        configButton.setContentAreaFilled(false);
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Ação ao clicar no botão de configuração
                JOptionPane.showMessageDialog(null, "Configurações");
            }
        });

        headerPanel.add(configButton);

        // Botão de voltar
        ImageIcon backIcon = new ImageIcon(getClass().getResource("/back.png"));
        Image backImage = backIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton backButton = new JButton(new ImageIcon(backImage));
        backButton.setBounds(10, 10, 30, 30); // Ajustar conforme necessário
        backButton.setBorder(null);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                dispose();
            }
        });

        headerPanel.add(backButton);

        // Painel principal para centralizar verticalmente
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        // Conteúdo principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel principal

        // Adicionar componentes ao painel principal
        JLabel label = new JLabel("Conteúdo da página Gerir Livros");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 0); // Adiciona um espaçamento superior
        mainPanel.add(label, gbc);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);

        // Atualiza a posição dos botões ao redimensionar a janela
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                configButton.setBounds(getWidth() - 80, 15, 50, 50);
                backButton.setBounds(10, 15, 50, 50);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GerirLivros::new);
    }
}
