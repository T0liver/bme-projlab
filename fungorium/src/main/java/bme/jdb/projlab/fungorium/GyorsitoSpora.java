package bme.jdb.projlab.fungorium;

/**
 * GyorsítóSpóra osztály definíciója.
 * 
 * Ez az osztály felelős azon spórákért, amelyeket ha a rovar megeszik, akkor gyorsabban tud tőle haladni.
 * @author Oliver
 */
public class GyorsitoSpora extends Spora {

    /**
     * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
     *
     * @param kcal a spóra tápanyagtartalma
     * @param db   a spóra darabszáma
     */
    public GyorsitoSpora(int kcal, int db) {
        super(kcal, db);
    }

    /**
     * A spóra kifejti a hatását a rovarra, amikor azt megeszi.
     * A rovar a spóra hatására fel fog gyorsulni.
     * @param mire a rovar, amire kifejti a hatását
     */
    @Override
    public void hatas(Rovar mire) {
        mire.setSebesseg((int) (Math.ceil(mire.getSebesseg() * 2)));
    }
}
