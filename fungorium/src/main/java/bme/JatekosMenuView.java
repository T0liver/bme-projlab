package bme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JatekosMenuView {
    private JatekosMenu jatekosMenu;
    private JPanel panel;
    private JatekvezerloView jatekvezerloview;

    GroupLayout layout;

    JPanel playerinfoPanel = new JPanel();
    JLabel nameLabel;
    JLabel typeLabel;
    JLabel pontLabel;

    Color playerColor;

    JPanel akcioPanel = new JPanel();

    JTextArea help;

    Akcio selectedAkcio;

    JPanel exitSavePanel = new JPanel();
    JButton saveBtn;
    JButton exitBtn;
    JButton endTurnBtn;

    public JatekosMenuView(JatekosMenu jatekosMenu) {
        this.jatekosMenu = jatekosMenu;

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 800));
        this.jatekosMenu = jatekosMenu;


        //Jatekos info (name, points)
        playerinfoPanel.setLayout(new GridLayout(3, 1));
        playerinfoPanel.setMinimumSize(new Dimension(200, 200));

        nameLabel = createLabel();
        playerinfoPanel.add(nameLabel);

        typeLabel = createLabel();
        playerinfoPanel.add(typeLabel);

        pontLabel = createLabel();
        pontLabel.setMaximumSize(new Dimension(200, 20));
        playerinfoPanel.add(pontLabel);

        //AKCIO GOMBOK XXXXX
        akcioPanel.setLayout(new GridLayout(3, 1));
        akcioPanel.setMaximumSize(new Dimension(200, 150));
        akcioPanel.setBorder(new StrokeBorder(new BasicStroke(3)));
        setupAkciok();
        //panel.add(akcioPanel);

        //HELP
        help = new JTextArea();
        help.setLineWrap(true);
        help.setWrapStyleWord(true);
        help.setEditable(false);
        help.setFont(new Font("Serif", Font.PLAIN, 14));
        help.setAlignmentX(Component.CENTER_ALIGNMENT);
        help.setMaximumSize(new Dimension(200, 200));
        help.setPreferredSize(new Dimension(200, 200));

        //panel.add(help);

        //GOMBOK -- MENTES -- KILEPEs
        exitSavePanel.setLayout(new GridLayout(3, 1));
        exitSavePanel.setMaximumSize(new Dimension(200, 150));
        exitSavePanel.setBorder(new StrokeBorder(new BasicStroke(3)));

        setupEndTurnBtn();
        setupSaveBtn();
        setupExitBtn();

        endTurnBtn.setBackground(Color.WHITE);
        saveBtn.setBackground(Color.WHITE);
        exitBtn.setBackground(Color.WHITE);
        endTurnBtn.setFont(new Font("Serif", Font.BOLD, 16));
        saveBtn.setFont(new Font("Serif", Font.BOLD, 16));
        exitBtn.setFont(new Font("Serif", Font.BOLD, 16));
        endTurnBtn.setBorder(null);
        saveBtn.setBorder(null);
        exitBtn.setBorder(null);

        exitSavePanel.add(endTurnBtn);
        exitSavePanel.add(saveBtn);
        exitSavePanel.add(exitBtn);

        //panel.add(exitSavePanel);

        layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // GroupLayout horizontal group
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(playerinfoPanel)
                        .addComponent(akcioPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(help, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(exitSavePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

// GroupLayout vertical group
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(playerinfoPanel)
                        .addComponent(akcioPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(help, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(exitSavePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
    }

    public void setJatekVezerloView(JatekvezerloView jvV) {
        jatekvezerloview = jvV;
    }

    public void setJatekosMenu(JatekosMenu jm) {
        jatekosMenu = jm;
    }

    public Akcio getSelectedAkcio() {
        return selectedAkcio;
    }

    public void changeJatekos(Jatekos ujJatekos){
        jatekosMenu.setJatekos(ujJatekos);
        jatekosMenu.setAkciok(ujJatekos.getAkciok());
    }

    //AKCIOK BILLENTYUZHOZ KOTESE
    private void setupAkciok() {
        //Egymas ala gombokat rak, kattintás után kiválasztva az akcio
        for (Akcio a : jatekosMenu.getAkciok()) {
            JButton akcioButton = new JButton(a.getNev());
            akcioButton.setBackground(Color.WHITE);
            akcioButton.setFont(new Font("Serif", Font.BOLD, 16));
            akcioButton.setBorder(null);
            akcioButton.setPreferredSize(new Dimension(200, 50));
            akcioButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            akcioButton.setFocusPainted(false);
            akcioButton.addActionListener(e -> {
                selectedAkcio = a;
                //a.csinal(new Mezo(-10, -10), new Mezo(-10, -10));
                frissitHelp();
            });
            akcioPanel.add(akcioButton);
        }
    }

    private void setupExitBtn() {
        exitBtn = new JButton("KILÉPÉS");
        exitBtn.setFocusable(false);
        exitBtn.setPreferredSize(new Dimension(200, 50));
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        exitBtn.addActionListener(e -> {
            int ok = JOptionPane.showConfirmDialog(null, "Biztos ki akarsz lépni?");
            if (ok == JOptionPane.OK_OPTION) {
                System.exit(0);
            } else if (ok == JOptionPane.CANCEL_OPTION) {
                JOptionPane.showMessageDialog(null, "  _____\n" +
                        "< hello >\n" +
                        "   -------\n" +
                        "       \\    ^__^\n" +
                        "         \\  (oo)\\______\n" +
                        "            (__)\\              )\\/\\\n" +
                        "                    ||----w   |\n" +
                        "                    ||           ||\n");
            }
        });
    }

    private void setupSaveBtn() {
        saveBtn = new JButton("MENTÉS");
        saveBtn.setFocusable(false);
        saveBtn.setPreferredSize(new Dimension(200, 50));
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveBtn.addActionListener(e -> {
            /// TODO
        });

    }

    private void setupEndTurnBtn() {
        endTurnBtn = new JButton("KÖR VÉGE");
        endTurnBtn.setFocusable(false);
        endTurnBtn.setPreferredSize(new Dimension(200, 50));
        endTurnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        endTurnBtn.addActionListener(e -> {
            jatekvezerloview.kovetkezoKor();
            changeJatekos(jatekvezerloview.getSoronLevoJatekos());
            frissitNameLabel();
            frissitPontLabel();
            frissitHelp();
            frissitAkciok();
        });
    }

    //FElrakás mainPanel-re
    public void draw(JPanel w){
        if (jatekosMenu.getJatekos() != null) {
            frissitNameLabel();
            frissitPontLabel();
            frissitHelp();
        }
        w.add(panel);
    }

    void frissitNameLabel(){
        nameLabel.setText("Játékos "+jatekosMenu.getJatekos().getNev());

        playerColor = jatekvezerloview.getSoronLevoJatekos().getSzin();
        nameLabel.setForeground(colorContrast(playerColor, Color.BLACK) ? Color.BLACK : Color.WHITE);
        playerinfoPanel.setBackground(playerColor);
    }

    void frissitPontLabel(){
        String type = (jatekosMenu.getJatekos().getType() == 1 ? "Rovarász" : "Gombász");
        typeLabel.setText(type);
        String pont = jatekosMenu.getJatekos().getPontok() + " Pont";
        pontLabel.setText(pont);

        if (pontLabel == null) {
            pontLabel = createLabel();
            pontLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pontLabel.setOpaque(true);
        }

        playerColor = jatekvezerloview.getSoronLevoJatekos().getSzin();
        boolean isContrast = colorContrast(playerColor, Color.BLACK);
        pontLabel.setForeground(isContrast ? Color.BLACK : Color.WHITE);
        typeLabel.setForeground(isContrast ? Color.BLACK : Color.WHITE);
    }

    void frissitAkciok(){
        akcioPanel.removeAll();
        akcioPanel.revalidate();
        setupAkciok();
    }

    void frissitHelp(){
        if(selectedAkcio == null){
            help.setText("");
        } else{
            help.setText(selectedAkcio.getHelp());
        }
    }

    // Helper method to create centered label
    JLabel createLabel() {
        JLabel label = new JLabel();
        label.setAlignmentY(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 30));
        label.setFont(new Font("Serif", Font.BOLD, 20));
        return label;
    }

    

    private boolean colorContrast(Color c1, Color c2) {
        double r1c = (c1.getRed() / 255.0 <= 0.03928) ? c1.getRed() / 12.92 : Math.pow((c1.getRed() + 0.055) / 1.055, 2.4);
        double g1c = (c1.getGreen() / 255.0 <= 0.03928) ? c1.getGreen() / 12.92 : Math.pow((c1.getGreen() + 0.055) / 1.055, 2.4);
        double b1c = (c1.getBlue() / 255.0 <= 0.03928) ? c1.getBlue() / 12.92 : Math.pow((c1.getBlue() + 0.055) / 1.055, 2.4);
        double luminance1 = 0.2126 * r1c + 0.7152 * g1c + 0.0722 * b1c;

        double r2c = (c2.getRed() / 255.0 <= 0.03928) ? c2.getRed() / 12.92 : Math.pow((c2.getRed() + 0.055) / 1.055, 2.4);
        double g2c = (c2.getGreen() / 255.0 <= 0.03928) ? c2.getGreen() / 12.92 : Math.pow((c2.getGreen() + 0.055) / 1.055, 2.4);
        double b2c = (c2.getBlue() / 255.0 <= 0.03928) ? c2.getBlue() / 12.92 : Math.pow((c2.getBlue() + 0.055) / 1.055, 2.4);
        double luminance2 = 0.2126 * r2c + 0.7152 * g2c + 0.0722 * b2c;

        double brighter = Math.max(luminance1, luminance2);
        double darker = Math.min(luminance1, luminance2);
        double contrastRatio = (brighter + 0.05) / (darker + 0.05);

        contrastRatio = contrastRatio / 100000.0;
        return contrastRatio >= 6.0;
    }
}
