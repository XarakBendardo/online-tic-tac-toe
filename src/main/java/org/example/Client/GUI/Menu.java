package org.example.Client.GUI;

import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    public static final Dimension PREFERRED_SIZE = new Dimension(400, 400);
    private static Menu instance;
    private Menu() {
        super();
        this.setPreferredSize(PREFERRED_SIZE);

        this.add(new JButton("PLAY"));
        this.add(new JButton("EXIT"));
    }

    public static Menu getInstance() {
        if(instance == null)
            instance = new Menu();
        return instance;
    }
}
