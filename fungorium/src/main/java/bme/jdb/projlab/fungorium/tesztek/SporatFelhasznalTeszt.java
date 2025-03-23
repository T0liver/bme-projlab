package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.Spora;
import bme.jdb.projlab.fungorium.Tekton;

public class SporatFelhasznalTeszt {

    /**
     * Tekton sporatFelhasznal függvényének tesztje kellő spóramennyiség hiányában
     */
    public static void sporatFelhasznalTeszt1(){
        JDBtesttool teszt = new JDBtesttool("sporatFelhasznalTeszt1");

        Tekton tekton1 = new Tekton();
        teszt.jdbNotEqual("Tekton létrehoz", tekton1, null);

        Spora spora1 = new Spora(10, 5);
        tekton1.getSporak().add(spora1);
        teszt.jdbEquals("Spora letrehozva, elhelyezve a tektonon", tekton1.getBestSpora(), spora1);

        teszt.jdbEquals("Sporátfelhasznál -> nincs elég spóra", tekton1.sporatFelhasznal(spora1), false);
    }

    /**
     * Tekton sporatFelhasznal függvényének tesztje kellő spóramennyiség jelenlétében
     */
    public static void sporatFelhasznalTeszt2(){
        JDBtesttool teszt = new JDBtesttool("sporatFelhasznalTeszt2");

        Tekton tekton1 = new Tekton();
        teszt.jdbNotEqual("Tekton létrehoz", tekton1, null);

        Spora spora1 = new Spora(10, 15);
        tekton1.getSporak().add(spora1);
        teszt.jdbEquals("Spora letrehozva, elhelyezve a tektonon", tekton1.getBestSpora(), spora1);

        teszt.jdbEquals("Sporátfelhasznál -> van elég spóra", tekton1.sporatFelhasznal(spora1), true);
        teszt.jdbEquals("Spora felhasználva", spora1.getDarabszam(), 0);

    }
}
