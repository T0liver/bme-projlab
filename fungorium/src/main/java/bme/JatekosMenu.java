package bme;

import java.util.ArrayList;
import java.util.List;

public class JatekosMenu {
    private Jatekos jatekos;
    private List<Akcio> akciok;

    public JatekosMenu() {
        akciok = new ArrayList<>();
    }

    public void lathatosag(boolean b){}

    public List<Akcio> getAkciok() {
        return akciok;
    }

    public Jatekos getJatekos() {
        return jatekos;
    }

    public void setAkciok(List<Akcio> akciok) {
        this.akciok = akciok;
    }

    public void setJatekos(Jatekos jatekos) {
        this.jatekos = jatekos;
    }
}
