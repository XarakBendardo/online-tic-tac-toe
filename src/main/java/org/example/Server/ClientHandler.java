package org.example.Server;

import org.example.Game.TicTacToeGame;

import java.net.Socket;

public class ClientHandler extends Thread {
    private TicTacToeGame.Board.Turn turn;
    private final Socket socket_X;
    private final Socket socket_O;
    @Override
    public void run() {
        try {
            if (this.socket_X == null || this.socket_O == null) {
                throw new IllegalStateException("Both sockets must be registered before starting the game.");
            }
            System.out.println("Game started");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.socket_X != null) this.socket_X.close();
                if (this.socket_O != null) this.socket_O.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ClientHandler(Socket socket_X, Socket socket_O) {
        this.socket_X = socket_X;
        this.socket_O = socket_O;
        this.turn = TicTacToeGame.Board.Turn.Player_X;
    }
}
