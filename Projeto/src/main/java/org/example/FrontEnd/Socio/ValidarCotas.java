package org.example.FrontEnd.Socio;

import org.example.BackEnd.Socio;
import org.example.BackEnd.Cota;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.BiblioLiz;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ValidarCotas extends BasePage {
    private List<Socio> socios;
    private JTextField searchText;
    private SocioTableModel tableModel;

    public ValidarCotas() {
        super("Validar Cotas", "/HeaderValidarCotas.png", e -> {
            new BiblioLiz();
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            frame.dispose();
        }, false);

        // Carregar dados dos sócios do arquivo
        socios = carregarSocios("socios.ser");

        // Filtrar sócios com cotas não pagas
        List<Socio> sociosNaoPagos = new ArrayList<>();
        for (Socio socio : socios) {
            if (!socio.getCota().isPago()) {
                sociosNaoPagos.add(socio);
            }
        }

        // Inicializar o SocioTableModel com os sócios não pagos
        tableModel = new SocioTableModel(sociosNaoPagos);

        // Configuração do painel principal
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(0xFFFFFF));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(0xFFFFFF));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de pesquisa
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

        JPanel searchContainerPanel = new JPanel(new GridBagLayout());
        searchContainerPanel.setBackground(Color.WHITE);
        searchContainerPanel.add(searchPanel);

        topPanel.add(searchContainerPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Tabela de sócios
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

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
        table.getTableHeader().setBackground(new Color(0x6EC2FF));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setFont(new Font("Inter", Font.PLAIN, 16));
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);

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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Imagem e contagem de sócios
        JLabel numeroSocios = new JLabel(new ImageIcon(getClass().getResource("/NumSocios.png")));
        numeroSocios.setBackground(Color.WHITE);

        JLabel countLabel = new JLabel("" + sociosNaoPagos.size());
        countLabel.setFont(new Font("Inter", Font.BOLD, 38));
        countLabel.setForeground(Color.BLACK);
        countLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

        JPanel bottomLeftPanel = new JPanel(null);
        bottomLeftPanel.setPreferredSize(new Dimension(100, 100));
        bottomLeftPanel.setBackground(Color.WHITE);

        numeroSocios.setBounds(30, 0, 450, 100);
        countLabel.setBounds(260, 55, 100, 40);

        bottomLeftPanel.add(numeroSocios);
        bottomLeftPanel.add(countLabel);

        // Adicionar componentes ao painel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomLeftPanel, BorderLayout.SOUTH);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        add(wrapperPanel, BorderLayout.CENTER);
        setVisible(true);

        // Adicionar listener para a barra de pesquisa
        searchText.addActionListener(e -> searchSocios());

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
    private List<Socio> carregarSocios(String nomeArquivo) {
        List<Socio> socios = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            socios = (List<Socio>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return socios;
    }
    public class SocioTableModel extends AbstractTableModel {
        private List<Socio> socios;
        private final String[] columnNames = {"Nome", "Email", "Telefone", "Morada", "Tipo de Sócio", "Pago"};

        public SocioTableModel(List<Socio> socios) {
            this.socios = socios;
        }


        public int getRowCount() {
            return socios.size();
        }

        public int getColumnCount() {
            return columnNames.length;
        }

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
                    return socio.getTipoDeSocio().toString();
                case 5:
                    return socio.getCota().isPago() ? "Sim" : "Não";
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void setSocios(List<Socio> socios) {
            this.socios = socios;
            fireTableDataChanged();
        }

        public Socio getSocioAt(int rowIndex) {
            return socios.get(rowIndex);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ValidarCotas::new);
    }
}
