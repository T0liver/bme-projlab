package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaFonal;
import bme.jdb.projlab.fungorium.Rovar;
import bme.jdb.projlab.fungorium.Tekton;

public class RovarTektonValtTeszt {

  /**
   * Teszt a rovar egyik tektonrol masikra lepesere 2 tekton letrehozasa melyek szomszedosak es
   * koztuk vezet gombafonal tekton1-en a rovar ami atlep tekton1-re
   */
  public static void RoarAtlepTeszt() {

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
    test.jdbNotEqual("Gombafonal létrehozása", gombaFonal, null);

    tekton1.getFonalak().add(gombaFonal);
    gombaFonal.athidal(tekton1);
    tekton2.getFonalak().add(gombaFonal);

    gombaFonal.getVezet().add(tekton1);
    gombaFonal.getVezet().add(tekton2);

    test.jdbEquals("gombafonal vezet tekton1-be", gombaFonal, tekton1.getFonalak().get(0));
    test.jdbEquals("gombafonal vezet tekton2-be", gombaFonal, tekton2.getFonalak().get(0));

    Rovar rovar = new Rovar(tekton1);

    test.jdbEquals("Rovar letrejott a tekton1-en", rovar.getTartozkodik(), tekton1);

    rovar.mozog(tekton2);
    test.jdbEquals("Rovar mozog a tekton2-re", rovar.getTartozkodik(), tekton2);
  }

  /**
   * Teszt a rovar nem tud az egyik tektonrol a masikra lepni 2 tekton letrehozasa melyek nem
   * szomszedosak tekton1-en a rovar ami atlepne tekton2-re de nem tud mivel nincs osszekottetes a 2
   * tekton kozt
   */
  public static void RovarNemTudAtlepniTeszt() {
    JDBtesttool test = new JDBtesttool("Rovar nem tud atlepni mert nincs osszekottetes");

    Tekton tekton1 = new Tekton();
    Tekton tekton2 = new Tekton();

    test.jdbNotEqual("tekton1 letrehozasa", tekton1, null);
    test.jdbNotEqual("tekton2 letrehozasa", tekton2, null);

    tekton1.addSzomszed(tekton2);
    tekton2.addSzomszed(tekton1);

    test.jdbEquals("tekton1 szomszedja beallitva", tekton1.getSzomszed(1).contains(tekton2), true);
    test.jdbEquals("tekton2 szomszedja beallitva", tekton2.getSzomszed(1).contains(tekton1), true);

    GombaFonal gombaFonal = new GombaFonal();
    test.jdbNotEqual("Gombafonal létrehozása", gombaFonal, null);

    tekton1.getFonalak().add(gombaFonal);

    gombaFonal.getVezet().add(tekton1);

    test.jdbEquals("gombafonal vezet tekton1-be", gombaFonal, tekton1.getFonalak().get(0));
    // test.jdbNotEqual("gombafonal nem vezet tekton2-be", gombaFonal, tekton2.getFonalak().get(0));

    Rovar rovar = new Rovar(tekton1);

    test.jdbEquals("Rovar letrejott a tekton1-en", rovar.getTartozkodik(), tekton1);

    rovar.mozog(tekton2);

    test.jdbEquals("Rovar nem tud atlepni --> marad a tekton1-en", rovar.getTartozkodik(), tekton1);
  }
}
