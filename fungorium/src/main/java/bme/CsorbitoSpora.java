package bme;

/**
 * BénítóSpóra osztály definíciója.
 *
 * <p>Ez az osztály felelős azon spórákért, amik letiltják a rovaroknak a fonalak rágását, ha
 * megeszik őket.
 *
 * @author Oliver
 */
public class CsorbitoSpora extends Spora {
  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param kcal a spóra tápanyagtartalma
   * @param db a spóra darabszáma
   */
  public CsorbitoSpora(int kcal, int db, Gombasz gombasz) {
    super(kcal, db, gombasz);
  }

  /**
   * A spóra kifejti a hatását a rovarra, amikor azt megeszi. A rovar a spóra hatására képtelen lesz
   * fonalat vágni.
   *
   * @param mire a rovar, amire kifejti a hatását
   */
  @Override
  public void hatas(Rovar mire) {
    mire.nemVaghat();
  }
}
