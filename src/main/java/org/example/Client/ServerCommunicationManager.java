package org.example.Client;

import org.example.Client.GUI.ComponentManager;
import org.example.Game.TicTacToeGame;
import org.example.Game.TicTacToeGameForClient;
import org.example.Networking.TicTacToeProtocol;
import org.example.Networking.NetworkInfo;
import org.example.utills.Tuple;

import java.io.*;
import java.net.Socket;

public class ServerCommunicationManager {
    private static Socket socket = null;
    private static BufferedWriter out = null;
    private static BufferedReader in = null;

    private static void closeResources() {
        try {
            System.out.println("Closing resources...");
            if (socket != null) socket.close();
            if (out != null) out.close();
            if (in != null) in.close();
        } catch (IOException e) {
            System.out.println("Failure resources closure");
        }
    }

    private static void connectToTheServer() {
        try {
            ComponentManager.switchMainFrameContentPane(ComponentManager.WaitingPanel("Waiting for server connection..."));
            socket = new Socket(NetworkInfo.SERVER_ADDRESS, NetworkInfo.SERVER_PORT);
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Failure during server connection set up");
            closeResources();
        }
    }

    private static void processEntity(final TicTacToeProtocol.ProtocolEntity protocolEntity) {
        switch (protocolEntity.getCommand()) {
            case TicTacToeProtocol.Commands.SERVER_START_GAME -> {
                TicTacToeGameForClient.initInstance(
                        protocolEntity.getArgs()[0].equals("X") ? TicTacToeGame.Turn.Player_X : TicTacToeGame.Turn.Player_O
                );
                ComponentManager.switchMainFrameContentPane(ComponentManager.Board());
                if(TicTacToeGameForClient.getInstance().getTurn() != TicTacToeGameForClient.getInstance().getPlayersSymbol()) {
                    waitForOpponentsMove();
                }
            }
            case TicTacToeProtocol.Commands.CLIENT_MOVE -> {
                var coords = Tuple.of(
                        Integer.parseInt(protocolEntity.getArgs()[0]),
                        Integer.parseInt(protocolEntity.getArgs()[1])
                );
                ComponentManager.Board().setField(
                        coords.first(),
                        coords.second(),
                        TicTacToeGameForClient.getInstance().getOpponentsSymbol().toString()
                );
                TicTacToeGameForClient.getInstance().setField(coords.first(), coords.second());
                TicTacToeGameForClient.getInstance().changeTurn();
            }
            case TicTacToeProtocol.Commands.SERVER_END -> {
                String prompt;
                if(TicTacToeGameForClient.getInstance().getPlayersSymbol().toString().equals(protocolEntity.getArgs()[0])) {
                    prompt = "YOU WIN";
                } else if(TicTacToeGameForClient.getInstance().getOpponentsSymbol().toString().equals(protocolEntity.getArgs()[0])) {
                    prompt = "YOU LOOSE";
                } else  {
                    prompt = "DRAW";
                }
                ComponentManager.switchMainFrameContentPane(ComponentManager.WaitingPanel(prompt));
            }
            default -> System.out.println("Unexpected server command: " + protocolEntity.getCommand());
        }
    }
    private static void initializeGame() {
        ComponentManager.switchMainFrameContentPane(ComponentManager.WaitingPanel("Waiting for an opponent..."));
        TicTacToeProtocol.receive(in).forEach(ServerCommunicationManager::processEntity);
        System.out.println("Initialized");
    }

    public static void startGame() {
        connectToTheServer();
        initializeGame();
    }

    public static void sendMove(final int x, final int y) {
        try {
            TicTacToeProtocol.addMessage(
                out,
                TicTacToeProtocol.ProtocolEntity.of(TicTacToeProtocol.Commands.CLIENT_MOVE, String.valueOf(x), String.valueOf(y))
            );
            TicTacToeProtocol.send(out);
        } catch (IOException e) {
            System.out.println("Failure during sending a move");
        }
    }

    public static void waitForOpponentsMove() {
        TicTacToeProtocol.receive(in).forEach(ServerCommunicationManager::processEntity);
    }
}
