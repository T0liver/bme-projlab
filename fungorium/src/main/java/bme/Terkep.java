package bme;

import java.util.ArrayList;
import java.util.List;

public class Terkep {
    private List<Tekton> tektonok = new ArrayList<>();
    private List<Mezo> mezok = new ArrayList<>();
    private Mezo activeMezo = null;
    public void addTekton(Tekton t) {
        if (!tektonok.contains(t)) tektonok.add(t);
    }
    public void addMezo(Mezo m) {
        if (!mezok.contains(m)) mezok.add(m);
    }
    public List<Mezo> getMezok() { return mezok; }
    public Mezo getActiveMezo() { return activeMezo; }
    public void setActiveMezo(Mezo m) { activeMezo = m; }
}