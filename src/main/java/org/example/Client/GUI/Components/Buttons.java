package org.example.Client.GUI.Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A factory class for creating customized buttons for main menu.
 */
public class Buttons {
    private static final Font FONT = new Font("Arial", Font.PLAIN, 50);

    private static final class MenuButtonMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {
            if(!(e.getSource() instanceof JButton button)) return;
            button.setForeground(Color.LIGHT_GRAY);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if(!(e.getSource() instanceof JButton button)) return;
            button.setForeground(Color.BLACK);
        }
    }
    public static JButton of(final String text, final ActionListener listener) {
        var button = new JButton(text);

        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setFont(FONT);

        button.addActionListener(listener);

        button.addMouseListener(new MenuButtonMouseListener());
        return button;
    }
}
