package org.example.Client.GUI.Components;

import javax.swing.*;

/**
 Main frame of the program.
 */
public final class MainFrame extends JFrame {
    private static final String TITLE = "Tic Tac Toe";

    /**
     * Create a new instance of the class with given content pane. Actually, it is always a MainMenu instance, but passed
     * as an argument for dependency injection.
     */
    public MainFrame(final JPanel contentPane) {
        super(TITLE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(contentPane);
        this.setResizable(false);
        this.pack();
    }

    /**
     * Replace current content pane new one. Automatically revalidates and repaints the frame.
     */
    public void changeContentPane(final JPanel contentPane) {
        this.setContentPane(contentPane);
        this.revalidate();
        this.repaint();
    }
}
