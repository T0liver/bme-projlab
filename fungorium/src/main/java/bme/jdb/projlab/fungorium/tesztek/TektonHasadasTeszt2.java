package bme.jdb.projlab.fungorium.tesztek;

import bme.jdb.projlab.fungorium.GombaTest;
import bme.jdb.projlab.fungorium.Tekton;
import java.util.List;

public class TektonHasadasTeszt2 {

  /**
   * A teszt ellenőrzi, hogy a Tekton hasadása megfelelően működik-e, amikor egy GombaTest objektum
   * van elhelyezve rajta.
   */
  public static void TektonHasadasTeszt2() {

    // Teszt inicializálása
    JDBtesttool teszt = new JDBtesttool("TektonHasadasTeszt2");

    // Tekton objektum létrehozása
    Tekton tekton = new Tekton();

    // GombaTest létrehozása és elhelyezése a Tektonon
    GombaTest gombaTest;
    try {
      gombaTest = new GombaTest(5, tekton);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // Ellenőrzések
    teszt.jdbNotEqual("Tekton Létrehozás", tekton, null);
    teszt.jdbNotEqual("Gombatest1 létrehozása", gombaTest, null);
    teszt.jdbEquals("Gombatest1 elhelyezés...Tekton1", tekton.getFoglalt(), true);

    // Hasadás tesztelése
    List<Tekton> tektonok = tekton.hasad();

    // Ellenőrzés: hasadásnak nem szabad sikeresnek lennie, mivel a Tekton foglalt
    teszt.jdbEquals("Tekton hasadás sikertelen (gombatest van a tektonon)", tektonok.size(), 1);
    teszt.jdbNotEqual("Az eredeti tekton megmaradt", tekton, null);
  }
}
