import java.awt.*;
public class Entity {
    private boolean marketForDeletion;
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private double height=2;
    private double width=.8;
    private double depth=.8;
    private double speed=.5;
    private double runningMultiplier=7;
    double xVelocity=0;
    double yVelocity=0;
    double zVelocity=0;
    int[] cubeIn=new int[3];
    static int[] chunkIn=new int[2];
    double angleWithPlayer;
    double distanceWithPlayer;
    static int diSpawnDistance=60;
    double MaxHP;
    double HP;
    double strength=1;
    double xp;
    Color color;
    Entity(double x, double y, double z,double strength,double hp,double xp){
        xPosition=x;
        yPosition=y;
        zPosition=z;
        this.strength=strength;
        this.MaxHP=hp;
        this.HP=hp;
        this.xp=xp;
        this.color=Color.red;
        while(detectionCollisionWithBlocks(0)[0]){
            zPosition++;
        }
    }

    public double getxPosition() {
        return xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }
    public double getzPosition() {
        return zPosition;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public double getDepth() {
        return depth;
    }

    public double getRunningMultiplier() {
        return runningMultiplier;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isMarketForDeletion() {
        return marketForDeletion;
    }


    public void updateData(double deltaTime) {
        setBasicData();
        setNewPositions(deltaTime);
        projectileCollisionHandler(deltaTime);
        deathHandler();
    }
    public void setNewPositions(double deltaTime){
        detectionCollisionWithOther(deltaTime);
        zVelocity-=GameGrid.gravityAcceleration*deltaTime;
        if(zVelocity<-90)zVelocity+=GameGrid.gravityAcceleration*deltaTime;
        zPosition+=zVelocity*deltaTime;
        if(zVelocity>0) detectionCollisionWithBlocks(2);
        if(zVelocity<0){
            if(detectionCollisionWithBlocks(1)[1]){
                xPosition+=xVelocity*deltaTime;
                yPosition+=yVelocity*deltaTime;
                rejectionFromSpawn(deltaTime);
                boolean[] coli= detectionCollisionWithBlocks(0);

                if(coli[3]||coli[4]||coli[5]||coli[6]){
                    if((coli[3]&&!coli[7])||(coli[4]&&!coli[8])||(coli[5]&&!coli[9])||(coli[6]&&!coli[10]))
                        zVelocity=Player.jumpSpeed;
                }
            }
        }
        else{
            xPosition+=xVelocity*deltaTime;
            yPosition+=yVelocity*deltaTime;
            rejectionFromSpawn(deltaTime);

        }
        if(xVelocity>0){
            detectionCollisionWithBlocks(4);
        }
        if(xVelocity<0){
            detectionCollisionWithBlocks(3);
        }
        if(yVelocity>0){
            detectionCollisionWithBlocks(6);
        }
        if(yVelocity<0){
            detectionCollisionWithBlocks(5);
        }
        detectionCollisionWithBlocks(0);
    }
    private void setBasicData(){
        chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX);
        chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY);
        if(xPosition<0)chunkIn[0]=(int)((xPosition+0.5)/Chunk.numOfCubeX-1);
        if(yPosition<0)chunkIn[1]=(int)((yPosition+0.5)/Chunk.numOfCubeY-1);
        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+0.5);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition-0.5);
        distanceWithPlayer=Math.sqrt(Math.pow(Player.xPosition-xPosition,2)+Math.pow(Player.yPosition-yPosition,2));

        angleWithPlayer=Math.atan((Player.yPosition-yPosition)/(Player.xPosition-xPosition));
        if(Player.xPosition-xPosition>0){
            angleWithPlayer=Math.PI+angleWithPlayer;
        }else  if(Player.xPosition-xPosition<0&&Player.yPosition-yPosition>0){
            angleWithPlayer=2*Math.PI+angleWithPlayer;
        }
        if(Player.xPosition-xPosition==0){
            if(Player.yPosition-yPosition<=0)angleWithPlayer=Math.PI/2;
            if(Player.yPosition-yPosition>0)angleWithPlayer=3*Math.PI/2;
        }
    }
    private void deathHandler(){
        if(distanceWithPlayer>diSpawnDistance) this.marketForDeletion=true;
        if(HP<=0){
            HP=0;
            Stats.xp+=xp;
            this.marketForDeletion=true;
        }
    }
    public void draw(Graphics g){

        if(Math.sqrt(Math.pow(yPosition-Player.yPosition,2)+Math.pow(xPosition-Player.xPosition,2))>(Player.numOfChunkToDraw)*Chunk.numOfCubeX)return;
        double[][] corners=getCorners();
        for(var i=0;i<16;i++){
            if(corners[i][1]<-GameGrid.GAME_HEIGHT)return;
            if(corners[i][1]>GameGrid.GAME_HEIGHT*2)return;
            if(corners[i][0]>GameGrid.GAME_WIDTH*2)return;
            if(corners[i][0]<-GameGrid.GAME_WIDTH)return;
        }
        g.setColor(Color.blue);
        //g.drawRect((int) (newPosX), (int)( newPosY-newHeight), (int) newWidth, (int) newHeight);
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
    public void draw1(Graphics g){
        if(Math.sqrt(Math.pow(yPosition-Player.yPosition,2)+Math.pow(xPosition-Player.xPosition,2))>(Player.numOfChunkToDraw)*Chunk.numOfCubeX)return;
        double[][] corners=getCorners();
        for(var i=0;i<16;i++){
            if(corners[i][1]<-GameGrid.GAME_HEIGHT)return;
            if(corners[i][1]>GameGrid.GAME_HEIGHT*2)return;
            if(corners[i][0]>GameGrid.GAME_WIDTH*2)return;
            if(corners[i][0]<-GameGrid.GAME_WIDTH)return;
        }

        g.setColor(color);
        //g.drawRect((int) (newPosX), (int)( newPosY-newHeight), (int) newWidth, (int) newHeight);

        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[1][0], (int) corners[1][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[2][0], (int) corners[2][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[3][0], (int) corners[3][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[0][0], (int) corners[0][1]);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[8][0], (int) corners[8][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[9][0], (int) corners[9][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[10][0], (int) corners[10][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[11][0], (int) corners[11][1]);

        //g.fillPolygon(new int[]{(int) corners[0][0], (int) corners[1][0], (int) corners[9][0], (int) corners[8][0]},new int[]{(int) corners[0][1], (int) corners[1][1], (int) corners[9][1], (int) corners[8][1]},4);

    }
    public void draw2(Graphics g){
        if(Math.sqrt(Math.pow(yPosition-Player.yPosition,2)+Math.pow(xPosition-Player.xPosition,2))>(Player.numOfChunkToDraw)*Chunk.numOfCubeX)return;
        double[][] corners=getCorners();
        for(var i=0;i<16;i++){
            if(corners[i][1]<-GameGrid.GAME_HEIGHT)return;
            if(corners[i][1]>GameGrid.GAME_HEIGHT*2)return;
            if(corners[i][0]>GameGrid.GAME_WIDTH*2)return;
            if(corners[i][0]<-GameGrid.GAME_WIDTH)return;
        }

        g.setColor(color);
        //g.drawRect((int) (newPosX), (int)( newPosY-newHeight), (int) newWidth, (int) newHeight);

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
    public void draw3(Graphics g){
        if(Math.sqrt(Math.pow(yPosition-Player.yPosition,2)+Math.pow(xPosition-Player.xPosition,2))>(Player.numOfChunkToDraw)*Chunk.numOfCubeX)return;
        double[][] corners=getCorners();
        for(var i=0;i<16;i++){
            if(corners[i][1]<-GameGrid.GAME_HEIGHT)return;
            if(corners[i][1]>GameGrid.GAME_HEIGHT*2)return;
            if(corners[i][0]>GameGrid.GAME_WIDTH*2)return;
            if(corners[i][0]<-GameGrid.GAME_WIDTH)return;
        }
        g.setColor(color);
        //g.drawRect((int) (newPosX), (int)( newPosY-newHeight), (int) newWidth, (int) newHeight);
        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);

        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[12][0], (int) corners[12][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[13][0], (int) corners[13][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[14][0], (int) corners[14][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[15][0], (int) corners[15][1]);
        drawHealthBar(g,corners);
    }
    public void drawHealthBar(Graphics g,double[][] corners){
        if(HP>=MaxHP)return;
        double width=Math.sqrt(Math.pow((corners[0][0]-corners[1][0]),2)+Math.pow((corners[0][0]-corners[3][0]),2));
        Rectangle healthRect=new Rectangle((int)( (corners[4][0]+corners[6][0])/2-width/2),
                (int)((corners[4][1]+corners[6][1])/2-width/5),
                (int) width,
                (int) (width/10));
        Rectangle healthRect2=new Rectangle((int) ((corners[4][0]+corners[6][0])/2-width/2), (int) ((corners[4][1]+corners[6][1])/2-width/5), (int)( width*HP/MaxHP), (int) (width/10));
        GamePanel.drawRectWithBorder2(g,healthRect,Color.red,Color.black,2);
        GamePanel.drawRectWithBorder2(g,healthRect2,Color.GREEN,Color.black,2);

    }
    private double[][] getCorners(){
        double[][] corners=new double[16][2];
        double a=GameGrid.angleForXRotation;
        double[] info0= Cube.getObjectScreenPos(xPosition+(1-width)/2,yPosition-(1-depth)/2,zPosition,a);
        double[] info1= Cube.getObjectScreenPos(xPosition+1-(1-width)/2,yPosition-(1-depth)/2,zPosition,a);
        double[] info2= Cube.getObjectScreenPos(xPosition+1-(1-width)/2,yPosition-1+(1-depth)/2,zPosition,a);
        double[] info3= Cube.getObjectScreenPos(xPosition+(1-width)/2,yPosition-1+(1-depth)/2,zPosition,a);

        double sizeRatio0=info0[2];
        double sizeRatio1=info1[2];
        double sizeRatio2=info2[2];
        double sizeRatio3=info3[2];

        corners[0][0]=info0[0];
        corners[1][0]=info1[0];
        corners[2][0]=info2[0];
        corners[3][0]=info3[0];

        corners[0][1]=info0[1];
        corners[1][1]=info1[1];
        corners[2][1]=info2[1];
        corners[3][1]=info3[1];

        corners[4][0]= corners[0][0];
        corners[5][0]=corners[1][0];
        corners[6][0]= corners[2][0];
        corners[7][0]=corners[3][0];
        corners[4][1]=corners[0][1]-height*sizeRatio0*Cube.defaultSize;
        corners[5][1]=corners[1][1]-height*sizeRatio1*Cube.defaultSize;
        corners[6][1]=corners[2][1]-height*sizeRatio2*Cube.defaultSize;
        corners[7][1]=corners[3][1]-height*sizeRatio3*Cube.defaultSize;

        corners[8][0]= corners[0][0];
        corners[9][0]=corners[1][0];
        corners[10][0]= corners[2][0];
        corners[11][0]=corners[3][0];
        corners[8][1]=corners[0][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio0;
        corners[9][1]=corners[1][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio1;
        corners[10][1]=corners[2][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio2;
        corners[11][1]=corners[3][1]-(1-zPosition+cubeIn[2])*Cube.defaultSize*sizeRatio3;

        corners[12][0]= corners[0][0];
        corners[13][0]=corners[1][0];
        corners[14][0]= corners[2][0];
        corners[15][0]=corners[3][0];

        if((2-zPosition+cubeIn[2])>height){
            corners[12][1]=corners[4][1];
            corners[13][1]=corners[5][1];
            corners[14][1]=corners[6][1];
            corners[15][1]=corners[7][1];
        }
        else {

            corners[12][1]=corners[8][1]-Cube.defaultSize*sizeRatio0;
            corners[13][1]=corners[9][1]-Cube.defaultSize*sizeRatio1;
            corners[14][1]=corners[10][1]-Cube.defaultSize*sizeRatio2;
            corners[15][1]=corners[11][1]-Cube.defaultSize*sizeRatio3;
        }




        return corners;
    }
    private boolean[] detectionCollisionWithBlocks(int num){
        boolean[] collision=new boolean[11];
        Chunk chunkInside=CubeContainer.chunks[CubeContainer.numOfChunkX+chunkIn[0]][CubeContainer.numOfChunkY+chunkIn[1]];

       // boolean[][][] cubesPosChunkInside=chunkInside.cubePositions;
        //System.out.println(chunkInside.xPosition+" "+chunkInside.yPosition);
        int zPosUnder=(int)(zPosition);
        if(zPosition<0)zPosUnder=(int)(zPosition-1);
        int zPosAbove=(int)(zPosition+height);
        if(zPosition<0)zPosAbove=(int)(zPosition+height-1);


        double forOtherSensitivity=0.02;




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





        int xLeftPosForOtherChunk=0;
        int newXLeftPosForOther=xLeftPosForOther;
        while(newXLeftPosForOther>=Chunk.numOfCubeX){
            xLeftPosForOtherChunk+=1;
            newXLeftPosForOther-=Chunk.numOfCubeX;
        }
        while(newXLeftPosForOther<0){
            xLeftPosForOtherChunk-=1;
            newXLeftPosForOther+=Chunk.numOfCubeX;
        }
        int xLeftPosChunk=0;
        int newXLeftPos=xLeftPos;
        while(newXLeftPos>=Chunk.numOfCubeX){
            xLeftPosChunk+=1;
            newXLeftPos-=Chunk.numOfCubeX;
        }
        while(newXLeftPos<0){
            xLeftPosChunk-=1;
            newXLeftPos+=Chunk.numOfCubeX;
        }

        int xRightPosForOtherChunk=0;
        int newXRightPosForOther=xRightPosForOther;
        while(newXRightPosForOther>=Chunk.numOfCubeX){
            xRightPosForOtherChunk+=1;
            newXRightPosForOther-=Chunk.numOfCubeX;
        }
        while(newXRightPosForOther<0){
            xRightPosForOtherChunk-=1;
            newXRightPosForOther+=Chunk.numOfCubeX;
        }
        int xRightPosChunk=0;
        int newXRightPos=xRightPos;
        while(newXRightPos>=Chunk.numOfCubeX){
            xRightPosChunk+=1;
            newXRightPos-=Chunk.numOfCubeX;
        }
        while(newXRightPos<0){
            xRightPosChunk-=1;
            newXRightPos+=Chunk.numOfCubeX;
        }

        int yFrontPosForOtherChunk=0;
        int newYFrontPosForOther=yFrontPosForOther;
        while(newYFrontPosForOther>=Chunk.numOfCubeY){
            yFrontPosForOtherChunk+=1;
            newYFrontPosForOther-=Chunk.numOfCubeY;
        }
        while(newYFrontPosForOther<0){
            yFrontPosForOtherChunk-=1;
            newYFrontPosForOther+=Chunk.numOfCubeY;
        }
        int yFrontPosChunk=0;
        int newYFrontPos=yFrontPos;
        while(newYFrontPos>=Chunk.numOfCubeY){
            yFrontPosChunk+=1;
            newYFrontPos-=Chunk.numOfCubeY;
        }
        while(newYFrontPos<0){
            yFrontPosChunk-=1;
            newYFrontPos+=Chunk.numOfCubeY;
        }
        int yBackPosForOtherChunk=0;
        int newYBackPosForOther=yBackPosForOther;
        while(newYBackPosForOther>=Chunk.numOfCubeY){
            yBackPosForOtherChunk+=1;
            newYBackPosForOther-=Chunk.numOfCubeY;
        }
        while(newYBackPosForOther<0){
            yBackPosForOtherChunk-=1;
            newYBackPosForOther+=Chunk.numOfCubeY;
        }
        int yBackPosChunk=0;
        int newYBackPos=yBackPos;
        while(newYBackPos>=Chunk.numOfCubeY){
            yBackPosChunk+=1;
            newYBackPos-=Chunk.numOfCubeY;
        }
        while(newYBackPos<0){
            yBackPosChunk-=1;
            newYBackPos+=Chunk.numOfCubeY;
        }




        if(num==1 ||num==0){
            collision[1]= CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPosForOther][newYFrontPosForOther][zPosUnder]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPosForOther][newYBackPosForOther][zPosUnder]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPosForOther][newYFrontPosForOther][zPosUnder]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPosForOther][newYBackPosForOther][zPosUnder];
        }

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


        if(num==7 ||num==0)
            collision[7]= CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPos][newYFrontPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPos][newYBackPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXLeftPos][newYFrontPosForOther][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXLeftPos][newYBackPosForOther][zPosAboveForOther];

        if(num==8 ||num==0)
            collision[8]= CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPos][newYFrontPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPos][newYBackPosForOther][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yFrontPosForOtherChunk].cubePositions[newXRightPos][newYFrontPosForOther][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosChunk][CubeContainer.numOfChunkY+yBackPosForOtherChunk].cubePositions[newXRightPos][newYBackPosForOther][zPosAboveForOther];

        if(num==9 ||num==0)
            collision[9]= CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXRightPosForOther][newYFrontPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXLeftPosForOther][newYFrontPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXRightPosForOther][newYFrontPos][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yFrontPosChunk].cubePositions[newXLeftPosForOther][newYFrontPos][zPosAboveForOther];
        if(num==10 ||num==0)
            collision[10]= CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXRightPosForOther][newYBackPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXLeftPosForOther][newYBackPos][zPosMiddleForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xRightPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXRightPosForOther][newYBackPos][zPosAboveForOther]||
                    CubeContainer.chunks[CubeContainer.numOfChunkX+xLeftPosForOtherChunk][CubeContainer.numOfChunkY+yBackPosChunk].cubePositions[newXLeftPosForOther][newYBackPos][zPosAboveForOther];


        double sensitivity=1;

        if(collision[1]&&zPosUnder+1-zPosition<sensitivity&&(zVelocity<0)){
            zPosition=zPosUnder+1.001;
            zVelocity=0;
        }
        if(collision[2]&&zPosition-zPosAbove+height<sensitivity&&(zVelocity>0)){
            zPosition=zPosAbove-height;
            zVelocity=0;
        }
        if(collision[3]&&(xLeftPos+1-(1-width)/2)-xPosition<sensitivity&&xVelocity<0){
            xPosition=xLeftPos+1-(1-width)/2;
        }
        if(collision[4]&&xPosition-(xRightPos-1+(1-width)/2)<sensitivity&&xVelocity>0){
            xPosition=xRightPos-1+(1-width)/2;
        }
        if(collision[5]&&(yFrontPos+1-(1-depth)/2)-yPosition<sensitivity&&yVelocity<0){
            yPosition=yFrontPos+1-(1-depth)/2;
        }
        if(collision[6]&&yPosition-(yBackPos-1+(1-depth)/2)<sensitivity&&yVelocity>0) {
            yPosition=yBackPos-1+(1-depth)/2;
        }



        for(var i=1;i<7;i++){
            if (collision[i]) {
                collision[0] = true;
                break;
            }
        }


        return collision;
    }
    private boolean detectionCollisionWithProjectile(double ProjectilePosX, double ProjectilePosY, double ProjectilePosZ, double ProjectileSize){
        if(ProjectilePosX<xPosition+(width/2) &&ProjectilePosX>xPosition-(width/2))
            if(ProjectilePosY>yPosition-(depth/2+1/2.0)&&ProjectilePosY<yPosition+(depth/2-1/2.0)){
                if(ProjectilePosZ>zPosition&&ProjectilePosZ<zPosition+height){
                    return true;
                }

            }
        if( Math.sqrt(Math.pow(ProjectilePosX - xPosition, 2) + Math.pow(ProjectilePosY - yPosition, 2)) < Math.sqrt(Math.pow(width, 2) + Math.pow(depth, 2))){
            return ProjectilePosZ > zPosition && ProjectilePosZ < zPosition + height ;
        }
        return false;
    }
    private void projectileCollisionHandler(double deltaTime){
        for(var i = 0; i< ProjectileContainer.Projectiles.size(); i++){
            if(ProjectileContainer.Projectiles.get(i).isFriendly() ) {
                Projectile p=ProjectileContainer.Projectiles.get(i);
                if (detectionCollisionWithProjectile(p.getxPosition(), p.getyPosition(), p.getzPosition(), p.getSize())) {
                    yPosition += ProjectileContainer.Projectiles.get(i).getyVelocity()  * deltaTime;
                    xPosition += ProjectileContainer.Projectiles.get(i).getxVelocity()  * deltaTime;
                    if (HP < p.getDamage()) {
                        p.setDamage(p.getDamage()-HP);
                        HP = 0;
                    } else {
                        HP -= ProjectileContainer.Projectiles.get(i).getDamage();
                        ProjectileContainer.Projectiles.remove(i);
                    }
                }
            }
        }


    }
    private boolean detectionCollisionWithOther(double deltaTime){
            for(int i = 0; i< EntityContainer.entities.size(); i++){
                Entity E=EntityContainer.entities.get(i);
                if(E.zPosition==zPosition&&E.yPosition==yPosition&&E.xPosition==xPosition)continue;
                if(zPosition< E.zPosition+ E.height&&zPosition+height> E.zPosition)
                    if(Math.sqrt(Math.pow(E.xPosition-xPosition,2)+Math.pow(E.yPosition-yPosition,2))<(E.width/2+width/2)){
                        double angleWithOther=Math.atan((E.yPosition-yPosition)/(E.xPosition-xPosition));
                        if(E.xPosition-xPosition>0){
                            angleWithOther=Math.PI+angleWithOther;
                        }else  if(E.xPosition-xPosition<0&&E.yPosition-yPosition>0){
                            angleWithOther=2*Math.PI+angleWithOther;
                        }
                        if(E.xPosition-xPosition==0){
                            if(E.yPosition-yPosition<=0)angleWithOther=Math.PI/2;
                            if(E.yPosition-yPosition>0)angleWithOther=3*Math.PI/2;

                        }
                        xPosition+=Math.cos(angleWithOther)*speed*1*deltaTime;
                        xVelocity=+Math.cos(angleWithOther)*speed*1*deltaTime;
                        yPosition+=Math.sin(angleWithOther)*speed*1*deltaTime;
                        yVelocity=+Math.sin(angleWithOther)*speed*1*deltaTime;
                        detectionCollisionWithBlocks(3);
                        detectionCollisionWithBlocks(4);
                        detectionCollisionWithBlocks(5);
                        detectionCollisionWithBlocks(6);

                        E.xPosition-=Math.cos(angleWithOther)*speed*1*deltaTime;
                        E.xVelocity=-Math.cos(angleWithOther)*speed*1*deltaTime;
                        E.yPosition-=Math.sin(angleWithOther)*speed*1*deltaTime;
                        E.yVelocity=-Math.sin(angleWithOther)*speed*1*deltaTime;
                        E.detectionCollisionWithBlocks(3);
                        E.detectionCollisionWithBlocks(4);
                        E.detectionCollisionWithBlocks(5);
                        E.detectionCollisionWithBlocks(6);




                        return true;


                    }
            }
        return false;

    }
    private void rejectionFromSpawn(double deltaTime){
        double  distanceFromMiddle =Math.sqrt(Math.pow(xPosition,2)+Math.pow(yPosition,2));
        if(distanceFromMiddle<=GameGrid.safeZone) {
            xPosition +=xPosition/distanceFromMiddle*speed*runningMultiplier*deltaTime;
            yPosition +=yPosition/distanceFromMiddle*speed*runningMultiplier*deltaTime;
        }
        if(distanceFromMiddle<=GameGrid.safeZone-2){
            this.marketForDeletion=true;
        }
    }
}
