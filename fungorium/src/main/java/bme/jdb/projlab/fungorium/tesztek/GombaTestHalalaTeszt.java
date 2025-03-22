package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaTest;
import bme.jdb.projlab.fungorium.Tekton;

public class GombaTestHalalaTeszt {

    /**
     * A teszt ellenőrzi, hogy a GombaTest megfelelően működik-e,
     * amikor egy Tektonon helyezkedik el, spórát szór, majd elpusztul.
     */
    public static void GombaTestHalalaTeszt() {

        // Teszt inicializálása
        JDBtesttool teszt = new JDBtesttool("Gombatest halála teszt");

        // Tekton objektumok létrehozása
        Tekton tekton = new Tekton();
        Tekton tekton2 = new Tekton();
        Tekton tekton3 = new Tekton();

        // Szomszédsági kapcsolatok beállítása
        tekton.addSzomszed(tekton2);
        tekton2.addSzomszed(tekton);
        tekton2.addSzomszed(tekton3);
        tekton3.addSzomszed(tekton);

        // Ellenőrzés: a létrehozott Tekton objektum nem lehet null
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        // GombaTest objektum létrehozása és elhelyezése egy Tektonon
        GombaTest gombaTest;
        try {
            gombaTest = new GombaTest(1, tekton);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Ellenőrzés: a létrehozott GombaTest objektum nem lehet null
        teszt.jdbNotEqual("Gombatest létrehozása", gombaTest, null);

        // Ellenőrzés: a GombaTest elhelyezése sikeres volt a Tektonon
        teszt.jdbEquals("Gombatest elhelyezés...Tekton", tekton.getFoglalt(), true);

        // A GombaTest spóraszórásának tesztelése egy másik Tektonra
        teszt.jdbEquals("A gombatest spórát szórt", gombaTest.sporatSzor(tekton3), true);

        // Ellenőrzés: a GombaTestnek el kell pusztulnia a folyamat végén
        teszt.jdbNotEqual("Gombatest elpusztult", gombaTest, null);

    }
}
