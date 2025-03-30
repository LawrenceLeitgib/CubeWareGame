import java.awt.*;

public class Projectile {
    private double xPosition;
    private double yPosition;
    private double zPosition;
    private final double xPositionIni;
    private final double yPositionIni;
    private final double zPositionIni;
    double newPosX;
    double newPosY;
    double newSize;
    double newPosX2;
    double newPosY2;
    double newSize2;
    double angleOfShout;
    double speed;
    int size;
    double damage;
    boolean marketForDeletion=false;
    double yVelocity;
    double xVelocity;
    int[] cubeIn=new int[3];
    int newXpos=cubeIn[0];
    int newYpos=cubeIn[1];
    int xChunkNum=0;
    int yChunkNum=0;
    static int diSpawnDistance=50;
    static int range=50;
    private double distanceWithPlayer;
    private double distanceWithOrigin;
    boolean isFriendly;
    Projectile(double xPos, double yPos, double zPos, double angle, double speed, int size, double damage,boolean isFriendly ){
        xPosition=xPos;
        yPosition=yPos-0.5;
        zPosition=zPos;
        xPositionIni=xPos;
        yPositionIni=yPos-0.5;
        zPositionIni=zPos;
        angleOfShout=angle;
        this.speed=speed;
        this.size=size;
        this.damage=damage;
        this.isFriendly=isFriendly;
        setNewCubeIn();
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

    public void updateData(double deltaTime) {
        setNewPosition(deltaTime);
        setNewCubeIn();
        if(detectCollisionWithBlock()){
            marketForDeletion=true;
        }
        if(distanceWithOrigin>range)marketForDeletion=true;
        if(damage<=0)marketForDeletion=true;
        if(distanceWithPlayer>diSpawnDistance)marketForDeletion=true;
    }
    public void setNewPosition(double deltaTime){
        xVelocity=speed * Math.cos(angleOfShout);
        yVelocity=speed * Math.sin(angleOfShout);
        xPosition += xVelocity*deltaTime;
        yPosition +=yVelocity*deltaTime;
    }
    public void setNewCubeIn(){
        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+1);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition);
        distanceWithPlayer=Math.sqrt(Math.pow(Player.xPosition-xPosition,2)+Math.pow(Player.yPosition-yPosition,2));
        distanceWithOrigin=Math.sqrt(Math.pow(xPosition-xPositionIni,2)+Math.pow(yPosition-yPositionIni,2));
    }
    private boolean detectCollisionWithBlock(){
        newXpos=cubeIn[0];
        newYpos=cubeIn[1];
        xChunkNum=0;
        yChunkNum=0;
        while(newXpos<0){
            newXpos+=Chunk.numOfCubeX;
            xChunkNum-=1;

        }
        while(newXpos>=Chunk.numOfCubeX){
            newXpos-=Chunk.numOfCubeX;
            xChunkNum+=1;

        }

        while(newYpos<0){
            newYpos+=Chunk.numOfCubeY;
            yChunkNum-=1;

        }
        while(newYpos>=Chunk.numOfCubeY){
            newYpos-=Chunk.numOfCubeY;
            yChunkNum+=1;

        }
        if(CubeContainer.chunksPosition[xChunkNum+CubeContainer.numOfChunkX][yChunkNum+CubeContainer.numOfChunkX]&&cubeIn[2]>=0&&cubeIn[2]<200){
            if(CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNum+CubeContainer.numOfChunkX].cubePositions[newXpos][newYpos][cubeIn[2]]){
               // CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNum+CubeContainer.numOfChunkX].cubes[newXpos][newYpos][cubeIn[2]]=null;
                //CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNum+CubeContainer.numOfChunkX].cubePositions[newXpos][newYpos][cubeIn[2]]=false;
                return true;
            }
        }

        return false;
    }
    public void draw(Graphics g){
        double correctionY=0.5;
        double correctionX=0;
        double difPosXA = (xPosition - correctionX - Player.xPosition);
        double difPosYA = (yPosition + correctionY - Player.yPosition);
        double difPosZ=((Player.zPosition-zPosition)*Cube.defaultSize);
        double xPositionA = (Player.xPosition + difPosXA * Math.cos(GameGrid.angleForXRotation) + difPosYA * Math.sin(GameGrid.angleForXRotation) + correctionX);
        double yPositionA = (Player.yPosition - difPosXA * Math.sin(GameGrid.angleForXRotation) + difPosYA * Math.cos(GameGrid.angleForXRotation) - correctionY);
        double difPosXR=((Player.xPosition-xPositionA)*Cube.width);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*Cube.depth);
        double sizeRatio=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        newSize=  (size*sizeRatio);
        newPosX=  (GameGrid.PVX-((Player.xPosition-xPositionA)*Cube.depth)*sizeRatio-newSize/2);
        double sizeRatio2=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY2=((GameGrid.PVY-GameGrid.PFY)*sizeRatio2+GameGrid.PFY+(Player.zPosition-2)*sizeRatio*Cube.defaultSize);
        newSize2=  (size*sizeRatio2);
        newPosX2=  (GameGrid.PVX-((Player.xPosition-xPositionA)*Cube.depth)*sizeRatio-newSize2/2);



    }
}
