package bme;

/**
 * Tekton nézeti osztály implementációja.
 *
 * <p>Tekton nézet
 *
 * @author JDB
 */
public class TektonView extends EntitasView {

  public TektonView(Tekton entitas, Mezo mezo, Jatekos jatekos) {
    super(entitas, mezo, jatekos);
    kinezet = entitas.getImage();
  }
}
