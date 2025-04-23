package bme;

import java.util.ArrayList;
import java.util.List;

/**
 * Tekton osztály definíciója.
 *
 * <p>A pálya Tektonokból áll. A tektonok kettéhasadhatnak (csak, ha nincs rajta gombatest), ekkor
 * minden rajtuk levő életforma elpusztul, közöttük gombafonál hidalhat át, rajtuk rovarok
 * mozoghatnak, és gombatestek spórát helyezhetnek el, melyek segítségével új gombatest nőhet
 *
 * @author Vid
 */
public class Tekton {

  /** boolean, ami megadja, hogy a tektonon van-e gombatest */
  private boolean foglalt;

  /** lista a tektonhoz szomszédos tektonokról */
  private List<Tekton> szomszedok;

  /** lista a tektonon levő spórákról */
  protected List<Spora> sporak;

  /** lista a tektonon levő spórákról */
  protected List<GombaFonal> fonalak;

  /** Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait. */
  public Tekton() {
    foglalt = false;
    szomszedok = new ArrayList<>();
    sporak = new ArrayList<>();
    fonalak = new ArrayList<>();
  }

  public boolean getFoglalt() {
    return foglalt;
  }

  public void setFoglalt(boolean foglaltsagi) {
    foglalt = foglaltsagi;
  }

  /**
   * A tekton szomszédjainak beállításához hasznos függvény
   *
   * @param tekton egy szomszédként beállítandó tekton
   */
  public void addSzomszed(
      Tekton tekton) { // TODO: legyen inkább csak setter? ez nincs az uml diagramon
    if (!szomszedok.contains(tekton)) // TODO: ez redundáns?
    szomszedok.add(tekton); // TODO: csináljuk visszairányba?
  }

  /**
   * A tektont hasadásra utasító függvény
   *
   * @return lista, aminek tartalma: a két új létrejött tekton, vagy önmaga, ha nem tudott hasadni
   */
  public List<Tekton> hasad() {
    List<Tekton> ret = new ArrayList<>();
    if (foglalt) {
      ret.add(this);
      return ret;
    }
    ret.add(new Tekton());
    ret.add(new Tekton());
    // TODO set Szomszedok hasadás iránya szerint (mezők szerint, majd grafikánál)
    return ret;
  }

  /**
   * A tekton maximum x. szomszédait megkereső függvény. Ez a gombatest spórázásánál hasznos.
   *
   * @param hanyadik hanyadik szomszéd a maximális elfogadott (ha 1, akkor csak közvetlen
   *     szomszédok; ha 2, akkor szomszédok szomszédai, stb)
   * @return a megtalált szomszédok
   */
  public List<Tekton> getSzomszed(int hanyadik) {
    List<Tekton> ret = new ArrayList<>();
    if (hanyadik <= 0) return ret;
    for (int i = 0; i < szomszedok.size(); ++i) {
      ret.add(szomszedok.get(i));
      List<Tekton> check = szomszedok.get(i).getSzomszed(hanyadik);
      for (int e = 0; e < check.size(); ++e) if (!ret.contains(check.get(i))) ret.add(check.get(i));
    }
    return ret;
  }

  /**
   * Spórák tektonon való elhelyezését megoldó függvény
   *
   * @param mennyiseg mennyi spórát rakunk rá
   * @return valami booleant
   */
  public boolean /* miért boolean */ addSpora(int mennyiseg /* TODO: , + valam faj jelölés */) {
    // TODO: sporak.add(spora); valami
    return true;
  }

  /**
   * A tektonon levő legmagasabb tápanyagtartalmú spóra lekérdezésére használt függvény
   *
   * @return a tekton spóra listájában levő legmagasabb tápanyagtartalmú spóra
   */
  public Spora getBestSpora() {
    if (sporak.isEmpty()) throw new IndexOutOfBoundsException();
    int reti = 0;
    for (int i = 1; i < sporak.size(); ++i) {
      if (sporak.get(reti).getTapanyag() < sporak.get(i).getTapanyag()) reti = i;
    }
    return sporak.get(reti);
  }

  /**
   * Függvény, ami felhasználja a gombatest készítéséhez megfelelő mennyiségű spórát
   *
   * @param mit melyik spórát használja fel
   * @return hogy a spóra felhasználása sikeres volt-e (volt e elég)
   */
  public boolean sporatFelhasznal(Spora mit) {
    return sporak.contains(mit) && mit.csokken(10) == mit.getTapanyag() * 10;
  }

  /**
   * Gombafonál tektonra való átérését kezelő függvény
   *
   * @param melyik melyik gombafonál próbál áthidalni a tektonra
   */
  public void fonalNo(GombaFonal melyik) {
    for (int i = 0; i < szomszedok.size(); ++i) {
      if (melyik.getVezet().contains(szomszedok.get(i))) {
        fonalak.add(melyik);
        melyik.athidal(this);
      }
    }
  }

  /** A kör elején meghívott függvény, felszívó tekton használja */
  public void tick() {
    // A kör elején meghívott függvény, felszívó tekton használja
  }
}
