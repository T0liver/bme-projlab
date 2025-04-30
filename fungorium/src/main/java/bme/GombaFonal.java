package bme;

import java.util.List;

import static bme.Jatekvezerlo.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * GombaFonal osztály definíciója.
 *
 * <p>
 * A gombatestből nőnek ki, így tudnak a gombafajok terjedni. A gombafonalak a
 * gombatestből
 * indulva ágaznak egy tektonokon, illetve a tektonok közti réseken átívelve
 * másikokon is. A
 * gombafajok terjedési módja, csak olyan tektonon alakulhat ki, ahol van
 * gombafonal is. Ha egy
 * gombafonal elveszíti a kapcsolatot a gombatesttel, akkor elhal.
 *
 * @author Oliver
 */
public class GombaFonal implements Jatekelem {

  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  /** Azoknak a tektonokran a listája, amin kereszül a fonal vezet. */
  private Map<Tekton, List<Tekton>> vezet;

  /** Gombasz, amihez tartozik */
  private Gombasz gombasz;

  /**
   * Publikus setter a gombafonal jatekosanak beallitasara
   */
  public void setGombasz(Gombasz j) {
    gombasz = j;
  }

  /**
   * Publikus getter a gombafonal jatekosanak lekerdezesere
   */
  public Gombasz getGombasz() {
    return gombasz;
  }

  /**
   * Ez a paraméter nélüli publikus konstruktor függvény, ami létrehozza a
   * GombaFonalat egy üres
   * listából.
   */
  public GombaFonal() {
    vezet = new HashMap<>();
  }

  /**
   * Ez a paraméteres publikus konstruktor függvény, ami létrehoz egy listát, és
   * belehelyezi a
   * kiinduló tektont.
   *
   * @param kezdet a tekton, ahonnan a gombafonal kiindul.
   */
  public GombaFonal(Tekton kezdet) {
    vezet = new HashMap<>();
    vezet.put(kezdet, new ArrayList<>());
    kezdet.fonalak.add(this);
  }

  public void addVezet(Tekton honnan, Tekton hova) {
    vezet.putIfAbsent(honnan, new ArrayList<>());
    if (!vezet.get(honnan).contains(hova))
      vezet.get(honnan).add(hova);
    if (!vezet.get(honnan).contains(honnan))
      vezet.get(honnan).add(honnan);

    vezet.putIfAbsent(hova, new ArrayList<>());
    if (!vezet.get(hova).contains(hova))
      vezet.get(hova).add(hova);
    if (!vezet.get(hova).contains(honnan))
      vezet.get(hova).add(honnan);

    // honnan.fonalak.add(this); //TODO ???
  }

  /**
   * Publikus getter függvény, visszatér azzal, hogy a paraméterként megadott két
   * tekton között vezet-e.
   *
   * @param honnan az egyik tekton
   * @param hova   a másik tekton
   * @return a paraméterként megadott két tekton között vezet-e.
   */
  public boolean getVezet(Tekton honnan, Tekton hova) {
    return vezet.containsKey(honnan) && vezet.get(honnan).contains(hova);
  }

  /**
   * Publikus getter függvény, visszatér azon tektonok listájával, ahová vezet.
   *
   * @return azon tektonok listája, ahová vezet a gombafonal.
   */
  public Map<Tekton, List<Tekton>> getVezet() {
    return vezet;
  }

  /**
   * Egyik tektonról egy másikra át akarunk jutni gombafonallal, ekkor alakul ki a
   * kapcsolat a két
   * tekton között
   *
   * @param hova melyik tektonra akarunk átjutni.
   * @return visszatér a művelet sikerességével, ha nem szomszédos a tekton, akkor
   *         nem sikerül a
   *         művelet.
   */
  public boolean athidal(Tekton hova) {
    for (Map.Entry<Tekton, List<Tekton>> entry : vezet.entrySet()) {
      Tekton t1 = entry.getKey();

      List<Tekton> szomszedok1 = t1.getSzomszed(1);

      if (szomszedok1.contains(hova)) {
        addVezet(t1, hova);
        return true;
      }
    }
    return false;
  }

  /**
   * Egyik tektonról egy másikra át akarunk jutni gombafonallal, ekkor alakul ki a
   * kapcsolat a két
   * tekton között
   * 
   * @param honnan melyik tektonról akarunk átjutni.
   * @param hova   melyik tektonra akarunk átjutni.
   * @return visszatér a művelet sikerességével, ha nem szomszédos a tekton, akkor
   *         nem sikerül a művelet.
   */
  public boolean athidal(Tekton honnan, Tekton hova) {
    if (!honnan.getSzomszed(1).contains(hova) || honnan == hova)
      return false;
    for (Map.Entry<Tekton, List<Tekton>> entry : vezet.entrySet()) {
      if (entry.getKey() == honnan) {
        addVezet(honnan, hova);
        return true;
      } else if (entry.getKey() == hova) {
        addVezet(hova, honnan);
        return true;
      }
    }
    return false;
  }

  /**
   * A gombafonalat elvágták az adott tektonon, ekkor kiveszi az elért tektonjai
   * közül az adott
   * tektont.
   *
   * @param hol az a tekton, ahol elvágták a gombafonalat
   */
  public void elvagodik(Tekton honnan, Tekton hova) {
    for (Tekton t : vezet.keySet()) {
      if (!vezet.get(t).contains(t))
        vezet.get(t).add(t);
      boolean talalt = false;
      List<Integer> lint = new ArrayList<>();
      for (int i = 0; i < vezet.get(t).size(); ++i) {
        if (t == vezet.get(t).get(i)) {
          if (!talalt) {
            talalt = true;
          } else {
            lint.add(i);
          }
        }
      }
      for (int i = lint.size() - 1; i >= 0; --i) {
        vezet.get(t).remove(i);
      }
    }
    if (vezet.containsKey(honnan)) {
      vezet.get(honnan).remove(hova);
    }
    if (vezet.containsKey(hova)) {
      vezet.get(hova).remove(honnan);
    }
    if (vezet.get(honnan) == null || vezet.get(honnan).isEmpty()) {
      honnan.fonalak.remove(this);
      vezet.remove(honnan);
    }
    if (vezet.get(hova) == null || vezet.get(hova).isEmpty()) {
      hova.fonalak.remove(this);
      vezet.remove(hova);
    }
  }

  /** A gombafonal elpusztul, így kitörli az őt tartalmazó kollekciókból. */
  public void elpusztul() {
    for (Map.Entry<Tekton, List<Tekton>> entry : vezet.entrySet()) {
      Tekton t = entry.getKey();
      t.fonalak.remove(this);
      entry.getValue().forEach(szomszed -> szomszed.fonalak.remove(this));
    }
    vezet.clear();
  }

  /**
   * A gombafonal ellenorzi, mielyik tektont eri el, majd amit nem, onnan
   * elpusztul.
   */
  public void tick() {
    Map<Tekton, List<Tekton>> ujVezet = new HashMap<>();
    for (Tekton entry : vezet.keySet()) {
      if (entry.vanGombaTest(this)) {
        ujVezet.put(entry, vezet.get(entry));
      }
    }
    for (int i = 0; i < 25; ++i) {
      Set<Tekton> ujKeys = new HashSet<>(ujVezet.keySet());
      for (Tekton entry : vezet.keySet()) {
        for (Tekton ujEntry : ujKeys) {
          if (ujVezet.get(ujEntry).contains(entry)) {
            ujVezet.put(entry, vezet.get(entry));
          }
        }
      }
    }
    for (Tekton entry : vezet.keySet()) {
      if (!ujVezet.keySet().contains(entry))
        entry.fonalak.remove(this);
    }
    vezet = ujVezet;
  }

  /** Ekkor a kiválasztott gombafonal terjeszkedik a tektonon belül. */
  public void novekszik() {
    // Még mindig nincs fogalmam, hogy ez itt mit csinálna, mert nincs
    // gombafonal-növekedés-szintje
    // változónk, amit lehetne növelni.
    int novekves = 0;
    novekves++;
    return;
  }

  /**
   * A class adatait kiiro fuggveny.
   */
  public void printData() {
    System.out.println("Osszekotott tektonok:");
    for (Tekton name : vezet.keySet()) {
      System.out.println("ID honnan: " + Jatekvezerlo.getIDof(name) + "\nIDk hova:");
      for (int i = 0; i < vezet.get(name).size(); ++i)
        System.out.println(Jatekvezerlo.getIDof(vezet.get(name).get(i)));
    }
  }

  /**
   * A bizonyos tekton szomszedaival valo osszekotteteseket kiiro fuggveny.
   */
  public void printData(Tekton t) {
    System.out.println("Osszekotott tektonok:");
    System.out.println("ID honnan: " + Jatekvezerlo.getIDof(t) + "\nIDk hova:");
    for (int i = 0; i < vezet.get(t).size(); ++i)
      System.out.println(Jatekvezerlo.getIDof(vezet.get(t).get(i)));
  }
}
