package org.example.Client.GUI;


import org.example.Game.TicTacToeGame;

import javax.swing.*;
import java.awt.*;


public class ComponentManager {
    private static final String MAIN_FRAME_NAME = "Tic Tac Toe";
    private static final Dimension MENU_BUTTON_PREFERRED_SIZE = new Dimension(200, 50);
    private static final Dimension MENU_PREFERRED_SIZE = new Dimension(400, 400);
    private static final Dimension BOARD_PREFERRED_SIZE = new Dimension(800, 800);
    private static final Dimension BOARD_FIELD_PREFERRED_SIZE = new Dimension(200, 200);

    public static class GUIBoard extends JPanel {
        static class GUIBoardField extends JPanel {
            private final int cordX;
            private final int cordY;

            private GUIBoardField(int x, int y) {
                super();
                this.setPreferredSize(BOARD_FIELD_PREFERRED_SIZE);
                this.cordX = x;
                this.cordY = y;
            }

            public void setField(final String content) {
                if (this.getComponentCount() == 0) {
                    this.add(new JTextField(content));
                }
            }

            public int getCordX() {
                return cordX;
            }

            public int getCordY() {
                return cordY;
            }
        }

        private final GUIBoardField[][] fields;

        public GUIBoard() {
            super();

            this.fields = new GUIBoardField[TicTacToeGame.Board.SIZE][TicTacToeGame.Board.SIZE];
            GUIBoardField field;
            for(int y = 0; y < TicTacToeGame.Board.SIZE; ++y) {
                for(int x = 0; x < TicTacToeGame.Board.SIZE; ++x) {
                    field = new GUIBoard.GUIBoardField(x, y);
                    field.addMouseListener(Listeners.BoardMouseListener());
                    this.add(field);
                    this.fields[y][x] = field;
                }
            }
            this.setLayout(new GridLayout(TicTacToeGame.Board.SIZE, TicTacToeGame.Board.SIZE, 40, 40));
            this.setBackground(Color.BLACK);
        }

        public void setField(final int x, final int y, final String content) {
            this.fields[y][x].setField(content);
            this.fields[y][x].revalidate();
            this.fields[y][x].repaint();
        }
    }

    private static class SingletonComponentInstances {
        public static JFrame mainFrame = null;
        public static JPanel mainMenu = null;
        public static GUIBoard board = null;
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

    public static void switchMainFrameContentPane(final JPanel newCP) {
        ComponentManager.MainFrame().setContentPane(newCP);
        ComponentManager.MainFrame().pack();
        ComponentManager.MainFrame().revalidate();
        ComponentManager.MainFrame().repaint();
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
        button.addActionListener(Listeners.PlayMenuButtonListener());
        return button;
    }

    public static JButton ExitMenuButton() {
        var button = MenuButton("EXIT");
        button.addActionListener(Listeners.ExitMenuButtonListener());
        return button;
    }

    public static JPanel WaitingPanel(final String prompt) {
        var panel = new JPanel();
        panel.setPreferredSize(MENU_PREFERRED_SIZE);
        panel.add(new JLabel(prompt));
        return panel;
    }

    public static GUIBoard Board() {
        if(SingletonComponentInstances.board == null) {
            SingletonComponentInstances.board = new GUIBoard();
        }
        return SingletonComponentInstances.board;
    }
}
