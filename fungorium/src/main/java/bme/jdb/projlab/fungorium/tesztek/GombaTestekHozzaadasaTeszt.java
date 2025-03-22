package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaTest;
import bme.jdb.projlab.fungorium.Tekton;

public class GombaTestekHozzaadasaTeszt {

    /**
     * A teszt célja, hogy ellenőrizze, hogy a GombaTest objektumok megfelelően
     * létrejönnek és helyezkednek el egy Tekton objektumon, valamint, hogy
     * a már foglalt Tekton objektumra nem lehet új GombaTestet hozzáadni.
     */
    public static void GombaTestekHozzaadasaTeszt() {

        // Teszt eszköz inicializálása
        JDBtesttool teszt = new JDBtesttool("Gombatestek hozzáadása");

        // Tekton objektum létrehozása
        Tekton tekton = new Tekton();
        // Ellenőrzés: a Tekton objektum nem lehet null
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        // GombaTest objektum létrehozása, amely egy Tekton objektumon helyezkedik el
        GombaTest gombaTest;
        try {
            gombaTest = new GombaTest(5, tekton);  // GombaTest létrehozása 5-ös szinttel és a Tekton objektumon
        } catch (Exception e) {
            throw new RuntimeException(e);  // Hiba esetén futásidejű kivétel dobása
        }

        // Ellenőrzés: a GombaTest objektum sikeresen létrejött
        teszt.jdbNotEqual("Gombatest1 létrehozása", gombaTest, null);
        // Ellenőrzés: a Tekton objektum most már foglalt
        teszt.jdbEquals("Gombatest1 elhelyezés...Tekton1", tekton.getFoglalt(), true);

        // Új GombaTest objektum próbálkozása ugyanarra a Tekton objektumra
        GombaTest gombaTest2 = null;
        boolean exceptionThrown = false;

        try {
            gombaTest2 = new GombaTest(5, tekton);  // Kísérlet új GombaTest létrehozására ugyanazon Tekton objektumon
        } catch (Exception e) {
            exceptionThrown = true;  // Ha kivétel keletkezik, akkor az exceptionThrown változó igaz lesz
        }

        // Ellenőrzés: kivétel történt, tehát új GombaTest nem jöhetett létre
        teszt.jdbEquals("Gombatest2 létrehozása sikertelen (kivétel dobódott)", exceptionThrown, true);

        // Ha a gombaTest2 létrejött volna, akkor ellenőrzés, hogy nem lehetett volna sikeres
        if (gombaTest2 != null) {
            teszt.jdbNotEqual("Gombatest2 nem jött létre", gombaTest2, null);
        }
    }
}
