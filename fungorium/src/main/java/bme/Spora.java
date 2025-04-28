package bme;

/**
 * Spóra osztály definíciója.
 *
 * <p>
 * Gombatestek termelik a spórákat, melyeket majd szomszédos tektonokon tudnak
 * elhelyezni.
 * Lehetővé teszik új gombatestek kifejlődését olyan tektonon melyen spóra
 * található. A spórák
 * továbbá hatással vannak a rovarokra is. Több fajta spóra van, eddig ismert
 * fajtái felgyorsítják,
 * lelassítják, megbénítják vagy nem engedik rágni az őket elfogyasztó
 * rovarokat. A spórák különböző
 * tápanyagtartalommal rendelkeznek, amik a rovaroknak fontosak a pontok
 * szerzésében.
 *
 * @author Oliver
 */
public class Spora implements Jatekelem {

  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  /** A spóra darabszáma. */
  protected int darabszam;

  /** Megadja, hogy melyik gombászhoz tartozik */
  protected Gombasz gombasz;

  /** A tápanyagtartalom, amit a spóra tartalmaz. */
  protected int tapanyagtartalom;

  protected Tekton tartozkodik;

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param kcal a spóra tápanyagtartalma
   * @param db   a spóra darabszáma
   */
  public Spora(int kcal, int db, Gombasz gombasz) {
    tapanyagtartalom = kcal;
    darabszam = db;
    this.gombasz = gombasz;
  }

  /**
   * Publikus getter a spora elhelyezkedesi tektonjanak megadasara.
   * 
   * @param t a tekton
   */
  public void setTartozkodik(Tekton t) {
    tartozkodik = t;
  }

  /**
   * Publikus getter a spora darabszamanak megadasara.
   * 
   * @param db a darabszam
   */
  public void setDarabszam(int db) {
    darabszam = db;
  }

  /**
   * Publikus getter a spora tapanyagertekenek megadasara.
   * 
   * @param kcal a tapertek
   */
  public void setTapanyag(int kcal) {
    tapanyagtartalom = kcal;
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
   * Publikus getter függvény a spóra gombászának a lekérdezésére.
   *
   * @return a spóra gombásza
   */
  public Gombasz getGombasz() {
    return gombasz;
  }

  /**
   * Csökkenti a spóra objektum darabszámát, amikor megeszik. Ammenyiben nagyobb a
   * mennyivel értéke,
   * mint a darabszám, akkor a a darabszám beáll 0-ra és csak az elérhető
   * mennyiséget adja vissza.
   *
   * @param mennyivel annak a száma, hogy mennyivel csökken a spóra.
   * @return a megevett spóraák tápanyagértéke.
   */
  public int csokken(int mennyivel) {
    if (darabszam <= 0) {
      return 0;
    }
    if (darabszam >= mennyivel) {
      int ret = mennyivel * tapanyagtartalom;
      darabszam -= mennyivel;
      return ret;
    } else {
      int ret = darabszam * tapanyagtartalom;
      darabszam = 0;
      return ret;
    }
  }

  /**
   * A spóra kifejti a hatását a rovarra, amikor azt megeszi.
   *
   * @param mire a rovar, amire kifejti a hatását
   */
  public void hatas(Rovar mire) {
  }

  /**
   * Növeli a spóra darabszámát a megadott értékkel.
   *
   * @param mennyivel amennyivel növelni kell a spóra darabszámát.
   */
  public void novel(int mennyivel) {
    darabszam += mennyivel;
  }

  /**
   * A class adatait kiiro fuggveny.
   */
  public void printData() {
    System.out.println("Normalis Spora\nElhelyezkedes: " + Jatekvezerlo.getIDof(tartozkodik) + "\ndb: " + darabszam
        + "\nTapanyagtartalom: " + tapanyagtartalom);
  }

  /**
   * publikus getter a spora tartozkodai helyenek lekerdezesehez
   * 
   * @return
   */
  public Tekton getTartozkodik() {
    return tartozkodik;
  }
}
