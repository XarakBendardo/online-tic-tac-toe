package org.example.Client;


import org.example.Client.GUI.ComponentManager;

public class Client {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        ComponentManager.MainFrame().setVisible(true);
    }
}
