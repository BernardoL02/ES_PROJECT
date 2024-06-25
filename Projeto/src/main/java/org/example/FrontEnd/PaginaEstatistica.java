package org.example;

import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaEstatistica extends BasePage {
    public PaginaEstatistica() {

        super("Informações Estatistica", "/HeaderPaginaEstatistica.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        JPanel wrapperPanel = new JPanel(new BorderLayout());

        // Adiciona o headerPanel ao wrapperPanel
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel para o conteúdo principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel principal

        // Painel para o botão "Adicionar"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel do botão

        RoundButton buttonFiltrar = new RoundButton("Filtro Por:");
        buttonFiltrar.setBackground(Color.LIGHT_GRAY);
        buttonFiltrar.setForeground(Color.BLACK);
        buttonFiltrar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonPanel.add(buttonFiltrar); // Adiciona o botão ao painel de botões

        // Adiciona o painel de botões acima da tabela
        mainPanel.add(buttonPanel, BorderLayout.NORTH);

        String[] columnNames = {" ", "", "", "", "", ""};
        Object[][] data = {
                {"#1", "A Formula de Deus", "Romance", "J. R dos Santos", "2024", "Portugues"},
                {"#2", "O Principezinho", "Infantil", "A. Saint-Exupéry", "2024", "Portugues"}
        };
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
        table.getTableHeader().setBackground(new Color(0x99D4FF));
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

        table.getTableHeader().setResizingAllowed(true);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        // Adiciona o scrollPane com a tabela ao painel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Adiciona o painel principal ao wrapperPanel
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }
}
