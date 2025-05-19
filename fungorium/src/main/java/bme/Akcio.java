package bme;

import java.io.Serializable;

/**
 * Akció osztály implementációját tartalmazó fájl.
 *
 * <p>Egy adott játékoshoz tartozó akciót képvisel, amelyet végre lehet hajtani.
 *
 * @author JDB
 */
public class Akcio implements Serializable {
  /** Az akció neve */
  protected String nev;

  /** Az a játékos, akihez az akció tartozik. */
  protected Jatekos jatekos;

  /* A gyors elérési billenytű karaktere. */
  protected char betu;

  /** Előző mező értéke dupla kattintásokhoz. */
  protected Mezo prevMezo = null;

  /**
   * Publikus konstruktor.
   *
   * @param j a játékos, akihez az akció tartozni fog.
   */
  public Akcio(Jatekos j) {
    jatekos = j;
  }

  /**
   * Az akció cselekvése, felülírandó.
   *
   * @param m0 első kattintott mező
   * @param m1 második kattintott mező
   */
  public boolean csinal(Mezo m0, Mezo m1) {
    return false;
  }

  /**
   * Publikus getter a névre.
   *
   * @return az akció neve
   */
  public String getNev() {
    return nev;
  }

  /**
   * Publikus getter a gyorselérési betűre.
   *
   * @return a gyorselérésí betű.
   */
  public char getBetu() {
    return betu;
  }

  /**
   * Publikus getter a segítő szövegre, felüírandó.
   *
   * @return a segítség szövege.
   */
  public String getHelp() {
    return "ez egy mukodest magyarazo szoveg";
  }

  /**
   * Új bemenetet (mezőre kattintást) feldolgozó metódus.
   *
   * @param clicked az újonnan kattintott mező
   */
  public void ujInput(Mezo clicked) {
    if (prevMezo == null) {
      prevMezo = clicked;
      return;
    }
    csinal(prevMezo, clicked);
    prevMezo = null;
    jatekos.setAktivAkcio(null);
  }
}
