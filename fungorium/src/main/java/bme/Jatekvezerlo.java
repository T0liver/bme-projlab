package bme;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.InvalidAttributeValueException;

public class Jatekvezerlo {
    static int jelenlegiKor = 0;
    static List<Tekton> tektonok = new ArrayList<Tekton>();
    static List<Jatekos> jatekosok = new ArrayList<Jatekos>();
    static int jelenlegiJatekos = 0;
    static int jatekHossz = 50;

    public static void tektontHasit() {
        // TODO random tekton hasad, vagy jelenlegikör % tektonok.size() indexű hasad
    }

    public static void tick() {
        for (int i = 0; i < tektonok.size(); ++i)
            tektonok.get(i).tick();
    }

    public static void korVege() {
        for (int i = 0; i < jatekosok.size(); ++i) {
            jatekosok.get(i).lep();
        }
        tick();
        tektontHasit(); // TODO, ezt random vagy 5 körönként vagy valahogy úgy meghívni
    }

    public static void jatekVege() {
        int gombaszIndex = -1;
        int rovaraszIndex = -1;
        for (int i = 0; i < jatekosok.size(); ++i) {
            if (jatekosok.get(i).getType() == 0
                    && (gombaszIndex == -1 || jatekosok.get(i).getPontok() > jatekosok.get(gombaszIndex).getPontok())) {
                gombaszIndex = i;
            }
            if (jatekosok.get(i).getType() == 1
                    && (rovaraszIndex == -1
                            || jatekosok.get(i).getPontok() > jatekosok.get(rovaraszIndex).getPontok())) {
                rovaraszIndex = i;
            }
        }
        // TODO: log id of winners
    }

    public static void jatekKezdes(int hossz) throws InvalidAttributeValueException {
        int gombaszok = 0;
        int rovaraszok = 0;
        jatekHossz = hossz;
        for (int i = 0; i < jatekosok.size(); ++i) {
            gombaszok += 1 - jatekosok.get(i).getType();
            rovaraszok += jatekosok.get(i).getType();
        }
        if (gombaszok < 2 || rovaraszok < 2)
            throw new InvalidAttributeValueException("nincs elég ilyen vagy olyan játékos");
        init();
        for (jelenlegiKor = 0; jelenlegiKor < hossz; ++jelenlegiKor)
            korVege();
    }

    public static void addJatekos(Jatekos j) {
        jatekosok.add(j);
    }

    public static void init() {
        jelenlegiJatekos = 0;
        // TODO random mennyiségű tekton, szomszédok, rovarok, gombatestek (1-1/jatekos)
    }
}
