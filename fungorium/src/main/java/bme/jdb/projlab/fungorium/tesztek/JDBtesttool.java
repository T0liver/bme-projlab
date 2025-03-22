package bme.jdb.projlab.fungorium.tesztek;

/**
 * Egyszerű teszt keret
 */
public class JDBtesttool {
    /** Tesztek száma*/
    static int testCount = 0;
    /** Sikertelen tesztek száma*/
    static int failedCount = 0;

    /** Teszt neve*/
    String testName;
    boolean failed;

    public JDBtesttool(String testName){
        this.testName = testName;
        testCount++;
        System.out.println("\n---| "+testName+" |---");
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
        info.append(name);

        if (actual.equals(expected)) {
            info.append("...\tOK");
        } else {
            fail();
            info.append("...\tSIKERTELEN")
            .append("\n\t> Elvárt: ")
            .append(expected)
            .append("\n\t> Kapott: ")
            .append(actual);
        }

        System.out.println(info);
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
        info.append(name);

        if (!actual.equals(notThis)) {
            info.append("...\tOK");
        } else {
            fail();
            info.append("...\tSIKERTELEN")
                    .append("\n\t> Elvárt: ")
                    .append(notThis)
                    .append("\n\t> Kapott: ")
                    .append(actual);
        }

        System.out.println(info);
    }

    /**
     * Összegzés
     */
    public static void jdbSummary(){
        StringBuilder info = new StringBuilder();

        if (testCount > 0) {
            if (failedCount == 0) {
                info.append("Teszt sikeres! ").append(testCount).append("/").append(testCount);
            } else {
                info.append("Teszt sikertelen! ").append(failedCount).append("/").append(testCount);
            }
        }

        System.out.println(info);
    }

    /**
     * Sikertelen teszt feljegyzése
     */
    private void fail(){
        if (!failed) {
            failed = true;
            failedCount++;
        }
    }

}
