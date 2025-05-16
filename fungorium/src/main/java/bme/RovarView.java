package bme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RovarView extends EntitasView{

    public RovarView(Object entitas, Mezo mezo, Jatekos jatekos) {
        super(entitas, mezo, jatekos);
        try {
            kinezet = ImageIO.read(new File("textures/Rovar.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void draw(GameWindow gw, Graphics g){
        gw.drawSprite(this, g);
    }
}
