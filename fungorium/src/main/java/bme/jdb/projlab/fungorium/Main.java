package bme.jdb.projlab.fungorium;

import bme.jdb.projlab.fungorium.tesztek.JDBtesttool;
import bme.jdb.projlab.fungorium.tesztek.TektonTeszt;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");

    //itt meghívjuk a tesztfüggvényeket
    TektonTeszt.megadottTeszt();



    //végén összegzés
    JDBtesttool.jdbSummary();
  }
}
