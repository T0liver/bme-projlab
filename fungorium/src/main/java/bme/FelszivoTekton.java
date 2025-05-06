package bme;

import java.util.ArrayList;
import java.util.List;

/**
 * FelszivoTekton osztály definíciója.
 *
 * <p>
 * A felszívó tekton egy olyan tekton, melyen idő után az elhelyezett spórák
 * felszívódnak
 *
 * @author Vid
 */
public class FelszivoTekton extends Tekton {

  /** mennyi idő van a gombafonalak felszívásához hátra */
  private int hatralevoido;

  /** mennyi idő van a gombafonalak felszívások között */
  private int maxido;

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param tekton a kezdő tartózkodási hely
   */
  public FelszivoTekton(int ido) { // majd hardcoded is lehet
    hatralevoido = ido;
    maxido = ido;
  }

  public FelszivoTekton() {
    hatralevoido = 2;
    maxido = 2;
  }

  /**
   * A kör elején meghívott függvény, visszaszámol a felszívásig, ha elérte a 0-t,
   * eltűnteti az
   * összes rajta lévő gombafonalat és spórát
   */
  @Override
  public void tick() {
    hatralevoido--;
    if (hatralevoido == 0) {
      for (GombaFonal fonal : fonalak) {
        List<Tekton> szomszedok = this.getSzomszed(1);
        for (Tekton tekton : szomszedok) {
          for (Mezo m0 : mezok) {
            for (Mezo m1 : tekton.getMezok()) {
              fonal.elvagodik(m1, m0);
            }
          }
        }
        for (Mezo m0 : mezok)
          fonal.elvagodik(m0, m0);
      }
      for (int i = 0; i < sporak.size(); ++i) {
        sporak
            .get(i)
            .csokken(sporak.get(i).getDarabszam()); // a spórákat is felszívja specifikáció szerint
      }
      fonalak = new ArrayList<>();
      hatralevoido = maxido;
    }
  }

  @Override
  public Tekton createTekton() {
    return new FelszivoTekton();
  }

  /**
   * A class adatait kiiro fuggveny.
   *
  @Override
  public void printData() {
    System.out.println("Felszivo Tekton\nFoglalt: " + foglalt);
    System.out.println("GombaFonalak:");
    for (int i = 0; i < fonalak.size(); ++i) {
      System.out.println("ID: " + i);
      fonalak.get(i).printData();
    }
    System.out.println("Sporak:");
    for (int i = 0; i < sporak.size(); ++i) {
      System.out.println("ID: " + i);
      sporak.get(i).printData();
    }
    System.out.println("Szomszed IDk:");
    for (int i = 0; i < szomszedok.size(); ++i) {
      System.out.println(Jatekvezerlo.getIDof(szomszedok.get(i)));
    }
  }*/
}
