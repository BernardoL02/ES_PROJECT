package org.example;

import javax.swing.*;
import java.awt.*;

public class RoundButton extends JButton {

    public RoundButton(String label) {
        super(label);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        int arcSize = 20; // Ajusta o raio do arco para arredondar os cantos

        // Pinta o fundo do botão
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, width, height, arcSize, arcSize);

        // Pinta o texto do botão
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(getForeground());
        g2.drawString(getText(), (width - fm.stringWidth(getText())) / 2, (height + fm.getAscent()) / 2 - fm.getDescent());

        g2.dispose();
    }
}
