package bme;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.*;

/**
 * EletbenTartoTekton osztály definíciója.
 *
 * <p>
 * Ez az osztály felelős azon tektonokért, amik csak Úgy viselkednek, mintha
 * lenne megfelelő gombatest rajtuk a gombafonalak túléléséhez.
 */
public class EletbenTartoTekton extends Tekton {

  public EletbenTartoTekton() {
    loadImage();
  }

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
   * Függvény, ami megadja, hogy van-e gobafonál túléléséhez szükséges gombatest a
   * tektonon, ez mindig igaz a tekton fajtája miatt
   * 
   * @param gombaFonal a gombafonal, amire vizsgájuk
   * @return hogy van-e rajta olyan gombatest, ami kell a fonal túléléséhez, ez
   *         mindig igaz a tekton fajtája miatt
   */
  @Override
  public boolean vanGombaTest(GombaFonal gombaFonal) {
    return true;
  }

  @Override
  public Tekton createTekton() {
    return new EletbenTartoTekton();
  }

  /**
   * A class adatait kiiro fuggveny.
   *
  @Override
  public void printData() {
    System.out.println("Eletben Tarto Tekton\nFoglalt: " + foglalt);
    System.out.println("GombaFonalak:");
    for (int i = 0; i < fonalak.size(); ++i) {
      System.out.println("ID: " + i);
      fonalak.get(i).printData();
    }
    System.out.println("Sporak:");
    for (int i = 0; i < sporak.size(); ++i) {
      System.out.println("ID: " + i);
      sporak.get(i).printData();
    }
    System.out.println("Szomszed IDk:");
    for (int i = 0; i < szomszedok.size(); ++i) {
      System.out.println(Jatekvezerlo.getIDof(szomszedok.get(i)));
    }
  }*/
}
