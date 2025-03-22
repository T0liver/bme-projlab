package bme.jdb.projlab.fungorium;

public class TermeketlenTekton extends Tekton {

  /**
   * Függvény, ami felhasználja a gombatest készítéséhez megfelelő mennyiségű
   * spórát
   *
   * @param mit melyik spórát használja fel
   * @return hogy a spóra felhasználása sikeres volt-e (volt e elég), mivel a
   *         tekton terméketlen, ez
   *         mindig hamis
   */
  @Override
  public boolean sporatFelhasznal(Spora mit) {
    super.sporatFelhasznal(mit);
    return false;
  }
}
