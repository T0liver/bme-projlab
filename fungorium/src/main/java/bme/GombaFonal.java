package bme;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

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
  private Map<Tekton, Tekton> vezet;

  /**
   * Ez a paraméter nélüli publikus konstruktor függvény, ami létrehozza a GombaFonalat egy üres
   * listából.
   */
  public GombaFonal() {
    vezet = new HashMap<>();
  }

  /**
   * Ez a paraméteres publikus konstruktor függvény, ami létrehoz egy listát, és belehelyezi a
   * kiinduló tektont.
   *
   * @param kezdet a tekton, ahonnan a gombafonal kiindul.
   */
  public GombaFonal(Tekton kezdet) {
    vezet = new HashMap<>();
    vezet.put(kezdet, kezdet);
    kezdet.fonalak.add(this);
  }

  public void addVezet(Tekton honnan, Tekton hova) {
    vezet.put(honnan, hova);
    vezet.put(hova, honnan);
    vezet.put(hova, hova);
    honnan.fonalak.add(this);
  }

  /**
   * Publikus getter függvény, visszatér azon tektonok listájával, ahová vezet.
   *
   * @return azon tektonok listája, ahová vezet a gombafonal.
   */
  public boolean getVezet(Tekton honnan, Tekton hova) {
    return (vezet.containsKey(honnan) && vezet.get(honnan).equals(hova)) ||
           (vezet.containsKey(hova) && vezet.get(hova).equals(honnan));

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
    for (Map.Entry<Tekton, Tekton> entry : vezet.entrySet()) {
      Tekton t1 = entry.getKey();
      Tekton t2 = entry.getValue();

      List<Tekton> szomszedok1 = t1.getSzomszed(1);
      if (szomszedok1.contains(hova)) {
        addVezet(t1, hova);
        return true;
      }

      List<Tekton> szomszedok2 = t2.getSzomszed(1);
      if (szomszedok2.contains(hova)) {
        addVezet(t2, hova);
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
    vezet.entrySet().removeIf(entry -> (entry.getKey().equals(honnan) && entry.getValue().equals(hova)) || (entry.getKey().equals(hova) && entry.getValue().equals(honnan)));

    if (vezet.entrySet().stream().noneMatch(entry ->
        entry.getKey().equals(honnan) || entry.getValue().equals(honnan))) {
      honnan.fonalak.remove(this);
    }

    if (vezet.entrySet().stream().noneMatch(entry ->
        entry.getKey().equals(hova) || entry.getValue().equals(hova))) {
      hova.fonalak.remove(this);
    }
  }

  /** A gombafonal elpusztul, így kitörli az őt tartalmazó kollekciókból. */
  public void elpusztul() {
    for (Map.Entry<Tekton, Tekton> entry : vezet.entrySet()) {
      entry.getKey().fonalak.remove(this);
      entry.getValue().fonalak.remove(this);
    }
    vezet.clear();
  }

  /** Ekkor a kiválasztott gombafonal terjeszkedik a tektonon belül. */
  public void novekszik() {
    // Még mindig nincs fogalmam, hogy ez itt mit csinálna, mert nincs gombafonal-növekedés-szintje
    // változónk, amit lehetne növelni.
    int novekves = 0;
    novekves++;
    return;
  }
}
