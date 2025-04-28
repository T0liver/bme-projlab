package bme;
/**
 * EletbenTartoTekton osztály definíciója.
 *
 * <p>Ez az osztály felelős azon tektonokért, amik csak Úgy viselkednek, mintha lenne megfelelő gombatest rajtuk a gombafonalak túléléséhez.
 */
public class EletbenTartoTekton extends Tekton {

  /**
   * Függvény, ami megadja, hogy van-e gobafonál túléléséhez szükséges gombatest a tektonon, ez mindig igaz a tekton fajtája miatt
   *
   * @param gombaFonal a gombafonal, amire vizsgájuk
   * @return hogy van-e rajta olyan gombatest, ami kell a fonal túléléséhez, ez mindig igaz a tekton fajtája miatt
   */

  public boolean vanGombaTest(GombaFonal gombaFonal) {
    return true;
  }

}