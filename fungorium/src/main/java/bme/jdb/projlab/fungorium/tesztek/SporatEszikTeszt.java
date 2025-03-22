package bme.jdb.projlab.fungorium.tesztek;
import bme.jdb.projlab.fungorium.Rovar;
import bme.jdb.projlab.fungorium.Spora;
import bme.jdb.projlab.fungorium.Tekton;

public class SporatEszikTeszt {

    /**
     * A teszt leellnőrzi, hogy az adott Rovar valóban elfogyasztja-e a Normál spórát
     */
    public static void NormalSporaTeszt() {
        JDBtesttool teszt = new JDBtesttool("Normál spórát eszik teszt");

        Tekton tekton = new Tekton();
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        Rovar rovar = new Rovar(tekton);



        Spora spora = new Spora(3,5);
        tekton.add(spora);

        teszt.jdbNotEqual("Rovar létrejött", rovar, null );

        teszt.jdbNotEqual("Spóra létrejött", spora, null);
        teszt.jdbEquals("Spóra elhelyezés...Tekton1", tekton.getSporak().getFirst(), spora);



        int eredmeny = rovar.eszik(spora); // A rovar eszik a spórából

        // Ellenőrizzük, hogy a rovar elfogyasztotta-e a spórát és csökkentette annak mennyiségét.
        teszt.jdbEquals("Rovar megette a Normál spórát", eredmeny, 15); // 3 * 5 = 15 tápanyag

        // Ellenőrizzük, hogy a spóra darabszáma most 0 (mert elfogyott)
        teszt.jdbEquals("Spóra csökkenés ellenőrzése", spora.getDarabszam(), 0);



    }
}
