package src;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class PlayerShip {
    final int size=35;
    final int speed=5;
    final double rotationSpeed=Math.toRadians(320);
    final double spriteHitboxScale=0.9;

    private int x, y;
    private boolean invulnerable=false;
    private double angle=Math.toRadians(0);
    
    private BufferedImage playerShipImage;
    private BufferedImage woundedPlayerShipImage;


    public PlayerShip(int x, int y){
        this.x=x;
        this.y=y;

        try {
            playerShipImage=ImageIO.read(new File("data/images/pixelShip.png"));
            playerShipImage=resizeImage(playerShipImage);
            woundedPlayerShipImage=ImageIO.read(new File("data/images/woundedPixelShip.png"));
            woundedPlayerShipImage=resizeImage(woundedPlayerShipImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        g2d.rotate(Math.toRadians(angle));
        
        if (invulnerable==false){
            g.drawImage(playerShipImage, -size, -size, 2 * size, 2 * size, null);
        }else{
            g.drawImage(woundedPlayerShipImage, -size, -size, 2 * size, 2 * size, null);
        }
        g2d.setTransform(saveAT);
    }

    public void drawHitbox(Graphics g){
        double[][] vertices = getVertices();

        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        for (int i = 0; i < 3; i++) {
            xPoints[i] = (int)vertices[i][0];
            yPoints[i] = (int)vertices[i][1];
        }

        g.drawPolygon(xPoints, yPoints, 3);
    }

    public void rotate(int multiplyer){
        angle+=multiplyer*rotationSpeed;
    }

    public void moveForward(){
        x+=Math.sin(Math.toRadians(angle))*speed;
        y-=Math.cos(Math.toRadians(angle))*speed;
        keepWithinBounds(800, 600);
    }

    public void moveBackwards(){
        x-=Math.sin(Math.toRadians(angle))*speed;
        y+=Math.cos(Math.toRadians(angle))*speed;
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
        vertices[0][0] = x + spriteHitboxScale * size * Math.sin(Math.toRadians(angle)); 
        vertices[0][1] = y - spriteHitboxScale * size * Math.cos(Math.toRadians(angle)); 
        vertices[1][0] = x + spriteHitboxScale * size * Math.sin(Math.toRadians(angle + 120)); 
        vertices[1][1] = y - spriteHitboxScale * size * Math.cos(Math.toRadians(angle + 120)); 
        vertices[2][0] = x + spriteHitboxScale * size * Math.sin(Math.toRadians(angle + 240));
        vertices[2][1] = y - spriteHitboxScale * size * Math.cos(Math.toRadians(angle + 240)); 
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

    public BufferedImage resizeImage(BufferedImage image){
        BufferedImage resizedImage= new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g=resizedImage.createGraphics();
        g.drawImage(image, 0, 0, size, size, null);
        g.dispose();
        return resizedImage;
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
