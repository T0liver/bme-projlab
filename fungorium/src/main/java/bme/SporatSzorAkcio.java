package bme;



public class SporatSzorAkcio extends Akcio {

    Gombasz g;
    public SporatSzorAkcio(Gombasz j) {
        super(j);
        g = j;
        betu = 'S';
        nev = "SPÓRASZÓRÁS";
    }

    @Override
    public boolean csinal(Mezo m0, Mezo m1) {
        GombaTest gt = null;
        for (int i = 0; i < g.getGombaTestek().size(); ++i) {
            if (g.getGombaTestek().get(i).getTartozkodik() == m0.getTekton()) {
                gt = g.getGombaTestek().get(i);
                break;
            }
        }
        return g.sporatSzorat(m1.getTekton(), gt);
    }

    @Override
    public String getHelp() {
        return "Spóraszórás:\nKattints a tektonra, amelyen van a gombatest, ami spórát szórjon, majd arra a tektonra, ahova!";
    }
}
