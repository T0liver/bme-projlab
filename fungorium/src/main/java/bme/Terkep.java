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
            
            Tekton tekton = new Tekton(this);
            Queue<Mezo> queue = new LinkedList<>();
            Set<Mezo> visited = new HashSet<>();
            
            queue.add(start);
            visited.add(start);

            while (!queue.isEmpty() && visited.size() < maxArea) {
                Mezo current = queue.poll();
                tekton.addMezo(current);
                assigned[current.getPos().get(1)][current.getPos().get(0)] = true;

                for (Mezo neighbor : current.getSzomszedok()) {
                    if (!assigned[current.getPos().get(1)][current.getPos().get(0)] && !visited.contains(neighbor)) {
                        queue.add(neighbor);
                        visited.add(neighbor);
                    }
                }
            }

            tektonok.add(tekton);
        }

        // Collect neighbours for all tektons
        for (Tekton t : tektonok) {
            t.collectSzomszedok();
        }
        //we have the 22by22 map, assign tektons at random
    }
}