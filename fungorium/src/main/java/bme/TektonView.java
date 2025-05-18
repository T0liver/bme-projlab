package bme;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TektonView extends EntitasView{

    public TektonView(Tekton entitas, Mezo mezo, Jatekos jatekos) {
        super((Tekton) entitas, mezo, jatekos);
        kinezet = entitas.getImage();
    }

    @Override
    public void draw(GameWindow gw, Graphics g){

        //Tekton tk = new Tekton();

    }

    public void addTektonView(GameWindow gw) {
        List<Mezo> szomszdok = new ArrayList<>();
        szomszdok = mezo.getSzomszedok();

        for (Mezo m : szomszdok) {
            if(m.getTekton() != null) {
                Tekton tk = (Tekton)this.entitas;
                tk.addSzomszed(m.getTekton());
                entitas = tk;
            }
        }

        //gw.getJatekVezerlo().addTekton( (Tekton)entitas );
        //gw.addEntitas(this);

    }

}
