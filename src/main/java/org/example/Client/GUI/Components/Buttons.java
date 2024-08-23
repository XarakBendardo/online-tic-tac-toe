package org.example.Client.GUI.Components;

import javax.swing.*;

/**
 * A factory class for creating customized buttons for main menu.
 */
public class Buttons {
    JButton of(final String text) {
        return new JButton(text);
    }
}
