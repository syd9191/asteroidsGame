package src;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){
        JFrame frame=new JFrame("Asteroids");
        AsteroidsGame gamePanel = new AsteroidsGame();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Thread gameThread= new Thread(gamePanel);
        gameThread.start();
    }
}