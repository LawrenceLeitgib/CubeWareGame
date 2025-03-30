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

    static FireBallContainer fireBallContainer;
   static  EnemiesContainer enemiesContainer;

   static boolean mouseLeftClickDown =false;
   static boolean mouseRightClickDown=false;

   static boolean oneDown=false;

   static boolean oneHaveBeenRelesed=false;

    //Enemy enemy;







    GameGrid(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);

        cubeContainer=new CubeContainer(depthRatio);
        fireBallContainer=new FireBallContainer();
        enemiesContainer=new EnemiesContainer();





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
       // System.out.println(mouseLeftClickDown+", "+mouseRightClickDown);

        if(isRotatingLeft){
            angleForXRotation-=Math.PI/64*deltaTime*30;

        }
        if(isRotatingRight){
            angleForXRotation+=Math.PI/64*deltaTime*30;
        }
        while(angleForXRotation>=Math.PI*2){
            angleForXRotation-=Math.PI*2;
        }
        while(angleForXRotation<0){
            angleForXRotation+=Math.PI*2;
        }
        mouseInGame= mousePosToGamePos(mousePositionX,mousePositionY);
        mouseAngleInGame=getMouseAngleInGame(mouseInGame[0],mouseInGame[1]);
      //  System.out.println(mousePositionX+", "+mousePositionY);
      //  System.out.println(mouseInGame[0]+", "+mouseInGame[1]);
        //System.out.println(mouseAngleInGame);
        cubeContainer.updateData(deltaTime);
        fireBallContainer.updateData(deltaTime);
        enemiesContainer.updateData(deltaTime);

        player.updateData(deltaTime);

    }
    public void draw(Graphics g){
        while(angleForXRotation>=Math.PI*2){
            angleForXRotation-=Math.PI*2;
        }
        while(angleForXRotation<0){
            angleForXRotation+=Math.PI*2;
        }

        cubeContainer.draw(g);
        g.setColor(Color.red);
        Player.draw(g);
        fireBallContainer.draw(g);
        enemiesContainer.draw(g);

        g.setColor(Color.RED);
        g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);
      //  System.out.println(mouseInGame[0]+", " +mouseInGame[1]);
    }
    public double[] mousePosToGamePos(int xPos,int yPos){
        double[] gamePos=new double[2];
        if(yPos<PFY){
           yPos= (int) (PFY+1);

        }

        double sizeRatioForMouse=(yPos-PFY)/(PVY-PFY-80);
        //double sizeRatioForMouse=(yPos-PFY)/(PVY-PFY);
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
        gamePos[1]=difPosYAForMouse+Player.yPosition;
        gamePos[0]=difPosXAForMouse+Player.xPosition;






        if(angleForXRotation==0){
            gamePos[0]=A+Player.xPosition;
            gamePos[1]=B+Player.yPosition;
        }
        if(angleForXRotation==Math.PI/2){
            gamePos[0]=-B+Player.xPosition;
            gamePos[1]=A+Player.yPosition;
        }
        if(angleForXRotation==Math.PI){
            gamePos[0]=-A+Player.xPosition;
            gamePos[1]=-B+Player.yPosition;
        }

        if(angleForXRotation==3*Math.PI/2){
            gamePos[0]=B+Player.xPosition;
            gamePos[1]=-A+Player.yPosition;
        }


        return gamePos;
    }

    public double getMouseAngleInGame(double xPos,double yPos){
        double angle=0;
        angle=Math.atan((Player.yPosition-yPos)/(Player.xPosition-xPos));


        if(Player.xPosition-xPos>0){
            angle=Math.PI+angle;
        }else  if(Player.xPosition-xPos<0&&Player.yPosition-yPos>0){
            angle=2*Math.PI+angle;
        }
        if(Player.xPosition-xPos==0){
            if(Player.yPosition-yPos<=0)angle=Math.PI/2;
            if(Player.yPosition-yPos>0)angle=3*Math.PI/2;

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
            case 49:
                oneDown=false;
                oneHaveBeenRelesed=true;
        }

    }

    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 75:
                PFY+=50;
                depthRatio=GAME_HEIGHT/(PVY-PFY);
                break;
            case 79:
                PFY-=50.0;
                depthRatio=GAME_HEIGHT/(PVY-PFY);
                break;
            case 73:
                PVY+=20;
                break;
            case 74:
                PVY-=20;
                break;
            case 49:
                if(oneHaveBeenRelesed){
                    oneDown=true;

                    oneHaveBeenRelesed=false;
                }

        }
    }

    public void mouseClicked(MouseEvent e) {
        //fireBallContainer.createFireBall();

    }
}
