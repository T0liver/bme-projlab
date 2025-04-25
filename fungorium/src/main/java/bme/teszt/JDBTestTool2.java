package bme.teszt;

import bme.*;

import java.io.*;
import java.util.*;

public class JDBTestTool2 {

    File input, expected, out;
    boolean fromFile;

    HashMap<Integer, Gombasz> gombaszok = new HashMap<>();
    HashMap<Integer, GombaFonal> gombaFonalak = new HashMap<>();
    HashMap<Integer, GombaTest> gombaTestek = new HashMap<>();
    HashMap<Integer, Spora> sporak = new HashMap<>();

    HashMap<Integer, Rovarasz> rovaraszok = new HashMap<>();
    HashMap<Integer, Rovar> rovarok = new HashMap<>();

    HashMap<Integer, Tekton> tektonok = new HashMap<>();

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

    private void Help(String[] args) {
        System.out.println("/adda: aktor hozzaadasa \n");
        System.out.println("/addtk: tekton hozzaadasa \n");
        System.out.println("/addsp: spora hozzaadasa \n");
        System.out.println("/addgt: gombatest hozzaadasa \n");
        System.out.println("/addgf: gombafonal hozzaadasa \n");
        System.out.println("/addrov: rovara hozzaadasa \n");
        System.out.println("/alttk: tekton adatainak modositasa \n");
        System.out.println("/altgf: gombafonal adatainak modositasa \n");
        System.out.println("/altgt: gombatest adatainak modositasa \n");
        System.out.println("/altrov: rovara adatainak modositasa \n");
        System.out.println("/random: random ertekek engedelyezese/letiltasa \n");
        System.out.println("/script: script futtatasa \n");
        System.out.println("/lsa: Aktorok listazasa \n");
        System.out.println("/lsr: Rovarok listazasa \n");
        System.out.println("/lsf: Gombafonalak listazasa \n");
        System.out.println("/lsg: Gombatestek listazasa \n");
        System.out.println("/lst: Tektonok listazasa \n");

    }

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
        Tekton tekton = new Tekton();
        if (args.length > 2) {

            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-t")) {
                    switch (args[++i]) {
                        case "egy":
                            tekton = new EgyetlenFonalTekton();
                            break;
                        case "flsz":
                            tekton = new FelszivoTekton(3);
                            break;
                        case "trm":
                            tekton = new TermeketlenTekton();
                            break;
                        //case "el": tekton = new
                        // NINCS ELETBENTARTO TEKTON
                    }
                    AppendOutput("new " + tekton.getClass() + " (" + args[1] + ")");
                }

                if (args[i].equals("-f")) {
                    tekton.setFoglalt(args[++i].equals("Y"));
                }
                if (args[i].equals("-nei")) {
                    tekton.addSzomszed(tektonok.get(Integer.parseInt(args[++i])));

                    List<Tekton> szomszedRegiSzomszedai = tektonok.get(Integer.parseInt(args[i])).getSzomszed(1);

                    List<Tekton> szomszedUjSzomszedai = szomszedRegiSzomszedai;
                    szomszedUjSzomszedai.add(tekton);

                    AppendOutput(tekton.getSzomszed(1).get(0).getClass()+ "("+404+") szomszédok: "
                            + Arrays.toString(szomszedRegiSzomszedai.toArray())
                            +" --> "+ Arrays.toString(szomszedUjSzomszedai.toArray()));

                }
                if (args[i].equals("-sp")) {
                    tekton.getSporak().add(sporak.get(Integer.parseInt(args[++i])));
                }
                //-fn nincs használva
            }
        } else {
            AppendOutput("new " + tekton.getClass() + " (" + args[1] + ")");
        }
        tektonok.put(Integer.parseInt(args[1]), tekton);
    }

    private void AddAktor(String[] args) {

        if (args.length < 6) {
            System.out.println("Használat: /adda -i <ID> -n <név> -f <g|r>");
            return;
        }

        if (args[6].equals("g")) {
            gombaszok.put(Integer.parseInt(args[2]), new Gombasz(args[4]));
            AppendOutput("new Gombász (" + args[2] + ")");

        } else if (args[6].equals("r")) {
            rovaraszok.put( Integer.parseInt(args[2]),new Rovarasz(args[4]));
            AppendOutput("new Rovarász (" + args[2] + ")");
        } else {
            System.out.println("Ismeretlen típus: " + args[6]);
        }
    }


    private void AddSpora(String[] args) {
        Spora spora = null;
        Gombasz gombasz = null;
        int db = 5;
        int tp = 5;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-a")) {
                gombasz = gombaszok.get(Integer.parseInt(args[i + 1]));
            }

            if (args[i].equals("-t")) {
                switch (args[i + 1]) {
                    case "gyrs":
                        spora = new GyorsitoSpora(tp, db, gombasz);
                        AppendOutput("new GyorsítóSpóra(" + args[1] + ")");
                        break;
                    case "ls":
                        spora = new LassitoSpora(tp, db, gombasz);
                        AppendOutput("new LassítóSpóra(" + args[1] + ")");
                        break;
                    case "bnt":
                        spora = new BenitoSpora(tp, db, gombasz);
                        AppendOutput("new BénítóSpóra(" + args[1] + ")");
                        break;
                    case "csrb":
                        spora = new CsorbitoSpora(tp, db, gombasz);
                        AppendOutput("new CsorbítóSpóra(" + args[1] + ")");
                        break;
                    case "oszt":
                        spora = new OsztoSpora(tp, db, gombasz);
                        AppendOutput("new OsztódóSpóra(" + args[1] + ")");
                        break;
                    default:
                        spora = new Spora(tp, db, gombasz);
                        AppendOutput("new Spóra(" + args[1] + ")");
                        break;
                }

            }

            if (args[i].equals("-db")) {
                assert spora != null;
                spora.setDarabszam(Integer.parseInt(args[i + 1]));
            }

            if (args[i].equals("-tp")) {
                spora.setTapanyag(Integer.parseInt(args[i + 1]));
            }

        }

        sporak.put(Integer.parseInt(args[1]), spora);

    }

    private void AddGombatest(String[] args){
        int sporak = 0;
        int elettartam = 0;
        int fejlettseg = 0;
        boolean fejlett = false;
        Tekton hely = null;
        Gombasz gombasz = null;

        int ID = Integer.parseInt(args[1]);
        AppendOutput("new   Gombatest("+ ID +")");

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    int aktorID = Integer.parseInt(args[i + 1]);
                    gombasz = gombaszok.get(aktorID);
                    break;
                case "-tart":
                    Integer tektonId = Integer.parseInt(args[i + 1]);
                    hely = tektonok.get(tektonId);
                    break;
                case "-sp":
                    sporak = Integer.parseInt(args[i + 1]);
                    break;
                case "-life":
                    elettartam = Integer.parseInt(args[i + 1]);
                    break;
                case "-fjl":
                    fejlettseg = Integer.parseInt(args[i + 1]);
                    break;
                case "-fj":
                    fejlett = args[i + 1].equalsIgnoreCase("Y");
                    break;
            }
        }

        try {
            GombaTest gombatest = new GombaTest(gombasz, sporak, elettartam, fejlett, fejlettseg, hely);
            gombaTestek.put(ID, gombatest);
        } catch (Exception e) {
            AppendOutput("INSTRUCTION FAIL "+ Arrays.toString(args) +" ("+e.getMessage()+")");
        }


    }


    private void AddGombafonal(String[] args) {}

    private void AddRovar(String[] args) {

        Rovar rovar = null;
        Rovarasz rovarasz = null;
        Tekton tekton = null;
        int seb = 5;
        boolean vaghat = false;
        int ujv = 5;

        for(int i = 0; i < args.length; i++) {
            if (args[i].equals("-a")) {
                rovarasz = rovaraszok.get(Integer.parseInt(args[i + 1]));
            }

            if (args[i].equals("-tk")){
                tekton = tektonok.get(Integer.parseInt(args[i + 1]));
            }

            if (args[i].equals("-seb")){
                seb = Integer.parseInt(args[i + 1]);
            }

            if (args[i].equals("-vag")) {
                if (args[i + 1].equalsIgnoreCase("Y")) vaghat = true;
                else vaghat = false;
            }

            if (args[i].equals("-ujv")) {
                ujv = Integer.parseInt(args[i + 1]);
            }
        }

        rovar = new Rovar(rovarasz, tekton);
        AppendOutput("new Rovar(" + args[1] + ")");

        rovar.setSebesseg(seb);
        rovar.setVaghat(vaghat);
        rovar.setUjravaghat(ujv);

        rovarok.put(Integer.parseInt(args[1]), rovar);

    }

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
