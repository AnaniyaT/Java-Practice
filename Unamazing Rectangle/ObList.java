import java.awt.Graphics;
import java.util.Random;
import java.awt.Color;

public class ObList {
    int end;
    int[][] obArray;
    int theY;

    Color blueish = new Color(42, 225, 215);
    Color oceanBlue = new Color(52, 168, 235);
    Color orangish = new Color(240, 146, 84);
    Color lightgreen = new Color(87, 240, 84);
    Color violet = new Color(217, 84, 240);
    Color[] colorArray = new Color[]{blueish, orangish, oceanBlue, lightgreen, violet};

    ObList(){
        this.end = 0;
        this.obArray = new int[10][5];
    }

    public void addOb(int x, int y, int width, int height, int colorIndex){
        for(int i = end; i > 0; i--){
            obArray[i] = obArray[i-1];
        }
        obArray[0] = new int[]{x, y, width, height, colorIndex};
        end++;
    }

    public void removeOb(){
        end--;
    }

    public void drawObs(Graphics g){
        int[][] arr = obArray;
        for(int i = 0; i < end; i++){
            g.setColor(colorArray[arr[i][4]]);
            g.fillRect(arr[i][0],arr[i][1],arr[i][2],arr[i][3]);
        }
        g.setColor(Color.BLACK);
    }

    public void moveObs(int vel){
        for(int i = 0; i < end; i++){
            obArray[i][1] += vel;
        }
        theY += vel;
    }

    public void removeAllObs(){
        end = 0;
    }

    public void generateObs(int score, int width, int y){
        this.theY = y;
        Random random = new Random();
        int obSize = 30;
        int obH = 40;
        int gap = 150;
        int ob2W = 150 + random.nextInt(width-300-gap);
        int ob1YOff = 35 + random.nextInt(30);
        int ob4YOff = 35 + random.nextInt(30);
        int ob1XOff = random.nextInt(gap-obSize);
        int ob4XOff = random.nextInt(gap-obSize);
        int ob1X = ob2W + ob1XOff;
        int ob1Y = y + obH + ob1YOff;
        int ob2Y = y;
        int ob3X = ob2W + gap;
        int ob3Y = y;
        int ob3W = width - ob3X;
        int ob4X = ob2W + ob4XOff;
        int ob4Y = y - ob4YOff - obSize;
        int colorIndex = (score/4)%5;
        addOb(ob1X, ob1Y, obSize, obSize, colorIndex);
        addOb(0, ob2Y, ob2W, obH, colorIndex);
        addOb(ob3X, ob3Y, ob3W + 20, obH, colorIndex);
        addOb(ob4X, ob4Y, obSize, obSize, colorIndex);
    }

    public void checkOutOfBounds(int height){
        for(int i = 0; i < end; i++){
            int obY = obArray[i][1];
            if(height < obY){
                removeOb();
            }
        }
    }

    public boolean isGameLost(int x, int y, int plyrSize, int height){
        for(int i = 0; i < end; i++){
            int obY = obArray[i][1];
            int obX = obArray[i][0];
            int obW = obArray[i][2];
            int obH = obArray[i][3];
            if((x < obX + obW &&
            x + plyrSize > obX &&
            y < obY + obH &&
            plyrSize + y > obY)||
            y + plyrSize > height)
            {
                return true;
            }
        }
        return false;
    }

}