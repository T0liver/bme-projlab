package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaFonal;
import bme.jdb.projlab.fungorium.GombaTest;
import bme.jdb.projlab.fungorium.Tekton;

public class TektonTeszt {

    public static void megadottTeszt(){
        JDBtesttool teszt = new JDBtesttool("Megadott teszt");

        Tekton tekton = new Tekton();
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        GombaTest gombaTest;
        try {
            gombaTest = new GombaTest(5, tekton);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        teszt.jdbNotEqual("Gombatest létrehozása", gombaTest, null );
        teszt.jdbEquals("Gombatest elhelyezés...Tekton1", tekton.getFoglalt(), true);

        GombaFonal gombaFonal = new GombaFonal();

        teszt.jdbNotEqual("Gombafonal létrehozása", gombaFonal, null );

        //fonálnő kell
    }
}
