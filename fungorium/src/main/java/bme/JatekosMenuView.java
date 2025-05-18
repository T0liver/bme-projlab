package bme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JatekosMenuView {
    private JatekosMenu jatekosMenu;
    private JPanel panel;

    JLabel nameLabel;
    JLabel pontLabel;

    JPanel akcioPanel = new JPanel();

    JTextArea help;

    Akcio selectedAkcio;

    JPanel exitSavePanel = new JPanel();
    JButton saveBtn;
    JButton exitBtn;

    public JatekosMenuView(JatekosMenu jatekosMenu) {
        this.jatekosMenu = jatekosMenu;

        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setPreferredSize(new Dimension(200, 600));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.jatekosMenu = jatekosMenu;

        //JÁTÉKOS X
        nameLabel = createLabel();
        panel.add(nameLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        //PONTOK
        pontLabel = createLabel();
        panel.add(pontLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        //AKCIO GOMBOK XXXXX
        akcioPanel.setLayout(new GridLayout(3, 1));
        akcioPanel.setMaximumSize(new Dimension(200, 150));
        setupAkciok();
        panel.add(akcioPanel);

        //HELP
        help = new JTextArea();
        help.setEditable(false);
        help.setFont(new Font("Serif", Font.PLAIN, 12));
        help.setAlignmentX(Component.CENTER_ALIGNMENT);
        help.setMaximumSize(new Dimension(200, 300));

        panel.add(help);

        //GOMBOK -- MENTES -- KILEPEs
        exitSavePanel.setLayout(new GridLayout(2, 1));
        exitSavePanel.setMaximumSize(new Dimension(200, 100));

        setupSaveBtn();
        setupExitBtn();

        exitSavePanel.add(saveBtn);
        exitSavePanel.add(exitBtn);

        panel.add(exitSavePanel);
    }

    public void changeJatekos(Jatekos ujJatekos){
        jatekosMenu.setJatekos(ujJatekos);
    }

    //AKCIOK BILLENTYUZHOZ KOTESE
    private void setupAkciok() {
        //Egymas ala gombokat rak, kattintás után kiválasztva az akcio
        for (Akcio a : jatekosMenu.getAkciok()) {
            JButton akcioButton = new JButton(a.getNev());
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
                JOptionPane.showMessageDialog(null, "  _______\n" +
                        "< hello >\n" +
                        "   -------\n" +
                        "       \\    ^__^\n" +
                        "         \\  (oo)\\_______\n" +
                        "            (__)\\              )\\/\\\n" +
                        "                 ||----w   |\n" +
                        "                 ||         ||\n");
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
    }

    void frissitPontLabel(){
        String type = (jatekosMenu.getJatekos().getType() == 1 ? "Rovarász" : "Gombász") + "\n";
        String pont = jatekosMenu.getJatekos().getPontok() + " Pont";

        if (pontLabel == null) {
            pontLabel = createLabel();
            pontLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pontLabel.setOpaque(true);
        }
        pontLabel.setText(type + pont);
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
        label.setFont(new Font("Serif", Font.BOLD, 16));
        return label;
    }
}
