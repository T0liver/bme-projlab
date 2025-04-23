package bme;

import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;

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
  private ArrayList<Pair<Tekton, Tekton>> vezet;

  /**
   * Ez a paraméter nélüli publikus konstruktor függvény, ami létrehozza a GombaFonalat egy üres
   * listából.
   */
  public GombaFonal() {
    vezet = new ArrayList<Pair<Tekton, Tekton>>();
  }

  /**
   * Ez a paraméteres publikus konstruktor függvény, ami létrehoz egy listát, és belehelyezi a
   * kiinduló tektont.
   *
   * @param kezdet a tekton, ahonnan a gombafonal kiindul.
   */
  public GombaFonal(Tekton kezdet) {
    vezet = new ArrayList<Pair<Tekton, Tekton>>();
    vezet.add(new Pair(kezdet, kezdet));
    kezdet.fonalak.add(this);
  }

  public void addVezet(Tekton honnan, Tekton hova) {
    vezet.add(new Pair(honnan, hova));
    honnan.fonalak.add(this);
  }

  /**
   * Publikus getter függvény, visszatér azon tektonok listájával, ahová vezet.
   *
   * @return azon tektonok listája, ahová vezet a gombafonal.
   */
  public boolean getVezet(Tekton honnan, Tekton hova) {
    return vezet.containsAll(List.of(new Pair(honnan, hova), new Pair(hova, honnan)));
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
    for (Pair t : vezet) {
      Tekton t1 = (Tekton) t.getKey();
      List<Tekton> szomszedok = t1.getSzomszed(1);

      if (szomszedok.isEmpty()) {
        return false;
      }

      if (szomszedok.indexOf(hova) != -1) {
        vezet.add(new Pair(t1, hova));
        hova.fonalak.add(this);
        return true;
      }
      Tekton t2 = (Tekton) t.getValue();
      szomszedok = t2.getSzomszed(1);

      if (szomszedok.isEmpty()) {
        return false;
      }

      if (szomszedok.indexOf(hova) != -1) {
        vezet.add(new Pair(t2, hova));
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
  public void elvagodik(Tekton honnan, Tekton hova) {
    vezet.removeIf(par -> (par.getKey().equals(honnan) && par.getValue().equals(hova)) || (par.getKey().equals(hova) && par.getValue().equals(honnan)));
  
    if (vezet.stream().noneMatch(par -> par.getKey().equals(honnan) || par.getValue().equals(honnan))) honnan.fonalak.remove(this);
    if (vezet.stream().noneMatch(par -> par.getKey().equals(hova) || par.getValue().equals(hova))) hova.fonalak.remove(this);
  }

  /** A gombafonal elpusztul, így kitörli az őt tartalmazó kollekciókból. */
  public void elpusztul() {
    for (Pair t : vezet) {
      Tekton t1 = (Tekton) t.getKey();
      t1.fonalak.remove(this);
      Tekton t2 = (Tekton) t.getKey();
      t2.fonalak.remove(this);
    }
  }

  /** Ekkor a kiválasztott gombafonal terjeszkedik a tektonon belül. */
  public void novekszik() {
    // Még mindig nincs fogalmam, hogy ez itt mit csinálna, mert nincs gombafonal-növekedés-szintje változónk, amit lehetne növelni.
    int novekves = 0;
    novekves++;
    return;
  }
}
