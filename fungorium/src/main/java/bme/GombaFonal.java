package bme;

import java.io.Serializable;
import java.util.List;

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
public class GombaFonal implements Jatekelem, Serializable {

  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  /** Azoknak a tektonokran a listája, amin kereszül a fonal vezet. */
  private Map<Mezo, List<Mezo>> vezet;

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
  public GombaFonal(Mezo kezdet) {
    vezet = new HashMap<>();
    vezet.put(kezdet, new ArrayList<>());
    kezdet.fonalak.add(this);
    addVezet(kezdet, kezdet);
  }

  public void addVezet(Mezo honnan, Mezo hova) {
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
    honnan.fonalak.add(this);
  }

  /**
   * Publikus getter függvény, visszatér azzal, hogy a paraméterként megadott két
   * tekton között vezet-e.
   *
   * @param honnan az egyik tekton
   * @param hova   a másik tekton
   * @return a paraméterként megadott két tekton között vezet-e.
   */
  public boolean getVezet(Mezo honnan, Mezo hova) {
    return (vezet.containsKey(honnan) && vezet.get(honnan).contains(hova)) || (vezet.containsKey(hova) && vezet.get(hova).contains(honnan));
  }

  /**
   * Publikus getter függvény, visszatér azon tektonok listájával, ahová vezet.
   *
   * @return azon tektonok listája, ahová vezet a gombafonal.
   */
  public Map<Mezo, List<Mezo>> getVezet() {
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
   *
  public boolean athidal(Mezo hova) {
    for (Map.Entry<Mezo, List<Mezo>> entry : vezet.entrySet()) {
      Mezo t1 = entry.getKey();
      if (t1.milyenSzomszed(hova) != 0) {
        addVezet(t1, hova);
        return true;
      }
    }
    return false;
  }*/

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
  public boolean athidal(Mezo honnan, Mezo hova) {
    if (honnan.milyenSzomszed(hova) == 0 || honnan == hova || !hova.getTekton().fonalNohet(this))
        return false;

    boolean ret = false;

    if (vezet.containsKey(honnan)) {
        addVezet(honnan, hova);
        ret = true;
    }
    if (vezet.containsKey(hova)) {
        addVezet(hova, honnan);
        ret = true;
    }

    return ret;
}


  /**
   * A gombafonalat elvágták az adott tektonon, ekkor kiveszi az elért tektonjai
   * közül az adott
   * tektont.
   *
   * @param hol az a tekton, ahol elvágták a gombafonalat
   */
  public void elvagodik(Mezo honnan, Mezo hova) {
    for (Mezo t : vezet.keySet()) {
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
    //for (Map.Entry<Mezo, List<Mezo>> entry : vezet.entrySet()) {
      //Mezo t = entry.getKey();
      //t.fonalak.remove(this);
      //entry.getValue().forEach(szomszed -> szomszed.fonalak.remove(this));
    //}
    //vezet.clear();
  }

  /**
   * A gombafonal ellenorzi, mielyik tektont eri el, majd amit nem, onnan
   * elpusztul.
   */
  public void tick() {
    Map<Mezo, List<Mezo>> ujVezet = new HashMap<>();
    for (Mezo entry : vezet.keySet()) {
      if (entry.getTekton().vanGombaTest(this)) {
        ujVezet.put(entry, vezet.get(entry));
      }
    }
    for (int i = 0; i < 22*22; ++i) {
      Set<Mezo> ujKeys = new HashSet<>(ujVezet.keySet());
      for (Mezo entry : vezet.keySet()) {
        for (Mezo ujEntry : ujKeys) {
          if (ujVezet.get(ujEntry).contains(entry)) {
            ujVezet.put(entry, vezet.get(entry));
          }
        }
      }
    }
    for (Mezo entry : vezet.keySet()) {
      if (!ujVezet.keySet().contains(entry))
        entry.fonalak.remove(this);
    }
    vezet = ujVezet;
    novekszik();
  }

  /** Ekkor a kiválasztott gombafonal terjeszkedik a tektonon belül. */
  public void novekszik() {
    List<Mezo> mezok = new ArrayList<>();
    for (Mezo m0 : vezet.keySet()) {
      mezok.add(m0);
      m0.addFonal(this);
    }
    for (Mezo m0 : mezok) {
      for (Mezo m1 : m0.getTekton().getMezok()) {
        m1.fonalNovekszik(this, m0);
      }
    }
    for (Mezo m0 : mezok) {
      if (m0.getTekton().hasSpora(this)) {
        for (Mezo m1 : m0.getTekton().getMezok()) {
          m1.fonalNovekszik(this, m0);
        }
      }
    }
    return;
  }

  /**
   * A class adatait kiiro fuggveny.
   *
  public void printData() {
    System.out.println("Osszekotott tektonok:");
    for (Mezo name : vezet.keySet()) {
      System.out.println("ID honnan: " + Jatekvezerlo.getIDof(name) + "\nIDk hova:");
      for (int i = 0; i < vezet.get(name).size(); ++i)
        System.out.println(Jatekvezerlo.getIDof(vezet.get(name).get(i)));
    }
  }

  /**
   * A bizonyos tekton szomszedaival valo osszekotteteseket kiiro fuggveny.
   *
  public void printData(Mezo t) {
    System.out.println("Osszekotott tektonok:");
    System.out.println("ID honnan: " + Jatekvezerlo.getIDof(t) + "\nIDk hova:");
    for (int i = 0; i < vezet.get(t).size(); ++i)
      System.out.println(Jatekvezerlo.getIDof(vezet.get(t).get(i)));
  }*/
}
