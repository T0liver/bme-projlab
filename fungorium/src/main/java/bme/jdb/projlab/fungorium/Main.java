package bme.jdb.projlab.fungorium;

import bme.jdb.projlab.fungorium.tesztek.*;
import bme.jdb.projlab.fungorium.tesztek.SporaTeszt;


public class Main {
  public static void main(String[] args) {
      System.out.println("JDB tesztelő program v1.0");

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
      RovarVagTeszt.RovarVagTeszt();



      //végén összegzés
      JDBtesttool.jdbSummary();
  }
}
