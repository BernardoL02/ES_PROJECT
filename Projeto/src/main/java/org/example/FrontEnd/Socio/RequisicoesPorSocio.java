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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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
        memberInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaçamento
        memberInfoPanel.add(memberInfoLabel, BorderLayout.NORTH);

        String[] memberColumnNames = {"Nif", "Nome", "Morada", "Telefone", "Email", "Pago", "Tipo de Sócio"};
        Object[][] memberData = {
                {socio.getNif(), socio.getNome(), socio.getEndereco(), socio.getTelefone(), socio.getEmail(), socio.getCota().isPago() ? "Sim" : "Não", socio.getTipoDeSocio().toString()}
        };
        memberTable = createTable(memberData, memberColumnNames);
        memberTable.setRowHeight(30); // Ajusta a altura da linha da tabela de sócios
        memberTable.setPreferredSize(new Dimension(memberTable.getPreferredSize().width, memberTable.getRowHeight() + memberTable.getTableHeader().getPreferredSize().height)); // Ajuste o tamanho da tabela de sócios
        JScrollPane memberScrollPane = new JScrollPane(memberTable);
        memberScrollPane.setPreferredSize(memberTable.getPreferredSize());
        memberScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Remover a borda preta
        memberScrollPane.getViewport().setBackground(Color.WHITE); // Define o fundo do viewport para branco

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

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel de informação do livro
        JPanel bookInfoPanel = new JPanel(new BorderLayout());
        bookInfoPanel.setBackground(Color.WHITE);
        bookInfoPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0)); // Remover a borda preta

        JLabel bookInfoLabel = new JLabel("Livros Requisitados", SwingConstants.CENTER);
        bookInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        bookInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaçamento
        bookInfoPanel.add(bookInfoLabel, BorderLayout.NORTH);

        String[] bookColumnNames = {"ISBN", "Título", "Edição", "Género", "Data Requisição", "Data Devolução", "Ação"};
        Object[][] bookData = criarReservasData();
        bookTable = createTable(bookData, bookColumnNames);
        bookTable.setRowHeight(30); // Ajusta a altura da linha da tabela de livro
        bookTable.setPreferredSize(new Dimension(bookTable.getPreferredSize().width, bookTable.getRowHeight() + bookTable.getTableHeader().getPreferredSize().height)); // Ajuste o tamanho da tabela de livro

        bookTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        bookTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor());

        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookScrollPane.setPreferredSize(bookTable.getPreferredSize());
        bookScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Remover a borda preta
        bookScrollPane.getViewport().setBackground(Color.WHITE); // Define o fundo do viewport para branco

        bookInfoPanel.add(bookScrollPane, BorderLayout.CENTER);

        // Adiciona o painel de informação do livro ao mainPanel
        mainPanel.add(bookInfoPanel);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        add(wrapperPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private Object[][] criarReservasData() {
        ArrayList<Object[]> data = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Requisitar reserva : reservas) {
            if (reserva.getSocio().getNif().equals(socio.getNif())) {
                data.add(new Object[]{
                        reserva.getLivro().getIsbn(),
                        reserva.getLivro().getTitulo(),
                        reserva.getLivro().getEdicao(),
                        reserva.getLivro().getGenero(),
                        reserva.getDataRequisicao().format(formatter),
                        reserva.getDataDevolucao() != null ? reserva.getDataDevolucao().format(formatter) : "",
                        ""
                });
            }
        }
        return data.toArray(new Object[0][0]);
    }

    private void devolverLivro(int row) {
        String isbnLivro = (String) bookTable.getValueAt(row, 0);
        for (Requisitar reserva : reservas) {
            if (reserva.getLivro().getIsbn().equals(isbnLivro) && reserva.getSocio().getNif().equals(socio.getNif())) {
                reserva.setDataDevolucao(LocalDate.now());
                JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!");
                break;
            }
        }
        // Update the table data
        Object[][] updatedData = criarReservasData();
        ((DefaultTableModel) bookTable.getModel()).setDataVector(updatedData, new String[]{"ISBN", "Título", "Edição", "Género", "Data Requisição", "Data Devolucao", "Ação"});
    }

    private JTable createTable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Torna todos os campos editáveis, exceto a coluna "Ação"
                return column != 6;
            }
        }) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (component instanceof JComponent) {
                    ((JComponent) component).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
                    component.setBackground(new Color(0xDFF3FF)); // Define a cor de fundo para azul claro
                    ((JComponent) component).setOpaque(true); // Garante que seja opaco
                }
                return component;
            }
        };
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 16));
        table.getTableHeader().setBackground(new Color(0x6EC2FF));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setFont(new Font("Inter", Font.PLAIN, 16));
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(true); // Permite redimensionar as colunas

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setOpaque(true); // Garante que seja opaco
        centerRenderer.setBackground(new Color(0x6EC2FF)); // Define a cor de fundo para azul claro
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setPreferredWidth(120);
        }
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.getTableHeader().setResizingAllowed(true);
        table.getTableHeader().setReorderingAllowed(false);

        return table;
    }

    private ArrayList<Requisitar> carregarReservas(String nomeArquivo) {
        ArrayList<Requisitar> requisitars = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            requisitars = (ArrayList<Requisitar>) ois.readObject();
        } catch (FileNotFoundException e) {
            // If the file does not exist, create it
            salvarReservas(requisitars, nomeArquivo);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return requisitars;
    }

    private void salvarReservas(ArrayList<Requisitar> requisitars, String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(requisitars);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true); // Garante que o botão seja opaco
            setBackground(Color.BLACK); // Define a cor de fundo do botão como preto
            setForeground(Color.WHITE); // Define a cor do texto do botão como branco
            setFont(new Font("Inter", Font.BOLD, 14));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Devolver"); // Define o texto do botão
            return this;
        }
    }

    private class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private final JPanel panel = new JPanel(new GridBagLayout());
        private final JButton actionButton = new RoundButton("Devolver");

        public ButtonEditor() {
            actionButton.setPreferredSize(new Dimension(100, 35)); // Define o tamanho fixo dos botões
            actionButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 14)); // Ajusta o tamanho da fonte
            actionButton.setBorder(null);
            actionButton.setContentAreaFilled(true);
            actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            actionButton.setBackground(Color.BLACK);
            actionButton.setForeground(Color.WHITE);

            actionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = bookTable.getEditingRow();
                    // Perform action when button is clicked
                    devolverLivro(row);
                    fireEditingStopped();
                }
            });

            panel.add(actionButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            panel.setOpaque(true);
            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            } else {
                panel.setBackground(table.getBackground());
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }
}
