package bme;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Rovarasz osztaly definicioja
 * 
 * Rovaraszok rovarokat iranyitanak a jatekban, es lekerdezhetik annak allasat.
 */
public class Rovarasz extends Jatekos {
  //* Jatekos rovarainak listaja */
  private List<Rovar> rovarok = new ArrayList<>();
  private List<Boolean> cselekedhet = new ArrayList<>();
  private List<Integer> lepesek = new ArrayList<>();

    /**
   * @param nev Konstruktorában megadható a név paraméter
   *            A leszármazottakban fognak kezelődni
   */
  public Rovarasz(String nev) {
    super(nev);
  }

  /**
   * Parameter nelkuli konstruktor
   */
  public Rovarasz() {
    super();
  }


  /**
   * A jatekos lepeseert felelos fuggveny (parancssort kezeli, akciopontokkal)
   */
  @Override
  public boolean lep() {
    cselekedhet = new ArrayList<>();
    lepesek = new ArrayList<>();
    boolean endOfTurn = false;
    for (int i = 0; i < rovarok.size(); ++i) {
      rovarok.get(i).tick();
      cselekedhet.add(true);
      lepesek.add(rovarok.get(i).getSebesseg());
    }
    System.out.println("Kör kezdése, jelenlegi játékos ID: " + Jatekvezerlo.getIDof(this));
    printData();
    Scanner scanner = new Scanner(System.in);
    System.out.println("parancsok:\nmovr [rovarID] -tk [ID]\t\trovar mozgatása szomszédos tektonra\neats [rovarID] -db [int]\t\trovar megetetése [int] db spórával");
    System.out.println("cutf [rovarID] -tk [tektonID] -if [jatekosID]\t\tjatekosID fonalának elvágása a kiválasztott rovar elhelyezkedése és tektonID között");
    System.out.println("/save [filepath]\t\tJáték állásának elmentése fájlba\n/end\t\tkör befejezése\n/lsa [ID]\t\tJatekosok listazasa; [ID] opcionális, egy játékos adatainak kiírása");
    System.out.println("/lst [ID]\t\tTektonok listazasa; [ID] opcionális, egy tekton adatainak kiírása\n/lsr [ID]\t\tRovarok listazasa (aktiv jatekose); [ID] opcionális, egy rovar adatainak kiírása");
    System.out.println("/help\t\t\tparancsok megjelenitese\n/exit\t\t\tkilepes a jatekbol");
    while(!endOfTurn) {
      try {
        String[] args = scanner.nextLine().strip().split(" ");
        switch (args[0]) {
          case "movr": if (lepesek.get(Integer.parseInt(args[1])) > 0) System.out.println(lepesek.set(Integer.parseInt(args[1]), lepesek.get(Integer.parseInt(args[1])) - mozgat(args)) < lepesek.get(Integer.parseInt(args[1])) ? "Mozgás sikeres" : "Mozgás sikertelen"); break;
          case "eats": if (cselekedhet.get(Integer.parseInt(args[1]))) System.out.println(pontok < (pontok += megetet(args)) ? "Evés sikeres, jelenlegi pontszám: " + pontok : "Evés sikertelen, jelenlegi pontszám: " + pontok); cselekedhet.set(Integer.parseInt(args[1]), false); break;
          case "cutf": if (cselekedhet.get(Integer.parseInt(args[1]))) System.out.println(elvagat(args) ? "Vágás sikeres" : "Vágás sikertelen"); break;
          case "/end": endOfTurn = true; break;
          case "/save": Jatekvezerlo.Save(args); break;
          case "/lsa": Jatekvezerlo.ListAktor(args); break;
          case "/lst": Jatekvezerlo.ListTekton(args); break;
          case "/lsr": if(args.length == 1) {listRovar();} else {listRovar(Integer.valueOf(args[1]));} break;
          case "/help":
          System.out.println("parancsok:\nmovr [rovarID] -tk [ID]\t\trovar mozgatása szomszédos tektonra\neats [rovarID] -db [int]\t\trovar megetetése [int] db spórával");
          System.out.println("cutf [rovarID] -tk [tektonID] -if [jatekosID]\t\tjatekosID fonalának elvágása a kiválasztott rovar elhelyezkedése és tektonID között");
          System.out.println("/save [filepath]\t\tJáték állásának elmentése fájlba\n/end\t\tkör befejezése\n/lsa [ID]\t\tJatekosok listazasa; [ID] opcionális, egy játékos adatainak kiírása");
          System.out.println("/lst [ID]\t\tTektonok listazasa; [ID] opcionális, egy tekton adatainak kiírása\n/lsr [ID]\t\tRovarok listazasa (aktiv jatekose); [ID] opcionális, egy rovar adatainak kiírása");
          System.out.println("/help\t\t\tparancsok megjelenitese\n/exit\t\t\tkilepes a jatekbol"); break;
          case "/exit": return true;
          default: System.out.println("Invalid command: " + args[0]); break;
        }
      } catch (Exception e) { System.out.println("Invalid Syntax");}
    }
    return false;
  }

  /**
   * fuggveny, amiben egy rovart mozgat egy masik tektonra
   * @param args parancssori argumentumok
   * @return 1, ha mozgott, 0, ha nem
   */
  private int mozgat(String[] args) {

    Rovar rovar = rovarok.get(Integer.parseInt(args[1]));
    int tektonID = -1;

    for (int i = 0; i < args.length; i++) {
        if(args[i].equals("-tk")){
            tektonID = Integer.parseInt(args[i + 1]);
        }
    }
    if (tektonID == -1) return 0; 
    rovar.mozog(Jatekvezerlo.tektonok.get(tektonID));
    return 1;
    //System.out.println("EVENT Rovar mozog\nRovar (" + args[1] + ") Tekton: (" + oldValue + ") --> Tekton (" + tektonID + ")");
  }


  /**
   * fuggveny, amiben egy rovarral megetet sporat tartozkodasi tektonjan
   * @param args parancssori argumentumok
   * @return evesert kapott pontszam
   */
  private int megetet(String[] args) {

    Rovar rovar = rovarok.get(Integer.parseInt(args[1]));
    Spora spora = rovar.getTartozkodik().getBestSpora();
    int db = 3;

    for (int i = 0; i < args.length; i++) {
        if (args[i].equals("-db")) {
            db = Integer.parseInt(args[i + 1]) <= 6 ? Integer.parseInt(args[i + 1]) : 6;
        }
    }

    return rovar.eszik(spora, db);
    //System.out.println("EVENT Spóra evés\nSpóra (" + spora.getId() +") darabszám " + oldValue + " --> " + spora.getDarabszam() + "\nRovarász (" + getId() + ") pontok: " + oldPont + " --> " + pontok);
    //return pontnoves;
  }

  /**
   * fuggveny, amiben egy rovarral elvagat egy fonalat egy tekton iranyaban
   * @param args parancssori argumentumok
   * @return sikeresen vagott-e
   */
  private boolean elvagat(String[] args) {
    int tektonID = -1;
    int fonalID = -1;
    Rovar rovar = rovarok.get(Integer.parseInt(args[1]));
    for (int i = 0; i < args.length; i++) {
      if(args[i].equals("-tk")){
          tektonID = Integer.parseInt(args[i + 1]);
      }
      if(args[i].equals("-if")){
          fonalID = Integer.parseInt(args[i + 1]);
      }
    }
    if (tektonID == -1 || fonalID == -1) return false;

    Tekton merre = rovar.getTartozkodik();
    GombaFonal gombaFonal = new GombaFonal();
    int oldID = -1;

    for (int i = 0; i < rovar.getTartozkodik().getFonalak().size(); ++i) {
      if (Jatekvezerlo.getIDof(rovar.getTartozkodik().getFonalak().get(i).getGombasz()) == fonalID) {
        gombaFonal = rovar.getTartozkodik().getFonalak().get(i); break;
      }
    }
    if (oldID == -1) return false;

    for (int i = 1; i < rovar.getTartozkodik().getSzomszed(1).size(); ++i) { //1-ről, mert a 0. saját maga
      if (Jatekvezerlo.getIDof(rovar.getTartozkodik().getSzomszed(1).get(i)) == tektonID) {
        merre = rovar.getTartozkodik().getSzomszed(1).get(i); break;
      }
    }
    if (merre == rovar.getTartozkodik()) return false;

    return rovar.vag(gombaFonal, merre);
  }

  /**
   * jatekos tipusat megado fuggveny
   */
  @Override
  public int getType() {
    return 1;
  }

  /*
   * Publikus getter függvény a Rovarász rovarjainak listájának lekérdezésére.
   *
   * @return a Rovarász rovarjainak listája
   */
  public List<Rovar> getRovarok() {
    return rovarok;
  }

  /*
   * Publikus setter függvény a Rovarász rovarjainak listájának beállítására.
   *
   * @param rovarok a Rovarász új rovarjainak listája
   */
  public void addRovar(Rovar rovar) {
    rovarok.add(rovar);
  }

  /**
   * A class adatait kiiro fuggveny.
   */
  public void printData() {
    System.out.println("Rovarasz\npontok: " + pontok);
    listRovar();
  }

  /**
   * A rovarasz rovarainak adatait kiiro fuggveny.
   */
  private void listRovar() {
    for (int i = 0; i < rovarok.size(); ++i) {
      System.out.println("Rovar:\nID: " + i + "\nElhelyezkedesi tekton ID: " + Jatekvezerlo.getIDof(rovarok.get(i).getTartozkodik()) + "\nMozások: " + lepesek.get(i) + "\nEhet/Vághat-e ebben a körben: " + cselekedhet.get(i) + "\nKépes vágni: " + rovarok.get(i).getVaghat());
    }
  }
  private void listRovar(int i) {
    System.out.println("Rovar:\nID: " + i + "\nElhelyezkedesi tekton ID: " + Jatekvezerlo.getIDof(rovarok.get(i).getTartozkodik()) + "\nMozások: " + lepesek.get(i) + "\nEhet/Vághat-e ebben a körben: " + cselekedhet.get(i) + "\nKépes vágni: " + rovarok.get(i).getVaghat());
  }
}