package bme;

public class TermeketlenTekton extends Tekton {

  /**
   * Függvény, ami felhasználja a gombatest készítéséhez megfelelő mennyiségű spórát
   *
   * @param mit melyik spórát használja fel
   * @return hogy a spóra felhasználása sikeres volt-e (volt e elég), mivel a tekton terméketlen, ez
   *     mindig hamis
   */
  @Override
  public boolean sporatFelhasznal(Spora mit) {
    super.sporatFelhasznal(mit);
    return false;
  }

  /**
   * A class adatait kiiro fuggveny.
   */
  @Override
  public void printData() {
    System.out.println("Termeketlen Tekton\nFoglalt: " + foglalt);
    System.out.println("GombaFonalak:");
    for (int i = 0; i < fonalak.size(); ++i) {
      System.out.println("ID: " + i);
      fonalak.get(i).printData();
    }
    System.out.println("Sporak:");
    for (int i = 0; i < sporak.size(); ++i) {
      System.out.println("ID: " + i);
      sporak.get(i).printData();
    }
    System.out.println("Szomszed IDk:");
    for (int i = 0; i < szomszedok.size(); ++i) {
      System.out.println(Jatekvezerlo.getIDof(szomszedok.get(i)));
    }
  }
}
