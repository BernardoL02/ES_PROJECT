package org.example.FrontEnd;

import org.example.BackEnd.Socio;
import org.example.FrontEnd.Livro.AdicionarLivro;
import org.example.FrontEnd.Livro.GerirLivros;
import org.example.FrontEnd.Socio.GerirSocio;
import org.example.FrontEnd.Socio.ValidarCotas;
import org.example.PaginaEstatistica;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BiblioLiz extends JFrame {

    private ArrayList<Socio> socios;

    public BiblioLiz() {
        // Inicializa a lista de sócios
        socios = new ArrayList<>();

        // Configurações do JFrame
        setTitle("BiblioLiz");
        setSize(800, 650);
        setMinimumSize(new Dimension(800, 650)); // Define a resolução mínima
        setMaximumSize(new Dimension(800, 650)); // Define a resolução máxima
        setResizable(false); // Desativa o redimensionamento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel para imagem de fundo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/Menu.png"));
                Image image = backgroundImage.getImage();
                int margin_W = 80;
                int margin_H = 20;
                int imgWidth = getWidth() - 2 * margin_W;
                int imgHeight = getHeight() - 2 * margin_H;
                g.drawImage(image, margin_W, margin_H, imgWidth, imgHeight, this);
            }
        };
        backgroundPanel.setLayout(null);  // Layout absoluto para posicionar os botões manualmente
        backgroundPanel.setBackground(Color.WHITE); // Define o fundo branco para o painel
        backgroundPanel.setOpaque(true);

        Font buttonFont = new Font("Inter", Font.BOLD | Font.ITALIC, 32);

        // Botões
        String[] buttonLabels = {"Gerir Livros", "Gerir Sócios", "Registar Livro", "Validar Cotas", "Ver Estatistica"};
        int buttonWidth = 325;
        int buttonHeight = 52;
        int imgMargin_W = 90;
        int imgMargin_H = 20;
        int imgWidth = 700; // Largura da imagem, assumida como constante
        int yPosition = imgMargin_H + 135; // Ajuste inicial da posição vertical do primeiro botão
        int xPosition = imgMargin_W + (imgWidth - buttonWidth) / 2; // Centraliza os botões horizontalmente

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(buttonFont);
            button.setBounds(xPosition, yPosition, buttonWidth, buttonHeight); // Posição e tamanho dos botões

            // Ajustes de cor e borda
            button.setBackground(new Color(0x99D4FF));
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Define cursor de mão

            // Adicionar ActionListener ao botão "Gerir Livros"
            if (label.equals("Gerir Livros")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new GerirLivros();
                        dispose(); // Fecha a janela principal
                    }
                });
            }
            if (label.equals("Gerir Sócios")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new GerirSocio(getSocios());
                        dispose(); // Fecha a janela principal
                    }
                });
            }
            if (label.equals("Registar Livro")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new AdicionarLivro();
                        dispose(); // Fecha a janela principal
                    }
                });
            }

            if (label.equals("Validar Cotas")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new ValidarCotas();
                        dispose(); // Fecha a janela principal
                    }
                });
            }

            if (label.equals("Ver Estatistica")) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new PaginaEstatistica();
                        dispose(); // Fecha a janela principal
                    }
                });
            }


            backgroundPanel.add(button);
            yPosition += buttonHeight + 36; // Espaçamento vertical entre os botões
        }

        // Adicionar botão de imagem para configurações
        ImageIcon configIcon = new ImageIcon(getClass().getResource("/config.png"));
        Image configImage = configIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        JButton configButton = new JButton(new ImageIcon(configImage));
        configButton.setBounds(625, 50, 50, 50); // Ajustar conforme necessário
        configButton.setBorder(null);
        configButton.setContentAreaFilled(false);
        configButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Define cursor de mão
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Configs();
                dispose();
            }
        });

        backgroundPanel.add(configButton);

        // Adicionar painel de fundo ao frame
        add(backgroundPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }

    // Método para adicionar sócios à lista
    public void addSocio(Socio socio) {
        socios.add(socio);
    }

    // Método para obter a lista de sócios
    public ArrayList<Socio> getSocios() {
        return socios;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BiblioLiz::new);
    }
}
