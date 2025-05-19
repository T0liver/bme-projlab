package bme;

/**
 * Gombatest növesztő akció osztály implementációja.
 *
 * <p>Egy új gombatest növesztését tartalmazó akció.
 *
 * @author Vid
 */
public class TestNovesztesAkcio extends Akcio {

  /** Az akcióhoz tartozó gombász. */
  Gombasz g;

  /**
   * Publikus konstruktor az akcióhoz.
   *
   * @param j az akcióhoz tartozandó játékos
   */
  public TestNovesztesAkcio(Gombasz j) {
    super(j);
    g = j;
    betu = 'T';
    nev = "TEST NÖVESZTÉS";
  }

  /**
   * Csinál metódus felülírása, hogy a gombatest új gombatestet növesszen.
   *
   * @param m0 elsőre kattinrott mező.
   * @param m0 másodikra kattinrott mező.
   */
  @Override
  public boolean csinal(Mezo m0, Mezo m1) {
    return g.testetNoveszt(m0.getTekton());
  }

  /**
   * Segítő szöveg felülírása
   *
   * @return Segítő szöveg a gombatest növesztéséhez.
   */
  @Override
  public String getHelp() {
    return "Gombatest növesztés:\nKattints a tektonra, amin gombatestet növesztenél!";
  }

  /**
   * Új bemenet lekezelésének felülírása
   *
   * @param clicked újonnan érkezett bemenet.
   */
  @Override
  public void ujInput(Mezo clicked) {
    csinal(clicked, null);
  }
}
