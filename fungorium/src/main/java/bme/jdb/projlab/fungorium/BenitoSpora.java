package bme.jdb.projlab.fungorium;

/**
 * GyorsítóSpóra osztály definíciója.
 *
 * <p>Ez az osztály felelős azon spórákért, amik megbénítják a rovarokat, ha megeszik őket, így a
 * rovarok nem tudnak mozogni a pályán.
 *
 * @author Oliver
 */
public class BenitoSpora extends Spora {

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param kcal a spóra tápanyagtartalma
   * @param db a spóra darabszáma
   */
  public BenitoSpora(int kcal, int db) {
    super(kcal, db);
  }

  /**
   * A spóra kifejti a hatását a rovarra, amikor azt megeszi. A rovar a spóra hatására meg fog
   * bénulni, nem tud mozogni..
   *
   * @param mire a rovar, amire kifejti a hatását
   */
  @Override
  public void hatas(Rovar mire) {
    mire.setSebesseg(0);
    mire.nemVaghat();
  }
}
