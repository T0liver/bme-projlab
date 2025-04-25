package bme;

import java.util.ArrayList;
import java.util.List;

public class Gombasz extends Jatekos {

  private List<GombaTest> gombaTestek = new ArrayList<GombaTest>();
  private List<GombaFonal> gombaFonalak = new ArrayList<GombaFonal>();
  private List<Spora> sporak = new ArrayList<Spora>();

  /**
   * @param nev Konstruktorában megadható a név paraméter
   *            A leszármazottakban fognak kezelődni
   */
  public Gombasz(String nev) {
    super(nev);
  }

  /**
   *
   * @return visszaadja a GombaTestek listáját
   */
  public List<GombaTest> getGombaTestek() { return gombaTestek; }

  /**
   *
   * @return visszaadja a Spórák listáját
   */
  public List<Spora> getSporak() { return sporak; }

  /**
   *
   * @return visszaadja a Gombafonalak listáját
   */
  public List<GombaFonal> getGombaFonalak() {return gombaFonalak;}

  /**
   *
   * @param gombaTestek megadható a Gombatestek listája
   */
  public void setGombaTestek(List<GombaTest> gombaTestek) { this.gombaTestek = gombaTestek; }

  /**
   *
   * @param sporak megadható a Srórák listája
   */
  public void setSporak(List<Spora> sporak) {this.sporak = sporak; }

  /**
   * @param gombaFonalak megadható a GombaFonalak listája
   */

  public void setGombaFonalak(List<GombaFonal> gombaFonalak){this.gombaFonalak = gombaFonalak;  }

  @Override
  public void lep() {
    List<Boolean> testCselekedett = new ArrayList<Boolean>();
    int fonalCselekedetek = gombaTestek.size();
    List<Boolean> sporaHasznalt = new ArrayList<Boolean>();
    for (int i = 0; i < sporak.size(); ++i) sporaHasznalt.add(false);
    for (int i = 0; i < gombaTestek.size(); ++i) testCselekedett.add(false);
    // TODO, mint a rovarásznál, új gombatest felvétele, akciópontok és bemenet
    // kezelése
  }

  @Override
  public int getType() {
    return 0;
  }
}
