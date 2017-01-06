package exam.logic.controllers;

import exam.elements.panels.Grid;
import exam.elements.tiles.Pawn;
import exam.logic.abstraction.GameLogic;
import exam.elements.tiles.Tile;
import sun.plugin.com.event.COMEventHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static exam.elements.panels.Menu.STEPCOUNTERLABEL;

public class MouseController implements MouseListener, MouseMotionListener {

    private int x;
    private int y;
    private int xOffset = 0;
    private int yOffset = 0;
    private JLayeredPane game;/*
    private JPanel gameField;*/
    private Component original;
    //private Pawn shadow;

    private GameLogic gameLogic;

    public MouseController(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void mousePressed(MouseEvent e) { //TODO ADD SHADOW
        game = (JLayeredPane) ((Grid) e.getSource()).getParent().getParent();/*
        gameField = (JPanel) ((Grid) e.getSource()).getParent();*/
        try {
            Pawn pawn = (Pawn) ((Grid) e.getSource()).findComponentAt(e.getX(), e.getY());
            if (!gameLogic.currentPlayersPawn(pawn)) {
                return;
            }
            //shadow = new Pawn(Color.BLUE, 1, 50, 50);

            gameLogic.clearValidSteps();
            gameLogic.setValidSteps((Tile) pawn.getParent());
            gameLogic.setActualPawn(pawn);

            original = pawn.getParent();

            Point parentLocation = pawn.getParent().getLocation();
            x = parentLocation.x - e.getX();
            y = parentLocation.y - e.getY();

            gameLogic.getActualPawn().setLocation(e.getX() + x + xOffset, e.getY() + y + yOffset);
            //shadow.setLocation(e.getX() + x + 30, e.getY() + y + 30);
            //shadow.setBounds(e.getX() + x + 30, e.getY() + y + 30, 50, 50);
            game.add(gameLogic.getActualPawn(), JLayeredPane.DRAG_LAYER);
            //game.add(shadow);
            game.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        } catch (ClassCastException ignored) {
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(gameLogic.isContinuusHighLighting()) {
            gameLogic.clearValidSteps();
            try {
                gameLogic.setValidSteps((Tile) e.getComponent().getComponentAt(e.getX(), e.getY()));
            } catch (ClassCastException ignored) {}
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        gameLogic.clearValidSteps();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        game.setCursor(null);

        if (gameLogic.getActualPawn() == null) {
            gameLogic.clearValidSteps();
            gameLogic.clearActualPawn();
            return;
        }

        gameLogic.getActualPawn().setVisible(false);
        game.remove(gameLogic.getActualPawn());
        //game.remove(shadow);
        gameLogic.getActualPawn().setVisible(true);

        int xMax = game.getWidth() - gameLogic.getActualPawn().getWidth();
        int ix = Math.min(e.getX(), xMax);
        ix = Math.max(ix, 0);

        int yMax = game.getHeight() - gameLogic.getActualPawn().getHeight();
        int iy = Math.min(e.getY(), yMax);
        iy = Math.max(iy, 0);

        try {
            gameLogic.evaluateStep((Tile) original, (Tile) e.getComponent().getComponentAt(ix, iy));
            STEPCOUNTERLABEL.increase();
        } catch (ClassCastException ignored) {
        } finally {
            game.revalidate();
            game.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (gameLogic.getActualPawn() == null) {
            return;
        }
        int ix = e.getX() + this.x + xOffset;
        int xMax = game.getWidth() - gameLogic.getActualPawn().getWidth();
        ix = Math.min(ix, xMax);
        ix = Math.max(ix, 0);
        int iy = e.getY() + this.y + yOffset;
        int yMax = game.getHeight() - gameLogic.getActualPawn().getHeight();
        iy = Math.min(iy, yMax);
        iy = Math.max(iy, 0);
        gameLogic.getActualPawn().setLocation(ix, iy);
        //shadow.setLocation(ix + 30, iy + 30);
        //game.repaint(); //INFO can be disabled for better performance
    }
}