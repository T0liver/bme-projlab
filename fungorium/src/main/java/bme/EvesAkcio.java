package bme;

/**
 * Evés akció osztály implementációja.
 *
 * <p>Egy spóra megevését tartalmazó akció.
 *
 * @author Vid
 */
public class EvesAkcio extends Akcio {

  /** Az akcióhoz tartozó rovarász */
  private Rovarasz r;

  /**
   * Publikus konstruktor az akcióhoz.
   *
   * @param j az akcióhoz tartozandó játékos
   */
  public EvesAkcio(Rovarasz j) {
    super(j);
    r = j;
    betu = 'E';
    nev = "EVÉS";
  }

  /**
   * Csinál metódus felülírása, hogy a rovar megegye a spórát.
   *
   * @param m0 elsőre kattinrott mező.
   * @param m0 másodikra kattinrott mező.
   */
  @Override
  public boolean csinal(Mezo m0, Mezo m1) {
    int pontok = r.megetet(m0);
    r.addPontok(pontok);
    return pontok > 0;
  }

  /**
   * Segítő szöveg felülírása
   *
   * @return Segítő szöveg a spóraevéshez.
   */
  @Override
  public String getHelp() {
    return "Spóraevés:\nKattints a rovarra, ami spórát egyen!";
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
