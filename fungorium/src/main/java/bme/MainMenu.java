package bme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MainMenu extends JPanel {
    private JButton addGombaszButton;
    private JButton addRovaraszButton;
    private JButton loadGameButton;
    private JButton exitButton;
    private JButton startGameButton;
    private DefaultListModel<PlayerDisplayData> gombaszListModel;
    private JList<PlayerDisplayData> gombaszList;
    private JButton removeGombaszButton;
    private DefaultListModel<PlayerDisplayData> rovaraszListModel;
    private JList<PlayerDisplayData> rovaraszList;
    private JButton removeRovaraszButton;
    private List<GameWindow.PlayerData> players;
    private GameWindow parent;
    private JLabel titleLabel;

    public MainMenu(GameWindow parent, List<GameWindow.PlayerData> players) {
        this.parent = parent;
        this.players = players;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;

        Font titleFont = new Font("Arial", Font.BOLD, 48); // Nagyobb cím
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        titleLabel = new JLabel("FUNGÓRIUM", SwingConstants.CENTER);
        titleLabel.setFont(titleFont);

        JButton gombaszButton = new JButton("Gombász");
        gombaszButton.setFont(buttonFont);
        JButton rovaraszButton = new JButton("Rovarasz");
        rovaraszButton.setFont(buttonFont);
        loadGameButton = new JButton("Betöltés");
        loadGameButton.setFont(buttonFont);
        exitButton = new JButton("Kilépés");
        exitButton.setFont(buttonFont);
        startGameButton = new JButton("Indítás");
        startGameButton.setFont(buttonFont);

        gombaszListModel = new DefaultListModel<>();
        gombaszList = new JList<>(gombaszListModel);
        JScrollPane gombaszListScrollPane = new JScrollPane(gombaszList);
        gombaszListScrollPane.setPreferredSize(new Dimension(150, 80)); // Kisebb magasság

        rovaraszListModel = new DefaultListModel<>();
        rovaraszList = new JList<>(rovaraszListModel);
        JScrollPane rovaraszListScrollPane = new JScrollPane(rovaraszList);
        rovaraszListScrollPane.setPreferredSize(new Dimension(150, 80)); // Kisebb magasság

        removeGombaszButton = new JButton("-");
        removeGombaszButton.setPreferredSize(new Dimension(50, 50));
        addGombaszButton = new JButton("+");
        addGombaszButton.setPreferredSize(new Dimension(50, 50));
        removeRovaraszButton = new JButton("-");
        removeRovaraszButton.setPreferredSize(new Dimension(50, 50));
        addRovaraszButton = new JButton("+");
        addRovaraszButton.setPreferredSize(new Dimension(50, 50));

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
        add(new JLabel("Gombászok", SwingConstants.CENTER), gbc);
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
        add(new JLabel("Rovarászok", SwingConstants.CENTER), gbc);
        gbc.gridy = 2;
        add(rovaraszListScrollPane, gbc);

        updatePlayerLists();

        addGombaszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(MainMenu.this, "Gombász neve:", "Új Gombász", JOptionPane.PLAIN_MESSAGE);
                if (playerName != null && !playerName.trim().isEmpty()) {
                    Color color = generateRandomColor();
                    players.add(new GameWindow.PlayerData(playerName, "Gombász", color));
                    updatePlayerLists();
                }
            }
        });

        addRovaraszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = JOptionPane.showInputDialog(MainMenu.this, "Rovarasz neve:", "Új Rovarasz", JOptionPane.PLAIN_MESSAGE);
                if (playerName != null && !playerName.trim().isEmpty()) {
                    Color color = generateRandomColor();
                    players.add(new GameWindow.PlayerData(playerName, "Rovarasz", color));
                    updatePlayerLists();
                }
            }
        });

        removeGombaszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = gombaszList.getSelectedIndex();
                if (selectedIndex != -1) {
                    PlayerDisplayData removedData = gombaszListModel.getElementAt(selectedIndex);
                    players.removeIf(p -> p.name.equals(removedData.getName()) && p.cast.equals("Gombász"));
                    updatePlayerLists();
                }
            }
        });

        removeRovaraszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = rovaraszList.getSelectedIndex();
                if (selectedIndex != -1) {
                    PlayerDisplayData removedData = rovaraszListModel.getElementAt(selectedIndex);
                    players.removeIf(p -> p.name.equals(removedData.getName()) && p.cast.equals("Rovarasz"));
                    updatePlayerLists();
                }
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Játék betöltése...");
                // Betöltés logika ide
            }
        });

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!players.isEmpty()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            new GameBoard(players);
                        }
                    });
                    parent.dispose(); // Főmenü bezárása
                } else {
                    JOptionPane.showMessageDialog(MainMenu.this, "Kérlek, adj hozzá legalább egy játékost a játék indításához!", "Figyelem", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void updatePlayerLists() {
        gombaszListModel.clear();
        rovaraszListModel.clear();
        for (GameWindow.PlayerData player : players) {
            PlayerDisplayData displayData = new PlayerDisplayData(player.name, player.color);
            if (player.cast.equals("Gombász")) {
                gombaszListModel.addElement(displayData);
            } else if (player.cast.equals("Rovarasz")) {
                rovaraszListModel.addElement(displayData);
            }
        }
        gombaszList.setCellRenderer(new PlayerListCellRenderer());
        rovaraszList.setCellRenderer(new PlayerListCellRenderer());
    }

    private Color generateRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private String colorToHexString(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    // Segédosztály a név és szín tárolására a listában
    private static class PlayerDisplayData {
        private String name;
        private Color color;

        public PlayerDisplayData(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public Color getColor() {
            return color;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    // Egyéni cell renderer a színnégyzet megjelenítéséhez a listában
    class PlayerListCellRenderer extends DefaultListCellRenderer {
        private static final int COLOR_SQUARE_SIZE = 12;
        private static final int PADDING = 5;

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