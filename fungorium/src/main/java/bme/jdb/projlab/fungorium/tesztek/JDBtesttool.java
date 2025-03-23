package bme.jdb.projlab.fungorium.tesztek;

import java.util.ArrayList;
import java.util.List;

/** Egyszerű teszt keret */
public class JDBtesttool {
  /** Tesztek száma */
  static int testCount = 0;

  /** Sikertelen tesztek száma */
  static int failedCount = 0;

  /** Sikertelen tesztek nevei */
  static List<String> failList = new ArrayList<>();

  /** Teszt neve */
  String testName;

  /** Teszt id */
  int id;

  /** Teszt sikeresseg */
  boolean failed;

  /** Konzolra iras formazo */
  String leftAlignFormat = "     %-50s  %-4s %n";

  /**
   * ÚJ teszt létrehozására használt konstruktor
   *
   * @param testName teszt neve
   */
  public JDBtesttool(String testName) {
    this.testName = testName;
    testCount++;
    this.id = testCount;
    System.out.println(System.lineSeparator() + "[" + id + "] ---| " + testName + " |---");
  }

  /**
   * Két érték megegyezésének vizsgálata
   *
   * @param name értékvizsgálat neve
   * @param actual aktuális érték
   * @param expected elvárt érték
   */
  public void jdbEquals(String name, Object actual, Object expected) {

    StringBuilder info = new StringBuilder();
    info.append(name).append("...");

    if (actual.equals(expected)) {
      System.out.format(leftAlignFormat, info, "OK");
    } else {
      fail();
      System.out.format(leftAlignFormat, info, "SIKERTELEN");
      System.out.println("        > Elvárt: " + expected);
      System.out.println("        > Kapott: " + actual);
    }
  }

  /**
   * Két érték eltérésének vizsgálata
   *
   * @param name értékvizsgálat neve
   * @param actual aktuális érték
   * @param notThis tiltott érték
   */
  public void jdbNotEqual(String name, Object actual, Object notThis) {

    StringBuilder info = new StringBuilder();
    info.append(name).append("...");

    if (!actual.equals(notThis)) {
      System.out.format(leftAlignFormat, info, "OK");
    } else {
      fail();
      System.out.format(leftAlignFormat, info, "SIKERTELEN");
      System.out.println("        > Tiltott: " + notThis);
      System.out.println("        > Kapott: " + actual);
    }
  }

  /** Összegzés */
  public static void jdbSummary() {
    StringBuilder info = new StringBuilder();
    System.out.println();

    if (testCount > 0) {
      if (failedCount == 0) {
        info.append("Teszt sikeres! ").append(testCount).append("/").append(testCount);
        System.out.println(info);
      } else {
        info.append("Teszt sikertelen! ")
            .append(testCount)
            .append("/")
            .append(testCount - failedCount);

        System.out.println(info);

        System.out.println("Sikertelen tesztek:");
        for (String fail : failList) {
          System.out.println("    " + fail);
        }
      }
    }
    testCount = 0;
    failedCount = 0;
    failList.clear();
  }

  /** Sikertelen teszt feljegyzése */
  private void fail() {
    if (!failed) {
      failed = true;
      failedCount++;
      failList.add("[" + id + "] : " + testName);
    }
  }
}
