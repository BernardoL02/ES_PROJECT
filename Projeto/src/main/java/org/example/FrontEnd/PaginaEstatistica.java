package org.example;

import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.PopUpEstatistica;
import org.example.FrontEnd.Resources.RoundButton;
import org.example.FrontEnd.Socio.RequisicoesPorSocio;

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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel para o botão "Filtrar Por"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel do botão

        RoundButton buttonFiltrar = new RoundButton("Filtrar Por:");
        buttonFiltrar.setBackground(Color.LIGHT_GRAY);
        buttonFiltrar.setForeground(Color.BLACK);
        buttonFiltrar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonPanel.add(buttonFiltrar); // Adiciona o botão ao painel de botões
        mainPanel.add(buttonPanel, BorderLayout.NORTH); // Adiciona o painel de botões acima da tabela

        // Ação ao clicar no botão "Filtrar Por"
        buttonFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int response = PopUpEstatistica.showCustomConfirmDialog();

                // Verifica a resposta
                if (response == JOptionPane.YES_OPTION) {

                    dispose();
                }
            }
        });

        String[] columnNames = {" ", " ", " ", " ", " ", " "};
        Object[][] data = {
                {"#1", "A Formula de Deus", "Romance", "J. R dos Santos", "2024", "Português"},
                {"#2", "O Principezinho", "Infantil", "A. Saint-Exupéry", "2024", "Português"}
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
        table.getTableHeader().setBackground(new Color(0x99D4FF));
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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        JLabel numeroSocios = new JLabel(new ImageIcon(getClass().getResource("/SocioRequisicoes.png")));
        numeroSocios.setBackground(Color.WHITE);
        JLabel countLabel = new JLabel("" + countNaoPago);
        countLabel.setFont(new Font("Inter", Font.BOLD, 38));
        countLabel.setForeground(Color.BLACK);
        countLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0)); // Move 30px para a direita

        // Painel para posicionar a imagem e a contagem no canto inferior esquerdo
        JPanel bottomLeftPanel = new JPanel(null); // Usar layout nulo para posicionamento absoluto
        bottomLeftPanel.setPreferredSize(new Dimension(100, 100));
        bottomLeftPanel.setBackground(Color.WHITE);
        numeroSocios.setBounds(300, 0, 800, 100); // Definir posição e tamanho da imagem
        countLabel.setBounds(570, 55, 100, 40); // Definir posição e tamanho do número, ajustar conforme necessário


        bottomLeftPanel.add(numeroSocios);
        bottomLeftPanel.add(countLabel);


        // Adiciona o scrollPane com a tabela ao painel principal
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomLeftPanel, BorderLayout.SOUTH);
        // Adiciona o painel principal ao wrapperPanel
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }
}
