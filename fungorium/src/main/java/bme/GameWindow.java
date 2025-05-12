package bme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameWindow extends JFrame {

    private int screenWidth = 960; // Csökkentett szélesség
    private int screenHeight = 720; // Csökkentett magasság
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel gamePanel;
    private JPanel playerMenuPanel;
    //private List<Jatekos> players = new ArrayList<>(); // A játékosok adatainak tárolására
    private Jatekvezerlo jatekvezerlo;
    private JatekvezerloView jatekvezerloView;
    //JatekosView
    private JatekosMenuView jelenlegiJatekosMenu;
    private List<EntitasView> entitasok;
    private TerkepView terkepView;

    public GameWindow() {


        setTitle("Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setResizable(false);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        MainMenu mainMenu = new MainMenu(this, Jatekvezerlo.jatekosok);
        gamePanel = new JPanel(); // Placeholder a későbbi játékpanelhez

        mainPanel.add(mainMenu, "MainMenu");
        mainPanel.add(gamePanel, "Game"); // Még nincs használatban

        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");

        setVisible(true);
    }


    private void renderMenu() {

    }


    private void renderGame() {

    }

    public void drawSprite(EntitasView entitasView) {

    }

    public void drawTerkep() {


    }



    private void createGamePanel(List<Jatekos> players) {
        final int GRID_SIZE = 50;
        final int CELL_SIZE = 15;
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

        gridPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;
                if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                    gridColors[row][col] = Color.YELLOW;
                    gridPanel.repaint();
                    System.out.println("Kattintás a [" + row + "][" + col + "] cellára.");
                }
            }
        });

        // Keyboard handler (JFrame helyett itt külön kell regisztrálni)
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.addKeyListener(new KeyAdapter() {
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

        JButton backToMainMenuButton = new JButton("Vissza a főmenübe");
        backToMainMenuButton.setBounds(10, 10, 150, 30);
        backToMainMenuButton.addActionListener(e -> cardLayout.show(mainPanel, "MainMenu"));

        gamePanel.add(gridPanel);
        gamePanel.add(backToMainMenuButton);
        mainPanel.add(gamePanel, "Game");
    }







    private void createPlayerMenu() {
        playerMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
    }


}

