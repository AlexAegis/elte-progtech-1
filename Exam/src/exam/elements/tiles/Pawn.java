package exam.elements.tiles;

import exam.config.ResizeableElement;

import javax.swing.*;
import java.awt.*;

public class Pawn extends JComponent implements ResizeableElement {

    protected Color color;
    protected Color colorMuter = new Color(232, 241, 219, 30);
    protected int width;
    protected int height;
    protected int padding;
    protected Paint paint;
    protected boolean promotion = false;

    protected int player;

    protected Color[] colors;
    protected float[] dist;

    public Pawn() {

    }

    public Pawn(Color color, int player, int width, int height) {
        this.width = width;
        this.height = height;
        this.player = player;
        this.padding = ((width + height) / 2) / 16;
        setLayout(new BorderLayout());
        colors = new Color[]{color, new Color(255,255,255,200)};
        dist = new float[]{0.2f, 1f};
        paint = new RadialGradientPaint(width / 2, height / 2,width, dist, colors);
        setPreferredSize(new Dimension(width, height));
        setVisible(true);
    }

    public Pawn(Color color, int player, Dimension dimension) {
        this(color, player, (int) dimension.getWidth(), (int) dimension.getHeight());
    }

    public int getPlayer() {
        return player;
    }

    public void promote() {
        promotion = true;
    }

    public boolean isPromoted() {
        return promotion;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        paint = new RadialGradientPaint(width / 2, height / 2,width, dist, colors);
        g.setPaint(paint);
        Polygon p = new Polygon();
        p.addPoint(padding + width / 5, height - padding - width / 10);
        p.addPoint(width - padding - width / 5, height - padding - width / 10);
        p.addPoint(width / 2, height / 10);

        g.fillPolygon(p);
        g.fillOval((width / 2) - (height / 6), padding, height / 3, height / 3);
    }

    public Pawn takeOff() {
        ((Tile)getParent()).removeChild();
        return this;
    }

    @Override
    public String toString() {
        return (player == 1) ? "Red" : "Blue";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pawn pawn = (Pawn) o;

        return player == pawn.player /*&& getColor().equals(pawn.getColor())*/;

    }

    @Override
    public int hashCode() {
        return player;
    }

    @Override
    public void onResize() {
        setSize(getParent().getSize());
        height = getParent().getWidth();
        width = getParent().getHeight();
        revalidate();
        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void demote() {
        promotion = false;
    }
}