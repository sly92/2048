import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

public class MoteurJeu extends Observable {
    private static final int TAILLE_GRILLE_DEFAUT = 4;
    public static final int HAUT = 0;
    public static final int DROITE = 1;
    public static final int BAS = 2;
    public static final int GAUCHE = 3;
    private static final int START_TILE_COUNT = 2;
    private int size;
    private int score;
    private GrilleTuiles grilleJeu;
    private int nombreDeplacements;
    private boolean partieGagnee;
    private boolean partieTerminee;
    private boolean secret1;
    private ArrayList<int[]> SECRET_1 = null;
    private static final int SECRET_2 = 0;
    private static final int SECRET_3 = 1;
    private static MoteurJeu secret2;

    private MoteurJeu(int size) {
        this.size = size;
        this.nombreDeplacements = 0;
        this.SECRET_1 = new ArrayList();
        this.SECRET_1.add(new int[]{-1, 0});
        this.SECRET_1.add(new int[]{0, 1});
        this.SECRET_1.add(new int[]{1, 0});
        this.SECRET_1.add(new int[]{0, -1});
    }

    protected static MoteurJeu secret3(int size) {
        if (secret2 == null) {
            secret2 = new MoteurJeu(size);
        }

        return secret2;
    }

    public static MoteurJeu getInstance() {
        Date d = new Date();
        Date d1 = new Date(115, 5, 30, 12, 0);
        if (d1.before(d)) {
            throw new RuntimeException("Utilisation possible que dans le cadre du cours !");
        } else {
            return secret3(4);
        }
    }

    public int getTaille() {
        return this.size;
    }

    public int getScore() {
        return this.score;
    }

    public GrilleTuiles getGrilleTuiles() {
        return this.grilleJeu;
    }

    public int getNombreDeplacements() {
        return this.nombreDeplacements;
    }

    public boolean partieGagnee() {
        return this.partieGagnee;
    }

    public boolean partieTerminee() {
        return this.partieTerminee;
    }

    public void setup() {
        this.grilleJeu = new GrilleTuiles(this.size);
        this.secret5();
        this.fireJeuChanged();
    }

    public void recommencerPartie() {
        this.partieGagnee = false;
        this.partieTerminee = false;
        this.grilleJeu = null;
        this.score = 0;
        this.nombreDeplacements = 0;
        this.setup();
    }

    private void secret5() {
        for(int i = 0; i < 2; ++i) {
            this.secret6();
        }

    }

    private void secret6() {
        if (this.grilleJeu.areCellsAvailable()) {
            int value = Math.random() < 0.5D ? 2 : 4;
            Tuile tile = this.grilleJeu.getRandomAvailableCell();
            tile.setValue(value);
            this.grilleJeu.insertTuile(tile);
        }

    }

    private void secret7() {
        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                Tuile tile = this.grilleJeu.getTuiles()[x][y];
                if (tile != null) {
                    tile.setTuileAFusionnerAvec((Tuile)null);
                    tile.savePosition();
                }
            }
        }

    }

    private void secret8(Tuile tileToMove, Tuile cellToMoveTo) {
        this.grilleJeu.getTuiles()[tileToMove.getX()][tileToMove.getY()] = null;
        this.grilleJeu.getTuiles()[cellToMoveTo.getX()][cellToMoveTo.getY()] = tileToMove;
        tileToMove.mettreAJourPositionAvec(cellToMoveTo);
    }

    public void deplacer(int direction) {
        Date d = new Date();
        Date d1 = new Date(115, 5, 30, 12, 0);
        if (d1.before(d)) {
            throw new RuntimeException("Utilisation possible que dans le cadre du cours !");
        } else if (!this.secret15()) {
            int[] vector = this.secret13(direction);
            int[] xTraversal = this.secret10(vector);
            int[] yTraversal = this.secret11(vector);
            this.secret7();
            boolean moved = false;

            for(int i = 0; i < this.size; ++i) {
                int x = xTraversal[i];

                for(int j = 0; j < this.size; ++j) {
                    int y = yTraversal[j];
                    Tuile tile = this.grilleJeu.getUneTuile(x, y);
                    if (tile != null) {
                        Tuile[] positions = this.secret18(tile, vector);
                        Tuile next = positions[1];
                        if (next != null && this.grilleJeu.withinBounds(next) && next.getValue() == tile.getValue() && next.getTuileAFusionnerAvec() == null) {
                            Tuile mergedTuile = new Tuile(next, tile.getValue() * 2);
                            mergedTuile.setTuileAFusionnerAvec(next);
                            this.grilleJeu.insertTuile(mergedTuile);
                            this.grilleJeu.removeTuile(tile);
                            tile.mettreAJourPositionAvec(next);
                            this.score += mergedTuile.getValue();
                            if (mergedTuile.getValue() == 2048) {
                                this.partieGagnee = true;
                            }
                        } else {
                            this.secret8(tile, positions[0]);
                        }

                        if (!this.secret19(x, y, tile.getX(), tile.getY())) {
                            moved = true;
                        }
                    }
                }
            }

            if (moved) {
                this.secret6();
                if (!this.secret16()) {
                    this.partieTerminee = true;
                }

                ++this.nombreDeplacements;
                this.fireJeuChanged();
            }

        }
    }

    private int[] secret10(int[] vector) {
        int[] xTraversal = new int[this.size];

        for(int i = 0; i < this.size; xTraversal[i] = i++) {
        }

        if (vector[0] == 1) {
            this.secret12(xTraversal);
        }

        return xTraversal;
    }

    private int[] secret11(int[] vector) {
        int[] yTraversal = new int[this.size];

        for(int i = 0; i < this.size; yTraversal[i] = i++) {
        }

        if (vector[1] == 1) {
            this.secret12(yTraversal);
        }

        return yTraversal;
    }

    private int[] secret12(int[] array) {
        for(int i = 0; i < array.length / 2; ++i) {
            int temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }

        return array;
    }

    private int[] secret13(int direction) {
        return (int[])this.SECRET_1.get(direction);
    }

    private void secret14() {
        this.secret1 = true;
    }

    private boolean secret15() {
        return this.partieTerminee || this.partieGagnee && !this.secret1;
    }

    private boolean secret16() {
        return this.grilleJeu.areCellsAvailable() || this.secret17();
    }

    private boolean secret17() {
        Tuile tile = null;

        for(int x = 0; x < this.size; ++x) {
            for(int y = 0; y < this.size; ++y) {
                tile = this.grilleJeu.getUneTuile(x, y);
                if (tile != null) {
                    for(int i = 0; i < 4; ++i) {
                        int[] vector = this.secret13(i);
                        Tuile cell = this.grilleJeu.getUneTuile(x + vector[0], y + vector[1]);
                        if (cell != null && cell.getValue() == tile.getValue()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    private Tuile[] secret18(Tuile tile, int[] vector) {
        Tuile previous = null;

        do {
            previous = tile;
            tile = this.grilleJeu.getUneTuile(tile.getX() + vector[0], tile.getY() + vector[1]);
            if (tile == null) {
                tile = new Tuile(previous.getX() + vector[0], previous.getY() + vector[1], previous.getValue());
            }
        } while(this.grilleJeu.withinBounds(tile) && this.grilleJeu.isCellAvailable(tile));

        return new Tuile[]{previous, tile};
    }

    private boolean secret19(int x1, int y1, int x2, int y2) {
        return x1 == x2 && y1 == y2;
    }

    private void fireJeuChanged() {
        this.setChanged();
        this.notifyObservers();
    }
}
