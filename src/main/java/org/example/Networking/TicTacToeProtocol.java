package org.example.Networking;

import java.io.*;
import java.net.Socket;
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

    public interface ServerCommunicationManager {
        void addMessage(final ProtocolEntity entity);
        void send();
        List<ProtocolEntity> receive();
        void closeResources() throws Exception;
    }

    public interface ClientCommunicationManager extends ServerCommunicationManager {
        void createServerConnection() throws Exception;
    }

    private static class ConcreteServerCommunicationManager implements ServerCommunicationManager {
        protected BufferedWriter out;
        protected BufferedReader in;
        protected Socket socket;

        private ConcreteServerCommunicationManager(final Socket socket, final BufferedWriter out, final BufferedReader in) {
            this.socket = socket;
            this.in = in;
            this.out = out;
        }

        @Override
        public void addMessage(final ProtocolEntity entity) {
            try {
                this.out.write(entity.toString());
                this.out.newLine();
            } catch (IOException e) {
                System.out.println("Failure during adding a message.");
                throw new RuntimeException();
            }
        }

        @Override
        public void send() {
            try {
                this.out.flush();
            } catch (IOException e) {
                System.out.println("Failure during sending messages.");
                throw new RuntimeException();
            }
        }

        @Override
        public List<ProtocolEntity> receive() {
            try {
                List<ProtocolEntity> entities = new ArrayList<>();
                do {
                    entities.add(ProtocolEntity.of(in.readLine()));
                } while(in.ready());
                return entities;
            } catch (IOException e) {
                System.out.println("Failure during receiving messages.");
                throw new RuntimeException();
            }
        }

        @Override
        public void closeResources() throws Exception {
            if(this.socket != null) this.socket.close();
            if(this.out != null) this.out.close();
            if(this.out != null) this.in.close();
        }
    }

    private static class ConcreteClientCommunicationManager extends ConcreteServerCommunicationManager implements ClientCommunicationManager {

        private final String serverAddress;
        private final int port;
        private ConcreteClientCommunicationManager(final String serverAddress, final int port) {
            super(null, null, null);
            this.serverAddress = serverAddress;
            this.port = port;
        }

        @Override
        public void createServerConnection() throws Exception {
            this.socket = new Socket(this.serverAddress, this.port);
            this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
    }

    public static ServerCommunicationManager createServerCommunicationManager(final Socket socket) throws IOException {
        return new ConcreteServerCommunicationManager(
                socket,
                new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
                new BufferedReader(new InputStreamReader(socket.getInputStream()))
        );
    }

    public static ClientCommunicationManager createClientCommunicationManager(final String serverAddress, final int port) {
        return new ConcreteClientCommunicationManager(serverAddress, port);
    }
}
