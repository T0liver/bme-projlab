package bme.jdb.projlab.proto_kod;

/**
 * Spóra osztály definíciója.
 *
 * <p>Gombatestek termelik a spórákat, melyeket majd szomszédos tektonokon tudnak elhelyezni.
 * Lehetővé teszik új gombatestek kifejlődését olyan tektonon melyen spóra található. A spórák
 * továbbá hatással vannak a rovarokra is. Több fajta spóra van, eddig ismert fajtái felgyorsítják,
 * lelassítják, megbénítják vagy nem engedik rágni az őket elfogyasztó rovarokat. A spórák különböző
 * tápanyagtartalommal rendelkeznek, amik a rovaroknak fontosak a pontok szerzésében.
 *
 * @author Oliver
 */
public class Spora {
  /** A tápanyagtartalom, amit a spóra tartalmaz. */
  private int tapanyagtartalom;

  /** A spóra darabszáma. */
  private int darabszam;

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param kcal a spóra tápanyagtartalma
   * @param db a spóra darabszáma
   */
  public Spora(int kcal, int db) {
    tapanyagtartalom = kcal;
    darabszam = db;
  }

  /**
   * Publikus getter függvény a spóra tápanyagtartalmának a lekérdezésére.
   *
   * @return a spóra tápanyagtartalma
   */
  public int getTapanyag() {
    return tapanyagtartalom;
  }

  /**
   * Publikus getter függvény a spóra darabszámának a lekérdezésére.
   *
   * @return a spóra darabszáma
   */
  public int getDarabszam() {
    return darabszam;
  }

  /**
   * Csökkenti a spóra objektum darabszámát, amikor megeszik. Ammenyiben nagyobb a mennyivel értéke,
   * mint a darabszám, akkor a a darabszám beáll 0-ra és csak az elérhető mennyiséget adja vissza.
   *
   * @param mennyivel annak a száma, hogy mennyivel csökken a spóra.
   * @return a megevett spóraák tápanyagértéke.
   */
  public int csokken(int mennyivel) {
    if (darabszam == 0) {
      return 0;
    }
    if (darabszam >= mennyivel) {
      int ret = mennyivel * tapanyagtartalom;
      darabszam = 0;
      return ret;
    } else {
      int ret = darabszam * tapanyagtartalom;
      darabszam = 0;
      return ret;
    }
  }

  /**
   * Növeli a spóra darabszámát a megadott értékkel.
   * 
   * @param mennyivel amennyivel növelni kell a spóra darabszámát.
   */
  public void novel(int mennyivel) {
    darabszam += mennyivel;
  }

  // TODO: hatas(Rovar mire), amikor már megvan a Rovar osztály
}
