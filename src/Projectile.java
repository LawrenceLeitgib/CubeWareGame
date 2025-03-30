import java.awt.*;

abstract class Projectile {
    private final double size;
    private final boolean isFriendly;
    private final double xPositionIni;
    private final double yPositionIni;
    private final double zPositionIni;
    private final double yVelocity;
    private final double xVelocity;
    private double xPosition;
    private double yPosition;
    private final double zPosition;
    private boolean marketForDeletion=false;
    private double damage;
    static final int diSpawnDistance=50;
    private static final int range=40;
    private int[] cubeIn;
    private final  GameGrid gameGrid;
    Projectile(GameGrid gameGrid,double xPos, double yPos, double zPos, double angle, double speed, double size, double damage,boolean isFriendly ){
        this.gameGrid=gameGrid;
        xPosition=xPos;
        yPosition=yPos-0.5;
        zPosition=zPos;
        xPositionIni=xPos;
        yPositionIni=yPos-0.5;
        zPositionIni=zPos;
        xVelocity=speed * Math.cos(angle);
        yVelocity=speed * Math.sin(angle);
        this.size=size;
        this.damage=damage;
        this.isFriendly=isFriendly;
        cubeIn=setNewCubeIn();
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
    public double getyVelocity() {
        return yVelocity;
    }
    public double getxVelocity() {
        return xVelocity;
    }

    public int[] getCubeIn() {
        return cubeIn;
    }
    public boolean isFriendly() {
        return isFriendly;
    }
    public boolean isMarketForDeletion() {
        return marketForDeletion;
    }

    public double getSize() {
        return size;
    }
    public double getDamage() {
        return damage;
    }
    void setDamage(double damage) {
        this.damage = damage;
    }
    void updateData(double deltaTime) {
        setNewPosition(deltaTime);
        cubeIn= setNewCubeIn();
        checkForDeletion();

    }
    private void checkForDeletion(){
        if(detectCollisionWithBlock()){
            marketForDeletion=true;
        }
        double distanceWithPlayer=Math.sqrt(Math.pow(gameGrid.player.xPosition-xPosition,2)+Math.pow(gameGrid.player.yPosition-yPosition,2));
        double distanceWithOrigin=Math.sqrt(Math.pow(xPosition-xPositionIni,2)+Math.pow(yPosition-yPositionIni,2));
        if(distanceWithOrigin>range)marketForDeletion=true;
        if(damage<=0)marketForDeletion=true;
        if(distanceWithPlayer>diSpawnDistance)marketForDeletion=true;
    }
    private void setNewPosition(double deltaTime){
        xPosition += xVelocity*deltaTime;
        yPosition +=yVelocity*deltaTime;
    }
    private int[] setNewCubeIn(){
        int[] cubeIn=new int[3];
        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+1);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition);
        return cubeIn;
    }
    private boolean detectCollisionWithBlock(){
        int[] xInfo=CubeContainer.YAndXPositionToChunkPos(cubeIn[0]);
        int[] yInfo=CubeContainer.YAndXPositionToChunkPos(cubeIn[1]);
        if(gameGrid.cubeContainer.chunksPosition[xInfo[0]+CubeContainer.numOfChunkX][yInfo[0]+CubeContainer.numOfChunkX]&&cubeIn[2]>=0&&cubeIn[2]<200){
            if(gameGrid.cubeContainer.chunks[xInfo[0]+CubeContainer.numOfChunkX][yInfo[0]+CubeContainer.numOfChunkX].cubePositions[xInfo[1]][yInfo[1]][cubeIn[2]]){
                gameGrid.cubeContainer.removeCube(cubeIn[0],cubeIn[1],cubeIn[2]);
                return true;
            }
        }
        return false;
    }
    public void draw(Graphics g){

    }
    double[] getScreenPosAndSize(){
        double correctionY=0.5;
        double correctionX=0;
        double difPosXA = (xPosition - correctionX - gameGrid.player.xPosition);
        double difPosYA = (yPosition + correctionY - gameGrid.player.yPosition);
        double difPosZ=((gameGrid.player.zPosition-zPosition)* GameGrid.defaultSize);
        double xPositionA = (gameGrid.player.xPosition + difPosXA * Math.cos(GameGrid.angleForHorizontalRotation) + difPosYA * Math.sin(GameGrid.angleForHorizontalRotation) + correctionX);
        double yPositionA = (gameGrid.player.yPosition - difPosXA * Math.sin(GameGrid.angleForHorizontalRotation) + difPosYA * Math.cos(GameGrid.angleForHorizontalRotation) - correctionY);
        double difPosXR=((gameGrid.player.xPosition-xPositionA)* GameGrid.defaultSize);
        double difPosYR= ((gameGrid.player.yPosition-(yPositionA- GameGrid.distancePlayerCamera))* GameGrid.defaultSize);
        double sizeRatio=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newSize=  (size*sizeRatio)* GameGrid.defaultSize;
        double newPosX=  (GameGrid.PVX-(difPosXR)*sizeRatio-newSize/2);
        double sizeRatio2=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        double newPosY2=((GameGrid.PVY-GameGrid.PFY)*sizeRatio2+GameGrid.PFY+(gameGrid.player.zPosition-2)*sizeRatio* GameGrid.defaultSize);
        double newSize2=  (size*sizeRatio2)* GameGrid.defaultSize;
        double newPosX2=  (GameGrid.PVX-((gameGrid.player.xPosition-xPositionA)* GameGrid.defaultSize)*sizeRatio-newSize2/2);

        return new double[]{newPosX,newPosY,newSize,newPosX2,newPosY2,newSize2};
    }
}
