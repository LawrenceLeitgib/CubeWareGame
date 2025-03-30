import java.awt.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static int safeZone=20;
    static int regenZone=6;
    static double gravityAcceleration=60;
    static double PFX;
    static double  PFY;
    static  double PVX;
    static double PVY;
    static int numOfChunkToDraw=3;
    static int defaultSize=100;
    static boolean[] bigZLayer=new boolean[Chunk.numOfCubeZ];
    CubeContainer cubeContainer;
    static double depthRatio=1;
    static double angleForXRotation=0.0;
    static boolean isRotatingLeft=false;
    static boolean isRotatingRight=false;
    static int mousePositionX;
    static int mousePositionY;
    static double mouseAngleInGame;
    static double[] mouseInGame;
    static ProjectileContainer fireBallContainer;
    static EntityContainer entityContainer;
    Player player;
    static boolean mouseLeftClickDown =false;
    static boolean mouseRightClickDown=false;
    static double[] cameraPos =new double[2];
    static boolean F3Down=true;
    GameGrid(int GAME_WIDTH,int GAME_HEIGHT) {
        setGameWidth(GAME_WIDTH);
        setGameHeight(GAME_HEIGHT);
        player = new Player(0, 0, 0);
        cubeContainer = new CubeContainer();
        fireBallContainer = new ProjectileContainer();
        entityContainer = new EntityContainer();
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
        PVY=GAME_HEIGHT;
    }
    public double[] moveMouse(double x,double y) throws AWTException {
        double[] delta=new double[2];
        Robot robot = new Robot();
        delta[0]=x-mousePositionX-1;
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

    public void bigZLayerHandler(){
        for(var k=0;k<Chunk.numOfCubeZ;k++){
            GameGrid.bigZLayer[k]=false;
        }
        for(var k=0;k<Chunk.numOfCubeZ;k++){
            if(ProjectileContainer.zLayers[k]|| EntityContainer.zLayers[k]){
                GameGrid.bigZLayer[k]=true;
            }
        }
        for(int i = Player.chunkIn[0]- GameGrid.numOfChunkToDraw; i<=Player.chunkIn[0]+ GameGrid.numOfChunkToDraw; i++){
            for(int j = Player.chunkIn[1]- GameGrid.numOfChunkToDraw; j<=Player.chunkIn[1]+ GameGrid.numOfChunkToDraw; j++){
                for(var k=0;k<Chunk.numOfCubeZ;k++){
                    if(CubeContainer.chunks[i+CubeContainer.numOfChunkX][j+CubeContainer.numOfChunkY].zLayer[k]){
                        GameGrid.bigZLayer[k]=true;
                    }
                }
            }
        }
    }
    public boolean checkToSkip3(int i){
        double[] info1=Cube.getObjectScreenPos(Player.xPosition,Player.yPosition,i+1);
        return info1[1] < GameGrid.PFY;
    }
    public void drawPlayer(Graphics g, int i, int xPos, int yPos){
        if(xPos==Player.cubeIn[0]&&yPos==Player.cubeIn[1]){
            if(Player.cubeIn[2]==i)Player.draw1(g);
            if(Player.cubeIn[2]+1==i)Player.draw2(g);
            if(Player.cubeIn[2]+2==i)Player.draw3(g);
        }
    }
    public void drawEntities(Graphics g, int i, int xPos, int yPos){
        int xNumForEnemy=xPos+EntityContainer.numOfEntities-Player.cubeIn[0];
        int yNumForEnemy=yPos+EntityContainer.numOfEntities-Player.cubeIn[1];
        if(xNumForEnemy>=0 && xNumForEnemy<EntityContainer.numOfEntities*2 && yNumForEnemy>=0 && yNumForEnemy<EntityContainer.numOfEntities*2) {
            if (EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).cubeIn[2]==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).draw1(g);
                }
            }
            if (i>0&& EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).cubeIn[2]+1==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).draw2(g);
                }
            }
            if (i>1&& EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).cubeIn[2]+2==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).draw3(g);
                }
            }
        }
    }
    public  void drawProjectile(Graphics g, int i, int xPos, int yPos){
        int xNumForFireBall=xPos+ ProjectileContainer.numOfProjectile -Player.cubeIn[0];
        int yNumForFireBall=yPos+ ProjectileContainer.numOfProjectile -Player.cubeIn[1];
        if(xNumForFireBall>=0 &&xNumForFireBall< ProjectileContainer.numOfProjectile *2&&yNumForFireBall>=0 &&yNumForFireBall< ProjectileContainer.numOfProjectile *2) {
            if (ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i] != null) {
                for (var l = 0; l < ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].size(); l++) {
                    ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].get(l).draw(g);
                }
            }
        }

    }
    public void drawCube(Graphics g, int i, int xPos, int yPos){
        int[] xPosInfo=CubeContainer.YAndXPositionToChunkPos(xPos);
        int[] yPosInfo=CubeContainer.YAndXPositionToChunkPos(yPos);
        if( cubeContainer.chunks[xPosInfo[0]+CubeContainer.numOfChunkX][yPosInfo[0]+CubeContainer.numOfChunkY].cubePositions[xPosInfo[1]][yPosInfo[1]][i]){
            cubeContainer.chunks[xPosInfo[0]+CubeContainer.numOfChunkX][yPosInfo[0]+CubeContainer.numOfChunkY].cubes[xPosInfo[1]][yPosInfo[1]][i].draw(g,xPos,yPos,i);
        }
    }
    public void drawAll(Graphics g, int i, int x, int y){
        int xPos=Player.cubeIn[0]+x- GameGrid.numOfChunkToDraw*Chunk.numOfCubeX-Chunk.numOfCubeX/2;
        int yPos=Player.cubeIn[1]+y- GameGrid.numOfChunkToDraw*Chunk.numOfCubeY-Chunk.numOfCubeY/2;

        drawCube(g,i,xPos,yPos);
        drawPlayer(g,i,xPos,yPos);
        drawEntities(g,i,xPos,yPos);
        drawProjectile(g,i,xPos,yPos);
    }
    public void drawLayer(Graphics g,int i){
        if(GameGrid.angleForXRotation<Math.PI/4){
            for(var j = 0; j<Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2); j++){
                for(var k = Chunk.numOfCubeX*(GameGrid.numOfChunkToDraw*2+1)-1; k>=0; k--){
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(GameGrid.angleForXRotation<Math.PI/2){
            for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k>=0; k--) {
                for (var j = 0; j < Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2); j++) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(GameGrid.angleForXRotation<3*Math.PI/4){
            for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k>=0; k--) {
                for (var j = Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2)-1; j >=0; j--) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(GameGrid.angleForXRotation<Math.PI){
            for(var j = Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2)-1; j>=0; j--){
                for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2)-1; k>=0; k--){
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(GameGrid.angleForXRotation<5*Math.PI/4){
            for(var j = Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2)-1; j>=0; j--){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++){
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(GameGrid.angleForXRotation<3*Math.PI/2){
            for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++) {
                for (var j = Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2)-1; j >=0; j--) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(GameGrid.angleForXRotation<7*Math.PI/4){
            for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++) {
                for (var j = 0; j <Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2); j++) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(GameGrid.angleForXRotation<2*Math.PI){
            for(var j = 0; j<Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2); j++){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++){
                    drawAll(g, i,k,j);
                }
            }
        }
    }
    public void draw(Graphics g){
        fireBallContainer.draw(g);
        entityContainer.draw(g);
        bigZLayerHandler();
        g.setColor(new Color(14, 172, 204));
        g.fillRect(0, 0, GameGrid.GAME_WIDTH, GameGrid.GAME_HEIGHT);
        for(var i=0;i<Chunk.numOfCubeZ;i++){
            if(!bigZLayer[i]&&!(Player.cubeIn[2]==i)&&!(Player.cubeIn[2]+1==i)&&!(Player.cubeIn[2]+2==i))continue;
            if(checkToSkip3(i))continue;
            drawLayer(g,i);
        }
        for(var i=Chunk.numOfCubeZ-1;i>=0;i--){
            if(!bigZLayer[i]&&!(Player.cubeIn[2]==i)&&!(Player.cubeIn[2]+1==i)&&!(Player.cubeIn[2]+2==i))continue;
            if(!checkToSkip3(i))continue;
            drawLayer(g,i);
        }
        g.setColor(Color.RED);
        g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);
        if(!Player.thirdPerspective)g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);
    }
    public double[] mousePosToGamePos(int xPos,int yPos){
        double[] gamePos=new double[2];
        if(yPos<=PFY){
           yPos= (int) (PFY+1);

        }
        double sizeRatioForMouse=(yPos-PFY)/(PVY-PFY- ProjectileContainer.ProjectileHeight * defaultSize);
        double difPosYRForMouse=((GAME_HEIGHT/sizeRatioForMouse-GAME_HEIGHT)/depthRatio);
        double yPositionAForMouse=-(difPosYRForMouse/ defaultSize-Player.yPosition-Player.cubeAway);
        double mouseNewWidth=  defaultSize*sizeRatioForMouse;
        double xPositionAForMouse=(xPos+mouseNewWidth/2-GameGrid.PVX)/sizeRatioForMouse/ defaultSize+Player.xPosition;
        double correctorXY=0.5;
        double A=xPositionAForMouse-Player.xPosition-correctorXY;
        double B=yPositionAForMouse-Player.yPosition+correctorXY;
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
        double angle = Math.atan((Player.yPosition - yPos) / (Player.xPosition - xPos));
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
            case 90:
                Player.cubeAway+=.5;
                break;
            case 71:
                Player.cubeAway-=.5;
                break;
            case 114:
                toggleF3();
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
    private void toggleF3(){
        F3Down= !F3Down;
    }
}
