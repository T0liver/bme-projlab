package bme;

/**
 * Mozgás akció osztály implementációja.
 *
 * <p>Egy rovar mozgatását tartalmazó akció.
 *
 * @author Vid
 */
public class MozgasAkcio extends Akcio {

  /** Az akcióhoz tartozó rovarász */
  private Rovarasz r;

  /**
   * Publikus konstruktor az akcióhoz.
   *
   * @param j az akcióhoz tartozandó játékos
   */
  public MozgasAkcio(Rovarasz j) {
    super(j);
    r = j;
    betu = 'M';
    nev = "MOZGÁS";
  }

  /**
   * Csinál metódus felülírása, hogy a rovar mozogjon.
   *
   * @param m0 elsőre kattinrott mező.
   * @param m0 másodikra kattinrott mező.
   */
  @Override
  public boolean csinal(Mezo m0, Mezo m1) {
    return r.mozgat(m0, m1) != 0;
  }

  /**
   * Segítő szöveg felülírása
   *
   * @return Segítő szöveg a rovar mozgatásához.
   */
  @Override
  public String getHelp() {
    return "Mozgás:\n"
               + "Kattints a rovarra, ami mozogjon, majd a hozzá szomszédos mezőre, ahova lépjen!";
  }
}
