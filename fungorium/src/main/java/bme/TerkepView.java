package bme;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TerkepView {
    private Terkep terkep;

    public TerkepView() {
        //this.terkep = new Terkep();
        //terkep.init();
    }

    public TerkepView(Terkep terkep) {
        this.terkep = terkep;
    }

    public void draw(JFrame window) {
        Container content = window.getContentPane(); // Fix: use the content pane
        //content.removeAll();
        content.setLayout(null); // Ensure absolute positioning
    
        window.setSize(960, 760);
        content.setBackground(new Color(0, 0, 100)); // Set background color here
    
        List<Mezo> mezok = terkep.getMezok();
        
        if (mezok.size() == 0) {
            terkep.init();
            mezok = terkep.getMezok();
        }

        
    
        // Add lines between non-matching neighbors
        for (Mezo mezo : mezok) {
            List<Integer> pos = mezo.getPos();
            int x = pos.get(0);
            int y = pos.get(1);
    
            Mezo right = getMezoAt(mezok, x + 1, y);
            Mezo down = getMezoAt(mezok, x, y + 1);
    
            // Horizontal line to the right
            if (right != null && !mezo.getTekton().equals(right.getTekton())) {
                JPanel line = new JPanel();
                line.setBackground(new Color(0, 0, 100));
                int drawX = 8 + x * 32 + 30;
                int drawY = 8 + y * 32;
                line.setBounds(drawX, drawY, 4, 32);
                content.add(line); 
            }
    
            // Vertical line downward
            if (down != null && !mezo.getTekton().equals(down.getTekton())) {
                JPanel line = new JPanel();
                line.setBackground(new Color(0, 0, 100));
                int drawX = 8 + x * 32;
                int drawY = 8 + y * 32 + 30;
                line.setBounds(drawX, drawY, 32, 4);
                content.add(line);
            }
        }

        // Add all tiles
        for (Mezo mezo : mezok) {
            BufferedImage img = mezo.getTekton().getImage();
            JLabel jl = new JLabel(new ImageIcon(img));
            List<Integer> pos = mezo.getPos();
            int x = 8 + pos.get(0) * 32;
            int y = 8 + pos.get(1) * 32;
            jl.setBounds(x, y, 32, 32);
            content.add(jl); // Fix: add to content pane
        }
        window.setVisible(true);
        window.revalidate();
        window.repaint();
    }

    // Helper method to find a Mezo at a specific grid position
    private Mezo getMezoAt(List<Mezo> mezok, int x, int y) {
        for (Mezo m : mezok) {
            List<Integer> p = m.getPos();
            if (p.get(0) == x && p.get(1) == y) {
                terkep.setActiveMezo(m);
                return m;
            }
        }
        return null;
    }

    /**
     * Térkép inicializálása szolgáló függvény
     * @return true, ha sikerült inicializálni a térképet; false, ha nem
     *
    public void init() {
        terkep.init();
    }*/
}
