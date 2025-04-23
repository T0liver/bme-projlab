package bme;

/**
 * OsztóSpóra osztály definíciója.
 *
 * <p>A spórák egy adott fajtája, ami a tulajdonságokat a Spóra osztályból örökli, azonban kiegészíti
 * / felülírja azokat. Ez az osztály felelős azon spórákért, amelyeket ha a rovar megeszik, akkor
 * létrehoz egy másik ugyanolyan rovart a tektonon
 *
 * @author Oliver
 */
public class OsztoSpora extends Spora {

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param kcal a spóra tápanyagtartalma
   * @param db a spóra darabszáma
   */
  public OsztoSpora(int kcal, int db, Gombasz gombasz) {
    super(kcal, db, gombasz);
  }

  /**
   * A spóra kifejti a hatását a rovarra, azaz létrehoz egy másik
   * ugyanolyan rovart a tektonon.
   *
   * @param mire a rovar, amire kifejti a hatását
   */
  @Override
  public void hatas(Rovar mire) {
    Rovar r2 = new Rovar(mire.getRovarasz(), mire.getTartozkodik());
    r2.setSebesseg(mire.getSebesseg());
    r2.setVaghat(mire.getVaghat());
    r2.setUjravaghat(mire.getUjravaghat());
    mire.getRovarasz().addRovar(r2);
  }
}
