package bme.jdb.projlab.fungorium;

import bme.jdb.projlab.fungorium.tesztek.*;
import bme.jdb.projlab.fungorium.tesztek.SporaTeszt;


public class Main {
  public static void main(String[] args) {
      System.out.println("\nJDB tesztelő program v1.0\n");

      //itt meghívjuk a tesztfüggvényeket
      TektonTeszt.megadottTeszt();
      TektonTeszt.egyetlenFonalTektonTeszt();
      SporatEszikTeszt.NormalSporaTeszt();
      SporatEszikTeszt.GyorsitoSporaTeszt();
      SporatEszikTeszt.LassitoSporaTeszt();
      SporatEszikTeszt.BenitoSporaTeszt();
      SporatEszikTeszt.CsorbitoSporaTeszt();
      GombaTestHalalaTeszt.GombaTestHalalaTeszt();
      GombaTestekHozzaadasaTeszt.GombaTestekHozzaadasaTeszt();
      SporaTeszt.SporatSzorTeszt();
      SporaTeszt.FejlettenSporatSzorTeszt();
      RovarTektonValtTeszt.RoarAtlepTeszt();
      RovarTektonValtTeszt.RovarNemTudAtlepniTeszt();
      RovarVagTeszt.RovarVagTeszt();



      TektonHasadasTeszt1.TektonHasadasTeszt1();
      TektonHasadasTeszt2.TektonHasadasTeszt2();

      FonalAthidal.FonalAthidal1();
      FonalAthidal.FonalAthidal2();
      FonalAthidal.FonalAthidal3();

      TektonTeszt.felszivoTektonTeszt();
      TektonTeszt.egyetlenFonalTektonTeszt();
      TektonTeszt.termeketlenTektonTeszt();

      //végén összegzés
      JDBtesttool.jdbSummary();
  }
}
