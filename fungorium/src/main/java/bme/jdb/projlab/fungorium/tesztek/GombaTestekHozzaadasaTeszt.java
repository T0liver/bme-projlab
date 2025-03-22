package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaTest;
import bme.jdb.projlab.fungorium.Tekton;

public class GombaTestekHozzaadasaTeszt {

    public static void GombaTestekHozzaadasaTeszt() {

        JDBtesttool teszt = new JDBtesttool("Gombatestek hozzáadása");

        Tekton tekton = new Tekton();
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        GombaTest gombaTest;
        try {
            gombaTest = new GombaTest(5, tekton);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        teszt.jdbNotEqual("Gombatest1 létrehozása", gombaTest, null );
        teszt.jdbEquals("Gombatest1 elhelyezés...Tekton1", tekton.getFoglalt(), true);

        GombaTest gombaTest2 = null;
        boolean exceptionThrown = false;

        try {
            gombaTest2 = new GombaTest(5, tekton);
        } catch (Exception e) {
            exceptionThrown = true;
        }

        teszt.jdbEquals("Gombatest2 létrehozása sikertelen (kivétel dobódott)", exceptionThrown, true);

        if (gombaTest2 != null) {
            teszt.jdbNotEqual("Gombatest2 nem jött létre", gombaTest2, null);
        }
    }


}
