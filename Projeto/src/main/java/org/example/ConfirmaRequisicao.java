package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmaRequisicao extends BasePage {

    public ConfirmaRequisicao() {
        super("Confirmar Requisição", "/HeaderConfirmaRequisicao.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        JPanel wrapperPanel = new JPanel(new BorderLayout(10, 10));
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);
        wrapperPanel.setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de informação do livro
        JPanel bookInfoPanel = createRoundedPanel(new BorderLayout());
        bookInfoPanel.setBackground(Color.WHITE);

        String[] bookColumnNames = {"ISBN", "Título", "Localização", "Código Único"};
        Object[][] bookData = {
                {"78853330227", "A Fórmula de Deus", "2 - 10 - 1B", "10"}
        };
        JTable bookTable = createTable(bookData, bookColumnNames);
        bookTable.setRowHeight(30); // Ajusta a altura da linha da tabela de livro
        bookTable.setPreferredSize(new Dimension(bookTable.getPreferredSize().width, bookTable.getRowHeight() + bookTable.getTableHeader().getPreferredSize().height)); // Ajuste o tamanho da tabela de livro
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookScrollPane.setPreferredSize(bookTable.getPreferredSize());
        bookScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remover a borda preta
        bookScrollPane.getViewport().setBackground(Color.WHITE); // Define o fundo do viewport para branco

        bookInfoPanel.add(bookScrollPane, BorderLayout.CENTER);

        JLabel bookInfoLabel = new JLabel("Livro a Requisitar", SwingConstants.CENTER);
        bookInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        bookInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaçamento
        bookInfoPanel.add(bookInfoLabel, BorderLayout.NORTH);

        // Painel de informação do sócio
        JPanel memberInfoPanel = createRoundedPanel(new BorderLayout());
        memberInfoPanel.setBackground(Color.WHITE);

        String[] memberColumnNames = {"Nr", "Nome", "Nif", "Telefone", "Pago", "Tipo de Sócio"};
        Object[][] memberData = {
                {"1", "Bernardo Lopes", "252490132", "999999999", "Sim", "Entusiasta"}
        };
        JTable memberTable = createTable(memberData, memberColumnNames);
        memberTable.setRowHeight(30); // Ajusta a altura da linha da tabela de sócios
        memberTable.setPreferredSize(new Dimension(memberTable.getPreferredSize().width, memberTable.getRowHeight() + memberTable.getTableHeader().getPreferredSize().height)); // Ajuste o tamanho da tabela de sócios
        JScrollPane memberScrollPane = new JScrollPane(memberTable);
        memberScrollPane.setPreferredSize(memberTable.getPreferredSize());
        memberScrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remover a borda preta
        memberScrollPane.getViewport().setBackground(Color.WHITE); // Define o fundo do viewport para branco

        memberInfoPanel.add(memberScrollPane, BorderLayout.CENTER);

        JLabel memberInfoLabel = new JLabel("Sócio Associado", SwingConstants.CENTER);
        memberInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        memberInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Espaçamento
        memberInfoPanel.add(memberInfoLabel, BorderLayout.NORTH);

        // Painel de datas customizado
        JPanel datesPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        datesPanel.setBackground(new Color(0x6EC2FF));
        datesPanel.setBorder(BorderFactory.createLineBorder(new Color(0x6EC2FF), 10));
        datesPanel.setPreferredSize(new Dimension(200, 150));

        datesPanel.add(createDatePanel("09 - 05 - 2024"));
        datesPanel.add(createDatePanel("09 - 06 - 2024"));

        JPanel lastRowPanel = new JPanel(new BorderLayout());
        lastRowPanel.setBackground(new Color(0x6EC2FF));
        lastRowPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        lastRowPanel.add(createPeriodPanel("30 Dias"), BorderLayout.EAST);

        datesPanel.add(lastRowPanel);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        RoundButton cancelButton = new RoundButton("Cancelar");
        cancelButton.setBackground(Color.LIGHT_GRAY);
        cancelButton.setPreferredSize(new Dimension(120, 40));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                dispose();
            }
        });

        RoundButton saveButton = new RoundButton("Guardar");
        saveButton.setBackground(new Color(0x64B5F6));
        saveButton.setPreferredSize(new Dimension(120, 40));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implementar a ação de guardar
                JOptionPane.showMessageDialog(null, "Requisição guardada com sucesso!");
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        JPanel combinedPanel = new JPanel(new BorderLayout(10, 10));
        combinedPanel.setBackground(Color.WHITE);
        combinedPanel.add(memberInfoPanel, BorderLayout.CENTER);
        combinedPanel.add(datesPanel, BorderLayout.EAST);

        mainPanel.add(bookInfoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adiciona espaço entre os painéis
        mainPanel.add(combinedPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adiciona espaço antes dos botões
        mainPanel.add(buttonPanel);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        add(wrapperPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel createRoundedPanel(LayoutManager layout) {
        return new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                super.paintBorder(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
    }

    private JPanel createDatePanel(String dateText) {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        datePanel.setBackground(Color.WHITE);
        datePanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        ImageIcon icon = new ImageIcon(getClass().getResource("/IconData.png"));
        Image image = icon.getImage();
        Image newimg = image.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        JLabel iconLabel = new JLabel(icon);
        JLabel dateLabel = new JLabel(dateText);
        dateLabel.setFont(new Font("Inter", Font.BOLD, 16));

        datePanel.add(iconLabel);
        datePanel.add(dateLabel);

        return datePanel;
    }

    private JPanel createPeriodPanel(String periodText) {
        JPanel periodPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        periodPanel.setBackground(Color.WHITE);

        JLabel periodLabel = new JLabel(periodText);
        periodLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 16));

        periodPanel.add(periodLabel);

        return periodPanel;
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

    private JTable createTable(Object[][] data, String[] columnNames) {
        JTable table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Impede a edição das células
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
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 16));
        table.getTableHeader().setBackground(new Color(0x6EC2FF));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setFont(new Font("Inter", Font.PLAIN, 16));
        table.setGridColor(Color.WHITE);
        table.setShowGrid(true);
        table.getTableHeader().setReorderingAllowed(false);

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

        return table;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConfirmaRequisicao::new);
    }
}
