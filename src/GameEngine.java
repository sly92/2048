import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

public class GameEngine extends Observable {
    public static final int HAUT = 0;
    public static final int DROITE = 1;
    public static final int BAS = 2;
    public static final int GAUCHE = 3;
    private int size;
    private int score;
    private int best_score = 0;
    private TilesGrid gridGame;
    private int nbMove;
    private boolean win;
    private boolean finish;
    private boolean full;
    private ArrayList<int[]> GRID = null;
    private static GameEngine gameEngine;

    private GameEngine(int size) {
        this.size = size;
        this.nbMove = 0;
        this.GRID = new ArrayList();
        this.GRID.add(new int[]{-1, 0});
        this.GRID.add(new int[]{0, 1});
        this.GRID.add(new int[]{1, 0});
        this.GRID.add(new int[]{0, -1});
    }

    protected static GameEngine getGrid(int size) {
        if (gameEngine == null) {
            gameEngine = new GameEngine(size);
        }
        return gameEngine;
    }

    public static GameEngine getInstance() {
        return getGrid(4);
    }

    public int getSize() {
        return this.size;
    }
    public int getScore() {return this.score; }
    public int getBestScore() {return this.best_score; }
    public TilesGrid getTilesGrid() {
        return this.gridGame;
    }
    public int getNbMove() {
        return this.nbMove;
    }
    public boolean win() {
        return this.win;
    }
    public boolean finish() {
        return this.finish;
    }

    public void setup() {
        this.gridGame = new TilesGrid(this.size);
        this.addRandomTiles();
        this.fireGameChanged();
    }

    public void restart() {
        this.win = false;
        this.finish = false;
        this.gridGame = null;
        this.score = 0;
        this.nbMove = 0;
        this.setup();
    }

    private void addRandomTiles() {
        for(int i = 0; i < 2; ++i) {
            this.addRandomTile();
        }
    }

    private void addRandomTile() {
        if (this.gridGame.areCellsAvailable()) {
            int value = Math.random() < 0.5D ? 2 : 4;
            Tile tile = this.gridGame.getRandomAvailableCell();
            tile.setValue(value);
            this.gridGame.insertTile(tile);
        }
    }

    private void mergeTiles() {
        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                Tile tile = this.gridGame.getTiles()[x][y];
                if (tile != null) {
                    tile.setTileToMerge((Tile)null);
                    tile.savePosition();
                }
            }
        }
    }

    private void moveTile(Tile tileToMove, Tile cellToMoveTo) {
        this.gridGame.getTiles()[tileToMove.getX()][tileToMove.getY()] = null;
        this.gridGame.getTiles()[cellToMoveTo.getX()][cellToMoveTo.getY()] = tileToMove;
        tileToMove.setPos(cellToMoveTo);
    }

    public void move(int direction) {
        if (!this.isEnd()) {
            int[] vector = this.getDirection(direction);
            int[] xTraversal = this.getXTraversal(vector);
            int[] yTraversal = this.getYTraversal(vector);
            this.mergeTiles();
            boolean moved = false;

            for(int i = 0; i < this.size; ++i) {
                int x = xTraversal[i];

                for(int j = 0; j < this.size; ++j) {
                    int y = yTraversal[j];
                    Tile tile = this.gridGame.getOneTile(x, y);
                    if (tile != null) {
                        Tile[] positions = this.getNewPosition(tile, vector);
                        Tile next = positions[1];
                        if (next != null && this.gridGame.withinBounds(next) && next.getValue() == tile.getValue() && next.getTileToMerge() == null) {
                            Tile mergedTile = new Tile(next, tile.getValue() * 2);
                            mergedTile.setTileToMerge(next);
                            this.gridGame.insertTile(mergedTile);
                            this.gridGame.removeTile(tile);
                            tile.setPos(next);
                            this.score += mergedTile.getValue();
                            if (mergedTile.getValue() == 2048) {
                                this.win = true;
                                JOptionPane.showMessageDialog(null, "You win ! :)");
                            }
                        } else {
                            this.moveTile(tile, positions[0]);
                        }

                        if (!this.sameState(x, y, tile.getX(), tile.getY())) {
                            moved = true;
                        }
                    }
                }
            }

            if (moved) {
                this.addRandomTile();
                if (this.noMove()) {
                    this.finish = true;
                }

                ++this.nbMove;
                this.fireGameChanged();
            }

        }
        else
        {
            System.err.println(this.score+"  "+this.best_score);
            if(this.score > this.best_score)
            this.best_score = this.score;
            JOptionPane.showMessageDialog(null, "You lose ! :/");
        }
    }

    private int[] getXTraversal(int[] vector) {
        int[] xTraversal = new int[this.size];

        for(int i = 0; i < this.size; i++) {
            xTraversal[i] = i;
        }

        if (vector[0] == 1) {
            this.tileReplace(xTraversal);
        }

        return xTraversal;
    }

    private int[] getYTraversal(int[] vector) {
        int[] yTraversal = new int[this.size];

        for(int i = 0; i < this.size; i++) {
            yTraversal[i] = i;
        }

        if (vector[1] == 1) {
            this.tileReplace(yTraversal);
        }

        return yTraversal;
    }

    private int[] tileReplace(int[] array) {
        for(int i = 0; i < array.length / 2; ++i) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }

        return array;
    }

    private int[] getDirection(int direction) {
        return this.GRID.get(direction);
    }

    private void isFull() {
        this.full = true;
    }

    private boolean isEnd() {
        return this.finish || this.win && !this.full;
    }

    private boolean noMove() {
        return !(this.gridGame.areCellsAvailable() || this.mergePossible());
    }

    private boolean mergePossible() {
        Tile tile = null;

        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                tile = this.gridGame.getOneTile(x, y);
                if (tile != null) {
                    for(int i = 0; i < 4; ++i) {
                        int[] vector = this.getDirection(i);
                        Tile cell = this.gridGame.getOneTile(x + vector[0], y + vector[1]);
                        if (cell != null && cell.getValue() == tile.getValue()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private Tile[] getNewPosition(Tile tile, int[] vector) {
        Tile previous = null;

        do {
            previous = tile;
            tile = this.gridGame.getOneTile(tile.getX() + vector[0], tile.getY() + vector[1]);
            if (tile == null) {
                tile = new Tile(previous.getX() + vector[0], previous.getY() + vector[1], previous.getValue());
            }
        } while(this.gridGame.withinBounds(tile) && this.gridGame.isCellAvailable(tile));

        return new Tile[]{previous, tile};
    }

    private boolean sameState(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }

    private void fireGameChanged() {
        this.setChanged();
        this.notifyObservers();
    }
}
