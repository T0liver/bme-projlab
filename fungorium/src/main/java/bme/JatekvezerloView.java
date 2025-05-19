package bme;

import java.util.List;
import javax.swing.*;

/**
 * Játékvezérlő nézei osztály implementációja
 *
 * <p>A játékmenethez tartozó fontos információk megjelenítésért felelős.
 *
 * @author Oliver
 */
public class JatekvezerloView {
  /** a körök lebonyolításáért felelős vezérlő */
  private Jatekvezerlo jatekvezerlo;

  /** a játékablak */
  private GameWindow gameWindow;

  /** a tékép nézete */
  private TerkepView terkepView;

  /** látható játékosok */
  boolean[] jatekoslatszik = {true, true, true, true, true, true, true, true, true, true};

  /** játékos menü nézete */
  private JatekosMenuView jatekosMenuView;

  /**
   * Publikus konstruktor a view inicializálására
   *
   * @param jatekvezerlo a körök lebonyolításáért felelős vezérlő
   */
  public JatekvezerloView(Jatekvezerlo jatekvezerlo, GameWindow gw) {
    this.jatekvezerlo = jatekvezerlo;
    gameWindow = gw;
    jatekvezerlo.setJatekvezerloView(this);
    // jatekvezerlo.init();
    // terkepView = new TerkepView(jatekvezerlo.getTerkep());
  }

  /**
   * Publikus getter a játék játékvezérlőjének lekérdezésére.
   *
   * @return
   */
  public Jatekvezerlo getJatekvezerlo() {
    return jatekvezerlo;
  }

  /**
   * Publikus setter játékosmenü nézetére.
   *
   * @param jmv a belááítandó játékosmenü nézet.
   */
  public void setJatekosMenuView(JatekosMenuView jmv) {
    jatekosMenuView = jmv;
  }

  /**
   * Kirajzoló függvény
   *
   * @param gw az ablak, ahova rajzol.
   */
  public void draw(GameWindow gw) {
    if (terkepView == null) terkepView = new TerkepView(jatekvezerlo.getTerkep());
    List<Jatekos> jatekosok = jatekvezerlo.getJatekosok();
    for (int i = 0; i < jatekosok.size(); ++i) {
      if (jatekoslatszik[i]) {
        JatekosView jv = new JatekosView(jatekosok.get(i));
        jv.draw(gw);
      }
    }
    terkepView.setJatekvezerlo(jatekvezerlo);
    terkepView.setJatekosMenuView(jatekosMenuView);
    terkepView.draw(gw);
  }

  /** Következő kör */
  public void kovetkezoKor() {
    jatekvezerlo.korVege();
    if (jatekvezerlo.getJelenlegiKor() == jatekvezerlo.getJatekHossz()) {
      jatekvezerlo.jatekVege();
      return;
    }
    draw(gameWindow);
  }

  /**
   * Publikus getter a soron lévő játékosra
   *
   * @return a soron lévő játékos
   */
  public Jatekos getSoronLevoJatekos() {
    return jatekvezerlo.getSoronLevoJatekos();
  }

  /** Játék vége metódus */
  public void jatekVege() {
    int[] nyertesek = jatekvezerlo.jatekVege();

    JOptionPane.showMessageDialog(
        null, "Nyertesek:\n" + "Gombász " + (nyertesek[0]) + "\n" + "Rovarász " + (nyertesek[1]));
  }
}
