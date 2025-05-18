package bme;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JatekosView {
    private Jatekos jatekos;
    private Random r = new Random();
    public JatekosView(Jatekos j) {
        jatekos = j;
    }
    public void draw(JFrame gw) {
        List<GombaTest> gts = jatekos.getGombaTestek();
        List<GombaFonal> gfs = jatekos.getGombaFonalak();
        List<Spora> ss = jatekos.getSporak();
        List<Rovar> rs = jatekos.getRovarok();

        //GombaFonalak rajzolása
        for (int i = 0; i < gfs.size(); ++i) {
            GombaFonal gf = gfs.get(i);
            Map<Mezo, List<Mezo>> vezet = gf.getVezet();
            BufferedImage negxnegy, negy, posxnegy, negx, neut, posx, negxposy, posy, posxposy;
            try {
                negxnegy = ImageIO.read(new File("textures/FonalDiagUL.png"));
                negy = ImageIO.read(new File("textures/FonalUp.png"));
                posxnegy = ImageIO.read(new File("textures/FonalDiagUR.png"));
                negx = ImageIO.read(new File("textures/FonalLeft.png"));
                neut = ImageIO.read(new File("textures/FonalNeut.png"));
                posx = ImageIO.read(new File("textures/FonalRight.png"));
                negxposy = ImageIO.read(new File("textures/FonalDiagDL.png"));
                posy = ImageIO.read(new File("textures/FonalDown.png"));
                posxposy = ImageIO.read(new File("textures/FonalDiagDR.png"));
                for (Mezo m : vezet.keySet()) {
                    JLabel label = createCompositeLabel(
                        gf.getVezet(),
                        m,
                        neut, negy, posy, negx, posx,
                        negxnegy, posxnegy, negxposy, posxposy
                    );
                    gw.add(label);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //GombaTestek rajzolása
        for (int i = 0; i < gts.size(); ++i) {
            GombaTest gt = gts.get(i);
            JLabel jl = new JLabel();
            BufferedImage colouredImage;
            try {
                colouredImage = new EntitasView(gt, gt.getTartozkodik().getMezok().get(0), jatekos).color(gt.getImg(), jatekos.getSzin());
                jl.setIcon(new ImageIcon(colouredImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Integer> pos = gt.getTartozkodik().getMezok().get(0).getPos();
            jl.setBounds(8 + pos.get(0) * 32, 8 + pos.get(1) * 32, 32, 32);
            gw.add(jl);
        }

        //Spórák rajzolása
        for (int i = 0; i < ss.size(); ++i) {
            Spora s = ss.get(i);
            JLabel jl = new JLabel();
            BufferedImage colouredImage;
            try {
                colouredImage = new EntitasView(s, s.getTartozkodik().getMezok().get(0), jatekos).color(s.getImg(), jatekos.getSzin());
                jl.setIcon(new ImageIcon(colouredImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Integer> pos = s.getTartozkodik().getMezok().get(r.nextInt(s.getTartozkodik().getMezok().size())).getPos();
            jl.setBounds(8 + pos.get(0) * 32, 8 + pos.get(1) * 32, 32, 32);
            gw.add(jl);
        }

        //Rovarok rajzolása
        for (int i = 0; i < rs.size(); ++i) {
            Rovar r = rs.get(i);
            JLabel jl = new JLabel();
            BufferedImage colouredImage;
            try {
                colouredImage = new EntitasView(r, r.getTartozkodik(), jatekos).color(r.getImg(), jatekos.getSzin());
                jl.setIcon(new ImageIcon(colouredImage));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Integer> pos = r.getTartozkodik().getPos();
            jl.setBounds(8 + pos.get(0) * 32, 8 + pos.get(1) * 32, 32, 32);
            gw.add(jl);
        }

        gw.repaint();
    }

    private String getDirection(Mezo from, Mezo to) {
        List<Integer> p1 = from.getPos();
        List<Integer> p2 = to.getPos();
    
        int dx = p2.get(0) - p1.get(0);
        int dy = p2.get(1) - p1.get(1);
    
        if (dx == 0 && dy == -1) return "Up";
        if (dx == 0 && dy == 1) return "Down";
        if (dx == -1 && dy == 0) return "Left";
        if (dx == 1 && dy == 0) return "Right";
        if (dx == -1 && dy == -1) return "DiagUL";
        if (dx == 1 && dy == -1) return "DiagUR";
        if (dx == -1 && dy == 1) return "DiagDL";
        if (dx == 1 && dy == 1) return "DiagDR";
        return null;
    }

    private JLabel createCompositeLabel(Map<Mezo, List<Mezo>> vezet, Mezo current,
                                   BufferedImage mid, BufferedImage up, BufferedImage down,
                                   BufferedImage left, BufferedImage right,
                                   BufferedImage diagUL, BufferedImage diagUR,
                                   BufferedImage diagDL, BufferedImage diagDR) {
        int width = mid.getWidth();
        int height = mid.getHeight();
        BufferedImage composed = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = composed.createGraphics();
        g.drawImage(mid, 0, 0, null); // base texture

        List<Mezo> neighbors = vezet.getOrDefault(current, new ArrayList<>());
        for (Mezo neighbor : neighbors) {
            String dir = getDirection(current, neighbor);
            if (dir == null) continue;

            switch (dir) {
                case "Up" -> g.drawImage(up, 0, 0, null);
                case "Down" -> g.drawImage(down, 0, 0, null);
                case "Left" -> g.drawImage(left, 0, 0, null);
                case "Right" -> g.drawImage(right, 0, 0, null);
                case "DiagUL" -> g.drawImage(diagUL, 0, 0, null);
                case "DiagUR" -> g.drawImage(diagUR, 0, 0, null);
                case "DiagDL" -> g.drawImage(diagDL, 0, 0, null);
                case "DiagDR" -> g.drawImage(diagDR, 0, 0, null);
            }
        }

        g.dispose();
        return new JLabel(new ImageIcon(composed));
    }
}