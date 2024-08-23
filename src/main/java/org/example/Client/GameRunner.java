package org.example.Client;

import org.example.Client.GUI.Components.*;
import org.example.Game.TicTacToeGame;
import org.example.Game.TicTacToeGameForClient;
import org.example.Networking.NetworkInfo;
import org.example.Networking.TicTacToeProtocol;
import org.example.utills.Tuple;

public class GameRunner {
    private final MainFrame mainFrame;
    private TicTacToeGameForClient game;
    private final BoardPanel boardPanel;

    private final TicTacToeProtocol.CommunicationManager communicationManager;

    public GameRunner() {
        // Main Menu
        var mainMenu = new MainMenu(
                Buttons.of("PLAY", e -> ThreadManager.runInNewThread(this::initializeGame)),
                Buttons.of("EXIT", e -> this.exit())
        );

        // Main Frame
        this.mainFrame = new MainFrame(mainMenu);
        this.boardPanel = new BoardPanel();

        // Communication manager
        this.communicationManager = TicTacToeProtocol.createCommunicationManager(NetworkInfo.SERVER_ADDRESS, NetworkInfo.SERVER_PORT);
    }

    public final void run() {
        this.mainFrame.setVisible(true);
    }

    private void exit() {
        this.mainFrame.dispose();
    }

    private void processEntity(final TicTacToeProtocol.ProtocolEntity protocolEntity) {
        switch (protocolEntity.getCommand()) {
            case TicTacToeProtocol.Commands.SERVER_START_GAME ->
                this.processStartGame(
                        protocolEntity.getArgs()[0].equals("X") ? TicTacToeGame.Turn.Player_X : TicTacToeGame.Turn.Player_O
                );
            case TicTacToeProtocol.Commands.CLIENT_MOVE ->
                this.processMove(
                    Tuple.of(
                        Integer.parseInt(protocolEntity.getArgs()[0]),
                        Integer.parseInt(protocolEntity.getArgs()[1])
                    )
                );
            case TicTacToeProtocol.Commands.SERVER_END ->
                this.processEndGame(protocolEntity.getArgs()[0]);
            default -> System.out.println("Unexpected server command: " + protocolEntity.getCommand());
        }
    }

    private void processStartGame(final TicTacToeGame.Turn playersSymbol) {
        this.game = new TicTacToeGameForClient(playersSymbol);
        this.mainFrame.changeContentPane(this.boardPanel);
    }

    private void processMove(final Tuple<Integer, Integer> coords) {
        this.boardPanel.setField(
                coords.first(),
                coords.second(),
                this.game.getOpponentsSymbol().toString()
        );
        this.game.setField(coords.first(), coords.second());
        this.game.changeTurn();
    }

    private void processEndGame(final String winner) {
        String prompt;
        if(this.game.getPlayersSymbol().toString().equals(winner)) {
            prompt = "YOU WIN";
        } else if(this.game.getOpponentsSymbol().toString().equals(winner)) {
            prompt = "YOU LOOSE";
        } else  {
            prompt = "DRAW";
        }
        this.mainFrame.changeContentPane(PromptPanel.of(prompt));

        try {
            this.communicationManager.closeResources();
        } catch (Exception e) {
            System.out.println("Failure during resources closure.");
        }
    }

    private void initializeGame() {
        try {
            this.mainFrame.changeContentPane(PromptPanel.of("Waiting for game initialization..."));
            this.communicationManager.createServerConnection();
            this.communicationManager.receive().forEach(this::processEntity);
        } catch (Exception e) {
            System.out.println("Failure during server connection setup.");
        }
    }
}
