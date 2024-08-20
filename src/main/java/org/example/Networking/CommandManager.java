package org.example.Networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class CommandManager {
    public static final String SERVER_START_GAME = "start";
    public static final String CLIENT_MOVE = "move";
    public static final String SERVER_OK = "ok";
    public static final String SERVER_END = "end";

    public static void send(final BufferedWriter out, final String command) throws IOException {
        out.write(command);
        out.newLine();
        out.flush();
    }

    public static void send(final BufferedWriter out, final String command, final String arg) throws IOException {
        out.write(command);
        out.newLine();
        out.write(arg);
        out.newLine();
        out.flush();
    }

    public static String receive(final BufferedReader in) {
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
