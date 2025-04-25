package bme;

/**
 * Játékos osztály definíciója.
 *
 * <p>A kétféle játékos (Gombász és Rovarász) ősosztálya, tárolja a játékos pontszámát és deklarálja
 * a lépésért felelős függvényt
 */
public abstract class Jatekos {

  private int pontok = 0; // -ról indul a játék
  private String nev; // a játékos neve

  private int id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  /**
   *
   * @param nev
   * Konstruktorában megadható a név paraméter
   * A leszármazottakban fognak kezelődni
   */
  public Jatekos(String nev) {
    this.nev = nev;
  }

  /**
   * Publikus getter függvény a Játékos nevének lekérdezésére.
   * @return a játékos neve
   */
  public String getNev() {
    return nev;
  }

  /**
   * Publikus getter függvény a Játékos pontszámának lekérdezésére.
   *
   * @return a játékos pontszáma
   */
  public int getPontok() {
    return pontok;
  }

  /**
   * A lépésért felelős függvény deklarációja, ezt felülírják a leszármazottak (gombász, rovarász)
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
