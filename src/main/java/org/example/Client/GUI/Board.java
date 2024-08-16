package org.example.Client.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Board extends JPanel {
    public static final Dimension PREFFERED_SIZE = new Dimension(800, 800);
    private final BufferedImage backgroundImg;
    public Board() {
        super();
        this.setPreferredSize(PREFFERED_SIZE);

        BufferedImage tempImg;
        try {
            tempImg = ImageIO.read(new File("C:\\Users\\Marcin\\IdeaProjects\\online-tic-tac-toe\\src\\main\\resources\\Board Sprite.png"));
        } catch (IOException e) {
            e.printStackTrace();
            tempImg = null;
        }
        this.backgroundImg = tempImg;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.backgroundImg != null)
            g.drawImage(this.backgroundImg, 0, 0, null);
    }
}
