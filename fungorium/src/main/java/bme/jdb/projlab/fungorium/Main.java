package bme.jdb.projlab.fungorium;

import bme.jdb.projlab.fungorium.tesztek.JDBtesttool;
import bme.jdb.projlab.fungorium.tesztek.SporatEszikTeszt;
import bme.jdb.projlab.fungorium.tesztek.TektonTeszt;

public class Main {
  public static void main(String[] args) {
      System.out.println("JDB tesztelő program v1.0");

      //itt meghívjuk a tesztfüggvényeket
      TektonTeszt.megadottTeszt();
      TektonTeszt.egyetlenFonalTektonTeszt();
      SporatEszikTeszt.NormalSporaTeszt();
      SporatEszikTeszt.LassitoSporaTeszt();

      //végén összegzés
      JDBtesttool.jdbSummary();
  }
}
