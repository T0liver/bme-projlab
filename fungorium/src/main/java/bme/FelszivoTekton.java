package bme;

import java.util.ArrayList;
import java.util.List;

/**
 * FelszivoTekton osztály definíciója.
 *
 * <p>A felszívó tekton egy olyan tekton, melyen idő után az elhelyezett spórák felszívódnak
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
  public FelszivoTekton(int ido) { // TODO: majd hardcoded is lehet
    hatralevoido = ido;
    maxido = ido;
  }

  /**
   * A kör elején meghívott függvény, visszaszámol a felszívásig, ha elérte a 0-t, eltűnteti az
   * összes rajta lévő gombafonalat és spórát
   */
  @Override
  public void tick() {
    hatralevoido--;
    if (hatralevoido == 0) {
      for (GombaFonal fonal : fonalak) {
        List<Tekton> szomszedok = this.getSzomszed(1);
        for (Tekton tekton : szomszedok) {
          fonal.elvagodik(this, tekton);
        }
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
}
