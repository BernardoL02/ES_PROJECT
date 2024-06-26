package org.example.FrontEnd.Livro;

import org.example.BackEnd.Livro;
import org.example.BackEnd.Reserva;
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

public class ConfirmaReserva extends BasePage {
    private Socio socio;
    private Livro livro;

    public ConfirmaReserva(Socio socio, Livro livro) {
        super("Confirmar Reserva", "/HeaderConfirmarReserva.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RequisicoesPorSocio(socio);
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
        bookTable.setRowHeight(30);
        bookTable.setPreferredSize(new Dimension(bookTable.getPreferredSize().width, bookTable.getRowHeight() + bookTable.getTableHeader().getPreferredSize().height));
        JScrollPane bookScrollPane = new JScrollPane(bookTable);
        bookScrollPane.setPreferredSize(bookTable.getPreferredSize());
        bookScrollPane.setBorder(BorderFactory.createEmptyBorder());
        bookScrollPane.getViewport().setBackground(Color.WHITE);

        bookInfoPanel.add(bookScrollPane, BorderLayout.CENTER);

        JLabel bookInfoLabel = new JLabel("Livro a Reservar", SwingConstants.CENTER);
        bookInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        bookInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        bookInfoPanel.add(bookInfoLabel, BorderLayout.NORTH);

        // Painel de informação do sócio
        JPanel memberInfoPanel = createRoundedPanel(new BorderLayout());
        memberInfoPanel.setBackground(Color.WHITE);

        String[] memberColumnNames = {"Nif", "Nome", "Telefone", "Pago", "Tipo de Sócio"};
        Object[][] memberData = {
                {socio.getNif(), socio.getNome(), socio.getTelefone(), socio.getCota().isPago() ? "Sim" : "Não", socio.getTipoDeSocio().toString()}
        };
        JTable memberTable = createTable(memberData, memberColumnNames);
        memberTable.setRowHeight(30);
        memberTable.setPreferredSize(new Dimension(memberTable.getPreferredSize().width, memberTable.getRowHeight() + memberTable.getTableHeader().getPreferredSize().height));
        JScrollPane memberScrollPane = new JScrollPane(memberTable);
        memberScrollPane.setPreferredSize(memberTable.getPreferredSize());
        memberScrollPane.setBorder(BorderFactory.createEmptyBorder());
        memberScrollPane.getViewport().setBackground(Color.WHITE);

        memberInfoPanel.add(memberScrollPane, BorderLayout.CENTER);

        JLabel memberInfoLabel = new JLabel("Sócio Associado", SwingConstants.CENTER);
        memberInfoLabel.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        memberInfoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        memberInfoPanel.add(memberInfoLabel, BorderLayout.NORTH);

        // Painel de "Número de Reservas"
        int numeroReservas = GerirRequisitar.contarReservasPorLivro(livro);
        JPanel numberOfReservationsPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon(getClass().getResource("/number_of_reservations_background.png"));
                Image image = icon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        numberOfReservationsPanel.setPreferredSize(new Dimension(300, 50));
        numberOfReservationsPanel.setBackground(Color.WHITE);

        JLabel numberOfReservationsLabel = new JLabel("Número de Reservas: " + numeroReservas);
        numberOfReservationsLabel.setFont(new Font("Inter", Font.BOLD, 18));
        numberOfReservationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberOfReservationsPanel.add(numberOfReservationsLabel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(numberOfReservationsPanel, BorderLayout.NORTH);
        rightPanel.setBounds(70, 500, 300, 50);

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
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende cancelar?", "Confirmação", "Nao", "Sim");
                // Verifica a resposta
                if (response == JOptionPane.YES_OPTION) {
                    new RequisicoesPorSocio(socio);
                    dispose();
                }

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
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende guardar a reserva?", "Confirmação", "Cancelar", "Confirmar");
                if (response == JOptionPane.YES_OPTION) {
                    Reserva reserva = new Reserva(socio, livro, LocalDate.now());
                    GerirRequisitar.adicionarReserva(reserva);
                    new RequisicoesPorSocio(socio);
                    dispose();
                }
            }
        });
        add(rightPanel);

        buttonPanel.add(buttonCancelar);
        buttonPanel.add(buttonGuardar);

        JPanel combinedPanel = new JPanel(new BorderLayout(10, 10));
        combinedPanel.setBackground(Color.WHITE);
        combinedPanel.add(memberInfoPanel, BorderLayout.CENTER);

        mainPanel.add(bookInfoPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(combinedPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
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
                return false;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (component instanceof JComponent) {
                    ((JComponent) component).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
                    component.setBackground(new Color(0xDFF3FF));
                    ((JComponent) component).setOpaque(true);
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
        centerRenderer.setOpaque(true);
        centerRenderer.setBackground(new Color(0x6EC2FF));
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
