package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.EgyetlenFonalTekton;
import bme.jdb.projlab.fungorium.GombaFonal;
import bme.jdb.projlab.fungorium.Tekton;

public class FonalAthidalTeszt {

  /**
   * Teszt amely a fonalak athidalasat teszteli 2 tekton kozott tekton1 szomszedos tekton2-vel
   * tekton1-re mar vezet a gombafonal, onnan athidal metodus tekton2-re a fonal mar a tekton2-re is
   * vezetni fog
   */
  public static void FonalAthidal1() {

    JDBtesttool test = new JDBtesttool("Gombafonal athidal szomszedos tektonok kozott");

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
    gombaFonal.athidal(tekton2);

    test.jdbEquals(
        "gombafonal athidal tekton1-rol tekton2-re --> oda is vezet",
        gombaFonal.getVezet().contains(tekton2),
        true);
  }

  /**
   * Teszt amely a fonalak athidalasat teszteli 2 tekton kozott tekton1 nem szomszedos tekton2-vel
   * tekton1-re mar vezet a gombafonal, onnan athidal metodus tekton2-re a fonal nem tud athidalni
   * mert a 2 tekton nem szomszedos --> nem fog vezetni tekton2-re
   */
  public static void FonalAthidal2() {
    JDBtesttool test = new JDBtesttool("Gombafonal nem hidal at nem szomszedos tektonok kozott");

    Tekton tekton1 = new Tekton();
    Tekton tekton2 = new Tekton();

    test.jdbNotEqual("tekton1 letrehozasa", tekton1, null);
    test.jdbNotEqual("tekton2 letrehozasa", tekton2, null);

    test.jdbEquals(
        "tekton1 nem szomszedja tekton2-nek", tekton1.getSzomszed(1).contains(tekton2), false);
    test.jdbEquals(
        "tekton2 nem szomszedja tekton2-nek", tekton2.getSzomszed(1).contains(tekton1), false);

    GombaFonal gombaFonal = new GombaFonal();
    test.jdbNotEqual("Gombafonal létrehozása", gombaFonal, null);

    tekton1.getFonalak().add(gombaFonal);
    gombaFonal.getVezet().add(tekton1);
    gombaFonal.athidal(tekton2);

    test.jdbEquals(
        "gombafonal athidal tekton1-rol tekton2-re --> oda is vezet",
        gombaFonal.getVezet().contains(tekton2),
        false);
  }

  /**
   * Teszt amely a fonalak athidalasat teszteli 2 tekton kozott tekton1 szomszedos tekton2-vel
   * tekton 2 EgyetlenFOnalTEkton tekton2-n van mar gombafonal2 tekton1-re mar vezet a gombafonal1,
   * onnan athidal metodus tekton2-re a fonal nem fog tudni athidalni mert tekton2-n mar van fonal
   */
  public static void FonalAthidal3() {

    JDBtesttool test =
        new JDBtesttool(
            "Gombafonal nem hidal at  szomszedos tektonra, ha azon csak egy fonal lehet es van is");

    Tekton tekton1 = new Tekton();
    Tekton tekton2 = new EgyetlenFonalTekton();

    test.jdbNotEqual("tekton1 letrehozasa", tekton1, null);
    test.jdbNotEqual("tekton2 letrehozasa", tekton2, null);

    tekton1.addSzomszed(tekton2);
    tekton2.addSzomszed(tekton1);

    test.jdbEquals(
        "tekton1  szomszedja tekton2-nek", tekton1.getSzomszed(1).contains(tekton2), true);
    test.jdbEquals(
        "tekton2  szomszedja tekton2-nek", tekton2.getSzomszed(1).contains(tekton1), true);

    GombaFonal gombaFonal2 = new GombaFonal();
    test.jdbNotEqual(
        "Gombafonal létrehozása amelyik az EgyetlenFonalTektonon van", gombaFonal2, null);

    tekton2.getFonalak().add(gombaFonal2);
    gombaFonal2.getVezet().add(tekton2);

    GombaFonal gombaFonal1 = new GombaFonal();
    test.jdbNotEqual("Gombafonal létrehozása amelyik nem tud athidalni", gombaFonal1, null);

    tekton1.getFonalak().add(gombaFonal1);
    gombaFonal1.getVezet().add(tekton1);

    tekton2.fonalNo(gombaFonal1);

    test.jdbEquals(
        "gombafonal nem tud athidalni, mert tekton2-n csak egy fonal lehet",
        gombaFonal1.getVezet().contains(tekton2),
        false);
  }
}
