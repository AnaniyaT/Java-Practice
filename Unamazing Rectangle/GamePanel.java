import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class GamePanel extends JPanel implements KeyListener, ActionListener {
    int width;
    int height;
    int score = 0;
    int plyrSize = 25;
    int delay = 8;
    int x = 250;
    int y = 450;
    double xVel = 0;
    double yVel = 0;
    double grav = 0.2;
    double HJump = 1;
    double VJump = -8;
    boolean fall = false;
    boolean play = false;
    boolean steadySideFall = false;
    Font font = new Font ("Lucida Sans", Font.BOLD , 40);
    Timer timer;
    ObList list;

    GamePanel(int width, int height){
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width,height));
        addKeyListener(this);
        setFocusable(true);
        this.setBackground(Color.WHITE);
        this.list = new ObList();
        list.generateObs(score, width, 100);
        this.timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.fillRect(x,y,plyrSize,plyrSize);
        list.drawObs(g);
        g.setFont(font);
        g.drawString(Integer.toString(score), width - 85, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(play){
            if (list.isGameLost(x, y, plyrSize, height)){
                loseGame();
            }
            handleMovement();
            if(x == 0 || x == width - plyrSize){
                steadySideFall = true;
            }
            if(list.theY > y){
                score++;
                list.generateObs(score, width, -150);
            }
            list.checkOutOfBounds(height);
        }
        repaint();
    }

    public void loseGame(){
        try {
            updateHighScore();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        play = false;
        try {
            Thread.sleep(700);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        x = 250;
        y = 450;
        xVel = 0;
        yVel = 0;
        int tempScore = score;
        score = 0;
        list.removeAllObs();
        list.generateObs(score, width, 100);
        play = false;
        Main.openScorePanel(this, width, height,tempScore);
    }

    public void handleMovement(){
        if(y < 350 && yVel < 0){
            list.moveObs(-new Double(yVel).intValue());
        }else if (steadySideFall){
            y += 2;
        }else{
            y += yVel;
        }
        if(!steadySideFall){
        x += xVel;
        }
        yVel += grav;
    }

    public void updateHighScore() throws Exception{
        int prevscore = 0;
        BufferedReader reader = new BufferedReader(new FileReader("HighScore.txt"));
        String str;
        while((str = reader.readLine()) != null){
            prevscore = Integer.parseInt(str);
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter("HighScore.txt"));
        writer.write(Integer.toString(Math.max(score, prevscore)));
        writer.close();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(e.getKeyCode()== KeyEvent.VK_LEFT){
            if(!play){play = true;}
            if(steadySideFall){
                if(x != 0){
                yVel = VJump;
                xVel = -HJump;
                steadySideFall = false;
                }
            }else{
            yVel = VJump;
            xVel = -HJump;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            if(!play){play = true;}
            if(steadySideFall){
                if(x != width - plyrSize){
                steadySideFall = false;
                yVel = VJump;
                xVel = HJump;
                }
            }else{
            yVel = VJump;
            xVel = HJump;
            }
        }
        
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            play = !play;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
