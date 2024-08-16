package org.example.Client.GUI;

import javax.swing.*;

import java.awt.*;

public class MainFrame extends JFrame {
    private static final String MAIN_FRAME_NAME = "Tic Tac Toe";
    private static MainFrame instance = null;

    private MainFrame() {
        super(MAIN_FRAME_NAME);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(
                (Toolkit.getDefaultToolkit().getScreenSize().width - WIDTH) / 2,
                (Toolkit.getDefaultToolkit().getScreenSize().height - HEIGHT) / 2,
                this.getWidth(),
                this.getHeight());
        this.setContentPane(Menu.getInstance());
        this.setResizable(false);
        this.pack();
    }

    public static MainFrame getInstance() {
        if(instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }
}
