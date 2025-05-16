package bme;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EntitasView {
    private Object entitas;
    public Mezo mezo;
    private Jatekos jatekos;
    protected BufferedImage kinezet;

    public EntitasView(Object entitas, Mezo mezo, Jatekos jatekos) {
        this.entitas = entitas;
        this.mezo = mezo;
        this.jatekos = jatekos;
    }

    public void draw(GameWindow gw, Graphics g) {}

    public BufferedImage getKinezet() {return kinezet;}

}