package bme.jdb.projlab.fungorium;

import bme.jdb.projlab.fungorium.tesztek.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("JDB tesztelő program v1.0");

        while (!exit) {
            System.out.println("\nVálassz egy tesztcsoportot:");
            System.out.println("1 - Tekton tesztek");
            System.out.println("2 - Spóra tesztek");
            System.out.println("3 - Gomba tesztek");
            System.out.println("4 - Rovar tesztek");
            System.out.println("5 - Fonal tesztek");
            System.out.println("6 - Összes teszt futtatása");
            System.out.println("7 - Csak a Megadott teszt futtatása");
            System.out.println("8 - Kilépés");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 8) {
                exit = true;
                break;
            }

            switch (choice) {
                case 1 -> {
                    System.out.println("\nTekton tesztek:");
                    System.out.println("1 - Megadott teszt");
                    System.out.println("2 - Egyetlen fonal tekton teszt");
                    System.out.println("3 - Terméketlen tekton teszt");
                    System.out.println("4 - Felszívó tekton teszt");
                    System.out.println("5 - Hasadás teszt 1");
                    System.out.println("6 - Hasadás teszt 2");
                    int tektonChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (tektonChoice) {
                        case 1 -> TektonTeszt.megadottTeszt();
                        case 2 -> TektonTeszt.egyetlenFonalTektonTeszt();
                        case 3 -> TektonTeszt.termeketlenTektonTeszt();
                        case 4 -> TektonTeszt.felszivoTektonTeszt();
                        case 5 -> TektonHasadasTeszt1.TektonHasadasTeszt1();
                        case 6 -> TektonHasadasTeszt2.TektonHasadasTeszt2();
                    }
                }
                case 2 -> {
                    System.out.println("\nSpóra tesztek:");
                    System.out.println("1 - Normál spóra teszt");
                    System.out.println("2 - Gyorsító spóra teszt");
                    System.out.println("3 - Lassító spóra teszt");
                    System.out.println("4 - Bénító spóra teszt");
                    System.out.println("5 - Csorbító spóra teszt");
                    System.out.println("6 - Spórát szór teszt");
                    System.out.println("7 - Fejletten spórát szór teszt");
                    System.out.println("8 - Spórát felhasznál teszt 1");
                    System.out.println("9 - Spórát felhasznál teszt 2");
                    int sporaChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (sporaChoice) {
                        case 1 -> SporatEszikTeszt.NormalSporaTeszt();
                        case 2 -> SporatEszikTeszt.GyorsitoSporaTeszt();
                        case 3 -> SporatEszikTeszt.LassitoSporaTeszt();
                        case 4 -> SporatEszikTeszt.BenitoSporaTeszt();
                        case 5 -> SporatEszikTeszt.CsorbitoSporaTeszt();
                        case 6 -> SporaTeszt.SporatSzorTeszt();
                        case 7 -> SporaTeszt.FejlettenSporatSzorTeszt();
                        case 8 -> SporatFelhasznalTeszt.sporatFelhasznalTeszt1();
                        case 9 -> SporatFelhasznalTeszt.sporatFelhasznalTeszt2();
                    }
                }
                case 3 -> {
                    GombaTestHalalaTeszt.GombaTestHalalaTeszt();
                    GombaTestekHozzaadasaTeszt.GombaTestekHozzaadasaTeszt();
                }
                case 4 -> {
                    RovarTektonValtTeszt.RoarAtlepTeszt();
                    RovarTektonValtTeszt.RovarNemTudAtlepniTeszt();
                    RovarVagTeszt.RovarVagTeszt();
                }
                case 5 -> {
                    FonalAthidalTeszt.FonalAthidal1();
                    FonalAthidalTeszt.FonalAthidal2();
                    FonalAthidalTeszt.FonalAthidal3();
                }
                case 6 -> {
                    TektonTeszt.megadottTeszt();
                    TektonTeszt.egyetlenFonalTektonTeszt();
                    TektonTeszt.termeketlenTektonTeszt();
                    TektonTeszt.felszivoTektonTeszt();
                    TektonHasadasTeszt1.TektonHasadasTeszt1();
                    TektonHasadasTeszt2.TektonHasadasTeszt2();
                    SporatEszikTeszt.NormalSporaTeszt();
                    SporatEszikTeszt.GyorsitoSporaTeszt();
                    SporatEszikTeszt.LassitoSporaTeszt();
                    SporatEszikTeszt.BenitoSporaTeszt();
                    SporatEszikTeszt.CsorbitoSporaTeszt();
                    SporaTeszt.SporatSzorTeszt();
                    SporaTeszt.FejlettenSporatSzorTeszt();
                    SporatFelhasznalTeszt.sporatFelhasznalTeszt1();
                    SporatFelhasznalTeszt.sporatFelhasznalTeszt2();
                    GombaTestHalalaTeszt.GombaTestHalalaTeszt();
                    GombaTestekHozzaadasaTeszt.GombaTestekHozzaadasaTeszt();
                    RovarTektonValtTeszt.RoarAtlepTeszt();
                    RovarTektonValtTeszt.RovarNemTudAtlepniTeszt();
                    RovarVagTeszt.RovarVagTeszt();
                    FonalAthidalTeszt.FonalAthidal1();
                    FonalAthidalTeszt.FonalAthidal2();
                    FonalAthidalTeszt.FonalAthidal3();
                    JDBtesttool.jdbSummary();
                }
                case 7 -> TektonTeszt.megadottTeszt();
                default -> System.out.println("Érvénytelen választás, próbáld újra!");
            }
        }
        JDBtesttool.jdbSummary();
        System.out.println("Tesztelés befejezve.");
        scanner.close();
    }
}
