package bme;

public class TestNovesztesAkcio extends Akcio {
    Gombasz g;
    public TestNovesztesAkcio(Gombasz j) {
        super(j);
        g = j;
        betu = 'T';
        nev = "TEST NÖVESZTÉS";
    }

    @Override
    public boolean csinal(Mezo m0, Mezo m1) {
        return g.testetNoveszt(m0.getTekton());
    }
}