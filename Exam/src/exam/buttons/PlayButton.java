package exam.buttons;

import exam.logic.FieldSize;
import exam.panels.ContentPane;
import exam.panels.Grid;

import javax.swing.*;
import java.awt.*;

import static exam.panels.ControlPane.SIZESELECTOR;

public class PlayButton extends JButton {

    private String name = "Play";

    public PlayButton() {
        setText(name);
        setPreferredSize(new Dimension(80, 40));
        addActionListener(e -> {
            ContentPane.GAME.removeAll();
            ContentPane.GAME.add(new Grid((FieldSize) SIZESELECTOR.getSelectedItem()));
            ContentPane.GAME.revalidate();
            ContentPane.GAME.repaint();
        });
    }
}