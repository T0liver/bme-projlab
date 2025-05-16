package bme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TektonView extends EntitasView{

    public TektonView(Tekton entitas, Mezo mezo, Jatekos jatekos) {
        super(entitas, mezo, jatekos);
        kinezet = entitas.getImage();
    }

    @Override
    public void draw(GameWindow gw, Graphics g){

        Tekton tk = new Tekton();

    }

}
