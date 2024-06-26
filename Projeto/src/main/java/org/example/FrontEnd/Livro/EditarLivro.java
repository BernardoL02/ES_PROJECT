package org.example.FrontEnd.Livro;

import org.example.BackEnd.Livro;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

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

        // Criação do NumberFormatter para números inteiros
        NumberFormatter formatter = new NumberFormatter(new DecimalFormat("#")) {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text == null || text.trim().isEmpty()) {
                    return null;
                }
                return super.stringToValue(text);
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value == null) {
                    return "";
                }
                return super.valueToString(value);
            }
        };
        formatter.setValueClass(Integer.class);
        formatter.setAllowsInvalid(true);
        formatter.setCommitsOnValidEdit(true);

        // Adicionando caixas de texto ao painel de fundo
        fieldTitulo = new JTextField(livro.getTitulo());
        fieldTitulo.setBounds(160, 90, 250, 30);
        fieldTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldTitulo);

        JFormattedTextField fieldISBN = new JFormattedTextField(formatter);
        fieldISBN.setBounds(160, 165, 250, 30);
        fieldISBN.setHorizontalAlignment(SwingConstants.LEFT);
        fieldISBN.setValue(Integer.parseInt(livro.getIsbn()));
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

        JFormattedTextField fieldAno = new JFormattedTextField(formatter);
        fieldAno.setBounds(510, 315, 250, 30);
        fieldAno.setHorizontalAlignment(SwingConstants.LEFT);
        fieldAno.setValue(livro.getAno());

        backgroundPanel.add(fieldAno);

        JFormattedTextField fieldQuantidade = new JFormattedTextField(formatter);
        fieldQuantidade.setBounds(510, 390, 250, 30);
        fieldQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
        fieldQuantidade.setValue(livro.getQuantidade());
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

                    // Verifica se alguma caixa de texto está vazia ou contém valor nulo
                    if (fieldTitulo.getText().trim().isEmpty() ||
                            fieldISBN.getText().trim().isEmpty() ||
                            fieldEdicao.getText().trim().isEmpty() ||
                            fieldGenero.getText().trim().isEmpty() ||
                            fieldSubGenero.getText().trim().isEmpty() ||
                            fieldLocalizacao.getText().trim().isEmpty() ||
                            fieldAutor.getText().trim().isEmpty() ||
                            fieldEditora.getText().trim().isEmpty() ||
                            fieldAno.getText().trim().isEmpty() ||
                            fieldQuantidade.getText().trim().isEmpty()
                    ) {
                        // Exibe uma mensagem de erro se alguma caixa de texto estiver vazia
                        JOptionPane.showMessageDialog(null, "Campos obrigatórios por preencher.", "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {

                        String newISBN = fieldISBN.getText().trim();
                        if (isbnExists(newISBN)) {
                            JOptionPane.showMessageDialog(null, "Já existe um livro com este ISBN.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        int ISBN = Integer.parseInt(fieldISBN.getText().trim());
                        int diasLeitorValue = Integer.parseInt(fieldAno.getText().trim());
                        int diasAcademicoValue = Integer.parseInt(fieldQuantidade.getText().trim());

                        if (ISBN < 0 || diasLeitorValue < 0 || diasAcademicoValue < 0) {
                            JOptionPane.showMessageDialog(null, "Dados Inválidos. Os valores não podem ser negativos.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

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

    private boolean isbnExists(String isbn) {
        ArrayList<Livro> livros = carregarLivros("livros.ser");
        for (Livro livro : livros) {
            if (livro.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Livro> carregarLivros(String nomeArquivo) {
        ArrayList<Livro> livros = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            livros = (ArrayList<Livro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return livros;
    }
}
