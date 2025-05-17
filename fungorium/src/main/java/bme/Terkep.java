package bme;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Terkep {
    private Random rand = new Random();
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
    public void init() {
        tektonok.clear();
        mezok.clear();
        activeMezo = null;
        boolean[][] assigned = new boolean[22][22];

        for (int i = 0; i < 22; ++i) {
            for (int e = 0; e < 22; ++e) {
                mezok.add(new Mezo(e, i));
                mezok.get(mezok.size() - 1).setTerkep(this);
            }
        }

        while (mezok.stream().anyMatch(m -> !assigned[m.getPos().get(1)][m.getPos().get(0)])) {
            // Pick a random unassigned Mezo
            Mezo start = null;
            while (start == null) {
                int x = rand.nextInt(22);
                int y = rand.nextInt(22);
                if (!assigned[y][x]) {
                    start = mezok.get(y * 22 + x);
                }
            }

            int targetSize = rand.nextInt(3) + 3; // 3–5 width
            int maxArea = targetSize * (rand.nextInt(3) + 3); // 3–5 height
            
            Tekton tekton = randomTekton();
            Queue<Mezo> queue = new LinkedList<>();
            Set<Mezo> visited = new HashSet<>();
            
            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty() && visited.size() < maxArea) {
                Mezo current = queue.poll();
                tekton.addMezo(current);
                current.setTekton(tekton);
                assigned[current.getPos().get(1)][current.getPos().get(0)] = true;
            
                for (Mezo neighbor : current.getSzomszedok()) {
                    int nx = neighbor.getPos().get(0);
                    int ny = neighbor.getPos().get(1);
                    if (!assigned[ny][nx] && !visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            tektonok.add(tekton);
        }

        // Safety pass: assign any leftover Mezo that somehow wasn't claimed
        for (Mezo m : mezok) {
            if (m.getTekton() == null) {
                Tekton fallback = randomTekton();
                fallback.addMezo(m);
                m.setTekton(fallback);
                tektonok.add(fallback);
            }
        }

        // Collect neighbours for all tektons
        for (int i = 0; i < tektonok.size(); ++i) {
            tektonok.get(i).collectSzomszedok();
        }
    }

    private Tekton randomTekton() {
        int num = rand.nextInt(25);
        Tekton ret;
        switch (num) {
            case 0: ret = new EgyetlenFonalTekton(); break;
            case 1: ret = new EletbenTartoTekton(); break;
            case 2: ret = new FelszivoTekton(); break;
            case 3: ret = new TermeketlenTekton(); break;
            default: ret = new Tekton(); break;
        }
        ret.setTerkep(this);
        return ret;
    }
}