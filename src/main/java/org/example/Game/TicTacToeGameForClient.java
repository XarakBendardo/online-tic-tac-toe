package org.example.Game;

public class TicTacToeGameForClient extends TicTacToeGame {
    private final Turn playersSymbol;

    private static TicTacToeGameForClient instance = null;

    private TicTacToeGameForClient(final Turn playersSymbol) {
        this.playersSymbol = playersSymbol;
    }

    public Turn getPlayersSymbol() {
        return playersSymbol;
    }
    public Turn getOpponentsSymbol() {return playersSymbol == Turn.Player_X ? Turn.Player_O : Turn.Player_X; }

    public static void initInstance(final Turn playersSymbol) {
        if(instance == null)
            instance = new TicTacToeGameForClient(playersSymbol);
        System.out.println(playersSymbol);
    }

    public static TicTacToeGameForClient getInstance() {
        return instance;
    }
}
