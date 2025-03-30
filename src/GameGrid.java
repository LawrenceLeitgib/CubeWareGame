import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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

    static double angleForXRotation=0.0;

    static boolean isRotatingLeft=false;
    static boolean isRotatingRight=false;

    static int mousePositionX;
    static int mousePositionY;

    static double mouseAngleInGame;

    static double[] mouseInGame;

    FireBallContainer fireBallContainer;

   static boolean mouseClicked =false;

Enemy enemy;






    GameGrid(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);

        cubeContainer=new CubeContainer(depthRatio);
        fireBallContainer=new FireBallContainer();
        enemy=new Enemy(2,4,2);



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
        //if(mouseClicked) fireBallContainer.createFireBall();


        if(isRotatingLeft){
            angleForXRotation-=Math.PI/64;

        }
        if(isRotatingRight){
            angleForXRotation+=Math.PI/64;
        }
        mouseInGame= mousePosToGamePos(mousePositionX,mousePositionY);
        mouseAngleInGame=mouseAngleInGame(mouseInGame[0],mouseInGame[1]);
      //  System.out.println(mousePositionX+", "+mousePositionY);
        //System.out.println(mouseInGame[0]+", "+mouseInGame[1]);
        cubeContainer.updateData(deltaTime);
        fireBallContainer.updateData(deltaTime);
        enemy.updateData(deltaTime);

        player.updateData(deltaTime);

    }
    public void draw(Graphics g){


        cubeContainer.draw(g);
        g.setColor(Color.red);
        Player.draw(g);
        enemy.draw(g);
        fireBallContainer.draw(g);

        g.setColor(Color.RED);
        g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);

    }
    public double[] mousePosToGamePos(int xPos,int yPos){
        double[] gamePos=new double[2];

        double sizeRatioForMouse=(yPos-PFY)/(PVY-PFY);
        double difPosYRForMouse=((GAME_HEIGHT/sizeRatioForMouse-GAME_HEIGHT)/depthRatio);

           //difPosYR*1.0=(GAME_HEIGHT/sizeRatio-GAME_HEIGHT/depthRatio;
        // double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*depth);
        double yPositionAForMouse=-(difPosYRForMouse/Cube.depth-Player.yPosition-Player.cubeAway);
        //        double difPosXR=((Player.xPosition-xPositionA)*width);
        double mouseNewWidth=  Cube.width*sizeRatioForMouse;
        double xPositionAForMouse=(xPos+mouseNewWidth/2-GameGrid.PVX)/sizeRatioForMouse/Cube.width+Player.xPosition;
        //(newPosX+newWidth/2-GameGrid.PVX)/sizeRatio/width= -((Player.xPosition-xPositionA));

        double correteurXY=0.5;
        //xPositionA-Player.xPosition-xCorrectorForRotation= difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation));
        //yPositionA-Player.yPosition+yCorrectorForRotation= -difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation));
        double A=xPositionAForMouse-Player.xPosition-correteurXY;
        double B=yPositionAForMouse-Player.yPosition+correteurXY;
        double difPosYAForMouse=(B*(Math.cos(angleForXRotation)/Math.sin(angleForXRotation))+A)/(Math.sin(angleForXRotation)+Math.cos(angleForXRotation)*Math.cos(angleForXRotation)/Math.sin(angleForXRotation));
        double difPosXAForMouse=(A*(Math.cos(angleForXRotation)/Math.sin(angleForXRotation))-B)/(Math.sin(angleForXRotation)+Math.cos(angleForXRotation)*Math.cos(angleForXRotation)/Math.sin(angleForXRotation));
        gamePos[1]=difPosYAForMouse-correteurXY+Player.yPosition;
        gamePos[0]=difPosXAForMouse+correteurXY+Player.xPosition;

        if(angleForXRotation==0){
            gamePos[0]=A;
            gamePos[1]=B;
        }
        if(angleForXRotation==Math.PI/2){
            gamePos[0]=-B;
            gamePos[1]=A;
        }
        if(angleForXRotation==Math.PI){
            gamePos[0]=-A;
            gamePos[1]=-B;
        }

        if(angleForXRotation==3*Math.PI/2){
            gamePos[0]=B;
            gamePos[1]=-A;
        }


        return gamePos;
    }

    public double mouseAngleInGame(double xPos,double yPos){
        double angle=0;
        angle=Math.atan((Player.yPosition-yPos)/(Player.xPosition-xPos));

        if(Player.xPosition-xPos>0){
            angle=Math.PI+angle;
        }else  if(Player.xPosition-xPos<0&&Player.yPosition-yPos>0){
            angle=2*Math.PI+angle;
        }


        return angle;
    }


    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case 85:

               // isRotatingRight=false;
                break;
            case 72:


               // isRotatingLeft=false;
                break;

        }

    }

    public void keyPressed(KeyEvent e) {
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
               // isRotatingRight=true;
                break;
            case 72:
                //PFX-=10;
                //PVX- =10;
                //Cube.xAddingNumber-=10;

               // isRotatingLeft=true;
                break;

        }
    }

    public void mouseClicked(MouseEvent e) {
        fireBallContainer.createFireBall();

    }
}
