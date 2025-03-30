import java.awt.*;

public class FireBall {

    //double intialPosX;
    //double intialPosY;

    double xPosition;
    double yPosition;

    double zPosition;


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







    FireBall(double xPos, double yPos,double zPos, double angle, double speed, int size, double damage ){
        xPosition=xPos;
        yPosition=yPos-0.5;

        angleOfShout=angle;
        this.speed=speed;
        this.size=size;
        this.damage=damage;
        zPosition=zPos;
        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+1);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition);
    }

    public void updateData(double deltaTime) {
        xVelocity=speed * Math.cos(angleOfShout);
        yVelocity=speed * Math.sin(angleOfShout);
        xPosition += xVelocity*deltaTime;
        yPosition +=yVelocity*deltaTime;
        double correctionY=0.5;
        double correctionX=0;
        double difPosXA = (xPosition - correctionX - Player.xPosition);
        double difPosYA = (yPosition + correctionY - Player.yPosition);
        cubeIn[0]=(int)(xPosition+0.5);
        cubeIn[1]=(int)(yPosition+1);
        cubeIn[2]=(int)(zPosition);
        if(xPosition<0)cubeIn[0]=(int)(xPosition-0.5);
        if(yPosition<0)cubeIn[1]=(int)(yPosition);
       // System.out.println(cubeIn[0]+", "+cubeIn[1]+", "+cubeIn[2]);

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
            marketForDeletion=true;
            //CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNum+CubeContainer.numOfChunkX].cubePositions[newXpos][newYpos][cubeIn[2]]=false;
            //CubeContainer.chunks[xChunkNum+CubeContainer.numOfChunkX][yChunkNum+CubeContainer.numOfChunkX].cubes[newXpos][newYpos][cubeIn[2]]=null;


        }
        }

        if(Math.sqrt(Math.pow(difPosYA,2)+Math.pow(difPosXA,2))>diSpawnDistance)marketForDeletion=true;
        if(damage<=0)marketForDeletion=true;


        double difPosZ=((Player.zPosition-zPosition)*Cube.defaultSize);

        double xPositionA = (Player.xPosition + difPosXA * Math.cos(GameGrid.angleForXRotation) + difPosYA * Math.sin(GameGrid.angleForXRotation) + correctionX);
        double yPositionA = (Player.yPosition - difPosXA * Math.sin(GameGrid.angleForXRotation) + difPosYA * Math.cos(GameGrid.angleForXRotation) - correctionY);
        double difPosXR=((Player.xPosition-xPositionA)*Cube.width);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*Cube.depth);
        double sizeRatio=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        double sizeRatio2=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        newSize=  (size*sizeRatio);
        newPosX=  (GameGrid.PVX-((Player.xPosition-xPositionA)*Cube.depth)*sizeRatio-newSize/2);
        newPosY2=((GameGrid.PVY-GameGrid.PFY)*sizeRatio2+GameGrid.PFY+(Player.zPosition-2)*sizeRatio*Cube.defaultSize);
        newSize2=  (size*sizeRatio2);
        newPosX2=  (GameGrid.PVX-((Player.xPosition-xPositionA)*Cube.depth)*sizeRatio-newSize2/2);



    }

    public void draw(Graphics g){
        g.setColor(new Color(10,10,10,60));
        g.fillOval((int) (newPosX2), (int) (newPosY2-newSize2/2), (int) newSize2, (int) newSize2);
        g.setColor(Color.yellow);
        g.fillOval((int) (newPosX-newSize/5), (int) (newPosY-newSize/2-newSize/5), (int)( newSize+newSize/2.5), (int)(newSize+newSize/2.5));
        g.setColor(Color.orange);
        g.fillOval((int) (newPosX-newSize/10), (int) (newPosY-newSize/2-newSize/10), (int) ( newSize+newSize/5), (int) (newSize+newSize/5));
        g.setColor(Color.red);
        g.fillOval((int) (newPosX), (int) (newPosY-newSize/2), (int) newSize, (int) newSize);
    }

}
