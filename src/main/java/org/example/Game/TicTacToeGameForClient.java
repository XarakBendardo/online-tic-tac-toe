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

    public static void initInstance(final Turn playersSymbol) {
        if(instance == null)
            instance = new TicTacToeGameForClient(playersSymbol);
    }

    public static TicTacToeGameForClient getInstance() {
        return instance;
    }
}
