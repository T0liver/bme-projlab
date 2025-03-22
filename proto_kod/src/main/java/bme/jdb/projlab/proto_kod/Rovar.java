package bme.jdb.projlab.proto_kod;

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

  /** A tekton, amin tartózkodik */
  // private Tekton tartozkodik;

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param tekton a kezdő tartózkodási hely
   */
  public Rovar(/* Tekton tekton */ ) {
    sebesseg = 1;
    vaghat = true;
    // tartozkodik = tekton;
  }

  /**
   * Átállítja a rovar tartozkodik változólyát a megadott tektonra, amennyiben az elérhető a rovar
   * számára
   *
   * @param tekton az úticél, egy szomszédos, de jelenlegiről gombafonallal áthidalt tekton
   */
  // public void mozog(Tekton tekton) //Tektonra vár

  /**
   * Elvágja a gombaFonal gombafonalat a jelenlegi tartózkodási tekton és egy szomszédos tekton
   * között
   *
   * @param gombaFonal az elvágandó GombaFonal
   */
  // public void vag(GombaFonal gombaFonal) //GombaFonalra vár

  public int eszik(Spora spora) {
    // spora.hatas(this);
    return spora.csokken(
        5); // jelenleg 5 placeholder érték, hatás még nincs, ahhoz az öröklést várom
  }
}
