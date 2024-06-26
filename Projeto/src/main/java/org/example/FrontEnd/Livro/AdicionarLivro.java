package org.example.FrontEnd.Livro;

import org.example.BackEnd.Fornecedor;
import org.example.BackEnd.Livro;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

public class AdicionarLivro extends BasePage {
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
    private JTextField fieldEditoraFornecedor;
    private JTextField fieldDistribuidora;

    public AdicionarLivro() {
        super("Adicionar Livros", "/HeaderAdicionarLivro.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirLivros();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, true);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/DadosAdicionarLivro.png"));
                Image image = backgroundImage.getImage();
                int margin_W = 20;
                int margin_H = 80;
                int imgWidth = getWidth() - 2 * margin_W;
                int imgHeight = getHeight() - margin_H;

                int startY = getHeight() - imgHeight - margin_H;

                g.drawImage(image, margin_W, startY, imgWidth, imgHeight, this);
            }
        };
        backgroundPanel.setBackground(Color.WHITE);
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(null);

        // Criação do NumberFormatter para números inteiros
        NumberFormatter formatter = new NumberFormatter(new DecimalFormat("#")) {
            private final int MAX_VALUE = Integer.MAX_VALUE; // Máximo valor para Long

            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text == null || text.trim().isEmpty()) {
                    return null;
                }

                try {
                    int parsedValue = Integer.parseInt(text);
                    if (parsedValue > MAX_VALUE) {
                        return null;
                    }
                } catch (NumberFormatException e) {
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

        formatter.setValueClass(Long.class);
        formatter.setAllowsInvalid(true);
        formatter.setCommitsOnValidEdit(true);

        fieldTitulo = new JTextField();
        fieldTitulo.setBounds(55, 90, 200, 30);
        fieldTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldTitulo);

        fieldISBN = new JFormattedTextField(formatter);
        fieldISBN.setBounds(55, 165, 200, 30);
        fieldISBN.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldISBN);

        fieldEdicao = new JTextField();
        fieldEdicao.setBounds(55, 240, 200, 30);
        fieldEdicao.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEdicao);

        fieldGenero = new JTextField();
        fieldGenero.setBounds(55, 315, 200, 30);
        fieldGenero.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldGenero);

        fieldSubGenero = new JTextField();
        fieldSubGenero.setBounds(55, 390, 200, 30);
        fieldSubGenero.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldSubGenero);

        fieldLocalizacao = new JTextField();
        fieldLocalizacao.setBounds(310, 90, 200, 30);
        fieldLocalizacao.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldLocalizacao);

        fieldAutor = new JTextField();
        fieldAutor.setBounds(310, 165, 200, 30);
        fieldAutor.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldAutor);

        fieldEditora = new JTextField();
        fieldEditora.setBounds(310, 240, 200, 30);
        fieldEditora.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEditora);

        fieldAno = new JFormattedTextField(formatter);
        fieldAno.setBounds(310, 315, 200, 30);
        fieldAno.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldAno);

        fieldQuantidade = new JFormattedTextField(formatter);
        fieldQuantidade.setBounds(310, 390, 200, 30);
        fieldQuantidade.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldQuantidade);

        fieldEditoraFornecedor = new JTextField();
        fieldEditoraFornecedor.setBounds(645, 90, 200, 30);
        fieldEditoraFornecedor.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEditoraFornecedor);

        fieldDistribuidora = new JTextField();
        fieldDistribuidora.setBounds(645, 165, 200, 30);
        fieldDistribuidora.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldDistribuidora);

        wrapperPanel.add(backgroundPanel, BorderLayout.CENTER);

        RoundButton buttonGuardar = new RoundButton("Guardar");
        buttonGuardar.setBackground(new Color(0x99D4FF));
        buttonGuardar.setForeground(Color.BLACK);
        buttonGuardar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonGuardar.setBounds(750, 560, 160, 40);
        add(buttonGuardar);

        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende guardar os dados do livro?", "Confirmação", "Cancelar", "Confirmar");

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
                            fieldQuantidade.getText().trim().isEmpty() ||
                            fieldEditoraFornecedor.getText().trim().isEmpty() ||
                            fieldDistribuidora.getText().trim().isEmpty()
                    )
                    {
                        // Exibe uma mensagem de erro se alguma caixa de texto estiver vazia
                        JOptionPane.showMessageDialog(null, "Campos obrigatorios por preencher.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        try {
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

                            Livro livro = new Livro(
                                    fieldTitulo.getText(),
                                    fieldISBN.getText(),
                                    fieldEdicao.getText(),
                                    fieldGenero.getText(),
                                    fieldSubGenero.getText(),
                                    fieldLocalizacao.getText(),
                                    fieldAutor.getText(),
                                    fieldEditora.getText(),
                                    Integer.parseInt(fieldAno.getText()),
                                    Integer.parseInt(fieldQuantidade.getText()),
                                    new Fornecedor(fieldEditoraFornecedor.getText(), fieldDistribuidora.getText())
                            );

                            GerirLivros.adicionarLivro(livro);
                            new GerirLivros();
                            dispose();
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Dados Inválidos. Por favor, insira valores válidos para o ISBN, Ano e Quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        RoundButton buttonCancelar = new RoundButton("Cancelar");
        buttonCancelar.setBackground(new Color(0xBABABA));
        buttonCancelar.setForeground(Color.BLACK);
        buttonCancelar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonCancelar.setBounds(560, 560, 160, 40);
        add(buttonCancelar);

        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirLivros();
                dispose();
            }
        });

        add(wrapperPanel, BorderLayout.CENTER);
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
