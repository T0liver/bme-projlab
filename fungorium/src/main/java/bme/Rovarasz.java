package bme;

import java.util.ArrayList;
import java.util.List;

public class Rovarasz extends Jatekos {
    private List<Rovar> rovarok = new ArrayList<Rovar>();

    @Override
    public void lep() {
        List<Boolean> cselekedhet = new ArrayList<Boolean>();
        List<Integer> lepesek = new ArrayList<Integer>();
        for (int i = 0; i < rovarok.size(); ++i) {
            rovarok.get(i).tick();
            cselekedhet.add(true);
            lepesek.add(rovarok.get(i).getSebesseg());
        }
        // TODO: command line
        // lepesnel lepesek.get(index) -= 1,
        // vágás/evés cselekedeteknél ugyanez
        // új rovar berakása a listába
    }

    @Override
    public int getType() {
        return 1;
    }

    /*
     * Publikus getter függvény a Rovarász rovarjainak listájának lekérdezésére.
     * 
     * @return a Rovarász rovarjainak listája
     */
    public List<Rovar> getRovarok() {
        return rovarok;
    }

    /*
     * Publikus setter függvény a Rovarász rovarjainak listájának beállítására.
     * 
     * @param rovarok a Rovarász új rovarjainak listája
     */
    public void addRovar(Rovar rovar) {
        rovarok.add(rovar);
    }
}
