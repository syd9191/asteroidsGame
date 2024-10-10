package src;
import java.awt.Color;
import java.awt.Graphics;


public class Bullet{
    private int x,y;
    private double angle;
    final int speed=12;
    final int size=3;

    public Bullet(int x, int y, double shipAngle){
        this.x=x;
        this.y=y;
        this.angle=shipAngle;
    }

    public void update(){
        x+=Math.sin(Math.toRadians(angle))*speed;
        y-=Math.cos(Math.toRadians(angle))*speed;
    }

    public void draw(Graphics g){
        g.setColor(Color.WHITE);
        g.fillOval(x,y,size,size);
    }
    
    public boolean outOfBoundsCheck(){
        if (x>800+size||y>600+size||x<-size||y<-size){
            return true;
        }
        return false;
    }

    public boolean checkCollision(Asteroid asteroid){
        int xDist=x-asteroid.getX();
        int yDist=y-asteroid.getY();
        int distSquare=xDist*xDist+yDist*yDist;
        int combinedRadius=(asteroid.getSize()/2)+(size/2);
        return distSquare<=combinedRadius*combinedRadius;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}
