package org.example.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
    private static final ArrayList<Socket> socketQueue = new ArrayList<>(2);
    private static final int PORT = 1234;
    public static void main( String[] args ) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(Server.PORT);
            System.out.println("Server is running on port " + Server.PORT);
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
        if(socketQueue.size() == 1) {
            new ClientHandler(socketQueue.remove(0), socket).start();
        } else {
            socketQueue.add(socket);
        }
        System.out.println("Client connected");
    }
}
