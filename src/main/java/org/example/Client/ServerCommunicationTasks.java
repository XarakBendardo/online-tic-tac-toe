package org.example.Client;

import org.example.Client.GUI.ComponentManager;
import org.example.Game.TicTacToeGame;
import org.example.Game.TicTacToeGameForClient;
import org.example.Networking.CommandManager;
import org.example.Networking.NetworkInfo;

import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class ServerCommunicationTasks {
    public static class GameInitializer implements Runnable {

        @Override
        public void run() {
//            Socket socket = null;
//            BufferedWriter out = null;
//            BufferedReader in = null;
//            try {
//                socket = new Socket(NetworkInfo.SERVER_ADDRESS, NetworkInfo.SERVER_PORT);
//                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String response = in.readLine();
//                if(!response.equals(CommandManager.SERVER_START_GAME)) {
//                    throw new IOException("Unexpected response from server: " + response);
//                }
//                TicTacToeGameForClient.initInstance(
//                        in.readLine().equals("X") ? TicTacToeGame.Turn.Player_X : TicTacToeGame.Turn.Player_O
//                );
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (socket != null) socket.close();
//                    if (out != null) out.close();
//                    if (in != null) in.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            try {
                Thread.sleep(3000);
                ComponentManager.switchMainFrameContentPane(ComponentManager.Board());
                TicTacToeGameForClient.initInstance(TicTacToeGame.Turn.Player_X);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
