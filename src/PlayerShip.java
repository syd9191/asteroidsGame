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


    private boolean invulnerable=false;
    private double angle=0;
    


    public PlayerShip(int startX, int startY){
        this.x=startX;
        this.y=startY;
    }

    public void draw(Graphics g){
        Graphics2D g2d= (Graphics2D) g;

        AffineTransform saveAT = g2d.getTransform();
        if (invulnerable==false){
            g2d.setColor(Color.WHITE);
        }
        else{
            g2d.setColor(Color.PINK);
        }

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

    public double[][] getVertices(){
        double[][] vertices = new double[3][2];
        vertices[0][0] = x + size * Math.cos(angle); 
        vertices[0][1] = y + size * Math.sin(angle); 
        vertices[1][0] = x + size * Math.cos(angle + 2 * Math.PI / 3); 
        vertices[1][1] = y + size * Math.sin(angle + 2 * Math.PI / 3); 
        vertices[2][0] = x + size * Math.cos(angle + 4 * Math.PI / 3);
        vertices[2][1] = y + size * Math.sin(angle + 4 * Math.PI / 3); 
        return vertices;
    }

    public boolean checkCollision(Asteroid asteroid) {
        double[][] vertices = getVertices();
        int asteroidRadius = asteroid.getSize() / 2;

        for (int i = 0; i < vertices.length; i++) {
            double xDist = vertices[i][0] - asteroid.getX();
            double yDist = vertices[i][1] - asteroid.getY();
            double distanceSquared = xDist * xDist + yDist * yDist;

            if (distanceSquared < asteroidRadius * asteroidRadius) {
                return true;
            }
        }

        return false; // No collision
    }

    public void makeInvulnerable(){
        invulnerable=true;
    }

    public void makeVulnerable(){
        invulnerable=false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public double getAngle(){
        return angle;
    }

    public boolean isInvulnerable(){
        return invulnerable;
    }

    
}
