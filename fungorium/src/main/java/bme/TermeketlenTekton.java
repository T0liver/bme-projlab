package bme;

import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Terméketlen osztály implementációja.
 *
 * <p>A tektonok egy adott típusa, ami a tulajdonságokat a Tekton osztályból örökli, azonban
 * kiegészíti/felülírja azokat. Ez az osztály felelős azon tektonokért, amelyeken nem nőhet
 * gombatest.
 *
 * @author Vid
 */
public class TermeketlenTekton extends Tekton {
  /** Publikus konstruktor */
  public TermeketlenTekton() {
    super();
    loadImage();
  }

  /** Textúra betöltésére szolgáló metódus. */
  public void loadImage() {
    try {
      img = ImageIO.read(new File("textures/termeketlen.png"));
    } catch (IOException e) {
      byte[] r = {0};
      byte[] g = {(byte) 255};
      byte[] b = {0};
      img = new BufferedImage(32, 32, 0, new IndexColorModel(1, 1, r, g, b));
      e.printStackTrace();
    }
  }

  /**
   * Függvény, ami felhasználja a gombatest készítéséhez megfelelő mennyiségű spórát
   *
   * @param mit melyik spórát használja fel
   * @return hogy a spóra felhasználása sikeres volt-e (volt e elég), mivel a tekton terméketlen, ez
   *     mindig hamis
   */
  @Override
  public boolean sporatFelhasznal(Spora mit) {
    super.sporatFelhasznal(mit);
    return false;
  }

  /** Új tekton létrehozására szolgáló metódus felülírása. */
  @Override
  public Tekton createTekton() {
    return new TermeketlenTekton();
  }

  /** Visszaadja a tekton típusát ez a függvény. */
  @Override
  public boolean getTermeketlen() {
    return true;
  }
}
