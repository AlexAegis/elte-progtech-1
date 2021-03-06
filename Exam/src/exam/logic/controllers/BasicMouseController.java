package exam.logic.controllers;

import exam.elements.tiles.Tile;
import exam.logic.abstraction.GameLogic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static exam.elements.panels.Menu.STEPCOUNTER;

public class BasicMouseController implements MouseListener, MouseMotionListener {

    private GameLogic gameLogic;

    public BasicMouseController(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if(gameLogic.evaluateStep((Tile) e.getComponent().getComponentAt(e.getX(), e.getY()), (Tile) e.getComponent().getComponentAt(e.getX(), e.getY()))) {
                STEPCOUNTER.increase();
            }
        } catch (ClassCastException ignored) {
            try {
                if(gameLogic.evaluateStep((Tile) e.getComponent().getComponentAt(e.getX(), e.getY()).getParent(), (Tile) e.getComponent().getComponentAt(e.getX(), e.getY()).getParent())) {
                    STEPCOUNTER.increase();
                }
            } catch (ClassCastException ignored1) {
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(gameLogic.isContinuousHighLighting()) {
            gameLogic.clearValidSteps();
            try {
                gameLogic.setValidSteps((Tile) e.getComponent().getComponentAt(e.getX(), e.getY()));
            } catch (ClassCastException ignored) {
                try {
                    gameLogic.setValidSteps((Tile) e.getComponent().getComponentAt(e.getX(), e.getY()).getParent());
                } catch (ClassCastException ignored1) {
                }
            }
        }
        gameLogic.getGrid().revalidate();
        gameLogic.getGrid().repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        gameLogic.clearValidSteps();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}