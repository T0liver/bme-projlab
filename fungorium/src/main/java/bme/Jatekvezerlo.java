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
  private Random r = new Random();


  public Jatekvezerlo() {
    jatekosok = new ArrayList<Jatekos>();
    jelenlegiKor = 0;
    //tektonok = new ArrayList<Tekton>();
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
    //tektonok = new ArrayList<Tekton>();
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
    //tektonok = new ArrayList<Tekton>();
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
  //public List<Tekton> getTektonok() {
    //return tektonok;
  //}

  /**
   * Publikus getter a játékosok listájára.
   * @return a játékban szereplő játékosok listája
   */
  public List<Jatekos> getJatekosok() {
    //System.out.println("\n" + String.valueOf(jatekosok.size()));
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
    List<Tekton> tektonok = terkep.getTektonok();
    int n = tektonok.size();
    for (int i = 0; i < n; ++i)
      tektonok.get(i).tick();
  }

  /**
   *  A kör végén lefutó metódus, ami lezárja a kört.
   */
  public void korVege() {
    jelenlegiJatekos++;
    if (jelenlegiJatekos >= jatekosok.size()) {
      jelenlegiKor++;
      jelenlegiJatekos = 0;
      tick();
    }
    jatekosok.get(jelenlegiJatekos).lep();
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
  public void jatekKezdes(int g, int r) {
    jatekHossz = 50;
    jelenlegiJatekos = 0;
    jelenlegiKor = 1;
    init(g, r);
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
  public boolean init(int gombasznum, int rovarasznum) {
    terkep.init();
    List<Tekton> tektonok = terkep.getTektonok();
    List<Mezo> mezok = terkep.getMezok();
    for (int i = 0; i < gombasznum; ++i) {
      Gombasz gom = new Gombasz();
      Tekton t = tektonok.get(r.nextInt(tektonok.size()));
      while (t.getTermeketlen() || t.getFoglalt()) {
        t = tektonok.get(r.nextInt(tektonok.size()));
      }
      try {
        gom.addGombaTest(new GombaTest(gom, 5, t));
      } catch (Exception e) {
        System.out.println("Elméletileg nem kéne ennek történnie, a loop valid tektont keres.");
        e.printStackTrace();
      }
      gom.addGombaFonal(new GombaFonal(t.getMezok().get(0)));
      jatekosok.add(gom);
    }
    for (int i = 0; i < rovarasznum; ++i) {
      Rovarasz rov = new Rovarasz();
      rov.addRovar(new Rovar(rov, mezok.get(r.nextInt(mezok.size()))));
      jatekosok.add(rov);
    }
    return true;
  } // 22x22 mezős, random tektonos pálya

  /**
   * fuggveny tekton id-jenek lekerdezesere
   * 
   * @param t a tekton, aminek idjere kivancsiak vagyunk
   * @return a keresett id
   */
  public int getIDof(Tekton t) {
    List<Tekton> tektonok = terkep.getTektonok();
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
