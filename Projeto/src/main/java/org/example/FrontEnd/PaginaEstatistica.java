package org.example.FrontEnd;

import org.example.FrontEnd.Resources.BasePage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaginaEstatistica extends BasePage {
    public PaginaEstatistica(){

        super("Informações Estatistica", "/HeaderPaginaEstatistica.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BiblioLiz();
                ((JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource())).dispose();
            }
        }, false);

        setVisible(true);
    }

}
