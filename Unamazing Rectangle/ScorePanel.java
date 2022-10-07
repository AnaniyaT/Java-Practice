import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Graphics;
import java.awt.Font;

public class ScorePanel extends JPanel implements KeyListener {
    String currentScore;
    String highScore;
    Font font1 = new Font ("Lucida Sans", Font.BOLD , 70);
    Font font2 = new Font ("Lucida Sans", Font.BOLD , 60);

    ScorePanel(int width, int height, int score) throws Exception{
        this.currentScore = Integer.toString(score);
        BufferedReader reader = new BufferedReader(new FileReader("HighScore.txt"));
        String str;
        while((str = reader.readLine()) != null){
            this.highScore = str;
        }
        reader.close();
        this.setPreferredSize(new Dimension(width, height));
        addKeyListener(this);
        this.setFocusable(true);
        this.setBackground(Color.white);
        this.setVisible(true);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawRoundRect(50, 150, 400, 500, 30, 30);
        g.setFont(font1);
        g.drawString("Score", 150, 290);
        g.drawString("Best", 160, 480);
        g.setFont(font2);
        g.drawString(currentScore, 235 - (15*currentScore.length()), 370);
        g.drawString(highScore, 235 - (15*highScore.length()), 560);
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER){
            Main.startGame(this);
        }
        
    }

    @Override
    public void keyReleased(KeyEvent arg0) {}
    @Override
    public void keyTyped(KeyEvent arg0) {}
    
}
