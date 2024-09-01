package src;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class PlayerShip {
    private int x, y;
    final int size=10;
    final int speed=5;
    final double rotationSpeed=Math.toRadians(5);
    double angle=0;
    


    public PlayerShip(int startX, int startY){
        this.x=startX;
        this.y=startY;
    }

    public void draw(Graphics g){
        Graphics2D g2d= (Graphics2D) g;

        AffineTransform saveAT = g2d.getTransform();

        g2d.setColor(Color.PINK);

        g2d.translate(x, y);
        g2d.rotate(angle);

        int[] xPoints = {-size, -size, size}; // Apex at the top
        int[] yPoints = {size, -size, 0};
        g2d.fillPolygon(xPoints, yPoints, 3);

        g2d.setTransform(saveAT);
    }

    public void rotate(int multiplyer){
        angle+=multiplyer*rotationSpeed;
    }

    public void moveForward(){
        x+=Math.cos(angle)*speed;
        y+=Math.sin(angle)*speed;
        keepWithinBounds(800, 600);
    }

    public void moveBackwards(){
        x-=Math.cos(angle)*speed;
        y-=Math.sin(angle)*speed;
        keepWithinBounds(800, 600);
    }

    public int test(int a) {
        return a;
    }

    public void keepWithinBounds(int screenWidth, int screenHeight){
        if (x<-size) x=screenWidth+size;
        if (x>screenWidth+size) x=-size;
        if (y<-size) y=screenHeight+size;
        if (y>screenWidth+size) y=-size;
    }
}
