package bme;

import java.io.Serializable;
import java.util.List;

/**
 * Mentés osztály implementációja
 *
 * <p>Egy játékmenet elmentéséért felelős osztály.
 *
 * @author Márton
 */
public class Save implements Serializable {

  int jelenlegiKor;
  int jelenlegiJatekos;
  List<Jatekos> jatekos;
  Terkep terkep;

  public Save(int jelenlegiKor, int jelenlegiJatekos, List<Jatekos> jatekos, Terkep terkep) {
    this.jelenlegiKor = jelenlegiKor;
    this.jelenlegiJatekos = jelenlegiJatekos;
    this.jatekos = jatekos;
    this.terkep = terkep;
  }

  public int getJelenlegiKor() {
    return jelenlegiKor;
  }

  public void setJelenlegiKor(int jelenlegiKor) {
    this.jelenlegiKor = jelenlegiKor;
  }

  public Terkep getTerkep() {
    return terkep;
  }

  public void setTerkep(Terkep terkep) {
    this.terkep = terkep;
  }

  public List<Jatekos> getJatekos() {
    return jatekos;
  }

  public void setJatekos(List<Jatekos> jatekos) {
    this.jatekos = jatekos;
  }

  public int getJelenlegiJatekos() {
    return jelenlegiJatekos;
  }

  public void setJelenlegiJatekos(int jelenlegiJatekos) {
    this.jelenlegiJatekos = jelenlegiJatekos;
  }
}
