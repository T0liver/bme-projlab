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
    private List<PlayerData> players = new ArrayList<>(); // A játékosok adatainak tárolására

    public GameWindow() {
        setTitle("Fungorium");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setResizable(false);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        MainMenu mainMenu = new MainMenu(this, players);
        gamePanel = new JPanel(); // Placeholder a későbbi játékpanelhez

        mainPanel.add(mainMenu, "MainMenu");
        mainPanel.add(gamePanel, "Game"); // Még nincs használatban

        add(mainPanel);
        cardLayout.show(mainPanel, "MainMenu");

        setVisible(true);
    }



    private void renderMenu(){

    }


    private void renderGame(){

    }

    public void drawSprite(EntitasView entitasView){

    }

    public void drawTerkep(){


    }

    private void createGamePanel() {
        gamePanel = new JPanel();
        gamePanel.setLayout(null);
        JLabel gameBackground = new JLabel(new ImageIcon("game_background.png"));
        gameBackground.setBounds(0, 0, screenWidth, screenHeight);
        gamePanel.add(gameBackground);
        JButton gameActionButton = new JButton("Akció");
        gameActionButton.setBounds(100, 100, 100, 30);
        gamePanel.add(gameActionButton);
        gamePanel.add(new JLabel("Játékterület"));
        JButton backToMainMenuButton = new JButton("Vissza a főmenübe");
        backToMainMenuButton.setBounds(10, 10, 150, 30);
        backToMainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "MainMenu");
            }
        });
        gamePanel.add(backToMainMenuButton);
    }

    private void createPlayerMenu() {
        playerMenuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 50, 10, 50);
    }



    // Segédosztály a játékos adatainak tárolására
    public static class PlayerData {
        String name;
        String cast;
        Color color;

        public PlayerData(String name, String cast, Color color) {
            this.name = name;
            this.cast = cast;
            this.color = color;
        }
    }



}