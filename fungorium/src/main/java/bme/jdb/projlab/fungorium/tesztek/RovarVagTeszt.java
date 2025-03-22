package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaFonal;
import bme.jdb.projlab.fungorium.Rovar;
import bme.jdb.projlab.fungorium.Tekton;

public class RovarVagTeszt {
    public static void RovarVagTeszt() {
        JDBtesttool test = new JDBtesttool("Rovar atlep egyik tektonrol a masikra");

        Tekton tekton1 = new Tekton();
        Tekton tekton2 = new Tekton();

        test.jdbNotEqual("tekton1 letrehozasa", tekton1, null);
        test.jdbNotEqual("tekton2 letrehozasa", tekton2, null);

        tekton1.addSzomszed(tekton2);
        tekton2.addSzomszed(tekton1);

        test.jdbEquals("tekton1 szomszedja beallitva", tekton1.getSzomszed(1).contains(tekton2), true);
        test.jdbEquals("tekton2 szomszedja beallitva", tekton2.getSzomszed(1).contains(tekton1), true);

        GombaFonal gombaFonal = new GombaFonal();
        test.jdbNotEqual("Gombafonal létrehozása", gombaFonal, null );

        tekton1.getFonalak().add(gombaFonal);
        gombaFonal.athidal(tekton1);
        tekton2.getFonalak().add(gombaFonal);

        gombaFonal.getVezet().add(tekton1);
        gombaFonal.getVezet().add(tekton2);



        test.jdbEquals("gombafonal vezet tekton1-be", gombaFonal, tekton1.getFonalak().getFirst());
        test.jdbEquals("gombafonal vezet tekton2-be", gombaFonal, tekton2.getFonalak().getFirst());

        Rovar rovar = new Rovar(tekton2);

        test.jdbEquals("Rovar letrejott a tekton2-on", rovar.getTartozkodik(), tekton2);

        rovar.vag(gombaFonal);

        test.jdbEquals("rovar elvagja a gombafonalat --> megszunik a kapcsolat tekton2-vel", gombaFonal.getVezet().contains(tekton2), false);


    }

}
