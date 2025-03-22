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
      GombaTestekHozzaadasaTeszt.GombaTestekHozzaadasaTeszt();
      SporaTeszt.SporatSzorTeszt();
      SporaTeszt.FejlettenSporatSzorTeszt();
      RovarTektonValtTeszt.RoarAtlepTeszt();
      RovarTektonValtTeszt.RovarNemTudAtlepniTeszt();



      //végén összegzés
      JDBtesttool.jdbSummary();
  }
}
