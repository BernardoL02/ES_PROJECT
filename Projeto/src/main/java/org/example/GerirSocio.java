package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GerirSocio extends BasePage {

    private ArrayList<Socio> socios;

    public GerirSocio(ArrayList<Socio> socios) {
        super("Gerir Sócio", "/HeaderGerirSocio.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, true);

        this.socios = socios;

        // Painel principal para centralizar verticalmente
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        // Painel para o conteúdo principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel principal

        // Painel para o botão "Adicionar"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(0xFFFFFF)); // Cor do fundo do painel do botão

        // Adicionar botão "Adicionar"
        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.setBackground(new Color(0x46AEB5));
        adicionarButton.setForeground(Color.WHITE); // Definindo a cor do texto como branco
        adicionarButton.setOpaque(true);
        adicionarButton.setBorderPainted(false); // Removendo a borda
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdicionarSocio();
                dispose(); // Fecha a janela principal
            }
        });

        buttonPanel.add(adicionarButton);

        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"Nome", "Email", "Telefone", "Morada", "Tipo de Sócio", "Pago", " "}, 0);

        // Adiciona dados à tabela
        adicionarDadosNaTabela(tableModel);

        // Criando a tabela com o modelo
        JTable table = new JTable(tableModel);

        // Configurando a cor de fundo e a cor do texto da tabela
        table.setBackground(new Color(0x6EC2FF));
        table.setForeground(Color.WHITE);

        // Configurando a cor das linhas da tabela
        table.setGridColor(Color.WHITE); // Cor das linhas da tabela
        table.setShowGrid(true);

        // Centralizando o texto na tabela
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Configurando o cabeçalho da tabela
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(0x6EC2FF)); // Cor de fundo do cabeçalho
        header.setForeground(Color.BLACK); // Cor do texto do cabeçalho
        header.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Borda do cabeçalho
        Font headerFont = header.getFont();
        header.setFont(headerFont.deriveFont(Font.BOLD)); // Define o texto em negrito

        // Adicionando a tabela em um JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE); // Cor de fundo do viewport do scrollPane

        // Adiciona os componentes ao painel principal
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Adiciona o wrapperPanel ao frame
        add(wrapperPanel, BorderLayout.CENTER);

        // Torna o frame visível
        setVisible(true);
    }

    private void adicionarDadosNaTabela(DefaultTableModel tableModel) {
        // Exemplo de sócios para adicionar à tabela

        // Cria um ícone a partir do caminho
        for (Socio socio : socios) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/EditButton.png"));
            Image image = icon.getImage();
            Object[] rowData = {socio.getNome(), socio.getEmail(), socio.getTelefone(), socio.getMorada(), socio.getTipoDeSocio(), socio.isPago(), image};
            tableModel.addRow(rowData);
        }
    }
}
