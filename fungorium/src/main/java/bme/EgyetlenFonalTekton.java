package bme;

/**
 * EgyetlenFonalTekton osztály definíciója.
 *
 * <p>
 * Ez az osztály felelős azon tektonokért, amik csak egyetlen gombafaj fonalát
 * engedik a
 * felszínükre, több gombafajba tartozó fonál egyidejűleg nem tartózkodhat, vagy
 * vezethet ennek a
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
    if (!fonalak.isEmpty()) { // ha van már nyilvántartott fonál a tektonon, nem lehet új
      return;
    }
    super.fonalNo(melyik); // ha nincs, mehet, mint ha rendes lenne
  }
  
  @Override
  public Tekton createTekton() {
    return new EgyetlenFonalTekton();
  }

  /**
   * A class adatait kiiro fuggveny.
   */
  @Override
  public void printData() {
    System.out.println("Egyetlen Fonal Tekton\nFoglalt: " + foglalt);
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
  }
}
