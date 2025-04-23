package bme;

import java.util.ArrayList;
import java.util.List;

/**
 * GombaFonal osztály definíciója.
 *
 * <p>A gombatestből nőnek ki, így tudnak a gombafajok terjedni. A gombafonalak a gombatestből
 * indulva ágaznak egy tektonokon, illetve a tektonok közti réseken átívelve másikokon is. A
 * gombafajok terjedési módja, csak olyan tektonon alakulhat ki, ahol van gombafonal is. Ha egy
 * gombafonal elveszíti a kapcsolatot a gombatesttel, akkor elhal.
 *
 * @author Oliver
 */
public class GombaFonal {
  /** Azoknak a tektonokran a listája, amin kereszül a fonal vezet. */
  private ArrayList<Tekton> vezet;

  /**
   * Ez a paraméter nélüli publikus konstruktor függvény, ami létrehozza a GombaFonalat egy üres
   * listából.
   */
  public GombaFonal() {
    vezet = new ArrayList<Tekton>();
  }

  /**
   * Ez a paraméteres publikus konstruktor függvény, ami létrehoz egy listát, és belehelyezi a
   * kiinduló tektont.
   *
   * @param kezdet a tekton, ahonnan a gombafonal kiindul.
   */
  public GombaFonal(Tekton kezdet) {
    vezet = new ArrayList<Tekton>();
    vezet.add(kezdet);
    kezdet.fonalak.add(this);
  }

  /**
   * Publikus getter függvény, visszatér azon tektonok listájával, ahová vezet.
   *
   * @return azon tektonok listája, ahová vezet a gombafonal.
   */
  public ArrayList<Tekton> getVezet() {
    return vezet;
  }

  /**
   * Egyik tektonról egy másikra át akarunk jutni gombafonallal, ekkor alakul ki a kapcsolat a két
   * tekton között
   *
   * @param hova melyik tektonra akarunk átjutni.
   * @return visszatér a művelet sikerességével, ha nem szomszédos a tekton, akkor nem sikerül a
   *     művelet.
   */
  public boolean athidal(Tekton hova) {
    for (Tekton t : vezet) {
      List<Tekton> szomszedok = t.getSzomszed(1);

      if (szomszedok.isEmpty()) {
        return false;
      }

      if (szomszedok.contains(hova)) {
        vezet.add(hova);
        hova.fonalak.add(this);
        return true;
      }
    }
    return false;
  }

  /**
   * A gombafonalat elvágták az adott tektonon, ekkor kiveszi az elért tektonjai közül az adott
   * tektont.
   *
   * @param hol az a tekton, ahol elvágták a gombafonalat
   */
  public void elvagodik(Tekton hol) {
    if (vezet.contains(hol)) {
      vezet.remove(hol);
      hol.fonalak.remove(this);
    }
    return;
  }

  /** A gombafonal elpusztul, így kitörli az őt tartalmazó kollekciókból. */
  public void elpusztul() {
    for (Tekton t : vezet) {
      t.fonalak.remove(this);
    }
  }

  /** Ekkor a kiválasztott gombafonal terjeszkedik a tektonon belül. */
  public void novekszik() {
    // Ez mit akar csinálni, hogyan tartjuk nyilván azt, hogy egy fonal "mennyire" van egy tektonon?
    // TODO: kitalálni ez mit csinál
  }
}
