package src;

import java.awt.Color;
import java.awt.Graphics;

public class Lives {
    private int lives;
    private int screenHeight;
    private int screenWidth;


    public Lives(int lives, int screenWidth, int screenHeight){
        this.lives=lives;
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;
    }

    public int getLives(){
        return lives;
    }

    public void deduct(){
        lives--;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.drawString("Lives: " + lives, 10, 20);
    }

}
