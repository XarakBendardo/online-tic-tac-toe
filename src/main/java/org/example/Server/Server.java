package org.example.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    private static final int PORT = 1234;
    public static void main( String[] args ) {
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            serverSocket = new ServerSocket(Server.PORT);
            System.out.println("Server is running on port " + Server.PORT);
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String message = in.readLine();
            System.out.println("Received message: " + message);
            out.write("Hello from server\n");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) out.close();
                if (in != null) in.close();
                if (clientSocket != null) clientSocket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
