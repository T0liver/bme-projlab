package bme;

// import bme.jdb.projlab.fungorium.tesztek.*;
import bme.teszt.JDBTestTool2;

import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("JDB tesztelő program v2.0");

    while (true) {
      System.out.println(System.lineSeparator() + "Válassz egy tesztcsoportot:");
      System.out.println("    0 - Szabad parancsmegadás");
      System.out.println("    1 - Tekton tesztek");
      System.out.println("    2 - Spóra tesztek");
      System.out.println("    3 - Gomba tesztek");
      System.out.println("    4 - Rovar tesztek");
      System.out.println("    5 - Fonal tesztek");
      System.out.println("    6 - Összes teszt futtatása");
      System.out.println("    7 - Csak a Megadott teszt futtatása");
      System.out.println("    8 - Kilépés" + System.lineSeparator());

      System.out.print("testing@JDB:$ ");
      int choice = scanner.nextInt();

      //KILEPES
      if (choice == 8) {
        break;
      }

      switch (choice) {
        case 0 -> FreeTesting();
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
    }

    System.out.println("Tesztelés befejezve.");
    scanner.close();
  }

  private static void FreeTesting() {
    JDBTestTool2 testTool = new JDBTestTool2();
    System.out.println();
    System.out.println("Szabad parancsmegadó mód    parancsok: /help");
    System.out.println();
    testTool.RunTest();
  }

  private static void ExecuteTest(String dir) {
    JDBTestTool2 testTool = new JDBTestTool2(new File(Path.of("fungorium", "testfiles", dir).toUri()));
    testTool.RunTest();
  }

  private static void TektonTesztek() {
    ExecuteTest("Tekton_hasadas_teszt1"); //Működik
    ExecuteTest("Tekton_hasadas_teszt2"); //Működik
    ExecuteTest("Termeketlentektont_tesztel"); //Működik
    ExecuteTest("Felszivotektont_tesztel");
  }

  private static void SporaTesztek(){
  ExecuteTest("Spora_Szoras"); //Működik
  ExecuteTest("Spora_Szoras_Fejlett_Gombatesttel");
  ExecuteTest("Sporaevest_tesztel");
  }

  private static void GombanTesztek() {
    ExecuteTest("Sporat_felhasznal_teszt1"); //Működik
    ExecuteTest("Sporat_felhasznal_teszt2"); //Működik
    ExecuteTest("Gombatest_halala");
    ExecuteTest("Gombatestek_hozzaadasa"); //Működik
  }

  private static void RovarTesztek() {

    ExecuteTest("Rovar_Tektont_Valt");
    //ExecuteTest("Rovar_Atlepne");
    //ExecuteTest("Rovar_oda-visszaall");
    ExecuteTest("Rovar_Vag"); //Működik
    ExecuteTest("Eletben_Tarto_Tekton_Rovar_Vag");
    ExecuteTest("Normal_Sporat_Eszik"); //Működik
    ExecuteTest("Benito_Sporat_Eszik"); //Működik
    ExecuteTest("Csorbito_Sporat_Eszik"); //Működik
    ExecuteTest("Gyorsito_Sporat_Eszik"); //Működik
    ExecuteTest("Lassito_Sporat_Eszik"); //Működik
    ExecuteTest("Oszto_Sporat_Eszik"); //Működik
  }

  private static void FonalTesztek() {
    ExecuteTest("Fonal_athidal_teszt1");
    ExecuteTest("Fonal_athidal_teszt2");
    ExecuteTest("Fonal_athidal_teszt3");
    ExecuteTest("Fonal_benult_rovart_eszik");
  }

}
