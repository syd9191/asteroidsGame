package src;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;


public class AsteroidsGame extends JPanel implements Runnable, KeyListener {
    final PlayerShip playership;
    final int asteroidsCap=10;
    final int bulletFireRate=15;
    final int invulTimer=100;


    private int lives=5;
    private int bulletCountdown=0;
    private int invulCountdown=0;

    private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
    final boolean running;

    private List<Asteroid> asteroids;
    private List<Bullet> bullets;
    
    


    public AsteroidsGame(){
        playership=new PlayerShip(400,300); //start new playership in the centre of the screen
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);   //this is to listen to inputs
        setFocusable(true);
        running=true;
        asteroids = new ArrayList<>();
        bullets= new ArrayList<>();
        


        for (int i=0;i<asteroidsCap;i++){
            asteroids.add(new Asteroid(800, 600, 20, 10));
        }
    }

    @Override
    public void run(){
        while (running){
            update();
            repaint();
            try {
                Thread.sleep(16);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if (upPressed) playership.moveForward();
        if (downPressed) playership.moveBackwards();


        if (leftPressed) playership.rotate(-1);
        if (rightPressed) playership.rotate(1);

        if (bulletCountdown>0){
            bulletCountdown--;
        }

        if (playership.isInvulnerable()&&invulCountdown>0){
            invulCountdown--;
        }     

        if (invulCountdown<=0){
            playership.makeVulnerable();
        }



        if (spacePressed&&bulletCountdown==0){
            bullets.add(new Bullet(playership.getX(), playership.getY(), playership.getAngle()));
            bulletCountdown=bulletFireRate;
        } 
        
        List<Asteroid> toRemoveAsteroids=new ArrayList<>();
        List<Bullet> toRemoveBullets=new ArrayList<>();


        for (Asteroid existingAsteroid: asteroids){
            if (playership.checkCollision(existingAsteroid)){
                if (playership.isInvulnerable()==false){
                    lives--;
                    playership.makeInvulnerable();
                    invulCountdown=invulTimer;
                }
            }
            existingAsteroid.update();
            if (existingAsteroid.outOfBoundsCheck()) {
                toRemoveAsteroids.add(existingAsteroid); // Respawn a new asteroid
            }
        
        }

        for (Bullet existingBullet:bullets){
            existingBullet.update();
            if (existingBullet.outOfBoundsCheck()){
                toRemoveBullets.add(existingBullet);
            }
            for (Asteroid existingAsteroid: asteroids){
                if (existingBullet.checkCollision(existingAsteroid)){
                    toRemoveBullets.add(existingBullet);
                    toRemoveAsteroids.add(existingAsteroid);
                }
            }
        }
        bullets.removeAll(toRemoveBullets);
        asteroids.removeAll(toRemoveAsteroids);

        //We should cap the asteroids at 10, too many damn hard to play
        if (asteroids.size()<=asteroidsCap){
            addNewAsteroids();
        }
    }

    private void updatePlayer(){
        if (upPressed) playership.moveForward();
        if (downPressed) playership.moveBackwards();
        if (leftPressed) playership.rotate(-1);
        if (rightPressed) playership.rotate(1);

        if (playership.isInvulnerable()&&invulCountdown>0){
            invulCountdown--;
        }     

        if (invulCountdown<=0){
            playership.makeVulnerable();
        }

    }

    private void addNewAsteroids() {
        // Example: Add a new asteroid with some probability or logic
        Random rand = new Random();
        int decidingFactor=rand.nextInt(100);
        if (decidingFactor>0&&decidingFactor<2){
            asteroids.add(new Asteroid(800, 600, 80, 2));
        }
        else if (decidingFactor>=2&&decidingFactor<5){
            asteroids.add(new Asteroid(800, 600, 40, 4));
        }
        else if (decidingFactor>=4&&decidingFactor<9){
            asteroids.add(new Asteroid(800, 600, 20, 8));
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed=true;
            case KeyEvent.VK_DOWN -> downPressed=true;
            case KeyEvent.VK_LEFT -> leftPressed=true;
            case KeyEvent.VK_RIGHT -> rightPressed=true;
            case KeyEvent.VK_SPACE -> spacePressed=true;

        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed=false;
            case KeyEvent.VK_DOWN -> downPressed=false;
            case KeyEvent.VK_LEFT -> leftPressed=false;
            case KeyEvent.VK_RIGHT -> rightPressed=false;
            case KeyEvent.VK_SPACE -> spacePressed=false;
        }
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        playership.draw(g);

        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }
        
        g.setColor(Color.white);
        g.drawString("Lives: " + lives, 10, 20);

    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    
}
