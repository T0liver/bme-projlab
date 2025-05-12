package bme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

class GameBoard extends JFrame {
    private static final int GRID_SIZE = 50;
    private static final int CELL_SIZE = 15;
    private List<Jatekos> players;
    private JPanel gridPanel;
    private Color[][] gridColors;

    public GameBoard(List<Jatekos> players) {
        this.players = players;
        setTitle("Fungorium - Játék");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE);
        setLocationRelativeTo(null);

        gridColors = new Color[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gridColors[i][j] = Color.LIGHT_GRAY; // Alapértelmezett szín
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
            }
        };

        gridPanel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));
        gridPanel.setLayout(null); // Abszolút pozicionálás, a rajzolás kézi

        gridPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = e.getY() / CELL_SIZE;
                int col = e.getX() / CELL_SIZE;
                if (row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE) {
                    gridColors[row][col] = Color.YELLOW; // Példa interakció
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
                // Példa billentyűzet interakcióra
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
        requestFocusInWindow(); // Fontos, hogy a billentyűzet eseményeket fogadja

        add(gridPanel);
        pack(); // Az ablak méretének beállítása a tartalomhoz
        setVisible(true);
    }
}
