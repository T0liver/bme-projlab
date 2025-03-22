package bme.jdb.projlab.fungorium;

import bme.jdb.projlab.fungorium.tesztek.*;

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
      GombaTestHalalaTeszt.GombaTestHalalaTeszt();
      GombaTestekHozzaadasaTeszt.GombaTestekHozzaadasaTeszt();
      TektonHasadasTeszt1.TektonHasadasTeszt1();
      TektonHasadasTeszt2.TektonHasadasTeszt2();

      //végén összegzés
      JDBtesttool.jdbSummary();
  }
}
