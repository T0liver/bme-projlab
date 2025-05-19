package bme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mezo implements Serializable {
    private int x, y;
    public Tekton tartozik;
    public List<GombaFonal> fonalak = new ArrayList<>();
    Terkep terkep;

    public Mezo(int _x, int _y) {x = _x; y = _y;}

    int milyenSzomszed(Mezo m) {
      List<Integer> mpos = m.getPos();
      if (Math.abs(mpos.get(0) - x) > 1 || Math.abs(mpos.get(1) - y) > 1 || (mpos.get(1) == y && mpos.get(0) == x))
          return 0;
      if (tartozik != m.getTekton()) {
        for (int i = 0; i < fonalak.size(); ++i)
            if (fonalak.get(i).getVezet(this, m))
                return 2;
        return 1;
      }
      for (int i = 0; i < fonalak.size(); ++i)
        if (fonalak.get(i).getVezet(this, m))
          return 4;
      return 3;
  }
  public List<Integer> getPos() {
      List<Integer> ret = new ArrayList<>();
      ret.add(x); ret.add(y);
      return ret;
  }
  public Tekton getTekton() {return tartozik;}
  /** Publikus getter a tektonon tartozkodo gombafonalak lekerdezesere */
  public List<GombaFonal> getFonalak() {
      return fonalak;
  }

    public void setTekton(Tekton t) {
      tartozik = t;
    }
    /**
   * A tektonra fonbafonalat helyező függvény
   *
   * @param fonal a lehelyezendő függvény
   */
    public void addFonal(GombaFonal gf) { // legyen inkább csak setter? ez nincs az uml diagramon - jó ez [Vid]
        if (!fonalak.contains(gf)) { // ellenőrzés, ne legyen loop (redundancia)
          fonalak.add(gf); // csináljuk visszairányba
        }
      }

      /**
       * Gombafonál tektonra való átérését kezelő függvény
       *
       * @param melyik melyik gombafonál próbál áthidalni a tektonra
       */
      public void fonalNo(GombaFonal melyik, Mezo honnan) {
        if (fonalak.contains(melyik))
          return;
        if (milyenSzomszed(honnan) > 0)
          if (melyik.getVezet(honnan, honnan)) { // Ellenőrzés, hogy szomszédról hidal-e át
            fonalak.add(melyik);
            // melyik.athidal(this); //ez kérdőjeles
          }
        }

        /**
         * Gombafonál tektonra való átérését kezelő függvény
         *
         * @param melyik melyik gombafonál próbál áthidalni a tektonra
         */
        public void fonalNovekszik(GombaFonal melyik, Mezo honnan) {
          //if (fonalak.contains(melyik)) {
            //return;
          //}
          if (milyenSzomszed(honnan) > 2) {
            if (melyik.getVezet(honnan, honnan)) { // Ellenőrzés, hogy szomszédról hidal-e át
              if (!fonalak.contains(melyik))
                fonalak.add(melyik);
              melyik.athidal(honnan, this);
              // melyik.athidal(this); //ez kérdőjeles
            }
          }
        }

        void setTerkep(Terkep t) {
          terkep = t;
        }

          List<Mezo> getSzomszedok() {
            List<Mezo> ret = new ArrayList<>();
            for (int i = 0; i < terkep.getMezok().size(); ++i) {
              if (Math.abs(terkep.getMezok().get(i).getPos().get(0) - x) < 2 && Math.abs(terkep.getMezok().get(i).getPos().get(1) - y) < 2) {
                System.out.println("ASD");
                ret.add(terkep.getMezok().get(i));
              }
            }
            System.out.println(ret.size());
            return ret;
          }
  
  public List<Mezo> getOrtoSzomszedok() {
    List<Mezo> result = new ArrayList<>();
    int[][] directions = {
      {0, -1}, // up
      {0, 1},  // down
      {-1, 0}, // left
      {1, 0}   // right
    };
    for (int[] dir : directions) {
      int nx = x + dir[0];
      int ny = y + dir[1];
      if (nx >= 0 && nx < 22 && ny >= 0 && ny < 22) {
        result.add(terkep.getMezok().get(ny * 22 + nx));
      }
    }
    return result;
  }
}