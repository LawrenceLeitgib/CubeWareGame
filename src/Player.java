import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    double xPosition;
    double yPosition;
    double zPosition;

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    int width=20;
    int height=20;

    int speed=6;

    int xVelocity;
    int yVelocity;

    boolean isSlowing=false;

    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double PositionZ){
        this.xPosition=positionX;
        this.yPosition=positionY;
        this.zPosition=PositionZ;
        this.GAME_WIDTH=GAME_WIDTH;
        this.GAME_HEIGHT=GAME_HEIGHT;



    }
    public void updateData(double deltaTime){
        if (isSlowing==true) {
            this.xPosition += this.xVelocity/10.0;
            this.yPosition += this.yVelocity/10.0;
        }else{
            this.xPosition += this.xVelocity;
            this.yPosition += this.yVelocity;
        }

    }

    public void setXVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }


    public void draw(Graphics g){
        g.setColor(new Color(3, 40, 252));
        g.fillRect(GAME_WIDTH/2-width/2,2*GAME_HEIGHT/3-height/2,width,height);
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.PLAIN,16));
        g.setColor(Color.red);
        g.drawString(String.valueOf(yPosition),15,90);
        g.drawString(Double.toString(xPosition),15,70);
    }


    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 65:
                setXVelocity(-speed);
                break;

            case 68:
                setXVelocity(speed);
                break;
            case 83:
                setYVelocity(speed);
                break;

            case 87:
                setYVelocity(-speed);
                break;
            case 16:
                isSlowing=true;
                break;

        }

    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case 65:
                if(xVelocity<0)
                setXVelocity(0);
                break;

            case 68:
                if(xVelocity>0)
                setXVelocity(0);
                break;
            case 83:
                if(yVelocity>0)
                setYVelocity(0);
                break;

            case 87:
                if(yVelocity<0)
                setYVelocity(0);
                break;
            case 16:
                isSlowing=false;
                break;
        }
    }
}
