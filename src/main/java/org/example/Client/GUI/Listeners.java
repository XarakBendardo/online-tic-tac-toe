package org.example.Client.GUI;

import org.example.Client.ServerCommunicationManager;
import org.example.Game.TicTacToeGameForClient;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Listeners {
    private static MouseListener boardMouseListener = null;
    private static ActionListener exitMenuButtonListener = null;
    private static ActionListener playMenuButtonListener = null;

    public static MouseListener BoardMouseListener() {
        if(boardMouseListener == null) {
            boardMouseListener = new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!(e.getSource() instanceof ComponentManager.GUIBoardField field)) return;
                    if(TicTacToeGameForClient.getInstance().getTurn() != TicTacToeGameForClient.getInstance().getPlayersSymbol()) return;

                    if(TicTacToeGameForClient.getInstance().setField(field.getCordX(), field.getCordY())) {
                        field.setField(TicTacToeGameForClient.getInstance().getPlayersSymbol());
                        field.revalidate();
                        field.repaint();

                        TicTacToeGameForClient.getInstance().changeTurn();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {}

                @Override
                public void mouseExited(MouseEvent e) {}
            };
        }
        return boardMouseListener;
    }

    public static ActionListener ExitMenuButtonListener() {
        if(exitMenuButtonListener == null) {
            exitMenuButtonListener = e -> ComponentManager.MainFrame().dispose();
        }
        return exitMenuButtonListener;
    }

    public static ActionListener PlayMenuButtonListener() {
        if(playMenuButtonListener == null) {
            playMenuButtonListener = e -> ServerCommunicationManager.startGame();
        }
        return playMenuButtonListener;
    }
}
