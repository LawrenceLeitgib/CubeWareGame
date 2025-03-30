import java.awt.*;
import java.awt.event.*;

public class InputHandler {
    static int mousePositionX;
    static int mousePositionY;
    static double mouseAngleInGame;
    static double[] mouseInGame;
    static boolean mouseLeftClickDown =false;
    static boolean mouseRightClickDown=false;
    private double flyingCount=0;
    boolean spaceHasBeenClick;
    boolean spaceHasBeenReleased;
    public boolean F3Down=true;
    static boolean oneDown=false;
    static boolean oneHaveBeenReleased =true;
    public InputHandler() {

    }
    public void update(double deltaTime){
        flySwitchHandler(deltaTime);
        if(oneDown){
            GameGrid.player.SMH.attackSpecial1();
            oneDown=false;

        }
        if(!GameGrid.player.thirdPerspective){
            try {
                moveMouse((int) GameGrid.PFX, (int) GameGrid.PFY);
            } catch (AWTException e) {
                throw new RuntimeException(e);
            }
        }
        mouseInGame= mousePosToGamePos(mousePositionX, mousePositionY);
        mouseAngleInGame=getMouseAngleInGame(mouseInGame[0], mouseInGame[1]);
    }
    private void toggleF3(){
        F3Down= !F3Down;
    }
    private void flySwitchHandler(double deltaTime){
        if(flyingCount>0){
            flyingCount+=deltaTime;
            if(spaceHasBeenClick){
                GameGrid.player.toggleFly();
                spaceHasBeenClick=false;
            }
        }
        if(spaceHasBeenClick){
            flyingCount+=deltaTime;
            spaceHasBeenClick=false;
        }
        double flyingTime = .2;
        if(flyingCount> flyingTime){
            flyingCount=0;
        }
    }
    public double[] moveMouse(double x,double y) throws AWTException {
        double[] delta=new double[2];
        Robot robot = new Robot();
        delta[0]=x- InputHandler.mousePositionX-1;
        delta[1]=y- InputHandler.mousePositionY-1;
        robot.mouseMove((int) (GamePanel.xPos+x-1), (int) (GamePanel.yPos+y-1));
        return delta;
    }
    public double[] mousePosToGamePos(int xPos,int yPos){
        double[] gamePos=new double[2];
        if(yPos<= GameGrid.PFY){
            yPos= (int) (GameGrid.PFY +1);

        }
        double sizeRatioForMouse=(yPos- GameGrid.PFY)/(GameGrid.PVY - GameGrid.PFY - ProjectileContainer.ProjectileHeight * GameGrid.defaultSize);
        double difPosYRForMouse=((GameGrid.GAME_HEIGHT /sizeRatioForMouse- GameGrid.GAME_HEIGHT)/ GameGrid.depthRatio);
        double yPositionAForMouse=-(difPosYRForMouse/ GameGrid.defaultSize -GameGrid.player.yPosition- GameGrid.distancePlayerCamera);
        double mouseNewWidth=  GameGrid.defaultSize *sizeRatioForMouse;
        double xPositionAForMouse=(xPos+mouseNewWidth/2- GameGrid.PVX)/sizeRatioForMouse/ GameGrid.defaultSize +GameGrid.player.xPosition;
        double correctorXY=0.5;
        double A=xPositionAForMouse-GameGrid.player.xPosition-correctorXY;
        double B=yPositionAForMouse-GameGrid.player.yPosition+correctorXY;
        double difPosYAForMouse=(B*(Math.cos(GameGrid.angleForHorizontalRotation)/Math.sin(GameGrid.angleForHorizontalRotation))+A)/(Math.sin(GameGrid.angleForHorizontalRotation)+Math.cos(GameGrid.angleForHorizontalRotation)*Math.cos(GameGrid.angleForHorizontalRotation)/Math.sin(GameGrid.angleForHorizontalRotation));
        double difPosXAForMouse=(A*(Math.cos(GameGrid.angleForHorizontalRotation)/Math.sin(GameGrid.angleForHorizontalRotation))-B)/(Math.sin(GameGrid.angleForHorizontalRotation)+Math.cos(GameGrid.angleForHorizontalRotation)*Math.cos(GameGrid.angleForHorizontalRotation)/Math.sin(GameGrid.angleForHorizontalRotation));
        gamePos[1]=difPosYAForMouse+GameGrid.player.yPosition;
        gamePos[0]=difPosXAForMouse+GameGrid.player.xPosition;

        if(GameGrid.angleForHorizontalRotation ==0){
            gamePos[0]=A+GameGrid.player.xPosition;
            gamePos[1]=B+GameGrid.player.yPosition;
        }
        if(GameGrid.angleForHorizontalRotation ==Math.PI/2){
            gamePos[0]=-B+GameGrid.player.xPosition;
            gamePos[1]=A+GameGrid.player.yPosition;
        }
        if(GameGrid.angleForHorizontalRotation ==Math.PI){
            gamePos[0]=-A+GameGrid.player.xPosition;
            gamePos[1]=-B+GameGrid.player.yPosition;
        }

        if(GameGrid.angleForHorizontalRotation ==3*Math.PI/2){
            gamePos[0]=B+GameGrid.player.xPosition;
            gamePos[1]=-A+GameGrid.player.yPosition;
        }
        return gamePos;
    }
    public double getMouseAngleInGame(double xPos,double yPos){
        double angle = Math.atan((GameGrid.player.yPosition - yPos) / (GameGrid.player.xPosition - xPos));
        if(GameGrid.player.xPosition-xPos>0){
            angle=Math.PI+angle;
        }else  if(GameGrid.player.xPosition-xPos<0&& GameGrid.player.yPosition -yPos>0){
            angle=2*Math.PI+angle;
        }
        if(GameGrid.player.xPosition-xPos==0){
            if(GameGrid.player.yPosition -yPos<=0)angle=Math.PI/2;
            if(GameGrid.player.yPosition -yPos>0)angle=3*Math.PI/2;

        }
        return angle;
    }
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode()+" = "+e.getKeyChar());
        GameGrid.player.keyPressed(e);
        GameGrid.player.SMH.keyPressed(e);
        GameGrid.keyPressed(e);
        switch(e.getKeyCode()){
            case 32->{
                if(spaceHasBeenReleased){
                    spaceHasBeenClick=true;
                    spaceHasBeenReleased=false;
                }
            }
            case 114-> toggleF3();
            case 49-> {
                if (oneHaveBeenReleased) {
                    oneDown = true;
                    oneHaveBeenReleased = false;
                }
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        GameGrid.player.keyReleased(e);
        GameGrid.player.SMH.keyReleased(e);
        switch (e.getKeyCode()){
            case 32->spaceHasBeenReleased = true;
            case 49 -> {
                oneDown = false;
                oneHaveBeenReleased = true;
            }
        }

    }
    public void mousePressed(MouseEvent e){
        if(e.getButton()==1)
            mouseLeftClickDown =true;
        if(e.getButton()==3) mouseRightClickDown =true;
    }
    public void mouseReleased(MouseEvent e){
        if(e.getButton()==1)
            mouseLeftClickDown =false;
        if(e.getButton()==3) mouseRightClickDown =false;
    }
    public void mouseDragged(MouseEvent e){
        if(!GameGrid.player.thirdPerspective&&GamePanel.gameState==GamePanel.GameStates.get("Running")){
            try {
                double[] delta =moveMouse(GameGrid.PFX, GameGrid.PFY);
                GameGrid.angleForHorizontalRotation -=delta[0]/200.0;
            } catch (AWTException e2) {
                throw new RuntimeException(e2);
            }
        }
        mousePositionX =e.getX();
        mousePositionY =e.getY();
        mouseInGame= mousePosToGamePos(InputHandler.mousePositionX, InputHandler.mousePositionY);
        mouseAngleInGame=getMouseAngleInGame(InputHandler.mouseInGame[0], InputHandler.mouseInGame[1]);
    }
    public void mouseMoved(MouseEvent e){
        if(!GameGrid.player.thirdPerspective &&GamePanel.gameState==GamePanel.GameStates.get("Running")){
            try {
                double[] delta =moveMouse((int) GameGrid.PFX, (int) GameGrid.PFY);
                GameGrid.angleForHorizontalRotation -=delta[0]/200.0;
            } catch (AWTException e2) {
                throw new RuntimeException(e2);
            }
        }
        mousePositionX =e.getX();
        mousePositionY =e.getY();
        mouseInGame= mousePosToGamePos(mousePositionX, mousePositionY);
        mouseAngleInGame=getMouseAngleInGame(mouseInGame[0], mouseInGame[1]);


    }
}
