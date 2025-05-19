package bme;


import java.util.List;

import javax.swing.*;

public class JatekvezerloView {
    /**
     * a körök lebonyolításáért felelős vezérlő
     */
    private Jatekvezerlo jatekvezerlo;

    private GameWindow gameWindow;

    private TerkepView terkepView;
    boolean[] jatekoslatszik = {true, true, true, true, true, true, true, true, true, true};

    private JatekosMenuView jatekosMenuView;

    /**
     * Publikus konstruktor a view inicializálására
     * @param jatekvezerlo a körök lebonyolításáért felelős vezérlő
     */
    public JatekvezerloView(Jatekvezerlo jatekvezerlo, GameWindow gw) {
        this.jatekvezerlo = jatekvezerlo;
        gameWindow = gw;
        jatekvezerlo.setJatekvezerloView(this);
        //jatekvezerlo.init();
        //terkepView = new TerkepView(jatekvezerlo.getTerkep());
    }

    /**
     * Publikus getter a játék játékvezérlőjének lekérdezésére.
     * @return
     */
    public Jatekvezerlo getJatekvezerlo() {
        return jatekvezerlo;
    }

    public void setJatekosMenuView(JatekosMenuView jmv) { jatekosMenuView = jmv; }

    public void draw(GameWindow gw) {
        if (terkepView == null) terkepView = new TerkepView(jatekvezerlo.getTerkep());
        List<Jatekos> jatekosok = jatekvezerlo.getJatekosok();
        for (int i = 0; i < jatekosok.size(); ++i) {
            if (jatekoslatszik[i]) {
                JatekosView jv = new JatekosView(jatekosok.get(i));
                jv.draw(gw);
            }
        }
        terkepView.setJatekvezerlo(jatekvezerlo);
        terkepView.setJatekosMenuView(jatekosMenuView);
        terkepView.draw(gw);
        
        /*JatekosMenu jm = new JatekosMenu(jatekosok.get(jatekvezerlo.getJelenlegiJatekos()));
        JatekosMenuView jmv = new JatekosMenuView(jm);
        jmv.draw(gw);*/
    }

    public void kovetkezoKor() {
        jatekvezerlo.korVege();
        if (jatekvezerlo.getJelenlegiKor() == jatekvezerlo.getJatekHossz()) {
            jatekvezerlo.jatekVege();
            return;
        }
        draw(gameWindow);
    }

    public Jatekos getSoronLevoJatekos() {
        return jatekvezerlo.getSoronLevoJatekos();
    }

    public boolean swapLatszik(int i) {
        if (i < 0 || i > 9) return false;
        jatekoslatszik[i] = !jatekoslatszik[i];
        return jatekoslatszik[i];
    }

    public void jatekVege() {
        int[] nyertesek = jatekvezerlo.jatekVege(); // 0. gombasz 1. rovarasz

        JOptionPane.showMessageDialog(null, "Nyertesek:\n"+
                "Gombász "+ (nyertesek[0] + 1) + "\n"
                +"Rovarász "+ (nyertesek[1] + 1));
    }
}
