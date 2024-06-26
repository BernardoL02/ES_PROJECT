package org.example;

import org.example.BackEnd.Livro;
import org.example.BackEnd.Requisitar;
import org.example.BackEnd.Socio;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.PopUpEstatistica;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class PaginaEstatistica extends BasePage {

    private JTable table;
    private ArrayList<Socio> socios;
    private ArrayList<Requisitar> requisicoes;
    private ArrayList<Livro> topLivros;

    public PaginaEstatistica() {

        super("Informações Estatistica", "/HeaderPaginaEstatistica.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        socios = carregarSocios("socios.ser");
        requisicoes = carregarRequisicoes("requisitar.ser");
        topLivros = getTop10Livros();

        JPanel wrapperPanel = new JPanel(new BorderLayout());

        // Adiciona o headerPanel ao wrapperPanel
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel para o conteúdo principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel principal
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel para o botão "Filtrar Por"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel do botão

        RoundButton buttonFiltrar = new RoundButton("Ordenar Por:");
        buttonFiltrar.setBackground(Color.LIGHT_GRAY);
        buttonFiltrar.setForeground(Color.BLACK);
        buttonFiltrar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonPanel.add(buttonFiltrar); // Adiciona o botão ao painel de botões
        mainPanel.add(buttonPanel, BorderLayout.NORTH); // Adiciona o painel de botões acima da tabela


        String[] columnNames = {"Ranking", "Título", "Género", "Autor", "Ano"};
        Object[][] data = new Object[topLivros.size()][5]; // Array com tamanho de topLivros

        // Preenchendo o array data com os dados de topLivros
        for (int i = 0; i < topLivros.size(); i++) {
            Livro livro = topLivros.get(i);
            data[i][0] = "#" + (i + 1); // Número de posição
            data[i][1] = livro.getTitulo(); // Título do livro
            data[i][2] = livro.getGenero(); // Gênero do livro
            data[i][3] = livro.getAutor(); // Autor do livro
            data[i][4] = livro.getAno(); // Ano do livro
        }
        table = new JTable(data, columnNames) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (component instanceof JComponent) {
                    ((JComponent) component).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
                }
                if (row % 2 == 0) {
                    component.setBackground(new Color(0xCCE5FF));
                } else {
                    component.setBackground(new Color(0xB3D9FF));
                }
                return component;
            }
        };

        table.setRowHeight(60);
        table.getTableHeader().setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        table.getTableHeader().setBackground(new Color(0x99D4FF));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setFont(new Font("Inter", Font.PLAIN, 16));
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true); // Permite redimensionar colunas

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JLabel numeroSocios = new JLabel(new ImageIcon(getClass().getResource("/SocioRequisicoes.png")));
        numeroSocios.setBackground(Color.WHITE);
        JLabel countLabel = new JLabel(String.valueOf(socios.size()));
        countLabel.setFont(new Font("Inter", Font.BOLD, 38));
        countLabel.setForeground(Color.BLACK);
        countLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Move 30px para a direita

        JLabel countLabelRequisicoes = new JLabel(String.valueOf(requisicoes.size()));
        countLabelRequisicoes.setFont(new Font("Inter", Font.BOLD, 38));
        countLabelRequisicoes.setForeground(Color.BLACK);
        countLabelRequisicoes.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Move 30px para a direita

        // Painel para posicionar a imagem e a contagem no canto inferior esquerdo
        JPanel bottomLeftPanel = new JPanel(null); // Usar layout nulo para posicionamento absoluto
        bottomLeftPanel.setPreferredSize(new Dimension(100, 100));
        bottomLeftPanel.setBackground(Color.WHITE);
        numeroSocios.setBounds(300, 0, 800, 100); // Definir posição e tamanho da imagem
        countLabel.setBounds(570, 55, 100, 40); // Definir posição e tamanho do número, ajustar conforme necessário
        countLabelRequisicoes.setBounds(750, 55, 100, 40);

        bottomLeftPanel.add(numeroSocios);
        bottomLeftPanel.add(countLabel);
        bottomLeftPanel.add(countLabelRequisicoes);

        // Adiciona o scrollPane com a tabela ao painel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomLeftPanel, BorderLayout.SOUTH);
        // Adiciona o painel principal ao wrapperPanel
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);

        // Ação ao clicar no botão "Filtrar Por"
        buttonFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = PopUpEstatistica.showCustomConfirmDialog();

                System.out.println("Response from filter dialog: " + response);

                // Obtém uma cópia dos top 10 livros
                ArrayList<Livro> topLivros = new ArrayList<>(getTop10Livros());

                // Verifica a resposta
                if (response == 0) {
                    // Ordenar por Ano Descendente
                    topLivros.sort(Comparator.comparingInt(Livro::getAno).reversed());
                    System.out.println("Sorted by year (descending):");
                } else if (response == 1) {
                    // Ordenar por Ranking
                    Map<Livro, Long> livroRequisicoes = requisicoes.stream()
                            .collect(Collectors.groupingBy(Requisitar::getLivro, Collectors.counting()));

                    topLivros.sort((livro1, livro2) ->
                            livroRequisicoes.getOrDefault(livro2, 0L).compareTo(livroRequisicoes.getOrDefault(livro1, 0L)));

                    System.out.println("Sorted by ranking:");
                }

                // Exibe os livros ordenados no console (opcional)
                for (Livro livro : topLivros) {
                    System.out.println(livro.getTitulo() + " - Ano: " + livro.getAno());
                }

                // Atualiza a tabela com os livros ordenados
                updateTable(topLivros, columnNames);
            }
        });

    }

    private ArrayList<Socio> carregarSocios(String nomeArquivo) {
        ArrayList<Socio> socios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            socios = (ArrayList<Socio>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return socios;
    }

    private ArrayList<Requisitar> carregarRequisicoes(String nomeArquivo) {
        ArrayList<Requisitar> requisicoes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            requisicoes = (ArrayList<Requisitar>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return requisicoes;
    }

    public ArrayList<Livro> getTop10Livros() {
        // HashMap para contar as requisições de cada livro usando o ISBN
        Map<String, Integer> livroRequisicoes = new HashMap<>();

        // Verifica se requisicoes não é nulo e está preenchido
        if (requisicoes != null) {
            for (Requisitar requisitar : requisicoes) {
                Livro livro = requisitar.getLivro();
                String isbnLivro = livro.getIsbn(); // Supondo que existe um método getISBN() na classe Livro

                livroRequisicoes.put(isbnLivro, livroRequisicoes.getOrDefault(isbnLivro, 0) + 1);
            }

            // Ordena os livros pelo número de requisições em ordem decrescente
            List<String> topLivrosIsbn = livroRequisicoes.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(10)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // Lista de livros correspondentes aos ISBNs mais requisitados
            ArrayList<Livro> topLivros = new ArrayList<>();
            for (String isbn : topLivrosIsbn) {
                // Encontrar o livro correspondente ao ISBN
                Livro livroEncontrado = encontrarLivroPorISBN(isbn); // Método fictício para encontrar o livro
                if (livroEncontrado != null) {
                    topLivros.add(livroEncontrado);
                }
            }

            return topLivros;
        } else {
            return new ArrayList<>(); // Retorna uma lista vazia se requisicoes for nulo
        }
    }

    // Método fictício para encontrar o livro pelo ISBN
    private Livro encontrarLivroPorISBN(String isbnLivro) {
        for (Requisitar requisitar : requisicoes) {
            Livro livro = requisitar.getLivro();
            if (livro.getIsbn().equals(isbnLivro)) {
                return livro;
            }
        }
        return null; // Retorna null se o livro não for encontrado
    }

    // Método para atualizar a tabela com os novos dados
    private void updateTable(ArrayList<Livro> livros, String[] columnNames) {

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            model.addRow(new Object[]{"#" + (i + 1), livro.getTitulo(), livro.getGenero(), livro.getAutor(), livro.getAno()});
        }

        table.setModel(model);
        table.setRowHeight(60);
        table.setFont(new Font("Inter", Font.PLAIN, 16));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
        columnModel.getColumn(0).setMaxWidth(180);

    }
}

