package org.example.Client.GUI;


import org.example.Game.TicTacToeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ComponentManager {
    private static final String MAIN_FRAME_NAME = "Tic Tac Toe";
    private static final Dimension MENU_BUTTON_PREFERRED_SIZE = new Dimension(200, 50);
    private static final Dimension MENU_PREFERRED_SIZE = new Dimension(400, 400);
    private static final Dimension BOARD_PREFERRED_SIZE = new Dimension(800, 800);
    private static final Dimension BOARD_FIELD_PREFERRED_SIZE = new Dimension(200, 200);

    private static class GUIBoard extends JPanel implements MouseListener {
        private static class GUIBoardField extends JPanel {
            private final int cordX;
            private final int cordY;
            private GUIBoardField(int x, int y) {
                super();
                this.setPreferredSize(BOARD_FIELD_PREFERRED_SIZE);
                this.cordX = x;
                this.cordY = y;
            }

            public void setField(TicTacToeGame.Turn turn) {
                if(this.getComponentCount() == 0) {
                    String content = turn == TicTacToeGame.Turn.Player_X ? "X" : "O";
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
        private final TicTacToeGame game;
        private GUIBoard() {
            super();
            this.game = new TicTacToeGame();

            GUIBoardField field;
            for(int y = 0; y < TicTacToeGame.Board.SIZE; ++y) {
                for(int x = 0; x < TicTacToeGame.Board.SIZE; ++x) {
                    field = new GUIBoardField(x, y);
                    field.addMouseListener(this);
                    this.add(field);
                }
            }
            this.setLayout(new GridLayout(TicTacToeGame.Board.SIZE, TicTacToeGame.Board.SIZE, 40, 40));
            this.setBackground(Color.BLACK);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getSource() instanceof GUIBoardField field) {
                if(this.game.setField(field.getCordX(), field.getCordY())) {
                    field.setField(this.game.getTurn());
                    field.revalidate();
                    field.repaint();

                    this.game.changeTurn();
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
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

    private static GUIBoard Board() {
        if(SingletonComponentInstances.board == null) {
            SingletonComponentInstances.board = new GUIBoard();
        }
        return SingletonComponentInstances.board;
    }
}
