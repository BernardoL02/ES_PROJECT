package org.example.FrontEnd.Livro;

import org.example.BackEnd.*;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class RequisitarLivro extends BasePage {
    private Socio socio;
    private ArrayList<Livro> livros;
    private ArrayList<Requisitar> requisitars;
    private ArrayList<Reserva> reservas;

    public RequisitarLivro(Socio socio) {
        super("Requisitar Livro", "/HeaderRequisitarLivro.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        this.socio = socio;
        this.livros = carregarLivros("livros.ser");
        this.requisitars = GerirRequisitar.carregarRequisicoes();
        this.reservas = GerirRequisitar.carregarReservas();

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
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
        searchPanel.setPreferredSize(new Dimension(700, 45));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        searchPanel.setBackground(Color.WHITE);

        JTextField searchText = new JTextField();
        searchText.setOpaque(false);
        searchText.setBorder(BorderFactory.createEmptyBorder(5, 55, 5, 5));
        searchPanel.add(searchText, BorderLayout.CENTER);

        // Centraliza a barra de pesquisa
        JPanel searchContainerPanel = new JPanel(new GridBagLayout());
        searchContainerPanel.setBackground(Color.WHITE);
        searchContainerPanel.add(searchPanel);

        topPanel.add(searchContainerPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"ISBN", "Título", "Edição", "Gênero", "Localização", "Quantidade", "Ações"};
        Object[][] data = new Object[livros.size()][columnNames.length];

        for (int i = 0; i < livros.size(); i++) {
            Livro livro = livros.get(i);
            int reservasAtivas = contarReservasAtivas(livro);
            int quantidadeDisponivel = livro.getQuantidade() - reservasAtivas;

            data[i][0] = livro.getIsbn();
            data[i][1] = livro.getTitulo();
            data[i][2] = livro.getEdicao();
            data[i][3] = livro.getGenero();
            data[i][4] = livro.getLocalizacao();
            data[i][5] = quantidadeDisponivel + "/" + livro.getQuantidade();
            data[i][6] = ""; // Placeholder for actions
        }

        JTable table = new JTable(data, columnNames) {
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

        table.getTableHeader().setResizingAllowed(false);

        columnModel.getColumn(6).setCellRenderer(new TableCellRenderer() {
            private final JPanel panel = new JPanel(new GridBagLayout());
            private final JButton actionButton = new RoundButton("");

            {
                actionButton.setPreferredSize(new Dimension(100, 35)); // Define o tamanho fixo dos botões
                actionButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 14)); // Ajusta o tamanho da fonte
                actionButton.setBorder(null);
                actionButton.setContentAreaFilled(true);
                actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                panel.add(actionButton);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                int quantidade = Integer.parseInt(table.getValueAt(row, 5).toString().split("/")[0]);
                if (quantidade > 0) {
                    actionButton.setText("Requisitar");
                    actionButton.setBackground(new Color(0x99D4FF));
                    actionButton.setBackground(Color.BLACK);
                    actionButton.setForeground(Color.WHITE);
                } else {
                    actionButton.setText("Reservar");
                    actionButton.setBackground(Color.BLACK);
                    actionButton.setForeground(Color.WHITE);
                }

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
            private final JButton actionButton = new RoundButton("");

            {
                actionButton.setPreferredSize(new Dimension(100, 35)); // Define o tamanho fixo dos botões
                actionButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 14)); // Ajusta o tamanho da fonte
                actionButton.setBorder(null);
                actionButton.setContentAreaFilled(true);
                actionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

                actionButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int row = table.getEditingRow();
                        int quantidade = Integer.parseInt(table.getValueAt(row, 5).toString().split("/")[0]);

                        // Coletar dados do livro selecionado
                        Livro livro = livros.get(row);
                        if (socio.getCota().isPago()) {
                            if (quantidade > 0) {
                                new ConfirmaRequisicao(socio, livro);
                            } else {
                                new ConfirmaReserva(socio, livro);
                            }
                            dispose(); // Fecha a janela principal
                        } else {
                            JOptionPane.showMessageDialog(null, "Impossivel Requisitar o Socio tem cotas por pagar!", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                panel.add(actionButton);
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                int quantidade = Integer.parseInt(table.getValueAt(row, 5).toString().split("/")[0]);
                if (quantidade > 0) {
                    actionButton.setText("Requisitar");
                    actionButton.setBackground(new Color(0x99D4FF));
                    actionButton.setBackground(Color.BLACK);
                    actionButton.setForeground(Color.WHITE);
                } else {
                    actionButton.setText("Reservar");
                    actionButton.setBackground(Color.BLACK);
                    actionButton.setForeground(Color.WHITE);
                }

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

    private ArrayList<Livro> carregarLivros(String nomeArquivo) {
        ArrayList<Livro> livros = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            livros = (ArrayList<Livro>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return livros;
    }

    private int contarReservasAtivas(Livro livro) {
        int count = 0;
        for (Requisitar requisitar : requisitars) {
            if (requisitar.getLivro().getIsbn().equals(livro.getIsbn()) && requisitar.getDataDevolucaoPrevista().isAfter(LocalDate.now())) {
                count++;
            }
        }
        for (Reserva reserva : reservas) {
            if (reserva.getLivro().getIsbn().equals(livro.getIsbn())) {
                count++;
            }
        }
        return count;
    }
}
