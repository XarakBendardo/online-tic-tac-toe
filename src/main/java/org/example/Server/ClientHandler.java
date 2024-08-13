package org.example.Server;

import org.example.Game.TicTacToeGame;
import org.example.Networking.Command;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private TicTacToeGame.Board.Turn turn;
    private final Socket socket_X, socket_O;
    private final BufferedReader in_X, in_O;
    private final BufferedWriter out_X, out_O;

    public ClientHandler(Socket socket_X, Socket socket_O) throws IOException {
        this.socket_X = socket_X;
        this.socket_O = socket_O;
        this.turn = TicTacToeGame.Board.Turn.Player_X;

        BufferedReader tempIn_X = null, tempIn_O = null;
        BufferedWriter tempOut_X = null, tempOut_O;

        try {
            tempIn_X = new BufferedReader(new InputStreamReader(socket_X.getInputStream()));
            tempIn_O = new BufferedReader(new InputStreamReader(socket_O.getInputStream()));
            tempOut_X = new BufferedWriter(new OutputStreamWriter(socket_X.getOutputStream()));
            tempOut_O = new BufferedWriter(new OutputStreamWriter(socket_O.getOutputStream()));

            this.in_X = tempIn_X;
            this.out_X = tempOut_X;
            this.in_O = tempIn_O;
            this.out_O = tempOut_O;
        } catch (IOException e) {
            try {
                this.socket_X.close();
                this.socket_O.close();
                if (tempIn_X != null) tempIn_X.close();
                if (tempIn_O != null) tempIn_O.close();
                if (tempOut_X != null) tempOut_X.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void run() {
        try {
            if (this.socket_X == null || this.socket_O == null) {
                throw new IllegalStateException("Both sockets must be registered before starting the game.");
            }
            System.out.println("Game started");
            this.sendCommand(this.out_X, Command.SERVER_START_GAME);
            this.sendCommand(this.out_O, Command.SERVER_START_GAME);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.socket_X != null) this.socket_X.close();
                if (this.socket_O != null) this.socket_O.close();
                if (this.in_X != null) this.in_X.close();
                if (this.in_O != null) this.in_O.close();
                if (this.out_X != null) this.out_X.close();
                if (this.out_O != null) this.out_O.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendCommand(BufferedWriter out, String command) {
        try {
            out.write(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
