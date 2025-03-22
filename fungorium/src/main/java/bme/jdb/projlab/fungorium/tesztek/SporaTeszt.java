package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaFonal;
import bme.jdb.projlab.fungorium.GombaTest;
import bme.jdb.projlab.fungorium.Tekton;
import bme.jdb.projlab.fungorium.TermeketlenTekton;

public class SporaTeszt {

    public static void SporatSzorTeszt() {
        JDBtesttool test = new JDBtesttool("Spora szorasa szomszedra");

        Tekton tekton1 = new Tekton();
        Tekton tekton2 = new Tekton();

        test.jdbNotEqual("tekton1 letrehozasa", tekton1, null);
        test.jdbNotEqual("tekton2 letrehozasa", tekton2, null);

        tekton1.addSzomszed(tekton2);
        tekton2.addSzomszed(tekton1);

        test.jdbEquals("tekton1 szomszedja beallitva", tekton1.getSzomszed(1).contains(tekton2), true);
        test.jdbEquals("tekton2 szomszedja beallitva", tekton2.getSzomszed(1).contains(tekton1), true);

        GombaTest gombatest;

        try {
            gombatest = new GombaTest(5, tekton2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        test.jdbEquals("gombatest letrehozva a tekton2-on", gombatest.getTartozkodik(), tekton2);

        gombatest.setSporaDarab(5);
        test.jdbEquals("gombatest sporadarab beallitva", gombatest.getSporaDarab(), 5);

        gombatest.sporatSzor(tekton1);

        test.jdbNotEqual("gombatest sporaszorasa --> csokken a gombatest sporaszama", gombatest.getSporaDarab(), 5);
        test.jdbEquals("gombatest spora szorasa --> csokken az elettartam", gombatest.getElettartam(), 4);

        test.jdbNotEqual("gombatest sporaszorasa --> sporak elhelyezva a szomszedos tektonon", tekton2.getSporak(), null);
    }


    public static void FejlettenSporatSzorTeszt() {
        JDBtesttool test = new JDBtesttool("Fejlett gombatest Spora szorasa szomszed szomszedjara");

        Tekton tekton1 = new Tekton();
        Tekton tekton2 = new Tekton();
        Tekton tekton3 = new Tekton();

        test.jdbNotEqual("tekton1 letrehozasa", tekton1, null);
        test.jdbNotEqual("tekton2 letrehozasa", tekton2, null);
        test.jdbNotEqual("tekton3 letrehozasa", tekton3, null);

        tekton1.addSzomszed(tekton2);
        tekton2.addSzomszed(tekton1);
        tekton2.addSzomszed(tekton3);
        tekton3.addSzomszed(tekton2);

        test.jdbEquals("tekton1 szomszedja beallitva (tekton2)", tekton1.getSzomszed(1).contains(tekton2), true);
        test.jdbEquals("tekton2 szomszedja beallitva (tekton1, tekton3)", tekton2.getSzomszed(1).contains(tekton1), true);
        test.jdbEquals("tekton3 szomszedja beallitva (tekton2)", tekton3.getSzomszed(1).contains(tekton2), true);

        GombaTest gombatest;

        try {
            gombatest = new GombaTest(5, 5, true, 5, tekton1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        test.jdbEquals("gombatest letrehozva a tekton1-en", gombatest.getTartozkodik(), tekton1);
        test.jdbEquals("gombatest sporadarab beallitva", gombatest.getSporaDarab(), 5);

        gombatest.sporatSzor(tekton3);

        test.jdbNotEqual("gombatest sporaszorasa --> csokken a gombatest sporaszama", gombatest.getSporaDarab(), 5);
        test.jdbEquals("gombatest spora szorasa --> csokken az elettartam", gombatest.getElettartam(), 4);

        test.jdbNotEqual("gombatest sporaszorasa --> sporak elhelyezva a szomszedos tektonon", tekton3.getSporak(), null);

    }




}
