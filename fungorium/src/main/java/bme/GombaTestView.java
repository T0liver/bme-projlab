package bme;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



public class GombaTestView extends EntitasView{

    public GombaTestView(Object entitas, Mezo mezo, Jatekos jatekos) {
        super(entitas, mezo, jatekos);
        try {
            kinezet = ImageIO.read(new File("textures/GombaTest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void draw(GameWindow gw, Graphics g){
        BufferedImage image = color(kinezet);

        gw.drawSprite(this, g);
    }

}
