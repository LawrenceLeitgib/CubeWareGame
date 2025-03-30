import java.awt.*;
import java.awt.event.KeyEvent;

final public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static double gravityAcceleration=60;
    static double PFX;
    static double  PFY;
    static double PVX;
    static double PVY;
    static int defaultSize=100;
    static int safeZone=20;
    static int regenZone=6;
    static int numOfChunkToDraw=3;
    static boolean[] bigZLayer=new boolean[Chunk.numOfCubeZ];
    static double distancePlayerCamera =2;
    static InputHandler inputHandler;
    static CubeContainer cubeContainer;
    static double depthRatio=1;
    static double angleForHorizontalRotation =0.0;
    static boolean isRotatingLeft=false;
    static boolean isRotatingRight=false;
    static ProjectileContainer projectileContainer;
    static EntityContainer entityContainer;
    static Stats stats;
    static Player player;
    static double[] cameraPos =new double[2];
    static final double[] PlayerPos=new double[3];
    GameGrid(int GAME_WIDTH,int GAME_HEIGHT) {
        player = new Player(0, 0, 0,this);
        PlayerPos[0]=player.xPosition;
        PlayerPos[1]=player.yPosition;
        PlayerPos[2]=player.zPosition;
        setGameWidth(GAME_WIDTH);
        setGameHeight(GAME_HEIGHT);
        cubeContainer = new CubeContainer();
        projectileContainer = new ProjectileContainer();
        entityContainer = new EntityContainer();
        inputHandler=new InputHandler();
        stats=new Stats();
    }
    static double[] getObjectScreenPos(double x, double y, double z){
       return getObjectScreenPos(x,y,z, angleForHorizontalRotation);
    }
    static double[] getObjectScreenPos(double x, double y, double z, double angle){
        return getObjectScreenPos(x,y,z,angle,PlayerPos[0],PlayerPos[1],PlayerPos[2]);
    }
    static double[] getObjectScreenPos(double x, double y, double z, double angle,double Px, double Py, double Pz){
        double xCorrectorForRotation=.5;
        double yCorrectorForRotation=.5;
        double sizeRatioValue=-GAME_HEIGHT / depthRatio;
        double difPosXA=(x-xCorrectorForRotation-Px);
        double difPosYA= (y+yCorrectorForRotation-Py);
        double difPosZ=((Pz-z)* defaultSize);
        double xPositionA=  (Px+difPosXA*Math.cos(angle)+difPosYA*Math.sin(angle)+xCorrectorForRotation);
        double yPositionA=  (Py-difPosXA*Math.sin(angle)+difPosYA*Math.cos(angle)-yCorrectorForRotation);
        double difPosYR= ((Py-(yPositionA- distancePlayerCamera))* defaultSize);
        double difPosXR=((Px-xPositionA)* defaultSize);
        double sizeRatio= GAME_HEIGHT /(difPosYR*1.0* depthRatio + GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GAME_HEIGHT * depthRatio)/(Math.pow(sizeRatioValue* depthRatio + GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+ GAME_HEIGHT / depthRatio;
        }
        double newPosYR=((PVY - PFY)*sizeRatio+ PFY +difPosZ*sizeRatio);
        double newPosXR=  (PVX -(difPosXR)*sizeRatio-(defaultSize *sizeRatio)/2);
        return new double[]{newPosXR, newPosYR, sizeRatio};
    }
    static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
        PFX=GAME_WIDTH/2.0;
        PVX=GAME_WIDTH/2.0;
    }
    static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
        if(player.thirdPerspective)
            PFY=GAME_HEIGHT/3.0;
        else{
            PFY=GAME_HEIGHT/2.0;
        }
        PVY=GAME_HEIGHT;
    }
    static void updateData(double deltaTime){
        cameraPos[0]=player.xPosition-Math.sin(angleForHorizontalRotation)*(distancePlayerCamera +.5);
        cameraPos[1]=player.yPosition+Math.cos(angleForHorizontalRotation)*(distancePlayerCamera +.5);
        double rotationMultiplier=1;
        if(player.isSlowing)rotationMultiplier=.3;
        if(isRotatingLeft){
            angleForHorizontalRotation -=Math.PI/32*deltaTime*30*rotationMultiplier;

        }
        if(isRotatingRight){
            angleForHorizontalRotation +=Math.PI/32*deltaTime*30*rotationMultiplier;
        }
        while(angleForHorizontalRotation >=Math.PI*2){
            angleForHorizontalRotation -=Math.PI*2;
        }
        while(angleForHorizontalRotation <0){
            angleForHorizontalRotation +=Math.PI*2;
        }


        player.updateData(deltaTime);
        PlayerPos[0]=player.xPosition;
        PlayerPos[1]=player.yPosition;
        PlayerPos[2]=player.zPosition;

        entityContainer.updateData(deltaTime);
        projectileContainer.updateData(deltaTime);
        cubeContainer.updateData(deltaTime);
        inputHandler.update(deltaTime);
        stats.updateData(deltaTime);
    }
    static void bigZLayerHandler(){
        for(var k=0;k<Chunk.numOfCubeZ;k++){
            bigZLayer[k]=false;
        }
        for(var k=0;k<Chunk.numOfCubeZ;k++){
            if(projectileContainer.zLayers[k]|| entityContainer.zLayers[k]){
                bigZLayer[k]=true;
            }
        }
        for(int i = player.chunkIn[0]- numOfChunkToDraw; i<=player.chunkIn[0]+ numOfChunkToDraw; i++){
            for(int j = player.chunkIn[1]- numOfChunkToDraw; j<=player.chunkIn[1]+ numOfChunkToDraw; j++){
                for(var k=0;k<Chunk.numOfCubeZ;k++){
                    if(cubeContainer.chunks[i+ CubeContainer.numOfChunkX][j+ CubeContainer.numOfChunkY].zLayer[k]){
                        bigZLayer[k]=true;
                    }
                }
            }
        }
    }
    static boolean checkToSkip3(int i){
        double[] info1= getObjectScreenPos(player.xPosition,player.yPosition,i+1);
        return info1[1] < PFY;
    }
    static void drawPlayer(Graphics g, int i, int xPos, int yPos){
        if(xPos==player.cubeIn[0]&&yPos==player.cubeIn[1]){
            if(player.cubeIn[2]==i)player.draw1(g);
            if(player.cubeIn[2]+1==i)player.draw2(g);
            if(player.cubeIn[2]+2==i)player.draw3(g);
        }
    }
    static void drawEntities(Graphics g, int i, int xPos, int yPos){
        int xNumForEnemy=xPos+entityContainer.numOfEntities-player.cubeIn[0];
        int yNumForEnemy=yPos+entityContainer.numOfEntities-player.cubeIn[1];
        if(xNumForEnemy>=0 && xNumForEnemy<entityContainer.numOfEntities*2 && yNumForEnemy>=0 && yNumForEnemy<entityContainer.numOfEntities*2) {
            if (entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i] != null) {
                for (var l = 0; l < entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].size(); l++) {
                    if( entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).cubeIn[2]==i) entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).draw1(g);
                }
            }
            if (i>0&& entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1] != null) {
                for (var l = 0; l < entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].size(); l++) {
                    if( entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).cubeIn[2]+1==i) entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).draw2(g);
                }
            }
            if (i>1&& entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2] != null) {
                for (var l = 0; l < entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].size(); l++) {
                    if( entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).cubeIn[2]+2==i) entityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).draw3(g);
                }
            }
        }
    }
    static void drawProjectile(Graphics g, int i, int xPos, int yPos){
        int xNumForFireBall=xPos+ projectileContainer.numOfProjectile -player.cubeIn[0];
        int yNumForFireBall=yPos+ projectileContainer.numOfProjectile -player.cubeIn[1];
        if(xNumForFireBall>=0 &&xNumForFireBall< projectileContainer.numOfProjectile *2&&yNumForFireBall>=0 &&yNumForFireBall< projectileContainer.numOfProjectile *2) {
            if (projectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i] != null) {
                for (var l = 0; l < projectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].size(); l++) {
                    projectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].get(l).draw(g);
                }
            }
        }
    }
    static void drawCube(Graphics g, int i, int xPos, int yPos){
        int[] xPosInfo= CubeContainer.YAndXPositionToChunkPos(xPos);
        int[] yPosInfo= CubeContainer.YAndXPositionToChunkPos(yPos);
        if( cubeContainer.chunks[xPosInfo[0]+ CubeContainer.numOfChunkX][yPosInfo[0]+ CubeContainer.numOfChunkY].cubePositions[xPosInfo[1]][yPosInfo[1]][i]){
            cubeContainer.chunks[xPosInfo[0]+ CubeContainer.numOfChunkX][yPosInfo[0]+ CubeContainer.numOfChunkY].getCubes()[xPosInfo[1]][yPosInfo[1]][i].draw(g,xPos,yPos,i);
        }
    }
    static void drawAll(Graphics g, int i, int x, int y){
        int xPos=player.cubeIn[0]+x- numOfChunkToDraw*Chunk.numOfCubeX-Chunk.numOfCubeX/2;
        int yPos=player.cubeIn[1]+y- numOfChunkToDraw*Chunk.numOfCubeY-Chunk.numOfCubeY/2;

        drawCube(g,i,xPos,yPos);
        drawPlayer(g,i,xPos,yPos);
        drawEntities(g,i,xPos,yPos);
        drawProjectile(g,i,xPos,yPos);
    }
    static void drawLayer(Graphics g, int i){
        if(angleForHorizontalRotation <Math.PI/4){
            for(var j = 0; j<Chunk.numOfCubeY*(1+ numOfChunkToDraw*2); j++){
                for(var k = Chunk.numOfCubeX*(numOfChunkToDraw*2+1)-1; k>=0; k--){
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(angleForHorizontalRotation <Math.PI/2){
            for(var k = Chunk.numOfCubeX*(1+ numOfChunkToDraw*2); k>=0; k--) {
                for (var j = 0; j < Chunk.numOfCubeY * (1 + numOfChunkToDraw * 2); j++) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(angleForHorizontalRotation <3*Math.PI/4){
            for(var k = Chunk.numOfCubeX*(1+ numOfChunkToDraw*2); k>=0; k--) {
                for (var j = Chunk.numOfCubeY * (1 + numOfChunkToDraw * 2)-1; j >=0; j--) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(angleForHorizontalRotation <Math.PI){
            for(var j = Chunk.numOfCubeY*(1+ numOfChunkToDraw*2)-1; j>=0; j--){
                for(var k = Chunk.numOfCubeX*(1+ numOfChunkToDraw*2)-1; k>=0; k--){
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(angleForHorizontalRotation <5*Math.PI/4){
            for(var j = Chunk.numOfCubeY*(1+ numOfChunkToDraw*2)-1; j>=0; j--){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ numOfChunkToDraw*2); k++){
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(angleForHorizontalRotation <3*Math.PI/2){
            for(var k = 0; k<Chunk.numOfCubeX*(1+ numOfChunkToDraw*2); k++) {
                for (var j = Chunk.numOfCubeY * (1 + numOfChunkToDraw * 2)-1; j >=0; j--) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(angleForHorizontalRotation <7*Math.PI/4){
            for(var k = 0; k<Chunk.numOfCubeX*(1+ numOfChunkToDraw*2); k++) {
                for (var j = 0; j <Chunk.numOfCubeY * (1 + numOfChunkToDraw * 2); j++) {
                    drawAll(g, i,k,j);
                }
            }
        }
        else if(angleForHorizontalRotation <2*Math.PI){
            for(var j = 0; j<Chunk.numOfCubeY*(1+ numOfChunkToDraw*2); j++){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ numOfChunkToDraw*2); k++){
                    drawAll(g, i,k,j);
                }
            }
        }
    }
    static void draw(Graphics g){
        projectileContainer.draw();
        entityContainer.draw();
        bigZLayerHandler();
        g.setColor(new Color(14, 172, 204));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        for(var i=0;i<Chunk.numOfCubeZ;i++){
            if(!bigZLayer[i]&&!(player.cubeIn[2]==i)&&!(player.cubeIn[2]+1==i)&&!(player.cubeIn[2]+2==i))continue;
            if(checkToSkip3(i))continue;
            drawLayer(g,i);
        }
        for(var i=Chunk.numOfCubeZ-1;i>=0;i--){
            if(!bigZLayer[i]&&!(player.cubeIn[2]==i)&&!(player.cubeIn[2]+1==i)&&!(player.cubeIn[2]+2==i))continue;
            if(!checkToSkip3(i))continue;
            drawLayer(g,i);
        }
        g.setColor(Color.RED);
        g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);
        if(!player.thirdPerspective)g.fillOval((int) (PFX-4), (int) (PFY-4),8,8);
        stats.draw(g);
    }
    static void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 75 -> {
                PFY += 50;
                depthRatio = GAME_HEIGHT / (PVY - PFY);
            }
            case 79 -> {
                PFY -= 50.0;
                depthRatio = GAME_HEIGHT / (PVY - PFY);
            }
            case 73 -> PVY += 20;
            case 74 -> PVY -= 20;
            case 90 -> distancePlayerCamera += .5;
            case 71 -> distancePlayerCamera -= .5;
        }
    }
}
