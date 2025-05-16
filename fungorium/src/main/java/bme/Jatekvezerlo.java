package bme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Jatekvezerlo osztály definiciója
 */
public class Jatekvezerlo implements Serializable {

  private static final long serialVersionUID = 1L;
  /** A jelenlegi kör száma */
  private int jelenlegiKor;

  /** Tektonok listája */
  private List<Tekton> tektonok;

  /** Játékosok listája */
  List<Jatekos> jatekosok;

  /** Jelenlegi játékos száma */
  private int jelenlegiJatekos;

  /** Hány körből áll egy játék össz */
  private int jatekHossz;

  /** Engedélyezve van-e a random */
  private boolean random;

  /** A játék térképje */
  private Terkep terkep;

  /** Felvett random osztály */
  @SuppressWarnings("unused")
  private Random r;


  public Jatekvezerlo() {
    jatekosok = new ArrayList<Jatekos>();
    jelenlegiKor = 0;
    tektonok = new ArrayList<Tekton>();
    jelenlegiJatekos = 0;
    jatekHossz = 50;
    random = false;
    terkep = new Terkep();
  }

  /**
   * Konstruktor, ami játékosok inicializásával
   * @param jatekosok a játékosok listája
   */
  public Jatekvezerlo(List<Jatekos> jatekosok){
    this.jatekosok = jatekosok;
    jelenlegiKor = 0;
    tektonok = new ArrayList<Tekton>();
    jelenlegiJatekos = 0;
    jatekHossz = 50;
    random = false;
    terkep = new Terkep();
  }

  public Jatekvezerlo(List<Jatekos> jatekosok, int jatekHossz, boolean random) {
    this.jatekosok = jatekosok;
    this.jatekHossz = jatekHossz;
    this.random = random;
    if (random) {
      r = new Random();
    }
    jelenlegiKor = 0;
    tektonok = new ArrayList<Tekton>();
    jelenlegiJatekos = 0;
    terkep =  new Terkep();
  }

  /**
   * Publikus getter a jelenlegi körre.
   * @return a jelenlegi kör
   */
  public int getJelenlegiKor() {
    return jelenlegiKor;
  }

  /**
   * Publikus getter a tektonok listájára.
   * @return a játékban szereplő tektonok listája
   */
  public List<Tekton> getTektonok() {
    return tektonok;
  }

  /**
   * Publikus getter a játékosok listájára.
   * @return a játékban szereplő játékosok listája
   */
  public List<Jatekos> getJatekosok() {
    return jatekosok;
  }


  /**
   * publikus setter a jatekosok listara.
   * @param jatekosok listát kell megadni paraméterként.
   */
  public void setJatekosok(List<Jatekos> jatekosok) {
    this.jatekosok = jatekosok;
  }

  /**
   * Publikus getter a jelenlegi játékosra.
   * @return a jelenlegi játékos száma
   */
  public int getJelenlegiJatekos() {
    return jelenlegiJatekos;
  }

  public Jatekos getSoronLevoJatekos(){return jatekosok.get(jelenlegiJatekos);}

  /**
   * Publikus getter a játékhosszra.
   * @return a játék hosszúsága körökben.
   */
  public int getJatekHossz() {
    return jatekHossz;
  }

  /**
   * Publikus getter a randomra.
   * @return true, ha random engedélyezve van; false, ha nem.
   */
  public boolean getRandom() {
    return random;
  }

  /**
   * Publikus getter a térképre.
   * @return a játék térképe
   */
  public Terkep getTerkep() {
    return terkep;
  }

  /**
   * Setter a random változóra.
   * @param random true, ha random engedélyezve van; false, ha nem.
   */
  public void setRandom(boolean random) {
    this.random = random;
    if (random) {
      r = new Random();
    }
  }

  /**
   * A kör eleji lefutásokért felelős, minden körben meghívódik.
   */
  public void tick() {
    for (int i = 0; i < tektonok.size(); ++i)
      tektonok.get(i).tick();
  }

  /**
   *  A kör végén lefutó metódus, ami lezárja a kört.
   */
  public void korVege() {
    tick();
    //tektontHasit();
  }

  /**
   * Függvény egy körben a játékosok léptetésére.
   * @return true, ha volt játékos, aki lépett; false ha nem volt
   */
  public boolean korMenete() {
    for (int i = 0; i < jatekosok.size(); ++i) {
      if (jatekosok.get(i).lep())
        return true;
    }
    return false;
  }

  /**
   * Függveny a játek lezárására, és a nyertesek kiírására.
   */
  public void jatekVege() {
    int gombaszIndex = -1;
    int rovaraszIndex = -1;
    for (int i = 0; i < jatekosok.size(); ++i) {
      if (jatekosok.get(i).getType() == 0
          && (gombaszIndex == -1
              || jatekosok.get(i).getPontok() > jatekosok.get(gombaszIndex).getPontok())) {
        gombaszIndex = i;
      }
      if (jatekosok.get(i).getType() == 1
          && (rovaraszIndex == -1
              || jatekosok.get(i).getPontok() > jatekosok.get(rovaraszIndex).getPontok())) {
        rovaraszIndex = i;
      }
    }
    System.out.println("Nyertes Rovarász: " + rovaraszIndex + ". játékos!");
    System.out.println("Nyertes Gombász: " + gombaszIndex + ". játékos!");
  }

  /**
   * Függvény a játék kezdésére és pörgetésére.
   */
  public void jatekKezdes() {
    jatekHossz = 50;
    if (init()) {
      return;
    }
    for (jelenlegiKor = 0; jelenlegiKor < 50; ++jelenlegiKor) {
      if (korMenete()) {
        return;
      }
      korVege();
    }
  }

  /**
   * Játékos hozzáadása a nyilvántartáshoz.
   * 
   * @param jatekos a hozáadandó játékos
   */
  public void addJatekos(Jatekos jatekos) {
    jatekosok.add(jatekos);
  }

  /**
   * fuggveny a jatek inicializalasahoz parancssorrol
   * 
   * @throws InvalidAttributeValueException
   */
  public boolean init() {return false;} // 22x22 mezős, random tektonos pálya

  /**
   * fuggveny tekton id-jenek lekerdezesere
   * 
   * @param t a tekton, aminek idjere kivancsiak vagyunk
   * @return a keresett id
   */
  public int getIDof(Tekton t) {
    for (int i = 0; i < tektonok.size(); ++i) {
      if (t == tektonok.get(i))
        return i;
    }
    return -1;
  }

  /**
   * Függvény egy játékos azonosítójának lekérdezésére.
   * 
   * @param j a jatekos, aminek idjere kivancsiak vagyunk
   * @return a keresett id, -1, ha nincs ilyen játékos
   */
  public int getIDof(Jatekos j) {
    if (jatekosok.contains(j)) {
      return jatekosok.indexOf(j);
    }
    return -1;
  }
}
