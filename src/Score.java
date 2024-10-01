package src;

import java.awt.Color;
import java.awt.Graphics;

public class Score {
    private int val=0;
    private int screenHeight;
    private int screenWidth;

    public Score(int screenWidth, int screenHeight){
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
    }
    

    public int getScore(){
        return val;
    }

    public void destroyAsteroid(){
        val+=5;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.drawString("Score: " + getScore(), screenWidth-60 , 20);
    }



    
}
