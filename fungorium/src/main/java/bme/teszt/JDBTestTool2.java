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


    /// TesztFile futtato teszt
    /// @param file tesztfajl
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
            System.out.println("Kimenet fajl keszitese sikertelen! "+out.getAbsolutePath());
        }

        fromFile = true;
    }

    /// Szabad utasitasvegrehajtasos teszt
    public JDBTestTool2() {
        fromFile = false;
    }

    public void RunTest() {
        Scanner scanner;
        if (fromFile) {
            try {
                scanner = new Scanner(new FileReader(input));
            } catch (FileNotFoundException e) {
                System.out.println("Tesztfajl megnyitasa sikertelen " + input.getAbsolutePath());
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
        if (fromFile){
            CheckOutput();
        }

    }

    private void AppendOutput(String line){
        if (fromFile) {
            try {
                FileWriter fw = new FileWriter(out, true);
                fw.write(line + System.lineSeparator());
                fw.close();
            } catch (Exception e) {
                System.out.println("Kimenet irasa sikertelen: "+out.getAbsolutePath());
            }
        } else {
            System.out.println(line);
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
                AppendOutput("EVENT tick");
                break;
            //TODO: EZ MIRE KELL?
            case "-np":
                Jatekvezerlo.jelenlegiJatekos = (Jatekvezerlo.jelenlegiJatekos + 1) % Jatekvezerlo.jatekosok.size();
                System.out.println("Kovetkezo jatekos: " + Jatekvezerlo.jatekosok.get(Jatekvezerlo.jelenlegiJatekos).getId());
                break;

            default:
                System.out.println("Ismeretlen parancs: " + args[1]);
                break;
        }
    }


    private void Load(String[] args) {
        if (args.length == 0) {
            System.out.println("Nem adtal meg mentesi fajlt.");
            return;
        }

        String filePath = args[1];
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            // Feltetelezve, hogy a Jatekvezerlo osztaly statikus valtozoit tolti vissza
            Jatekvezerlo.jelenlegiKor = ois.readInt();
            Jatekvezerlo.jelenlegiJatekos = ois.readInt();
            Jatekvezerlo.jatekHossz = ois.readInt();
            Jatekvezerlo.tektonok = (List<Tekton>) ois.readObject();
            Jatekvezerlo.jatekosok = (List<Jatekos>) ois.readObject();

            System.out.println("Jatek betoltve sikeresen: " + filePath);
        } catch (Exception e) {
            System.out.println("Hiba a jatek betoltesekor: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void Save(String[] args) {
        if (args.length == 0) {
            System.out.println("Nem adtal meg mentesi fajlt.");
            return;
        }

        String filePath = args[1];
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            // Jatek allapotanak mentese
            oos.writeInt(Jatekvezerlo.jelenlegiKor);
            oos.writeInt(Jatekvezerlo.jelenlegiJatekos);
            oos.writeInt(Jatekvezerlo.jatekHossz);
            oos.writeObject(Jatekvezerlo.tektonok);
            oos.writeObject(Jatekvezerlo.jatekosok);

            System.out.println("Jatek elmentve sikeresen: " + filePath);
        } catch (Exception e) {
            System.out.println("Hiba a jatek mentesekor: " + e.getMessage());
            e.printStackTrace();
        }
    }

//TODO: A kimeneteben segitsetek
    private void GrowGombatest(String[] args) {

        Gombasz gombasz = null;
        Tekton hely = null;
        int aktorID = -1;
        Spora spora = null;

        // Beolvassuk az ID-t (elso argumentum: Gombatest ID)
        int ID = Integer.parseInt(args[1]);

        // Bemeneti argumentumok feldolgozasa
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    // AktorID beallitasa
                    aktorID = Integer.parseInt(args[i + 1]);
                    gombasz = gombaszok.get(aktorID);
                    break;
                case "-tk":
                    // Tekton ID beallitasa
                    Integer tektonId = Integer.parseInt(args[i + 1]);
                    hely = tektonok.get(tektonId);
                    break;
            }
        }

        int cnt = 0;
        for (int i = 0; i < hely.getSporak().size(); i++) {
            cnt += hely.getSporak().get(i).getDarabszam();
        }

        boolean vanBenult = false;
        int benultID = 0;
        for (Integer rovar : rovarok.keySet()){
            if (!rovarok.get(rovar).getVaghat() && rovarok.get(rovar).getTartozkodik() == hely){
                vanBenult = true;
                benultID = rovar;
            }
        }

        // Ellenorzes, hogy elegendo spora van-e a tektonton
        if (cnt < 5 && !vanBenult) {
            AppendOutput("\nINSTRUCTION FAIL " + Arrays.toString(args) + " (Nincs eleg spora)");
            return;
        }

        // Kivalasztjuk a megfelelo sporat
        for (int i = 0; i < hely.getSporak().size(); i++) {
            if (hely.getSporak().get(i).getGombasz().getId() == aktorID) {
                spora = hely.getSporak().get(i);
                break;
            }
        }

        // Ha nem talalunk sporat, hibauzenetet adunk
        if (spora == null && !vanBenult) {
            AppendOutput("\nINSTRUCTION FAIL " + Arrays.toString(args) + " (Nem talalhato megfelelo spora)");
            return;
        }

        if (hely instanceof TermeketlenTekton) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Termeketlen a tekton)");
            return;
        }


        try {
            GombaTest gombatest = new GombaTest(gombasz, 10, hely);
            gombaTestek.put(ID, gombatest);

            if (!vanBenult) {
                hely.sporatFelhasznal(spora);
            }

            int pontok = gombasz.getPontok();
            int ujpontok = gombasz.addPontok(10);

            if (vanBenult){
                AppendOutput("\nEVENT Gombatest novesztes\n"
                        + "remove "+Name(rovarok.get(benultID))
                        + "\nnew Gombatest (" + ID + ")\n"
                        + "Gombatest (" + ID + ") aktor: null --> Gombasz (" + gombasz.getId() + ")\n"
                        + "Gombatest (" + ID + ") tekton: null --> Tekton (" + hely.getId() + ")\n"
                        + "Gombatest (" + ID + ") spora: 0 --> " + gombatest.getSporaDarab() + "\n"
                        + "Gombasz (" + gombasz.getId() + ") pontok: " + pontok + " --> " + ujpontok
                );
                rovarok.remove(benultID);
            } else {

                AppendOutput("\nEVENT Gombatest novesztes\n"
                        + "remove Spora (" + spora.getId() + ")\n"
                        + "new Gombatest (" + ID + ")\n"
                        + "Gombatest (" + ID + ") aktor: null --> Gombasz (" + gombasz.getId() + ")\n"
                        + "Gombatest (" + ID + ") tekton: null --> Tekton (" + hely.getId() + ")\n"
                        + "Gombatest (" + ID + ") spora: 0 --> " + gombatest.getSporaDarab() + "\n"
                        + "Gombasz (" + gombasz.getId() + ") pontok: " + pontok + " --> " + ujpontok
                );
            }

        } catch (Exception e){
            AppendOutput("INSTRUCTION FAIL "+ Arrays.toString(args) +" ("+e.getMessage()+")");
        }


    }

    private void AltGombafonal(String[] args) {
    }

    private void AltTekton(String[] args) {
    }

    private void AddTekton(String[] args) {
        Tekton tekton = new Tekton();
        if (args.length > 2) {

            String fullline = String.join("", args);
            if (!fullline.contains("-t")){
                tekton.setId(Integer.parseInt(args[1]));
                AppendOutput("new " + Name(tekton));
            } else {
                int typeID = 0;
                for (int i = 1; i < args.length; i++) {
                    if (args[i].equals("-t")){
                        typeID = i + 1;
                    }
                }
                //case "el": tekton = new
                // NINCS ELETBENTARTO TEKTON
                tekton = switch (args[typeID]) {
                    case "egy" -> new EgyetlenFonalTekton();
                    case "flsz" -> new FelszivoTekton(3);
                    case "trm" -> new TermeketlenTekton();
                    case "el" -> new EletbenTartoTekton();
                    default -> tekton;
                };
                tekton.setId(Integer.parseInt(args[1]));
                AppendOutput("new " + Name(tekton));
            }

            for (int i = 1; i < args.length; i++) {

                if (args[i].equals("-f")) {
                    tekton.setFoglalt(args[i + 1].equals("Y"));
                }
                if (args[i].equals("-nei")) {
                    Tekton szomszed = tektonok.get(Integer.parseInt(args[i + 1]));
                    tekton.addSzomszed(szomszed);

                    List<Tekton> szomszedRegiSzomszedai = new ArrayList<>(szomszed.getSzomszed(1));
                    szomszed.addSzomszed(tekton);
                    List<Tekton> szomszedSzomszedai = szomszed.getSzomszed(1);

                    AppendOutput(Name(szomszed)+" szomszedok: "
                            + ListToString(szomszedRegiSzomszedai)
                            +" --> "+ ListToString(szomszedSzomszedai));
                }
                if (args[i].equals("-sp")) {
                    tekton.getSporak().add(sporak.get(Integer.parseInt(args[i + 1])));
                }
                //-fn nincs hasznalva
            }
        } else {
            tekton.setId(Integer.parseInt(args[1]));
            AppendOutput("new " + Name(tekton));
        }
        tektonok.put(tekton.getId(), tekton);
        Jatekvezerlo.tektonok.add(tekton);
    }

        private void AddAktor(String[] args) {

            if (args.length < 6) {
                System.out.println("Hasznalat: /adda -i <ID> -n <nev> -f <g|r>");
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
                System.out.println("Ismeretlen tipus: " + args[6]);
            }
        }


    private void AddSpora(String[] args) {
        Gombasz gombasz = null;
        Spora spora = null;
        boolean normal = true;
        int db = 5;
        int tp = 5;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-a")) {
                gombasz = gombaszok.get(Integer.parseInt(args[i + 1]));
            }

            if (args[i].equals("-t")) {
                normal = false;
                switch (args[i + 1]) {
                    case "gyrs":
                        spora = new GyorsitoSpora(tp, db, gombasz);
                        AppendOutput("new GyorsitoSpora (" + args[1] + ")");
                        break;
                    case "ls":
                        spora = new LassitoSpora(tp, db, gombasz);
                        AppendOutput("new LassitoSpora (" + args[1] + ")");
                        break;
                    case "bnt":
                        spora = new BenitoSpora(tp, db, gombasz);
                        AppendOutput("new BenitoSpora (" + args[1] + ")");
                        break;
                    case "csrb":
                        spora = new CsorbitoSpora(tp, db, gombasz);
                        AppendOutput("new CsorbitoSpora (" + args[1] + ")");
                        break;
                    case "oszt":
                        spora = new OsztoSpora(tp, db, gombasz);
                        AppendOutput("new OsztodoSpora (" + args[1] + ")");
                        break;
                }
                spora.setId(Integer.parseInt(args[1]));
            }

            if (args[i].equals("-db")) {
                db = Integer.parseInt(args[i + 1]);
            }

            if (args[i].equals("-tp")) {
                tp = Integer.parseInt(args[i + 1]);
            }

        }

        if (normal) {
            spora = new Spora(tp, db, gombasz);
            spora.setId(Integer.parseInt(args[1]));
            AppendOutput("new " + Name(spora));
        }

        spora.setDarabszam(db);
        spora.setTapanyag(tp);


        sporak.put(spora.getId(), spora);

    }

    private void AddGombatest(String[] args){
        int sporak = 0;
        int elettartam = 0;
        int fejlettseg = 0;
        boolean fejlett = false;
        Tekton hely = null;
        Gombasz gombasz = null;
        Integer tektonId = 0;

        int ID = Integer.parseInt(args[1]);
        AppendOutput("new GombaTest ("+ ID +")");

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-a":
                    int aktorID = Integer.parseInt(args[i + 1]);
                    gombasz = gombaszok.get(aktorID);
                    break;
                case "-tart":
                    tektonId = Integer.parseInt(args[i + 1]);
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
                    if (args[i + 1].equals("Y")) fejlett = true;
                    break;
            }
        }

        try {
            GombaTest gombatest = new GombaTest(gombasz, sporak, elettartam, fejlett, fejlettseg, hely);
            gombatest.setId(ID);
            gombaTestek.put(ID, gombatest);
            AppendOutput("GombaTest ("+ ID +") tartozkodik: [ ] --> [Tekton(" + tektonId +")]");

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

            Tekton t2 = tektonok.get(tId2);
            List<GombaFonal> ujvezet2 = t2.getFonalak();
            List<GombaFonal> vezet2 = new ArrayList<>(ujvezet2);
            ujvezet2.add(gf);

            gf.addVezet(t1, t2);
            AppendOutput(Name(t1)+" vezet: "+ListToString(vezet)+" --> "+ListToString(ujvezet));
            AppendOutput(Name(t2)+" vezet: "+ListToString(vezet2)+" --> "+ListToString(ujvezet2));

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
        AppendOutput("new Rovar (" + args[1] + ")");

        rovar.setId(Integer.parseInt(args[1]));
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
        if (rovar == null) {
            if (rovar == null) {
                AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Nincs rovar)");
                return;
            }
        }

        int oldValue = rovar.getTartozkodik().getId();
        int tektonID = 0;

        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("-tk")){
                tektonID = Integer.parseInt(args[i + 1]);
            }
        }

        rovar.mozog(tektonok.get(tektonID));

        if (oldValue == rovar.getTartozkodik().getId()) {
            AppendOutput("\nINSTRUCTION FAIL \"" + String.join(" ", args) + "\" (Nincs osszekottetes)");
            return;
        }

        AppendOutput("\nEVENT Rovar mozog\nRovar (" + args[1] + ") mozog: Tekton (" + oldValue + ") --> Tekton (" + rovar.getTartozkodik().getId() + ")");
    }

    private void GrowFonal(String[] args) {

        GombaTest gt = gombaTestek.get(Integer.parseInt(args[1]));
        if (gt == null) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (GombaTest nem letezik)");
            return;
        }

        Tekton gtt = gt.getTartozkodik();

        int gombaszID = gt.getGombasz().getId();

        String[] tektonokIds = args[3].split(";");
        int tId1 = Integer.parseInt(tektonokIds[0]);
        int tId2 = Integer.parseInt(tektonokIds[1]);

        Tekton innen = tektonok.get(tId1);
        Tekton ide = tektonok.get(tId2);
        if (innen == null || ide == null) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Tekton nem letezik)");
            return;
        }

        boolean innenEgy = innen.getClass() == EgyetlenFonalTekton.class;
        boolean ideEgy = ide.getClass() == EgyetlenFonalTekton.class;

        if (innenEgy){
            if (!innen.getFonalak().isEmpty()){
                AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (EgyetlenfonalTektonon már van fonal)");
                return;
            }
        }
        if (ideEgy){
            if (!ide.getFonalak().isEmpty()){
                AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (EgyetlenfonalTektonon már van fonal)");
                return;
            }
        }

        if (!gtt.getSzomszed(1).contains(ide)) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Tektonok nem szomszedosak)");
        } else {
            AppendOutput("\nEVENT Fonal athidal");

            int gfId = gombaFonalak.size() + 1;
            AddGombafonal(new String[]{"/addgf", String.valueOf(gfId), "-a", String.valueOf(gombaszID),"-vez", tektonokIds[0]+";"+tektonokIds[1]});

            gombaFonalak.get(gfId).addVezet(innen, ide);
        }
    }

    private void Eats(String[] args) {

        Rovar rovar = rovarok.get(Integer.parseInt(args[1]));
        Spora spora;

        try {
            spora = rovar.getTartozkodik().getBestSpora();
        } catch (Exception e) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Nincs spora)");
            return;
        }
        if (spora == null) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Nincs spora)");
            return;
        }


        Rovarasz rovarasz = rovar.getRovarasz();
        int db = 0;
        int sporaID = 0;

        int oldPont = rovarasz.getPontok();
        int oldSeb = rovar.getSebesseg();
        boolean plVaghat = rovar.getVaghat();

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-sp")) {
                spora = sporak.get(Integer.parseInt(args[i + 1]));
                sporaID = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-db")) {
                db = Integer.parseInt(args[i + 1]);
            }
        }

        if (spora == null) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Nincs spora)");
            return;
        }
        int oldValue = spora.getDarabszam();

        rovar.eszik(spora);
        AppendOutput("\nEVENT Spora eves\n" + Name(spora) + " darabszam " + oldValue + " --> " + spora.getDarabszam() + "\nRovarasz (" + rovarasz.getId() + ") pontok: " + oldPont + " --> " + rovarasz.getPontok());

        if (spora instanceof LassitoSpora || spora instanceof GyorsitoSpora) {
            AppendOutput(Name(rovar) + " sebesseg: " + oldSeb + " --> " + rovar.getSebesseg());
        }
        if (spora instanceof BenitoSpora) {
            AppendOutput(Name(rovar) + " sebesseg: " + oldSeb + " --> " + rovar.getSebesseg() + "\n" + Name(rovar) + " vaghat: " + plVaghat + " --> " + rovar.getVaghat());
        }
        if (spora instanceof CsorbitoSpora) {
            AppendOutput(Name(rovar) + " vaghat: " + plVaghat + " --> " + rovar.getVaghat());
        }
        if (spora instanceof OsztoSpora) {
            int id = rovar.getId() + 1;
            AddRovar(new String[]{"/addrov", "" + id + "", "-a", "1", "-tk", "1", "-seb", "5", "-vag", "Y"});
        }


    }

    private void CutFonal(String[] args) {
        int rovarID = Integer.parseInt(args[1]);
        int gombafonalID = -1;
        int tektonfeleID = -1;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-if")) {
                gombafonalID = Integer.parseInt(args[i + 1]);
            }
            if (args[i].equals("-tk")) {
                tektonfeleID = Integer.parseInt(args[i + 1]);
            }
        }

        GombaFonal gombaFonal = gombaFonalak.get(gombafonalID);
        Tekton tekton = tektonok.get(tektonfeleID);
        Rovar rovar = rovarok.get(rovarID);

        if (rovar == null) {

                AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Nincs rovar)");
                return;

        }

        if (rovar.getVaghat()) {
            List<String> vezetElotte = new ArrayList<>();
            // Tektonok rendezese, hogy biztosan az ID alapjan helyes sorrendben jelenjenek meg
            List<Tekton> tektonokSorted = new ArrayList<>(tektonok.values());
            tektonokSorted.sort(Comparator.comparingInt(Tekton::getId));

            for (Tekton honnan : tektonokSorted) {
                for (Tekton hova : tektonokSorted) {
                    if (honnan.getId() < hova.getId() && gombaFonal.getVezet(honnan, hova)) {
                        vezetElotte.add("Tekton (" + honnan.getId() + "), Tekton (" + hova.getId() + ")");
                    }
                }
            }

            rovar.vag(gombaFonal, tekton);

            AppendOutput("\nEVENT Rovar vag");

            // Kiirjuk a GombaFonal vezetesi kapcsolatokat
            if (vezetElotte.isEmpty()) {
                AppendOutput("GombaFonal (" + gombafonalID + ") vezet: [] --> [ ]");
            } else {
                AppendOutput("GombaFonal (" + gombafonalID + ") vezet: [{" + String.join("}, {", vezetElotte) + "}] --> [ ]");
            }

            // A Tektonok sorrendje forditottan: eloszor a Tekton (2), majd a Tekton (1)
            for (int i = tektonokSorted.size() - 1; i >= 0; i--) {
                Tekton t = tektonokSorted.get(i);
                if (t.getFonalak().contains(gombaFonal) && t != tekton) {
                    // A Tektonokat most mar forditott sorrendben irjuk ki
                    AppendOutput("Tekton (" + t.getId() + ") vezet: [GombaFonal (" + gombafonalID + ")] --> [ ]");
                }
            }

            // Az alap tekton kiirasa
            AppendOutput("Tekton (" + tekton.getId() + ") vezet: [GombaFonal (" + gombafonalID + ")] --> [ ]");

        } else {
            AppendOutput("\nINSTRUCTION FAIL " + String.join(" ", args) + " (A rovar nem vaghat)\n");
        }
    }

    public void RunTests() {
        File in = expected;
        Scanner scanner;
        if (fromFile) {
            try {
                scanner = new Scanner(new FileReader(in));
            } catch (FileNotFoundException e) {
                System.out.println("Tesztfajl megnyitasa sikertelen " + input.getAbsolutePath());
                return;
            }
        } else {
            scanner = new Scanner(System.in);
        }
        while (scanner.hasNextLine()) {
            AppendOutput(scanner.nextLine());
        }
    }

    //Elegge gatya sok mindent kell meg benne csinalni
    private void SporaSzoras(String[] args) {

        GombaTest gombatest = null;
        Tekton tekton = null;
        Spora spora = null;
        int TektonID = 0;
        int GombatestID = 0;
        int GTOldValue = 0;
        int type = 5;


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
            if (args[i].equals("-type")) {
                switch (args[i + 1]) {
                    case "bnt" : type = 0; break;
                    case "gyrs" : type = 1; break;
                    case "ls" : type = 2; break;
                    case "oszt" : type = 3; break;
                    case "csrb" : type = 4; break;
                }
            }

        }
        int oldElet = gombatest.getElettartam();



        if (!gombatest.sporatSzor(tekton, type)) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Nem szomszedos tekton)");
            return;
        }

        if (tekton == gombatest.getTartozkodik()) {
            AppendOutput("\nINSTRUCTION FAIL \""+String.join(" ", args)+"\" (Sajat magan van)");
            return;
        }

        AppendOutput("\nEVENT Sporazas");


        AddSpora(new String[]{"/addsp", "0", "-tk", "" + TektonID, "-a", "" + gombatest.getGombasz().getId()});
        AppendOutput("" + Name(tekton) + " sporak: [] --> " + ListToString(tekton.getSporak()));

        if(gombatest.getElettartam() > 0) {
            AppendOutput(Name(gombatest) + " elettartam: " + oldElet + " --> " + gombatest.getElettartam());
        }else{
            AppendOutput("remove Gombatest(" + GombatestID + ")");
        }

    }

    private void Hasadas(String[] args) {

        Tekton tekton = tektonok.get(Integer.parseInt(args[1]));

        if (tekton.getFoglalt()) {
            AppendOutput("\nINSTRUCTION FAIL \"" +String.join(" ", args)+ "\" (Nem tud hasadni, mert Gombatest van rajta)");
            return;
        }

        tekton.hasad();
        AppendOutput("\nEVENT Tekton hasad");

        AppendOutput("remove " + Name(tekton));

        int tId1 = tektonok.size() + 1;
        int tId2 = tektonok.size() + 2;
        AddTekton(new String[] {"/addtk", String.valueOf(tId1)});
        AddTekton(new String[] {"/addtk", String.valueOf(tId2), "-nei", String.valueOf(tId1)});
        AppendOutput(Name(tektonok.get(tId2)) + " szomszedok: [ ] --> " + ListToString(tektonok.get(tId1).getSzomszed(1)));

        tektonok.remove(Integer.parseInt(args[1]));
    }

    private void OnTekton(String[] args) {}

    private void Print(String[] args) {
        if (args.length < 2) {
            System.out.println("Hiba: Nem adtal meg fajlnevet!");
            return;
        }

        String fileName = args[1];

        try {
            // Itt feltetelezem, hogy van egy StringBuilder output, amiben az eddigi konzolkimenetet tarolod.
            File file = new File(fileName);
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.print(out.toString()); // Vagy amit hasznalsz a kimenet tarolasara
            }
            System.out.println("Kimenet fajlba irva: " + fileName);
        } catch (Exception e) {
            System.out.println("Hiba tortent a fajliras soran: " + e.getMessage());
        }
    }

    private void ListGombafonal(String[] args) {

        for (GombaFonal f : gombaFonalak.values()){

            StringBuilder sb = new StringBuilder();
            sb.append("[ ");
            for (Tekton o : f.getVezet().keySet()) {
                sb.append("{ ").append(Name(o))
                        .append(", ").append(Name(f.getVezet().get(o).get(0)))
                        .append(" }")
                        .append(", ");
            }
            if (!f.getVezet().isEmpty()) {
                sb.replace(sb.length()-2, sb.length(), " ]");
            } else
                sb.append("]");

            AppendOutput(Name(f) + " vezet: "+ sb.toString());
        }
    }

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

            //outputba
            AppendOutput(Name(tekton) + " vezet: "+ListToString(tekton.getFonalak()));

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
        String file1 = expected.getAbsolutePath();
        String file2 = out.getAbsolutePath();

        System.out.println(System.lineSeparator()+"Kimenet ellenorzese...");
        //FC
        List<String> command = List.of("cmd.exe", "/c", "fc", file1, file2);
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        try {
            Process process = processBuilder.start();

            // Read standard output
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
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
            sb.replace(sb.length()-2, sb.length(), " ]");
        } else
            sb.append("]");


        return sb.toString();
    }


}
