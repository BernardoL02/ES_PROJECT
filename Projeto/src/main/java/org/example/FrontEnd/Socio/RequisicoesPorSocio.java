package org.example.FrontEnd.Socio;

import org.example.BackEnd.Socio;
import org.example.BackEnd.TipoDeSocio;
import org.example.BackEnd.Requisitar;
import org.example.FrontEnd.Livro.RequisitarLivro;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RequisicoesPorSocio extends BasePage {
    private Socio socio;
    private ArrayList<Requisitar> reservas;
    private JTable memberTable;
    private JTable bookTable;

    public RequisicoesPorSocio(Socio socio) {
        super("Requisicoes Por Socio", "/HeaderRequisicoesPorSocio.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        this.socio = socio;
        this.reservas = carregarReservas("requisitar.ser");

        JPanel wrapperPanel = new JPanel(new BorderLayout(10, 10));
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);
        wrapperPanel.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Alinha o botão à esquerda
        topPanel.setBackground(Color.WHITE);

        RoundButton requisitarButton = new RoundButton("Requisitar");
        requisitarButton.setBackground(new Color(0x99D4FF));
        requisitarButton.setForeground(Color.BLACK);
        requisitarButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        requisitarButton.setPreferredSize(new Dimension(160, 40));
        requisitarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RequisitarLivro(socio);
                dispose(); // Fecha a janela principal
            }
        });

        topPanel.add(requisitarButton);
        mainPanel.add(topPanel);

        // Painel de informação do sócio
        JPanel memberInfoPanel = new JPanel(new BorderLayout());
        memberInfoPanel.setBackground(Color.WHITE);
        memberInfoPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0)); // Remover a borda preta

        JLabel memberInfoLabel = new JLabel("Sócio Associado", SwingConstants.CENTER);
        memberInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        memberInfoPanel.add(memberInfoLabel, BorderLayout.NORTH);

        String[] memberColumnNames = {"Nif", "Nome", "Morada", "Telefone", "Email", "Pago", "Tipo de Sócio"};
        Object[][] memberData = {
                {socio.getNif(), socio.getNome(), socio.getEndereco(), socio.getTelefone(), socio.getEmail(), socio.getCota().isPago() ? "Sim" : "Não", socio.getTipoDeSocio().toString()}
        };
        memberTable = createTable(memberData, memberColumnNames);
        memberTable.setRowHeight(30); // Ajusta a altura da linha da tabela de sócios
        memberTable.setPreferredSize(new Dimension(memberTable.getPreferredSize().width, memberTable.getRowHeight() + memberTable.getTableHeader().getPreferredSize().height)); // Ajuste o tamanho da tabela de sócios

        // Define o editor de célula para a coluna "Pago"
        JComboBox<String> pagoComboBox = new JComboBox<>(new String[]{"Sim", "Não"});
        memberTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(pagoComboBox));

        // Define o editor de célula para a coluna "Tipo de Sócio"
        JComboBox<String> tipoDeSocioComboBox = new JComboBox<>(new String[]{"Entusiasta", "Académico", "Leitor"});
        memberTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(tipoDeSocioComboBox));

        JScrollPane memberScrollPane = new JScrollPane(memberTable);
        memberScrollPane.setPreferredSize(new Dimension(memberTable.getPreferredSize().width, 120)); // Altura fixa para o painel de informações do sócio
        memberScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Borda com a cor desejada
        memberScrollPane.getViewport().setBackground(Color.WHITE);
        memberInfoPanel.add(memberScrollPane, BorderLayout.CENTER);

        // Adiciona o painel de informações do sócio ao mainPanel
        mainPanel.add(memberInfoPanel);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Alinhamento ao centro
        buttonPanel.setBackground(Color.WHITE);

        RoundButton buttonCancelar = new RoundButton("Cancelar");
        buttonCancelar.setBackground(new Color(0xBABABA));
        buttonCancelar.setForeground(Color.BLACK);
        buttonCancelar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonPanel.add(buttonCancelar);

        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirSocio();
                dispose(); // Fecha a janela principal
            }
        });

        RoundButton buttonGuardar = new RoundButton("Guardar");
        buttonGuardar.setBackground(new Color(0x99D4FF));
        buttonGuardar.setForeground(Color.BLACK);
        buttonGuardar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonPanel.add(buttonGuardar);
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exibe o diálogo de confirmação personalizado
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende guardar as alterações?", "Confirmação", "Cancelar", "Confirmar");

                // Verifica a resposta
                if (response == JOptionPane.YES_OPTION) {
                    // Força a parada da edição da célula ativa
                    if (memberTable.isEditing()) {
                        memberTable.getCellEditor().stopCellEditing();
                    }

                    // Coleta os dados da tabela
                    String nome = (String) memberTable.getValueAt(0, 1);
                    String endereco = (String) memberTable.getValueAt(0, 2);
                    String telefone = (String) memberTable.getValueAt(0, 3);
                    String email = (String) memberTable.getValueAt(0, 4);
                    boolean pago = "Sim".equals(memberTable.getValueAt(0, 5));
                    TipoDeSocio tipoDeSocio = TipoDeSocio.valueOf((String) memberTable.getValueAt(0, 6));

                    // Atualiza o objeto Socio
                    socio.setNome(nome);
                    socio.setEndereco(endereco);
                    socio.setTelefone(telefone);
                    socio.setEmail(email);
                    socio.getCota().setPago(pago);
                    socio.setTipoDeSocio(tipoDeSocio);

                    // Salva o objeto Socio atualizado
                    GerirSocio.atualizarSocio(socio);

                    // Atualiza a tabela
                    new GerirSocio();
                    dispose();
                }
            }
        });

        buttonPanel.add(buttonGuardar);
        mainPanel.add(buttonPanel);

        // Painel de informação do livro
        JPanel bookInfoPanel = new JPanel(new BorderLayout());
        bookInfoPanel.setBackground(Color.WHITE);
        bookInfoPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0)); // Borda com a cor desejada

        JLabel bookInfoLabel = new JLabel("Livros Requisitados", SwingConstants.CENTER);
        bookInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        bookInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaçamento
        bookInfoPanel.add(bookInfoLabel, BorderLayout.NORTH);

        String[] bookColumnNames = {"ISBN", "Título", "Edição", "Género", "Data Requisição", "Data Devolução", "Ação"};
        Object[][] bookData = criarReservasData();
        bookTable = createTable(bookData, bookColumnNames);
        bookTable.setRowHeight(30); // Ajusta a altura da linha da tabela de livro



        // Ajuste para centralizar o texto nas células da tabela de livros
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        bookTable.setDefaultRenderer(Object.class, centerRenderer);

        // Ajusta o tamanho das colunas da tabela de livros
        TableColumnModel columnModel = bookTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100); // ISBN
        columnModel.getColumn(1).setPreferredWidth(250); // Título
        columnModel.getColumn(2).setPreferredWidth(80); // Edição
        columnModel.getColumn(3).setPreferredWidth(150); // Género
        columnModel.getColumn(4).setPreferredWidth(120); // Data Requisição
        columnModel.getColumn(5).setPreferredWidth(120); // Data Devolução
        columnModel.getColumn(6).setPreferredWidth(100); // Ação

        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookScrollPane.setPreferredSize(new Dimension(bookTable.getPreferredSize().width, 200)); // Altura fixa para o painel de informações do livro
        bookScrollPane.setBorder(BorderFactory.createLineBorder(new Color(0x99D4FF))); // Borda com a cor desejada
        bookInfoPanel.add(bookScrollPane, BorderLayout.CENTER);

        // Adiciona o painel de informações do livro ao mainPanel
        mainPanel.add(bookInfoPanel);

        // Adiciona o mainPanel ao wrapperPanel
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao JFrame
        add(wrapperPanel);

        // Configurações do JFrame
        pack();
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setVisible(true);
    }

    // Método para carregar as reservas a partir de um arquivo serializado
    private ArrayList<Requisitar> carregarReservas(String filename) {
        ArrayList<Requisitar> reservas = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            reservas = (ArrayList<Requisitar>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    // Método para criar os dados da tabela de reservas
    private Object[][] criarReservasData() {
        Object[][] data = new Object[reservas.size()][7];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < reservas.size(); i++) {
            Requisitar reserva = reservas.get(i);
            data[i][0] = reserva.getLivro().getIsbn();
            data[i][1] = reserva.getLivro().getTitulo();
            data[i][2] = reserva.getLivro().getEdicao();
            data[i][3] = reserva.getLivro().getGenero();
            data[i][4] = reserva.getDataRequisicao().format(formatter);
            data[i][5] = reserva.getDataDevolucao() != null ? reserva.getDataDevolucao().format(formatter) : "-";
            data[i][6] = "Devolver";
        }

        return data;
    }

    // Método utilitário para criar uma tabela com os dados e os nomes das colunas fornecidos
    private JTable createTable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir edição apenas na coluna "Pago" e "Tipo de Sócio" da tabela de sócios
                return column == 5 || column == 6;
            }
        };
        table.getTableHeader().setFont(new Font("Inter", Font.BOLD, 14));
        table.setFont(new Font("Inter", Font.PLAIN, 14));
        table.setRowHeight(30);

        // Centraliza o texto nas células
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        return table;
    }

    // Método para devolver o livro
    public void devolverLivro(String isbn) {
        for (Requisitar reserva : reservas) {
            if (reserva.getLivro().getIsbn().equals(isbn) && reserva.getSocio().getNif().equals(socio.getNif())) {
                reservas.remove(reserva);
                salvarReservas("requisitar.ser", reservas);
                atualizarTabelaLivros();
                break;
            }
        }
    }

    // Método para salvar as reservas em um arquivo serializado
    private void salvarReservas(String filename, ArrayList<Requisitar> reservas) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(reservas);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar a tabela de livros
    private void atualizarTabelaLivros() {
        Object[][] bookData = criarReservasData();
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
        model.setDataVector(bookData, new String[]{"ISBN", "Título", "Edição", "Género", "Data Requisição", "Data Devolução", "Ação"});
    }
}
