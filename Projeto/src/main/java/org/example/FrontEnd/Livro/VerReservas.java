package org.example.FrontEnd.Livro;

import org.example.BackEnd.GerirRequisitar;
import org.example.BackEnd.Livro;
import org.example.BackEnd.Reserva;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

public class VerReservas extends BasePage {
    private Livro livro;
    private ArrayList<Reserva> reservas;
    private DefaultTableModel reservasTableModel;

    public VerReservas(Livro livro) {
        super("Ver Reservas", "/HeaderVerReservas.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirLivros();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        this.livro = livro;
        this.reservas = GerirRequisitar.carregarReservas(); // Carregar todas as reservas
        reservas = filtrarReservasPorLivro(livro);

        JPanel wrapperPanel = new JPanel(new BorderLayout(10, 10));
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);
        wrapperPanel.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de informação do livro
        JPanel bookInfoPanel = new JPanel(new BorderLayout());
        bookInfoPanel.setBackground(Color.WHITE);
        bookInfoPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0)); // Remover a borda preta
        bookInfoPanel.setMaximumSize(new Dimension(400, 100));

        String[] bookColumnNames = {"ISBN", "Título"};
        Object[][] bookData = {
                {livro.getIsbn(), livro.getTitulo()},
        };
        JTable bookTable = createTable(bookData, bookColumnNames, false); // false to prevent cell editing
        bookTable.setRowHeight(40); // Ajusta a altura da linha da tabela de livro
        Dimension bookTableSize = new Dimension(bookTable.getPreferredSize().width, bookTable.getRowHeight() + bookTable.getTableHeader().getPreferredSize().height);
        bookTable.setPreferredScrollableViewportSize(bookTableSize);

        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookScrollPane.setPreferredSize(bookTableSize);
        bookScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Remover a borda preta
        bookScrollPane.getViewport().setBackground(Color.WHITE); // Define o fundo do viewport para branco
        bookScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); // Disable vertical scroll
        bookScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll

        bookInfoPanel.add(bookScrollPane, BorderLayout.CENTER);

        JLabel bookInfoLabel = new JLabel("Livro", SwingConstants.CENTER);
        bookInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        bookInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaçamento
        bookInfoPanel.add(bookInfoLabel, BorderLayout.NORTH);

        // Painel de informação do sócio
        JPanel memberInfoPanel = new JPanel(new BorderLayout());
        memberInfoPanel.setBackground(Color.WHITE);
        memberInfoPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 0)); // Remover a borda preta

        String[] columnNames = {"NIF", "Nome", "Data de Reserva", "Ações"};
        Object[][] data = new Object[reservas.size()][columnNames.length];

        for (int i = 0; i < reservas.size(); i++) {
            Reserva reserva = reservas.get(i);
            data[i][0] = reserva.getSocio().getNif();
            data[i][1] = reserva.getSocio().getNome();
            data[i][2] = reserva.getDataReserva().toString();
            data[i][3] = ""; // Placeholder for actions
        }

        reservasTableModel = new DefaultTableModel(data, columnNames);
        JTable tabelaReservas = createTable(reservasTableModel, columnNames, true); // true to allow cell editing in the last column
        tabelaReservas.setRowHeight(60); // Ajusta a altura da linha da tabela de sócios

        // Add JScrollPane with better scroll functionality
        JScrollPane reservasScrollPane = new JScrollPane(tabelaReservas);
        reservasScrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Remover a borda preta
        reservasScrollPane.getViewport().setBackground(Color.WHITE); // Define o fundo do viewport para branco
        reservasScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        reservasScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        reservasScrollPane.setPreferredSize(new Dimension(800, 300)); // Limitar a altura da tabela com um tamanho preferido fixo

        memberInfoPanel.add(reservasScrollPane, BorderLayout.CENTER);

        JLabel memberInfoLabel = new JLabel("Reservas", SwingConstants.CENTER);
        memberInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        memberInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaçamento
        memberInfoPanel.add(memberInfoLabel, BorderLayout.NORTH);

        JPanel combinedPanel = new JPanel(new BorderLayout(10, 10));
        combinedPanel.setBackground(Color.WHITE);
        combinedPanel.add(memberInfoPanel, BorderLayout.CENTER);

        mainPanel.add(bookInfoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adiciona espaço entre os painéis
        mainPanel.add(combinedPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adiciona espaço antes dos botões

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        add(wrapperPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private ArrayList<Reserva> filtrarReservasPorLivro(Livro livro) {
        ArrayList<Reserva> reservasFiltradas = new ArrayList<>();
        for (Reserva reserva : reservas) {
            if (reserva.getLivro().getIsbn().equals(livro.getIsbn())) {
                reservasFiltradas.add(reserva);
            }
        }
        return reservasFiltradas;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 16));
        label.setOpaque(true);
        label.setBackground(Color.WHITE);
        return label;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Inter", Font.PLAIN, 16));
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        label.setBackground(Color.WHITE);
        label.setOpaque(true);
        return label;
    }

    private JTable createTable(Object[][] data, String[] columnNames, boolean isActionColumnEditable) {
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        return createTable(tableModel, columnNames, isActionColumnEditable);
    }

    private JTable createTable(DefaultTableModel tableModel, String[] columnNames, boolean isActionColumnEditable) {
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (isActionColumnEditable) {
                    return column == columnNames.length - 1; // Allow editing only in the last column
                } else {
                    return false; // Prevent editing in all columns
                }
            }

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

        // Configuração visual da tabela
        table.setRowHeight(60);
        table.getTableHeader().setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 16));
        table.getTableHeader().setBackground(new Color(0x6EC2FF));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setFont(new Font("Inter", Font.PLAIN, 16));
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false); // Prevent column reordering

        // Renderizador centralizado para células
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

        // Adicionando um botão "Cancelar" na coluna "Ações" se a tabela permitir edição
        if (isActionColumnEditable && columnNames.length > 0 && columnNames[columnNames.length - 1].equals("Ações")) {
            columnModel.getColumn(columnNames.length - 1).setCellRenderer(new TableCellRenderer() {
                private final JPanel panel = new JPanel(new GridBagLayout());
                private final JButton cancelButton = new RoundButton("Cancelar");

                {
                    cancelButton.setBackground(Color.BLACK);
                    cancelButton.setForeground(Color.WHITE);
                    cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    cancelButton.setFont(new Font("Inter", Font.BOLD, 14));
                    cancelButton.setOpaque(true);
                    cancelButton.setBorderPainted(false);
                    cancelButton.setContentAreaFilled(false);
                    cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    cancelButton.setPreferredSize(new Dimension(100, 30));

                    panel.add(cancelButton);
                }

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    if (isSelected) {
                        panel.setBackground(table.getSelectionBackground());
                    } else {
                        panel.setBackground(table.getBackground());
                    }
                    return panel;
                }
            });

            columnModel.getColumn(columnNames.length - 1).setCellEditor(new TableCellEditor() {
                private final JPanel panel = new JPanel(new GridBagLayout());
                private final JButton cancelButton = new RoundButton("Cancelar");

                {
                    cancelButton.setBackground(Color.BLACK);
                    cancelButton.setForeground(Color.WHITE);
                    cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    cancelButton.setFont(new Font("Inter", Font.BOLD, 14));
                    cancelButton.setOpaque(true);
                    cancelButton.setBorderPainted(false);
                    cancelButton.setContentAreaFilled(false);
                    cancelButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    cancelButton.setPreferredSize(new Dimension(100, 30));

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Exibe o diálogo de confirmação personalizado
                            int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende cancelar a reserva?", "Confirmação", "Não", "Sim");

                            // Verifica a resposta
                            if (response == JOptionPane.YES_OPTION) {
                                int selectedRow = table.getSelectedRow();
                                if (selectedRow != -1) {
                                    reservas.remove(selectedRow);
                                    reservasTableModel.removeRow(selectedRow);
                                    GerirRequisitar.salvarReservas(reservas);
                                }
                            }
                        }
                    });

                    panel.add(cancelButton);
                }

                @Override
                public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                    if (isSelected) {
                        panel.setBackground(table.getSelectionBackground());
                    } else {
                        panel.setBackground(table.getBackground());
                    }
                    return panel;
                }

                @Override
                public Object getCellEditorValue() {
                    return null; // Não é necessário retornar nenhum valor
                }

                @Override
                public boolean isCellEditable(EventObject anEvent) {
                    return true; // Permitir edição da célula quando o botão for clicado
                }

                @Override
                public boolean shouldSelectCell(EventObject anEvent) {
                    return true;
                }

                @Override
                public boolean stopCellEditing() {
                    return true;
                }

                @Override
                public void cancelCellEditing() {
                }

                @Override
                public void addCellEditorListener(CellEditorListener l) {
                }

                @Override
                public void removeCellEditorListener(CellEditorListener l) {
                }
            });
        }

        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        return table;
    }

}
