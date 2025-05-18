package bme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GombaFonalView extends EntitasView {

    public GombaFonalView(GombaFonal entitas, Mezo mezo, Gombasz jatekos) {
        super(entitas, mezo, jatekos);

        try {
            kinezet = ImageIO.read(new File("textures/FonalDown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        jatekos.addGombaFonal((GombaFonal)entitas);

    }

    @Override
    public void draw(GameWindow gw, Graphics g){

        Color jszin = jatekos.getSzin();
        BufferedImage image = color(kinezet, jszin);
        kinezet = image;

        if (gw.getFirstClick() == null || gw.getSecondClick() == null) return;

        int x1 = gw.getFirstClick().getPos().get(0);
        int y1 = gw.getFirstClick().getPos().get(1);

        int x2 = gw.getSecondClick().getPos().get(0);
        int y2 = gw.getSecondClick().getPos().get(1);

        int cellsize = gw.CELL_SIZE;

        if (y1 == y2) {
            //vizszintes

            int y = y1 * cellsize;
            int width = (Math.abs(x1 - x2) + 1) * cellsize;

            // Forgatás 90° jobbra
            BufferedImage rotated = new BufferedImage(cellsize, cellsize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gRot = rotated.createGraphics();
            gRot.rotate(Math.PI / 2, cellsize / 2.0, cellsize / 2.0);
            gRot.drawImage(kinezet, 0, 0, cellsize, cellsize, null);
            gRot.dispose();

            BufferedImage result = new BufferedImage(width, cellsize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = result.createGraphics();
            for (int i = 0; i < width; i++) {
                g2.drawImage(rotated, i * cellsize, 0, null);
            }
            g2.dispose();

            this.kinezet = result;
        } else if (x1 == x2) {
            // Függőleges fonal
            int length = Math.abs(y2 - y1) + 1;
            int totalHeight = length * cellsize;

            BufferedImage result = new BufferedImage(cellsize, totalHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = result.createGraphics();
            for (int i = 0; i < length; i++) {
                g2.drawImage(kinezet, 0, i * cellsize, null);
            }
            g2.dispose();

            this.kinezet = result;
        }
    }

}
