package bme;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Gombasz osztaly definicioja
 * 
 * Gombaszok gombakat (gombatesteket, gombafonalakat es sporakat) iranyitanak a jatekban, es lekerdezhetik annak allasat.
 */
public class Gombasz extends Jatekos implements Serializable {

  //* Jatekos gombatestjeinek listaja */
  private List<GombaTest> gombaTestek = new ArrayList<>();
  
  //* Jatekos gombafonalainak listaja */
  private List<GombaFonal> gombaFonalak = new ArrayList<>();
  
  //* Jatekos sporainak listaja */
  private List<Spora> sporak = new ArrayList<>();

  /**
   * @param nev Konstruktorában megadható a név paraméter
   *            A leszármazottakban fognak kezelődni
   */
  public Gombasz(String nev, Color szin) {
    super(nev, szin);
  }

  /**
   * Parameter nelkuli konstruktor
   */
  public Gombasz() {
    super();
    akciok.add(new SporatSzorAkcio(this));
    akciok.add(new FonalNoveszetesAkcio(this));
    akciok.add(new TestNovesztesAkcio(this));
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

  /**
   * A jatekos lepeseert felelos fuggveny (parancssort kezeli, akciopontokkal)
   */
  @Override
  public boolean lep() {
    List<Boolean> testCselekedett = new ArrayList<>();
    int fonalCselekedetek = gombaTestek.size();
    List<Boolean> sporaHasznalt = new ArrayList<>();
    for (int i = 0; i < sporak.size(); ++i) sporaHasznalt.add(false);
    for (int i = 0; i < gombaTestek.size(); ++i) {testCselekedett.add(false); gombaTestek.get(i).tick();}
    if (gombaFonalak != null) for (int i = 0; i < gombaFonalak.size(); ++i) gombaFonalak.get(i).tick();
    boolean endOfTurn = false;
    //while(!endOfTurn) {
      //kör
    //}
    return false;
  }

  /**
   * jatekos tipusat megado fuggveny
   */
  @Override
  public int getType() {
    return 0;
  }

  /**
   * Gombatesttel sporat szorato fuggveny
   * @param args parancssori argumentumok
   * @return sikeres volt-e a szoras
   *
  private boolean sporatSzorat(String[] args) {
    GombaTest gt = gombaTestek.get(Integer.parseInt(args[1]));
    int tektonid = -1;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-tk")) {
        tektonid = Integer.parseInt(args[i + 1]);
      }
    }
    if (tektonid == -1) return false;
    return gt.sporatSzor(Jatekvezerlo.tektonok.get(tektonid));
  }
*/
public boolean sporatSzorat(Tekton tekton, GombaTest gTest) {
  return gTest.sporatSzor(tekton);
}

  /**
   * Gombafonalat noveszto fuggveny
   * @param args parancssori argumentumok
   * @return 1, ha sikeres volt, 0, ha nem
  */
  public int fonalatNoveszt(Mezo m0, Mezo m1) {
      for (int i = 0; i < gombaFonalak.size(); ++i) {
        if (gombaFonalak.get(i).athidal(m0, m1)) {
          return 1;
        }
      }
      return 0;
  }

  /**
   * Gombatestet noveszto fuggveny
   * @param args parancssori argumentumok
   *
  private void testetNoveszt(String[] args) {
    for (int i = 0; i < sporak.size(); ++i) {
      if (Jatekvezerlo.tektonok.get(Integer.parseInt(args[1])).sporatFelhasznal(sporak.get(i))) {
        try {
          gombaTestek.add(new GombaTest(this, 0, 5, false, 0, Jatekvezerlo.tektonok.get(Integer.parseInt(args[1]))));
        } catch (NumberFormatException e) {
          e.printStackTrace();
        } catch (Exception e) {
          e.printStackTrace();
        }
        Jatekvezerlo.tektonok.get(Integer.parseInt(args[1])).setFoglalt(true); //addGombaTest(gombaTestek.get(gombaTestek.size() - 1));
      }
    }
  }
  */

  public boolean testetNoveszt(Tekton hova) {
    if (hova.getFoglalt()) {
      return false;
    }

    for (int i = 0; i < sporak.size(); i++) {
      if (hova.getSporak().contains(sporak.get(i)) && hova.sporatFelhasznal(sporak.get(i))) {
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
      } else {System.out.println("asd");}
    }

    return false;
    
  }

  /** publikus tagfuggveny gombatest hozzaaadasahoz */
  @Override
  public void addGombaTest(GombaTest gt) {gombaTestek.add(gt);}

  /** publikus tagfuggveny gombafonal hozzaaadasahoz */
  @Override
  public void addGombaFonal(GombaFonal gf) {gombaFonalak.add(gf);}

  /** publikus tagfuggveny spora hozzaaadasahoz */
  public void addSpora(Spora ujSpora) {sporak.add(ujSpora);}

  public void removeTest(GombaTest gt) {gombaTestek.remove(gt);}

  public void removeSpora(Spora s) {sporak.remove(s);}
} 
