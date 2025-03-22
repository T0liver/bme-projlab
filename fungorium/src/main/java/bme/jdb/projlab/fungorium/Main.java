package bme.jdb.projlab.fungorium;

import bme.jdb.projlab.fungorium.tesztek.*;
import bme.jdb.projlab.fungorium.tesztek.SporaTeszt;


public class Main {
  public static void main(String[] args) {
      System.out.println("JDB tesztelő program v1.0");

      //itt meghívjuk a tesztfüggvényeket
      TektonTeszt.megadottTeszt();

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

      TektonTeszt.felszivoTektonTeszt();
      TektonTeszt.egyetlenFonalTektonTeszt();
      TektonTeszt.termeketlenTektonTeszt();

      SporatFelhasznalTeszt.sporatFelhasznalTeszt1();
      SporatFelhasznalTeszt.sporatFelhasznalTeszt2();

      //végén összegzés
      JDBtesttool.jdbSummary();
  }
}
