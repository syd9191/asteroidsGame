package src;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;



public class Lives {
    private int lives;
    private int screenHeight;
    private int screenWidth;

    BufferedImage heartImage;
    BufferedImage resizedImage;



    public Lives(int lives, int screenWidth, int screenHeight){
        this.lives=lives;
        this.screenHeight=screenHeight;
        this.screenWidth=screenWidth;

        try{
            heartImage= ImageIO.read(new File("data/images/heart.png"));
            resizedImage=resizeImage(heartImage, 30, 30);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public BufferedImage resizeImage(BufferedImage image, int targetWidth , int targetHeight){
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.setComposite(java.awt.AlphaComposite.Src);
        g2d.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose(); 
        return resizedImage;
    }

    public int getLives(){
        return lives;
    }

    public void deduct(){
        lives--;
    }

    public void draw(Graphics g){
        g.setColor(Color.CYAN);

        for (int numHearts=0; numHearts<lives; numHearts++){
            g.drawImage(resizedImage, 20+numHearts*40 , 20, null);
        }
        
    }



}
