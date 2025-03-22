package bme.jdb.projlab.fungorium.tesztek;
import bme.jdb.projlab.fungorium.*;

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


    /**
     * A teszt leellnőrzi, hogy az adott Rovar valóban elfogyasztja-e a Bénító spórát.
     * Ezen kívül a spóra elfogyasztása után megvizsgálja a hatást [felgyorsul-e az adott Rovar].
     */


    public static void GyorsitoSporaTeszt() {

        JDBtesttool teszt = new JDBtesttool("Gyorsító spórát eszik teszt");

        Tekton tekton = new Tekton();
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        Rovar rovar = new Rovar(tekton);
        GyorsitoSpora gyorsitoSpora = new GyorsitoSpora(3, 5);
        tekton.add(gyorsitoSpora);

        teszt.jdbNotEqual("Rovar létrejött", rovar, null);
        teszt.jdbNotEqual("Spóra létrejött", gyorsitoSpora, null);
        teszt.jdbEquals("Spóra elhelyezés...Tekton1", tekton.getSporak().get(0), gyorsitoSpora);

        // Mentsük el a rovar alap sebességét, mielőtt bármit is változtatnánk



        int alapsebesseg = rovar.getSebesseg();
        int eredmeny = rovar.eszik(gyorsitoSpora); // A rovar eszik a spórából


        // Ellenőrizzük, hogy a rovar elfogyasztotta-e a spórát és csökkentette annak mennyiségét.
        teszt.jdbEquals("Rovar megette a Gyorsító spórát", eredmeny, 15); // 3 * 5 = 15 tápanyag

        // Ellenőrizzük, hogy a spóra darabszáma most 0 (mert elfogyott)
        teszt.jdbEquals("Spóra csökkenés ellenőrzése", gyorsitoSpora.getDarabszam(), 0);

        // Ellenőrizzük, hogy a rovar sebessége valóban lecsökkent
        teszt.jdbEquals("A Rovar begyorsult", rovar.getSebesseg(), (int) Math.ceil(alapsebesseg * 2));

    }


    /**
     * A teszt leellnőrzi, hogy az adott Rovar valóban elfogyasztja-e a Lassító spórát.
     * Ezen kívül a spóra elfogyasztása után megvizsgálja a hatást [lelassul-e az adott Rovar].
     */

    public static void LassitoSporaTeszt() {
        JDBtesttool teszt = new JDBtesttool("Lassító spórát eszik teszt");

        Tekton tekton = new Tekton();
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        Rovar rovar = new Rovar(tekton);
        LassitoSpora lassitoSporaspora = new LassitoSpora(3, 5);
        tekton.add(lassitoSporaspora);

        teszt.jdbNotEqual("Rovar létrejött", rovar, null);
        teszt.jdbNotEqual("Spóra létrejött", lassitoSporaspora, null);
        teszt.jdbEquals("Spóra elhelyezés...Tekton1", tekton.getSporak().get(0), lassitoSporaspora);

        // Mentsük el a rovar alap sebességét, mielőtt bármit is változtatnánk



        int alapsebesseg = rovar.getSebesseg();
        int eredmeny = rovar.eszik(lassitoSporaspora); // A rovar eszik a spórából


        // Ellenőrizzük, hogy a rovar elfogyasztotta-e a spórát és csökkentette annak mennyiségét.
        teszt.jdbEquals("Rovar megette a Lassító spórát", eredmeny, 15); // 3 * 5 = 15 tápanyag

        // Ellenőrizzük, hogy a spóra darabszáma most 0 (mert elfogyott)
        teszt.jdbEquals("Spóra csökkenés ellenőrzése", lassitoSporaspora.getDarabszam(), 0);

        // Ellenőrizzük, hogy a rovar sebessége valóban lecsökkent
        teszt.jdbEquals("A Rovar lelassult", rovar.getSebesseg(), (int) Math.ceil(alapsebesseg * 0.5));
    }


    /**
     * A teszt leellnőrzi, hogy az adott Rovar valóban elfogyasztja-e a Bénító spórát.
     * Ezen kívül a spóra elfogyasztása után megvizsgálja a hatást [mozgásképtelen lesz-e az adott Rovar].
     */


    public static void BenitoSporaTeszt() {

        JDBtesttool teszt = new JDBtesttool("Bénító spórát eszik teszt");

        Tekton tekton = new Tekton();
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        Rovar rovar = new Rovar(tekton);
        BenitoSpora benitoSpora = new BenitoSpora(3, 5);
        tekton.add(benitoSpora);

        teszt.jdbNotEqual("Rovar létrejött", rovar, null);
        teszt.jdbNotEqual("Spóra létrejött", benitoSpora, null);
        teszt.jdbEquals("Spóra elhelyezés...Tekton1", tekton.getSporak().get(0), benitoSpora);

        // Mentsük el a rovar alap sebességét, mielőtt bármit is változtatnánk



        int alapsebesseg = rovar.getSebesseg();
        int eredmeny = rovar.eszik(benitoSpora); // A rovar eszik a spórából


        // Ellenőrizzük, hogy a rovar elfogyasztotta-e a spórát és csökkentette annak mennyiségét.
        teszt.jdbEquals("Rovar megette a Bénító spórát", eredmeny, 15); // 3 * 5 = 15 tápanyag

        // Ellenőrizzük, hogy a spóra darabszáma most 0 (mert elfogyott)
        teszt.jdbEquals("Spóra csökkenés ellenőrzése", benitoSpora.getDarabszam(), 0);

        // Ellenőrizzük, hogy a rovar sebessége valóban lecsökkent
        teszt.jdbEquals("A Rovar lebénult", rovar.getSebesseg(), 0);


    }





    /**
     * A teszt leellnőrzi, hogy az adott Rovar valóban elfogyasztja-e a Csorbító spórát.
     * Ezen kívül a spóra elfogyasztása után megvizsgálja a hatást [képtelen lesz e vágni az adott Rovar].
     */

    public static void CsorbitoSporaTeszt() {

        JDBtesttool teszt = new JDBtesttool("Csorbító spórát eszik teszt");

        Tekton tekton = new Tekton();
        teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);

        Rovar rovar = new Rovar(tekton);
        CsorbitoSpora csorbitoSpora = new CsorbitoSpora(3, 5);
        tekton.add(csorbitoSpora);

        teszt.jdbNotEqual("Rovar létrejött", rovar, null);
        teszt.jdbNotEqual("Spóra létrejött", csorbitoSpora, null);
        teszt.jdbEquals("Spóra elhelyezés...Tekton1", tekton.getSporak().get(0), csorbitoSpora);

        // Mentsük el a rovar alap sebességét, mielőtt bármit is változtatnánk



        int alapsebesseg = rovar.getSebesseg();
        int eredmeny = rovar.eszik(csorbitoSpora); // A rovar eszik a spórából


        // Ellenőrizzük, hogy a rovar elfogyasztotta-e a spórát és csökkentette annak mennyiségét.
        teszt.jdbEquals("Rovar megette a Csorbító spórát", eredmeny, 15); // 3 * 5 = 15 tápanyag

        // Ellenőrizzük, hogy a spóra darabszáma most 0 (mert elfogyott)
        teszt.jdbEquals("Spóra csökkenés ellenőrzése", csorbitoSpora.getDarabszam(), 0);

        // Ellenőrizzük, hogy a rovar sebessége valóban lecsökkent
        teszt.jdbEquals("A Rovar lebénult", rovar.getVaghat(), false);

    }






}
