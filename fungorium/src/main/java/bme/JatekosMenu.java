package bme;

import java.util.List;

/**
 * JátékosMenü osztály implementációja.
 *
 * <p>Az oldalpanelben megjelenő menü, amely játékosonként eltérő, mivel mások az engedélyezett
 * műveletek.
 *
 * @author Márton
 */
public class JatekosMenu {

  /** A játékos, amihez a menü tartozik. */
  private Jatekos jatekos;

  /** A menüben foglalt akciók listája. */
  private List<Akcio> akciok;

  /**
   * Publikus getter az akciókra.
   *
   * @return az akciók listája
   */
  public List<Akcio> getAkciok() {
    return akciok;
  }

  /**
   * Publikus getter a játékosra
   *
   * @return a menü játékosa
   */
  public Jatekos getJatekos() {
    return jatekos;
  }

  /**
   * Publikus setter az akciókra.
   *
   * @param akciok a beállítandó akciók listája
   */
  public void setAkciok(List<Akcio> akciok) {
    this.akciok = akciok;
  }

  /**
   * Publikus setter a játékosra.
   *
   * @param akciok a beállítandó játékos
   */
  public void setJatekos(Jatekos jatekos) {
    this.jatekos = jatekos;
  }
}
