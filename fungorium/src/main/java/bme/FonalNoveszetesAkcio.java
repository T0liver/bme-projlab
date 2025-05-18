package bme;

public class FonalNoveszetesAkcio extends Akcio {
    Gombasz g;
    public FonalNoveszetesAkcio(Gombasz j) {
        super(j);
        g = j;
        betu = 'F';
        nev = "FONAL NÖVESZTÉS";
    }

    @Override
    public boolean csinal(Mezo m0, Mezo m1) {
        return g.fonalatNoveszt(m0, m1) != 0;
    }
}
