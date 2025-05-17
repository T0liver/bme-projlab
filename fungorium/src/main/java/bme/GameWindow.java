package bme;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class GameWindow extends JFrame {

    public static final boolean DEBUG = true;

    public static final int CELL_SIZE = 15;
    public static final int GRID_SIZE = 50;

    private int screenWidth = 960;
    private int screenHeight = 720;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel gamePanel;
    private JPanel playerMenuPanel;
    private Jatekvezerlo jatekvezerlo;
    private JatekvezerloView jatekvezerloView;
    //private Jatekos JelenlegiJatekos;

    private JatekosMenu jelenlegiJatekosMenu;
    private JatekosMenuView jelenlegiJatekosMenuView;
    private List<EntitasView> entitasok = new ArrayList<>();
    private TerkepView terkepView;

    /**
     * Konstruktor, amely inicializálja az ablakot és a főmenüt.
     */


    public GameWindow() {


        setTitle("Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        jatekvezerlo = new Jatekvezerlo();
        jatekvezerloView = new JatekvezerloView(jatekvezerlo);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        gamePanel = new JPanel();

        MainMenu mainMenu = new MainMenu(this, jatekvezerlo.getJatekosok());


        mainPanel.add(mainMenu, "MainMenu");
        mainPanel.add(gamePanel, "Game");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "MainMenu");



        //jelenlegiJatekosMenu = new JatekosMenu(null);
        //jelenlegiJatekosMenuView = new JatekosMenuView(jelenlegiJatekosMenu);

        setVisible(true);
    }

    //public Jatekos getJelenlegiJatekos() {return JelenlegiJatekos; }

    /**
     * Menü kirajzolása (jövőbeli implementációhoz).
     */
    private void renderMenu() {
        // TODO: Menü kirajzolása
    }

    /**
     * Játék kirajzolása (jövőbeli implementációhoz).
     */
    private void renderGame() {

    }

    /**
     * Egy entitás (pl. karakter vagy objektum) megjelenítése a játékban.
     *
     * @param entitasView az entitás nézete, amelyet meg kell jeleníteni
     */
    public void drawSprite(EntitasView entitasView, Graphics g) {

        entitasView.draw(this, g);

        BufferedImage image = entitasView.getKinezet();
        int x = (entitasView.mezo.getPos().get(0)) * CELL_SIZE;
        int y = (entitasView.mezo.getPos().get(1)) * CELL_SIZE;

        if (image != null) {
            g.drawImage(image, x, y, CELL_SIZE, CELL_SIZE, null);
        }
    }

    /**
     * A térkép (játékterület) kirajzolása.
     */
    public void drawTerkep() {
        // TODO: Térkép kirajzolása
    }

    /**
     * A játékpanel létrehozása és konfigurálása, beleértve a rács és a kezelők beállítását.
     *
     * @param players A játékban résztvevő játékosok listája
     */
    private void createGamePanel(List<Jatekos> players) {


        final Color[][] gridColors = new Color[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridColors[i][j] = Color.LIGHT_GRAY;
            }
        }

        gamePanel = new JPanel(null);
        gamePanel.setPreferredSize(new Dimension(screenWidth, screenHeight));

        JPanel gridPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (int i = 0; i < GRID_SIZE; i++) {
                    for (int j = 0; j < GRID_SIZE; j++) {
                        g.setColor(gridColors[i][j]);
                        g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        };

        gridPanel.setBounds(0, 0, GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        gridPanel.setLayout(null);

        // Egérkattintás kezelése
        gridPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;
                if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                    gridPanel.repaint();
                    System.out.println("Kattintás a [" + row + "][" + col + "] cellára.");
                }
            }
        });

        // Billentyűzet események kezelése
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        // Vissza gomb a főmenübe
        JButton backToMainMenuButton = new JButton("Vissza a főmenübe");
        backToMainMenuButton.setBounds(10, 10, 150, 30);
        backToMainMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        gamePanel.add(gridPanel);
        gamePanel.add(backToMainMenuButton);
        mainPanel.add(gamePanel, "Game");
    }

    /**
     * Játékosválasztó menü létrehozása.
     */
    private void createPlayerMenu() {
        playerMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
    }

    /**
     * Belső osztály, amely külön ablakban jelenít meg egy játékmezőt.
     */
    class GameBoard extends JFrame {
        private static final int GRID_SIZE = 50;

        private List<Jatekos> players;
        private JPanel gridPanel;
        private Color[][] gridColors;

        /**
         * Konstruktor, amely létrehozza a játékmezőt.
         *
         * @param players A játékosok listája
         */
        public GameBoard(List<Jatekos> players) {
            this.players = players;
            setTitle("Fungorium - Játék");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
            setLocationRelativeTo(null);

            gridColors = new Color[GRID_SIZE][GRID_SIZE];
            for (int i = 0; i < GRID_SIZE; i++) {
                for (int j = 0; j < GRID_SIZE; j++) {
                    gridColors[i][j] = Color.LIGHT_GRAY;
                }
            }



            gridPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    for (int i = 0; i < GRID_SIZE; i++) {
                        for (int j = 0; j < GRID_SIZE; j++) {
                            g.setColor(gridColors[i][j]);
                            g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                            g.setColor(Color.BLACK);
                            g.drawRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        }
                    }
                    //EntitasView e = new GombaTestView(null, new Mezo(1,1), null);


                    if (entitasok != null) {
                        for (EntitasView e : entitasok) {
                            drawSprite(e, g);
                        }
                    }




                }
            };

            gridPanel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
            gridPanel.setLayout(null);

            gridPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = e.getY() / CELL_SIZE;
                    int col = e.getX() / CELL_SIZE;
                    if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                        //gridColors[row][col] = Color.YELLOW;

                        entitasok.add(new RovarView(null, new Mezo(col, row), jatekvezerlo.getSoronLevoJatekos()));
                        try {
                            Rovar rov = new Rovar((Rovarasz) jatekvezerlo.getSoronLevoJatekos(), new Mezo(col, row));
                            jatekvezerlo.getSoronLevoJatekos().addRovar(rov);

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }



                        gridPanel.repaint();
                        System.out.println("Kattintás a [" + row + "][" + col + "] cellára.");
                    }
                }
            });

            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    char keyChar = e.getKeyChar();
                    System.out.println("Billentyű lenyomva: " + keyChar);
                    if (keyChar == 'r') {
                        for (int i = 0; i < GRID_SIZE; i++) {
                            for (int j = 0; j < GRID_SIZE; j++) {
                                gridColors[i][j] = Color.LIGHT_GRAY;
                            }
                        }
                        gridPanel.repaint();
                    }
                }
            });

            setFocusable(true);
            requestFocusInWindow();

            add(gridPanel);
            pack();
            setVisible(true);
        }
    }















    /**
     * A játék főmenüjét megjelenítő panel.
     * Itt lehet új játékosokat hozzáadni, eltávolítani, játékot indítani vagy mentést betölteni.
     */
    class MainMenu extends JPanel {
        /** Gombász hozzáadására szolgáló gomb. */
        private JButton addGombaszButton;

        /** Rovarász hozzáadására szolgáló gomb. */
        private JButton addRovaraszButton;

        /** Korábbi játék betöltésére szolgáló gomb. */
        private JButton loadGameButton;

        /** Alkalmazás bezárására szolgáló gomb. */
        private JButton exitButton;

        /** Játék indítására szolgáló gomb. */
        private JButton startGameButton;

        /** A gombász játékosokat tartalmazó lista modellje. */
        private DefaultListModel<PlayerDisplayData> gombaszListModel;

        /** A gombász játékosokat megjelenítő lista. */
        private JList<PlayerDisplayData> gombaszList;

        /** Gombász eltávolítására szolgáló gomb. */
        private JButton removeGombaszButton;

        /** A rovarász játékosokat tartalmazó lista modellje. */
        private DefaultListModel<PlayerDisplayData> rovaraszListModel;

        /** A rovarász játékosokat megjelenítő lista. */
        private JList<PlayerDisplayData> rovaraszList;

        /** Rovarász eltávolítására szolgáló gomb. */
        private JButton removeRovaraszButton;

        /** A játékosokat tároló lista. */
        public List<Jatekos> players;

        /** A szülő ablak, amely tartalmazza ezt a menüt. */
        private GameWindow parent;

        /** A főcím felirat. */
        private JLabel titleLabel;

        /** Háttérkép a menühöz. */
        private Image backgroundImage;



        /**
         * Konstruktor, amely létrehozza és inicializálja a főmenü komponenseit.
         *
         * @param parent  A szülő ablak, amely ezt a menüt tartalmazza.
         * @param players A játékosok listája.
         */
        public MainMenu(GameWindow parent, List<Jatekos> players) {
            this.parent = parent;
            this.players = players;

            // Elrendezés beállítása
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 0.5;
            gbc.weighty = 0.5;


            // Háttérkép betöltése
            loadBackgroundImage();


            // Betűtípusok beállítása
            Font titleFont = new Font("Rockwell", Font.BOLD, 48); // Nagyobb cím
            Font buttonFont = new Font("Rockwell", Font.BOLD, 16);

            // Cím létrehozása
            titleLabel = new JLabel("FUNGÓRIUM", SwingConstants.CENTER);
            titleLabel.setFont(titleFont);

            // Gombok létrehozása
            JButton gombaszButton = new JButton("Gombász");
            gombaszButton.setFont(buttonFont);
            JButton rovaraszButton = new JButton("Rovarász");
            rovaraszButton.setFont(buttonFont);
            loadGameButton = new JButton("Betöltés");
            loadGameButton.setFont(buttonFont);
            exitButton = new JButton("Kilépés");
            exitButton.setFont(buttonFont);
            startGameButton = new JButton("Játék indítása");
            startGameButton.setFont(buttonFont);



            // Listamodellek és listák létrehozása
            gombaszListModel = new DefaultListModel<>();
            gombaszList = new JList<>(gombaszListModel);
            JScrollPane gombaszListScrollPane = new JScrollPane(gombaszList);
            gombaszListScrollPane.setPreferredSize(new Dimension(150, 80)); // Kisebb magasság

            rovaraszListModel = new DefaultListModel<>();
            rovaraszList = new JList<>(rovaraszListModel);
            JScrollPane rovaraszListScrollPane = new JScrollPane(rovaraszList);
            rovaraszListScrollPane.setPreferredSize(new Dimension(150, 80)); // Kisebb magasság

            // Gombok hozzáadása/eltávolítása
            removeGombaszButton = new JButton("-");
            removeGombaszButton.setPreferredSize(new Dimension(50, 50));
            addGombaszButton = new JButton("+");
            addGombaszButton.setPreferredSize(new Dimension(50, 50));
            removeRovaraszButton = new JButton("-");
            removeRovaraszButton.setPreferredSize(new Dimension(50, 50));
            addRovaraszButton = new JButton("+");
            addRovaraszButton.setPreferredSize(new Dimension(50, 50));

            // Vezérlőpanelek létrehozása
            JPanel gombaszControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            gombaszControlPanel.add(removeGombaszButton);
            gombaszControlPanel.add(gombaszButton);
            gombaszControlPanel.add(addGombaszButton);

            JPanel rovaraszControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            rovaraszControlPanel.add(removeRovaraszButton);
            rovaraszControlPanel.add(rovaraszButton);
            rovaraszControlPanel.add(addRovaraszButton);

            // Felirat (felső rész)
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 3;
            gbc.weighty = 0.3; // Nagyobb súly a felső résznek
            add(titleLabel, gbc);

            gbc.weighty = 0.7; // Kisebb súly a többi résznek
            gbc.gridy = 1;
            gbc.insets = new Insets(5, 5, 5, 5);

            // Bal oldal: Gombászok
            gbc.gridx = 0;
            gbc.gridwidth = 1;
            JLabel gombaszLabel  = new JLabel("Gombászok", SwingConstants.CENTER);
            gombaszLabel.setFont(buttonFont);
            add(gombaszLabel, gbc);
            gbc.gridy = 2;
            add(gombaszListScrollPane, gbc);

            // Középen: Gombok
            gbc.gridx = 1;
            gbc.gridy = 1;
            add(gombaszControlPanel, gbc);
            gbc.gridy = 2;
            add(rovaraszControlPanel, gbc);
            gbc.gridy = 3;
            add(loadGameButton, gbc);
            gbc.gridy = 4;
            add(startGameButton, gbc);
            gbc.gridy = 5;
            add(exitButton, gbc);

            // Jobb oldal: Rovarászok
            gbc.gridx = 2;
            gbc.gridy = 1;
            JLabel rovaraszokLabel = new JLabel("Rovarászok", SwingConstants.CENTER);
            rovaraszokLabel.setFont(buttonFont);
            add(rovaraszokLabel, gbc);
            gbc.gridy = 2;
            add(rovaraszListScrollPane, gbc);

            updatePlayerLists();

            /**
            * Gombász hozzáadása gombra kattintás eseménykezelője.
            * Bekéri a felhasználótól a játékos nevét, létrehoz egy új Gombasz példányt véletlenszerű színnel,
            * majd hozzáadja a játékosok listájához, és frissíti a megjelenített listát.
            */
            addGombaszButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String playerName = JOptionPane.showInputDialog(MainMenu.this, "Gombász neve:", "Új Gombász", JOptionPane.PLAIN_MESSAGE);
                    if (playerName != null && !playerName.trim().isEmpty()) {
                        Color color = generateRandomColor();
                        players.add(new Gombasz(playerName, color));
                        updatePlayerLists();
                    }
                }
            });

            /**
            * Rovarász hozzáadása gombra kattintás eseménykezelője.
            * Bekéri a felhasználótól a játékos nevét, létrehoz egy új Rovarasz példányt véletlenszerű színnel,
            * majd hozzáadja a játékosok listájához, és frissíti a megjelenített listát.
            */
            addRovaraszButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String playerName = JOptionPane.showInputDialog(MainMenu.this, "Rovarász neve:", "Új Rovarasz", JOptionPane.PLAIN_MESSAGE);
                    if (playerName != null && !playerName.trim().isEmpty()) {
                        Color color = generateRandomColor();
                        players.add(new Rovarasz(playerName, color));
                        updatePlayerLists();
                    }
                }
            });

            /**
            * Gombász eltávolítása gombra kattintás eseménykezelője.
            * Megkeresi az első Gombasz típusú játékost, eltávolítja a listából, majd frissíti a listanézetet.
            */
            removeGombaszButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Jatekos p : players) {
                        if (p instanceof Gombasz) {
                            players.remove(p);
                            break;
                        }
                    }
                    updatePlayerLists();
                }
            });

            /**
             * Rovarász eltávolítása gombra kattintás eseménykezelője.
             * Megkeresi az első Rovarasz típusú játékost, eltávolítja a listából, majd frissíti a listanézetet.
             */
            removeRovaraszButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (Jatekos p : players) {
                        if (p instanceof Rovarasz) {
                            players.remove(p);
                            break;
                        }
                    }
                    updatePlayerLists();
                }
            });

            /**
            * Mentés betöltése gombra kattintás eseménykezelője.
            * Megnyitja a mentések mappát, listázza a .dat kiterjesztésű fájlokat, lehetővé teszi a kiválasztást,
            * majd betölti a kiválasztott mentést. Sikeres betöltés esetén megnyitja a játéktáblát.
            */
            loadGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    File mentesiMappa = new File("saves");

                    // Ha a mappa nem létezik vagy üres
                    if (!mentesiMappa.exists() || !mentesiMappa.isDirectory()) {
                        JOptionPane.showMessageDialog(MainMenu.this, "Nincs elérhető mentés.", "Hiba", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Csak .dat fájlok listázása
                    File[] mentettFajlok = mentesiMappa.listFiles((dir, name) -> name.endsWith(".dat"));

                    if (mentettFajlok == null || mentettFajlok.length == 0) {
                        JOptionPane.showMessageDialog(MainMenu.this, "Nem található mentés.", "Hiba", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Fájlnevek kilistázása
                    String[] fajlNevek = Arrays.stream(mentettFajlok)
                            .map(File::getName)
                            .toArray(String[]::new);

                    // Megjelenítjük a választó ablakot
                    String kivalasztottFajl = (String) JOptionPane.showInputDialog(
                            MainMenu.this,
                            "Válassz egy mentést:",
                            "Mentés betöltése",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            fajlNevek,
                            fajlNevek[0]);

                    // Ha nem választott semmit (cancel)
                    if (kivalasztottFajl == null) {
                        return;
                    }

                    // Kiválasztott mentés betöltése
                    try {
                        File file = new File("saves", kivalasztottFajl);
                        FileInputStream fis = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        Jatekvezerlo betoltottJatek = (Jatekvezerlo) ois.readObject();
                        ois.close();

                        List<Jatekos> betoltottJatekosok = betoltottJatek.getJatekosok();

                        SwingUtilities.invokeLater(() -> {
                            GameBoard gameBoard = new GameWindow.GameBoard(betoltottJatekosok);
                            gameBoard.setVisible(true);
                        });

                        parent.dispose();

                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(MainMenu.this,
                                "Hiba történt a játék betöltése közben: " + ex.getMessage(),
                                "Hiba",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            /**
             * A játék indítását kezelő eseménykezelő.
            * Létrehozza a játéktáblát (GameBoard), bezárja a főmenüt, majd külön szálon elindítja a játéklogikát.
            * Ha nincs játékos hozzáadva, figyelmeztetést jelenít meg.
            */
            startGameButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!players.isEmpty() || DEBUG) {
                        try {
                            // Először létrehozzuk és megjelenítjük a GameBoard-ot
                            SwingUtilities.invokeLater(() -> {
                                GameBoard gameBoard = new GameWindow.GameBoard(players);
                                gameBoard.setVisible(true);
                                parent.dispose(); // Főablak bezárása
                            });

                            // Külön szálon indítjuk a játéklogikát
                            Thread gameThread = new Thread(() -> {
                                try {
                                    if (DEBUG) {
                                        jatekvezerlo.setJatekosok(new ArrayList<Jatekos>(){
                                            {add(new Rovarasz("debug", Color.RED));
                                            add(new Gombasz("debug", Color.BLUE));}
                                        });
                                    } else {
                                        jatekvezerlo.setJatekosok(players);
                                    }
                                    jatekvezerlo.jatekKezdes();
                                } catch (Exception ex) {
                                    SwingUtilities.invokeLater(() -> {
                                        JOptionPane.showMessageDialog(MainMenu.this,
                                                "Hiba történt a játék futtatásakor: " + ex.getMessage(),
                                                "Hiba",
                                                JOptionPane.ERROR_MESSAGE);
                                    });
                                }
                            });
                            gameThread.start();

                        } catch (Exception ex) {
                            System.err.println("Hiba a játék indításakor: " + ex.getMessage());
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(MainMenu.this,
                                    "Hiba történt a játék indításakor: " + ex.getMessage(),
                                    "Hiba",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else if (!DEBUG) {
                        JOptionPane.showMessageDialog(MainMenu.this,
                                "Kérlek, adj hozzá legalább egy játékost!",
                                "Figyelmeztetés",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            /**
            * Kilépés gombra kattintás eseménykezelője.
            * Bezárja a főablakot (parent.dispose()).
            */
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.dispose(); // bezárja az ablakot
                }
            });

        /**
        * A komponens átlátszóvá tétele.
        */
            setOpaque(false);

            /**
            * Az összes komponens átlátszóvá tétele rekurzívan.
             */
            makeComponentsTransparent(this);




        }


        /**
         * Háttérkép betöltése
         */
        private void loadBackgroundImage() {
            try {
                Random random = new Random();
                int szam = random.nextInt(10) + 2;

                // Ha a kép például a "images" mappában van a projekt gyökerében
                File file = new File("background/output" + szam + ".jpg");
                backgroundImage = ImageIO.read(file);
            } catch (IOException e) {
                System.err.println("Hiba a háttérkép betöltésekor: " + e.getMessage());
            }
        }




        /**
         * Rekurzívan minden komponenst átlátszóvá tesz
         */
        private void makeComponentsTransparent(Container container) {
            // Az aktuális konténer átlátszó legyen
            if (container instanceof JPanel) {
                ((JPanel) container).setOpaque(false);
            }

            // Minden komponens beállítása a konténerben
            for (Component comp : container.getComponents()) {
                // Háttér és szövegszín beállítása
                if (comp instanceof JComponent) {
                    ((JComponent) comp).setOpaque(false);

                    // Gombok beállítása
                    if (comp instanceof JButton) {
                        JButton button = (JButton) comp;
                        button.setBackground(new Color(0, 0, 0, 100));
                        button.setForeground(Color.WHITE);
                    }

                    // Listák beállítása
                    if (comp instanceof JList) {
                        JList<?> list = (JList<?>) comp;
                        list.setBackground(new Color(255, 255, 255, 100));
                        list.setForeground(Color.BLACK);
                    }

                    // ScrollPane beállítása
                    if (comp instanceof JScrollPane) {
                        JScrollPane scrollPane = (JScrollPane) comp;
                        scrollPane.setOpaque(false);
                        scrollPane.getViewport().setOpaque(false);
                    }

                    // Címkék beállítása
                    if (comp instanceof JLabel) {
                        ((JLabel) comp).setForeground(Color.WHITE);
                    }
                }

                // Ha a komponens maga is egy konténer, rekurzívan kezeljük
                if (comp instanceof Container) {
                    makeComponentsTransparent((Container) comp);
                }
            }
        }



        /**
         * Egyéni festés a háttérképpel
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (backgroundImage != null) {
                // Háttérkép méretezése a panel méretéhez
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                // Opcionális: félig átlátszó sötétítés a jobb olvashatóságért
                g.setColor(new Color(0, 0, 0, 128));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }





        /**
         * Frissíti a gombászok és rovarászok listáját a meglévő játékosok alapján.
         * <p>
         * A játékosokat különválasztja típus szerint ({@code Gombasz} vagy {@code Rovarasz}),
         * és a megfelelő {@code DefaultListModel}-hez hozzáadja őket {@code PlayerDisplayData} formájában.
         * Ezután beállítja az egyéni {@code PlayerListCellRenderer}-t a listákhoz,
         * hogy megjelenjen a név és a színes négyzet is.
         * </p>
         */
        private void updatePlayerLists() {
            gombaszListModel.clear();
            rovaraszListModel.clear();
            for (Jatekos player : players) {
                PlayerDisplayData displayData = new PlayerDisplayData(player.getNev(), player.getSzin());
                if (player instanceof Gombasz) {
                    gombaszListModel.addElement(displayData);
                } else if (player instanceof Rovarasz) {
                    rovaraszListModel.addElement(displayData);
                }
            }
            gombaszList.setCellRenderer(new PlayerListCellRenderer());
            rovaraszList.setCellRenderer(new PlayerListCellRenderer());
        }

        /**
         * Véletlenszerű színt generál.
         * <p>
         * A szín mindhárom komponensét (vörös, zöld, kék) 0 és 255 közötti véletlenszámmal állítja be.
         * </p>
         *
         * @return Egy új véletlenszerű {@link Color} objektum.
         */
        private Color generateRandomColor() {
            Random random = new Random();
            return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        /**
         * Egy {@link Color} objektumot hexadecimális színkóddá alakít.
         * <p>
         * A kimenet a HTML-ben is használható formátumot követi (pl. {@code "#ff0000"}).
         * </p>
         *
         * @param color Az átalakítandó {@code Color} objektum.
         * @return A szín hexadecimális reprezentációja stringként.
         */
        private String colorToHexString(Color color) {
            return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
        }

        /**
         * Segédosztály a játékos nevének és színének tárolására a listák megjelenítéséhez.
         * <p>
         * Ez az osztály egyszerű adattárolóként (DTO) szolgál a {@code JList}-ekhez,
         * és megkönnyíti az egyéni megjelenítést a {@link PlayerListCellRenderer} segítségével.
         * </p>
         */
        private static class PlayerDisplayData {
            private String name;
            private Color color;

            /**
             * Létrehoz egy új {@code PlayerDisplayData} példányt a megadott névvel és színnel.
             *
             * @param name  A játékos neve.
             * @param color A játékoshoz tartozó szín.
             */
            public PlayerDisplayData(String name, Color color) {
                this.name = name;
                this.color = color;
            }

            /**
             * Visszaadja a játékos nevét.
             *
             * @return A játékos neve.
             */
            public String getName() {
                return name;
            }

            /**
             * Visszaadja a játékos színét.
             *
             * @return A játékoshoz tartozó {@code Color} objektum.
             */
            public Color getColor() {
                return color;
            }

            /**
             * A játékos nevét adja vissza szöveges reprezentációként.
             *
             * @return A játékos neve.
             */
            @Override
            public String toString() {
                return name;
            }
        }




        /**
         * Egyéni cell renderer, amely lehetővé teszi, hogy egy {@link PlayerDisplayData} objektumot
         * tartalmazó JList elem egy színes négyzettel és a játékos nevével jelenjen meg.
         * <p>
         * Ha az aktuális listaelem {@code PlayerDisplayData} típusú, akkor egy JPanel
         * kerül visszaadásra, amely tartalmazza a játékos nevét és egy színes négyzetet.
         * Ellenkező esetben az alapértelmezett JLabel renderelés kerül használatra.
         * </p>
         *
         * <p>Ez az osztály belső osztályként használható például egy játékoslistához,
         * ahol a játékos színét vizuálisan is meg szeretnénk jeleníteni.</p>
         */
        class PlayerListCellRenderer extends DefaultListCellRenderer {

            /**
             * A megjelenített színnégyzet szélessége és magassága pixelben.
             */
            private static final int COLOR_SQUARE_SIZE = 12;

            /**
             * A belső padding (kitöltés) mérete pixelben.
             * Ez biztosítja a komponensek közti és a panel széléhez viszonyított távolságot.
             */
            private static final int PADDING = 5;

            /**
             * A lista celláinak megjelenítéséhez használt komponens létrehozása és testreszabása.
             *
             * @param list         A {@code JList} komponens, amely megjeleníti az elemeket.
             * @param value        Az aktuálisan megjelenítendő érték.
             * @param index        Az aktuális elem indexe a listában.
             * @param isSelected   Igaz, ha az elem ki van jelölve.
             * @param cellHasFocus Igaz, ha az elem fókuszban van.
             * @return Egy {@code Component} objektum, amely a cella megjelenítéséhez használatos.
             *
             * <p>
             * Ha az {@code value} egy {@link PlayerDisplayData} példány, akkor a visszatérési érték
             * egy {@code JPanel}, amely bal oldalon egy színnégyzetet, jobb oldalon pedig a játékos nevét tartalmazza.
             * Egyébként az alapértelmezett {@code JLabel} kerül visszaadásra.
             * </p>
             */
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(new EmptyBorder(PADDING, PADDING, PADDING, PADDING));
                if (value instanceof PlayerDisplayData) {
                    PlayerDisplayData data = (PlayerDisplayData) value;
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, PADDING, 0));
                    JLabel nameLabel = new JLabel(data.getName());
                    JPanel colorPanel = new JPanel();
                    colorPanel.setPreferredSize(new Dimension(COLOR_SQUARE_SIZE, COLOR_SQUARE_SIZE));
                    colorPanel.setBackground(data.getColor());
                    colorPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panel.add(colorPanel);
                    panel.add(nameLabel);
                    panel.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
                    panel.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
                    return panel;
                }
                return label;
            }
        }


    }
}

