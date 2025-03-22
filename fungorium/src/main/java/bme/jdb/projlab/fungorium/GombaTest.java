package bme.jdb.projlab.fungorium;

/**
 * GombaTest osztály definíciója.
 * 
 * @author Oliver
 */
public class GombaTest {
    private int sporadarab;
    private int elettartam;
    private boolean fejlett;
    private int fejlettseg;
    private Tekton tartozkodik;

    /**
     * * Az egyik publikus konstruktor függvény, ami beállítja az objektum alap tulajdonságait.
     * @param elett a gombatest alap élettartama
     */
    public GombaTest(int elett, Tekton hely) {
        sporadarab = 0;
        elettartam = elett;
        fejlett = false;
        fejlettseg = 0;
        tartozkodik = hely;
    }

    /**
     * Az egyik publikus konstruktor függvény, ami beállítja az objektum összes tulajdonságát.
     * @param sporadb a gombatest spóráinak a darabszáma
     * @param elett a gombatest kezdeti élettartama
     * @param fejlett jelző, hogy a gombatest fejlett-e
     * @param fejlettseg a gombatest fejlettségi szintje
     */
    public GombaTest(int sporadb, int elett, boolean fejlett, int fejlettseg, Tekton hely) {
        sporadarab = sporadb;
        elettartam = elett;
        this. fejlett = fejlett;
        this.fejlettseg = fejlettseg;
        tartozkodik = hely;
    }

    /**
     * Publikus getter függvény a gombatest spóra darabszámának lekérdezésére.
     * @return a gombatest spóra darabszáma
     */
    public int getSporaDarab() {
        return sporadarab;
    }

    /**
     * Publikus getter függvény a gombatest élettratamának lekérdezésére.
     * @return a gombatest élettartama
     */
    public int getElettartam() {
        return elettartam;
    }

    /**
     * Publikus getter függvény a gombatest fejlettségének lekérdezésére.
     * @return jelző, hogy fejlett-e a gombatest
     */
    public boolean getFejlett() {
        return fejlett;
    }

    /**
     * Publikus getter függvény a gombatest fejlettségi szintjének lekérdezésére.
     * @return a gombatest fejlettségi szintje
     */
    public int getFejlettseg() {
        return fejlettseg;
    }

    public Tekton getTartozkodik() {
        return tartozkodik;
    }

    public void setSporaDarab(int db) {
        sporadarab = db;
    }

    public void setElettartam(int ido) {
        elettartam = ido;
    }

    public void sporatSzor(Tekton hova) {

    }

    public void novekszik() {

    }

    public void elpusztul() {

    }

    public void eletcsokken() {

    }
}
