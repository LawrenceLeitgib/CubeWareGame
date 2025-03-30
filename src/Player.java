import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

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
    static double[][] corners=new double[12][2];
    double speed=4.2;
    double xVelocity;
    double yVelocity;
    double zVelocity;

    boolean isSlowing=false;
    boolean isFlying=true;

    double slowMultiplier=0.1;
    double runningMultiplier=1.8;

    boolean isMovingUp=false;
    boolean isMovingDown=false;
    boolean isMovingForward =false;
    boolean isMovingBackward =false;
    static boolean isMovingLeft=false;
    static boolean isMovingRight=false;

    boolean isJumping=false;

    static double newPosY;
    static double newPosX;

    boolean lightningSprint=false;
    int lightningSprintCount=0;

    double lightningSprintTime =1;

    double lightningSprintCount2= lightningSprintTime;


    static int[] chunkIn=new int[2];

    static int numOfChunkToDraw=3;

    static boolean thirdPerspective=true;
    boolean[][][] megaChunkCubePositions =new boolean[Chunk.numOfCubeX*3][Chunk.numOfCubeY*3][Chunk.numOfCubeZ];
    static int[] cubeIn=new int[3];

    int yPosCount=0;
    double[] yPosHistoric=new double[10];
    boolean isSprinting;
    boolean spaceHasBeenClick;

    double flyingCount=0;
    double flyingTime=.2;

    boolean spaceHasBeenReleased;
    static double distanceFromMiddle;
    static double jumpSpeed=15;


    double pushCount=0;
    double pushTime=.05;


    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double positionZ){
        Player.xPosition=0;
        Player.yPosition=0;
        Player.zPosition=5;
        Player.GAME_WIDTH =GAME_WIDTH;
        Player.GAME_HEIGHT =GAME_HEIGHT;
        chunkIn[0]=(int)(xPosition/Chunk.numOfCubeX);
        chunkIn[1]=(int)(yPosition/Chunk.numOfCubeY);
        if(xPosition<0)Player.chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX-1);
        if(yPosition<0)Player.chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY-1);
        distanceFromMiddle =Math.sqrt(Math.pow(Player.xPosition,2)+Math.pow(Player.yPosition,2));
        newChunkAround();
        double sizeRatio=GameGrid.GAME_HEIGHT/((cubeAway*Cube.defaultSize)*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);

        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY);
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        double newDepth=  (depth*sizeRatio);
        newPosX=  (GameGrid.PVX-Cube.defaultSize*sizeRatio/2);
        corners=getCorners(newPosX,newPosY,newWidth,newHeight,0,0,0);





    }

    public  void respawn() {
        Stats.health=Stats.maxHealth;
        Player.xPosition=0;
        Player.yPosition=0;
        Player.zPosition=5;
        chunkIn[0]=(int)(xPosition/Chunk.numOfCubeX);
        chunkIn[1]=(int)(yPosition/Chunk.numOfCubeY);
        if(xPosition<0)Player.chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX-1);
        if(yPosition<0)Player.chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY-1);
        Stats.mana=Stats.maxMana;
        Stats.xp=0;
        GamePanel.gameState=GamePanel.GameStates.get("Running");
        newChunkAround();
        EnemiesContainer.enemies = new ArrayList<Enemy>();
        FireBallContainer.fireBalls=new ArrayList<FireBall>();




    }

    public void newChunkAround(){

        for(int i=Player.chunkIn[0]-Player.numOfChunkToDraw;i<=Player.chunkIn[0]+Player.numOfChunkToDraw;i++){
            for(int j=Player.chunkIn[1]-Player.numOfChunkToDraw;j<=Player.chunkIn[1]+Player.numOfChunkToDraw;j++){
                if(!CubeContainer.chunksPosition[CubeContainer.numOfChunkX+i][CubeContainer.numOfChunkY+j]){
                    CubeContainer.chunks[CubeContainer.numOfChunkX+i][CubeContainer.numOfChunkY+j]=new Chunk(GAME_WIDTH,GAME_HEIGHT,i,j);
                     CubeContainer.chunksPosition[CubeContainer.numOfChunkX+i][CubeContainer.numOfChunkY+j]=true;
                }
            }
        }

    }
    public void updateData(double deltaTime)
    {
        Player.chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX);
        Player.chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY);
        if(xPosition<0)Player.chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX-1);
        if(yPosition<0)Player.chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY-1);

        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+0.5);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition-0.5);
        distanceFromMiddle =Math.sqrt(Math.pow(Player.xPosition,2)+Math.pow(Player.yPosition,2));

        if(flyingCount>0){
            flyingCount+=deltaTime;
            if(spaceHasBeenClick){
                toggleFly();
                spaceHasBeenClick=false;
            }
        }
        if(spaceHasBeenClick){
            flyingCount+=deltaTime;
            spaceHasBeenClick=false;
        }

        if(flyingCount>flyingTime){
            flyingCount=0;
        }


        lightningSprintCount2+=deltaTime;
        if(lightningSprint){
            if(lightningSprintCount2>= lightningSprintTime){
                lightningSprintCount2=0;
                Stats.mana-=4;
                lightningSprintCount=30;
                if(Stats.mana<0){
                    Stats.mana+=4;
                    lightningSprintCount=0;
                }
            }
        }



//System.out.println(GameGrid.PVY-GameGrid.PFY);




        //System.out.println(Player.chunkIn[0]+" "+Player.chunkIn[1]);
        updateMegaChunk();


        double multiplierOfSpeed=1;
        if(isSprinting){
            multiplierOfSpeed=runningMultiplier;
            GameGrid.depthRatio=1.2;
        }else{
            GameGrid.depthRatio=1;

        }
        if(!thirdPerspective){
            cubeAway=-5;
            //cubeAway=-3.5;

        }else{
            cubeAway=2;


        }
        if(!isFlying){
            if(isSlowing)multiplierOfSpeed=slowMultiplier;

            setZVelocity(zVelocity-GameGrid.gravityAcceleration*deltaTime);
           if(zVelocity<-90)zVelocity+=GameGrid.gravityAcceleration*deltaTime;
           zPosition+=zVelocity*deltaTime;
           if(zVelocity>0) detectionCollision(2);
            if(zVelocity<0){
                if(detectionCollision(1)[1]){
                    if(isJumping){
                        zVelocity=jumpSpeed;
                    }
                }

            }
        }
        else{
            if(isMovingUp) {
                zPosition+=speed*multiplierOfSpeed*deltaTime;
                detectionCollision(2);
            }
            if(isMovingDown){
                zPosition-=speed*multiplierOfSpeed*deltaTime;
                if(detectionCollision(1)[1]){
                   // isFlying=false;
                };
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
            yVelocity-=200.2*Math.cos(GameGrid.angleForXRotation)*deltaTime*20;
            xVelocity+=200.2*Math.sin(GameGrid.angleForXRotation)*deltaTime*20;
            lightningSprintCount--;
        }


        xPosition+=xVelocity*deltaTime;
        yPosition+=yVelocity*deltaTime;




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
        if(cubeBack&&cubeLeft&&cubeFront&&cubeRight) {

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
        else if((xVelocity>0&&cubeRight&&yVelocity<0&&cubeFront)||(xVelocity<0&&cubeLeft&&yVelocity<0&&cubeFront)){
            if(deltaYPos>0){
                if(xVelocity>0)detectionCollision(4);
                if(xVelocity<0) detectionCollision(3);
                detectionCollision(5);




            }else{
                detectionCollision(5);
                if(xVelocity<0) detectionCollision(3);
                if(xVelocity>0)detectionCollision(4);
            }
        }
        else if((xVelocity>0&&cubeRight&&yVelocity>0&&cubeBack)||(xVelocity<0&&cubeLeft&&yVelocity>0&&cubeBack)){
            if(deltaYPos<0){
                if(xVelocity>0)detectionCollision(4);
                if(xVelocity<0) detectionCollision(3);
                detectionCollision(6);




            }else{
                detectionCollision(6);
                if(xVelocity<0) detectionCollision(3);
                if(xVelocity>0)detectionCollision(4);

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
        pushCount+=deltaTime;
        if(pushCount>=pushTime){
                for(int i=0;i<EnemiesContainer.enemies.size();i++){
                    if(zPosition<EnemiesContainer.enemies.get(i).zPosition+EnemiesContainer.enemies.get(i).height/Cube.defaultSize&&zPosition+height/Cube.defaultSize>EnemiesContainer.enemies.get(i).zPosition)
                        if(Math.sqrt(Math.pow(EnemiesContainer.enemies.get(i).xPosition-xPosition,2)+Math.pow(EnemiesContainer.enemies.get(i).yPosition-yPosition,2))<(EnemiesContainer.enemies.get(i).width/2+width/2)/Cube.defaultSize){
                            xPosition-=Math.cos(EnemiesContainer.enemies.get(i).angleWithPlayer)*speed*20*deltaTime;
                            xVelocity=-Math.cos(EnemiesContainer.enemies.get(i).angleWithPlayer)*speed*20*deltaTime;
                            yPosition-=Math.sin(EnemiesContainer.enemies.get(i).angleWithPlayer)*speed*20*deltaTime;
                            yVelocity=-Math.sin(EnemiesContainer.enemies.get(i).angleWithPlayer)*speed*20*deltaTime;
                            detectionCollision(3);
                            detectionCollision(4);
                            detectionCollision(5);
                            detectionCollision(6);
                            Stats.health-=EnemiesContainer.enemies.get(i).damage;
                            pushCount = 0;
                        }
                }
            }
        yPosHistoric[yPosCount]=yPosition;
            yPosCount++;
            if(yPosCount>=10){
                yPosCount=0;
        }

        detectionCollision(0);
        xPosition=  (int)((xPosition)*1000)/1000.0;
        yPosition=  (int)((yPosition)*1000)/1000.0;
        zPosition=  (int)((zPosition)*1000)/1000.0;
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


        if(!thirdPerspective)return;
        double sizeRatio=GameGrid.GAME_HEIGHT/((cubeAway*Cube.defaultSize)*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY);
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        double newDepth=  (depth*sizeRatio);
        newPosX=  (GameGrid.PVX-Cube.defaultSize*sizeRatio/2);
        corners=getCorners(newPosX,newPosY,newWidth,newHeight,0,0,0);

        g.setColor(Color.BLACK);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[1][0], (int) corners[1][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[2][0], (int) corners[2][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[3][0], (int) corners[3][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[0][0], (int) corners[0][1]);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[4][0], (int) corners[4][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);






    }
    public static void draw1(Graphics g){
        if(!thirdPerspective)return;
       // if(true)return;


        double sizeRatio=GameGrid.GAME_HEIGHT/((cubeAway*Cube.defaultSize)*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY);
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        double newDepth=  (depth*sizeRatio);
        newPosX=  (GameGrid.PVX-Cube.defaultSize*sizeRatio/2);
        corners=getCorners(newPosX,newPosY,newWidth,newHeight,0,0,0);


        g.setColor(Color.black);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[1][0], (int) corners[1][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[2][0], (int) corners[2][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[3][0], (int) corners[3][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[0][0], (int) corners[0][1]);

        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[8][0], (int) corners[8][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[9][0], (int) corners[9][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[10][0], (int) corners[10][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[11][0], (int) corners[11][1]);

        //g.fillOval((int) (corners[1][0]-5), (int) (corners[1][1]-5),10,10);
        g.setColor(new Color(0,0,0,100));
        g.fillOval((int) ((corners[0][0]+corners[1][0])/2+((corners[0][0]-corners[1][0]))/2), (int) ((corners[0][1]+corners[3][1])/2-((corners[0][1]-corners[3][1]))/2), (int) ((-corners[0][0]+corners[1][0])+0.5), (int) ((corners[0][1]-corners[3][1])+0.5));

    }
    public static void draw2(Graphics g){
        if(!thirdPerspective)return;
        double sizeRatio=GameGrid.GAME_HEIGHT/((cubeAway*Cube.defaultSize)*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY);
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        double newDepth=  (depth*sizeRatio);
        newPosX=  (GameGrid.PVX-Cube.defaultSize*sizeRatio/2);
        corners=getCorners(newPosX,newPosY,newWidth,newHeight,0,0,0);
        g.setColor(Color.black);
        g.drawLine((int) corners[12][0], (int) corners[12][1], (int) corners[8][0], (int) corners[8][1]);
        g.drawLine((int) corners[13][0], (int) corners[13][1], (int) corners[9][0], (int) corners[9][1]);
        g.drawLine((int) corners[14][0], (int) corners[14][1], (int) corners[10][0], (int) corners[10][1]);
        g.drawLine((int) corners[15][0], (int) corners[15][1], (int) corners[11][0], (int) corners[11][1]);
        if((2-zPosition+cubeIn[2])*Cube.defaultSize>height||Math.round((zPosition-cubeIn[2])*100000)/100000.0==0.2)
{
            g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
            g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
            g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
            g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);

        }


    }
    public static void draw3(Graphics g){
        if(!thirdPerspective)return;
        if((2-zPosition+cubeIn[2])*Cube.defaultSize>=height)return;
        //System.out.println(Math.round((zPosition-cubeIn[2])*100000)/100000.0);
        if(Math.round((zPosition-cubeIn[2])*100000)/100000.0==.2)return;
        if(GamePanel.gameState==GamePanel.GameStates.get("Menu"))return;
        double sizeRatio=GameGrid.GAME_HEIGHT/((cubeAway*Cube.defaultSize)*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY);
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        double newDepth=  (depth*sizeRatio);
        newPosX=  (GameGrid.PVX-Cube.defaultSize*sizeRatio/2);
        corners=getCorners(newPosX,newPosY,newWidth,newHeight,0,0,0);
        g.setColor(Color.black);
        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);

        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[12][0], (int) corners[12][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[13][0], (int) corners[13][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[14][0], (int) corners[14][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[15][0], (int) corners[15][1]);



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
           zPosition=zPosUnder+1;
           zVelocity=0;
       }
       if(collision[2]&&zPosition-zPosAbove+height/Cube.defaultSize<sensitivity&&(isMovingUp||zVelocity>0)){
           zPosition=zPosAbove-height/Cube.defaultSize;
           zVelocity=0;
       }
       if(collision[3]&&(xLeftPos+1-(1-width/Cube.defaultSize)/2)-xPosition<sensitivity&&xVelocity<0){
           xPosition=xLeftPos+1-(1-width/Cube.defaultSize)/2;
       }
       if(collision[4]&&xPosition-(xRightPos-1+(1-width/Cube.defaultSize)/2)<sensitivity&&xVelocity>0){
         xPosition=xRightPos-1+(1-width/Cube.defaultSize)/2;
       }
       if(collision[5]&&(yFrontPos+1-(1-depth/Cube.defaultSize)/2)-yPosition<sensitivity&&yVelocity<0){
           yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;
       }
       if(collision[6]&&yPosition-(yBackPos-1+(1-depth/Cube.defaultSize)/2)<sensitivity&&yVelocity>0) {
           yPosition=yBackPos-1+(1-depth/Cube.defaultSize)/2;
       }




       for(var i=1;i<7;i++){
           if (collision[i]) {
               collision[0] = true;
               break;
           }
       }


       return collision;
   }
    public static double[][]  getCorners(double newPosX,double newPosY,double newWidth,double newHeight,double difPosZ,double difPosXA,double difPosYA){
        double sizeRatioValue=(GameGrid.depthRatio-GameGrid.GAME_HEIGHT)/GameGrid.depthRatio;

        double difPosXARight=(xPosition+Cube.defaultSize/Cube.defaultSize-Player.xPosition);
        double yPositionARight=  (Player.yPosition);
        double xPositionARight=  (Player.xPosition+difPosXARight);;
        double difPosYRRight= ((Player.yPosition-(yPositionARight-Player.cubeAway))*Cube.defaultSize);
        double sizeRatioRight=GameGrid.GAME_HEIGHT/(difPosYRRight*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);


        if(difPosYRRight<sizeRatioValue){
            sizeRatioRight=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRRight-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }
        double newPosYRight=((GameGrid.PVY-GameGrid.PFY)*sizeRatioRight+GameGrid.PFY+difPosZ*sizeRatioRight);
        double newPosXRight=  (GameGrid.PVX-((Player.xPosition-xPositionARight)*Cube.defaultSize)*sizeRatioRight-(Cube.defaultSize*sizeRatioRight)/2);

       double difPosYAFront=(yPosition-1-Player.yPosition);
        double yPositionAFront=  (Player.yPosition+difPosYAFront);
        double xPositionAFront=  (Player.xPosition+difPosXA);
        double difPosYRFront= ((Player.yPosition-(yPositionAFront-Player.cubeAway))*Cube.defaultSize);
        double sizeRatioFront=GameGrid.GAME_HEIGHT/(difPosYRFront*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);

        if(difPosYRFront<sizeRatioValue){
            sizeRatioFront=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRFront-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }

        double newPosYFront=((GameGrid.PVY-GameGrid.PFY)*sizeRatioFront+GameGrid.PFY+difPosZ*sizeRatioFront);
        double newPosXFront=  (GameGrid.PVX-((Player.xPosition-xPositionAFront)*Cube.defaultSize)*sizeRatioFront-(Cube.defaultSize*sizeRatioFront)/2);


        double yPositionAFrontRight=  (Player.yPosition+difPosYAFront);
        double xPositionAFrontRight=  (Player.xPosition+difPosXARight);
        double difPosYRFrontRight= ((Player.yPosition-(yPositionAFrontRight-Player.cubeAway))*Cube.defaultSize);
        double sizeRatioFrontRight=GameGrid.GAME_HEIGHT/(difPosYRFrontRight*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        if(difPosYRFrontRight<sizeRatioValue){
            sizeRatioFrontRight=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRFrontRight-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }


        double newPosYFrontRight=((GameGrid.PVY-GameGrid.PFY)*sizeRatioFrontRight+GameGrid.PFY+difPosZ*sizeRatioFrontRight);
        double newPosXFrontRight=  (GameGrid.PVX-((Player.xPosition-xPositionAFrontRight)*Cube.defaultSize)*sizeRatioFrontRight-(Cube.defaultSize*sizeRatioFrontRight)/2);







        //if(xPosition==5)System.out.println(deltaYRight);






        double [][] corners=new double[16][2];
        corners[0][1]=newPosY;
        corners[1][1]=newPosYRight;
        corners[2][1]=newPosYFrontRight;
        corners[3][1]=newPosYFront;




        corners[0][0]=newPosX;
        corners[1][0]=newPosXRight;
        corners[2][0]=newPosXFrontRight;
        corners[3][0]=newPosXFront;



        double [][] corners2=new double[4][2];
        double [][] corners3=new double[4][2];



        corners2[0][0]=(corners[0][0]*(width/2+Cube.defaultSize/2.0)+corners[1][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners2[0][1]=(corners[0][1]*(width/2+Cube.defaultSize/2.0)+corners[1][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);

        corners2[1][0]=(corners[1][0]*(depth/2+Cube.defaultSize/2.0)+corners[2][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners2[1][1]=(corners[1][1]*(depth/2+Cube.defaultSize/2.0)+corners[2][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners2[2][0]=(corners[2][0]*(width/2+Cube.defaultSize/2.0)+corners[3][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners2[2][1]=(corners[2][1]*(width/2+Cube.defaultSize/2.0)+corners[3][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);

        corners2[3][0]=(corners[3][0]*(depth/2+Cube.defaultSize/2.0)+corners[0][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners2[3][1]=(corners[3][1]*(depth/2+Cube.defaultSize/2.0)+corners[0][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);


        corners3[0][0]=(corners[0][0]*(depth/2+Cube.defaultSize/2.0)+corners[3][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners3[0][1]=(corners[0][1]*(depth/2+Cube.defaultSize/2.0)+corners[3][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners3[1][0]=(corners[1][0]*(width/2+Cube.defaultSize/2.0)+corners[0][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners3[1][1]=(corners[1][1]*(width/2+Cube.defaultSize/2.0)+corners[0][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);

        corners3[2][0]=(corners[2][0]*(depth/2+Cube.defaultSize/2.0)+corners[1][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners3[2][1]=(corners[2][1]*(depth/2+Cube.defaultSize/2.0)+corners[1][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners3[3][0]=(corners[3][0]*(width/2+Cube.defaultSize/2.0)+corners[2][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners3[3][1]=(corners[3][1]*(width/2+Cube.defaultSize/2.0)+corners[2][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);



        corners[0][0]=(corners2[0][0]*(depth/2+Cube.defaultSize/2.0)+corners3[3][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[0][1]=(+corners2[0][1]*(depth/2+Cube.defaultSize/2.0)+corners3[3][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);


        corners[1][0]=(corners3[1][0]*(depth/2+Cube.defaultSize/2.0)+corners2[2][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[1][1]=(corners3[1][1]*(depth/2+Cube.defaultSize/2.0)+corners2[2][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners[2][0]=(corners2[2][0]*(depth/2+Cube.defaultSize/2.0)+corners3[1][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[2][1]=(corners2[2][1]*(depth/2+Cube.defaultSize/2.0)+corners3[1][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);


        corners[3][0]=(corners3[3][0]*(depth/2+Cube.defaultSize/2.0)+corners2[0][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[3][1]=(corners3[3][1]*(depth/2+Cube.defaultSize/2.0)+corners2[0][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);




        double sizeRatio1=(corners[0][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY);
        double sizeRatio2=(corners[1][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY);
        double sizeRatio3=(corners[2][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY);
        double sizeRatio4=(corners[3][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY);



        corners[4][0]= corners[0][0];
        corners[5][0]=corners[1][0];
        corners[6][0]= corners[2][0];
        corners[7][0]=corners[3][0];
        corners[4][1]=corners[0][1]-height*sizeRatio1;
        corners[5][1]=corners[1][1]-height*sizeRatio2;
        corners[6][1]=corners[2][1]-height*sizeRatio3;
        corners[7][1]=corners[3][1]-height*sizeRatio4;

        corners[8][0]= corners[0][0];
        corners[9][0]=corners[1][0];
        corners[10][0]= corners[2][0];
        corners[11][0]=corners[3][0];
        corners[8][1]=corners[0][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio1;
        corners[9][1]=corners[1][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio2;
        corners[10][1]=corners[2][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio3;
        corners[11][1]=corners[3][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio4;

        corners[12][0]= corners[0][0];
        corners[13][0]=corners[1][0];
        corners[14][0]= corners[2][0];
        corners[15][0]=corners[3][0];

        if((2-zPosition+cubeIn[2])*Cube.defaultSize>height){
            corners[12][1]=corners[4][1];
            corners[13][1]=corners[5][1];
            corners[14][1]=corners[6][1];
            corners[15][1]=corners[7][1];
        }
        else {

        corners[12][1]=corners[8][1]-Cube.defaultSize*sizeRatio1;
        corners[13][1]=corners[9][1]-Cube.defaultSize*sizeRatio2;
        corners[14][1]=corners[10][1]-Cube.defaultSize*sizeRatio3;
        corners[15][1]=corners[11][1]-Cube.defaultSize*sizeRatio4;}

        /*
        corners[12][1]=corners[4][1]+(zPosition-cubeIn[2])*Cube.defaultSize*sizeRatio1;
        corners[13][1]=corners[5][1]+(zPosition-cubeIn[2])*Cube.defaultSize*sizeRatio2;
        corners[14][1]=corners[6][1]+(zPosition-cubeIn[2])*Cube.defaultSize*sizeRatio3;
        corners[15][1]=corners[7][1]+(zPosition-cubeIn[2])*Cube.defaultSize*sizeRatio4;

         */



        return corners;
    }


    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 65:
                if(thirdPerspective)GameGrid.isRotatingLeft=true;
                else{
                    isMovingLeft=true;
                }
                break;

            case 68:
                if(thirdPerspective)GameGrid.isRotatingRight=true;
                else{
                    isMovingRight=true;
                }
                break;
            case 83:
                isMovingBackward=true;
                break;

            case 87:
                isMovingForward=true;
                break;
            case 89:
                break;

            case 88:

                break;
            case 70:
                //flySwitch();
                //FireBall.speed=20;
                break;
            case 32:
                if(!isFlying)isJumping=true;
                if(isFlying)isMovingUp=true;
                if(spaceHasBeenReleased){
                    spaceHasBeenClick=true;
                    spaceHasBeenReleased=false;
                }
                break;

            case 16:
                isSlowing=true;
                if(isFlying)isMovingDown=true;

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
                if(thirdPerspective)GameGrid.isRotatingLeft=false;
                else{
                    isMovingLeft=false;
                }
                break;

            case 68:
                if(thirdPerspective)GameGrid.isRotatingRight=false;
                else{
                    isMovingRight=false;
                }
                break;
            case 83:
                isMovingBackward=false;
                break;

            case 87:
                isMovingForward=false;
                isSprinting=false;
                break;
            case 89:
                break;

            case 88:
                break;
            case 32:
                if(!isFlying)isJumping=false;
                if(isFlying)isMovingUp=false;
                spaceHasBeenReleased=true;

                break;
            case 16:
                if(isFlying)isMovingDown=false;

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
if(GamePanel.gameState==GamePanel.GameStates.get("Running")){
        if(thirdPerspective){
            thirdPerspective=false;
            GameGrid.PFY=GAME_HEIGHT/2.0;
            //GameGrid.PVY=GAME_HEIGHT*.8;
            GameGrid.PVY=GameGrid.PFY+height;
            GameGrid.depthRatio=GAME_HEIGHT/(GameGrid.PVY-GameGrid.PFY)/4.0;
            GameGrid.isRotatingLeft=false;
            GameGrid.isRotatingRight=false;


        }else{
            GameGrid.PFY=GAME_HEIGHT/3.0;
            GameGrid.PVY=GAME_HEIGHT;
            GameGrid.depthRatio=GAME_HEIGHT/(GameGrid.PVY-GameGrid.PFY);
            thirdPerspective=true;
            isMovingLeft=false;
            isMovingRight=false;

        }}

    }
    private void toggleFly() {
        if(isFlying){
            isFlying=false;
        }
        else{
            isFlying=true;
        }
        isJumping=false;
        isMovingUp=false;
        isMovingDown=false;
        zVelocity=0;
    }
}
