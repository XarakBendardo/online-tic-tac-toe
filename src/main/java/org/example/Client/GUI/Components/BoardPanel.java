package org.example.Client.GUI.Components;

import org.example.Game.TicTacToeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * A graphical representation of a Tic Tac Toe board.
 */
public final class BoardPanel extends JPanel {
    /**
     * A graphical representation of a Tic Tac Toe field, in which X and O symbols can be put.
     */
    public static final class BoardPanelField extends JPanel {
        public static final Dimension PREFERRED_SIZE = new Dimension(200, 200);
        public static final int FONT_SIZE = 100;
        private final int x, y;

        /**
         * @param cordX - x coordinate on 3x3 Tic Tac Toe board
         * @param cordY - y coordinate on 3x3 Tic Tac Toe board
         */
        private BoardPanelField(final int cordX, final int cordY) {
            super();
            this.setPreferredSize(PREFERRED_SIZE);
            this.setLayout(new GridBagLayout());

            this.x = cordX;
            this.y = cordY;
        }

        public int getCordX() {
            return x;
        }

        public int getCordY() {
            return y;
        }
    }

    private static JLabel BoardPanelFieldContent(final String text) {
        var content = new JLabel(text);
        var font = content.getFont();
        content.setFont(new Font(font.getName(), font.getStyle(), BoardPanelField.FONT_SIZE));
        return content;
    }

    private final BoardPanelField[][] fields;

    public BoardPanel() {
        super();

        this.fields = new BoardPanelField[TicTacToeGame.Board.SIZE][TicTacToeGame.Board.SIZE];
        for(int y = 0; y < TicTacToeGame.Board.SIZE; ++y) {
            for(int x = 0; x < TicTacToeGame.Board.SIZE; ++x) {
                this.fields[y][x] = new BoardPanelField(x, y);
                this.add(this.fields[y][x]);
            }
        }
        this.setLayout(new GridLayout(TicTacToeGame.Board.SIZE, TicTacToeGame.Board.SIZE, 40, 40));
        this.setBackground(Color.BLACK);
    }

    /**
     * Adds given mouse listener to each field. Used for putting contents on user's click.
     */
    public void applyMouseListener(MouseListener listener) {
        for(var row : this.fields) {
            for(var field : row) {
                field.addMouseListener(listener);
            }
        }
    }

    /**
     * Puts a JLabel with given content in board panel field with given coordinates
     */
    public void setField(final int x, final int y, final String text) {
        this.fields[y][x].add(BoardPanelFieldContent(text));
        this.fields[y][x].revalidate();
        this.fields[y][x].repaint();
    }
}
