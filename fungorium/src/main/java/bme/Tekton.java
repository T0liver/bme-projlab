package bme;

// import static bme.Jatekvezerlo.jatekosok;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Tekton osztály definíciója.
 *
 * <p>
 * A pálya Tektonokból áll. A tektonok kettéhasadhatnak (csak, ha nincs rajta
 * gombatest), ekkor
 * minden rajtuk levő életforma elpusztul, közöttük gombafonál hidalhat át,
 * rajtuk rovarok
 * mozoghatnak, és gombatestek spórát helyezhetnek el, melyek segítségével új
 * gombatest nőhet
 *
 * @author Vid
 */
public class Tekton implements Jatekelem {

  Random r = new Random();
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  protected BufferedImage img;

  /** boolean, ami megadja, hogy a tektonon van-e gombatest */
  protected boolean foglalt;

  /** lista a tektonhoz szomszédos tektonokról */
  protected List<Tekton> szomszedok;

  /** lista a tektonon levő spórákról */
  protected List<Spora> sporak;

  /** lista a tektonon levő spórákról */
  protected List<GombaFonal> fonalak;

  protected List<Mezo> mezok;

  protected List<Rovar> rovarok;

  protected Terkep terkep;

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   */
  public Tekton() {
    foglalt = false;
    szomszedok = new ArrayList<>();
    sporak = new ArrayList<>();
    fonalak = new ArrayList<>();
    mezok = new ArrayList<>();
    rovarok = new ArrayList<>();
    try {
      img = ImageIO.read(new File("textures/Normalis.png"));
    } catch (IOException e) {
      byte[] r = {0};
      byte[] g = {(byte) 255};
      byte[] b = {0};
      img = new BufferedImage(32, 32, 0, new IndexColorModel(1, 1, r, g, b));
      e.printStackTrace();
    }
  }

  public Tekton(Terkep terkep) {
    foglalt = false;
    szomszedok = new ArrayList<>();
    sporak = new ArrayList<>();
    fonalak = new ArrayList<>();
    mezok = new ArrayList<>();
    rovarok = new ArrayList<>();
    this.terkep = terkep;
    try {
      img = ImageIO.read(new File("textures/Normalis.png"));
    } catch (IOException e) {
      byte[] r = {0};
      byte[] g = {(byte) 255};
      byte[] b = {0};
      img = new BufferedImage(32, 32, 0, new IndexColorModel(1, 1, r, g, b));
      e.printStackTrace();
    }
  }

  public void setTerkep(Terkep t) {
    terkep = t;
  }

  public BufferedImage getImage() {
    return img;
  }

  public void addMezo(Mezo m) {
    if (!mezok.contains(m)) mezok.add(m);
  }

  public void addRovar(Rovar r) {
    if (!rovarok.contains(r)) rovarok.add(r);
  }

  public void removeRovar(Rovar r) {
    if (rovarok.contains(r)) rovarok.remove(r);
  }

  public List<Mezo> getMezok() { return mezok; }

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
   * A tektonra fonbafonalat helyező függvény
   *
   * @param fonal a lehelyezendő függvény
   */
  public void addFonal(GombaFonal gf) { // legyen inkább csak setter? ez nincs az uml diagramon - jó ez [Vid]
    if (!fonalak.contains(gf)) { // ellenőrzés, ne legyen loop (redundancia)
      fonalak.add(gf); // csináljuk visszairányba
    }
  }

  /**
   * A tekton másik tektonhoz való kapcsolatát megadó függvény
   *
   * @param tekton a másik tekton
   * @return a szomszédsági státus: 0 - nem szomszéd, 1 - szomszéd és fonállal
   *         össze nem kötött, 2
   *         szomszéd és fonállal összekötött
   */
  public int milyenszomszed(Tekton tekton) {
    if (!szomszedok.contains(tekton))
      return 0;
    for (int i = 0; i < fonalak.size(); ++i)
      for (Mezo m0 : mezok) {
        for (Mezo m1 : tekton.getMezok()) {
          if (fonalak.get(i).getVezet(m0, m1))
            return 2;
        }
      }
    return 1;
  }

  /**
   * A tektont hasadásra utasító függvény
   *
   * @return lista, aminek tartalma: a két új létrejött tekton, vagy önmaga, ha
   *         nem tudott hasadni
   */
  public List<Tekton> hasad() {
  // TODO: rovar tartózkodását megtartani
  //gombatest van nem lehet hasadni, fonalak elvágódnak, spórák eltűnnek (vagy a sporatFelhasznalban az összes mezőt megvizsgálni, hogy van-e rajta olyan rovar a tekton vizsgálata helyett)
    List<Tekton> ret = new ArrayList<>();
    if (foglalt) {
      ret.add(this);
      return ret;
    }
    Tekton t1 = createTekton();
    Tekton t2 = createTekton();
    for (int i = 0; i < mezok.size(); ++i) {
      for (int e = 0; e < terkep.getMezok().size(); ++e) {
        for (int f = 0; f < mezok.get(i).getFonalak().size(); ++f) {
          mezok.get(i).getFonalak().get(f).elvagodik(mezok.get(i), terkep.getMezok().get(e));
        }
      }
    }
    boolean yAxis = r.nextBoolean();
    int miny = mezok.get(0).getPos().get(1);
    int maxy = mezok.get(0).getPos().get(1);
    int minx = mezok.get(0).getPos().get(0);
    int maxx = mezok.get(0).getPos().get(0);
    for (int i = 1; i < mezok.size(); ++i) {
      List<Integer> pos = mezok.get(i).getPos();
      if (pos.get(1) < miny) miny = pos.get(1);
      if (pos.get(1) > maxy) maxy = pos.get(1);
      if (pos.get(1) < minx) minx = pos.get(0);
      if (pos.get(1) > maxx) maxx = pos.get(0);
    }
    List<Mezo> elso = new ArrayList<>(); List<Mezo> masodik = new ArrayList<>();
    int split;
    if (yAxis) {
      split = r.nextInt(miny, maxy);
      for (int i = 0; i < mezok.size(); ++i) {
        if (mezok.get(i).getPos().get(1) <= split) {
          elso.add(mezok.get(i));
        } else {
          masodik.add(mezok.get(i));
        }
      }
    } else {
      split = r.nextInt(minx, maxx);
      for (int i = 0; i < mezok.size(); ++i) {
        if (mezok.get(i).getPos().get(0) <= split) {
          elso.add(mezok.get(i));
        } else {
          masodik.add(mezok.get(i));
        }
      }
    }
    for (int i = 0; i < elso.size(); ++i) t1.addMezo(elso.get(i));
    for (int i = 0; i < masodik.size(); ++i) t2.addMezo(masodik.get(i));
    List<List<Mezo>> szigetek = new ArrayList<>();
    szigetek.addAll(t1.getOsszefuggo());
    szigetek.addAll(t2.getOsszefuggo());
    for (int i = 0; i < szigetek.size(); ++i) {
      ret.add(createTekton());
      for (int e = 0; e < szigetek.get(i).size(); ++e) {
        ret.get(i).addMezo(szigetek.get(i).get(e));
      }
    }
    for (int i = 0; i < ret.size(); ++i) ret.get(i).collectSzomszedok();
    return ret;
  }

  public Tekton createTekton() {
    return new Tekton(terkep);
  }

  public Spora getSpora(Gombasz g) {
    for (int i = 0; i < sporak.size(); ++i) {
      if (sporak.get(i).getGombasz() == g) { // keresünk azonos fajú spórát
        return sporak.get(i);
      }
    }
    return null;
  }

  /**
   * A tekton maximum x. szomszédait megkereső függvény. Ez a gombatest
   * spórázásánál hasznos.
   *
   * @param hanyadik hanyadik szomszéd a maximális elfogadott (ha 1, akkor csak
   *                 közvetlen
   *                 szomszédok; ha 2, akkor szomszédok szomszédai, stb)
   * @return a megtalált szomszédok
   */
  public List<Tekton> getSzomszed(int hanyadik) {
    List<Tekton> ret = new ArrayList<>();
    if (hanyadik <= 0)
      return ret;
    for (int i = 0; i < szomszedok.size(); ++i) {
      ret.add(szomszedok.get(i));
      List<Tekton> check = szomszedok.get(i).getSzomszed(hanyadik - 1);
      for (int e = 0; e < check.size(); ++e) {
        if (!ret.contains(check.get(e)) && check.get(e) != this) {
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
   * @param gt        melyik gombatest szórta
   * @param random    random spóratípus-e, vagy csak alapértelmezett
   * @return valami booleant
   */
  public boolean addSpora(int mennyiseg, GombaTest gt, boolean random) {
    for (int i = 0; i < sporak.size(); ++i) {
      if (sporak.get(i).getGombasz() == gt.getGombasz()) { // keresünk azonos fajú spórát
        sporak.get(i).novel(mennyiseg);
        return false;
      }
    }
    Spora ujSpora = null;
    int adandoSporaTipus = 0;
    if (random)
      adandoSporaTipus = r.nextInt(6);
    switch (adandoSporaTipus) {
      case 0:
        ujSpora = new Spora(10, mennyiseg, gt.getGombasz());
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
        ujSpora = new Spora(3, mennyiseg, gt.getGombasz());
        ujSpora.setTartozkodik(this);
        add(ujSpora);
        break;
    }
    gt.getGombasz().addSpora(ujSpora);
    return true; // kellett újat hozzáadni
  }

  /**
   * Spórák tektonon való elhelyezését megoldó függvény
   *
   * @param mennyiseg mennyi spórát rakunk rá
   * @param gt        melyik gombatest szórta
   * @return új spóra-e a tektonon
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
    gt.getGombasz().addSpora(ujSpora);
    return true; // kellett újat hozzáadni
  }

  /**
   * Spórák tektonon való elhelyezését megoldó függvény
   *
   * @param mennyiseg mennyi spórát rakunk rá
   * @param s         melyik spórát
   * @return új spóra-e a tektonon
   */
  public boolean addSpora(int mennyiseg, Spora s) {
    if (s.getTartozkodik() == this) {
      s.novel(mennyiseg);
      return false;
    }
    s.setTartozkodik(this);
    add(s);
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
   * A tektonon levő legmagasabb tápanyagtartalmú spóra lekérdezésére használt
   * függvény
   *
   * @return a tekton spóra listájában levő legmagasabb tápanyagtartalmú spóra
   */
  public Spora getBestSpora() {
    if (sporak.isEmpty())
      throw new IndexOutOfBoundsException();
    int reti = 0;
    for (int i = 1; i < sporak.size(); ++i) {
      if (sporak.get(reti).getTapanyag() < sporak.get(i).getTapanyag())
        reti = i;
    }
    return sporak.get(reti);
  }

  /**
   * Függvény, ami felhasználja a gombatest készítéséhez megfelelő mennyiségű
   * spórát
   *
   * @param mit melyik spórát használja fel
   * @return hogy a spóra felhasználása sikeres volt-e (volt e elég)
   */
  public boolean sporatFelhasznal(Spora mit) {
    for (Rovar rovar : rovarok) {
      if (rovar.isBenult()) {
        return true;
      }
    }

    return sporak.contains(mit) && mit.csokken(10) == mit.getTapanyag() * 10;
  }

  /**
   * Függvény, ami megadja, hogy van-e gobafonál túléléséhez szükséges gombatest a
   * tektonon
   * 
   * @param gombaFonal a gombafonal, amire vizsgájuk
   * @return hogy van-e rajta olyan gombatest, ami kell a fonal túléléséhez
   */
  public boolean vanGombaTest(GombaFonal gombaFonal) {
    if (!foglalt || gombaFonal.getGombasz() == null || gombaFonal.getGombasz().getGombaTestek() == null || gombaFonal.getGombasz().getGombaTestek().isEmpty()) {
      return false;
    }

    try {
      for (GombaTest gombaTest : gombaFonal.getGombasz().getGombaTestek()) {
        if (gombaTest.getTartozkodik() == this) {
          return true;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    return false;
  }

  /**
   * A kör elején meghívott függvény, felszívó tekton használja
   */
  public void tick() {}

  public void removeSpora(Spora s) {sporak.remove(s);}

  public void collectSzomszedok() {
    szomszedok.clear();
    List<Mezo> osszes = terkep.getMezok();
    for (int i = 0; i < osszes.size(); ++i) {
      for (int e = 0; e < mezok.size() - 1; ++e) {
        if (mezok.get(e).milyenSzomszed(osszes.get(i)) > 2)
          szomszedok.add(osszes.get(i).getTekton());
      }
    }
  }

  public List<List<Mezo>> getOsszefuggo() {
      List<List<Mezo>> ret = new ArrayList<>();
      List<Mezo> talalt = new ArrayList<>();
      List<Mezo> csoport = new ArrayList<>();
      while (talalt.size() < mezok.size()) {
        csoport.clear();
        for (int i = 0; i < mezok.size(); ++i) {
          if (!talalt.contains(mezok.get(i))) {
            csoport.add(mezok.get(i));
            break;
          }
        }
        for (int i = 0; i < mezok.size(); ++i) {
          for (int e = 0; e < csoport.size(); ++i) {
            if (!talalt.contains(mezok.get(i)) && !csoport.contains(mezok.get(e)) && csoport.get(e).milyenSzomszed(mezok.get(i)) > 2) {
              csoport.add(mezok.get(i));
              break;
            }
          }
        }
        talalt.addAll(csoport);
        ret.add(csoport);
      }
      return ret;
  }

  //inithez kell
  public boolean getTermeketlen() {
    return false;
  }
}
