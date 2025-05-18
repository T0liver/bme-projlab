package bme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JatekosMenuView {
    private JatekosMenu jatekosMenu;
    private JPanel panel;

    JLabel pontLabel;
    JTextArea controls;
    JTextArea help;

    JButton saveBtn;
    JButton exitBtn;

    public JatekosMenuView(JatekosMenu jatekosMenu) {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.jatekosMenu = jatekosMenu;

        //JÁTÉKOS X
        panel.add(createLabel("Játékos "+jatekosMenu.getJatekos().getNev(), 20, true));
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        frissitPontLabel();
        panel.add(pontLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        //AKCIOK --- X: XXXXX
        controls = new JTextArea();
        setupAkciok();
        controls.setEditable(false);
        controls.setFont(new Font("Serif", Font.PLAIN, 12));
        controls.setAlignmentX(Component.CENTER_ALIGNMENT);
        controls.setOpaque(false);
        panel.add(controls);

        //HELP

        //GOMBOK -- MENTES -- KILEPEs
        saveBtn = new JButton("MENTÉS");
        exitBtn = new JButton("KILÉPÉS");
        saveBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(saveBtn);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(exitBtn);



        this.jatekosMenu = jatekosMenu;
    }

    //AKCIOK BILLENTYUZHOZ KOTESE
    private void setupAkciok() {
        StringBuilder builder = new StringBuilder();
        for (Akcio a : jatekosMenu.getAkciok()) {
            builder.append(a.getNev().charAt(0)).append(": ").append(a.getNev()).append("\n");
            panel.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyChar() == a.getNev().charAt(0)) {
                        //a.csinal();
                    }
                }
            });
        }
        controls.setText(builder.toString());
    }

    //FElrakás mainPanel-re
    public void draw(JFrame gw){
        frissitPontLabel();
        gw.getContentPane().add(panel);
    }

    void frissitPontLabel(){
        String type = jatekosMenu.getJatekos().getType() == 1 ? "Rovarász" : "Gombász" + "\n";
        String pont = String.valueOf(jatekosMenu.getJatekos().getPontok()) + "Pont";

        if (pontLabel == null) {
            pontLabel = createLabel(type + pont, 20, true);
            pontLabel.setHorizontalAlignment(SwingConstants.CENTER);
            pontLabel.setOpaque(true);
        }

        pontLabel.setText(type + pont);
    }

    // Helper method to create centered label
    JLabel createLabel(String text, int size, boolean bold) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Serif", bold ? Font.BOLD : Font.PLAIN, size));
        return label;
    }
}
