package bme;

import javax.swing.JFrame;

public class JatekvezerloView {
    /**
     * a körök lebonyolításáért felelős vezérlő
     */
    private Jatekvezerlo jatekvezerlo;

    private TerkepView terkepView;

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
        //terkepView.draw(gw);
    }
}
