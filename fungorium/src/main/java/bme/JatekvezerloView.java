package bme;


import java.util.List;

import javax.swing.JFrame;

public class JatekvezerloView {
    /**
     * a körök lebonyolításáért felelős vezérlő
     */
    private Jatekvezerlo jatekvezerlo;

    private TerkepView terkepView;
    boolean[] jatekoslatszik = {true, true, true, true, true, true, true, true, true, true};

    /**
     * Publikus konstruktor a view inicializálására
     * @param jatekvezerlo a körök lebonyolításáért felelős vezérlő
     */
    public JatekvezerloView(Jatekvezerlo jatekvezerlo) {
        this.jatekvezerlo = jatekvezerlo;
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

    public void draw(JFrame gw) {
        if (terkepView == null) terkepView = new TerkepView(jatekvezerlo.getTerkep());
        List<Jatekos> jatekosok = jatekvezerlo.getJatekosok();
        for (int i = 0; i < jatekosok.size(); ++i) {
            if (jatekoslatszik[i]) {
                JatekosView jv = new JatekosView(jatekosok.get(i));
                jv.draw(gw);
            }
        }
        terkepView.draw(gw);
        
        /*JatekosMenu jm = new JatekosMenu(jatekosok.get(jatekvezerlo.getJelenlegiJatekos()));
        JatekosMenuView jmv = new JatekosMenuView(jm);
        jmv.draw(gw);*/
    }

    public boolean swapLatszik(int i) {
        if (i < 0 || i > 9) return false;
        jatekoslatszik[i] = !jatekoslatszik[i];
        return jatekoslatszik[i];
    }
}
