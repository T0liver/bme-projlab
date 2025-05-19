package bme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Gombasz osztaly definicioja
 *
 * <p>Gombaszok gombakat (gombatesteket, gombafonalakat es sporakat) iranyitanak a jatekban, es
 * lekerdezhetik annak állasat.
 *
 * @author Vid
 */
public class Gombasz extends Jatekos {

  /** Jatekos gombatestjeinek listaja */
  private List<GombaTest> gombaTestek = new ArrayList<>();

  /** Jatekos gombafonalainak listaja */
  private List<GombaFonal> gombaFonalak = new ArrayList<>();

  /** Jatekos sporainak listaja */
  private List<Spora> sporak = new ArrayList<>();

  private int hanyatNoveszt = 0;
  private List<Boolean> sporaHasznalt = new ArrayList<>();
  private List<Boolean> testCselekedett = new ArrayList<>();

  /**
   * @param nev Konstruktorában megadható a név paraméter A leszármazottakban fognak kezelődni
   */
  public Gombasz(String nev, Color szin) {
    super(nev, szin);
  }

  /** Parameter nelkuli konstruktor */
  public Gombasz() {
    super();
    akciok.add(new SporatSzorAkcio(this));
    akciok.add(new FonalNoveszetesAkcio(this));
    akciok.add(new TestNovesztesAkcio(this));
  }

  /**
   * Publikus getter a gombatestekre.
   *
   * @return visszaadja a GombaTestek listáját
   */
  public List<GombaTest> getGombaTestek() {
    return gombaTestek;
  }

  /**
   * Publikus getter a spórákra.
   *
   * @return visszaadja a Spórák listáját
   */
  public List<Spora> getSporak() {
    return sporak;
  }

  /**
   * Publikus getter a gombafonalakra.
   *
   * @return visszaadja a Gombafonalak listáját
   */
  public List<GombaFonal> getGombaFonalak() {
    return gombaFonalak;
  }

  /**
   * A jatekos lépéséért felelős metódus (parancssort kezeli, akciopontokkal)
   *
   * @return true, ha sikerült a lépés; különben false
   */
  @Override
  public boolean lep() {
    testCselekedett = new ArrayList<>();
    hanyatNoveszt = gombaTestek.size();
    sporaHasznalt = new ArrayList<>();
    for (int i = 0; i < sporak.size(); ++i) sporaHasznalt.add(false);
    for (int i = 0; i < gombaTestek.size(); ++i) {
      testCselekedett.add(false);
      gombaTestek.get(i).tick();
    }
    if (gombaFonalak != null)
      for (int i = 0; i < gombaFonalak.size(); ++i) {
        for (GombaTest gt : gombaTestek) {
          gombaFonalak
              .get(i)
              .addVezet(
                  gt.getTartozkodik().getMezok().get(0), gt.getTartozkodik().getMezok().get(0));
        }
        gombaFonalak.get(i).tick();
      }
    return false;
  }

  /** Publikus getter a játékos típusára. */
  @Override
  public int getType() {
    return 0;
  }

  /**
   * Spóra szóratása a gombatesttel.
   *
   * @param tekton a tekton, ahova a gombász spórát akar szórni.
   * @param gTest a gombatest, amelyik elvégzi a spóraszórást.
   * @return true, ha sikerült (lehetséges) a spóraszórás; különben false
   */
  public boolean sporatSzorat(Tekton tekton, GombaTest gTest) {
    int index = -1;
    for (int i = 0; i < gombaTestek.size(); ++i) {
      if (gombaTestek.get(i) == gTest) {
        if (testCselekedett.size() > i && testCselekedett.get(i)) return false;
        index = i;
      }
    }
    if (gTest.sporatSzor(tekton)) {
      testCselekedett.set(index, true);
      return true;
    }
    return false;
  }

  /**
   * Gombafonalat noveszto fuggveny
   *
   * @param args parancssori argumentumok
   * @return 1, ha sikeres volt, 0, ha nem
   */
  public int fonalatNoveszt(Mezo m0, Mezo m1) {
    if (hanyatNoveszt > 0)
      for (int i = 0; i < gombaFonalak.size(); ++i) {
        if (gombaFonalak.get(i).athidal(m0, m1)) {
          hanyatNoveszt--;
          return 1;
        }
      }
    return 0;
  }

  /**
   * Gombatest növesztésére szolgáló metódus.
   *
   * @param hova a tekton, ahová a gombász a gombatestet akarja növeszteni.
   * @return true, ha sikerült (lehetséges) a gombatest növesztése; különben false.
   */
  public boolean testetNoveszt(Tekton hova) {
    if (hova.getFoglalt()) {
      return false;
    }
    for (int i = 0; i < sporak.size(); i++) {
      if (hova.getSporak().contains(sporak.get(i))) {
        if (sporaHasznalt.size() > i
            && !sporaHasznalt.get(i)
            && hova.sporatFelhasznal(sporak.get(i))) {
          sporaHasznalt.set(i, true);
          try {
            GombaTest gt = new GombaTest(this, 0, 5, false, 0, hova);
            gombaTestek.add(gt);
            hova.setFoglalt(true);
            pontok += 1;
            return true;
          } catch (Exception e) {
            e.printStackTrace();
            return false;
          }
        }
      }
    }

    return false;
  }

  /**
   * Publikus tagfuggveny gombatest hozzaaadasahoz
   *
   * @param gt a hozzáadandó gombatest
   */
  @Override
  public void addGombaTest(GombaTest gt) {
    gombaTestek.add(gt);
  }

  /**
   * Publikus tagfuggveny gombafonal hozzaaadasahoz
   *
   * @param gf a hozzáadandó gombafonal
   */
  @Override
  public void addGombaFonal(GombaFonal gf) {
    gombaFonalak.add(gf);
  }

  /**
   * Publikus tagfuggveny spóra hozzaaadasahoz
   *
   * @param ujSpora a hozzáadandó spóra
   */
  public void addSpora(Spora ujSpora) {
    sporak.add(ujSpora);
  }

  /**
   * Gombatest eltávolítására szolgáló függvény.
   *
   * @param gt az eltávolítandó gombatest.
   */
  public void removeTest(GombaTest gt) {
    gombaTestek.remove(gt);
  }

  /**
   * Spóra eltávolítására szolgáló függvény.
   *
   * @param gt az eltávolítandó spóra.
   */
  public void removeSpora(Spora s) {
    sporak.remove(s);
  }
}
