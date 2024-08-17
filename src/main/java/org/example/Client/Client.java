package org.example.Client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import org.example.Client.GUI.ComponentManager;
import org.example.Game.TicTacToeGame;
import org.example.Networking.CommandManager;
import org.example.Networking.NetworkInfo;

public class Client {
    private static Socket socket = null;
    private static BufferedWriter out = null;
    private static BufferedReader in = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean playerTurn;
    private static TicTacToeGame game = new TicTacToeGame();

    public static void main(String[] args) {
//        try {
//            connectToTheServer();
//            startGame();
//            while(true) {
//                processTurn();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (out != null) out.close();
//                if (in != null) in.close();
//                if (socket != null) socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        ComponentManager.MainFrame().setVisible(true);
    }

    private static void connectToTheServer() throws IOException {
        socket = new Socket(NetworkInfo.SERVER_ADDRESS, NetworkInfo.SERVER_PORT);
        System.out.println("Connected to server");
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private static void startGame() throws IOException {
        String response = in.readLine();
        if(!response.equals(CommandManager.SERVER_START_GAME)) {
            throw new IOException("Unexpected response from server: " + response);
        }
        playerTurn = in.readLine().equals("X");
    }

    private static void processTurn() throws IOException {
        String cords;
        if(playerTurn) {
            System.out.println("Enter your move (x y): ");
            cords = scanner.nextLine();
            CommandManager.sendCommand(out, CommandManager.CLIENT_MOVE, cords);
            String responseCommand = in.readLine();
            if(!responseCommand.equals(CommandManager.SERVER_OK)) {
                throw new IOException("Unexpected response from server: " + responseCommand);
            }
        } else {
            System.out.println("Waiting for opponent's move");
            String responseCommand = in.readLine();
            if(!responseCommand.equals(CommandManager.CLIENT_MOVE)) {
                throw new IOException("Unexpected response from server: " + responseCommand);
            }
            cords = in.readLine();
        }
        playerTurn = !playerTurn;
        game.setField(Integer.parseInt(cords.substring(0, 1)), Integer.parseInt(cords.substring(1, 2)));
        game.changeTurn();
        System.out.println(game.getBoardAsString());
    }
}
