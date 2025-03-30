import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    static double xPosition;
    static double yPosition;
    static double zPosition;

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    int width=40;
    int height=40;

    double speed=10;

    double xVelocity;
    double yVelocity;

    boolean isSlowing=false;

    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double PositionZ){
        Player.xPosition=positionX;
        Player.yPosition=positionY;
        Player.zPosition=PositionZ;
        Player.GAME_WIDTH =GAME_WIDTH;
        Player.GAME_HEIGHT =GAME_HEIGHT;



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

    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }

    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }

    public void draw(Graphics g){
        g.setColor(new Color(3, 40, 252));
        //g.fillRect(GAME_WIDTH/2-width/2,2*GAME_HEIGHT/3-height/2,width,height);
        g.fillOval(GAME_WIDTH/2-width/2, (int) (2*GAME_HEIGHT/3-height*CubeContainer.depthRatio/4),width, (int) (height*CubeContainer.depthRatio/2));


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
