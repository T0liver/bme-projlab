package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.Tekton;

import java.util.List;

public class TektonHasadasTeszt1 {

    /**
     * A teszt ellenőrzi, hogy a Tekton megfelelően kettéhasad-e
     * normál körülmények között, amikor nincs rajta akadályozó tényező.
     */
    public static void TektonHasadasTeszt1() {

        // Teszt inicializálása
        JDBtesttool teszt = new JDBtesttool("TektonHasadasTeszt1");

        // Tekton objektum létrehozása
        Tekton tekton = new Tekton();

        // Ellenőrzés: a létrehozott Tekton nem lehet null
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        // Hasadás tesztelése
        List<Tekton> tektonok = tekton.hasad();

        // Ellenőrzés: a Tekton ketté kell hogy hasadjon, azaz 2 elemből kell állnia a listának
        teszt.jdbEquals("Tekton ketté hasadt", tektonok.size(), 2);

        // Ha a hasadás után az eredeti objektumnak meg kell szűnnie
        // teszt.jdbEquals("Az eredeti tekton megszűnt", tekton, null);



    }
}
