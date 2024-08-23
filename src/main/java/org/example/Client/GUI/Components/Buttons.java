package org.example.Client.GUI.Components;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * A factory class for creating customized buttons for main menu.
 */
public class Buttons {
    public static JButton of(final String text, final ActionListener listener) {
        var button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }
}
