package org.example.FrontEnd.Livro;

import org.example.BackEnd.Livro;
import org.example.BackEnd.Requisitar;
import org.example.BackEnd.Socio;
import org.example.BackEnd.GerirRequisitar;
import org.example.FrontEnd.BiblioLiz;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;
import org.example.FrontEnd.Socio.RequisicoesPorSocio;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;

public class ConfirmaRequisicao extends BasePage {
    private Socio socio;
    private Livro livro;

    public ConfirmaRequisicao(Socio socio, Livro livro) {
        super("Confirmar Requisição", "/HeaderConfirmaRequisicao.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        this.socio = socio;
        this.livro = livro;

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

        String[] bookColumnNames = {"ISBN", "Título", "Localização", "Quantidade"};
        Object[][] bookData = {
                {livro.getIsbn(), livro.getTitulo(), livro.getLocalizacao(), livro.getQuantidade()}
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
        memberInfoPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20)); // Adiciona espaçamento à direita

        String[] memberColumnNames = {"Nif", "Nome", "Telefone", "Pago", "Tipo de Sócio"};
        Object[][] memberData = {
                {socio.getNif(), socio.getNome(), socio.getTelefone(), socio.getCota().isPago() ? "Sim" : "Não", socio.getTipoDeSocio().toString()}
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

        // Painel de datas customizado com imagem e labels
        JPanel datesPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon datesIcon = new ImageIcon(getClass().getResource("/Datas.png"));
                Image datesImage = datesIcon.getImage();
                g.drawImage(datesImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        datesPanel.setPreferredSize(new Dimension(220, 130));
        datesPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Adiciona margens ao painel

        // Add data labels on top of the dates panel
        JLabel date1Label = new JLabel(LocalDate.now().toString());
        date1Label.setFont(new Font("Inter", Font.BOLD, 18));
        date1Label.setBounds(53, 30, 180, 30);
        datesPanel.add(date1Label);

        JLabel date2Label = new JLabel(LocalDate.now().plusDays(30).toString());
        date2Label.setFont(new Font("Inter", Font.BOLD, 18));
        date2Label.setBounds(53, 95, 180, 30);
        datesPanel.add(date2Label);

        JLabel periodLabel = new JLabel("30 Dias");
        periodLabel.setFont(new Font("Inter", Font.BOLD, 18));
        periodLabel.setBounds(120, 153, 180, 30);
        datesPanel.add(periodLabel);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        RoundButton buttonCancelar = new RoundButton("Cancelar");
        buttonCancelar.setBackground(new Color(0xBABABA));
        buttonCancelar.setForeground(Color.BLACK);
        buttonCancelar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonCancelar.setBounds(500, 560, 160, 40);

        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RequisicoesPorSocio(socio);
                dispose(); // Fecha a janela principal
            }
        });

        RoundButton buttonGuardar = new RoundButton("Guardar");
        buttonGuardar.setBackground(new Color(0x99D4FF));
        buttonGuardar.setForeground(Color.BLACK);
        buttonGuardar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonGuardar.setBounds(690, 560, 160, 40);
        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exibe o diálogo de confirmação personalizado
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende guardar as alterações?", "Confirmação", "Cancelar", "Confirmar");

                // Verifica a resposta
                if (response == JOptionPane.YES_OPTION) {
                    // Guardar os dados
                    LocalDate dataRequisicao = LocalDate.now();
                    LocalDate dataDevolucaoPrevista = dataRequisicao.plusDays(30);
                    Requisitar requisitar = new Requisitar(socio, livro, dataRequisicao, dataDevolucaoPrevista);
                    GerirRequisitar.adicionarRequisicao(requisitar);

                    new RequisicoesPorSocio(socio);
                    dispose();
                }
            }
        });

        buttonPanel.add(buttonCancelar);
        buttonPanel.add(buttonGuardar);

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
}
