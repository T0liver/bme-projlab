package bme;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * EgyetlenFonalTekton osztály definíciója.
 *
 * <p>Ez az osztály felelős azon tektonokért, amik csak egyetlen gombafaj fonalát engedik a
 * felszínükre, több gombafajba tartozó fonál egyidejűleg nem tartózkodhat, vagy vezethet ennek a
 * tektonnak a felszínén.
 */
public class EgyetlenFonalTekton extends Tekton {

  /**
   * Gombafonál tektonra való átérését kezelő függvény, csak egy lehet a tektonon
   *
   * @param melyik melyik gombafonál próbál áthidalni a tektonra
   */
  @Override
  public boolean fonalNohet(GombaFonal melyik) {
    boolean vanMasFonal = false;
    for (Mezo m : mezok) {
      for (GombaFonal gf : m.getFonalak()) {
        if (gf != melyik) vanMasFonal = true;
      }
    }
    return !vanMasFonal;
  }

  /** Publikus konstruktor */
  public EgyetlenFonalTekton() {
    loadImage();
  }

  /** Textúra betöltésére szolgáló metódus. */
  public void loadImage() {
    try {
      img = ImageIO.read(new File("textures/EgyetlenFonal.png"));
    } catch (IOException e) {
      byte[] r = {0};
      byte[] g = {(byte) 255};
      byte[] b = {0};
      img = new BufferedImage(32, 32, 0, new IndexColorModel(1, 1, r, g, b));
      e.printStackTrace();
    }
  }

  /** Új tekton létrehozására szolgáló metódus felülírása. */
  @Override
  public Tekton createTekton() {
    return new EgyetlenFonalTekton();
  }
}
