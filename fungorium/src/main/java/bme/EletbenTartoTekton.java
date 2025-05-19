package bme;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * EletbenTartoTekton osztály definíciója.
 *
 * <p>Ez az osztály felelős azon tektonokért, amik csak Úgy viselkednek, mintha lenne megfelelő
 * gombatest rajtuk a gombafonalak túléléséhez.
 */
public class EletbenTartoTekton extends Tekton {
  /** Publikus konstruktor */
  public EletbenTartoTekton() {
    loadImage();
  }

  /** Textúra betöltésére szolgáló metódus. */
  public void loadImage() {
    try {
      img = ImageIO.read(new File("textures/EletbenTarto.png"));
    } catch (IOException e) {
      byte[] r = {0};
      byte[] g = {(byte) 255};
      byte[] b = {0};
      img = new BufferedImage(32, 32, 0, new IndexColorModel(1, 1, r, g, b));
      e.printStackTrace();
    }
  }

  /**
   * Függvény, ami megadja, hogy van-e gobafonál túléléséhez szükséges gombatest a tektonon, ez
   * mindig igaz a tekton fajtája miatt
   *
   * @param gombaFonal a gombafonal, amire vizsgájuk
   * @return hogy van-e rajta olyan gombatest, ami kell a fonal túléléséhez, ez mindig igaz a tekton
   *     fajtája miatt
   */
  @Override
  public boolean vanGombaTest(GombaFonal gombaFonal) {
    return true;
  }

  /** Új tekton létrehozására szolgáló metódus felülírása. */
  @Override
  public Tekton createTekton() {
    return new EletbenTartoTekton();
  }
}
