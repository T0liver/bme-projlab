package bme;

public class Akcio {
    protected String nev;
    protected Jatekos jatekos;
    protected char betu;
    protected Mezo prevMezo = null;

    public Akcio(Jatekos j) {jatekos = j;}

    public boolean csinal(Mezo m0, Mezo m1){ return false; }

    public String getNev() {
        return nev;
    }

    public char getBetu() {
        return betu;
    }

    public String getHelp() {return "ez egy mukodest magyarazo szoveg";}

    public void ujInput(Mezo clicked) {
        if (prevMezo == null) {
            prevMezo = clicked;
            return;
        }
        csinal(prevMezo, clicked);
        prevMezo = null;
        jatekos.setAktivAkcio(null);
    }
}