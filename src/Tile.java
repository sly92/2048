public class Tile {
    private int x;
    private int y;
    private int value = 2;
    private Tile oldTilePos;
    private Tile tileToMerge;

    public Tile(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public Tile(Tile tile, int value) {
        this.x = tile.x;
        this.y = tile.y;
        this.value = value;
    }

    public void savePosition() {
        this.oldTilePos = new Tile(this.x, this.y, this.value);
    }

    public void setPos(Tile tile) {
        this.x = tile.x;
        this.y = tile.y;
    }

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return this.y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public Tile getOldTilePos() {
        return this.oldTilePos;
    }
    public void setOldTilePos(Tile previousPosition) {
        this.oldTilePos = previousPosition;
    }
    public Tile getTileToMerge() {
        return this.tileToMerge;
    }
    public void setTileToMerge(Tile mergedFrom) {
        this.tileToMerge = mergedFrom;
    }
    public String toString() {
        return "Tile position x=" + this.x + "; y=" + this.y;
    }
}

