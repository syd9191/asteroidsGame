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
    final Score score;
    final Lives lives;
    final GameOver gameOver;

    final int asteroidsCap=15;
    final int bulletFireRate=15;
    final int invulTimer=100;
    final int screenWidth=800;
    final int screenHeight=600;

    final List<Asteroid> asteroids;
    final List<Bullet> bullets;
    final List<Asteroid> toRemoveAsteroidsOOB;
    final List<Asteroid> toRemoveAsteroids;
    final List<Bullet> toRemoveBullets;
    final List<Asteroid> toAddAsteroids;

    private int bulletCountdown=0;
    private int invulCountdown=0;
    private boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, running;

    public AsteroidsGame(){
        running=true;
        asteroids = new ArrayList<>();
        bullets= new ArrayList<>();
        toAddAsteroids= new ArrayList<>();
        toRemoveAsteroids= new ArrayList<>();
        toRemoveAsteroidsOOB= new ArrayList<>();
        toRemoveBullets= new ArrayList<>();
        playership=new PlayerShip(screenWidth/2,screenHeight/2);
        score= new Score(screenWidth, screenHeight);
        lives= new Lives(5, screenWidth, screenHeight);
        gameOver= new GameOver(screenWidth, screenHeight);

        setPreferredSize(new Dimension(screenWidth, screenHeight));
        addKeyListener(this); 
        setFocusable(true);
    }

    @Override
    public void run(){
        while (running){
            updatePlayer();
            updateObjects();
            repaint();
            gameOver();
            try {
                Thread.sleep(16);
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        updatePlayer();
        updateObjects();
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

    private void updateObjects(){
        if (bulletCountdown>0){
            bulletCountdown--;
        }
        shoot();
        asteroidCollisions();
        bulletCollisions();
        asteroidSplitting();
        asteoroidsCleaning();

        if (asteroids.size()<=asteroidsCap){
            addNewAsteroids();
        }
    }

    private void gameOver(){
        if (lives.getLives()==0){
            running=false;
        }
    }


    private void bulletCollisions(){
        for (Bullet existingBullet:bullets){
            existingBullet.update();
            if (existingBullet.outOfBoundsCheck()){
                toRemoveBullets.add(existingBullet);
            }
            for (Asteroid existingAsteroid: asteroids){
                bulletCollisionWithAsteroid(existingAsteroid, existingBullet, score);
            }
        }
    }

    private void asteroidCollisions(){
        for (Asteroid existingAsteroid: asteroids){
            playerCollisionWithAsteroid(existingAsteroid);
            existingAsteroid.update();
            if (existingAsteroid.outOfBoundsCheck()) {
                toRemoveAsteroidsOOB.add(existingAsteroid); // Respawn a new asteroid
            }
        }
    }

    private void shoot(){
        //shooting logic
        if (spacePressed&&bulletCountdown==0){
            bullets.add(new Bullet(playership.getX(), 
                        playership.getY(), 
                        playership.getAngle()));
            bulletCountdown=bulletFireRate;
        } 
    }

    private void asteroidSplitting(){
        //splitting mechanic for asteroids, does not return anything
        for (Asteroid destroyedAsteroid: toRemoveAsteroids){
            int asteroidType=destroyedAsteroid.getType();
            int counter=0;
            switch (asteroidType){
                case 3:
                    counter=4;
                    break;
                case 2:
                    counter=2;
                    break;
            }
            for (int numAsteroids=0; numAsteroids<counter; numAsteroids++){
                Asteroid splitAsteroid=new Asteroid(screenWidth, 
                                                screenHeight, 
                                                destroyedAsteroid.getType()-1, 
                                                destroyedAsteroid.getX(), 
                                                destroyedAsteroid.getY());
                toAddAsteroids.add(splitAsteroid);
            }
        }
    }

    private void asteoroidsCleaning(){
        asteroids.addAll(toAddAsteroids);
        toAddAsteroids.clear();

        bullets.removeAll(toRemoveBullets);
        toRemoveBullets.clear();

        asteroids.removeAll(toRemoveAsteroidsOOB);
        toRemoveAsteroidsOOB.clear();

        asteroids.removeAll(toRemoveAsteroids);
        toRemoveAsteroids.clear();
    }

    private void playerCollisionWithAsteroid(Asteroid existingAsteroid){
        if (playership.checkCollision(existingAsteroid)){
            if (playership.isInvulnerable()==false){
                playerHit();
            }
        }
    }

    private void bulletCollisionWithAsteroid(Asteroid existingAsteroid, Bullet existingBullet, Score score){
        if (existingBullet.checkCollision(existingAsteroid)){
            toRemoveBullets.add(existingBullet);
            toRemoveAsteroids.add(existingAsteroid);
            score.destroyAsteroid();
        }
    }

    private void playerHit(){
        lives.deduct();
        playership.makeInvulnerable();
        invulCountdown=invulTimer;
    }

    private void addNewAsteroids() {
        // Example: Add a new asteroid with some probability or logic
        Random rand = new Random();
        int decidingFactor=rand.nextInt(100);
        if (decidingFactor>0&&decidingFactor<5){
            asteroids.add(new Asteroid(screenWidth, screenHeight, 3 , -1, -1));
        }
        else if (decidingFactor>=6&&decidingFactor<20){
            asteroids.add(new Asteroid(screenWidth, screenHeight, 2, -1, -1));
        }
        else if (decidingFactor>=20&&decidingFactor<40){
            asteroids.add(new Asteroid(screenWidth, screenHeight, 1, -1, -1));
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

        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(g);
        }

        playership.draw(g); //this order of asteroid and bullets then playership score and lives MUST BE MAINTAINED for right layering
        lives.draw(g);
        if (lives.getLives()==0){
            gameOver.draw(g, score.getScore());
            return;
        }
        score.draw(g);
        
    }

    @Override
    public void keyTyped(KeyEvent e){
    }

    
}
