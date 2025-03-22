package bme.jdb.projlab.proto_kod;

/**
 * GyorsítóSpóra osztály definíciója.
 * 
 * Ez az osztály felelős azon spórákért, amelyeket ha a rovar megeszik, akkor gyorsabban tud tőle haladni.
 * @author Oliver
 */
public class LassitoSpora extends Spora {

    /**
     * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
     *
     * @param kcal a spóra tápanyagtartalma
     * @param db   a spóra darabszáma
     */
    public LassitoSpora(int kcal, int db) {
        super(kcal, db);
    }

    /**
     * A spóra kifejti a hatását a rovarra, amikor azt megeszi.
     * A rovar a spóra hatására le fog lassulni.
     * @param mire a rovar, amire kifejti a hatását
     */
    @Override
    public void hatas(Rovar mire) {
        mire.setSebesseg((int) (Math.ceil(mire.getSebesseg() * 0.5)));
    }
}
