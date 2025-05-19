package bme;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * FelszivoTekton osztály definíciója.
 *
 * <p>A felszívó tekton egy olyan tekton, melyen idő után az elhelyezett spórák felszívódnak
 *
 * @author Vid
 */
public class FelszivoTekton extends Tekton {

  /** mennyi idő van a gombafonalak felszívásához hátra */
  private int hatralevoido;

  /** mennyi idő van a gombafonalak felszívások között */
  private int maxido;

  /**
   * Ez a publikus konstruktor függvény, ami beállítja az objektum tulajdonságait.
   *
   * @param tekton a kezdő tartózkodási hely
   */
  public FelszivoTekton(int ido) { // majd hardcoded is lehet
    hatralevoido = ido;
    maxido = ido;
    loadImage();
  }

  /** Publikus konstruktor */
  public FelszivoTekton() {
    hatralevoido = 2;
    maxido = 2;
    loadImage();
  }

  /** Textúra betöltésére szolgáló metódus. */
  public void loadImage() {
    try {
      img = ImageIO.read(new File("textures/Felszivo.png"));
    } catch (IOException e) {
      byte[] r = {0};
      byte[] g = {(byte) 255};
      byte[] b = {0};
      img = new BufferedImage(32, 32, 0, new IndexColorModel(1, 1, r, g, b));
      e.printStackTrace();
    }
  }

  /**
   * A kör elején meghívott függvény, visszaszámol a felszívásig, ha elérte a 0-t, eltűnteti az
   * összes rajta lévő gombafonalat és spórát
   */
  @Override
  public void tick() {
    hatralevoido--;
    if (hatralevoido <= 0) {
      List<GombaFonal> fonalak0 = new ArrayList<>();
      for (Mezo m0 : mezok) {
        fonalak0.addAll(m0.getFonalak());
      }
      for (GombaFonal fonal : fonalak0) {
        for (Mezo m0 : mezok) {
          for (Mezo m1 : terkep.getMezok()) {
            fonal.elvagodik(m0, m1);
          }
        }
      }
      for (int i = 0; i < sporak.size(); ++i) {
        sporak
            .get(i)
            .csokken(sporak.get(i).getDarabszam()); // a spórákat is felszívja specifikáció szerint
      }
      fonalak = new ArrayList<>();
      hatralevoido = maxido;
    }
  }

  /** Új tekton létrehozására szolgáló metódus felülírása. */
  @Override
  public Tekton createTekton() {
    return new FelszivoTekton();
  }
}
