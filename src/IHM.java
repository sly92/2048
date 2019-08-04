import sun.plugin2.util.ColorUtil;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class IHM extends JFrame implements ColorsInterface{

    private static final long serialVersionUID = 1L;

    private GameEngine ge;
    private JTextField jtf_score = null;
    private JTextField jtf_move = null;
    private JTextField jtf_best_score = null;
    private JButton[][] JButtonGrid = null;

    public IHM(GameEngine m) {
        super("2048");
        this.ge = m;
        this.ge.setup();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        setSize((int) d.getWidth() / 2, (int) d.getHeight() / 2);
        initialise();
    }

    public void initialise() {
        this.setLayout(new BorderLayout());
        this.add(creePanelBas(), BorderLayout.SOUTH);
        this.add(creePanelHaut(), BorderLayout.NORTH);
        this.add(creePanelDroite(), BorderLayout.EAST);
        this.add(creePanelCentre(), BorderLayout.CENTER);
        this.addKeyListener(new MyKeyListener());
    }


    public JPanel creePanelHaut() {
        JPanel pHaut = new JPanel(new FlowLayout()); // ?
        this.jtf_score = new JTextField(10);
        this.jtf_move = new JTextField(10);
        jtf_score.setEditable(false);
        jtf_move.setEditable(false);
        pHaut.add(new JLabel("Score courant"));
        pHaut.add(jtf_score);
        pHaut.add(new JLabel("Nombre de déplacements"));
        pHaut.add(jtf_move);
        return pHaut;
    }

    public JPanel creePanelBas() {
        BoutonListener b = new BoutonListener();
        JPanel pBas = new JPanel(new FlowLayout());

        JButton bRestart = new JButton("Restart");
        bRestart.addActionListener(b);
        bRestart.setFocusable(false);
        pBas.add(bRestart);
        return pBas;
    }

    public JPanel creePanelDroite() {
        JPanel pDroite = new JPanel(new FlowLayout());
        this.jtf_best_score = new JTextField(10);
        jtf_best_score.setEditable(false);
        pDroite.add(jtf_best_score);
        return pDroite;
    }


    public JPanel creePanelCentre() {

        JPanel jBtGrid = new JPanel(new GridLayout(4, 4));
        this.JButtonGrid = new JButton[this.ge.getSize()][this.ge.getSize()];
        for (int i = 0; i < ge.getSize(); i++) {
            for (int j = 0; j < ge.getSize(); j++) {
                JButtonGrid[i][j] = new JButton("");
                JButtonGrid[i][j].setBackground(Color.WHITE);
                jBtGrid.add(JButtonGrid[i][j]);

            }
        }

        gridRefresh();
        return jBtGrid;

    }

    public void gridRefresh() {

        for (int i = 0; i < ge.getSize(); i++) {
            for (int j = 0; j < ge.getSize(); j++) {
                if (ge.getTilesGrid().getOneTile(i, j) == null) {
                    JButtonGrid[i][j].setText("");
                    JButtonGrid[i][j].setBackground(Color.WHITE);
                } else {
                   colorTiles(i,j);
                }
            }
        }
    }

    public void colorTiles(int i,int j){
        JButtonGrid[i][j].setText(""+ge.getTilesGrid().getOneTile(i, j).getValue());
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==2)JButtonGrid[i][j].setBackground(TUILE_COLOR_2);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==4)JButtonGrid[i][j].setBackground(TUILE_COLOR_4);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==8)JButtonGrid[i][j].setBackground(TUILE_COLOR_8);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==16)JButtonGrid[i][j].setBackground(TUILE_COLOR_16);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==32)JButtonGrid[i][j].setBackground(TUILE_COLOR_32);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==64)JButtonGrid[i][j].setBackground(TUILE_COLOR_64);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==128)JButtonGrid[i][j].setBackground(TUILE_COLOR_128);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==256)JButtonGrid[i][j].setBackground(TUILE_COLOR_256);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==512)JButtonGrid[i][j].setBackground(TUILE_COLOR_512);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==1024)JButtonGrid[i][j].setBackground(TUILE_COLOR_1024);
        if (ge.getTilesGrid().getOneTile(i, j).getValue() ==2048)JButtonGrid[i][j].setBackground(TUILE_COLOR_2048);
    }

    class MyKeyListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == 38) {
                IHM.this.ge.move(GameEngine.HAUT);
            } else if (e.getKeyCode() == 37) {
                IHM.this.ge.move(GameEngine.GAUCHE);
            } else if (e.getKeyCode() == 39) {
                IHM.this.ge.move(GameEngine.DROITE);
            } else if (e.getKeyCode() == 40) {
                IHM.this.ge.move(GameEngine.BAS);
            }

            jtf_score.setText("" + ge.getScore());
            jtf_move.setText("" + ge.getNbMove());
            gridRefresh();
        }

    }

    public class BoutonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String s = e.getActionCommand();

            if (s.equals("Restart")) {
                IHM.this.ge.restart();
            }
            gridRefresh();

        }

    }
}

