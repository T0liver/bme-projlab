package bme;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.naming.directory.InvalidAttributeValueException;

import main.java.bme.EletbenTartoTekton;

/**
 * Jatekvezerlo osztaly definicioja
 */
public class Jatekvezerlo {
  /** jelenlegi kor szama */
  public static int jelenlegiKor = 0;
  /** tektonok listaja */
  public static List<Tekton> tektonok = new ArrayList<Tekton>();
  /** jatekosok listaja */
  public static List<Jatekos> jatekosok = new ArrayList<Jatekos>();
  /** jelenlegi jatekos szama */
  public static int jelenlegiJatekos = 0;
  /** mennyi kor egy jatek */
  public static int jatekHossz = 50;
  /** engedelyezve van-e a random */
  public static boolean random = false;
  /** a random osztaly */
  public static Random r = new Random();

  /**
  * privat konstruktor, mert warning
  */
  private Jatekvezerlo() {
    /*jelenlegiKor = 0;
    tektonok = new ArrayList<Tekton>();
    jatekosok = new ArrayList<Jatekos>();
    jelenlegiJatekos = 0;
    jatekHossz = 50;
    random = false;*/
  }

  /**
   * Tekton hasitasara hasznalt fuggveny
   * ha van random, randomot hasit
   * ha nincs, minden 5. korben a korszamadikat
   */
  public static void tektontHasit() {
    Tekton talalt = null;
    if (random && r.nextInt(4) == 0) {
      talalt = tektonok.get(r.nextInt(tektonok.size()));
    }
    if (!random && jelenlegiKor % 5 == 4) {
      talalt = tektonok.get(jelenlegiKor % tektonok.size());
    }
    if (talalt == null) return;
    List<Tekton> ujak = talalt.hasad();
    if (ujak.size() == 1) return;
    tektonok.remove(talalt);
    tektonok.addAll(ujak);
  }

  /**
   * tick fuggveny a tektonok tick fuggvenyeinek meghivasara
   */
  public static void tick() {
    for (int i = 0; i < tektonok.size(); ++i) tektonok.get(i).tick();
  }

  /**
   * Ez kor vegen a tick() es hadasas meghivasa
   */
  public static void korVege() {
    tick();
    tektontHasit();
  }

  /**
   * fuggveny egy korben a jatekosok leptetesere
   */
  public static void korMenete() {
    for (int i = 0; i < jatekosok.size(); ++i) {
      jatekosok.get(i).lep();
    }
  }

  /**
   * fuggveny a jatek vegen a nyertesek kiirasara
   */
  public static void jatekVege() {
    int gombaszIndex = -1;
    int rovaraszIndex = -1;
    for (int i = 0; i < jatekosok.size(); ++i) {
      if (jatekosok.get(i).getType() == 0
          && (gombaszIndex == -1
              || jatekosok.get(i).getPontok() > jatekosok.get(gombaszIndex).getPontok())) {
        gombaszIndex = i;
      }
      if (jatekosok.get(i).getType() == 1
          && (rovaraszIndex == -1
              || jatekosok.get(i).getPontok() > jatekosok.get(rovaraszIndex).getPontok())) {
        rovaraszIndex = i;
      }
    }
    System.out.println("Nyertes Rovarász: " + rovaraszIndex + ". játékos!");
    System.out.println("Nyertes Gombász: " + gombaszIndex + ". játékos!");
  }

  /**
   * fuggveny a jatek kezdesere es porgetesere
   */
  public static void jatekKezdes() {
    jatekHossz = 50;
    try {
      init();
    } catch (InvalidAttributeValueException e) {
      e.printStackTrace();
    }
    for (jelenlegiKor = 0; jelenlegiKor < 50; ++jelenlegiKor) {korMenete(); korVege();}
  }

  /**
   * fuggveny jatekos hozzaadasara a jatekosok listahoz
   * @param j a hozzaaadando jatekos
   */
  public static void addJatekos(Jatekos j) {
    jatekosok.add(j);
  }

  /**
   * fuggveny jatekos hozzaadasara a jatekosok listahoz parancssorrol
   * @param args parancssori argumentumok
   */
  public static void addJatekos(String[] args) {
    Jatekos uj = null;
    switch (args[1]) {
      case "r": uj = new Rovarasz(); break;
      case "g": uj = new Gombasz(); break;
      default: System.out.println("rossz formátumú parancs");
    }
    if (uj == null) return;
    if (uj.getType() != -1) addJatekos(uj);
    if (uj.getType() == 0) System.out.println("Gombász hozzáadva, id: " + (jatekosok.size() - 1));
    if (uj.getType() == 1) System.out.println("rovarász hozzáadva, id: " + (jatekosok.size() - 1));
  }

  /**
   * fuggveny a jatek inicializalasahoz parancssorrol
      * @throws InvalidAttributeValueException 
      */
     public static void init() throws InvalidAttributeValueException {
    jelenlegiJatekos = 0;
    int gombaszok = 0;
    int rovaraszok = 0;
    System.out.println("parancsok:\n/random -set <on|off>\t\trandom funkció beállítása\n/adda <r|g>\t\taktor [Rovarász/Gombász] hozzáadása\n/load [filepath]\t\tjáték betöltése fájlból\n/start\t\t\tjáték indítása\n/help\t\r\rparancsok megjelenitese");
    boolean startGame = false;
    boolean loaded = false;
    Scanner scanner = new Scanner(System.in);
    while(!startGame) {
      String[] args = scanner.nextLine().strip().split(" ");
      switch (args[0]) {
        case "/random": if (args[1] == "on") random = true; if (args[1] == "off") random = false; System.out.println("Random: " + random); break;
        case "/adda": addJatekos(args); break;
        case "/start": startGame = true; break;
        case "/load": if(Jatekvezerlo.Load(args)) loaded = true; break;
        case "/help": System.out.println("parancsok:\n/random -set <on|off>\t\trandom funkció beállítása\n/adda <r|g>\t\taktor [Rovarász/Gombász] hozzáadása\n/load [filepath]\t\tjáték betöltése fájlból\n/start\t\t\tjáték indítása\n/help\t\r\rparancsok megjelenitese"); break;
        default: System.out.println("Invalid command: " + args[0]); break;
      }
    }

    for (int i = 0; i < jatekosok.size(); ++i) {
      gombaszok += 1 - jatekosok.get(i).getType();
      rovaraszok += jatekosok.get(i).getType();
    }
    if (gombaszok < 2 || rovaraszok < 2)
      throw new InvalidAttributeValueException("nincs elég Gombász/Rovarász");
    if (!loaded) {
      if (!random) {
        for (int i = 0; i < 25; ++i) {
          tektonok.add(new Tekton());
        }
        tektonok.set(1, new TermeketlenTekton());
        tektonok.set(23, new EgyetlenFonalTekton());
        tektonok.set(3, new FelszivoTekton());
        tektonok.set(21, new EletbenTartoTekton());
      } else {
        for (int i = 0; i < 25; ++i) {
          switch(r.nextInt(25)) {
            case 0: tektonok.add(new TermeketlenTekton()); break;
            case 1: tektonok.add(new EgyetlenFonalTekton()); break;
            case 2: tektonok.add(new FelszivoTekton()); break;
            case 3: tektonok.add(new EletbenTartoTekton()); break;
            default: tektonok.add(new Tekton());
          }
          tektonok.add(new Tekton());
        }
      }

        for (int i = 0; i < 5; ++i) {
          for (int e = 0; e < 5; ++e) {
            for (int j = -1; j < 2; ++j) {
              for (int k = -1; k < 2; ++k) {
                int x = ((i + j) * 5 + 25) % 25;
                int y = ((e + k) + 5) % 5;
                tektonok.get(i).addSzomszed(tektonok.get(x + y));
              }
            }
          }
        }

        for (int i = 0; i < jatekosok.size(); ++i) {
          if (jatekosok.get(i).getType() == 0) {
            GombaTest gt = null;
            try {
              gt = new GombaTest(jatekosok.get(i), 5, tektonok.get(tektonok.size()/jatekosok.size() * i));
            } catch (Exception e) {
              e.printStackTrace();
            }
            GombaFonal gf = new GombaFonal();
            tektonok.get(tektonok.size()/jatekosok.size() * i).setFoglalt(true);
            gf.addVezet(tektonok.get(tektonok.size()/jatekosok.size() * i), tektonok.get(tektonok.size()/jatekosok.size() * i));
            jatekosok.get(i).addGombaTest(gt);
            jatekosok.get(i).addGombaFonal(gf);
          } else {
            Rovar r = new Rovar((Rovarasz) jatekosok.get(i), tektonok.get(tektonok.size()/jatekosok.size() * i));
            jatekosok.get(i).addRovar(r);
          }
        }
    }
  }

  /**
   * fuggveny a jatek mentesehez parancssorrol
   * @param args parancssori argumentumok (mentesi fajl)
   */
  public static void Save(String[] args) {
    if (args.length == 0) {
        System.out.println("Nem adtál meg mentési fájlt.");
        return;
    }

    String filePath = args[1];
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
        // Játék állapotának mentése
        oos.writeInt(Jatekvezerlo.jelenlegiKor);
        oos.writeInt(Jatekvezerlo.jelenlegiJatekos);
        oos.writeInt(Jatekvezerlo.jatekHossz);
        oos.writeObject(Jatekvezerlo.tektonok);
        oos.writeObject(Jatekvezerlo.jatekosok);

        System.out.println("Játék elmentve sikeresen: " + filePath);
    } catch (Exception e) {
        System.out.println("Hiba a játék mentésekor: " + e.getMessage());
        e.printStackTrace();
    }
  }

  /**
   * fuggveny jatek betoltesehez parancssorrol
   * @param args parancssoria rgumentumok (mentesi fajl)
   * @return a visszatoltes sikeressege
   */
  private static boolean Load(String[] args) {
    if (args.length == 0) {
        System.out.println("Nem adtál meg mentési fájlt.");
        return false;
    }

    String filePath = args[1];
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
        // Feltételezve, hogy a Jatekvezerlo osztály statikus változóit tölti vissza
        Jatekvezerlo.jelenlegiKor = ois.readInt();
        Jatekvezerlo.jelenlegiJatekos = ois.readInt();
        Jatekvezerlo.jatekHossz = ois.readInt();
        Jatekvezerlo.tektonok = (List<Tekton>) ois.readObject();
        Jatekvezerlo.jatekosok = (List<Jatekos>) ois.readObject();

        System.out.println("Játék betöltve sikeresen: " + filePath);
    } catch (Exception e) {
        System.out.println("Hiba a játék betöltésekor: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
    return true;
  }

  /**
   * fuggveny tekton id-jenek lekerdezesere
   * @param t a tekton, aminek idjere kivancsiak vagyunk
   * @return a keresett id
   */
  public static int getIDof(Tekton t) {
    for (int i = 0; i < tektonok.size(); ++i) {
      if (t == tektonok.get(i)) return i;
    }
    return -1;
  } 

  /**
   * fuggveny jatekos id-jenek lekerdezesere
   * @param j a jatekos, aminek idjere kivancsiak vagyunk
   * @return a keresett id
   */
  public static int getIDof(Jatekos j) {
    for (int i = 0; i < jatekosok.size(); ++i) {
      if (j == jatekosok.get(i)) return i;
    }
    return -1;
  } 

  /**
   * fuggveny a jatekban levo jatekosok kilistazasara
   * @param args parancssori argumentumok
   */
  public static void ListAktor(String[] args) {
    System.out.println("Jatekosok listaja:");
    for (int i = 0; i < jatekosok.size(); ++i) {
      System.out.println("ID: " + i);
      jatekosok.get(i).printData();
    }
  }

  /**
   * fuggveny a jatekban levo tektonok kilistazasara
   * @param args parancssori argumentumok
   */
  public static void ListTekton(String[] args) {

    System.out.println("Tektonok listaja:");
    for (int i = 0; i < tektonok.size(); ++i) {
      System.out.println("ID: " + i);
      tektonok.get(i).printData();
    }
  }
}
