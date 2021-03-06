package exam.elements.panels;

import exam.config.GameModes;
import exam.config.ResizeableElement;
import exam.logic.abstraction.GameLogic;
import exam.utilities.GridTools;
import exam.logic.controllers.PickupMouseController;
import exam.config.FieldSizes;
import exam.logic.abstraction.Coordinate;
import exam.logic.abstraction.Directions;
import exam.logic.iterators.ContinuousMatrixColumnIterator;
import exam.logic.iterators.ContinuousMatrixRowIterator;
import exam.elements.tiles.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static exam.config.Config.DEFAULT_WINDOW_HEIGHT;
import static exam.config.Config.GAME_BG_COLOR;
import static exam.config.Config.DEFAULT_WINDOW_WIDTH;
import static exam.elements.panels.Menu.PLAYERINDICATOR;

public class Grid extends JPanel implements Iterable<Tile>, ResizeableElement {

    private Map<Coordinate, Tile> tileMap;
    private Dimension tileSize;
    private int gridWidthByPixels;
    private int gridHeightByPixels;
    private int gridWidthByTiles;
    private int gridHeightByTiles;
    private int tileWidthByPixels;
    private int tileHeightByPixels;
    private GameLogic gameLogic;
    private int hGap = 0;
    private int vGap = 0;

    public Grid(FieldSizes fieldSize, GameModes gameMode, int minRng, int maxRng, int mod, List<Directions> directions) {
        setFocusable(true);
        requestFocusInWindow();
        tileMap = new TreeMap<>();
        setBounds(0,0, Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT), Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        GridLayout gridLayout = new GridLayout(fieldSize.getN(), fieldSize.getM(), hGap, vGap);
        setLayout(gridLayout);
        gameLogic = gameMode.getLogic();
        addMouseListener(gameMode.getLogic().getMouseListener());
        addMouseMotionListener(gameMode.getLogic().getMouseMotionListener());
        gridHeightByTiles = fieldSize.getN();
        gridWidthByTiles = fieldSize.getM();
        gridWidthByPixels = Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        gridHeightByPixels = Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        tileHeightByPixels = gridHeightByPixels / gridHeightByTiles - vGap;
        tileWidthByPixels = gridWidthByPixels / gridWidthByTiles - hGap;
        tileSize = new Dimension(tileWidthByPixels, tileHeightByPixels);
        setBackground(GAME_BG_COLOR);

        Color TILE_COLOR_A = GAME_BG_COLOR;
        Color TILE_COLOR_B = GAME_BG_COLOR.darker();

        for (int i = 0; i < fieldSize.getN(); i++) {
            for (int j = 0; j < fieldSize.getM(); j++) {
                Coordinate coordinate = new Coordinate(i, j);
                Tile tile = new Tile((i + j) % 2 == 0 ? TILE_COLOR_A : TILE_COLOR_B,
                        getTileSize(),
                        coordinate);
                tileMap.put(coordinate, tile);
            }
        }
        tileMap.values().forEach(this::add);
        if(directions != null && !directions.isEmpty()) {
            gameMode.getLogic().setValidDirections(directions.stream().toArray(size -> new Directions[directions.size()]));
        }
        gameMode.getLogic().setIndicator(PLAYERINDICATOR);
        gameMode.getLogic().setRng(minRng, maxRng);
        gameMode.getLogic().setModifier(mod);
        gameMode.getLogic().setGrid(this);
        gameMode.getLogic().initGame();
        setVisible(true);
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public Map<Coordinate, Tile> getTiles() {
        return tileMap;
    }

    public int getGridWidthByPixels() {
        return gridWidthByPixels;
    }

    public int getGridHeightByPixels() {
        return gridHeightByPixels;
    }

    public int getGridWidthByTiles() {
        return gridWidthByTiles;
    }

    public int getGridHeightByTiles() {
        return gridHeightByTiles;
    }

    public int getTileWidthByPixels() {
        return tileWidthByPixels;
    }

    public int getTileHeightByPixels() {
        return tileHeightByPixels;
    }

    public int gethGap() {
        return hGap;
    }

    public int getvGap() {
        return vGap;
    }

    @Override
    public Iterator<Tile> iterator() {
        return rowIterator();
    }

    public Iterator<Tile> rowIterator() {
        return new ContinuousMatrixRowIterator<>(GridTools.getRowsFromGrid(this, 0, this.getGridHeightByTiles() -1));
    }

    public Iterator<Tile> columnIterator() {
        return new ContinuousMatrixColumnIterator<>(GridTools.getRowsFromGrid(this, 0, this.getGridHeightByTiles() -1));
    }

    @Override
    public void onResize() {
        setBounds(0,0, Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT), Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));
        this.gridWidthByPixels = Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        this.gridHeightByPixels = Math.min(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        this.tileHeightByPixels = gridHeightByPixels / gridHeightByTiles - vGap;
        this.tileWidthByPixels = gridWidthByPixels / gridWidthByTiles - hGap;
        revalidate();
        repaint();
    }

    public Dimension getTileSize() {
        return tileSize;
    }
}