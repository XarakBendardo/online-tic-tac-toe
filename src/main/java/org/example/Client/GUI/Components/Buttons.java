package org.example.Client.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * A factory class for creating customized buttons for main menu.
 */
public class Buttons {
    private static final Font FONT = new Font("Arial", Font.PLAIN, 50);
    public static JButton of(final String text, final ActionListener listener) {
        var button = new JButton(text);
        button.setFont(FONT);
        button.addActionListener(listener);
        return button;
    }
}
