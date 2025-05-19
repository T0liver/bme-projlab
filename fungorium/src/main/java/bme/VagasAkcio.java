package bme;

/**
 * Vágás akció osztály implementációja.
 *
 * <p>Egy fonal elvágását tartalmazó akció.
 *
 * @author Vid
 */
public class VagasAkcio extends Akcio {

  /** Az akcióhoz tartozó rovarász. */
  private Rovarasz r;

  /**
   * Publikus konstruktor az akcióhoz.
   *
   * @param j az akcióhoz tartozandó játékos
   */
  public VagasAkcio(Rovarasz j) {
    super(j);
    r = j;
    betu = 'V';
    nev = "VÁGÁS";
  }

  /**
   * Csinál metódus felülírása, hogy a rovar elvágja a gombafonalat.
   *
   * @param m0 elsőre kattinrott mező.
   * @param m0 másodikra kattinrott mező.
   */
  @Override
  public boolean csinal(Mezo m0, Mezo m1) {
    return r.elvagat(m0, m1);
  }

  /**
   * Segítő szöveg felülírása
   *
   * @return Segítő szöveg a fonalvágáshoz.
   */
  @Override
  public String getHelp() {
    return "Fonal vágás:\n"
               + "Kattints a rovarra, ami fonalat vágjon, majd arra a hozzá szomszédos (és másik"
               + " tektonon lévő) mezőre, ami felé elvágódjon egy gombafonál!";
  }
}
