package bme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SporaView extends EntitasView {

    public SporaView(Object entitas, Mezo mexo, Jatekos jatekos) {
        super(entitas, mexo, jatekos);

        try {
            kinezet = ImageIO.read(new File("textures/Spora.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(GameWindow gw, Graphics g){
        Color jszin = jatekos.getSzin();
        BufferedImage image = color(kinezet, jszin);
        kinezet = image;
    }
}
