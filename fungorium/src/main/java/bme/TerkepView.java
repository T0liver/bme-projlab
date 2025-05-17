package bme;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class TerkepView {
    private Terkep terkep;

    public TerkepView() {
        this.terkep = new Terkep();
        terkep.init();
    }

    public TerkepView(Terkep terkep) {
        this.terkep = terkep;
    }

    public void draw(JFrame window) {
        window.setLayout(null); // Ensure absolute positioning

        List<Mezo> mezok = terkep.getMezok();

        // Add all tiles
        for (Mezo mezo : mezok) {
            BufferedImage img = mezo.getTekton().getImage();
            JLabel jl = new JLabel(new ImageIcon(img));
            List<Integer> pos = mezo.getPos();
            int x = 9 + pos.get(0) * 32;
            int y = 9 + pos.get(1) * 32;
            jl.setBounds(x, y, 32, 32);
            window.add(jl);
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
                line.setBackground(Color.BLACK);
                int drawX = 9 + x * 32 + 30; // 2 pixels into current tile
                int drawY = 9 + y * 32;
                line.setBounds(drawX, drawY, 4, 32); // 4px wide vertical line
                window.add(line);
            }

            // Vertical line downward
            if (down != null && !mezo.getTekton().equals(down.getTekton())) {
                JPanel line = new JPanel();
                line.setBackground(Color.BLACK);
                int drawX = 9 + x * 32;
                int drawY = 9 + y * 32 + 30; // 2 pixels into current tile
                line.setBounds(drawX, drawY, 32, 4); // 4px tall horizontal line
                window.add(line);
            }
        }

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
     */
    public void init() {
        terkep.init();
    }
}
