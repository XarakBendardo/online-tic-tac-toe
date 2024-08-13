package org.example.Game;

import java.util.Map;

public class TicTacToeGame {
    public static class Board {
        public static final int SIZE = 3;
        public enum Field {X, O, EMPTY}
        static final Map<Field, Character> map = Map.of(
                Field.X, 'X',
                Field.O, 'O',
                Field.EMPTY, ' '
        );

        public enum Turn {Player_X, Player_O}
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
                for (var field : row) {
                    sb.append(map.get(field));
                }
                sb.append('\n');
            }
            return sb.toString();
        }

        public void setField(final int x, final int y, final Field field) {
            this.fields[y][x] = field;
        }
    }

    private final Board board;

    public TicTacToeGame() {
        this.board = new Board();
    }
}
