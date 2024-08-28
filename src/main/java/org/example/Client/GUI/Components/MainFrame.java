package org.example.Client.GUI.Components;

import javax.swing.*;
import java.awt.*;

/**
 Main frame of the program.
 */
public final class MainFrame extends JFrame {
    private static final String TITLE = "Tic Tac Toe";
    private final JLabel prompt;
    private final JPanel contentPane;

    /**
     * Create a new instance of the class with given content pane. Actually, it is always a MainMenu instance, but passed
     * as an argument for dependency injection.
     */
    public MainFrame(final JPanel contentPane) {
        super(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(new BorderLayout());

        this.prompt = new JLabel();

        var promptHolder = new JPanel();
        promptHolder.setLayout(new GridBagLayout());
        promptHolder.add(this.prompt);
        this.add(promptHolder, BorderLayout.NORTH);

        this.contentPane = new JPanel();
        this.contentPane.add(contentPane);

        this.add(this.contentPane, BorderLayout.CENTER);
        this.setResizable(false);
        this.pack();
    }

    /**
     * Replace current content pane new one. Automatically revalidates and repaints the frame.
     */
    @Override
    public void setContentPane(Container contentPane) {
        this.contentPane.removeAll();
        this.contentPane.add(contentPane);
        this.pack();
        this.revalidate();
        this.repaint();
    }

    /**
     * Set new text of a prompt.
     */
    public void setPrompt(final String prompt) {
        this.prompt.setText(prompt);
    }
}
