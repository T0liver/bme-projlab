package bme;

/**
 * Leír és típusba foglal egy játékhoz tartozó objektumot, amiből minden objektum leszármazik.
 *
 * @author idk, valaki az előző iterációból
 */
public interface Jatekelem {
  /**
   * Publikus getter a játékelem azonosítójára.
   *
   * @return az objektum azonosítója.
   */
  public int getId();

  /**
   * Publikus setter az objektum azonosítóra.
   *
   * @param id a beállítandó azonosító.
   */
  public void setId(int id);
}
