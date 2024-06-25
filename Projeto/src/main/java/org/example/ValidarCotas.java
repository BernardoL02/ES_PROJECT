package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ValidarCotas extends BasePage {
    public ValidarCotas() {
        super("Validar Cotas", "/HeaderValidarCotas.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        // Painel principal para centralizar verticalmente
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel para o conteúdo principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel principal

        // Painel para o botão "Adicionar"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel do botão

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(new Color(0xFFFFFF));
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

        JTextField searchText = new JTextField();
        searchText.setOpaque(false);
        searchText.setBorder(BorderFactory.createEmptyBorder(5, 55, 5, 5));
        searchPanel.add(searchText, BorderLayout.CENTER);
        searchPanel.setBackground(Color.WHITE);

        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(topPanel, BorderLayout.NORTH); // Adiciona o topPanel ao mainPanel

        String[] columnNames = {"Nome", "Email", "Telefone", "Morada", "Tipo de Sócio", "Pago"};
        Object[][] data = {
                {"Bernardo", "bernardo@example.com", "123456789", "Rua A, 123 - Bairro B", "Entusiasta", "Não"},
                {"AAAA", "aaaa@example.com", "123456789", "Rua A, 123 - Bairro B", "Entusiasta", "Não"}
        };
        int countNaoPago = 0;
        for (Object[] row : data) {
            if ("Não".equals(row[5])) {
                countNaoPago++;
            }
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

        table.getTableHeader().setResizingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Usar JLabel para a imagem
        JLabel numeroSocios = new JLabel(new ImageIcon(getClass().getResource("/NumSocios.png")));
        numeroSocios.setBackground(Color.WHITE);


        // Label para exibir a contagem
        JLabel countLabel = new JLabel("" + countNaoPago);
        countLabel.setFont(new Font("Inter", Font.BOLD, 38));
        countLabel.setForeground(Color.BLACK);
        countLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Move 30px para a direita

        // Painel para posicionar a imagem e a contagem no canto inferior esquerdo
        JPanel bottomLeftPanel = new JPanel(null); // Usar layout nulo para posicionamento absoluto
        bottomLeftPanel.setPreferredSize(new Dimension(100, 100));
        bottomLeftPanel.setBackground(Color.WHITE);

        numeroSocios.setBounds(30, 0, 450, 100); // Definir posição e tamanho da imagem
        countLabel.setBounds(260, 55, 100, 40); // Definir posição e tamanho do número, ajustar conforme necessário

        bottomLeftPanel.add(numeroSocios);
        bottomLeftPanel.add(countLabel);

        // Adiciona os componentes ao painel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomLeftPanel, BorderLayout.SOUTH);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }
}
