package bme;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Gombasz osztaly definicioja
 * 
 * Gombaszok gombakat (gombatesteket, gombafonalakat es sporakat) iranyitanak a jatekban, es lekerdezhetik annak allasat.
 */
public class Gombasz extends Jatekos {

  //* Jatekos gombatestjeinek listaja */
  private List<GombaTest> gombaTestek = new ArrayList<GombaTest>();
  
  //* Jatekos gombafonalainak listaja */
  private List<GombaFonal> gombaFonalak = new ArrayList<GombaFonal>();
  
  //* Jatekos sporainak listaja */
  private List<Spora> sporak = new ArrayList<Spora>();

  /**
   * @param nev Konstruktorában megadható a név paraméter
   *            A leszármazottakban fognak kezelődni
   */
  public Gombasz(String nev) {
    super(nev);
  }

  /**
   *
   * @return visszaadja a GombaTestek listáját
   */
  public List<GombaTest> getGombaTestek() { return gombaTestek; }

  /**
   *
   * @return visszaadja a Spórák listáját
   */
  public List<Spora> getSporak() { return sporak; }

  /**
   *
   * @return visszaadja a Gombafonalak listáját
   */
  public List<GombaFonal> getGombaFonalak() {return gombaFonalak;}

  /**
   *
   * @param gombaTestek megadható a Gombatestek listája
   */
  public void setGombaTestek(List<GombaTest> gombaTestek) { this.gombaTestek = gombaTestek; }

  /**
   *
   * @param sporak megadható a Srórák listája
   */
  public void setSporak(List<Spora> sporak) {this.sporak = sporak; }

  /**
   * @param gombaFonalak megadható a GombaFonalak listája
   */

  public void setGombaFonalak(List<GombaFonal> gombaFonalak){this.gombaFonalak = gombaFonalak;  }

  /**
   * A jatekos lepeseert felelos fuggveny (parancssort kezeli, akciopontokkal)
   */
  @Override
  public void lep() {
    List<Boolean> testCselekedett = new ArrayList<Boolean>();
    int fonalCselekedetek = gombaTestek.size();
    List<Boolean> sporaHasznalt = new ArrayList<Boolean>();
    for (int i = 0; i < sporak.size(); ++i) sporaHasznalt.add(false);
    for (int i = 0; i < gombaTestek.size(); ++i) testCselekedett.add(false);
    Scanner scanner = new Scanner(System.in);
    System.out.println("parancsok:\nspor [gombaTestID] -tk [ID]\t\tgombaTestID gombatest utasítása spóra szórására az [ID] tektonra");
    System.out.println("growf [TektonID0] [TektonID1]\t\tgombafonal novesztese [TektonID0] tekton es [TektonID1] tekton kozott");
    System.out.println("growg [TektonID]\t\tGombatest novesztese [TektonID] tektonon");
    System.out.println("/save [filepath]\t\tJáték állásának elmentése fájlba\n/end\t\tkör befejezése\n/lsa\t\tJatekosok listazasa");
    System.out.println("/lst\t\tTektonok listazasa\n/lsg\t\tGombatestek listazasa (aktiv jatekose)");
    System.out.println("/lsf\t\tGombafonalak listazasa (aktiv jatekose)\n/lss\t\tSporak listazasa (aktiv jatekose)");
    System.out.println("/help\t\t\tparancsok megjelenitese");
    while(!endOfTurn) {
      String[] args = scanner.nextLine().strip().split(" ");
      switch (args[0]) {
        case "spor": if (!testCselekedett.get(Integer.parseInt(args[1]))) testCselekedett.set(Integer.parseInt(args[1]), sporatSzorat(args)); break;
        case "growf": if (fonalCselekedetek > 0) fonalCselekedetek -= fonalatNoveszt(args); break;
        case "growg": if (!sporaHasznalt.get(Integer.parseInt(args[1]))) {testetNoveszt(args); sporaHasznalt.set(Integer.parseInt(args[1]), true);}; break;
        case "/end": endOfTurn = true; break;
        case "/save": Jatekvezerlo.Save(args); break;
        case "/lsa": Jatekvezerlo.ListAktor(args); break;
        case "/lst": Jatekvezerlo.ListTekton(args); break;
        case "/lsg": listTest(); break;
        case "/lsf": listFonal(); break;
        case "/lss": listSpora(); break;
        case "/help": 
        System.out.println("parancsok:\nspor [gombaTestID] -tk [ID]\t\tgombaTestID gombatest utasítása spóra szórására az [ID] tektonra");
        System.out.println("growf [TektonID0] [TektonID1]\t\tgombafonal novesztese [TektonID0] tekton es [TektonID1] tekton kozott");
        System.out.println("growg [TektonID]\t\tGombatest novesztese [TektonID] tektonon");
        System.out.println("/save [filepath]\t\tJáték állásának elmentése fájlba\n/end\t\tkör befejezése\n/lsa\t\tJatekosok listazasa");
        System.out.println("/lst\t\tTektonok listazasa\n/lsg\t\tGombatestek listazasa (aktiv jatekose)");
        System.out.println("/lsf\t\tGombafonalak listazasa (aktiv jatekose)\n/lss\t\tSporak listazasa (aktiv jatekose)");
        System.out.println("/help\t\t\tparancsok megjelenitese"); break;
        default: System.out.println("Invalid command: " + args[0]); break;
      }
    }
  }

  /**
   * jatekos tipusat megado fuggveny
   */
  @Override
  public int getType() {
    return 0;
  }

  /**
   * A class adatait kiiro fuggveny.
   */
  public void printData() {
    System.out.println("Gombasz\npontok: " + pontok);
    listTest();
    listFonal();
    listSpora();
  }

  /**
   * jatekos gombatesteinek adatait kiiro fuggveny
   */
  private void listTest() {
    for (int i = 0; i < gombaTestek.size(); ++i) {
      System.out.println("GombaTest:\nID: " + i + "\nElhelyezkedési tekton ID: " + Jatekvezerlo.getIDof(gombaTestek.get(i).getTartozkodik()) + "\nSpora db: " + rovarok.get(i).getSporaDarab() + "\nElettartam: " + gombaTestek.get(i).getElettartam() + "\nFejlettseg: " + gombaTestek.get(i).getFejlett() + "\n");
    }
  }

  /**
   * jatekos gombafonalainak adatait kiiro fuggveny
   */
  private void listFonal() {
    for (int i = 0; i < gombaFonalak.size(); ++i) {
      System.out.println("GombaFonal:\nID: " + i);
      gombaFonalak.get(i).printData();
    }
  }

  /**
   * jatekos sporainak adatait kiiro fuggveny
   */
  private void listSpora() {
    for (int i = 0; i < sporak.size(); ++i) {
      System.out.println("Spora:\nID: " + i);
      sporak.get(i).printData();
    }
  }

  /**
   * Gombatesttel sporat szorato fuggveny
   * @param args parancssori argumentumok
   * @return sikeres volt-e a szoras
   */
  private boolean sporatSzorat(String[] args) {
    GombaTest gt = gombaTestek.get(Integer.parseInt(args[1]));
    int tektonid = -1;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-tk")) {
        tektonid = Integer.parseInt(args[i + 1]);
      }
    }
    if (tektonid == -1) return false;
    return gt.sporatSzor(Jatekvezerlo.tektonok.get(tektonid));
  }

  /**
   * Gombafonalat noveszto fuggveny
   * @param args parancssori argumentumok
   * @return 1, ha sikeres volt, 0, ha nem
  */
  private int fonalatNoveszt(String[] args) {
      for (int i = 0; i < gombaFonalak.size(); ++i) {
        if (gombaFonalak.athidal(Jatekvezerlo.tektonok.get(Integer.parseInt(args[1])), Jatekvezerlo.tektonok.get(Integer.parseInt(args[2])))) return 1;
      }
      return 0;
  }

  /**
   * Gombatestet noveszto fuggveny
   * @param args parancssori argumentumok
   */
  private void testetNoveszt(String[] args) {
    for (int i = 0; i < sporak.size(); ++i) {
      if (Jatekvezerlo.tektonok.get(Integer.parseInt(args[1])).sporatFelhasznal(sporak.get(i))) {
        gombaTestek.add(new GombaTest(this, 0, 5, false, 0, Jatekvezerlo.tektonok.get(Integer.parseInt(args[1]))));
        Jatekvezerlo.tektonok.get(Integer.parseInt(args[1])).addGombaTest(gombaTestek.get(gombaTestek.size() - 1));
      }
    }
  }
}
