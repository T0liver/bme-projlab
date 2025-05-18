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

    public static final int CELL_SIZE = 15;
    public static final int GRID_SIZE = 50;

    private int screenWidth = 960;
    private int screenHeight = 740;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel gamePanel;
    private JPanel playerMenuPanel;
    private Jatekvezerlo jatekvezerlo;
    private JatekvezerloView jatekvezerloView;
    //private Jatekos JelenlegiJatekos;

    private JatekosMenu jelenlegiJatekosMenu = new JatekosMenu(new Gombasz());
    private JatekosMenuView jelenlegiJatekosMenuView = new JatekosMenuView(jelenlegiJatekosMenu);
    private List<EntitasView> entitasok = new ArrayList<>();
    private TerkepView terkepView;

    
    private int gombaszok = 2;
    private int rovaraszok = 2;
    private JLabel gombaszokLabel;
    private JLabel rovaraszokLabel;

    /**
     * Konstruktor, amely inicializálja az ablakot és a főmenüt.
     */
    public GameWindow() {
        setTitle("Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        jatekvezerlo = new Jatekvezerlo();
        jatekvezerlo.setGameWindow(this);
        jatekvezerloView = new JatekvezerloView(jatekvezerlo, this);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        gamePanel = new JPanel();
        terkepView = new TerkepView(jatekvezerlo.getTerkep());

        playerMenuPanel = new JPanel();
        playerMenuPanel.setOpaque(true);
        playerMenuPanel.setBackground(Color.WHITE);
        playerMenuPanel.setBounds(723, 8,  210, 704);

        mainPanel.add(gamePanel, "Game");

        add(mainPanel, BorderLayout.CENTER);
        cardLayout.show(mainPanel, "MainMenu");

        setVisible(true);
    }

    public void altMenu() {
        Container content = getContentPane();
        content.removeAll();
        setSize(960, 760);
        content.setLayout(null);

        // Cím
        JLabel title = new JLabel("FUNGORIUM", SwingConstants.CENTER);
        title.setFont(new Font("Rockwell", Font.BOLD, 36));
        title.setBounds(0, 50, 960, 50);
        content.add(title);

        // GOMBÁSZOK beállítás
        gombaszokLabel = new JLabel("GOMBÁSZOK: " + gombaszok);
        gombaszokLabel.setFont(new Font("Rockwell", Font.BOLD, 24));
        gombaszokLabel.setBounds(320, 150, 300, 30);
        content.add(gombaszokLabel);

        JButton gombaszokMinus = new JButton("-");
        gombaszokMinus.setBounds(260, 150, 50, 30);
        gombaszokMinus.addActionListener(e -> {
            if (gombaszok > 2) {
                gombaszok--;
                updateLabels();
            }
        });
        content.add(gombaszokMinus);

        JButton gombaszokPlus = new JButton("+");
        gombaszokPlus.setBounds(630, 150, 50, 30);
        gombaszokPlus.addActionListener(e -> {
            if (gombaszok < 5) {
                gombaszok++;
                updateLabels();
            }
        });
        content.add(gombaszokPlus);

        // ROVARÁSZOK beállítás
        rovaraszokLabel = new JLabel("ROVARÁSZOK: " + rovaraszok);
        rovaraszokLabel.setFont(new Font("Rockwell", Font.BOLD, 24));
        rovaraszokLabel.setBounds(320, 200, 300, 30);
        content.add(rovaraszokLabel);

        JButton rovaraszokMinus = new JButton("-");
        rovaraszokMinus.setBounds(260, 200, 50, 30);
        rovaraszokMinus.addActionListener(e -> {
            if (rovaraszok > 2) {
                rovaraszok--;
                updateLabels();
            }
        });
        content.add(rovaraszokMinus);

        JButton rovaraszokPlus = new JButton("+");
        rovaraszokPlus.setBounds(630, 200, 50, 30);
        rovaraszokPlus.addActionListener(e -> {
            if (rovaraszok < 5) {
                rovaraszok++;
                updateLabels();
            }
        });
        content.add(rovaraszokPlus);

        // Menü gombok
        JButton startGame = new JButton("JÁTÉK KEZDÉSE");
        startGame.setFont(new Font("Rockwell", Font.BOLD, 20));
        startGame.setBounds(330, 280, 300, 40);
        startGame.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gwClearAll();
                jatekvezerlo.jatekKezdes(gombaszok, rovaraszok);
                jatekvezerloView = new JatekvezerloView(jatekvezerlo, GameWindow.this);
                drawJatekvezerlo();
            }
        });
        content.add(startGame);

        JButton loadGame = new JButton("BETÖLTÉS");
        loadGame.setFont(new Font("Rockwell", Font.BOLD, 20));
        loadGame.setBounds(330, 340, 300, 40);
        //ezt fentről másoltam ki, nem fogok gameboardot használni, kevesebb, mint felesleges
        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File mentesiMappa = new File("saves");

                // Ha a mappa nem létezik vagy üres
                if (!mentesiMappa.exists() || !mentesiMappa.isDirectory()) {
                    JOptionPane.showMessageDialog(GameWindow.this, "Nincs elérhető mentés.", "Hiba", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Csak .dat fájlok listázása
                File[] mentettFajlok = mentesiMappa.listFiles((dir, name) -> name.endsWith(".dat"));

                if (mentettFajlok == null || mentettFajlok.length == 0) {
                    JOptionPane.showMessageDialog(GameWindow.this, "Nem található mentés.", "Hiba", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Fájlnevek kilistázása
                String[] fajlNevek = Arrays.stream(mentettFajlok)
                        .map(File::getName)
                        .toArray(String[]::new);

                // Megjelenítjük a választó ablakot
                String kivalasztottFajl = (String) JOptionPane.showInputDialog(
                        GameWindow.this,
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

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(GameWindow.this,
                            "Hiba történt a játék betöltése közben: " + ex.getMessage(),
                            "Hiba",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        content.add(loadGame);

        JButton exit = new JButton("KILÉPÉS");
        exit.setFont(new Font("Rockwell", Font.BOLD, 20));
        exit.setBounds(330, 400, 300, 40);
        exit.addActionListener(e -> System.exit(0));
        content.add(exit);

        content.revalidate();
        content.repaint();
    }

    private void updateLabels() {
        gombaszokLabel.setText("GOMBÁSZOK: " + gombaszok);
        rovaraszokLabel.setText("ROVARÁSZOK: " + rovaraszok);
    }

    public void drawJatekvezerlo() {
        getContentPane().removeAll();
        jatekvezerloView.draw(this);
        jelenlegiJatekosMenu = new JatekosMenu(jatekvezerlo.getSoronLevoJatekos());
        jelenlegiJatekosMenuView.setJatekVezerloView(jatekvezerloView);
        jelenlegiJatekosMenuView.draw(playerMenuPanel);
        add(playerMenuPanel);
        revalidate();
    }

    private void gwClearAll() {
        this.getContentPane().removeAll();
    }
}

