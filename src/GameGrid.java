import java.awt.*;
import java.awt.event.KeyEvent;

public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Player player;



    int squareLength=40;

    static double PFX;
    static double  PFY;
    static  double PVX;
    static double PVY;
    CubeContainer cubeContainer;

    static double depthRatio=1.0;




    GameGrid(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);

        cubeContainer=new CubeContainer(depthRatio);

    }

    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
        PFX=GAME_WIDTH/2.0;
        PVX=GAME_WIDTH/2.0;
    }

    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
        PFY=0*GAME_HEIGHT/8.0;
        PVY=3*GAME_HEIGHT/3;
    }
    public void newPlayer(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);

    }
    public void updateData(double deltaTime){
        cubeContainer.updateData(deltaTime);
        player.updateData(deltaTime);

    }
    public void draw(Graphics g){


        cubeContainer.draw(g);
        g.setColor(Color.red);
        Player.draw(g);
        g.setColor(Color.RED);
        g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);

    }


    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case 75:
                PFY+=50;
               // PVY+=20;
                //depthRatio+=0.02;
                depthRatio=GAME_HEIGHT/(PVY-PFY);
                //Player.cubeAway=-GAME_HEIGHT/(PVY-PFY)+2;
               // Player.cubeAway=GAME_HEIGHT/((GAME_HEIGHT-PFY)*(GAME_HEIGHT-PFY));
                //System.out.println(depthRatio+" "+Player.cubeAway);
               // System.out.println(Player.cubeAway);
               // PFX+=10;
                break;
            case 79:
                PFY-=50.0;
                //PVY-=20;
                //depthRatio-=0.02;
                depthRatio=GAME_HEIGHT/(PVY-PFY);
                //Player.cubeAway=-GAME_HEIGHT/(PVY-PFY)+2;
               // Player.cubeAway=GAME_HEIGHT/((GAME_HEIGHT-PFY)*(GAME_HEIGHT-PFY));
                //PFX-=10;
                break;
            case 73:
                PVY+=20;
                //Player.cubeAway+=0.5;
                //Player.cubeAway=GAME_HEIGHT/(PVY-PFY)+2;
               // System.out.println(PVY);
                //PVX+=10;
                break;
            case 74:
                PVY-=20;
                //Player.cubeAway=GAME_HEIGHT/(PVY-PFY)+2;
                //Player.cubeAway-=0.5;
               // PVX-=10;
                break;
            case 85:
                //PFX+=10;
                //PVX+=10;
                //Cube.xAddingNumber+=10;
                Cube.angleForXRotation+=Math.PI/16;
                break;
            case 72:
                //PFX-=10;
                //PVX- =10;
                //Cube.xAddingNumber-=10;
                Cube.angleForXRotation-=Math.PI/16;
                break;

        }

    }

    public void keyPressed(KeyEvent e) {
    }
}
