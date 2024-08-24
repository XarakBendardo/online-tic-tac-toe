package org.example.Server;

import org.example.Networking.NetworkInfo;
import org.example.Networking.TicTacToeProtocol;

import java.io.*;
import java.util.ArrayList;

/*
* @TODO listeners and communication managers should implement Resource interface
*/
public class Server
{
    private static final ArrayList<TicTacToeProtocol.ServerCommunicationManager> managerQueue = new ArrayList<>(2);
    public static void main( String[] args ) {
        TicTacToeProtocol.ServerListener listener = null;
        try {
            listener = TicTacToeProtocol.createServerListener(NetworkInfo.SERVER_PORT);
            System.out.println("Server is running on port " + NetworkInfo.SERVER_PORT);
            while (true) {
                registerPlayer(listener.acceptPlayer());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (listener != null) listener.closeResources();
            } catch (Exception e) {
                System.out.println("Failure during resources closure");
            }
        }
    }

    public static void registerPlayer(TicTacToeProtocol.ServerCommunicationManager manager) {
        String msg = "Client connected. ";
        if(managerQueue.size() == 1) {
            try {
                new ClientHandler(managerQueue.remove(0), manager).start();
                msg += "Game started.";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            managerQueue.add(manager);
            msg += "Waiting for second player.";
        }
        System.out.println(msg);
    }
}
