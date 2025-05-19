package bme;

/**
 * Spóraszórás akció osztály implementációja.
 *
 * <p>Spóra szórását tartalmazó akció.
 *
 * @author Vid
 */
public class SporatSzorAkcio extends Akcio {

  /** Az akcióhoz tartozó gombász. */
  Gombasz g;

  /**
   * Publikus konstruktor az akcióhoz.
   *
   * @param j az akcióhoz tartozandó játékos
   */
  public SporatSzorAkcio(Gombasz j) {
    super(j);
    g = j;
    betu = 'S';
    nev = "SPÓRASZÓRÁS";
  }

  /**
   * Csinál metódus felülírása, hogy a gombatest spórát szórjon.
   *
   * @param m0 elsőre kattinrott mező.
   * @param m0 másodikra kattinrott mező.
   */
  @Override
  public boolean csinal(Mezo m0, Mezo m1) {
    GombaTest gt = null;
    for (int i = 0; i < g.getGombaTestek().size(); ++i) {
      if (g.getGombaTestek().get(i).getTartozkodik() == m0.getTekton()) {
        gt = g.getGombaTestek().get(i);
        break;
      }
    }
    if (gt == null) return false;
    return g.sporatSzorat(m1.getTekton(), gt);
  }

  /**
   * Segítő szöveg felülírása
   *
   * @return Segítő szöveg a spóra szórásához.
   */
  @Override
  public String getHelp() {
    return "Spóraszórás:\n"
               + "Kattints a tektonra, amelyen van a gombatest, ami spórát szórjon, majd arra a"
               + " tektonra, ahova!";
  }
}
