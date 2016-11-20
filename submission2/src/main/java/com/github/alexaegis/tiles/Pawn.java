package com.github.alexaegis.tiles;

import javax.swing.*;
import java.awt.*;

import static com.github.alexaegis.Main.GRID_SIZE_DEFAULT;
import static com.github.alexaegis.Main.TILE_SIZE;

public final class Pawn extends JComponent {

    private int player;

    private static GradientPaint player0Color = new GradientPaint(0, 0, new Color(220,60, 40, 255),100, 255, new Color(220,160, 100, 230));
    private static GradientPaint player1Color = new GradientPaint(0, 0, new Color(0, 110, 180, 255),100, 255, new Color(160,200, 220, 230));

    public Pawn(int player) {
        this.player = player;
        setPreferredSize(new Dimension(GRID_SIZE_DEFAULT / TILE_SIZE, GRID_SIZE_DEFAULT / TILE_SIZE));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(player == 1 ? player0Color : player1Color);
        g.fillOval(0,0, TILE_SIZE,TILE_SIZE);
    }

    public int getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pawn pawn = (Pawn) o;

        return player == pawn.player;

    }

    @Override
    public int hashCode() {
        return player;
    }
}