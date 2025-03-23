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

        teszt.jdbNotEqual("tekton1 letrehozasa", tekton, null);
        teszt.jdbNotEqual("tekton2 letrehozasa", tekton2, null);
        teszt.jdbNotEqual("tekton3 letrehozasa", tekton3, null);

        // Szomszédsági kapcsolatok beállítása
        tekton.addSzomszed(tekton2);
        tekton2.addSzomszed(tekton);
        tekton2.addSzomszed(tekton3);
        tekton3.addSzomszed(tekton2);

        // Ellenőrzés: a létrehozott Tekton objektum nem lehet null
        teszt.jdbEquals("tekton1 szomszedja beallitva (tekton2)", tekton.getSzomszed(1).contains(tekton2), true);
        teszt.jdbEquals("tekton2 szomszedja beallitva (tekton1, tekton3)", tekton2.getSzomszed(1).contains(tekton), true);
        teszt.jdbEquals("tekton3 szomszedja beallitva (tekton2)", tekton3.getSzomszed(1).contains(tekton2), true);



        // GombaTest objektum létrehozása és elhelyezése egy Tektonon
        GombaTest gombaTest;
        try {
            gombaTest = new GombaTest(1, 1, true, 5, tekton);
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
