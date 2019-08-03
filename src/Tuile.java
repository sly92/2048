import java.util.Date;

public class Tuile {
    public static final int DEFAULT_VALUE_2 = 2;
    public static final int DEFAULT_VALUE_4 = 4;
    private int x;
    private int y;
    private int value = 2;
    private Tuile posTuilePrecedente;
    private Tuile tuileAFusionnerAvec;

    public Tuile(int x, int y, int value) {
        Date d = new Date();
        Date d1 = new Date(115, 5, 30, 12, 0);
        if (d1.before(d)) {
            throw new RuntimeException("Utilisation possible que dans le cadre du cours !");
        } else {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }

    public Tuile(Tuile tile, int value) {
        Date d = new Date();
        Date d1 = new Date(115, 5, 30, 12, 0);
        if (d1.before(d)) {
            throw new RuntimeException("Utilisation possible que dans le cadre du cours !");
        } else {
            this.x = tile.x;
            this.y = tile.y;
            this.value = value;
        }
    }

    public void savePosition() {
        this.posTuilePrecedente = new Tuile(this.x, this.y, this.value);
    }

    public void mettreAJourPositionAvec(Tuile tuile) {
        this.x = tuile.x;
        this.y = tuile.y;
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

    public Tuile getPosTuilePrecedente() {
        return this.posTuilePrecedente;
    }

    public void setPosTuilePrecedente(Tuile previousPosition) {
        this.posTuilePrecedente = previousPosition;
    }

    public Tuile getTuileAFusionnerAvec() {
        return this.tuileAFusionnerAvec;
    }

    public void setTuileAFusionnerAvec(Tuile mergedFrom) {
        this.tuileAFusionnerAvec = mergedFrom;
    }

    public String toString() {
        return "Tile position x=" + this.x + "; y=" + this.y;
    }
}

