package src;

import java.awt.Color;
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
    final int type;

    final int size3=150;
    final int speed3=2;

    final int size2=100;
    final int speed2=2;

    final int size1=50;
    final int speed1=3;


    BufferedImage asteroidImage;

    private int x,y;
    private int speed;
    private int size;

    
    Random rand=new Random();


    public Asteroid(int screenWidth, int screenHeight, int type, int x, int y){
        this.type=type; //randomise which side the asteroid spawns
        side=rand.nextInt(0,4);
        angle = Math.toRadians(rand.nextInt(30,360));

        try {
            asteroidImage= ImageIO.read(new File("data/images/Asteroid Brown.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        switch (type){
            case 3:
                size=size3;
                speed=speed3; 
                break;
            case 2:
                size=size2;
                speed=speed2; 
                break;
            case 1:
                size=size1;
                speed=speed1; 
                break;
        }
        if (x==-1 && y==-1){
            switch (side){
                case 0: 
                    this.x=-size;
                    this.y=rand.nextInt(screenHeight);
                    break;
                case 1:
                    this.x=rand.nextInt(screenWidth);
                    this.y=-size;
                    break;
                case 2:
                    this.x=screenWidth+size;
                    this.y=rand.nextInt(screenHeight);
                    break;
                case 3:
                    this.x=rand.nextInt(screenWidth);
                    this.y=screenHeight+size;
                    break;
            }
        }
        else{
            this.x=x;
            this.y=y;
        }
    }

    public void draw(Graphics g){
        BufferedImage newImage= resizeImage(asteroidImage);
        g.drawImage(newImage, x-size/2, y-size/2, null);
        g.setColor(Color.CYAN);
        //g.drawOval(x-size/2, y-size/2, size, size); for future hitbox testing
    }


    public void update(){
        x+=Math.sin(angle)*speed;
        y+=Math.cos(angle)*speed;
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

    public int getType(){
        return type;
    }

}
