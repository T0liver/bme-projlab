package bme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Játékos osztály definíciója.
 *
 * <p>A kétféle játékos (Gombász és Rovarász) ősosztálya, tárolja a játékos pontszámát és deklarálja
 * a lépésért felelős függvényt
 */
public abstract class Jatekos implements Jatekelem{

  protected int pontok = 0; // -ról indul a játék
  private String nev; // a játékos neve
  protected Color szin;


  private int id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Parameteres konstruktor
   * @param nev
   * Konstruktorában megadható a név paraméter
   * A leszármazottakban fognak kezelődni
   */
  public Jatekos(String nev, Color szin) {
    this.nev = nev;
    this.szin= szin;
  }
  /**
   * Parameter nelkuli konstruktor
   * @param nev
   * Konstruktorában megadható a név paraméter
   * A leszármazottakban fognak kezelődni
   */
  public Jatekos() {
    this.nev = "not given";
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

  public Color getSzin() {
    return szin;
  }

  /**
   * A lépésért felelős függvény deklarációja, ezt felülírják a leszármazottak (gombász, rovarász)
   */
  public abstract boolean lep();

  /**
   * jatekos tipusat megado fuggveny
   */
  public int getType() {return -1;}

  /**
   * A class adatait kiiro fuggveny.
   */
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
   * publikus fuggveny gombatest hozzaadasara, gombasz hasznalja.
   */
  public void addGombaTest(GombaTest gt) {}

  /**
   * publikus fuggveny gombafonal hozzaadasara, gombasz hasznalja.
   */
  public void addGombaFonal(GombaFonal gt) {}

  /**
   * publikus fuggveny rovar hozzaadasara, rovarasz hasznalja.
   */
  public void addRovar(Rovar gt) {}

  /**
   * publikus fuggveny rovarok lekerdezesere, rovarasz hasznalja.
   * @return rovarok üres listája
   */
  public List<Rovar> getRovarok() {return new ArrayList<>();}
}
