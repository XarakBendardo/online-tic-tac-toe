package org.example.Client.GUI.Components;

import javax.swing.*;
import java.awt.*;

/**
 * A factory class for creating panels with text prompts for the user.
 */
public final class PromptPanel {
    public static final Dimension PREFERRED_SIZE = new Dimension(400, 100);

    /**
     * Create a new instance of the prompt panel with given text as contents.
     */
    public static JPanel of(String prompt) {
        var panel = new JPanel();
        panel.setPreferredSize(PREFERRED_SIZE);
        panel.add(new JLabel(prompt));
        return panel;
    }
}
