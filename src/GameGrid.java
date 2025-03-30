import java.awt.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Player player;
    static int safeZone=20;

    //static int diSpawnZone=50;
    static int regenZone=6;



    int squareLength=40;
    static double gravityAcceleration=60;


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

    static ProjectileContainer fireBallContainer;
   static EntityContainer entityContainer;

   static boolean mouseLeftClickDown =false;
   static boolean mouseRightClickDown=false;



   static double[] cameraPos =new double[2];

    GameGrid(){
        player = new Player(0,0,0);
        cubeContainer=new CubeContainer(depthRatio);
        fireBallContainer=new ProjectileContainer();
        entityContainer =new EntityContainer();
    }

    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
        PFX=GAME_WIDTH/2.0;
        PVX=GAME_WIDTH/2.0;
    }

    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
        if(Player.thirdPerspective)
            PFY=GAME_HEIGHT/3.0;
        else{
            PFY=GAME_HEIGHT/2.0;
        }
        PVY=3*GAME_HEIGHT/3;
    }
    public void newPlayer(){
        player = new Player(0,0,0);

    }
    public double[] moveMouse(double x,double y) throws AWTException {
        double[] delta=new double[2];
        Robot robot = new Robot();
        delta[0]=x-mousePositionX-1;
        //if( delta[0]==0)delta[0]=+.3;
        //System.out.println(mousePositionX);
        delta[1]=y-mousePositionY-1;
        robot.mouseMove((int) (GamePanel.xPos+x-1), (int) (GamePanel.yPos+y-1));

        return delta;
    }
    public void updateData(double deltaTime){
        cameraPos[0]=Player.xPosition-Math.sin(angleForXRotation)*(Player.cubeAway+.5);
        cameraPos[1]=Player.yPosition+Math.cos(angleForXRotation)*(Player.cubeAway+.5);
        if(!Player.thirdPerspective){
            try {
                moveMouse((int) PFX, (int) PFY);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
        }
        double rotationMultiplier=1;
        if(player.isSlowing)rotationMultiplier=player.slowMultiplier*3;
        if(isRotatingLeft){
            angleForXRotation-=Math.PI/32*deltaTime*30*rotationMultiplier;

        }
        if(isRotatingRight){
            angleForXRotation+=Math.PI/32*deltaTime*30*rotationMultiplier;
        }
        while(angleForXRotation>=Math.PI*2){
            angleForXRotation-=Math.PI*2;
        }
        while(angleForXRotation<0){
            angleForXRotation+=Math.PI*2;
        }
        mouseInGame= mousePosToGamePos(mousePositionX,mousePositionY);
        mouseAngleInGame=getMouseAngleInGame(mouseInGame[0],mouseInGame[1]);
        player.updateData(deltaTime);
        entityContainer.updateData(deltaTime);
        fireBallContainer.updateData(deltaTime);
        cubeContainer.updateData(deltaTime);
    }
    public void draw(Graphics g){
        fireBallContainer.draw(g);
        entityContainer.draw(g);
        cubeContainer.draw(g);
        g.setColor(Color.RED);
        if(!Player.thirdPerspective)g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);
    }
    public double[] mousePosToGamePos(int xPos,int yPos){
        double[] gamePos=new double[2];
        if(yPos<=PFY){
           yPos= (int) (PFY+1);

        }
        double sizeRatioForMouse=(yPos-PFY)/(PVY-PFY- ProjectileContainer.ProjectileHeight *Cube.defaultSize);
        double difPosYRForMouse=((GAME_HEIGHT/sizeRatioForMouse-GAME_HEIGHT)/depthRatio);
        double yPositionAForMouse=-(difPosYRForMouse/Cube.depth-Player.yPosition-Player.cubeAway);
        double mouseNewWidth=  Cube.width*sizeRatioForMouse;
        double xPositionAForMouse=(xPos+mouseNewWidth/2-GameGrid.PVX)/sizeRatioForMouse/Cube.width+Player.xPosition;
        double correteurXY=0.5;
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
        /*
        if(mousePositionX==PFX&&mousePositionY==PFY){
            return 3*Math.PI/2;
        }

         */


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


        }
    }

    public void mousePressed(MouseEvent e) {
        if(e.getButton()==1)
            GameGrid.mouseLeftClickDown =true;

        if(e.getButton()==3)GameGrid.mouseRightClickDown =true;

    }
    public void mouseReleased(MouseEvent e) {
        if(e.getButton()==1)
            GameGrid.mouseLeftClickDown =false;

        if(e.getButton()==3)GameGrid.mouseRightClickDown =false;
    }

    public void mouseDragged(MouseEvent e) {
        if(!Player.thirdPerspective&&GamePanel.gameState==GamePanel.GameStates.get("Running")){
            try {
                double[] delta =moveMouse( PFX,  PFY);
                angleForXRotation-=delta[0]/200.0;
            } catch (AWTException e2) {
                throw new RuntimeException(e2);
            }


        }
        mousePositionX =e.getX();
        mousePositionY =e.getY();



        mouseInGame= mousePosToGamePos(mousePositionX,mousePositionY);
        mouseAngleInGame=getMouseAngleInGame(mouseInGame[0],mouseInGame[1]);

    }

    public void mouseMoved(MouseEvent e) {


        if(!Player.thirdPerspective &&GamePanel.gameState==GamePanel.GameStates.get("Running")){
            try {
                double[] delta =moveMouse((int) PFX, (int) PFY);
                angleForXRotation-=delta[0]/200.0;
            } catch (AWTException e2) {
                throw new RuntimeException(e2);
            }
        }
        mousePositionX =e.getX();
        mousePositionY =e.getY();
        mouseInGame= mousePosToGamePos(mousePositionX,mousePositionY);
        mouseAngleInGame=getMouseAngleInGame(mouseInGame[0],mouseInGame[1]);






        // System.out.println("truc "+mouseAngleInGame+", "+mouseInGame[0]+","+mouseInGame[1]);
    }



}
