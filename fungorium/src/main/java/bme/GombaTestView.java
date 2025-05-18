package bme;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;



public class GombaTestView extends EntitasView{

    public GombaTestView(GombaTest entitas, Mezo mezo, Gombasz jatekos) {
        super((GombaTest)entitas, mezo, jatekos);
        try {
            kinezet = ImageIO.read(new File("textures/GombaTest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        entitas.getTartozkodik().setFoglalt(true);
        jatekos.addGombaTest(entitas);


    }


    @Override
    public void draw(GameWindow gw, Graphics g){
        Color jszin = jatekos.getSzin();
        BufferedImage image = color(kinezet, jszin);
        kinezet = image;

        try {
            GombaTest gt = new GombaTest( jatekos, 5, mezo.getTekton());
            jatekos.addGombaTest(gt);
        } catch (Exception e) {
            e.printStackTrace();
        }





    }

}
