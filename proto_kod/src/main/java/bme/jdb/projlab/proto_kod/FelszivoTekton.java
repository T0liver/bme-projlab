package bme.jdb.projlab.proto_kod;

import java.util.ArrayList;

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
  public FelszivoTekton(int ido) { // TODO: majd hardcoded is lehet
    hatralevoido = ido;
    maxido = ido;
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
      for (int i = 0; i < fonalak.size(); ++i) {
        fonalak.get(i).elvagodik(this);
        // TODO: valahogy ki kell szedni erről a tektonról, majd az általa
        // TODO: elérhető gombatestektől és a vezet listában maradt tektonokból
        // TODO: egy gráfbejárással új listát építünk (ezt a gombafonal függvénye
        // TODO: csinálja)
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
