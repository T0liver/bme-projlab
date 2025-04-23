package bme;

/**
 * Játékos osztály definíciója.
 * 
 * A kétféle játékos (Gombász és Rovarász) ősosztálya,
 * tárolja a játékos pontszámát és deklarálja a lépésért felelős függvényt
 */
public abstract class Jatekos {

    private int pontok = 0; // -ról indul a játék

    /**
     * Publikus getter függvény a Játékos pontszámának lekérdezésére.
     *
     * @return a játékos pontszáma
     */
    public int getPontok() {
        return pontok;
    }

    /**
     * A lépésért felelős függvény deklarációja,
     * ezt felülírják a leszármazottak (gombász, rovarász)
     */
    public abstract void lep();

    public abstract int getType();

    /**
     * Publikus tagfüggvény a játékos pontszámának növelésére.
     * 
     * @param mennyit mennyivel növeljük a játékos pontszámát
     * @return az új pontszám
     */
    public int addPontok(int mennyit) {
        pontok += mennyit;
        return pontok;
    }
}
