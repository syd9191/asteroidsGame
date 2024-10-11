package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;


public class GameOver{
    final float fontSize=25;

    private int screenHeight;
    private int screenWidth;
    private Font customFont;

    public GameOver(int screenWidth, int screenHeight){
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

    public void draw(Graphics g, int score){
        g.setColor(Color.RED);
        if (customFont!=null){
            g.setFont(customFont);
        }
        else{
            g.setFont(new Font("Serif", Font.BOLD, 30));
        }
        String gameOverText = "Game Over!";
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int x = (screenWidth - metrics.stringWidth(gameOverText)) / 2;
        int y = screenHeight / 2;

        g.drawString(gameOverText, x, y);

        String scoreText = "Score: " + score;
        metrics = g.getFontMetrics(g.getFont());
        x = (screenWidth - metrics.stringWidth(scoreText)) / 2;
        int scoreY = y + metrics.getHeight() + 10;

        g.drawString(scoreText, x, scoreY);
    }
    


}