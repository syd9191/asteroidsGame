import javax.swing.JFrame;
import src.AsteroidsGame;

public class Main {
    public static void main(String[] args){
        JFrame frame=new JFrame("Asteroids");
        AsteroidsGame gamePanel = new AsteroidsGame();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        gamePanel.setOpaque(false);

        Thread gameThread= new Thread(gamePanel);
        gameThread.start();
    }
}