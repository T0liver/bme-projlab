package bme;

/**
 * Fonal növesztés akció osztály implementációja.
 *
 * <p>Egy fonal növesztését tartalmazó akció.
 *
 * @author Vid
 */
public class FonalNoveszetesAkcio extends Akcio {

  /** Az akcióhoz tartozó gombász. */
  Gombasz g;

  /**
   * Publikus konstruktor az akcióhoz.
   *
   * @param j az akcióhoz tartozandó játékos
   */
  public FonalNoveszetesAkcio(Gombasz j) {
    super(j);
    g = j;
    betu = 'F';
    nev = "FONAL NÖVESZTÉS";
  }

  /**
   * Csinál metódus felülírása, hogy a gombatest tovább növessze a fonalat.
   *
   * @param m0 elsőre kattinrott mező.
   * @param m0 másodikra kattinrott mező.
   */
  @Override
  public boolean csinal(Mezo m0, Mezo m1) {
    return g.fonalatNoveszt(m0, m1) != 0;
  }

  /**
   * Segítő szöveg felülírása
   *
   * @return Segítő szöveg a gombafonal növesztéshez.
   */
  @Override
  public String getHelp() {
    return "Fonál növesztés:\n"
               + "Kattints a mezőre, ahonnan, majd arra (a szomszédos tektonon lévő) szomszédos"
               + " mezőre, ahova fonal nőjjön!";
  }
}
