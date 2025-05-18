package bme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SporaView extends EntitasView {

    public SporaView(Spora entitas, Mezo mexo, Gombasz jatekos) {
        super((Spora)entitas, mexo, jatekos);

        try {
            kinezet = ImageIO.read(new File("textures/Spora.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        entitas.setTartozkodik(mezo.getTekton());
        jatekos.addSpora((Spora)entitas);
        entitas.getTartozkodik().addSpora(entitas.getDarabszam(), entitas);
    }

    @Override
    public void draw(GameWindow gw, Graphics g){
        Color jszin = jatekos.getSzin();
        BufferedImage image = color(kinezet, jszin);
        kinezet = image;
    }
}
