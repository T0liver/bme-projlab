package bme;

// import bme.jdb.projlab.fungorium.tesztek.*;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("JDB tesztelő program v2.0");

    while (true) {
      System.out.println(System.lineSeparator() + "Válassz egy tesztcsoportot:");
      System.out.println("    1 - Tekton tesztek");
      System.out.println("    2 - Spóra tesztek");
      System.out.println("    3 - Gomba tesztek");
      System.out.println("    4 - Rovar tesztek");
      System.out.println("    5 - Fonal tesztek");
      System.out.println("    6 - Összes teszt futtatása");
      System.out.println("    7 - Csak a Megadott teszt futtatása");
      System.out.println("    8 - Kilépés" + System.lineSeparator());

      System.out.print("testing@JDB:$ ");
      String[] choice = scanner.nextLine().strip().split(" ");
      scanner.nextLine();

      //KILEPES
      if (choice[0].equals("8")) {
        break;
      }

      /*
      switch (choice) {
        case 1 -> TektonTesztek();
        case 2 -> SporaTesztek();
        case 3 -> GombanTesztek();
        case 4 -> RovarTesztek();
        case 5 -> FonalTesztek();
        case 6 -> {
          TektonTesztek();
          SporaTesztek();
          GombanTesztek();
          RovarTesztek();
          FonalTesztek();
        }
        // case 7 -> TektonTeszt.megadottTeszt();
        default -> System.out.println("Érvénytelen választás, próbáld újra!");
      }
      // JDBtesttool.jdbSummary();
      */
    }

    System.out.println("Tesztelés befejezve.");
    scanner.close();
  }
  /*
    private static void TektonTesztek() {
      TektonTeszt.megadottTeszt();
      TektonTeszt.egyetlenFonalTektonTeszt();
      TektonTeszt.termeketlenTektonTeszt();
      TektonTeszt.felszivoTektonTeszt();
      TektonHasadasTeszt1.TektonHasadasTeszt1();
      TektonHasadasTeszt2.TektonHasadasTeszt2();
    }

      private static void SporaTesztek(){
          SporatEszikTeszt.NormalSporaTeszt();
          SporatEszikTeszt.GyorsitoSporaTeszt();
          SporatEszikTeszt.LassitoSporaTeszt();
          SporatEszikTeszt.BenitoSporaTeszt();
          SporatEszikTeszt.CsorbitoSporaTeszt();
          SporaTeszt.SporatSzorTeszt();
          SporaTeszt.FejlettenSporatSzorTeszt();
          SporatFelhasznalTeszt.sporatFelhasznalTeszt1();
          SporatFelhasznalTeszt.sporatFelhasznalTeszt2();
          SporatEszikTeszt.RovarOdaVisszaall();
      }

    private static void GombanTesztek() {
      GombaTestHalalaTeszt.GombaTestHalalaTeszt();
      GombaTestekHozzaadasaTeszt.GombaTestekHozzaadasaTeszt();
    }

    private static void RovarTesztek() {
      RovarTektonValtTeszt.RoarAtlepTeszt();
      RovarTektonValtTeszt.RovarNemTudAtlepniTeszt();
      RovarVagTeszt.RovarVagTeszt();
    }

    private static void FonalTesztek() {
      FonalAthidalTeszt.FonalAthidal1();
      FonalAthidalTeszt.FonalAthidal2();
      FonalAthidalTeszt.FonalAthidal3();
    }
  */

}
