import java.awt.*;

public class Chunk {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static int numOfCubeX=16;
    static int numOfCubeY=16;
    static int numOfCubeZ=200;
    int xPosition;
    int yPosition;
    Cube[][][] cubes=new Cube[numOfCubeX][numOfCubeY][numOfCubeZ];
    boolean[][][] cubePositions =new boolean[numOfCubeX][numOfCubeY][numOfCubeZ];

    int chunkToNormNumX;
    int chunkToNormNumY;


    Chunk(int GAME_WIDTH,int GAME_HEIGHT,int positionX,int positionY){
        this.xPosition=positionX;
        this.yPosition=positionY;
        Chunk.GAME_WIDTH =GAME_WIDTH;
        Chunk.GAME_HEIGHT =GAME_HEIGHT;
        this.chunkToNormNumX=numOfCubeX*xPosition;
        this.chunkToNormNumY=numOfCubeY*yPosition;

        createCubes();
    }
    public void drawMap(int x, int y,int z){
        for(var i=-5;i<5;i++){
            for(var j=0;j<10;j++){
                newCube(i,j,-1);
            }
        }
        for(var j=0;j<10;j++){
            newCube(-1,j,0);
            newCube(1,j,0);
        }
        for(var i=0;i<10;i++){
            for(var j=0;j<10;j++){
                if(j>=i)
                    newCube(4,i,j-i);
            }
        }




    }
    public void newCube(int x,int y,int z){
        cubes[x][y][z]=new Cube(x+xPosition*numOfCubeX,y+yPosition*numOfCubeY,z,GameGrid.depthRatio,this);
        cubePositions[x][y][z]=true;
    }
    public void createCubes(){
        //drawMap(0,0,0);
        /*
        newCube(1,1,1);
        newCube(2,1,1);
        newCube(3,1,1);
        newCube(4,1,1);
        */
/*
        for(var i=0;i<numOfCubeX;i++){
            newCube(i,0,0);
            newCube(i,numOfCubeX-1,0);
            newCube(0,i,0);
            newCube(numOfCubeX-1,i,0);


        }
        */
    /*
        for(var i=0;i<numOfCubeX;i++) {
            for (var j = 0; j < numOfCubeX; j++){
                newCube(i, j, 0);
            }
        }

     */

        for(var k=0;k<numOfCubeX;k++){
            for(var i=k;i<numOfCubeX-k;i++) {
                for (var j = k; j < numOfCubeX-k; j++){
                    newCube(i, j, k);
                }
            }

        }





/*
        newCube(6,6,12);
        newCube(6,6,13);
        newCube(5,6,13);
        newCube(7,6,13);
        newCube(6,6,14);
*/

    }
    public void updateData(double deltaTime){

        for(var i=0;i<numOfCubeX;i++){
                for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = 0; k < numOfCubeZ; k++) {
                            if (cubePositions[i][j][k]) {
                                cubes[i][j][k].updateData(deltaTime);

                            }
                        }
                }
        }


    }
    public void draw(Graphics g){
        for (var i= 0; i < numOfCubeZ; i++) {
                for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = 0; k < numOfCubeX; k++) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                                /*
                                if((Player.yPosition+1-(1-Player.depth/Cube.defaultSize)/2.0-(cubes[k][j][i].yPosition))>0){
                                    Player.draw(g);
                                }
                                
                                 */



                            }
                        }
                }
        }

        for (var i= numOfCubeZ-1; i >-1; i--) {
             for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = 0; k < numOfCubeX; k++) {
                            if (cubePositions[k][j][i]&&(i+1)*Cube.defaultSize-GameGrid.PFY>Player.zPosition*Cube.defaultSize) {
                                cubes[k][j][i].draw(g);
                               //System.out.println("test");
                            }
                        }
                }
        }

    }

}
