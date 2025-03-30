import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    static double xPosition;
    static double yPosition;
    static double zPosition;

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static double cubeAway=2;
    static double initialCubeAway=2;

    static double width=.8*Cube.defaultSize;
    static double height=1.8*Cube.defaultSize;

    static double depth=.8*Cube.defaultSize;
    double speed=5;
    double gravityAcceleration=60;
    double xVelocity;
    double yVelocity;
    double zVelocity;

    boolean isSlowing=false;
    boolean isFlying=true;

    double slowMultiplier=0.1;
    double runningMultiplier=2;

    boolean isMovingUp=false;
    boolean isMovingDown=false;
    boolean isMovingForward =false;
    boolean isMovingBackward =false;
    boolean isMovingLeft=false;
    boolean isMovingRight=false;

    boolean isJumping=false;

    static double newPosY;
    static double newPosX;

    boolean lightningSprint=false;
    int lightningSprintCount=0;

    double lightningSprintTime =1;

    double lightningSprintCount2= lightningSprintTime;


    static int[] chunkIn=new int[2];

    static int numOfChunkToDraw=2;

    static boolean thirdPerspective=true;
    boolean[][][] megaChunkCubePositions =new boolean[Chunk.numOfCubeX*3][Chunk.numOfCubeY*3][Chunk.numOfCubeZ];
    static int[] cubeIn=new int[3];


    int yPosCount=0;
    double[] yPosHistoric=new double[10];
    boolean isSprinting;



    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double positionZ){
        Player.xPosition=0;
        Player.yPosition=-50;
        Player.zPosition=4;
        Player.GAME_WIDTH =GAME_WIDTH;
        Player.GAME_HEIGHT =GAME_HEIGHT;
        chunkIn[0]=(int)(xPosition/Chunk.numOfCubeX);
        chunkIn[1]=(int)(yPosition/Chunk.numOfCubeY);
        newChunkAround();



    }

    public void newChunkAround(){
        for(int i=Player.chunkIn[0]-Player.numOfChunkToDraw;i<=Player.chunkIn[0]+Player.numOfChunkToDraw;i++){
            for(int j=Player.chunkIn[1]-Player.numOfChunkToDraw;j<=Player.chunkIn[1]+Player.numOfChunkToDraw;j++){
                if(!CubeContainer.chunksPosition[CubeContainer.numOfChunkX+i][CubeContainer.numOfChunkY+j])
                    CubeContainer.chunks[CubeContainer.numOfChunkX+i][CubeContainer.numOfChunkY+j]=new Chunk(GAME_WIDTH,GAME_HEIGHT,i,j);
                CubeContainer.chunksPosition[CubeContainer.numOfChunkX+i][CubeContainer.numOfChunkY+j]=true;
            }
        }

    }
    public void updateData(double deltaTime){
        Player.chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX);
        Player.chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY);
        if(xPosition<0)Player.chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX-1);
        if(yPosition<0)Player.chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY-1);

        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+0.5);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition-0.5);
       // System.out.println(cubeIn[0]+", "+cubeIn[1]+", "+cubeIn[2]);

        lightningSprintCount2+=deltaTime;
        if(lightningSprint){
            if(lightningSprintCount2>= lightningSprintTime){
                lightningSprintCount2=0;
                Stats.mana-=4;
                lightningSprintCount=13;
                if(Stats.mana<0){
                    Stats.mana+=4;
                    lightningSprintCount=0;
                }
            }
        }



//System.out.println(GameGrid.PVY-GameGrid.PFY);
        if(!thirdPerspective){
            cubeAway=-5;
            //cubeAway=-3.5;

        }else{
            cubeAway=2;

        }



        //System.out.println(Player.chunkIn[0]+" "+Player.chunkIn[1]);
        updateMegaChunk();


        double multiplierOfSpeed=1*deltaTime;
        if(isSprinting){
            multiplierOfSpeed=runningMultiplier*deltaTime;
            GameGrid.depthRatio=1.6;
        }else{
            GameGrid.depthRatio=1;
        }
        if(isSlowing)multiplierOfSpeed=slowMultiplier*deltaTime;
        if(!isFlying){

            if(isJumping){
                zVelocity=15;
                isJumping=false;
            }
           setZVelocity(zVelocity-gravityAcceleration*deltaTime);
           if(zVelocity<-90)zVelocity+=gravityAcceleration*deltaTime;
           zPosition+=zVelocity*deltaTime;
           if(zVelocity>0)
           detectionCollision(2);
            if(zVelocity<0)
                detectionCollision(1);


        }
        else{
            if(isMovingUp) {
                zPosition+=speed*multiplierOfSpeed;
                detectionCollision(2);
            }
            if(isMovingDown){
                zPosition-=speed*multiplierOfSpeed;
                detectionCollision(1);
            }

        }
        yVelocity=0;
        xVelocity=0;
        if(isMovingRight) {
            yVelocity+=speed*multiplierOfSpeed*Math.sin(GameGrid.angleForXRotation);
            xVelocity+=speed*multiplierOfSpeed*Math.cos(GameGrid.angleForXRotation);
        }
        if(isMovingLeft){
            yVelocity-=speed*multiplierOfSpeed*Math.sin(GameGrid.angleForXRotation);
            xVelocity-=speed*multiplierOfSpeed*Math.cos(GameGrid.angleForXRotation);
        }

        if(isMovingBackward){
            yVelocity+=speed*multiplierOfSpeed*Math.cos(GameGrid.angleForXRotation);
            xVelocity-=speed*multiplierOfSpeed*Math.sin(GameGrid.angleForXRotation);
        }
        if(isMovingForward) {
            yVelocity-=speed*multiplierOfSpeed*Math.cos(GameGrid.angleForXRotation);
            xVelocity+=speed*multiplierOfSpeed*Math.sin(GameGrid.angleForXRotation);

        }
        if(lightningSprintCount>0){

            yVelocity=0;
            yVelocity-=200.2*Math.cos(GameGrid.angleForXRotation)*deltaTime/6;
            xVelocity+=200.2*Math.sin(GameGrid.angleForXRotation)*deltaTime/6;
            lightningSprintCount--;
        }


        xPosition+=xVelocity;
        yPosition+=yVelocity;




        //double forOtherSensitivity=0.02;
        int newXpos=cubeIn[0];
        int newYposFront=cubeIn[1]-1;
        int newYposBack=cubeIn[1] +1;

       int  xChunkNum=0;
        int yChunkNumFront=0;
        int yChunkNumBack=0;

        int newYpos=cubeIn[1];
        int newXposLeft=cubeIn[0]-1;
        int newXposRight=cubeIn[0]+1;

        int  YChunkNum=0;
        int XChunkNumLeft=0;
        int XChunkNumRight=0;
        while(newXpos<0){
            newXpos+=Chunk.numOfCubeX;
            xChunkNum-=1;

        }
        while(newXpos>=Chunk.numOfCubeX){
            newXpos-=Chunk.numOfCubeX;
            xChunkNum+=1;

        }
        while(newYposFront<0){
            newYposFront+=Chunk.numOfCubeY;
            yChunkNumFront-=1;

        }
        while(newYposFront>=Chunk.numOfCubeY){
            newYposFront-=Chunk.numOfCubeY;
            yChunkNumFront+=1;

        }
        while(newYposBack<0){
            newYposBack+=Chunk.numOfCubeY;
            yChunkNumBack-=1;

        }
        while(newYposBack>=Chunk.numOfCubeY){
            newYposBack-=Chunk.numOfCubeY;
            yChunkNumBack+=1;
        }


        while(newYpos<0){
            newYpos+=Chunk.numOfCubeY;
            YChunkNum-=1;

        }
        while(newYpos>=Chunk.numOfCubeY){
            newYpos-=Chunk.numOfCubeY;
            YChunkNum+=1;

        }
        while(newXposLeft<0){
            newXposLeft+=Chunk.numOfCubeX;
            XChunkNumLeft-=1;

        }
        while(newXposLeft>=Chunk.numOfCubeX){
            newXposLeft-=Chunk.numOfCubeX;
            XChunkNumLeft+=1;

        }
        while(newXposRight<0){
            newXposRight+=Chunk.numOfCubeX;
            XChunkNumRight-=1;

        }
        while(newXposRight>=Chunk.numOfCubeX){
            newXposRight-=Chunk.numOfCubeX;
            XChunkNumRight+=1;
        }
        if(cubeIn[2]<0){
            cubeIn[2]=Chunk.numOfCubeZ-3;
        }
        boolean cubeFront=(CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumFront+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposFront][cubeIn[2]]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumFront+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposFront][cubeIn[2]+1]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumFront+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposFront][(int)(cubeIn[2]+height/Cube.defaultSize+.5)]);
        boolean cubeBack=(CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][cubeIn[2]]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][cubeIn[2]+1]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][(int)(cubeIn[2]+height/Cube.defaultSize+.5)]);
        boolean cubeRight=(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]])||
                xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]+1])||
                xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][(int)(cubeIn[2]+height/Cube.defaultSize+.5)]);
        boolean cubeLeft=xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]])||
                xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]+1])||
                xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][(int)(cubeIn[2]+height/Cube.defaultSize+.5)]);


//System.out.println( yPosHistoric[yPosCount]);
int beforeCount=yPosCount-3;
if(beforeCount<0)beforeCount+=10;
double deltaYPos=yPosHistoric[yPosCount]-yPosHistoric[beforeCount];
//System.out.println(deltaYPos);
        if((xVelocity>0&&cubeRight&&yVelocity<0&&cubeFront)||(xVelocity<0&&cubeLeft&&yVelocity<0&&cubeFront)){
            if(deltaYPos>0){
                detectionCollision(3);
                detectionCollision(4);
                detectionCollision(5);
                detectionCollision(6);


            }else{
            detectionCollision(5);
            detectionCollision(6);
            detectionCollision(4);
            detectionCollision(3);

            }
        }

        if((xVelocity>0&&cubeRight&&yVelocity>0&&cubeBack)||(xVelocity<0&&cubeLeft&&yVelocity>0&&cubeBack)){
            if(deltaYPos<0){
                detectionCollision(3);
                detectionCollision(4);
                detectionCollision(5);
                detectionCollision(6);


            }else{
                detectionCollision(5);
                detectionCollision(6);
                detectionCollision(4);
                detectionCollision(3);

            }
        }



        else if(yVelocity<0&&cubeFront){
            if(yVelocity>0){
                detectionCollision(6);
            }
            if(yVelocity<0){
                detectionCollision(5);
            }
            if(xVelocity>0){
                detectionCollision(4);
            }
            if(xVelocity<0){
                detectionCollision(3);
            }

        }

        else if(yVelocity>0&&cubeBack){

            if(yVelocity>0){
                detectionCollision(6);
            }
            if(yVelocity<0){
                detectionCollision(5);
            }
            if(xVelocity>0){
                detectionCollision(4);
            }
            if(xVelocity<0){
                detectionCollision(3);
            }
        }
            else{
            if(xVelocity>0){
                detectionCollision(4);
            }
            if(xVelocity<0){
                detectionCollision(3);
            }
            if(yVelocity>0){
                detectionCollision(6);
            }
            if(yVelocity<0){
                detectionCollision(5);
            }
        }


            for(int i=0;i<EnemiesContainer.enemies.length;i++){
                if(EnemiesContainer.enemyList[i]&&(zPosition<EnemiesContainer.enemies[i].zPosition+EnemiesContainer.enemies[i].height/Cube.defaultSize&&zPosition+height/Cube.defaultSize>EnemiesContainer.enemies[i].zPosition))
                if(Math.sqrt(Math.pow(EnemiesContainer.enemies[i].xPosition-xPosition,2)+Math.pow(EnemiesContainer.enemies[i].yPosition-yPosition,2))<(EnemiesContainer.enemies[i].width/2+width/2)/Cube.defaultSize){
                    xPosition-=Math.cos(EnemiesContainer.enemies[i].angleWithPlayer)*speed*10*deltaTime;
                    yPosition-=Math.sin(EnemiesContainer.enemies[i].angleWithPlayer)*speed*10*deltaTime;
                    Stats.health-=EnemiesContainer.enemies[i].damage;
                }
            }

        yPosHistoric[yPosCount]=yPosition;
            yPosCount++;
            if(yPosCount>=10){
                yPosCount=0;
        }

        //detectionCollision(0);
        xPosition=  (int)((xPosition)*1000)/1000.0;
        yPosition=  (int)((yPosition)*1000)/1000.0;
        zPosition=  (int)((zPosition)*1000)/1000.0;
        }

        /*
          if(yVelocity<0&&(CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumFront+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposFront][cubeIn[2]]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumFront+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposFront][cubeIn[2]+1]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumFront+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposFront][(int)(cubeIn[2]+height/Cube.defaultSize+.5)])){

            if(xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]])||
                    xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]+1])||
                    xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][(int)(cubeIn[2]+height/Cube.defaultSize+.5)])){

                if(xVelocity>0){
                    detectionCollision(4);
                }
                if(xVelocity<0){
                    detectionCollision(3);
                }
                if(yVelocity>0){
                    detectionCollision(6);
                }
                if(yVelocity<0){
                    detectionCollision(5);
                }
            }
            else if(xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]])||
                    xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]+1])||
                    xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][(int)(cubeIn[2]+height/Cube.defaultSize+.5)])){

                if(xVelocity>0){
                    detectionCollision(4);
                }
                if(xVelocity<0){
                    detectionCollision(3);
                }
                if(yVelocity>0){
                    detectionCollision(6);
                }
                if(yVelocity<0){
                    detectionCollision(5);
                }
            }
                else{
                if(yVelocity>0){
                    detectionCollision(6);
                }
                if(yVelocity<0){
                    detectionCollision(5);
                }
                if(xVelocity>0){
                    detectionCollision(4);
                }
                if(xVelocity<0){
                    detectionCollision(3);
                }
            }


        }
        else if(yVelocity>0&&(CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][cubeIn[2]]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][cubeIn[2]+1]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][(int)(cubeIn[2]+height/Cube.defaultSize+.5)])){

            if(xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]])||
                    xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]+1])||
                    xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][(int)(cubeIn[2]+height/Cube.defaultSize+.5)])){

                if(xVelocity>0){
                    detectionCollision(4);
                }
                if(xVelocity<0){
                    detectionCollision(3);
                }
                if(yVelocity>0){
                    detectionCollision(6);
                }
                if(yVelocity<0){
                    detectionCollision(5);
                }
            }
            else if(xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]])||
                    xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]+1])||
                    xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][(int)(cubeIn[2]+height/Cube.defaultSize+.5)])){

                if(xVelocity>0){
                    detectionCollision(4);
                }
                if(xVelocity<0){
                    detectionCollision(3);
                }
                if(yVelocity>0){
                    detectionCollision(6);
                }
                if(yVelocity<0){
                    detectionCollision(5);
                }
            }
            else{
                if(yVelocity>0){
                    detectionCollision(6);
                }
                if(yVelocity<0){
                    detectionCollision(5);
                }
                if(xVelocity>0){
                    detectionCollision(4);
                }
                if(xVelocity<0){
                    detectionCollision(3);
                }
            }
        }
            else{

            if(xVelocity>0){
                detectionCollision(4);
            }
            if(xVelocity<0){
                detectionCollision(3);
            }
            if(yVelocity>0){
                detectionCollision(6);
            }
            if(yVelocity<0){
                detectionCollision(5);
            }
        }*/
    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }
    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
    public void setZVelocity(double zVelocity) {

        this.zVelocity = zVelocity;
    }
    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }
    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }
    public static void draw(Graphics g){
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.PLAIN,24));
        g.setColor(Color.red);
        g.drawString(String.valueOf(yPosition),15,90);
        g.drawString(Double.toString(xPosition),15,70);
        g.drawString(Double.toString(zPosition),15,110);

        if(!thirdPerspective)return;


        double sizeRatio=GAME_HEIGHT/((cubeAway*Cube.defaultSize+(Cube.defaultSize-depth)/2.0)*GameGrid.depthRatio+GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio)+GameGrid.PFY;
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        newPosX=  (GAME_WIDTH/2.0);


        double[][] corners=getCorners(newPosX,newPosY,newWidth,newHeight);
        double[] angleList=getAngleList(corners);
        double[][] newCorners=getCornersB(corners,angleList,newPosY);


        for(var i=0;i<4;i++) {
            g.setColor(Color.BLACK);
            g.setColor(Color.red);
            if (i < 3) {
                g.drawLine((int) corners[i][0], (int) corners[i][1], (int) corners[i + 1][0], (int) corners[i + 1][1]);
                g.drawLine((int) newCorners[i][0], (int) newCorners[i][1], (int) newCorners[i + 1][0], (int) newCorners[i + 1][1]);

            } else {
                g.drawLine((int) corners[i][0], (int) corners[i][1], (int) corners[0][0], (int) corners[0][1]);
                g.drawLine((int) newCorners[i][0], (int) newCorners[i][1], (int) newCorners[0][0], (int) newCorners[0][1]);

            }
            g.drawLine((int) corners[i][0], (int) corners[i][1], (int) newCorners[i][0], (int) newCorners[i][1]);

        }




    }

    public void updateMegaChunk(){
        for(var i=0;i<Chunk.numOfCubeX;i++){
            for(var j=0;j<Chunk.numOfCubeY;j++){
                for(var k=0;k<Chunk.numOfCubeZ;k++){
                    megaChunkCubePositions[i][j][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]-1][CubeContainer.numOfChunkY+chunkIn[1]-1].cubePositions[i][j][k];
                    megaChunkCubePositions[i+Chunk.numOfCubeX][j][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]][CubeContainer.numOfChunkY+chunkIn[1]-1].cubePositions[i][j][k];
                    megaChunkCubePositions[i+Chunk.numOfCubeX*2][j][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]+1][CubeContainer.numOfChunkY+chunkIn[1]-1].cubePositions[i][j][k];

                    megaChunkCubePositions[i][j+Chunk.numOfCubeY][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]-1][CubeContainer.numOfChunkY+chunkIn[1]].cubePositions[i][j][k];
                    megaChunkCubePositions[i+Chunk.numOfCubeX][j+Chunk.numOfCubeY][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]][CubeContainer.numOfChunkY+chunkIn[1]].cubePositions[i][j][k];
                    megaChunkCubePositions[i+Chunk.numOfCubeX*2][j+Chunk.numOfCubeY][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]+1][CubeContainer.numOfChunkY+chunkIn[1]].cubePositions[i][j][k];

                    megaChunkCubePositions[i][j+Chunk.numOfCubeY*2][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]-1][CubeContainer.numOfChunkY+chunkIn[1]+1].cubePositions[i][j][k];
                    megaChunkCubePositions[i+Chunk.numOfCubeX][j+Chunk.numOfCubeY*2][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]][CubeContainer.numOfChunkY+chunkIn[1]+1].cubePositions[i][j][k];
                    megaChunkCubePositions[i+Chunk.numOfCubeX*2][j+Chunk.numOfCubeY*2][k]=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]+1][CubeContainer.numOfChunkY+chunkIn[1]+1].cubePositions[i][j][k];
                }
            }
        }

    }
   public boolean[] detectionCollision(int num){
       boolean[] collision=new boolean[7];
       Chunk chunkInside=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]][CubeContainer.numOfChunkY+chunkIn[1]];

       boolean[][][] cubesPosChunkInside=chunkInside.cubePositions;
       //System.out.println(chunkInside.xPosition+" "+chunkInside.yPosition);
       int xPosUnder=(int)(xPosition+0.5);
       if(xPosition<0)xPosUnder=(int)(xPosition-0.5);
       int yPosUnder=(int)(yPosition+0.5);
       if(yPosition<0)yPosUnder=(int)(yPosition-0.5);
       int zPosUnder=(int)(zPosition);
       if(zPosition<0)zPosUnder=(int)(zPosition-1);
       int zPosAbove=(int)(zPosition+height/Cube.defaultSize);
       if(zPosition<0)zPosAbove=(int)(zPosition+height/Cube.defaultSize-1);


       double forOtherSensitivity=0.02;




       int xLeftPos=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2);
       if(xPosition<0)xLeftPos=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2-1);

       int xRightPos=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2+1);
       if(xPosition<0)xRightPos=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2);

       int xLeftPosForOther=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2+forOtherSensitivity);
       if(xPosition<0.5)xLeftPosForOther=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2-1+forOtherSensitivity);

       int xRightPosForOther=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2+1-forOtherSensitivity);
       if(xPosition<-0.5)xRightPosForOther=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2-forOtherSensitivity);

       int yFrontPos=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2);
       if(yPosition<0)yFrontPos=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2-1);

       int yBackPos=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2+1);
       if(yPosition<0)yBackPos=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2);

       int yFrontPosForOther=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2+forOtherSensitivity);
       if(yPosition<0.5)yFrontPosForOther=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2-1+forOtherSensitivity);

       int yBackPosForOther=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2+1-forOtherSensitivity);
       if(yPosition<-0.5)yBackPosForOther=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2-forOtherSensitivity);

       int zPosUnderForOther=(int)(zPosition+forOtherSensitivity);
       if(zPosition<0)zPosUnderForOther=(int)(zPosition-1+forOtherSensitivity);

       int zPosMiddleForOther=(int)(zPosition+1+forOtherSensitivity);
       if(zPosition<0)zPosMiddleForOther=(int)(zPosition+1-1+forOtherSensitivity);

       int zPosAboveForOther=(int)(zPosition+height/Cube.defaultSize-forOtherSensitivity);
       if(zPosition<0)zPosAboveForOther=(int)(zPosition+height/Cube.defaultSize-1-forOtherSensitivity);

       String sideOfCube="rien";
       if(xPosUnder-xPosition>0)sideOfCube="left";
       if(xPosUnder-xPosition<=0)sideOfCube="right";





      int numCubX=-Chunk.numOfCubeX*(chunkInside.xPosition-1);
      int numCubY=-Chunk.numOfCubeY*(chunkInside.yPosition-1);
       cubesPosChunkInside=megaChunkCubePositions;

       if(zPosUnder<0)zPosUnder=Chunk.numOfCubeZ-1;
       if(zPosAbove<0)zPosAbove=Chunk.numOfCubeZ-1;

       if(zPosUnderForOther<0)zPosUnderForOther=Chunk.numOfCubeZ-1;
       if(zPosAboveForOther<0)zPosAboveForOther=Chunk.numOfCubeZ-1;
       if(zPosMiddleForOther<0)zPosMiddleForOther=Chunk.numOfCubeZ-1;

       if(zPosUnder>=Chunk.numOfCubeZ)zPosUnder=Chunk.numOfCubeZ-1;
       if(zPosAbove>=Chunk.numOfCubeZ)zPosAbove=Chunk.numOfCubeZ-1;
       if(zPosUnderForOther>=Chunk.numOfCubeZ)zPosUnderForOther=Chunk.numOfCubeZ-1;
       if(zPosAboveForOther>=Chunk.numOfCubeZ)zPosAboveForOther=Chunk.numOfCubeZ-1;
       if(zPosMiddleForOther>=Chunk.numOfCubeZ)zPosMiddleForOther=Chunk.numOfCubeZ-1;




       if(num==1 ||num==0){
           collision[1]= cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yFrontPosForOther][zPosUnder]||
                   cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yBackPosForOther][zPosUnder]||
                   cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yFrontPosForOther][zPosUnder]||
                   cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yBackPosForOther][zPosUnder];

       }

        if(num==2 ||num==0)
            collision[2]= cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yFrontPosForOther][zPosAbove]||
               cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yBackPosForOther][zPosAbove]||
               cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yFrontPosForOther][zPosAbove]||
               cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yBackPosForOther][zPosAbove];


    if(num==3 ||num==0)
        collision[3]= cubesPosChunkInside[numCubX+xLeftPos][numCubY+yFrontPosForOther][zPosUnderForOther]||
                cubesPosChunkInside[numCubX+xLeftPos][numCubY+yBackPosForOther][zPosUnderForOther]||
                cubesPosChunkInside[numCubX+xLeftPos][numCubY+yFrontPosForOther][zPosMiddleForOther]||
                cubesPosChunkInside[numCubX+xLeftPos][numCubY+yBackPosForOther][zPosMiddleForOther]||
                cubesPosChunkInside[numCubX+xLeftPos][numCubY+yFrontPosForOther][zPosAboveForOther]||
                cubesPosChunkInside[numCubX+xLeftPos][numCubY+yBackPosForOther][zPosAboveForOther];


       if(num==4 ||num==0)
           collision[4]= cubesPosChunkInside[numCubX+xRightPos][numCubY+yFrontPosForOther][zPosUnderForOther]||
                   cubesPosChunkInside[numCubX+xRightPos][numCubY+yBackPosForOther][zPosUnderForOther]||
                   cubesPosChunkInside[numCubX+xRightPos][numCubY+yFrontPosForOther][zPosMiddleForOther]||
                   cubesPosChunkInside[numCubX+xRightPos][numCubY+yBackPosForOther][zPosMiddleForOther]||
                   cubesPosChunkInside[numCubX+xRightPos][numCubY+yFrontPosForOther][zPosAboveForOther]||
                   cubesPosChunkInside[numCubX+xRightPos][numCubY+yBackPosForOther][zPosAboveForOther];


       if(num==5 ||num==0)

           collision[5]= cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yFrontPos][zPosUnderForOther]||
                   cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yFrontPos][zPosUnderForOther]||
                   cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yFrontPos][zPosMiddleForOther]||
                   cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yFrontPos][zPosMiddleForOther]||
                   cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yFrontPos][zPosAboveForOther]||
                   cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yFrontPos][zPosAboveForOther];
       if(num==6 ||num==0)
           collision[6]= cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yBackPos][zPosUnderForOther]||
                   cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yBackPos][zPosUnderForOther]||
                   cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yBackPos][zPosMiddleForOther]||
                   cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yBackPos][zPosMiddleForOther]||
                   cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yBackPos][zPosAboveForOther]||
                   cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yBackPos][zPosAboveForOther];

       double sensitivity=1;
       //double sensitivity=0.5;
      // System.out.println( collision[3]);
      // System.out.println(collision[5]+" "+xRightPosForOther+" "+xPosition);

       if(collision[1]&&zPosUnder+1-zPosition<sensitivity&&(isMovingDown||zVelocity<0)){
           zPosition=zPosUnder+1.000001;
           zVelocity=0;
         // isMovingDown=false;
       }
       if(collision[2]&&zPosition-zPosAbove+height/Cube.defaultSize<sensitivity&&(isMovingUp||zVelocity>0)){
           zPosition=zPosAbove-height/Cube.defaultSize;
           zVelocity=0;
          //isMovingUp=false;
       }
       if(collision[3]&&(xLeftPos+1-(1-width/Cube.defaultSize)/2)-xPosition<sensitivity&&xVelocity<0){

           xPosition=xLeftPos+1-(1-width/Cube.defaultSize)/2;
         //ddd  xVelocity=0;
         //   isMovingLeft=false;

       }
       if(collision[4]&&xPosition-(xRightPos-1+(1-width/Cube.defaultSize)/2)<sensitivity&&xVelocity>0){
          // System.out.println("test" );

           xPosition=xRightPos-1+(1-width/Cube.defaultSize)/2;
        //  isMovingRight=false;
       }
       if(collision[5]&&(yFrontPos+1-(1-depth/Cube.defaultSize)/2)-yPosition<sensitivity&&yVelocity<0){
           /*if(sideOfCube=="right"&&(cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yFrontPos][zPosAboveForOther]||cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yFrontPos][zPosUnderForOther]))
           yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;
          if(sideOfCube=="left"&&(cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yFrontPos][zPosAboveForOther]||cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yFrontPos][zPosUnderForOther]))
               yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;
           if(xVelocity==0) yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;*/
           yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;
           //yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;

           //isMovingForward=false;
       }
       if(collision[6]&&yPosition-(yBackPos-1+(1-depth/Cube.defaultSize)/2)<sensitivity&&yVelocity>0) {
          /* if(sideOfCube.equals("right") &&(cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yBackPos][zPosAboveForOther]||cubesPosChunkInside[numCubX+xLeftPosForOther][numCubY+yBackPos][zPosUnderForOther]))
               yPosition=yBackPos-1+(1-depth/Cube.defaultSize)/2;
           if(sideOfCube.equals("left") &&( cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yBackPos][zPosUnderForOther]||cubesPosChunkInside[numCubX+xRightPosForOther][numCubY+yBackPos][zPosUnderForOther]))
               yPosition=yBackPos-1+(1-depth/Cube.defaultSize)/2;

           if(xVelocity==0)yPosition=yBackPos-1+(1-depth/Cube.defaultSize)/2;*/
           yPosition=yBackPos-1+(1-depth/Cube.defaultSize)/2;
         // isMovingBackward=false;
       }




       for(var i=1;i<7;i++){
           if (collision[i]) {
               collision[0] = true;
               break;
           }
       }


       return collision;
   }
    static public double[][] getCorners(double newPosX,double newPosY,double newWidth,double newHeight){
        double [][] corners=new double[4][2];
        corners[0][1]=newPosY-newHeight;
        corners[1][1]=newPosY-newHeight;
        corners[2][1]=newPosY;
        corners[3][1]=newPosY;


        corners[0][0]=newPosX-newWidth/2.0;
        corners[1][0]=newPosX+newWidth/2.0;
        corners[2][0]=newPosX+newWidth/2.0;
        corners[3][0]=newPosX-newWidth/2.0;




        return corners;
    }
    static public double[] getAngleList(double[][] corners) {
        double[] angleList=new double[4];
        double[] deltaXList=new double[4];
        double[] deltaYList=new double[4];

        for (var i = 0; i < 4; i++) {
            deltaXList[i] = corners[i][0] - GameGrid.PFX;
            deltaYList[i] = corners[i][1] - GameGrid.PFY;
            angleList[i] = Math.atan(deltaYList[i] / deltaXList[i]);

            if (deltaXList[i] > 0 && deltaYList[i] < 0) {
                angleList[i] = Math.PI * 2 + Math.atan(deltaYList[i] / deltaXList[i]);
            } else if (deltaXList[i] < 0) angleList[i] = Math.PI + Math.atan(deltaYList[i] / deltaXList[i]);
            if (deltaXList[i] == 0 && deltaYList[i] < 0) angleList[i] = 3 * Math.PI / 2;
            if (deltaXList[i] == 0 && deltaYList[i] > 0) angleList[i] = Math.PI / 2;
        }
        return angleList;
    }
    static public double[][] getCornersB(double[][] corners, double[] angleList,double newPosY){
        double[][] newCorners = new double[4][2];
        double groundAngle;
        double deltaXAngle=corners[3][0]-GameGrid.PFX;
        double deltaYAngle= corners[3][1]-GameGrid.PFY;
        groundAngle=Math.atan(deltaYAngle/deltaXAngle);
        if (deltaXAngle>0&&deltaYAngle<0){
            groundAngle=Math.PI*2.0+Math.atan(deltaYAngle/deltaXAngle);
        }
        else  if(deltaXAngle<0) groundAngle=Math.PI+Math.atan(deltaYAngle/deltaXAngle);
        double sizeRatioAtPosition=GAME_HEIGHT/((cubeAway*Cube.defaultSize+depth)*GameGrid.depthRatio+GAME_HEIGHT);
        double DeltaY=(newPosY)-((GameGrid.PVY-GameGrid.PFY)*(sizeRatioAtPosition)+GameGrid.PFY);
        double groundRatio=DeltaY/Math.sin(groundAngle);




        newCorners[3][0] = corners[3][0] -groundRatio  * Math.cos(groundAngle);
        newCorners[3][1] = corners[3][1] -groundRatio  * Math.sin(groundAngle);
        newCorners[0][0] = newCorners[3][0];
        newCorners[0][1] = newCorners[3][1]-sizeRatioAtPosition*height;
        newCorners[1][0] =newCorners[0][0]+sizeRatioAtPosition*width;
        newCorners[1][1] = newCorners[0][1];
        newCorners[2][0] =  newCorners[1][0];
        newCorners[2][1] = newCorners[1][1]+sizeRatioAtPosition*height;
        //System.out.println(newCorners[a1][1]+" x: "+xPosition+" y: "+yPosition+" z: "+zPosition);




        return newCorners;

    }

    public void flySwitch(){
        if(isFlying){
            isFlying=false;
            setZVelocity(0);
        }
        else {
            isFlying=true;
            setZVelocity(0);
        }
    }



    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 65:
               // isMovingLeft=true;
                GameGrid.isRotatingLeft=true;
                break;

            case 68:
                //isMovingRight = true;
                GameGrid.isRotatingRight=true;
                break;
            case 83:
                isMovingBackward=true;
                break;

            case 87:
                isMovingForward=true;
                break;
            case 89:
                if(isFlying)isMovingDown=true;
                break;

            case 88:
                if(isFlying)isMovingUp=true;
                break;
            case 70:
                flySwitch();
                //FireBall.speed=20;
                break;
            case 32:
                if(!isFlying)isJumping=true;
                break;

            case 16:
                isSlowing=true;
                break;
            case 80:
              togglePerspective();
                break;
            case 85:

                isMovingRight=true;
                break;
            case 72:
                isMovingLeft=true;
                break;
            case 81:
                lightningSprint=true;
                break;
            case 82:
                if(isMovingForward)
                    isSprinting=true;
                break;


        }

    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case 65:
                //isMovingLeft=false;
                GameGrid.isRotatingLeft=false;
                break;

            case 68:
                //isMovingRight=false;
                GameGrid.isRotatingRight=false;
                break;
            case 83:
                isMovingBackward=false;
                break;

            case 87:
                isMovingForward=false;
                isSprinting=false;
                break;
            case 89:
                if(isFlying)isMovingDown=false;
                break;

            case 88:
                if(isFlying)isMovingUp=false;
                break;
            case 32:
                if(!isFlying)isJumping=false;
                break;
            case 16:
                isSlowing=false;
                break;
            case 85:

                isMovingRight=false;
                break;
            case 72:


                isMovingLeft=false;
                break;
            case 81:
                lightningSprint=false;
                break;
            case 82:
                //isSprinting=false;
                break;


        }
    }
    static public void togglePerspective(){

        if(thirdPerspective){
            thirdPerspective=false;
            GameGrid.PFY=GAME_HEIGHT/2.0;
            //GameGrid.PVY=GAME_HEIGHT*.8;
            GameGrid.PVY=GameGrid.PFY+height;
            GameGrid.depthRatio=GAME_HEIGHT/(GameGrid.PVY-GameGrid.PFY)/4.0;

        }else{
            GameGrid.PFY=0;
            GameGrid.PVY=GAME_HEIGHT;
            GameGrid.depthRatio=GAME_HEIGHT/(GameGrid.PVY-GameGrid.PFY);
            thirdPerspective=true;

        }

    }
}
