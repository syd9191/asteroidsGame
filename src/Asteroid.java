package src;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;



public class Asteroid {
    final double angle;
    final int side;

    BufferedImage asteroidImage;

    private int x,y;
    private int speed;
    private int size;

    
    Random rand=new Random();


    public Asteroid(int screenWidth, int screenHeight, int sizeArg, int speedArg){
        size=sizeArg;
        speed=speedArg;

        try {
            asteroidImage= ImageIO.read(new File("data/images/Asteroid Brown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        side=rand.nextInt(0,4); //randomise which side the asteroid spawns
        angle = Math.toRadians(rand.nextInt(360));
        
        
        switch(side){
            case 0: 
                x=-size;
                y=rand.nextInt(screenHeight);
                break;
            case 1:
                x=rand.nextInt(screenWidth);
                y=-size;
                break;
            case 2:
                x=screenWidth+size;
                y=rand.nextInt(screenHeight);
                break;
            case 3:
                x=rand.nextInt(screenWidth);
                y=screenHeight+size;
                break;
        }
    }

    public void draw(Graphics g){
        BufferedImage newImage= resizeImage((asteroidImage));
        g.drawImage(newImage, x-size/2, y-size/2, null);
    }


    public void update(){
        x+=Math.cos(angle)*speed;
        y+=Math.sin(angle)*speed;
    }
    
    public BufferedImage resizeImage(BufferedImage image){
        BufferedImage resizedImage= new BufferedImage(size*2, size*2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=resizedImage.createGraphics();
        g.drawImage(image, 0, 0, size, size, null);
        g.dispose();
        return resizedImage;
    }


    public boolean outOfBoundsCheck(){
        if (x>800+size||y>600+size||x<-size||y<-size){
            return true;
        }
        return false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getSize(){
        return size;
    }

}
