package bme;

// import static bme.Jatekvezerlo.jatekosok;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
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
public class Tekton implements Jatekelem, Serializable {

  Random r = new Random();
  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  protected transient BufferedImage img;

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
    loadImage();
  }

  public Tekton(Terkep terkep) {
    foglalt = false;
    szomszedok = new ArrayList<>();
    sporak = new ArrayList<>();
    fonalak = new ArrayList<>();
    mezok = new ArrayList<>();
    rovarok = new ArrayList<>();
    this.terkep = terkep;
    loadImage();
  }

  public void loadImage() {
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
    List<Tekton> ret = new ArrayList<>();

    // If this Tekton is reserved, do not split
    if (foglalt) {
        ret.add(this);
        return ret;
    }

    // Cut threads
    for (Mezo mezo : mezok) {
        for (Mezo target : terkep.getMezok()) {
          for (GombaFonal fonal : new ArrayList<>(mezo.getFonalak())) {
            fonal.elvagodik(mezo, target);
          }          
        }
    }

    // Decide whether to split along Y or X axis
    boolean yAxis = r.nextBoolean();

    // Calculate bounding box
    int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
    int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
    for (Mezo m : mezok) {
        int x = m.getPos().get(0);
        int y = m.getPos().get(1);
        if (x < minX) minX = x;
        if (x > maxX) maxX = x;
        if (y < minY) minY = y;
        if (y > maxY) maxY = y;
    }

    List<Mezo> elso = new ArrayList<>();
    List<Mezo> masodik = new ArrayList<>();
    if (yAxis && minY < maxY) {
        int split = r.nextInt(minY, maxY);
        for (Mezo m : mezok) {
            if (m.getPos().get(1) <= split) {
                elso.add(m);
            } else {
                masodik.add(m);
            }
        }
    } else if (!yAxis && minX < maxX) {
        int split = r.nextInt(minX, maxX);
        for (Mezo m : mezok) {
            if (m.getPos().get(0) <= split) {
                elso.add(m);
            } else {
                masodik.add(m);
            }
        }
    } else {
        // Cannot split (region too small), return self
        ret.add(this);
        return ret;
    }

    // Assign split Mezo sets to two new Tektons
    Tekton t1 = createTekton();
    Tekton t2 = createTekton();
    for (Mezo m : elso) {
      t1.addMezo(m);
      m.setTekton(t1);
    }
    for (Mezo m : masodik) {
      t2.addMezo(m);
      m.setTekton(t2);
    }

    // Separate disconnected subregions
    List<List<Mezo>> szigetek = new ArrayList<>();
    szigetek.addAll(t1.getOsszefuggo());
    szigetek.addAll(t2.getOsszefuggo());

    for (List<Mezo> sziget : szigetek) {
        Tekton uj = createTekton();
        for (Mezo m : sziget) {
            uj.addMezo(m);
            m.setTekton(uj);
        }
        ret.add(uj);
    }

    // Finalize each Tekton
    for (Tekton t : ret) {
        for (Mezo m : t.getMezok()) {
            m.setTekton(t);
        }
        t.setTerkep(terkep);
        t.collectSzomszedok(terkep);
    }


    if (ret.isEmpty()) {
        ret.add(this);
    }

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
    if (hanyadik == 1) return szomszedok;
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

    //return sporak.contains(mit) && mit.csokken(5) == mit.getTapanyag() * 10;

    if (sporak.contains(mit)) {
      return  mit.csokken(5) == mit.getTapanyag() * 5;
    }

    return false;
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

  public void collectSzomszedok(Terkep t) {
    szomszedok.clear();
    List<Mezo> osszes = t.getMezok();
    for (int i = 0; i < mezok.size(); ++i) {
      for (int e = 0; e < osszes.size(); ++e) {
        if (osszes.get(e).milyenSzomszed(mezok.get(i)) > 0 && !szomszedok.contains(mezok.get(i).getTekton())) {
          szomszedok.add(osszes.get(e).getTekton());
        }
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
          for (int e = 0; e < csoport.size(); ++e) {
            if (!talalt.contains(mezok.get(i)) && !csoport.contains(mezok.get(e)) && csoport.get(e).getOrtoSzomszedok().contains(mezok.get(i))) {
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

  public boolean hasSpora(GombaFonal gombaFonal) {
    for (int i = 0; i < sporak.size(); ++i) {
      if (sporak.get(i).getGombasz() == gombaFonal.getGombasz()) return true;
    }
    return false;
  }

  public boolean fonalNohet(GombaFonal gf) { return true; }
}
