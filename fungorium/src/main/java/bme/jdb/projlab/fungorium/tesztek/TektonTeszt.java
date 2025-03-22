package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.EgyetlenFonalTekton;
import bme.jdb.projlab.fungorium.GombaFonal;
import bme.jdb.projlab.fungorium.GombaTest;
import bme.jdb.projlab.fungorium.Tekton;

import java.util.List;

public class TektonTeszt {

    /**
     * 5. laboron megadott teszteset
     * Tektonra elhelyezni egy gombatestet és egy fonalat, majd hasítani a tektont
     */
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

        tekton.getFonalak().add(gombaFonal);

        teszt.jdbEquals("Gombafonal vezet...Tekton1", gombaFonal, tekton.getFonalak().getFirst());

        List<Tekton> hasadasUtan = tekton.hasad();
        teszt.jdbEquals("Tekton hasad (Sikertelenül)", hasadasUtan.getFirst(), tekton);
    }

    /**
     * EgyetlenFonálTektonon csak egy fonál lehet
     */
    public static void egyetlenFonalTektonTeszt(){
        JDBtesttool teszt = new JDBtesttool("EgyetlenFonálTekton teszt");

        Tekton tekton8 = new EgyetlenFonalTekton();
        teszt.jdbNotEqual("EgyetlenFonalTekton létrehoz", tekton8, null);

        Tekton tekton1 = new Tekton();
        tekton8.addSzomszed(tekton1);

        GombaFonal gombaFonal1 = new GombaFonal();
        GombaFonal gombaFonal2 = new GombaFonal();
        teszt.jdbNotEqual("Gombafonal létrehoz", gombaFonal2, null);

        tekton8.getFonalak().add(gombaFonal1);
        teszt.jdbEquals("Egyik Gombafonal elhelyez", tekton8.getFonalak().getFirst(), gombaFonal1);

        tekton8.fonalNo(gombaFonal1);
        teszt.jdbNotEqual("Másik Gombafonal elhelyezése sikertelen", tekton8.getFonalak().size(), 2);
    }
}
