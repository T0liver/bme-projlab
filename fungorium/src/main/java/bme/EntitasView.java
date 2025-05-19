package bme;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * EntitásView osztály definíciója.
 *
 * <p>Ez az osztály egy adott entitás grafikus reprezentációját kezeli.
 *
 * @author Balint
 */
public class EntitasView {
  @SuppressWarnings("unused")
  private Object entitas;

  protected Mezo mezo;
  protected Jatekos jatekos;
  protected BufferedImage kinezet;

  public EntitasView(Object entitas, Mezo mezo, Jatekos jatekos) {
    this.entitas = entitas;
    this.mezo = mezo;
    this.jatekos = jatekos;
  }

  public void draw(GameWindow gw) {}

  public BufferedImage getKinezet() {
    return kinezet;
  }

  public BufferedImage color(BufferedImage image, Color csere) {

    BufferedImage ujKep = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

    for (int y = 0; y < 32; y++) {
      for (int x = 0; x < 32; x++) {
        int rgb = image.getRGB(x, y);
        Color szin = new Color(rgb, true);

        int r = szin.getRed();
        int g = szin.getGreen();
        int b = szin.getBlue();

        if (Math.abs(r - g) < 10 && Math.abs(r - b) < 10 && Math.abs(g - b) < 10) {
          Color ujSzin =
              new Color(csere.getRed(), csere.getGreen(), csere.getBlue(), szin.getAlpha());
          ujKep.setRGB(x, y, ujSzin.getRGB());
        } else {
          // Minden más marad
          ujKep.setRGB(x, y, rgb);
        }
      }
    }
    return ujKep;
  }
}
