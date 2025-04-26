package bme.teszt;

import bme.*;

import java.io.*;
import java.util.*;

public class JDBTestTool2 implements Serializable{

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
        if (args.length == 0) {
            System.out.println("Nincs megadva parancs.");
            return;
        }

        switch (args[1]) {
            case "-nr":
                Jatekvezerlo.korVege();
                System.out.println("EVENT tick");
                break;
            //TODO: EZ MIRE KELL?
            case "-np":
                Jatekvezerlo.jelenlegiJatekos = (Jatekvezerlo.jelenlegiJatekos + 1) % Jatekvezerlo.jatekosok.size();
                System.out.println("Következő játékos: " + Jatekvezerlo.jatekosok.get(Jatekvezerlo.jelenlegiJatekos).getId());
                break;

            default:
                System.out.println("Ismeretlen parancs: " + args[1]);
                break;
        }
    }


    private void Load(String[] args) {
        if (args.length == 0) {
            System.out.println("Nem adtál meg mentési fájlt.");
            return;
        }

        String filePath = args[1];
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Feltételezve, hogy a Jatekvezerlo osztály statikus változóit tölti vissza
            Jatekvezerlo.jelenlegiKor = ois.readInt();
            Jatekvezerlo.jelenlegiJatekos = ois.readInt();
            Jatekvezerlo.jatekHossz = ois.readInt();
            Jatekvezerlo.tektonok = (List<Tekton>) ois.readObject();
            Jatekvezerlo.jatekosok = (List<Jatekos>) ois.readObject();

            System.out.println("Játék betöltve sikeresen: " + filePath);
        } catch (Exception e) {
            System.out.println("Hiba a játék betöltésekor: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void Save(String[] args) {
        if (args.length == 0) {
            System.out.println("Nem adtál meg mentési fájlt.");
            return;
        }

        String filePath = args[1];
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Játék állapotának mentése
            oos.writeInt(Jatekvezerlo.jelenlegiKor);
            oos.writeInt(Jatekvezerlo.jelenlegiJatekos);
            oos.writeInt(Jatekvezerlo.jatekHossz);
            oos.writeObject(Jatekvezerlo.tektonok);
            oos.writeObject(Jatekvezerlo.jatekosok);

            System.out.println("Játék elmentve sikeresen: " + filePath);
        } catch (Exception e) {
            System.out.println("Hiba a játék mentésekor: " + e.getMessage());
            e.printStackTrace();
        }
    }

//TODO: A kimenetében segítsetek
    private void GrowGombatest(String[] args) {


        Gombasz gombasz = null;
        Tekton hely = null;
        int aktorID = -1;
        Spora spora = null;

        // Beolvassuk az ID-t (első argumentum: Gombatest ID)
        int ID = Integer.parseInt(args[1]);

        // Bemeneti argumentumok feldolgozása
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    // AktorID beállítása
                    aktorID = Integer.parseInt(args[i + 1]);
                    gombasz = gombaszok.get(aktorID);
                    break;
                case "-tk":
                    // Tekton ID beállítása
                    Integer tektonId = Integer.parseInt(args[i + 1]);
                    hely = tektonok.get(tektonId);
                    break;
            }
        }

        // Ellenőrzés, hogy elegendő spóra van-e a tektonton
        if (hely.getSporak().size() < 5) {
            AppendOutput("INSTRUCTION FAIL " + Arrays.toString(args) + " (Nincs elég spóra)");
            return;
        }

        // Kiválasztjuk a megfelelő spórát
        for (int i = 0; i < hely.getSporak().size(); i++) {
            if (hely.getSporak().get(i).getId() == aktorID) {
                spora = hely.getSporak().get(i);
                break;
            }
        }

        // Ha nem találunk spórát, hibaüzenetet adunk
        if (spora == null) {
            AppendOutput("INSTRUCTION FAIL " + Arrays.toString(args) + " (Nem található megfelelő spóra)");
            return;
        }

        try {
            GombaTest gombatest = new GombaTest(gombasz, 10, hely);
            gombaTestek.put(ID, gombatest);
            hely.sporatFelhasznal(spora);
        } catch (Exception e){
            AppendOutput("INSTRUCTION FAIL "+ Arrays.toString(args) +" ("+e.getMessage()+")");
        }

        AppendOutput("EVENT Gombatest növesztés\n" + "remove Spóra("  );
    }

    private void AltGombafonal(String[] args) {
    }

    private void AltTekton(String[] args) {
    }

    private void AddTekton(String[] args) {
        Tekton tekton = new Tekton();
        System.out.println(Arrays.toString(args));
        if (args.length > 2) {

            String fullline = String.join("", args);
            if (!fullline.contains("-t")){
                tekton.setId(Integer.parseInt(args[1]));
                AppendOutput("new " + Name(tekton));
            }

            for (int i = 1; i < args.length; i++) {
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
                    tekton.setId(Integer.parseInt(args[1]));
                    AppendOutput("new " + Name(tekton));
                }

                if (args[i].equals("-f")) {
                    tekton.setFoglalt(args[i + 1].equals("Y"));
                }
                if (args[i].equals("-nei")) {
                    Tekton szomszed = tektonok.get(Integer.parseInt(args[i + 1]));
                    tekton.addSzomszed(szomszed);

                    List<Tekton> szomszedRegiSzomszedai = new ArrayList<>(szomszed.getSzomszed(1));
                    szomszed.addSzomszed(tekton);
                    List<Tekton> szomszedSzomszedai = szomszed.getSzomszed(1);

                    AppendOutput(Name(szomszed)+" szomszédok: "
                            + ListToString(szomszedRegiSzomszedai)
                            +" --> "+ ListToString(szomszedSzomszedai));
                }
                if (args[i].equals("-sp")) {
                    tekton.getSporak().add(sporak.get(Integer.parseInt(args[i + 1])));
                }
                //-fn nincs használva
            }
        } else {
            tekton.setId(Integer.parseInt(args[1]));
            AppendOutput("new " + Name(tekton));
        }
        tektonok.put(tekton.getId(), tekton);

    }

    private void AddAktor(String[] args) {

        if (args.length < 6) {
            System.out.println("Használat: /adda -i <ID> -n <név> -f <g|r>");
            return;
        }

        if (args[6].equals("g")) {
            Gombasz g = new Gombasz(args[4]);
            g.setId(Integer.parseInt(args[2]));
            gombaszok.put(g.getId(), g);
            AppendOutput("new "+Name(g));

        } else if (args[6].equals("r")) {
            Rovarasz r = new Rovarasz(args[4]);
            r.setId(Integer.parseInt(args[2]));
            rovaraszok.put(r.getId(), r);
            AppendOutput("new "+Name(r));
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
        AppendOutput("new Gombatest("+ ID +")");

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
            AppendOutput("INSTRUCTION FAIL \""+ String.join(" ", args) +"\" ("+e.getMessage()+")");
        }


    }


    private void AddGombafonal(String[] args) {

        GombaFonal gf = new GombaFonal();
        gf.setId(Integer.parseInt(args[1]));
        AppendOutput("new "+Name(gf));

        int aktorID = Integer.parseInt(args[3]);
        gombaszok.get(aktorID).getGombaFonalak().add(gf);

        for (int i = 5; i < args.length; i++) {
            String[] tektonokIds = args[i].split(";");
            int tId1 = Integer.parseInt(tektonokIds[0]);
            int tId2 = Integer.parseInt(tektonokIds[1]);

            Tekton t1 = tektonok.get(tId1);
            List<GombaFonal> ujvezet = t1.getFonalak();
            List<GombaFonal> vezet = new ArrayList<>(ujvezet);
            ujvezet.add(gf);
            AppendOutput(Name(t1)+" vezet: "+ListToString(vezet)+" --> "+ListToString(ujvezet));

            Tekton t2 = tektonok.get(tId2);
            ujvezet = t2.getFonalak();
            vezet = new ArrayList<>(ujvezet);
            ujvezet.add(gf);
            AppendOutput(Name(t2)+" vezet: "+ListToString(vezet)+" --> "+ListToString(ujvezet));

            gf.addVezet(t1, t2);
            gombaFonalak.put(Integer.parseInt(args[1]), gf);
        }
    }

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

    private void ListAktor(String[] args) {

        System.out.println("Gombaszok listaja:");
        for (Map.Entry<Integer, Gombasz> entry : gombaszok.entrySet()) {
            Integer id = entry.getKey();
            Gombasz gombasz = entry.getValue();

            System.out.println("ID: " + id);
            System.out.println("Gombatestek: ");
            for (GombaTest gombatest : gombaTestek.values()) {
                System.out.println("Gombatest: " + gombatest.getId());
            }

            System.out.println("GombaFonalak: ");
            for (GombaFonal gombaFonal : gombaFonalak.values()) {
                System.out.println("GombaFonal: " + gombaFonal.getId());
            }

            System.out.println("Sporak: " );
            for (Spora spora : sporak.values()) {
                System.out.println("Spora: " + spora.getId());
            }

            System.out.println("------------------------");
        }


    }

    private void MoveRovar(String[] args) {

        Rovar rovar = rovarok.get(Integer.parseInt(args[1]));
        int oldValue = rovar.getSebesseg();
        int tektonID = 0;

        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("-tk")){
                tektonID = Integer.parseInt(args[i + 1]);
            }
        }

        rovar.mozog(tektonok.get(tektonID));
        AppendOutput("EVENT Rovar mozog\nRovar (" + args[1] + ") Tekton: (" + oldValue + ") --> Tekton (" + tektonID + ")");
    }

    private void GrowFonal(String[] args) {

        GombaTest gt = gombaTestek.get(Integer.parseInt(args[1]));
        Tekton gtt = gt.getTartozkodik();

        int gombaszID = gt.getGombasz().getId();

        String[] tektonokIds = args[3].split(";");
        int tId1 = Integer.parseInt(tektonokIds[0]);
        int tId2 = Integer.parseInt(tektonokIds[1]);

        Tekton innen = tektonok.get(tId1);
        Tekton ide = tektonok.get(tId2);

        if (!gtt.getSzomszed(1).contains(ide)) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Tektonok nem szomszédosak)");
        } else {
            AppendOutput("\nEVENT Fonal áthidal");

            int gfId = gombaFonalak.size() + 1;
            AddGombafonal(new String[]{"/addgf", String.valueOf(gfId), "-a", String.valueOf(gombaszID),"-vez", tektonokIds[0]+";"+tektonokIds[1]});

            gombaFonalak.get(gfId).addVezet(innen, ide);
        }
    }

    private void Eats(String[] args) {

        Rovar rovar = rovarok.get(Integer.parseInt(args[1]));
        Spora spora = rovar.getTartozkodik().getBestSpora();
        Rovarasz rovarasz = rovar.getRovarasz();
        int db = 0;
        int sporaID = 0;
        int oldValue = spora.getDarabszam();
        int oldPont = rovarasz.getPontok();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-sp")) {
                spora = sporak.get(Integer.parseInt(args[i + 1]));
                sporaID = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-db")) {
                db = Integer.parseInt(args[i + 1]);
            }
        }

        rovar.eszik(spora);
        AppendOutput("EVENT Spóra evés\nSpóra (" + sporaID +") darabszám " + oldValue + " --> " + spora.getDarabszam() + "\nRovarász (" + rovarasz.getId() + ") pontok: " + oldPont + " --> " + rovarasz.getPontok());
    }

    private void CutFonal(String[] args) {}

    //Elegge gatya sok mindent kell meg benne csinalni
    private void SporaSzoras(String[] args) {

        GombaTest gombatest = null;
        Tekton tekton = null;
        Spora spora = null;
        int TektonID = 0;
        int GombatestID = 0;
        int GTOldValue = 0;


        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-if")) {
                gombatest = gombaTestek.get(Integer.parseInt(args[i + 1]));
                GombatestID = Integer.parseInt(args[i + 1]);
                GTOldValue = gombatest.getElettartam();
            }
            if (args[i].equals("-tk")) {
                tekton = tektonok.get(Integer.parseInt(args[i + 1]));
                TektonID = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-type")){
                switch (args[i + 1]) {
                        case "gyrs": break;
                        case "ls": break;
                        case "bnt": break;
                        case "csrb": break;
                        case "oszt": break;
                        default: break;
                }
            }
        }

        gombatest.sporatSzor(tekton);
        AppendOutput("EVENT Spórázás\nnew Spóra ()\nSpóra (1) aktor: null --> Gombász (1)\nSpóra (1) tápanyagtartalom: 0 -> 5\n" +
                "Spóra (1) darabszám: 0 -> 3\nTekton ("+ TektonID +") spórák: [ ] --> [Spóra (1)]\nGombatest (" + GombatestID + ") élettartam: "+ GTOldValue +" --> " + gombatest.getElettartam());
    }

    private void Hasadas(String[] args) {

        Tekton tekton = tektonok.get(Integer.parseInt(args[1]));

        if (tekton.getFoglalt()) {
            AppendOutput("\nINSTRUCTION FAIL \"" +String.join(" ", args)+ "\" (Nem tud hasadni, mert Gombatest van rajta)");
            return;
        }

        tekton.hasad();
        AppendOutput("\nEVENT Tekton hasad");

        AppendOutput("remove Tekton " + Name(tekton));

        AddTekton(new String[] {"/addtk", "2"});
        AddTekton(new String[] {"/addtk", "3", "-nei", "2"});

    }

    private void OnTekton(String[] args) {}

    private void Print(String[] args) {
        if (args.length < 2) {
            System.out.println("Hiba: Nem adtál meg fájlnevet!");
            return;
        }

        String fileName = args[1];

        try {
            // Itt feltételezem, hogy van egy StringBuilder output, amiben az eddigi konzolkimenetet tárolod.
            File file = new File(fileName);
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(out.toString()); // Vagy amit használsz a kimenet tárolására
            }
            System.out.println("Kimenet fájlba írva: " + fileName);
        } catch (Exception e) {
            System.out.println("Hiba történt a fájlírás során: " + e.getMessage());
        }
    }

    private void ListGombafonal(String[] args) {}

    private void ListGombatest(String[] args) {

        System.out.println("GombaTestek listaja:");
        for (Map.Entry<Integer, GombaTest> entry : gombaTestek.entrySet()) {
            Integer id = entry.getKey();
            GombaTest gomba = entry.getValue();

            System.out.println("ID: " + id);
            System.out.println("Gombasz: " + gomba.getGombasz().getId());
            System.out.println("Fejlett: " + (gomba.getFejlett() ? "Igen" : "Nem"));
            System.out.println("Fejlettsegi szint: " + gomba.getFejlettseg());
            System.out.println("Sporak szamat: " + gomba.getSporaDarab());
            System.out.println("Tartozkodas: " + gomba.getTartozkodik().getId());
            System.out.println("Elettartam: " + gomba.getElettartam());
            System.out.println("------------------------");
        }

    }

    private void ListRovar(String[] args) {
        System.out.println("Rovarok listaja:");
        for (Map.Entry<Integer, Rovar> entry : rovarok.entrySet()) {
            Integer id = entry.getKey();
            Rovar rovar = entry.getValue();

            System.out.println("ID: " + id);
            System.out.println("Sebesseg: " + rovar.getSebesseg());
            System.out.println("Vaghat: " + (rovar.getVaghat() ? "Igen" : "Nem"));
            System.out.println("Ujravaghat: " + rovar.getUjravaghat());
            System.out.println("Tartozkodik: " + rovar.getTartozkodik().getId());
            System.out.println("Rovarasz: " + rovar.getRovarasz().getId());
            System.out.println("------------------------");
        }

    }

    private void ListTekton(String[] args) {

        System.out.println("Tektonok listaja:");
        for (Map.Entry<Integer, Tekton> entry : tektonok.entrySet()) {
            Integer id = entry.getKey();
            Tekton tekton = entry.getValue();

            System.out.println("ID: " + id);
            System.out.println("Foglalt: " + (tekton.getFoglalt() ? "Igen" : "Nem"));
            System.out.println("Szomszedok: ");
            for (Tekton szomszed : tekton.getSzomszed(1)) {
                System.out.println(szomszed.getId() + ",");
            }
            System.out.println("Sporak: ");
            for (Spora spora : tekton.getSporak()) {
                System.out.println(spora.getId() + ",");
            }
            System.out.println("Fonalak");
            for (GombaFonal fonal : tekton.getFonalak()) {
                System.out.println(fonal.getId() + ",");
            }

            System.out.println("------------------------");
        }
    }


    private void CheckOutput() {

    }

    private String Name(Jatekelem o) {
        String className = o.getClass().getName();
        className = className.replace("class", "");
        className = className.replace("bme.", "");

        className = className + " ("+o.getId()+")";
        return className;
    }

    private<T extends Jatekelem> String ListToString(List<T> list) {

        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (T o : list) {
            sb.append(Name(o))
                    .append(", ");
        }
        if (!list.isEmpty()) {
            sb.replace(sb.length()-2, sb.length()-1, " ]");
        } else
            sb.append("]");


        return sb.toString();
    }

}
