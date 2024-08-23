package org.example.Networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicTacToeProtocol {
    public static final String SEPARATOR = " ";
    public static class Commands {
        public static final String SERVER_START_GAME = "start";
        public static final String CLIENT_MOVE = "move";
        public static final String SERVER_END = "end";
    }

    public static class ProtocolEntity {
        private final String command;
        private final String[] args;

        private ProtocolEntity(final String command, final String... args) {
            this.command = command;
            this.args = args;
        }

        public String getCommand() {
            return this.command;
        }

        public String[] getArgs() {
            return this.args;
        }

        @Override
        public String toString() {
            var builder = new StringBuilder();
            builder.append(this.command);
            if(this.args != null) {
                for (final var arg : args) {
                    builder.append(TicTacToeProtocol.SEPARATOR).append(arg);
                }
            }
            return builder.toString();
        }

        public static ProtocolEntity of(String data) {
            var split = data.split(TicTacToeProtocol.SEPARATOR);
            var command = split[0];
            String[] args = null;
            if(split.length > 1) {
                args = new String[split.length - 1];
                System.arraycopy(split, 1, args, 0, split.length - 1);
            }
            return new ProtocolEntity(command, args);
        }

        public static ProtocolEntity of(final String command, final String... args) {
            return new ProtocolEntity(command, args);
        }
    }

    public static void addMessage(final BufferedWriter out, final ProtocolEntity entity) throws IOException {
        out.write(entity.toString());
        out.newLine();
    }

    public static void send(final BufferedWriter out) throws IOException {
        out.flush();
    }

    public static List<ProtocolEntity> receive(final BufferedReader in) {
        try {
            List<ProtocolEntity> entities = new ArrayList<>();
            do {
                entities.add(ProtocolEntity.of(in.readLine()));
                System.out.println("RECEIVED");
            } while(in.ready());
            return entities;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
