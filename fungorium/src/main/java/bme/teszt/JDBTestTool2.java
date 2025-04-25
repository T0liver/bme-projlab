package bme.teszt;

import bme.Gombasz;
import bme.Rovarasz;
import bme.Tekton;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class JDBTestTool2 {

    File input, expected, out;
    boolean fromFile;

    ArrayList<Gombasz> gombaszok = new ArrayList<>();
    ArrayList<Rovarasz> rovaraszok = new ArrayList<>();
    ArrayList<Tekton> tektonok = new ArrayList<>();

    /// TesztFile futtató teszt
    /// @param file tesztfájl
    public JDBTestTool2(File file) {
        input = new File(file.getAbsolutePath(), "input.txt");
        expected = new File(file.getAbsolutePath(), "expected.txt");
        out = new File(file.getAbsolutePath(), "output.txt");
        try {
            if (!out.exists()) {
                out.createNewFile();
            } else {
                out.delete();
                out.createNewFile();
            }

        } catch (IOException e) {
            System.out.println("Kimenet fájl készítése sikertelen! "+out.getAbsolutePath());
        }

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
                scanner = new Scanner(new FileReader(input));
            } catch (FileNotFoundException e) {
                System.out.println("Tesztfájl megnyitása sikertelen " + input.getAbsolutePath());
                return;
            }
        } else {
            scanner = new Scanner(System.in);
        }

        while (scanner.hasNextLine()) {
            String[] args = scanner.nextLine().strip().split(" ");

            //MINDEN PARANCS EGY FUGGVENY
            switch (args[0]) {
                case "/adda":    AddAktor(args); break;
                case "/addtk":   AddTekton(args); break;
                case "/addsp":  AddSpora(args); break;
                case "/addgt":  AddGombatest(args); break;
                case "/addgf":  AddGombafonal(args); break;
                case "/addrov": AddRovar(args); break;
                case "/alttk": AltTekton(args); break;
                case "/altgf": AltGombafonal(args); break;
                case "/altgt": AltGombatest(args); break;
                case "/altrov": AltRovar(args); break;
                case "/help": Help(args); break;
                case "/random": Random(args); break;
                case "/script": Script(args); break;
                case "/lsa": ListAktor(args); break;
                case "/trig": Trig(args); break;
                case "/print": Print(args); break;
                case "/save": Save(args); break;
                case "/load": Load(args); break;
                case "/lst": ListTekton(args); break;
                case "/lsg": ListGombatest(args); break;
                case "/lsf": ListGombafonal(args); break;
                case "/lsr": ListRovar(args); break;
                case "cutf": CutFonal(args); break;
                case "spor": SporaSzoras(args); break;
                case "has": Hasadas(args); break;
                case "ontekton": OnTekton(args); break;
                case "movr": MoveRovar(args); break;
                case "growf": GrowFonal(args); break;
                case "eats": Eats(args); break;
                case "growg": GrowGombatest(args); break;
                default: System.out.println("Unknown command: " + args[0]); break;
            }
        }
        scanner.close();

        CheckOutput();
    }

    private void AppendOutput(String line){
        try {
            FileWriter fw = new FileWriter(out, true);
            fw.write(line + System.lineSeparator());
            fw.close();
        } catch (Exception e) {
            System.out.println("Kimenet írása sikertelen: "+out.getAbsolutePath());
        }
    }

    private void AltGombatest(String[] args) {
    }

    private void Help(String[] args) {}

    private void AltRovar(String[] args) {
    }

    private void Random(String[] args) {
    }

    private void Script(String[] args) {
    }

    private void Trig(String[] args) {
    }

    private void Load(String[] args) {
    }

    private void Save(String[] args) {
    }

    private void GrowGombatest(String[] args) {
    }

    private void AltGombafonal(String[] args) {
    }

    private void AltTekton(String[] args) {
    }

    private void AddTekton(String[] args) {
        System.out.println(args[0]);
        AppendOutput("Tektonnak lenni jó");
        AppendOutput("juhé");
    }

    private void AddAktor(String[] args) {

    }

    private void AddSpora(String[] args) {
    }

    private void AddGombatest(String[] args) {}

    private void AddGombafonal(String[] args) {}

    private void AddRovar(String[] args) {}

    private void ListAktor(String[] args) {}

    private void MoveRovar(String[] args) {}

    private void GrowFonal(String[] args) {}

    private void Eats(String[] args) {}

    private void CutFonal(String[] args) {}

    private void SporaSzoras(String[] args) {}

    private void Hasadas(String[] args) {}

    private void OnTekton(String[] args) {}

    private void Print(String[] args) {}

    private void ListGombafonal(String[] args) {}

    private void ListGombatest(String[] args) {}

    private void ListRovar(String[] args) {}

    private void ListTekton(String[] args) {}


    private void CheckOutput() {

    }



}
