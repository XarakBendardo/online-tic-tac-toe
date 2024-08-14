package org.example.Server;

import org.example.Networking.NetworkInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private static final ArrayList<Socket> socketQueue = new ArrayList<>(2);
    public static void main( String[] args ) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(NetworkInfo.SERVER_PORT);
            System.out.println("Server is running on port " + NetworkInfo.SERVER_PORT);
            while (true) {
                registerPlayer(serverSocket.accept());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void registerPlayer(Socket socket) {
        String msg = "Client connected. ";
        if(socketQueue.size() == 1) {
            try {
                new ClientHandler(socketQueue.remove(0), socket).start();
                msg += "Game started.";
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            socketQueue.add(socket);
            msg += "Waiting for second player.";
        }
        System.out.println(msg);
    }
}
