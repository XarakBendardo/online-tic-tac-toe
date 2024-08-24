package org.example.Server;

import org.example.Game.TicTacToeGame;
import org.example.Networking.TicTacToeProtocol;

import java.io.*;
import java.util.Arrays;

public class ClientHandler extends Thread {

    private final TicTacToeGame game;
    private TicTacToeProtocol.ServerCommunicationManager activeManager, inactiveManager;
    private boolean done;

    public ClientHandler(
            final TicTacToeProtocol.ServerCommunicationManager activePlayerManager,
            final TicTacToeProtocol.ServerCommunicationManager inactivePlayerManager) throws IOException {
        this.activeManager = activePlayerManager;
        this.inactiveManager = inactivePlayerManager;
        this.game = new TicTacToeGame();
        this.done = false;
    }

    @Override
    public void run() {
        try {
            this.startGame();
            while(!this.done) {
                this.processTurn();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                this.activeManager.closeResources();
                this.inactiveManager.closeResources();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void startGame() throws IOException {
        this.activeManager.addMessage(TicTacToeProtocol.ProtocolEntity.of(TicTacToeProtocol.Commands.SERVER_START_GAME, "X"));
        this.inactiveManager.addMessage(TicTacToeProtocol.ProtocolEntity.of(TicTacToeProtocol.Commands.SERVER_START_GAME, "O"));
        this.activeManager.send();
        System.out.println("Send to first player");
        this.inactiveManager.send();
        System.out.println("Send to second player");
    }

    private void processTurn() throws IOException {
        for(var protocolEntity : this.activeManager.receive()) {
            System.out.println("Command: " + protocolEntity.getCommand());
            this.processEntity(protocolEntity);
        }
    }

    private void processEntity(final TicTacToeProtocol.ProtocolEntity protocolEntity) throws IOException {
        switch (protocolEntity.getCommand()) {
            case TicTacToeProtocol.Commands.CLIENT_MOVE -> {
                System.out.println("Move: " + Arrays.toString(protocolEntity.getArgs()));
                this.game.setField(
                        Integer.parseInt(protocolEntity.getArgs()[0]),
                        Integer.parseInt(protocolEntity.getArgs()[1])
                );
                this.inactiveManager.addMessage(protocolEntity);
                if(this.game.hasEnded()) {
                    this.endGame();
                    this.activeManager.send();
                }
                this.inactiveManager.send();
            }
            default -> throw new IOException("Invalid command: " + protocolEntity.getCommand());
        }
        this.changeTurn();
    }

    private void changeTurn() {
        var temp = this.activeManager;
        this.activeManager = this.inactiveManager;
        this.inactiveManager = temp;

        this.game.changeTurn();
    }

    private void endGame() {
        var winnerMsg = TicTacToeProtocol.ProtocolEntity.of(
                TicTacToeProtocol.Commands.SERVER_END,
                this.game.checkWinner().toString()
        );
        this.activeManager.addMessage(winnerMsg);
        this.inactiveManager.addMessage(winnerMsg);

        this.done = true;
    }
}
