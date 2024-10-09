package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;




public class Score {
    final float fontSize=25;

    private int val=0;
    private int screenHeight;
    private int screenWidth;
    private Font customFont;


    public Score(int screenWidth, int screenHeight){
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
        try {
            customFont=Font.createFont(Font.TRUETYPE_FONT, new File("data/fonts/PixelDigivolve-mOm9.ttf")).deriveFont(fontSize);
        } 
        catch (IOException| FontFormatException e) {
            e.printStackTrace();
        }
    }

    public Font getCustomFont(){
        return customFont;
    }
    

    public int getScore(){
        return val;
    }

    public void destroyAsteroid(){
        val+=5;
    }

    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        if (customFont!=null){
            g.setFont(customFont);
        }
        else{
            g.setFont(new Font("Serif", Font.BOLD, 30));
        }
        g.drawString("Score: " + getScore(), screenWidth-170 , 50);
    }
}
