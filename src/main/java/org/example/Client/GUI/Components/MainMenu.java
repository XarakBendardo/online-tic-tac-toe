package org.example.Client.GUI.Components;

import javax.swing.*;
import java.awt.*;

/**
 * Main menu of the game. It is always the first content pane of the mainFrame instance.
 */
public final class MainMenu extends JPanel {
    public static final Dimension PREFERRED_SIZE = new Dimension(400, 400);

    /**
     * Create a JPanel with fixed grid layout containing all buttons in a single column.
     */
    MainMenu(JButton... buttons) {
        super();
        this.setPreferredSize(PREFERRED_SIZE);
        for(final var button : buttons)
            this.add(button);
        var layout = new GridLayout(buttons.length, 1, 0, 50);
        this.setLayout(layout);
    }
}
