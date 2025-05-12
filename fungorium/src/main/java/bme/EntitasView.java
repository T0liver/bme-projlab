package bme;

import java.awt.image.BufferedImage;

public class EntitasView {
    private Object entitas;
    private int mexoX;
    private int mexoY;
    private Jatekos jatekos;
    private BufferedImage kinezet;

    public EntitasView(Object entitas, int mexoX, int mexoY, Jatekos jatekos, BufferedImage kinezet) {
        this.entitas = entitas;
        this.mexoX = mexoX;
        this.mexoY = mexoY;
        this.jatekos = jatekos;
        this.kinezet = kinezet;
    }

    public void draw(GameWindow gw) {



    }


}