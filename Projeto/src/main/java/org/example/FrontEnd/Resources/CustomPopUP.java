package org.example.FrontEnd.Resources;

import org.example.FrontEnd.Resources.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomPopUP {
    public static int showCustomConfirmDialog(String message, String title, String textoBotaoEsq, String textoBotaoDir) {
        JFrame parent = new JFrame();
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setSize(500, 200);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.WHITE);

        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        dialog.add(messageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        RoundButton noButton = new RoundButton(textoBotaoEsq);
        noButton.setBackground(new Color(0xBABABA));
        noButton.setForeground(Color.BLACK);
        noButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));

        RoundButton yesButton = new RoundButton(textoBotaoDir);
        yesButton.setBackground(new Color(0x99D4FF));
        yesButton.setForeground(Color.BLACK);
        yesButton.setFont(new Font("Inter", Font.BOLD | Font.ITALIC, 18));

        buttonPanel.add(noButton);
        buttonPanel.add(yesButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        final int[] response = {JOptionPane.NO_OPTION};
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response[0] = JOptionPane.YES_OPTION;
                dialog.dispose();
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response[0] = JOptionPane.NO_OPTION;
                dialog.dispose();
            }
        });

        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return response[0];
    }
}