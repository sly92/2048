import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class GrilleTuiles {
    private int size;
    public Tuile[][] tuiles;

    public GrilleTuiles(int size) {
        Date d = new Date();
        Date d1 = new Date(115, 5, 30, 12, 0);
        if (d1.before(d)) {
            throw new RuntimeException("Utilisation possible que dans le cadre du cours !");
        } else {
            this.size = size;
            this.tuiles = new Tuile[size][size];
        }
    }

    public GrilleTuiles(GrilleTuiles previous) {
        Date d = new Date();
        Date d1 = new Date(115, 5, 30, 12, 0);
        if (d1.before(d)) {
            throw new RuntimeException("Utilisation possible que dans le cadre du cours !");
        } else if (previous == null) {
            throw new IllegalStateException("Cannot create a grid from a null grid.");
        } else {
            this.size = previous.size;
            this.secret1(previous.tuiles);
        }
    }

    public void empty() {
        this.tuiles = null;
        this.tuiles = new Tuile[this.size][this.size];
    }

    private void secret1(Tuile[][] previousState) {
        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                Tuile tile = previousState[x][y];
                if (tile != null) {
                    this.tuiles[x][y] = tile;
                }
            }
        }

    }

    public Tuile getRandomAvailableCell() {
        ArrayList<Tuile> tiles = this.secret2();
        int availableTuileSize = tiles.size();
        if (availableTuileSize > 0) {
            Random rand = new Random();
            int randNum = rand.nextInt(availableTuileSize);
            return (Tuile)tiles.get(randNum);
        } else {
            return null;
        }
    }

    private ArrayList<Tuile> secret2() {
        ArrayList<Tuile> availableCells = new ArrayList();

        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                Tuile tile = this.tuiles[x][y];
                if (tile == null) {
                    availableCells.add(new Tuile(x, y, 0));
                }
            }
        }

        return availableCells;
    }

    public boolean areCellsAvailable() {
        return this.secret2().size() > 0;
    }

    public boolean isCellAvailable(Tuile tuile) {
        return this.getUneTuile(tuile.getX(), tuile.getY()) == null;
    }

    public boolean isCellOccupied(Tuile tuile) {
        return this.getUneTuile(tuile.getX(), tuile.getY()) != null;
    }

    public Tuile getUneTuile(int x, int y) {
        return this.withinBounds(x, y) ? this.tuiles[x][y] : null;
    }

    public void insertTuile(Tuile tile) {
        if (!this.withinBounds(tile)) {
            throw new IllegalStateException("The tile is not within range:" + tile.toString());
        } else {
            this.tuiles[tile.getX()][tile.getY()] = tile;
        }
    }

    public void removeTuile(Tuile tile) {
        this.removeTuile(tile.getX(), tile.getY());
    }

    public void removeTuile(int x, int y) {
        if (!this.withinBounds(x, y)) {
            throw new IllegalStateException("The tile is not within range. x: " + x + "; y: " + y);
        } else {
            this.tuiles[x][y] = null;
        }
    }

    public boolean withinBounds(Tuile tile) {
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

                Tuile tile = this.tuiles[x][y];
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

    public Tuile[][] getTuiles() {
        return this.tuiles;
    }

    public void setTuiles(Tuile[][] tuiles) {
        this.tuiles = tuiles;
    }
}
