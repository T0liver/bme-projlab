package bme;

public class EvesAkcio extends Akcio {
    
    private Rovarasz r;
    public EvesAkcio(Rovarasz j) {
        super(j);
        r = j;
        betu = 'E';
        nev = "EVÉS";
    }

    @Override
    public boolean csinal(Mezo m0, Mezo m1) {
        int pontok = r.megetet(m0);
        r.addPontok(pontok);
        return pontok > 0;
    }

    @Override
    public String getHelp() {
        return "Spóraevés:\nKattints a rovarra, ami spórát egyen!";
    }

    @Override
    public void ujInput(Mezo clicked) {
        csinal(clicked, null);
    } 
}
