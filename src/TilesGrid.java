import java.util.ArrayList;
import java.util.Random;

public class TilesGrid {
    private int size;
    public Tile[][] tiles;

    public TilesGrid(int size) {
        this.size = size;
        this.tiles = new Tile[size][size];
    }

    public TilesGrid(TilesGrid previous) {

        if (previous == null) {
            throw new IllegalStateException("Cannot create a grid from a null grid.");
        } else {
            this.size = previous.size;
            this.getTile(previous.tiles);
        }
    }

    public void empty() {
        this.tiles = null;
        this.tiles = new Tile[this.size][this.size];
    }

    private void getTile(Tile[][] previousState) {
        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                Tile tile = previousState[x][y];
                if (tile != null) {
                    this.tiles[x][y] = tile;
                }
            }
        }

    }

    public Tile getRandomAvailableCell() {
        ArrayList<Tile> tiles = this.getAvailableCells();
        int availableTileSize = tiles.size();
        if (availableTileSize > 0) {
            Random rand = new Random();
            int randNum = rand.nextInt(availableTileSize);
            return tiles.get(randNum);
        } else {
            return null;
        }
    }

    private ArrayList<Tile> getAvailableCells() {
        ArrayList<Tile> availableCells = new ArrayList();
        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                Tile tile = this.tiles[x][y];
                if (tile == null) {
                    availableCells.add(new Tile(x, y, 0));
                }
            }
        }
        return availableCells;
    }

    public boolean areCellsAvailable() {
        return this.getAvailableCells().size() > 0;
    }
    public boolean isCellAvailable(Tile tile) {
        return this.getOneTile(tile.getX(), tile.getY()) == null;
    }
    public Tile getOneTile(int x, int y) {
        return this.withinBounds(x, y) ? this.tiles[x][y] : null;
    }
    public void insertTile(Tile tile) {
        if (!this.withinBounds(tile)) {
            throw new IllegalStateException("The tile is not within range:" + tile.toString());
        } else {
            this.tiles[tile.getX()][tile.getY()] = tile;
        }
    }

    public void removeTile(Tile tile) {
        this.removeTile(tile.getX(), tile.getY());
    }
    public void removeTile(int x, int y) {
        if (!this.withinBounds(x, y)) {
            throw new IllegalStateException("The tile is not within range. x: " + x + "; y: " + y);
        } else {
            this.tiles[x][y] = null;
        }
    }

    public boolean withinBounds(Tile tile) {
        return tile.getX() >= 0 && tile.getX() < this.size && tile.getY() >= 0 && tile.getY() < this.size;
    }

    public boolean withinBounds(int x, int y) {
        return x >= 0 && x < this.size && y >= 0 && y < this.size;
    }

    public String toString() {
        String row = "";

        for(int x = 0; x < this.size; ++x) {
            row = row + "[";

            for(int y = 0; y < this.size; ++y) {
                if (y > 0) {
                    row = row + ", ";
                }

                Tile tile = this.tiles[x][y];
                if (tile != null) {
                    row = row + tile.getValue();
                } else {
                    row = row + "x";
                }
            }

            row = row + "]\n";
        }

        return row;
    }

    public int getSize() {
        return this.size;
    }
    public Tile[][] getTiles() {
        return this.tiles;
    }
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
