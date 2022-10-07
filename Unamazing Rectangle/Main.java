import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main{
    static JFrame window = new JFrame();
    static GamePanel gamePanel = new GamePanel(500, 800);
    static boolean visibility = false;

    public static void startGame(ScorePanel panel){
       gamePanel.setVisible(true);
       window.remove(panel);
    }

    public static void openScorePanel(GamePanel gamePanell, int width, int height, int score){
        try {
            window.add(new ScorePanel(width, height, score));
        } catch (Exception e) {
            e.printStackTrace();
        }
        gamePanell.setVisible(false);
    }

    public static void main(String[] args){
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Unamazing Rectangle");
        ImageIcon icon = new ImageIcon("Icon.png");
        window.setIconImage(icon.getImage());
        window.add(gamePanel);
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }
}