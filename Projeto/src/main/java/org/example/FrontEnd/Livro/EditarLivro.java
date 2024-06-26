package org.example.FrontEnd.Livro;

import org.example.BackEnd.Livro;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarLivro extends BasePage {
    private Livro livro;
    private JTextField fieldTitulo;
    private JTextField fieldISBN;
    private JTextField fieldEdicao;
    private JTextField fieldGenero;
    private JTextField fieldSubGenero;
    private JTextField fieldLocalizacao;
    private JTextField fieldAutor;
    private JTextField fieldEditora;
    private JTextField fieldAno;
    private JTextField fieldQuantidade;

    public EditarLivro(Livro livro) {
        super("Editar Livro", "/HeaderEditarLivro.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirLivros();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, true);

        this.livro = livro;

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
        fieldTitulo = new JTextField(livro.getTitulo());
        fieldTitulo.setBounds(160, 90, 250, 30);
        fieldTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldTitulo);

        fieldISBN = new JTextField(livro.getIsbn());
        fieldISBN.setBounds(160, 165, 250, 30);
        fieldISBN.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldISBN);

        fieldEdicao = new JTextField(livro.getEdicao());
        fieldEdicao.setBounds(160, 240, 250, 30);
        fieldEdicao.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEdicao);

        fieldGenero = new JTextField(livro.getGenero());
        fieldGenero.setBounds(160, 315, 250, 30);
        fieldGenero.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldGenero);

        fieldSubGenero = new JTextField(livro.getSubGenero());
        fieldSubGenero.setBounds(160, 390, 250, 30);
        fieldSubGenero.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldSubGenero);

        // Coluna 2
        fieldLocalizacao = new JTextField(livro.getLocalizacao());
        fieldLocalizacao.setBounds(510, 90, 250, 30);
        fieldLocalizacao.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldLocalizacao);

        fieldAutor = new JTextField(livro.getAutor());
        fieldAutor.setBounds(510, 165, 250, 30);
        fieldAutor.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldAutor);

        fieldEditora = new JTextField(livro.getEditora());
        fieldEditora.setBounds(510, 240, 250, 30);
        fieldEditora.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEditora);

        fieldAno = new JTextField(String.valueOf(livro.getAno()));
        fieldAno.setBounds(510, 315, 250, 30);
        fieldAno.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldAno);

        fieldQuantidade = new JTextField(String.valueOf(livro.getQuantidade()));
        fieldQuantidade.setBounds(510, 390, 250, 30);
        fieldQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldQuantidade);

        wrapperPanel.add(backgroundPanel, BorderLayout.CENTER);

        // Botões
        RoundButton buttonGuardar = new RoundButton("Guardar");
        buttonGuardar.setBackground(new Color(0x99D4FF));
        buttonGuardar.setForeground(Color.BLACK);
        buttonGuardar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonGuardar.setBounds(690, 560, 160, 40);
        add(buttonGuardar);

        // Adiciona o ActionListener ao botão Guardar
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exibe o diálogo de confirmação personalizado
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende guardar os dados do livro?", "Confirmação", "Cancelar", "Confirmar");

                // Verifica a resposta
                if (response == JOptionPane.YES_OPTION) {
                    // Atualiza os dados do livro
                    livro.setTitulo(fieldTitulo.getText());
                    livro.setIsbn(fieldISBN.getText());
                    livro.setEdicao(fieldEdicao.getText());
                    livro.setGenero(fieldGenero.getText());
                    livro.setSubGenero(fieldSubGenero.getText());
                    livro.setLocalizacao(fieldLocalizacao.getText());
                    livro.setAutor(fieldAutor.getText());
                    livro.setEditora(fieldEditora.getText());
                    livro.setAno(Integer.parseInt(fieldAno.getText()));
                    livro.setQuantidade(Integer.parseInt(fieldQuantidade.getText()));

                    // Salvar os livros atualizados no arquivo
                    GerirLivros.salvarLivros("livros.ser");

                    new GerirLivros();
                    dispose();
                }
            }
        });

        RoundButton buttonCancelar = new RoundButton("Cancelar");
        buttonCancelar.setBackground(new Color(0xBABABA));
        buttonCancelar.setForeground(Color.BLACK);
        buttonCancelar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonCancelar.setBounds(490, 560, 160, 40);
        add(buttonCancelar);

        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirLivros();
                dispose(); // Fecha a janela principal
            }
        });

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EditarLivro(null));
    }
}
