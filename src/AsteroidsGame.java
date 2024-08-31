package src;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;


public class AsteroidsGame extends JPanel implements Runnable, KeyListener {

    final PlayerShip playership;
    final boolean running;
    private boolean upPressed, downPressed, leftPressed, rightPressed;


    public AsteroidsGame(){
        playership=new PlayerShip(400,300); //start new playership in the centre of the screen
        setPreferredSize(new Dimension(800, 600));
        addKeyListener(this);   //this is to listen to inputs
        setFocusable(true);
        running=true;
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


        if (leftPressed) playership.rotate(1);
        if (rightPressed) playership.rotate(-1);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed=true;
            case KeyEvent.VK_DOWN -> downPressed=true;
            case KeyEvent.VK_LEFT -> leftPressed=true;
            case KeyEvent.VK_RIGHT -> rightPressed=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> upPressed=false;
            case KeyEvent.VK_DOWN -> downPressed=false;
            case KeyEvent.VK_LEFT -> leftPressed=false;
            case KeyEvent.VK_RIGHT -> rightPressed=false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        playership.draw(g);
    }

    
}
