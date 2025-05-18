package bme;

public class VagasAkcio extends Akcio {

    private Rovarasz r;
    public VagasAkcio(Rovarasz j) {
        super(j);
        r = j;
        betu = 'V';
        nev = "VÁGÁS";
    }

    @Override
    public boolean csinal(Mezo m0, Mezo m1) {
        return r.elvagat(m0, m1);
    }

    @Override
    public String getHelp() {
        return "Fonal vágás:\nKattints a rovarra, ami fonalat vágjon, majd arra a hozzá szomszédos (és másik tektonon lévő) mezőre, ami felé elvágódjon egy gombafonál!";
    }
}
