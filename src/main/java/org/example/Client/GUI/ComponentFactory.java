package org.example.Client.GUI;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ComponentFactory {
    private static final String MAIN_FRAME_NAME = "Tic Tac Toe";

    private static final Dimension MENU_BUTTON_PREFERRED_SIZE = new Dimension(200, 50);
    private static final Dimension MENU_PREFERRED_SIZE = new Dimension(400, 400);
    public static final Dimension BOARD_PREFERRED_SIZE = new Dimension(800, 800);

    private static class SingletonComponentInstances {
        public static JFrame mainFrame = null;
        public static JPanel mainMenu = null;
        public static JPanel board = null;
    }

    public static JFrame MainFrame() {
        if(SingletonComponentInstances.mainFrame == null) {
            SingletonComponentInstances.mainFrame = new JFrame(MAIN_FRAME_NAME);
            SingletonComponentInstances.mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            SingletonComponentInstances.mainFrame.setContentPane(MainMenu());
            SingletonComponentInstances.mainFrame.setResizable(false);
            SingletonComponentInstances.mainFrame.pack();
        }
        return SingletonComponentInstances.mainFrame;
    }

    public static JPanel MainMenu() {
        if(SingletonComponentInstances.mainMenu == null) {
            SingletonComponentInstances.mainMenu = new JPanel();
            SingletonComponentInstances.mainMenu.setPreferredSize(MENU_PREFERRED_SIZE);
            SingletonComponentInstances.mainMenu.add(PlayMenuButton());
            SingletonComponentInstances.mainMenu.add(ExitMenuButton());
            var layout = new GridLayout(SingletonComponentInstances.mainMenu.getComponentCount(), 1, 0, 50);
            SingletonComponentInstances.mainMenu.setLayout(layout);
        }
        return SingletonComponentInstances.mainMenu;
    }
    public static JButton MenuButton(String text) {
        var button = new JButton(text);
        button.setPreferredSize(MENU_BUTTON_PREFERRED_SIZE);
        return button;
    }

    public static JButton PlayMenuButton() {
        var button = MenuButton("PLAY");
        button.addActionListener(e -> {
            MainFrame().setContentPane(Board());
            MainFrame().pack();
            MainFrame().revalidate();
            MainFrame().repaint();
        });
        return button;
    }

    public static JButton ExitMenuButton() {
        var button = MenuButton("EXIT");
        button.addActionListener(e -> MainFrame().dispose());
        return button;
    }

    public static JPanel Board() {
        if(SingletonComponentInstances.board == null) {
            // Image for background
            final BufferedImage backgroundImg;
            BufferedImage tmpImg;
            try {
                tmpImg = ImageIO.read(new File("C:\\Users\\Marcin\\IdeaProjects\\online-tic-tac-toe\\src\\main\\resources\\Board Sprite.png"));
            } catch (IOException e) {
                e.printStackTrace();
                tmpImg = null;
            }
            backgroundImg = tmpImg;
            SingletonComponentInstances.board = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    if(backgroundImg != null)
                        g.drawImage(backgroundImg, 0, 0, null);
                }
            };
            SingletonComponentInstances.board.setPreferredSize(BOARD_PREFERRED_SIZE);
            SingletonComponentInstances.board.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(e.getX());
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        return SingletonComponentInstances.board;
    }
}
