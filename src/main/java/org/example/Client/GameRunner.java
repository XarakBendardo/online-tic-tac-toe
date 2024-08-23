package org.example.Client;

import org.example.Client.GUI.ComponentManager;

import javax.swing.*;

public class GameRunner {
    private final JFrame mainFrame;

    public GameRunner() {
        this.mainFrame = ComponentManager.MainFrame();
    }

    public final void runGame() {
        this.mainFrame.setVisible(true);
    }
}
