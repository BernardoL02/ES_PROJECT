package org.example.FrontEnd.Socio;

import org.example.BackEnd.Configuracoes;
import org.example.BackEnd.Cota;
import org.example.BackEnd.Socio;
import org.example.BackEnd.TipoDeSocio;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdicionarSocio extends BasePage {
    private JTextField fieldNome;
    private JTextField fieldNif;
    private JTextField fieldEndereco;
    private JTextField fieldTelefone;
    private JTextField fieldEmail;
    private JComboBox<TipoDeSocio> tipoDeSocioComboBox;
    private JCheckBox pagoCheckBox;
    private Configuracoes configuracoes;

    public AdicionarSocio() {
        super("Adicionar Sócio", "/HeaderRegistarSocio.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirSocio();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, true);

        configuracoes = new Configuracoes();

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/DadosSocio.png"));
                Image image = backgroundImage.getImage();
                int margin_W = 80;
                int margin_H = 20;
                int imgWidth = getWidth() - 2 * margin_W;
                int imgHeight = getHeight() - 2 * margin_H;
                g.drawImage(image, margin_W, margin_H, imgWidth, imgHeight, this);

            }
        };

        backgroundPanel.setBackground(Color.WHITE);
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(null);

        fieldNome = new JTextField();
        fieldNome.setBounds(120, 130, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldNome.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldNome);

        fieldNif = new JTextField();
        fieldNif.setBounds(520, 130, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldNif.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldNif);

        fieldEndereco = new JTextField();
        fieldEndereco.setBounds(120, 250, 170, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldEndereco.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEndereco);

        fieldTelefone = new JTextField();
        fieldTelefone.setBounds(520, 250, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldTelefone.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldTelefone);

        fieldEmail = new JTextField();
        fieldEmail.setBounds(120, 370, 200, 30); // Posicionando a primeira caixa de texto com coordenadas absolutas
        fieldEmail.setHorizontalAlignment(SwingConstants.LEFT);
        backgroundPanel.add(fieldEmail);

        tipoDeSocioComboBox = new JComboBox<>(TipoDeSocio.values());
        tipoDeSocioComboBox.setBounds(520, 370, 200, 30);
        backgroundPanel.add(tipoDeSocioComboBox);

        pagoCheckBox = new JCheckBox("Pago");
        pagoCheckBox.setBounds(740, 370, 70, 30);
        pagoCheckBox.setBackground(new Color(0xDFF3FF));
        backgroundPanel.add(pagoCheckBox);

        wrapperPanel.add(backgroundPanel, BorderLayout.CENTER);

        RoundButton buttonGuardar = new RoundButton("Guardar");
        buttonGuardar.setBackground(new Color(0x99D4FF));
        buttonGuardar.setForeground(Color.BLACK);
        buttonGuardar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonGuardar.setBounds(750, 560, 160, 40);
        add(buttonGuardar);

        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende guardar os dados do sócio?", "Confirmação", "Cancelar", "Confirmar");

                if (response == JOptionPane.YES_OPTION) {
                    TipoDeSocio tipoDeSocio = (TipoDeSocio) tipoDeSocioComboBox.getSelectedItem();
                    double valorCota = configuracoes.getValorCota(tipoDeSocio);
                    Cota cota = new Cota(valorCota, pagoCheckBox.isSelected());
                    Socio socio = new Socio(
                            fieldNome.getText(),
                            fieldNif.getText(),
                            fieldEndereco.getText(),
                            fieldTelefone.getText(),
                            fieldEmail.getText(),
                            tipoDeSocio,
                            cota
                    );
                    GerirSocio.adicionarSocio(socio);
                    new GerirSocio();
                    dispose();
                }
            }
        });

        RoundButton buttonCancelar = new RoundButton("Cancelar");
        buttonCancelar.setBackground(new Color(0xBABABA));
        buttonCancelar.setForeground(Color.BLACK);
        buttonCancelar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonCancelar.setBounds(560, 560, 160, 40);
        add(buttonCancelar);

        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GerirSocio();
                dispose();
            }
        });

        add(wrapperPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}
