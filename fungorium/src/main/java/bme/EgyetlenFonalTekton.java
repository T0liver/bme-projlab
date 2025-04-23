package bme;

/**
 * EgyetlenFonalTekton osztály definíciója.
 *
 * <p>Ez az osztály felelős azon tektonokért, amik csak egyetlen gombafaj fonalát engedik a
 * felszínükre, több gombafajba tartozó fonál egyidejűleg nem tartózkodhat, vagy vezethet ennek a
 * tektonnak a felszínén.
 */
public class EgyetlenFonalTekton extends Tekton {

  /**
   * Gombafonál tektonra való átérését kezelő függvény, csak egy lehet a tektonon
   *
   * @param melyik melyik gombafonál próbál áthidalni a tektonra
   */
  @Override
  public void fonalNo(GombaFonal melyik) {
    if (fonalak.size() > 0) { // ha van már nyilvántartott fonál a tektonon, nem lehet új
      return;
    }
    super.fonalNo(melyik); // ha nincs, mehet, mint ha rendes lenne
  }
}
