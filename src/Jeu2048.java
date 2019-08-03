import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Jeu2048 extends JFrame {

    private static final long serialVersionUID = 1L;

    private MoteurJeu mj;
    private JTextArea tuiles;
    private JButton bRaz;
    private JButton bRecommencer;
    private JPanel jBtVisuJeu;
    private JTextField jtf_score = null;
    private JTextField jtf_deplacement = null;
    private JTextField jtf_meilleur_score = null;
    //private boolean IHMconfigure;
    private JButton[][] grilleJButton = null;


    public Jeu2048(MoteurJeu m) {
        super("2048!");
        this.mj = m;
        this.mj.setup();

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
    }


    public JPanel creePanelHaut() {
        JPanel pHaut = new JPanel(new FlowLayout()); // ?
        this.jtf_score = new JTextField(10);
        this.jtf_deplacement = new JTextField(10);
        jtf_score.setEditable(false);
        jtf_deplacement.setEditable(false);
        pHaut.add(new JLabel("Score courant"));
        pHaut.add(jtf_score);
        pHaut.add(new JLabel("Nombre de déplacements"));
        pHaut.add(jtf_deplacement);
        return pHaut;
    }

    public JPanel creePanelBas() {
        BoutonListener b = new BoutonListener();

        JPanel pBas = new JPanel(new FlowLayout());

        JButton recommencer = new JButton("Recommencer");
        recommencer.addActionListener(b);
        pBas.add(recommencer);

        JButton raz = new JButton("RAZ Jeu");
        raz.addActionListener(b);
        raz.setEnabled(false);
        pBas.add(raz);
        return pBas;
    }

    public JPanel creePanelDroite() {
        JPanel pDroite = new JPanel(new FlowLayout());
        this.jtf_meilleur_score = new JTextField(10);
        jtf_meilleur_score.setEditable(false);
        pDroite.add(jtf_meilleur_score);


        return pDroite;
    }


    public JTabbedPane creePanelCentre() {

        BoutonListener b = new BoutonListener();
        JTabbedPane tabPane = new JTabbedPane();
        JPanel panTexte = new JPanel(new BorderLayout());
        JPanel jBtVisuJeu = new JPanel(new GridLayout(4, 4));
        tuiles = new JTextArea();
        tuiles.setText(mj.getGrilleTuiles().toString());
        panTexte.add(tuiles);
        tabPane.add("1", panTexte);
        panTexte.setFont(new Font("Arial", Font.BOLD, 50));
        tabPane.add("2", jBtVisuJeu);
        this.grilleJButton = new JButton[this.mj.getTaille()][this.mj.getTaille()];
        for (int i = 0; i < mj.getTaille(); i++) {
            for (int j = 0; j < mj.getTaille(); j++) {
                grilleJButton[i][j] = new JButton("");
                grilleJButton[i][j].setBackground(Color.WHITE);
                jBtVisuJeu.add(grilleJButton[i][j]);

            }
        }
        refreshGrille();
        panTexte.add(tuiles);
        return tabPane;

    }

    public void refreshGrille() {

        for (int i = 0; i < mj.getTaille(); i++) {
            for (int j = 0; j < mj.getTaille(); j++) {
                if (mj.getGrilleTuiles().getUneTuile(i, j) == null) {
                    grilleJButton[i][j].setText("");
                    grilleJButton[i][j].setBackground(Color.WHITE);
                } else {
                    grilleJButton[i][j].setText("" + mj.getGrilleTuiles().getUneTuile(i, j).getValue());
                }


            }

        }

    }

    public class BoutonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            String s = e.getActionCommand();

            if (s.equals("Recommencer")) {
                Jeu2048.this.mj.recommencerPartie();
            } else if (s.equals("RAZ Jeu")) {
                Jeu2048.this.mj.recommencerPartie();
            }
            refreshGrille();
        }

    }

    public class MouseAction implements MouseListener {


        public void mouseClicked(MouseEvent e) {

            Point p = e.getPoint();


            if (p.equals(grilleJButton[1][0])) {

                Jeu2048.this.mj.deplacer(MoteurJeu.HAUT);
            } else if (p.equals(grilleJButton[0][1])) {

                Jeu2048.this.mj.deplacer(MoteurJeu.GAUCHE);
            } else if (p.equals(grilleJButton[3][1])) {

                Jeu2048.this.mj.deplacer(MoteurJeu.DROITE);
            } else if (p.equals(grilleJButton[1][3])) {

                Jeu2048.this.mj.deplacer(MoteurJeu.BAS);
            }

            jtf_score.setText("" + mj.getScore());
            jtf_deplacement.setText("" + mj.getNombreDeplacements());
            refreshGrille();


            Jeu2048.this.tuiles.setText(mj.getGrilleTuiles().toString());
        }


        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub

        }


        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub

        }


        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub

        }


        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub

        }
    }
}
