package data.Astar;

import javax.swing.*;
import java.awt.*;

public class GrilleAstar extends JPanel {
    private static final long serialVersionUID = 1L;
    private Image background;

    public GrilleAstar() {
        this.background = new ImageIcon("/mnt/data/fond.webp").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }
}