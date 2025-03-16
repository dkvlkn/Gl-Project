package Environnement;

import javax.swing.*;
import java.awt.*;

public class Obstacles {
    private int x, y;
    private Image image;

    public Obstacles(int x, int y, String imagePath) {
        this.x = x;
        this.y = y;
        this.image = new ImageIcon(imagePath).getImage();
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, 50, 50, null);
    }
}