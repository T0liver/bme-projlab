package bme;

import java.util.List;
import java.util.Random;

/**
 * GombaTest osztály definíciója.
 *
 * <p>A gombatest egy tektonon élősködő organizmus, amelyből gombafonalak nőnek. Bizonyos idő
 * elteltével spórákat termel, melyeket a szomszédos tektonokra képes szóri, ezáltal segíti elő a
 * gombafonalak terjedését. Idővel a gombatest elkezd fejlődni és fejlettebb állapotában már a
 * szomszédos tektonok szomszédjaira is képes spórákat szórni. Minden tektonon csupán egyetlen
 * gombatest fejlődhet, amely elpusztul miután elég spórát szórt. A gombafonalak révén új
 * gombatestek alakulhatnak ki, ha elegendő spóra halmozódik fel azon a tektonon, ahol új testet
 * szeretnénk létrehozni.
 *
 * @author Oliver
 */
public class GombaTest implements Jatekelem{

  private int id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  private boolean fejlett;
  private int fejlettseg;
  private Gombasz gombasz;
  private int sporadarab;
  private Tekton tartozkodik;
  private int elettartam;

  /**
   * Az egyik publikus konstruktor függvény, ami beállítja az objektum alap tulajdonságait.
   *
   * @param elett a gombatest alap élettartama
   * @throws Exception ha vár foglalt a tekton, akkor nem tud rajta új gombatest elhelyezkedni
   */
  public GombaTest(Gombasz gombasz, int elett, Tekton hely) throws Exception {
    if (!hely.getFoglalt()) {
      sporadarab = 5;
      elettartam = elett;
      fejlett = false;
      fejlettseg = 0;
      tartozkodik = hely;
      this.gombasz = gombasz;
      hely.setFoglalt(true);
    } else {
      throw new Exception("A tekton már foglalt, nem lehet új gombatestet rátenni!");
    }
  }

  /**
   * Az egyik publikus konstruktor függvény, ami beállítja az objektum összes tulajdonságát.
   *
   * @param sporadb a gombatest spóráinak a darabszáma
   * @param elett a gombatest kezdeti élettartama
   * @param fejlett jelző, hogy a gombatest fejlett-e
   * @param fejlettseg a gombatest fejlettségi szintje
   * @throws Exception ha vár foglalt a tekton, akkor nem tud rajta új gombatest elhelyezkedni
   */
  public GombaTest(
      Gombasz gombasz, int sporadb, int elett, boolean fejlett, int fejlettseg, Tekton hely)
      throws Exception {
    if (!hely.getFoglalt()) {
      sporadarab = sporadb;
      elettartam = elett;
      this.fejlett = fejlett;
      this.fejlettseg = fejlettseg;
      tartozkodik = hely;
      this.gombasz = gombasz;
      hely.setFoglalt(true);
    } else {
      throw new Exception("A tekton már foglalt, nem lehet új gombatestet rátenni!");
    }
  }

  /**
   * Publikus getter függvény a gombatest spóra darabszámának lekérdezésére.
   *
   * @return a gombatest spóra darabszáma
   */
  public int getSporaDarab() {
    return sporadarab;
  }

  /**
   * Publikus getter függvény a gombatest élettratamának lekérdezésére.
   *
   * @return a gombatest élettartama
   */
  public int getElettartam() {
    return elettartam;
  }

  /**
   * Publikus getter függvény a gombatest fejlettségének lekérdezésére.
   *
   * @return jelző, hogy fejlett-e a gombatest
   */
  public boolean getFejlett() {
    return fejlett;
  }

  /**
   * Publikus getter függvény a gombatest fejlettségi szintjének lekérdezésére.
   *
   * @return a gombatest fejlettségi szintje
   */
  public int getFejlettseg() {
    return fejlettseg;
  }

  /**
   * Publikus getter függvény a gombatest tartózkodási helyének lekérdezésére.
   *
   * @return a tekton, ahol a gombatest tartózkodik
   */
  public Tekton getTartozkodik() {
    return tartozkodik;
  }

  /**
   * Publikus setter függvény, beállítja a gombatest spóraszámát.
   *
   * @param db a gombatest spóraszáma
   */
  public void setSporaDarab(int db) {
    sporadarab = db;
  }

  /**
   * Publikus setter függvény, beállítja a gombatest fejlettségét.
   *
   * @param fejlett jelzi, hogy fejlett gombatestről beszélünk-e
   */
  public void setFejlett(boolean fejlett) {
    this.fejlett = fejlett;
  }

  /**
   * Publikus setter függvény, beállítja a gombatest élettartamát.
   *
   * @param ido a kezdeti élettartam
   */
  public void setElettartam(int ido) {
    elettartam = ido;
  }

  /*
   * Publikus getteter függvény a gombatest gombaszának lekérdezésére.
   *
   * @return a gombatest gombaszája
   */
  public Gombasz getGombasz() {
    return gombasz;
  }

  /**
   * Elindítja a spóraszórást. Spóra szórását kezdeményezi egy szomszédos tektonra. Ekkor csökken a
   * saját spórakészlete és az élettartama is.
   *
   * @param hova melyik tektonra szórjon spórát
   */
  public boolean sporatSzor(Tekton hova) {
    if (fejlett) {
      List<Tekton> szomszedok = tartozkodik.getSzomszed(2);
      if (!szomszedok.contains(hova)) {
        return false;
      }
    }
    List<Tekton> szomszedok = tartozkodik.getSzomszed(1);
    if (!szomszedok.contains(hova)) {
      return false;
    }
    int db = Random.from(new Random()).nextInt() % sporadarab % 5;
    if (db == 0) {
      ++db;
    }
    hova.addSpora(db, this);
    eletcsokken();
    sporadarab -= db;
    return true;
  }

  /** Eggyel fejleszti a gombatestet, és növeli a fejlettség értékét. */
  public void novekszik() {
    fejlettseg++;
  }

  /**
   * Megsemmisül a gombatest, törlődik a listából, és felszabadítja a tektont, ahol eddig
   * tartózkodott
   */
  public void elpusztul() {
    tartozkodik.setFoglalt(false);
    tartozkodik = null;
  }

  /** Csökkenti az élettartamot, mert spóraszórás után csökken. */
  public void eletcsokken() {
    elettartam--;
  }

  /** Növeli a spóradarab értékét, a kör elején meghívódik. */
  public void tick() {
    sporadarab++;
  }
}
