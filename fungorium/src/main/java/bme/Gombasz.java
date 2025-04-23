package bme;

import java.util.ArrayList;
import java.util.List;

public class Gombasz extends Jatekos {

    private List<GombaTest> gombaTestek = new ArrayList<GombaTest>();
    private List<GombaFonal> gombaFonalak = new ArrayList<GombaFonal>();
    private List<Spora> sporak = new ArrayList<Spora>();

    @Override
    public void lep() {
        List<Boolean> testCselekedett = new ArrayList<Boolean>();
        int fonalCselekedetek = gombaTestek.size();
        List<Boolean> sporaHasznalt = new ArrayList<Boolean>();
        for (int i = 0; i < sporak.size(); ++i)
            sporaHasznalt.add(false);
        for (int i = 0; i < gombaTestek.size(); ++i)
            testCselekedett.add(false);
        // TODO, mint a rovarásznál, új gombatest felvétele, akciópontok és bemenet
        // kezelése
    }

    @Override
    public int getType() {
        return 0;
    }
}
