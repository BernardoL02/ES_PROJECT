package org.example.FrontEnd.Livro;

import org.example.BackEnd.Fornecedor;
import org.example.BackEnd.GerirRequisitar;
import org.example.BackEnd.Livro;
import org.example.BackEnd.Reserva;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class GerirLivros extends BasePage {
    private static ArrayList<Livro> livros = new ArrayList<>();
    private JTable table;
    private LivroTableModel tableModel;
    private JTextField searchText;

    public GerirLivros() {
        super("Gerir Livros", "/HeaderGerirLivros.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, true);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon searchBarImage = new ImageIcon(getClass().getResource("/ProcuraBar.png"));
                Image image = searchBarImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setPreferredSize(new Dimension(600, 40));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchPanel.setBackground(Color.WHITE);

        searchText = new JTextField();
        searchText.setOpaque(false);
        searchText.setBorder(BorderFactory.createEmptyBorder(5, 55, 5, 5));
        searchPanel.add(searchText, BorderLayout.CENTER);

        // Add DocumentListener to the searchText field
        searchText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchBooks();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchBooks();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchBooks();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(0xFFFFFF));

        RoundButton addButton = new RoundButton("Adicionar Livro");
        addButton.setBackground(new Color(0x99D4FF));
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        addButton.setPreferredSize(new Dimension(160, 40));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarLivro();
                dispose(); // Fecha a janela principal
            }
        });

        buttonPanel.add(addButton);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ISBN", "Título", "Edição", "Gênero", "Localização", "Quantidade", "Fornecedor", "Ações"};

        // Carregar livros do arquivo
        carregarLivros("livros.ser");

        tableModel = new LivroTableModel(livros, columnNames);
        table = new JTable(tableModel) {
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

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Somente a coluna de ações é editável
            }
        };

        table.setRowHeight(60);
        table.getTableHeader().setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        table.getTableHeader().setBackground(new Color(0x6EC2FF));
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
        columnModel.getColumn(0).setPreferredWidth(120);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(120);
        columnModel.getColumn(5).setPreferredWidth(120);
        columnModel.getColumn(6).setPreferredWidth(180);
        columnModel.getColumn(7).setPreferredWidth(180);

        table.getTableHeader().setResizingAllowed(true);

        columnModel.getColumn(7).setCellRenderer(new TableCellRenderer() {
            private final JPanel panel = new JPanel(new GridBagLayout());
            private final JButton editButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
            private final JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
            private final JButton reserveButton = new RoundButton("Reservas");

            {
                editButton.setBorder(null);
                editButton.setContentAreaFilled(false);
                editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                deleteButton.setBorder(null);
                deleteButton.setContentAreaFilled(false);
                deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                ImageIcon editIcon = new ImageIcon(getClass().getResource("/edit.png"));
                Image editImg = editIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                editButton.setIcon(new ImageIcon(editImg));

                ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/delete.png"));
                Image deleteImg = deleteIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                deleteButton.setIcon(new ImageIcon(deleteImg));

                reserveButton.setBackground(Color.BLACK);
                reserveButton.setForeground(Color.WHITE);
                reserveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                panel.add(editButton);
                panel.add(deleteButton);
                panel.add(reserveButton);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                panel.setOpaque(true);
                if (isSelected) {
                    panel.setBackground(table.getSelectionBackground());
                } else {
                    panel.setBackground(table.getBackground());
                }
                return panel;
            }
        });

        columnModel.getColumn(7).setCellEditor(new TableCellEditor() {
            private final JPanel panel = new JPanel(new GridBagLayout());
            private final JButton editButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
            private final JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/delete.png")));
            private final JButton reserveButton = new RoundButton("Reservas");

            {
                editButton.setBorder(null);
                editButton.setContentAreaFilled(false);
                editButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                deleteButton.setBorder(null);
                deleteButton.setContentAreaFilled(false);
                deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                ImageIcon editIcon = new ImageIcon(getClass().getResource("/edit.png"));
                Image editImg = editIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                editButton.setIcon(new ImageIcon(editImg));

                ImageIcon deleteIcon = new ImageIcon(getClass().getResource("/delete.png"));
                Image deleteImg = deleteIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                deleteButton.setIcon(new ImageIcon(deleteImg));

                reserveButton.setBackground(Color.BLACK);
                reserveButton.setForeground(Color.WHITE);
                reserveButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Livro livro = livros.get(table.convertRowIndexToModel(table.getSelectedRow()));
                        new EditarLivro(livro);
                        dispose();
                    }
                });

                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que deseja eliminar o livro?", "Confirmação", "Cancelar", "Confirmar");

                        if (response == JOptionPane.YES_OPTION) {
                            int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                            if (selectedRow != -1) {
                                Livro livro = livros.get(selectedRow);

                                if (hasReservations(livro)) {
                                    JOptionPane.showMessageDialog(null, "Não é possível eliminar o livro, pois existem reservas associadas.", "Erro", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    livros.remove(selectedRow);
                                    tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
                                    salvarLivros("livros.ser");
                                }
                            }
                        }
                    }
                });

                reserveButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
                        Livro livro = livros.get(selectedRow);

                        ArrayList<Reserva> reservas = GerirRequisitar.carregarReservas();
                        ArrayList<Reserva> reservasFiltradas = new ArrayList<>();

                        for (Reserva reserva : reservas) {
                            if (reserva.getLivro().getIsbn().equals(livro.getIsbn())) {
                                reservasFiltradas.add(reserva);
                            }
                        }

                        if (reservasFiltradas.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Livro sem reservas efectuadas no momento", "Aviso", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        new VerReservas(livro);
                        dispose(); // Fecha a janela principal
                    }
                });

                panel.add(editButton);
                panel.add(deleteButton);
                panel.add(reserveButton);
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

            @Override
            public boolean isCellEditable(java.util.EventObject e) {
                return true;
            }

            @Override
            public boolean shouldSelectCell(java.util.EventObject anEvent) {
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
            public void addCellEditorListener(javax.swing.event.CellEditorListener l) {
            }

            @Override
            public void removeCellEditorListener(javax.swing.event.CellEditorListener l) {
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        add(wrapperPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void carregarLivros(String nomeArquivo) {
        File file = new File(nomeArquivo);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Arquivo de livros criado.");
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo de livros: " + e.getMessage());
            }
        }

        if (file.length() == 0) {
            livros = new ArrayList<>();
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
                livros = (ArrayList<Livro>) ois.readObject();
                System.out.println("Livros carregados com sucesso.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar livros: " + e.getMessage());
            }
        }
    }

    private boolean hasReservations(Livro livro) {
        ArrayList<Reserva> reservas = GerirRequisitar.carregarReservas();
        for (Reserva reserva : reservas) {
            if (reserva.getLivro().getIsbn().equals(livro.getIsbn())) {
                return true;
            }
        }
        return false;
    }

    public static void adicionarLivro(Livro livro) {
        livros.add(livro);
        salvarLivros("livros.ser");
    }

    public static void salvarLivros(String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(livros);
            System.out.println("Livros salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar livros: " + e.getMessage());
        }
    }

    private void searchBooks() {
        String query = searchText.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            tableModel.setLivros(livros);
        } else {
            ArrayList<Livro> filteredLivros = new ArrayList<>();
            for (Livro livro : livros) {
                if (livro.getIsbn().toLowerCase().contains(query) || livro.getTitulo().toLowerCase().contains(query)) {
                    filteredLivros.add(livro);
                }
            }
            if (filteredLivros.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome Introduzido Invalido", "Erro", JOptionPane.ERROR_MESSAGE);
                tableModel.setLivros(livros); // Reset to show all books
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        searchText.setText("");
                    }
                });
            } else {
                tableModel.setLivros(filteredLivros);
            }
        }
    }
}

class LivroTableModel extends AbstractTableModel {
    private ArrayList<Livro> livros;
    private String[] columnNames;

    public LivroTableModel(ArrayList<Livro> livros, String[] columnNames) {
        this.livros = livros;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return livros.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Livro livro = livros.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return livro.getIsbn();
            case 1:
                return livro.getTitulo();
            case 2:
                return livro.getEdicao();
            case 3:
                return livro.getGenero();
            case 4:
                return livro.getLocalizacao();
            case 5:
                return livro.getQuantidade();
            case 6:
                return livro.getFornecedor().getNome();  // Exibir o nome do fornecedor
            case 7:
                return "Ações";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7; // Apenas a coluna de ações é editável
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
        fireTableDataChanged();
    }
}
