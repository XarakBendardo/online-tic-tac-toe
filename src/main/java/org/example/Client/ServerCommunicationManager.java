package org.example.Client;

import org.example.Client.GUI.ComponentManager;
import org.example.Game.TicTacToeGame;
import org.example.Game.TicTacToeGameForClient;
import org.example.Networking.TicTacToeProtocol;
import org.example.Networking.NetworkInfo;

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
    private static void initializeGame() {
        ComponentManager.switchMainFrameContentPane(ComponentManager.WaitingPanel("Waiting for an opponent..."));
        TicTacToeProtocol.ProtocolEntity response = TicTacToeProtocol.receive(in);
        TicTacToeGameForClient.initInstance(
                response.getArgs()[0].equals("X") ? TicTacToeGame.Turn.Player_X : TicTacToeGame.Turn.Player_O
        );
        ComponentManager.switchMainFrameContentPane(ComponentManager.Board());
        if(TicTacToeGameForClient.getInstance().getTurn() != TicTacToeGameForClient.getInstance().getPlayersSymbol()) {
            waitForOpponentsMove();
        }
    }

    public static void startGame() {
        new Thread(() -> {
            connectToTheServer();
            initializeGame();
        }).start();
    }

    public static void sendMove(final int x, final int y) {
        new Thread(() -> {
            try {
                TicTacToeProtocol.send(
                        out,
                        TicTacToeProtocol.ProtocolEntity.of(TicTacToeProtocol.Commands.CLIENT_MOVE, String.valueOf(x), String.valueOf(y))
                );
            } catch (IOException e) {
                System.out.println("Failure during sending a move");
            }
        }).start();
    }

    public static void waitForOpponentsMove() {
        new Thread(() -> {
            TicTacToeProtocol.ProtocolEntity response = TicTacToeProtocol.receive(in);
            System.out.println("Response: " + response);
        }).start();
    }
}
