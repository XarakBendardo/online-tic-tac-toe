package org.example.Server;

import org.example.Game.TicTacToeGame;
import org.example.Networking.Command;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final TicTacToeGame.Board board;
    private TicTacToeGame.Board.Turn turn;
    private final Socket activePlayerSocket, inactivePlayerSocket;
    private BufferedReader activeIn, inactiveIn;
    private BufferedWriter activeOut, inactiveOut;

    public ClientHandler(Socket activePlayerSocket, Socket inactivePlayerSocket) throws IOException {
        this.activePlayerSocket = activePlayerSocket;
        this.inactivePlayerSocket = inactivePlayerSocket;
        this.turn = TicTacToeGame.Board.Turn.Player_X;
        this.board = new TicTacToeGame.Board();

        BufferedReader tempIn_X = null, tempIn_O = null;
        BufferedWriter tempOut_X = null, tempOut_O;

        try {
            tempIn_X = new BufferedReader(new InputStreamReader(activePlayerSocket.getInputStream()));
            tempIn_O = new BufferedReader(new InputStreamReader(inactivePlayerSocket.getInputStream()));
            tempOut_X = new BufferedWriter(new OutputStreamWriter(activePlayerSocket.getOutputStream()));
            tempOut_O = new BufferedWriter(new OutputStreamWriter(inactivePlayerSocket.getOutputStream()));

            this.activeIn = tempIn_X;
            this.activeOut = tempOut_X;
            this.inactiveIn = tempIn_O;
            this.inactiveOut = tempOut_O;
        } catch (IOException e) {
            try {
                this.activePlayerSocket.close();
                this.inactivePlayerSocket.close();
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
            if (this.activePlayerSocket == null || this.inactivePlayerSocket == null) {
                throw new IllegalStateException("Both sockets must be registered before starting the game.");
            }
            System.out.println("Game started");
            this.sendCommand(this.activeOut, Command.SERVER_START_GAME);
            this.sendCommand(this.inactiveOut, Command.SERVER_START_GAME);
            while(true) {
                this.processTurn();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (this.activePlayerSocket != null) this.activePlayerSocket.close();
                if (this.inactivePlayerSocket != null) this.inactivePlayerSocket.close();
                if (this.activeIn != null) this.activeIn.close();
                if (this.inactiveIn != null) this.inactiveIn.close();
                if (this.activeOut != null) this.activeOut.close();
                if (this.inactiveOut != null) this.inactiveOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processTurn() {
        try {
            String command = this.activeIn.readLine();
            if(command == null) {
                throw new IOException("Connection closed");
            }
            switch (command) {
                case Command.CLIENT_MOVE -> {
                    String move = this.activeIn.readLine();
                    if (move == null) {
                        throw new IOException("Connection closed");
                    }
                    this.board.setField(
                            Integer.parseInt(move.substring(0, 1)),
                            Integer.parseInt(move.substring(1, 2)),
                            this.turn == TicTacToeGame.Board.Turn.Player_X ? TicTacToeGame.Board.Field.X : TicTacToeGame.Board.Field.O);
                    this.sendCommand(this.activeOut, Command.SERVER_OK);
                    this.sendCommand(this.inactiveOut, Command.CLIENT_MOVE, move);
                }
                default -> throw new IOException("Invalid command: " + command);
            }
            this.changeTurn();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void changeTurn() {
        var tempIn = this.activeIn;
        var tempOut = this.activeOut;
        this.activeIn = this.inactiveIn;
        this.inactiveIn = tempIn;
        this.activeOut = this.inactiveOut;
        this.inactiveOut = tempOut;
        this.turn = this.turn == TicTacToeGame.Board.Turn.Player_X ? TicTacToeGame.Board.Turn.Player_O : TicTacToeGame.Board.Turn.Player_X;
    }

    private void sendCommand(BufferedWriter out, String command) {
        try {
            out.write(command);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(BufferedWriter out, String command, String arg) {
        try {
            out.write(command);
            out.newLine();
            out.write(arg);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
