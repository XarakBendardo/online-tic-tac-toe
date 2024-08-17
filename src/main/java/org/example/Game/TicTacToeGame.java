package org.example.Game;

import java.util.Map;

public class TicTacToeGame {
    public static class Board {
        public static final int SIZE = 3;
        public enum Field {X, O, EMPTY}
        private static final Map<Field, Character> field2Char = Map.of(
                Field.X, 'X',
                Field.O, 'O',
                Field.EMPTY, ' '
        );
        Field[][] fields;

        public Board() {
            this.fields = new Field[][]{
                    {Field.EMPTY, Field.EMPTY, Field.EMPTY},
                    {Field.EMPTY, Field.EMPTY, Field.EMPTY},
                    {Field.EMPTY, Field.EMPTY, Field.EMPTY}
            };
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (var row : fields) {
                sb.append('|');
                for (var field : row) {
                    sb.append(field2Char.get(field)).append('|');
                }
                sb.append('\n');
            }
            return sb.toString();
        }

        public boolean isFieldFree(final int x, final int y) {
            return this.fields[y][x] == Field.EMPTY;
        }
        public void setField(final int x, final int y, final Field field) {
            this.fields[y][x] = field;
        }
    }
    private final Board board;
    public enum Turn {Player_X, Player_O}
    private Turn turn;

    public TicTacToeGame() {
        this.board = new Board();
        this.turn = Turn.Player_X;
    }

    public Turn getTurn() {return this.turn;}
    public void changeTurn() {this.turn = this.turn == Turn.Player_X ? Turn.Player_O : Turn.Player_X;}
    public boolean setField(final int x, final int y) {
        if(this.board.isFieldFree(x, y)) {
            this.board.setField(x, y, this.turn == Turn.Player_X ? Board.Field.X : Board.Field.O);
            return true;
        }
        return false;
    }
    public String getBoardAsString() {return this.board.toString();}
}
