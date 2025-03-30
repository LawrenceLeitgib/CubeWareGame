import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {
    static double xPosition;
    static double yPosition;
    static double zPosition;
    static double cubeAway=2;
    static double width=.8;
    static double height=1.8;
    static double depth=.8;
    static double[][] corners=new double[12][2];
    double speed=4.2;
    double runningMultiplier=1.8;
    static double xVelocity;
    static double yVelocity;
    double zVelocity;
    boolean isSlowing=false;
    boolean isFlying=true;
    boolean isJumping=false;
    double slowMultiplier=0.1;
    boolean isRunning;
    private boolean isMovingUp=false;
    private boolean isMovingDown=false;
    private boolean isMovingForward =false;
    private boolean isMovingBackward =false;
    private boolean isMovingLeft=false;
    private boolean isMovingRight=false;
    static int[] chunkIn=new int[2];
    static boolean thirdPerspective=true;
    static int[] cubeIn=new int[3];
    private int yPosCount=0;
    double[] yPosHistoric=new double[10];
    static double distanceFromMiddle;
    static double jumpSpeed=15;
    private double pushCount=0;
    private double pushTime=.1;
    SpecialMoveHandler SMH;
    Player(double positionX, double positionY, double positionZ){
        Player.xPosition=positionX;
        Player.yPosition=positionY;
        Player.zPosition=positionZ+10;
        setCubeAndChunkIn();
        //newChunkAround();
        SMH=new SpecialMoveHandler();
    }
    public void updateData(double deltaTime) {
        SMH.updateData(deltaTime);
        //updateMegaChunk();
        setNewPositions(deltaTime);
        detectionCollisionWithContext();
        projectileCollisionHandler(deltaTime);
        detectionCollisionWithEnemy(deltaTime);
        setCubeAndChunkIn();
        detectionCollision(0);
        xPosition=  (int)((xPosition)*1000)/1000.0;
        yPosition=  (int)((yPosition)*1000)/1000.0;
        zPosition=  (int)((zPosition)*1000)/1000.0;
    }
    public static void draw(Graphics g){
        if(!thirdPerspective)return;
        corners=getCorners();

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
        corners=getCorners();
        g.setColor(Color.blue);
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
        corners=getCorners();
        g.setColor(Color.yellow);
        g.drawLine((int) corners[12][0], (int) corners[12][1], (int) corners[8][0], (int) corners[8][1]);
        g.drawLine((int) corners[13][0], (int) corners[13][1], (int) corners[9][0], (int) corners[9][1]);
        g.drawLine((int) corners[14][0], (int) corners[14][1], (int) corners[10][0], (int) corners[10][1]);
        g.drawLine((int) corners[15][0], (int) corners[15][1], (int) corners[11][0], (int) corners[11][1]);
        if((2-zPosition+cubeIn[2])>height||Math.round((zPosition-cubeIn[2])*100000)/100000.0==0.2)
        {
            g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
            g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
            g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
            g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);

        }
    }
    public static void draw3(Graphics g){
        if(!thirdPerspective)return;
        if((2-zPosition+cubeIn[2])>=height)return;
        if(Math.round((zPosition-cubeIn[2])*100000)/100000.0==.2)return;
        if(GamePanel.gameState==GamePanel.GameStates.get("Menu"))return;
        corners=getCorners();
        g.setColor(Color.red);
        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);

        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[12][0], (int) corners[12][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[13][0], (int) corners[13][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[14][0], (int) corners[14][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[15][0], (int) corners[15][1]);
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
        EntityContainer.entities = new ArrayList<Entity>();
        ProjectileContainer.Projectiles =new ArrayList<Projectile>();




    }
    private void newChunkAround(){
        CubeContainer.CreateNewChunks();
    }
    private void setCubeAndChunkIn(){
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

    }
    private boolean getCubeIn(){
        int[] cubeIn=new int[3];
        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+0.5);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition-0.5);

        int[] info1=CubeContainer.YAndXPositionToChunkPos(cubeIn[0]);
        int[] info2=CubeContainer.YAndXPositionToChunkPos(cubeIn[1]);
    try {


        if (CubeContainer.chunks[info1[0]+CubeContainer.numOfChunkX][info2[0]+CubeContainer.numOfChunkY].cubePositions[info1[1]][info2[1]][cubeIn[2]]) return true;
        if (CubeContainer.chunks[info1[0]+CubeContainer.numOfChunkX][info2[0]+CubeContainer.numOfChunkY].cubePositions[info1[1]][info2[1]][cubeIn[2] + 1]) return true;
        if (CubeContainer.chunks[info1[0]+CubeContainer.numOfChunkX][info2[0]+CubeContainer.numOfChunkY].cubePositions[info1[1]][info2[1]][cubeIn[2] + 2]) return true;
    }catch (Exception ignored){

    }


        return false;

    }
    private void setNewPositions(double deltaTime){
        double multiplierOfSpeed=1;
        if(isRunning){
            multiplierOfSpeed=runningMultiplier;
            GameGrid.depthRatio=1.2;
        }else{
            GameGrid.depthRatio=1;

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
        SMH.lightningSprintHandler(deltaTime);
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

        xPosition+=xVelocity*deltaTime;
        yPosition+=yVelocity*deltaTime;

    }

    private void detectionCollisionWithContext(){

        int newXpos=cubeIn[0];
        int newYposFront=cubeIn[1]-1;
        int newYposBack=cubeIn[1] +1;

        int xChunkNum=0;
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
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumFront+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposFront][(int)(cubeIn[2]+height+.5)]);
        boolean cubeBack=(CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][cubeIn[2]]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][cubeIn[2]+1]
                ||CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNumBack+CubeContainer.numOfChunkY].cubePositions[newXpos][newYposBack][(int)(cubeIn[2]+height+.5)]);
        boolean cubeRight=(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]])||
                xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][cubeIn[2]+1])||
                xVelocity>0&&(CubeContainer.chunks[XChunkNumRight+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposRight][newYpos][(int)(cubeIn[2]+height+.5)]);
        boolean cubeLeft=xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]])||
                xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][cubeIn[2]+1])||
                xVelocity<0&&(CubeContainer.chunks[XChunkNumLeft+CubeContainer.numOfChunkX][YChunkNum+CubeContainer.numOfChunkY].cubePositions[newXposLeft][newYpos][(int)(cubeIn[2]+height+.5)]);

        int beforeCount=yPosCount-3;
        if(beforeCount<0)beforeCount+=10;
        double deltaYPos=yPosHistoric[yPosCount]-yPosHistoric[beforeCount];
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
        yPosHistoric[yPosCount]=yPosition;
        yPosCount++;
        if(yPosCount>=10){
            yPosCount=0;
        }

    }
    private void detectionCollisionWithEnemy(double deltaTime){
        pushCount+=deltaTime;
        if(pushCount>=pushTime){
            for(int i = 0; i< EntityContainer.entities.size(); i++){
                Entity entity=EntityContainer.entities.get(i);
                if(zPosition<entity.getzPosition()+ entity.getHeight()&&zPosition+height> entity.getzPosition()) {
                    if (Math.sqrt(Math.pow(entity.getxPosition() - xPosition, 2) + Math.pow(entity.getyPosition() - yPosition, 2)) < (entity.getWidth() / 2 + width / 2)) {
                        xPosition -= Math.cos(entity.angleWithPlayer) * entity.getSpeed() * 20 * deltaTime;
                        xVelocity = -Math.cos(entity.angleWithPlayer) * entity.getSpeed() * 20 * deltaTime;
                        yPosition -= Math.sin(entity.angleWithPlayer) * entity.getSpeed() * 20 * deltaTime;
                        yVelocity = -Math.sin(entity.angleWithPlayer) * entity.getSpeed() * 20 * deltaTime;
                        detectionCollision(3);
                        detectionCollision(4);
                        detectionCollision(5);
                        detectionCollision(6);
                        Stats.health -= EntityContainer.entities.get(i).strength;
                        pushCount = 0;
                    }
                }
            }
        }
    }
    private boolean detectionCollisionWithProjectile(double ProjectilePosX, double ProjectilePosY, double ProjectilePosZ, double ProjectileSize){
        if(ProjectilePosX<xPosition+(width/2) &&ProjectilePosX>xPosition-(width/2))
            if(ProjectilePosY>yPosition-(depth/2+1/2.0)&&ProjectilePosY<yPosition+(depth/2-1/2.0)){
                if(ProjectilePosZ>zPosition&&ProjectilePosZ<zPosition+height){

                    return true;
                }

            }
        if( Math.sqrt(Math.pow(ProjectilePosX - xPosition, 2) + Math.pow(ProjectilePosY - yPosition, 2)) < Math.sqrt(Math.pow(width, 2) + Math.pow(depth, 2)) ){
            return ProjectilePosZ > zPosition && ProjectilePosZ < zPosition + height;
        }
        return false;
    }
    private void projectileCollisionHandler(double deltaTime){
        for(var i = 0; i< ProjectileContainer.Projectiles.size(); i++){
            if(!ProjectileContainer.Projectiles.get(i).isFriendly() ) {
                if (detectionCollisionWithProjectile(ProjectileContainer.Projectiles.get(i).getxPosition(), ProjectileContainer.Projectiles.get(i).getyPosition(), ProjectileContainer.Projectiles.get(i).getzPosition(), ProjectileContainer.Projectiles.get(i).getSize())) {
                    yVelocity = ProjectileContainer.Projectiles.get(i).getyVelocity()  * deltaTime*2;
                    xVelocity = ProjectileContainer.Projectiles.get(i).getxVelocity()  * deltaTime*2;
                    yPosition += ProjectileContainer.Projectiles.get(i).getyVelocity()  * deltaTime*2;
                    xPosition += ProjectileContainer.Projectiles.get(i).getxVelocity()  * deltaTime*2;
                    detectionCollision(3);
                    detectionCollision(4);
                    detectionCollision(5);
                    detectionCollision(6);
                    Stats.health-=ProjectileContainer.Projectiles.get(i).getDamage();
                    ProjectileContainer.Projectiles.remove(i);

                }
            }
        }


    }
    private void setZVelocity(double zVelocity) {
        this.zVelocity = zVelocity;
    }
    private boolean[] detectionCollision(int num){
       boolean[] collision=new boolean[7];
        int zPosUnder=(int)(zPosition);
        if(zPosition<0)zPosUnder=(int)(zPosition-1);
        int zPosAbove=(int)(zPosition+height);
        if(zPosition<0)zPosAbove=(int)(zPosition+height-1);

        double forOtherSensitivity=0.01;

        int xLeftPos=(int)(xPosition+(1-width)/2);
        if(xPosition<0)xLeftPos=(int)(xPosition+(1-width)/2-1);

        int xRightPos=(int)(xPosition-(1-width)/2+1);
        if(xPosition<0)xRightPos=(int)(xPosition-(1-width)/2);

        int xLeftPosForOther=(int)(xPosition+(1-width)/2+forOtherSensitivity);
        if(xPosition<0.5)xLeftPosForOther=(int)(xPosition+(1-width)/2-1+forOtherSensitivity);

        int xRightPosForOther=(int)(xPosition-(1-width)/2+1-forOtherSensitivity);
        if(xPosition<-0.5)xRightPosForOther=(int)(xPosition-(1-width)/2-forOtherSensitivity);

        int yFrontPos=(int)(yPosition+(1-depth)/2);
        if(yPosition<0)yFrontPos=(int)(yPosition+(1-depth)/2-1);

        int yBackPos=(int)(yPosition-(1-depth)/2+1);
        if(yPosition<0)yBackPos=(int)(yPosition-(1-depth)/2);

        int yFrontPosForOther=(int)(yPosition+(1-depth)/2+forOtherSensitivity);
        if(yPosition<0.5)yFrontPosForOther=(int)(yPosition+(1-depth)/2-1+forOtherSensitivity);

        int yBackPosForOther=(int)(yPosition-(1-depth)/2+1-forOtherSensitivity);
        if(yPosition<-0.5)yBackPosForOther=(int)(yPosition-(1-depth)/2-forOtherSensitivity);

        int zPosUnderForOther=(int)(zPosition+forOtherSensitivity);
        if(zPosition<0)zPosUnderForOther=(int)(zPosition-1+forOtherSensitivity);

        int zPosMiddleForOther=(int)(zPosition+1+forOtherSensitivity);
        if(zPosition<0)zPosMiddleForOther=(int)(zPosition+1-1+forOtherSensitivity);

        int zPosAboveForOther=(int)(zPosition+height-forOtherSensitivity);
        if(zPosition<0)zPosAboveForOther=(int)(zPosition+height-1-forOtherSensitivity);


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


        int[] xLeftPosForOtherInfo=CubeContainer.YAndXPositionToChunkPos(xLeftPosForOther);
        int xLeftPosForOtherChunk=xLeftPosForOtherInfo[0];
        int newXLeftPosForOther=xLeftPosForOtherInfo[1];
        int[] xLeftPosInfo=CubeContainer.YAndXPositionToChunkPos(xLeftPos);
        int xLeftPosChunk=xLeftPosInfo[0];
        int newXLeftPos=xLeftPosInfo[1];
        int[] xRightPosForOtherInfo=CubeContainer.YAndXPositionToChunkPos(xRightPosForOther);
        int xRightPosForOtherChunk=xRightPosForOtherInfo[0];
        int newXRightPosForOther=xRightPosForOtherInfo[1];
        int[] xRightPosInfo=CubeContainer.YAndXPositionToChunkPos(xRightPos);
        int xRightPosChunk=xRightPosInfo[0];
        int newXRightPos=xRightPosInfo[1];
        int[] yFrontPosForOtherInfo=CubeContainer.YAndXPositionToChunkPos(yFrontPosForOther);
        int yFrontPosForOtherChunk=yFrontPosForOtherInfo[0];
        int newYFrontPosForOther=yFrontPosForOtherInfo[1];
        int[] yFrontPosInfo=CubeContainer.YAndXPositionToChunkPos(yFrontPos);
        int yFrontPosChunk=yFrontPosInfo[0];
        int newYFrontPos=yFrontPosInfo[1];
        int[] yBackPosForOtherInfo=CubeContainer.YAndXPositionToChunkPos(yBackPosForOther);
        int yBackPosForOtherChunk=yBackPosForOtherInfo[0];
        int newYBackPosForOther=yBackPosForOtherInfo[1];
        int[] yBackPosInfo=CubeContainer.YAndXPositionToChunkPos(yBackPos);
        int yBackPosChunk=yBackPosInfo[0];
        int newYBackPos=yBackPosInfo[1];


        if(num==1 ||num==0)
            collision[1]= CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPosForOther][newYFrontPosForOther][zPosUnder]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPosForOther][newYBackPosForOther][zPosUnder]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPosForOther][newYFrontPosForOther][zPosUnder]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPosForOther][newYBackPosForOther][zPosUnder];


        if(num==2 ||num==0)
            collision[2]= CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPosForOther][newYFrontPosForOther][zPosAbove]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPosForOther][newYBackPosForOther][zPosAbove]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPosForOther][newYFrontPosForOther][zPosAbove]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPosForOther][newYBackPosForOther][zPosAbove];


        if(num==3 ||num==0)
            collision[3]= CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPos][newYFrontPosForOther][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPos][newYBackPosForOther][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPos][newYFrontPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPos][newYBackPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPos][newYFrontPosForOther][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPos][newYBackPosForOther][zPosAboveForOther];

        if(num==4 ||num==0)
            collision[4]= CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPos][newYFrontPosForOther][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPos][newYBackPosForOther][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPos][newYFrontPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPos][newYBackPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPos][newYFrontPosForOther][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPos][newYBackPosForOther][zPosAboveForOther];

        if(num==5 ||num==0)
            collision[5]= CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXRightPosForOther][newYFrontPos][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXLeftPosForOther][newYFrontPos][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXRightPosForOther][newYFrontPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXLeftPosForOther][newYFrontPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXRightPosForOther][newYFrontPos][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXLeftPosForOther][newYFrontPos][zPosAboveForOther];
        if(num==6 ||num==0)
            collision[6]= CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXRightPosForOther][newYBackPos][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXLeftPosForOther][newYBackPos][zPosUnderForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXRightPosForOther][newYBackPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXLeftPosForOther][newYBackPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXRightPosForOther][newYBackPos][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXLeftPosForOther][newYBackPos][zPosAboveForOther];

        double sensitivity=1;
        //double sensitivity=0.5;
      // System.out.println( collision[3]);
      // System.out.println(collision[5]+" "+xRightPosForOther+" "+xPosition);

       if(collision[1]&&zPosUnder+1-zPosition<sensitivity&&(isMovingDown||zVelocity<0)){
           zPosition=zPosUnder+1;
           zVelocity=0;
       }
       if(collision[2]&&zPosition-zPosAbove+height<sensitivity&&(isMovingUp||zVelocity>0)){
           zPosition=zPosAbove-height;
           zVelocity=0;
       }
       if(collision[3]&&(xLeftPos+1-(1-width)/2)-xPosition<sensitivity&&xVelocity<0){
            double old=xPosition;
              xPosition=xLeftPos+1-(1-width)/2;
              if(getCubeIn()&&Math.abs(old-xPosition)>.2){
                  xPosition=old;
              }
       }
       if(collision[4]&&xPosition-(xRightPos-1+(1-width)/2)<sensitivity&&xVelocity>0){
           double old=xPosition;
           xPosition=xRightPos-1+(1-width)/2;
           if(getCubeIn()&&Math.abs(old-xPosition)>.2){
               xPosition=old;

           }

       }
       if(collision[5]&&(yFrontPos+1-(1-depth)/2)-yPosition<sensitivity&&yVelocity<0){
           double old=yPosition;
           yPosition=yFrontPos+1-(1-depth)/2;
           if(getCubeIn()&&Math.abs(old-yPosition)>.2){
               yPosition=old;

           }
       }
       if(collision[6]&&yPosition-(yBackPos-1+(1-depth)/2)<sensitivity&&yVelocity>0) {
           double old=yPosition;
           yPosition=yBackPos-1+(1-depth)/2;
           if(getCubeIn()&&Math.abs(old-yPosition)>.2){
               yPosition=old;
           }
       }

       for(var i=1;i<7;i++){
           if (collision[i]) {
               collision[0] = true;
               break;
           }
       }
       return collision;
   }
    public static double[][] getCorners(){
        double[][] corners=new double[16][2];
        double a=GameGrid.angleForXRotation;
        a=0;
        double[] info0= Cube.getObjectScreenPos(xPosition+(1-width)/2,yPosition-(1-depth)/2,zPosition,a);
        double[] info1= Cube.getObjectScreenPos(xPosition+1-(1-width)/2,yPosition-(1-depth)/2,zPosition,a);
        double[] info2= Cube.getObjectScreenPos(xPosition+1-(1-width)/2,yPosition-1+(1-depth)/2,zPosition,a);
        double[] info3= Cube.getObjectScreenPos(xPosition+(1-width)/2,yPosition-1+(1-depth)/2,zPosition,a);
        double[][] info= {info0,info1,info2,info3};
        int n=4;
        for(int i=0;i<4;i++){
            for(int j=0;j<n;j++){
                corners[i+j*4][0]=info[i][0];
            }
            corners[i][1]=info[i][1];
            corners[i+4][1]=info[i][1]-height* GameGrid.defaultSize*info[i][2];
            corners[i+4*2][1]=info[i][1]-(1-zPosition+cubeIn[2])* GameGrid.defaultSize*info[i][2];
            if((2-zPosition+cubeIn[2])>height){
                corners[i+4*3][1]=corners[4+i][1];
            }
            else {
                corners[i+4*3][1]=corners[4*2+i][1]- GameGrid.defaultSize*info[i][2];
            }
        }
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
                break;
            case 32:
                if(!isFlying)isJumping=true;
                if(isFlying)isMovingUp=true;
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
            case 82:
                isRunning =true;
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
                isRunning=false;
                break;
            case 89:
                break;

            case 88:
                break;
            case 32:
                if(!isFlying)isJumping=false;
                if(isFlying)isMovingUp=false;

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
            case 82:
                if(!isMovingForward)isRunning=false;
                break;


        }
    }
    public void togglePerspective(){
        System.out.println(Chunk.numberOfCube);
        if(GamePanel.gameState==GamePanel.GameStates.get("Running")){
            if(thirdPerspective){
                thirdPerspective=false;
                GameGrid.PFY= GameGrid.GAME_HEIGHT/2.0;
                //GameGrid.PVY=GAME_HEIGHT*.8;
                GameGrid.PVY=GameGrid.PFY+height* GameGrid.defaultSize;
                GameGrid.depthRatio= GameGrid.GAME_HEIGHT/(GameGrid.PVY-GameGrid.PFY)/4.0;
                GameGrid.isRotatingLeft=false;
                GameGrid.isRotatingRight=false;
                cubeAway=-5;

            }
            else{
                GameGrid.PFY= GameGrid.GAME_HEIGHT/3.0;
                GameGrid.PVY= GameGrid.GAME_HEIGHT;
                GameGrid.depthRatio= GameGrid.GAME_HEIGHT/(GameGrid.PVY-GameGrid.PFY);
                thirdPerspective=true;
                isMovingLeft=false;
                isMovingRight=false;
                cubeAway=2;
            }
        }
    }
    public void toggleFly() {
        isFlying= !isFlying;
        isJumping=false;
        isMovingUp=false;
        isMovingDown=false;
        zVelocity=0;
    }
}
