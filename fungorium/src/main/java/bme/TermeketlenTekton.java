package bme;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;

public class TermeketlenTekton extends Tekton {

  public TermeketlenTekton() {
    super();
    loadImage();
  }

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

  @Override
  public Tekton createTekton() {
    return new TermeketlenTekton();
  }

  @Override
  public boolean getTermeketlen() {
    return true;
  }

  /**
   * A class adatait kiiro fuggveny.
   *
  @Override
  public void printData() {
    System.out.println("Termeketlen Tekton\nFoglalt: " + foglalt);
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
