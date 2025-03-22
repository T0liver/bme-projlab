package bme.jdb.projlab.fungorium;

/**
 * Rovar osztály definíciója.
 *
 * <p>Rovarok tektonokon mozognak, tektonok között vezető gombafonalakon átkelnek, azokat
 * elvághatják és spóákat esznek, melyek különböző hatásall vannak rájuk
 *
 * @author Vid
 */
public class Rovar {

  /** A rovar sebessége */
  private int sebesseg;

  /** Boolean, ami megadja, hogy eltud-e két tekton közti gombafonalat vágni */
  private boolean vaghat;

  /** Mennyi idő múlva vághat újra a rovar gombafonalat */
  private int ujravaghat;

  /** A tekton, amin tartózkodik */
  private Tekton tartozkodik;

  /**
   * publikus getter a rovar sebességének lekérdezésére
   *
   * @return a rovar sebessége
   */
  public int getSebesseg() {
    return sebesseg;
  }

  /**
   * publikus getter a rovar fonál vágására való készségének lekérdezésére
   *
   * @return a rovar tud-e fonalat vágni
   */
  public boolean getVaghat() {
    return vaghat;
  }

  /**
   * publikus setter a rovár sebességének beállítására
   *
   * @param s az új sebesség
   */
  public void setSebesseg(int s) {
    sebesseg = s;
  }

  /**
   * publikus getter a rovar tartozkodasi helyenek lekerdezesehez
   * @return a tekton amin a rovar van
   */
  public Tekton getTartozkodik() {return tartozkodik;}

  /** publikus setter a rovar fonál vágására való készségének letiltására */
  public void nemVaghat() {
    vaghat = false;
    ujravaghat = 5; // TODO: placeholder, mennyi idő múlva vághat újra
  }

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param tekton a kezdő tartózkodási hely
   */
  public Rovar(Tekton tekton) {
    sebesseg = 1;
    vaghat = true;
    ujravaghat = 0;
    tartozkodik = tekton;
  }

  /**
   * Átállítja a rovar tartozkodik változólyát a megadott tektonra, amennyiben az elérhető a rovar
   * számára
   *
   * @param tekton az úticél, egy szomszédos, de jelenlegiről gombafonallal áthidalt tekton
   */
  public void mozog(Tekton tekton) {
    tartozkodik = tekton;
  }

  /**
   * Elvágja a gombaFonal gombafonalat a jelenlegi tartózkodási tekton és egy szomszédos tekton
   * között
   *
   * @param gombaFonal az elvágandó GombaFonal
   */
  public void vag(GombaFonal gombaFonal) {
    if (vaghat) gombaFonal.elvagodik(tartozkodik);
  }

  public int eszik(Spora spora) {
    spora.hatas(this);
    return spora.csokken(
        5); // TODO: jelenleg 5 placeholder érték, hatás még nincs, ahhoz az öröklést várom
  }

  /**
   * Kör elején meghívott függvény, ami ha a rovar nem tud vágni csökkenti az ujravaghat értékét,
   * majd ha az elérte a 0-t, visszaállítja a vaghat értékét true-ra
   */
  public void tick() {
    if (!vaghat) {
      ujravaghat--;
      if (ujravaghat <= 0) {
        vaghat = true;
      }
    }
  }
}
