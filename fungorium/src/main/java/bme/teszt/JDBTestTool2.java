package bme.teszt;

import bme.Gombasz;
import bme.Rovarasz;
import bme.Tekton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class JDBTestTool2 {

    File testFile;
    boolean fromFile;

    ArrayList<Gombasz> gombaszok = new ArrayList<>();
    ArrayList<Rovarasz> rovaraszok = new ArrayList<>();
    ArrayList<Tekton> tektonok = new ArrayList<>();

    /// TesztFile futtató teszt
    /// @param file tesztfájl
    public JDBTestTool2(File file) {
        testFile = file;
        fromFile = true;
    }

    /// Szabad utasításvégrehajtásos teszt
    public JDBTestTool2() {
        fromFile = false;
    }

    public void RunTest() {
        Scanner scanner;
        if (fromFile) {
            try {
                scanner = new Scanner(new FileReader(testFile));
            } catch (FileNotFoundException e) {
                System.out.println("Tesztfájl megnyitása sikertelen " + testFile.getAbsolutePath());
                return;
            }
        } else {
            scanner = new Scanner(System.in);
        }

        while (scanner.hasNextLine()) {
            String[] args = scanner.nextLine().strip().split(" ");

            //MINDEN PARANCS EGY FUGGVENY
            switch (args[0]) {
                case "adda":    AddAktor(args); break;
                case "addtk":   AddTekton(args); break;
            }
        }
        scanner.close();
    }

    private void AddTekton(String[] args) {
    }

    private void AddAktor(String[] args) {
    }
}
