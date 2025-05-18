package bme;

public class MozgasAkcio extends Akcio {
    private Rovarasz r;
    public MozgasAkcio(Rovarasz j) {
        super(j);
        r = j;
        betu = 'M';
        nev = "MOZGÁS";
    }

    @Override
    public boolean csinal(Mezo m0, Mezo m1) {
        return r.mozgat(m0, m1) != 0;
    }
}
