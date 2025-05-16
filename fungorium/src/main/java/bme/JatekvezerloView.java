package bme;

public class JatekvezerloView {
    /**
     * a körök lebonyolításáért felelős vezérlő
     */
    private Jatekvezerlo jatekvezerlo;

    /**
     * Publikus konstruktor a view inicializálására
     * @param jatekvezerlo a körök lebonyolításáért felelős vezérlő
     */
    public JatekvezerloView(Jatekvezerlo jatekvezerlo) {
        this.jatekvezerlo = jatekvezerlo;
    }

    /**
     * Publikus getter a játék játékvezérlőjének lekérdezésére.
     * @return
     */
    public Jatekvezerlo getJatekvezerlo() {
        return jatekvezerlo;
    }

    public void draw(GameWindow gw) {
        
    }
}
