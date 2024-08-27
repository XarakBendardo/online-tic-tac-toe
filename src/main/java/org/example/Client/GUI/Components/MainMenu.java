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
    public MainMenu(JButton... buttons) {
        super();

        this.setLayout(new BorderLayout());

        JPanel contentHolder = new JPanel();
        contentHolder.setLayout(new GridLayout(buttons.length, 1, 0, 50));

        this.add(SideSpacer(), BorderLayout.EAST);
        this.add(SideSpacer(), BorderLayout.WEST);
        this.add(UpDownSpacer(), BorderLayout.NORTH);
        this.add(UpDownSpacer(), BorderLayout.SOUTH);

        this.setPreferredSize(PREFERRED_SIZE);
        for(final var button : buttons)
            contentHolder.add(button, BorderLayout.NORTH);
        this.add(contentHolder, BorderLayout.CENTER);
    }

    private static JPanel SideSpacer() {
        var spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(100, 0));
        return spacer;
    }

    private static JPanel UpDownSpacer() {
        var spacer = new JPanel();
        spacer.setPreferredSize(new Dimension(0, 100));
        return spacer;
    }
}
