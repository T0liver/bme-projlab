package bme;

public class Akcio {
    protected String nev;
    protected Jatekos jatekos;
    protected char betu;

    public Akcio(Jatekos j) {jatekos = j;}

    public boolean csinal(Mezo m0, Mezo m1){ return false; }

    public String getNev() {
        return nev;
    }

    public char getBetu() {
        return betu;
    }

    public String getHelp() {return "ez egy mukodest magyarazo szoveg";}
}