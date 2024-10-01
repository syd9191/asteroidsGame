package src;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Asteroid {
    private int x,y;
    private int speed;
    private  int size;
    final double angle;
    Random rand=new Random();


    public Asteroid(int screenWidth, int screenHeight, int sizeArg, int speedArg){
        size=sizeArg;
        speed=speedArg;
        angle = Math.toRadians(rand.nextInt(360));
        int side=rand.nextInt(0,3); //randomise which side the asteroid spawns
        switch (side){
            case 0: 
                x=-size;
                y=rand.nextInt(screenHeight);
                break;
            case 1:
                x=rand.nextInt(screenWidth);
                y=-size;
                break;
            case 2:
                x=screenWidth;
                y=rand.nextInt(screenHeight);
                break;
            case 3:
                x=rand.nextInt(screenWidth);
                y=screenHeight;
                break;
        }
    }

    public void draw(Graphics g){
        g.setColor(Color.gray);
        g.fillOval(x-size/2,y-size/2,size,size); //oval with height=width to make circle
    }


    public void update(){
        x+=Math.cos(angle)*speed;
        y+=Math.sin(angle)*speed;    
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
