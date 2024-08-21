package org.example.Server;

import org.example.Game.TicTacToeGame;
import org.example.Networking.TicTacToeProtocol;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler extends Thread {

    private final TicTacToeGame game;
    private final Socket activePlayerSocket, inactivePlayerSocket;
    private BufferedReader activeIn, inactiveIn;
    private BufferedWriter activeOut, inactiveOut;

    public ClientHandler(Socket activePlayerSocket, Socket inactivePlayerSocket) throws IOException {
        this.activePlayerSocket = activePlayerSocket;
        this.inactivePlayerSocket = inactivePlayerSocket;
        this.game = new TicTacToeGame();

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
            this.startGame();
            while(true) {
                this.processTurn();
            }
        } catch (IOException e) {
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

    private void startGame() throws IOException {
        TicTacToeProtocol.send(
                this.activeOut,
                TicTacToeProtocol.ProtocolEntity.of(TicTacToeProtocol.Commands.SERVER_START_GAME, "X")
        );
        System.out.println("Send to first player");
        TicTacToeProtocol.send(
                this.inactiveOut,
                TicTacToeProtocol.ProtocolEntity.of(TicTacToeProtocol.Commands.SERVER_START_GAME, "O")
        );
        System.out.println("Send to second player");
    }

    private void processTurn() throws IOException {
        var protocolEntity = TicTacToeProtocol.receive(this.activeIn);
        System.out.println("Command: " + protocolEntity.getCommand());
        switch (protocolEntity.getCommand()) {
            case TicTacToeProtocol.Commands.CLIENT_MOVE -> {
                System.out.println("Move: " + Arrays.toString(protocolEntity.getArgs()));
                this.game.setField(
                        Integer.parseInt(protocolEntity.getArgs()[0]),
                        Integer.parseInt(protocolEntity.getArgs()[1])
                );
                TicTacToeProtocol.send(
                        this.activeOut,
                        TicTacToeProtocol.ProtocolEntity.of(TicTacToeProtocol.Commands.CLIENT_MOVE, protocolEntity.getArgs())
                );
            }
            default -> throw new IOException("Invalid command: " + protocolEntity.getCommand());
        }
        this.changeTurn();
    }

    private void changeTurn() {
        var tempIn = this.activeIn;
        var tempOut = this.activeOut;
        this.activeIn = this.inactiveIn;
        this.inactiveIn = tempIn;
        this.activeOut = this.inactiveOut;
        this.inactiveOut = tempOut;
        this.game.changeTurn();
    }
}
