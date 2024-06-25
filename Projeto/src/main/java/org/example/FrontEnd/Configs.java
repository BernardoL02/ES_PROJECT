package org.example.FrontEnd;

import org.example.BackEnd.Configuracoes;
import org.example.FrontEnd.Livro.GerirLivros;
import org.example.FrontEnd.Resources.BasePage;
import org.example.FrontEnd.Resources.CustomPopUP;
import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.text.NumberFormatter;

public class Configs extends BasePage {
    private Configuracoes configuracoes;

    public Configs() {
        super("Configuracoes", "/HeaderConfiguracoes.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        configuracoes = new Configuracoes();

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/DadosConfiguracoes.png"));
                Image image = backgroundImage.getImage();
                int margin_W = 100;
                int margin_H = 80;
                int imgWidth = getWidth() - 2 * margin_W;
                int imgHeight = getHeight() - margin_H;
                int startY = getHeight() - imgHeight - margin_H;
                g.drawImage(image, margin_W, startY, imgWidth, imgHeight, this);
            }
        };
        backgroundPanel.setBackground(Color.WHITE);
        backgroundPanel.setOpaque(true);
        backgroundPanel.setLayout(null);

        NumberFormatter formatter = new NumberFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text == null || text.trim().isEmpty()) {
                    return null;
                }
                return super.stringToValue(text);
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                if (value == null) {
                    return "";
                }
                return super.valueToString(value);
            }
        };
        formatter.setValueClass(Integer.class);
        formatter.setCommitsOnValidEdit(true);
        formatter.setAllowsInvalid(true);
        formatter.setMaximum(999);

        JTextField diasLeitor = new JFormattedTextField(formatter);
        diasLeitor.setText(configuracoes.getProperty("diasLeitor"));
        diasLeitor.setBounds(265, 123, 50, 25);
        diasLeitor.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(diasLeitor);

        JTextField diasAcademico = new JFormattedTextField(formatter);
        diasAcademico.setText(configuracoes.getProperty("diasAcademico"));
        diasAcademico.setBounds(265, 160, 50, 25);
        diasAcademico.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(diasAcademico);

        JTextField diasEntusiasta = new JFormattedTextField(formatter);
        diasEntusiasta.setText(configuracoes.getProperty("diasEntusiasta"));
        diasEntusiasta.setBounds(265, 198, 50, 25);
        diasEntusiasta.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(diasEntusiasta);

        JTextField cotasLeitor = new JFormattedTextField(formatter);
        cotasLeitor.setText(configuracoes.getProperty("cotasLeitor"));
        cotasLeitor.setBounds(460, 123, 75, 25);
        cotasLeitor.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(cotasLeitor);

        JTextField cotasAcademico = new JFormattedTextField(formatter);
        cotasAcademico.setText(configuracoes.getProperty("cotasAcademico"));
        cotasAcademico.setBounds(460, 160, 75, 25);
        cotasAcademico.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(cotasAcademico);

        JTextField cotasEntusiasta = new JFormattedTextField(formatter);
        cotasEntusiasta.setText(configuracoes.getProperty("cotasEntusiasta"));
        cotasEntusiasta.setBounds(460, 198, 75, 25);
        cotasEntusiasta.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(cotasEntusiasta);

        JTextField maxLivros = new JFormattedTextField(formatter);
        maxLivros.setText(configuracoes.getProperty("maxLivros"));
        maxLivros.setBounds(145, 290, 60, 30);
        maxLivros.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(maxLivros);

        JTextField danificacaoLivro = new JFormattedTextField(formatter);
        danificacaoLivro.setText(configuracoes.getProperty("danificacaoLivro"));
        danificacaoLivro.setBounds(370, 290, 60, 30);
        danificacaoLivro.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(danificacaoLivro);

        JTextField multa = new JFormattedTextField(formatter);
        multa.setText(configuracoes.getProperty("multa"));
        multa.setBounds(145, 375, 60, 30);
        multa.setHorizontalAlignment(SwingConstants.CENTER);
        backgroundPanel.add(multa);

        wrapperPanel.add(backgroundPanel, BorderLayout.CENTER);

        RoundButton buttonGuardar = new RoundButton("Guardar");
        buttonGuardar.setBackground(new Color(0x99D4FF));
        buttonGuardar.setForeground(Color.BLACK);
        buttonGuardar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonGuardar.setBounds(685, 560, 160, 40);
        add(buttonGuardar);

        buttonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int response = CustomPopUP.showCustomConfirmDialog("Tem a certeza que pretende guardar os dados do livro?", "Confirmação", "Cancelar", "Confirmar");

                if (response == JOptionPane.YES_OPTION) {
                    configuracoes.setProperty("diasLeitor", diasLeitor.getText());
                    configuracoes.setProperty("diasAcademico", diasAcademico.getText());
                    configuracoes.setProperty("diasEntusiasta", diasEntusiasta.getText());
                    configuracoes.setProperty("cotasLeitor", cotasLeitor.getText());
                    configuracoes.setProperty("cotasAcademico", cotasAcademico.getText());
                    configuracoes.setProperty("cotasEntusiasta", cotasEntusiasta.getText());
                    configuracoes.setProperty("maxLivros", maxLivros.getText());
                    configuracoes.setProperty("danificacaoLivro", danificacaoLivro.getText());
                    configuracoes.setProperty("multa", multa.getText());
                    configuracoes.saveProperties();

                    new GerirLivros();
                    dispose();
                }
            }
        });

        RoundButton buttonCancelar = new RoundButton("Cancelar");
        buttonCancelar.setBackground(new Color(0xBABABA));
        buttonCancelar.setForeground(Color.BLACK);
        buttonCancelar.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));
        buttonCancelar.setBounds(495, 560, 160, 40);
        add(buttonCancelar);

        buttonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                dispose();
            }
        });

        add(wrapperPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Configs::new);
    }
}
