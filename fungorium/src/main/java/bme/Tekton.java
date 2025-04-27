package bme;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Tekton osztály definíciója.
 *
 * <p>A pálya Tektonokból áll. A tektonok kettéhasadhatnak (csak, ha nincs rajta gombatest), ekkor
 * minden rajtuk levő életforma elpusztul, közöttük gombafonál hidalhat át, rajtuk rovarok
 * mozoghatnak, és gombatestek spórát helyezhetnek el, melyek segítségével új gombatest nőhet
 *
 * @author Vid
 */
public class Tekton implements Jatekelem{

  
  Random r = new Random();
  private int id;
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  /** boolean, ami megadja, hogy a tektonon van-e gombatest */
  protected boolean foglalt;

  /** lista a tektonhoz szomszédos tektonokról */
  protected List<Tekton> szomszedok;

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

  /** Publikus getter a tekton foglaltsaganak lekerdezesere */
  public boolean getFoglalt() {
    return foglalt;
  }

  /** Publikus setter a tekton foglaltsaganak megadasara */
  public void setFoglalt(boolean foglaltsagi) {
    foglalt = foglaltsagi;
  }

  /** Publikus getter a tektonon tartozkodo gombafonalak lekerdezesere */
  public List<GombaFonal> getFonalak() {
    return fonalak;
  }

  /** Publikus getter a tektonon tartozkodo sporak lekerdezesere */
  public List<Spora> getSporak() {
    return sporak;
  }

  /**
   * A tekton szomszédjainak beállításához hasznos függvény
   *
   * @param tekton egy szomszédként beállítandó tekton
   */
  public void addSzomszed(Tekton tekton) { // legyen inkább csak setter? ez nincs az uml diagramon - jó ez [Vid]
    if (!szomszedok.contains(tekton)) { // ellenőrzés, ne legyen loop (redundancia)
      szomszedok.add(tekton); // csináljuk visszairányba
      tekton.addSzomszed(this);
    }
  }

  /**
   * A tekton másik tektonhoz való kapcsolatát megadó függvény
   *
   * @param tekton a másik tekton
   * @return a szomszédsági státus: 0 - nem szomszéd, 1 - szomszéd és fonállal össze nem kötött, 2
   *     szomszéd és fonállal összekötött
   */
  public int milyenszomszed(Tekton tekton) {
    if (!szomszedok.contains(tekton)) return 0;
    for (int i = 0; i < fonalak.size(); ++i) if (fonalak.get(i).getVezet(this, tekton)) return 2;
    return 1;
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
    Tekton t1 = new Tekton();
    Tekton t2 = new Tekton();
    for (int i = 0; i < fonalak.size(); ++i) {
      for (int e = 0; e < szomszedok.size(); ++e) {
        if (e <= szomszedok.size() / 2) { //ez a rész majd grafikusan más lesz
          t1.addSzomszed(szomszedok.get(e));
        } else {
            t2.addSzomszed(szomszedok.get(e));
        }
        fonalak.get(i).elvagodik(this, szomszedok.get(e));
      }
    }
    for (int i = 0; i < Jatekvezerlo.jatekosok.size(); ++i) {
      if (Jatekvezerlo.jatekosok.get(i).getType() == 1) {
        for (int e = 0; e < Jatekvezerlo.jatekosok.get(i).getRovarok().size(); ++e) {
          if (Jatekvezerlo.jatekosok.get(i).getRovarok().get(e).getTartozkodik() == this)
            Jatekvezerlo.jatekosok.get(i).getRovarok().get(e).setTartozkodik(t1);
        }
      }
    }
    t1.addSzomszed(t2);
    t2.addSzomszed(t1);
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
      List<Tekton> check = szomszedok.get(i).getSzomszed(hanyadik - 1);
      for (int e = 0; e < check.size(); ++e) {
        if (!ret.contains(check.get(e))) {
          ret.add(check.get(e));
        }
      }
    }
    return ret;
  }

  /**
   * Spórák tektonon való elhelyezését megoldó függvény
   *
   * @param mennyiseg mennyi spórát rakunk rá
   * @param gt melyik gombatest szórta
   * @param random random spóratípus-e, vagy csak alapértelmezett
   * @return valami booleant
   */
  public boolean addSpora(int mennyiseg, GombaTest gt, boolean random) {
    for (int i = 0; i < sporak.size(); ++i) {
      if (sporak.get(i).getGombasz() == gt.getGombasz()) { // keresünk azonos fajú spórát
        sporak.get(i).novel(mennyiseg);
        return false;
      }
    }
    Spora ujSpora;
    int adandoSporaTipus = 0;
    if (random)
      adandoSporaTipus = r.nextInt(6);
    switch (adandoSporaTipus) {
      case 0:
        ujSpora = new Spora(3, mennyiseg, gt.getGombasz());
        ujSpora.setTartozkodik(this);
        add(ujSpora);
        break;
      case 1:
        ujSpora = new LassitoSpora(3, mennyiseg, gt.getGombasz());
        ujSpora.setTartozkodik(this);
        add(ujSpora);
        break;
      case 2:
        ujSpora = new GyorsitoSpora(3, mennyiseg, gt.getGombasz());
        ujSpora.setTartozkodik(this);
        add(ujSpora);
        break;
      case 3:
        ujSpora = new CsorbitoSpora(3, mennyiseg, gt.getGombasz());
        ujSpora.setTartozkodik(this);
        add(ujSpora);
        break;
      case 4:
        ujSpora = new BenitoSpora(3, mennyiseg, gt.getGombasz());
        ujSpora.setTartozkodik(this);
        add(ujSpora);
        break;
      case 5:
        ujSpora = new OsztoSpora(3, mennyiseg, gt.getGombasz());
        ujSpora.setTartozkodik(this);
        add(ujSpora);
        break;
      default:
        break;
    }
    return true; // kellett újat hozzáadni
  }

  /**
   * Spórák tektonon való elhelyezését megoldó függvény
   *
   * @param mennyiseg mennyi spórát rakunk rá
   * @param gt melyik gombatest szórta
   * @return valami booleant
   */
  public boolean addSpora(int mennyiseg, GombaTest gt) {
    for (int i = 0; i < sporak.size(); ++i) {
      if (sporak.get(i).getGombasz() == gt.getGombasz()) { // keresünk azonos fajú spórát
        sporak.get(i).novel(mennyiseg);
        return false;
      }
    }
    Spora ujSpora = new Spora(3, mennyiseg, gt.getGombasz());
    ujSpora.setTartozkodik(this);
    add(ujSpora);
    return true; // kellett újat hozzáadni
  }

  /**
   * Az adott spórát elhelyezi a tektonon
   *
   * @param spora hozzáadódik a tektonhoz
   * @return nem ad vissza semmit sem
   */
  public void add(Spora spora) {
    spora.setTartozkodik(this);
    sporak.add(spora);
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
      if (melyik.getVezet(szomszedok.get(i), szomszedok.get(i))) { //Ellenőrzés, hogy szomszédról hidal-e át
        fonalak.add(melyik);
        //melyik.athidal(this); //ez kérdőjeles
      }
    }
  }

  /** Függvény, ami megadja, hogy van-e gobafonál túléléséhez szükséges gombatest a tektonon
   * 
   * @param gombaFonal a gombafonal, amire vizsgájuk
   * @return hogy van-e rajta olyan gombatest, ami kell a fonal túléléséhez
   */
  public boolean vanGombaTest(GombaFonal gombaFonal) {
    if (foglalt) {
       for (int i = 0; i < gombaFonal.getGombasz().getGombaTestek().size(); ++i) {
          if (gombaFonal.getGombasz().getGombaTestek().get(i).getTartozkodik() == this) return true;
       }
    }
    return false;
  }

  /** A kör elején meghívott függvény, felszívó tekton használja */
  public void tick() {
    // A kör elején meghívott függvény, felszívó tekton használja
  }

  /**
   * A class adatait kiiro fuggveny.
   */
  public void printData() {
    System.out.println("Normalis Tekton\nFoglalt: " + foglalt);
    System.out.println("GombaFonalak:");
    for (int i = 0; i < fonalak.size(); ++i) {
      System.out.println("ID: " + i);
      fonalak.get(i).printData(this);
      fonalak.get(i).printData();
    }
    System.out.println("Sporak:");
    for (int i = 0; i < sporak.size(); ++i) {
      System.out.println("ID: " + i);
      sporak.get(i).printData();
    }
    System.out.println("Szomszed IDk:");
    for (int i = 0; i < szomszedok.size(); ++i) {
      System.out.println(Jatekvezerlo.getIDof(szomszedok.get(i)));
    }
  }
}
