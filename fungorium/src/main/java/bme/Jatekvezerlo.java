package bme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Jatekvezerlo osztály implementációja
 *
 * <p>A játékmenethez tartozó fontos információk tárolásáért, nyilvántartásáért, a körök és
 * játékosok léptetéséért, pontok számolásáért, és a játék menetéért felelős osztály.
 *
 * @author Oliver
 */
public class Jatekvezerlo implements Serializable {

  /** Szerializáláshoz szükséges azonosító */
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
  @SuppressWarnings("unused")
  private boolean random;

  /** A játék térképje */
  private Terkep terkep;

  /** a játék ablaka */
  private GameWindow gameWindow;

  /** a játékvezérlő nézeti osztálya */
  private JatekvezerloView jatekvezerloview;

  /** Felvett random osztály */
  @SuppressWarnings("unused")
  private Random r = new Random();

  public Jatekvezerlo() {
    jatekosok = new ArrayList<Jatekos>();
    jelenlegiKor = 0;
    // tektonok = new ArrayList<Tekton>();
    jelenlegiJatekos = 0;
    jatekHossz = 50;
    random = false;
    terkep = new Terkep();
  }

  /**
   * Publikus setter a játékvezérlőview-ra
   *
   * @param jvv a beállítandó játékvezérlőview
   */
  public void setJatekvezerloView(JatekvezerloView jvv) {
    jatekvezerloview = jvv;
  }

  /**
   * Konstruktor, ami játékosok inicializásával
   *
   * @param jatekosok a játékosok listája
   */
  public Jatekvezerlo(List<Jatekos> jatekosok) {
    this.jatekosok = jatekosok;
    jelenlegiKor = 0;
    jelenlegiJatekos = 0;
    jatekHossz = 50;
    random = false;
    terkep = new Terkep();
  }

  /**
   * Konstruktor mentéshez
   *
   * @param s - save
   */
  public Jatekvezerlo(Save s) {
    this.jatekosok = s.getJatekos();
    this.jelenlegiKor = s.getJelenlegiKor();
    this.jelenlegiJatekos = s.getJelenlegiJatekos();
    jatekHossz = 50;
    random = false;
    this.terkep = s.getTerkep();
  }

  /**
   * Egyik paraméteres konstruktor
   *
   * @param jatekosok a beállítandó játékosok listája
   * @param jatekHossz a beállítandó játékhossz
   * @param random a random lesz-e
   */
  public Jatekvezerlo(List<Jatekos> jatekosok, int jatekHossz, boolean random) {
    this.jatekosok = jatekosok;
    this.jatekHossz = jatekHossz;
    this.random = random;
    if (random) {
      r = new Random();
    }
    jelenlegiKor = 0;
    jelenlegiJatekos = 0;
    terkep = new Terkep();
  }

  /**
   * Publikus getter a jelenlegi körre.
   *
   * @return a jelenlegi kör
   */
  public int getJelenlegiKor() {
    return jelenlegiKor;
  }

  /**
   * Publikus getter a játékosok listájára.
   *
   * @return a játékban szereplő játékosok listája
   */
  public List<Jatekos> getJatekosok() {
    return jatekosok;
  }

  /**
   * publikus setter a jatekosok listara.
   *
   * @param jatekosok listát kell megadni paraméterként.
   */
  public void setJatekosok(List<Jatekos> jatekosok) {
    this.jatekosok = jatekosok;
  }

  /**
   * Publikus getter a jelenlegi játékosra.
   *
   * @return a jelenlegi játékos száma
   */
  public int getJelenlegiJatekos() {
    return jelenlegiJatekos;
  }

  /**
   * Publikus getter a soron lévő játékosra
   *
   * @return a soron lévő játékos
   */
  public Jatekos getSoronLevoJatekos() {
    return jatekosok.get(jelenlegiJatekos);
  }

  /**
   * Publikus getter a játékhosszra.
   *
   * @return a játék hosszúsága körökben.
   */
  public int getJatekHossz() {
    return jatekHossz;
  }

  /**
   * Publikus getter a térképre.
   *
   * @return a játék térképe
   */
  public Terkep getTerkep() {
    return terkep;
  }

  /** A kör eleji lefutásokért felelős, minden körben meghívódik. */
  public void tick() {
    List<Tekton> tektonok = terkep.getTektonok();
    int n = tektonok.size();
    for (int i = 0; i < n; ++i) tektonok.get(i).tick();
    int hasad = r.nextInt(tektonok.size());
    List<Tekton> hasadt = tektonok.get(hasad).hasad();
    if (hasadt.size() > 1) {
      terkep.addTektonok(hasadt);
      terkep.removeTekton(hasad);
    }
  }

  /** A kör végén lefutó metódus, ami lezárja a kört. */
  public void korVege() {
    jelenlegiJatekos++;
    if (jelenlegiJatekos >= jatekosok.size()) {
      jelenlegiKor++;
      jelenlegiJatekos = 0;
      tick();
    }
    if (jelenlegiKor >= jatekHossz) {
      jatekvezerloview.jatekVege();
      jatekvezerloview.getJatekvezerlo().getGameWindow().altMenu();
    } else {
      jatekosok.get(jelenlegiJatekos).lep();
      if (gameWindow != null) {
        gameWindow.drawJatekvezerlo();
      }
    }
  }

  /**
   * Függvény egy körben a játékosok léptetésére.
   *
   * @return true, ha volt játékos, aki lépett; false ha nem volt
   */
  public boolean korMenete() {
    for (int i = 0; i < jatekosok.size(); ++i) {
      if (jatekosok.get(i).lep()) return true;
    }
    return false;
  }

  /** Függveny a játek lezárására, és a nyertesek kiírására. */
  public int[] jatekVege() {
    int gombaszIndex = -1;
    int rovaraszIndex = -1;
    int gP = -1, rP = -1;
    for (int i = 0; i < jatekosok.size(); ++i) {
      int type = jatekosok.get(i).getType();
      int points = jatekosok.get(i).getPontok();
      if (type == 0 && points > gP) {
        gP = points;
        gombaszIndex = jatekosok.get(i).getId();
      }
      if (type == 1 && points > rP) {
        rP = points;
        rovaraszIndex = jatekosok.get(i).getId();
      }
    }
    System.out.println("Nyertes Rovarász: " + rovaraszIndex + ". játékos!");
    System.out.println("Nyertes Gombász: " + gombaszIndex + ". játékos!");
    return new int[] {gombaszIndex, rovaraszIndex};
  }

  /** Függvény a játék kezdésére és pörgetésére. */
  public void jatekKezdes(int g, int r) {
    jatekHossz = 50;
    jelenlegiJatekos = 0;
    jelenlegiKor = 0;
    init(g, r);
    jatekosok.get(0).lep();
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
   * @throws InvalidAttributeValueException ha nem létezik az érték, hiba
   */
  public boolean init(int gombasznum, int rovarasznum) {
    terkep.init();
    jatekosok.clear();
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
      GombaFonal gf = new GombaFonal(t.getMezok().get(0));
      gf.setGombasz(gom);
      gom.addGombaFonal(gf);
      jatekosok.add(gom);
      gom.setId(jatekosok.size());
    }
    for (int i = 0; i < rovarasznum; ++i) {
      Rovarasz rov = new Rovarasz();
      rov.addRovar(new Rovar(rov, mezok.get(r.nextInt(mezok.size()))));
      jatekosok.add(rov);
      rov.setId(jatekosok.size());
    }
    return true;
  }

  /**
   * fuggveny tekton id-jenek lekerdezesere
   *
   * @param t a tekton, aminek idjere kivancsiak vagyunk
   * @return a keresett id
   */
  public int getIDof(Tekton t) {
    List<Tekton> tektonok = terkep.getTektonok();
    for (int i = 0; i < tektonok.size(); ++i) {
      if (t == tektonok.get(i)) return i;
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

  /**
   * Publikus setter a játékablakra
   *
   * @param gw a beállítandó játékablak
   */
  public void setGameWindow(GameWindow gw) {
    gameWindow = gw;
  }

  /**
   * Publikus getter a játékablakra
   *
   * @return a játékablak
   */
  public GameWindow getGameWindow() {
    return gameWindow;
  }
}
