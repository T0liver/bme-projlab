package bme;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Játékos osztály definíciója.
 *
 * <p>A kétféle játékos (Gombász és Rovarász) ősosztálya, tárolja a játékos pontszámát és deklarálja
 * a lépésért felelős függvényt.
 *
 * @author Vid
 */
public abstract class Jatekos implements Jatekelem, Serializable {

  /** Játékos pontjai, kezdetben 0. */
  protected int pontok = 0;

  /** Játékos neve */
  private String nev;

  /** Játékos színe */
  protected Color szin;

  /** Játékoshoz tartozó akciók listája. */
  protected List<Akcio> akciok = new ArrayList<>();

  /** Éppen aktuális akció. */
  private Akcio aktivAkcio = null;

  /** Objektum azonosító. */
  private int id;

  /**
   * Publikus getter az objektum azonosítóra.
   *
   * @return az objektum azonosítója.
   */
  public int getId() {
    return id;
  }

  /**
   * Publikus setter az objektum azonosítóra.
   *
   * @param id a beállítandó azonosító.
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Parameteres konstruktor
   *
   * @param nev Konstruktorában megadható a név paraméter A leszármazottakban fognak kezelődni
   */
  public Jatekos(String nev, Color szin) {
    this.nev = nev;
    this.szin = szin;
  }

  /**
   * Parameter nelkuli konstruktor
   *
   * @param nev Konstruktorában megadható a név paraméter A leszármazottakban fognak kezelődni
   */
  public Jatekos() {
    this.nev = "";
    Random random = new Random();
    this.szin = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
  }

  /**
   * Publikus getter függvény a Játékos nevének lekérdezésére.
   *
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

  public Color getSzin() {
    return szin;
  }

  /**
   * A lépésért felelős függvény deklarációja, ezt felülírják a leszármazottak (gombász, rovarász)
   */
  public abstract boolean lep();

  /** jatekos tipusat megado fuggveny */
  public int getType() {
    return -1;
  }

  /** A class adatait kiiro fuggveny. */
  public void printData() {}

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

  /**
   * Publikus fuggveny gombatest hozzaadasara, gombasz hasznalja.
   *
   * @param gt a hozzáadandó gombatest
   */
  public void addGombaTest(GombaTest gt) {}

  /**
   * publikus fuggveny gombafonal hozzaadasara, gombasz hasznalja.
   *
   * @param gt a hozzáadandó gombafonal
   */
  public void addGombaFonal(GombaFonal gt) {}

  /**
   * publikus fuggveny rovar hozzaadasara, rovarasz hasznalja.
   *
   * @param gt a hozzáadandó rovar
   */
  public void addRovar(Rovar gt) {}

  /**
   * publikus fuggveny rovarok lekerdezesere, rovarasz hasznalja.
   *
   * @return rovarok üres listája
   */
  public List<Rovar> getRovarok() {
    return new ArrayList<>();
  }

  /**
   * publikus fuggveny gombatestek lekerdezesere, gombasz hasznalja.
   *
   * @return gombatestek üres listája
   */
  public List<GombaTest> getGombaTestek() {
    return new ArrayList<>();
  }

  /**
   * publikus fuggveny gombafonalak lekerdezesere, gombasz hasznalja.
   *
   * @return gombafonalak üres listája
   */
  public List<GombaFonal> getGombaFonalak() {
    return new ArrayList<>();
  }

  /**
   * publikus fuggveny sporak lekerdezesere, gombasz hasznalja.
   *
   * @return sporak üres listája
   */
  public List<Spora> getSporak() {
    return new ArrayList<>();
  }

  /**
   * Publikus getter az akciókra.
   *
   * @return a játékos akciói.
   */
  public List<Akcio> getAkciok() {
    return akciok;
  }

  /**
   * Publikus getter az aktuális akcióra.
   *
   * @return a játékos aktuális akciója.
   */
  public Akcio getAktivAkcio() {
    return aktivAkcio;
  }

  /**
   * Aktuális akció beállítása
   *
   * @param c az akció gyorselérési billenytűkaraktere (egyértelműen azonosítja), amit be kell
   *     állítani aktuális akcióként.
   * @return a beállított akció
   */
  public Akcio setAktivAkcio(char c) {
    for (int i = 0; i < akciok.size(); ++i) {
      if (akciok.get(i).getBetu() == c) {
        aktivAkcio = akciok.get(i);
        return aktivAkcio;
      }
    }
    return null;
  }

  /**
   * Publikus setter az aktuális akcióra
   *
   * @param a a beállítandó akció
   * @return a beállított akció
   */
  public Akcio setAktivAkcio(Akcio a) {
    aktivAkcio = a;
    return aktivAkcio;
  }

  public void akcio(Mezo clicked) {
    if (aktivAkcio != null) aktivAkcio.ujInput(clicked);
  }
}
