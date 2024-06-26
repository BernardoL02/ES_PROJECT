package org.example.FrontEnd.Socio;

import org.example.BackEnd.Socio;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerirSocio extends BasePage {
    private static ArrayList<Socio> socios = new ArrayList<>();
    private JTable table;
    private SocioTableModel tableModel;
    private JTextField searchText;

    public GerirSocio() {
        super("Gerir Sócios", "/HeaderGerirSocio.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, true);

        carregarSocios("socios.ser");

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

        searchText.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchSocios();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchSocios();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchSocios();
            }
        });

        RoundButton addButton = new RoundButton("Adicionar Sócio");
        addButton.setBackground(new Color(0x99D4FF));
        addButton.setForeground(Color.BLACK);
        addButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        addButton.setPreferredSize(new Dimension(160, 40));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarSocio();
                dispose(); // Fecha a janela principal
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(new Color(0xFFFFFF));
        buttonPanel.add(addButton);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Nome", "Email", "Telefone", "Morada", "Tipo de Sócio", "Pago", "Ações"};

        tableModel = new SocioTableModel(socios, columnNames);
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
                return column == 6; // Somente a coluna de ações é editável
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

        table.getTableHeader().setResizingAllowed(true);

        columnModel.getColumn(6).setCellRenderer(new TableCellRenderer() {
            private final JPanel panel = new JPanel(new GridBagLayout());
            private final JButton editButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
            private final JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/delete.png")));

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

                panel.add(editButton);
                panel.add(deleteButton);
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

        columnModel.getColumn(6).setCellEditor(new TableCellEditor() {
            private final JPanel panel = new JPanel(new GridBagLayout());
            private final JButton editButton = new JButton(new ImageIcon(getClass().getResource("/edit.png")));
            private final JButton deleteButton = new JButton(new ImageIcon(getClass().getResource("/delete.png")));

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

                editButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) {
                            Socio socio = socios.get(selectedRow);
                            new RequisicoesPorSocio(socio);
                            dispose(); // Fecha a janela principal
                        }
                    }
                });

                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow != -1) {
                            socios.remove(selectedRow);
                            tableModel.fireTableRowsDeleted(selectedRow, selectedRow);
                            salvarSocios("socios.ser"); // Salva os sócios após a exclusão
                        }
                    }
                });

                panel.add(editButton);
                panel.add(deleteButton);
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

        pack();
    }

    private void searchSocios() {
        String query = searchText.getText().trim().toLowerCase();
        if (query.isEmpty()) {
            // Se a barra de pesquisa estiver vazia, mostrar todos os sócios não pagos
            tableModel.setSocios(socios);
        } else {
            // Filtrar pelo nome ou NIF do sócio
            List<Socio> filteredSocios = new ArrayList<>();
            for (Socio socio : socios) {
                if (socio.getNome().toLowerCase().contains(query) || socio.getNif().contains(query)) {
                    filteredSocios.add(socio);
                }
            }
            if (filteredSocios.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Não existem sócios com esse Nome ou NIF", "Erro", JOptionPane.ERROR_MESSAGE);
                tableModel.setSocios(socios);
                SwingUtilities.invokeLater(() -> searchText.setText(""));
            } else {
                tableModel.setSocios(filteredSocios);
            }
        }
    }

    public static void adicionarSocio(Socio socio) {
        socios.add(socio);
        salvarSocios("socios.ser");
    }

    public static void atualizarSocio(Socio socio) {
        for (int i = 0; i < socios.size(); i++) {
            if (socios.get(i).getNif().equals(socio.getNif())) {
                socios.set(i, socio);
                salvarSocios("socios.ser");
                return;
            }
        }
    }

    private void carregarSocios(String nomeArquivo) {
        File file = new File(nomeArquivo);
        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Arquivo de sócios criado.");
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo de sócios: " + e.getMessage());
            }
        }

        if (file.length() == 0) {
            socios = new ArrayList<>();
        } else {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
                socios = (ArrayList<Socio>) ois.readObject();
                System.out.println("Sócios carregados com sucesso.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar sócios: " + e.getMessage());
            }
        }
    }

    public static void salvarSocios(String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(socios);
            System.out.println("Sócios salvos com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar sócios: " + e.getMessage());
        }
    }
}

class SocioTableModel extends AbstractTableModel {
    private ArrayList<Socio> socios;
    private String[] columnNames;

    public SocioTableModel(ArrayList<Socio> socios, String[] columnNames) {
        this.socios = socios;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return socios.size();
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
        Socio socio = socios.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return socio.getNome();
            case 1:
                return socio.getEmail();
            case 2:
                return socio.getTelefone();
            case 3:
                return socio.getEndereco();
            case 4:
                return socio.getTipoDeSocio().name();
            case 5:
                return socio.getCota().isPago() ? "Sim" : "Não";
            case 6:
                return "Ações";
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6; // Apenas a coluna de ações é editável
    }

    public void setSocios(List<Socio> socios) {
        this.socios = new ArrayList<>(socios);
        fireTableDataChanged(); // Notifica a tabela que os dados foram alterados
    }
}
