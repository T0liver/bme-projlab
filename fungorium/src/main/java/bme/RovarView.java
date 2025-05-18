package bme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RovarView extends EntitasView{

    public RovarView(Rovar entitas, Mezo mezo, Jatekos jatekos) {
        super((Rovar)entitas, mezo, jatekos);
        try {
            kinezet = ImageIO.read(new File("textures/Rovar.png"));
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

    public Rovar NewRovar(Mezo mezo, Rovarasz jatekos) {

        Rovar rov = new Rovar(jatekos, mezo);
        rov.getTartozkodik().getTekton().addRovar(rov);
        jatekos.addRovar(rov);

        return rov;

    }
}
