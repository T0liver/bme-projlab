package bme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Rovarasz osztaly definicioja
 *
 * <p>Rovaraszok rovarokat iranyitanak a jatekban, es lekerdezhetik annak allasat.
 *
 * @author Vid
 */
public class Rovarasz extends Jatekos {
  // * Jatekos rovarainak listaja */
  private List<Rovar> rovarok = new ArrayList<>();
  private List<Boolean> cselekedhet = new ArrayList<>();
  private List<Integer> lepesek = new ArrayList<>();

  /**
   * @param nev Konstruktorában megadható a név paraméter A leszármazottakban fognak kezelődni
   */
  public Rovarasz(String nev, Color szin) {
    super(nev, szin);
  }

  /** Parameter nelkuli konstruktor */
  public Rovarasz() {
    super();
    akciok.add(new MozgasAkcio(this));
    akciok.add(new EvesAkcio(this));
    akciok.add(new VagasAkcio(this));
  }

  /** A jatekos lepeseert felelos fuggveny (parancssort kezeli, akciopontokkal) */
  @Override
  public boolean lep() {
    cselekedhet = new ArrayList<>();
    lepesek = new ArrayList<>();
    for (int i = 0; i < rovarok.size(); ++i) {
      rovarok.get(i).tick();
      cselekedhet.add(true);
      lepesek.add(rovarok.get(i).getSebesseg());
    }
    printData();
    return false;
  }

  /**
   * fuggveny, amiben egy rovart mozgat egy masik tektonra
   *
   * @param args parancssori argumentumok
   * @return 1, ha mozgott, 0, ha nem
   */
  public int mozgat(Mezo m0, Mezo m1) {

    Rovar rovar = null;
    int index = -1;
    for (int i = 0; i < rovarok.size(); ++i) {
      if (rovarok.get(i).getTartozkodik() == m0) {
        rovar = rovarok.get(i);
        index = i;
        break;
      }
    }
    if (rovar == null || lepesek.get(index) < 1) return 0;
    if (rovar.mozog(m1)) {
      lepesek.set(index, lepesek.get(index) - 1);
      return 1;
    }
    return 0;
  }

  /**
   * fuggveny, amiben egy rovarral megetet sporat tartozkodasi tektonjan
   *
   * @param args parancssori argumentumok
   * @return evesert kapott pontszam
   */
  public int megetet(Mezo m0) {

    Rovar rovar = null;
    int index = -1;
    for (int i = 0; i < rovarok.size(); ++i) {
      if (rovarok.get(i).getTartozkodik() == m0) {
        if (!cselekedhet.get(i)) return 0;
        rovar = rovarok.get(i);
        index = i;
        break;
      }
    }
    if (rovar == null) return 0;
    Spora spora = rovar.getTartozkodik().getTekton().getBestSpora();

    int ret = rovar.eszik(spora);
    if (ret > 0) cselekedhet.set(index, false);
    return ret;
  }

  /**
   * fuggveny, amiben egy rovarral elvagat egy fonalat egy tekton iranyaban
   *
   * @param args parancssori argumentumok
   * @return sikeresen vagott-e
   */
  public boolean elvagat(Mezo m0, Mezo m1) {
    Rovar rovar = null;
    int index = -1;
    for (int i = 0; i < rovarok.size(); ++i) {
      if (rovarok.get(i).getTartozkodik() == m0) {
        rovar = rovarok.get(i);
        index = i;
        if (!cselekedhet.get(i)) return false;
        break;
      }
    }
    if (rovar == null) return false;
    List<GombaFonal> gombaFonalak = rovar.getTartozkodik().getFonalak();
    GombaFonal gombaFonal = null;
    for (int i = 0; i < gombaFonalak.size(); ++i) {
      if (gombaFonalak.get(i).getVezet(m0, m1)) {
        gombaFonal = gombaFonalak.get(i);
        break;
      }
    }
    if (gombaFonal == null) return false;
    if (rovar.vag(gombaFonal, m1)) {
      cselekedhet.set(index, false);
      return true;
    }
    return false;
  }

  /** jatekos tipusat megado fuggveny */
  @Override
  public int getType() {
    return 1;
  }

  /**
   * Publikus getter függvény a Rovarász rovarjainak listájának lekérdezésére.
   *
   * @return a Rovarász rovarjainak listája
   */
  public List<Rovar> getRovarok() {
    return rovarok;
  }

  /*
   * Publikus setter függvény a Rovarász rovarjainak listájának beállítására.
   *
   * @param rovarok a Rovarász új rovarjainak listája
   */
  public void addRovar(Rovar rovar) {
    rovarok.add(rovar);
  }
}
