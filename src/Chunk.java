import java.awt.*;

public class Chunk {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static int numOfCubeX = 16;
    static int numOfCubeY = 16;
    static int numOfCubeZ = 200;
    int xPosition;
    int yPosition;
    Cube[][][] cubes = new Cube[numOfCubeX][numOfCubeY][numOfCubeZ];
    boolean[][][] cubePositions = new boolean[numOfCubeX][numOfCubeY][numOfCubeZ];

    int chunkToNormNumX;
    int chunkToNormNumY;


    Chunk(int GAME_WIDTH, int GAME_HEIGHT, int positionX, int positionY) {
        this.xPosition = positionX;
        this.yPosition = positionY;
        Chunk.GAME_WIDTH = GAME_WIDTH;
        Chunk.GAME_HEIGHT = GAME_HEIGHT;
        this.chunkToNormNumX = numOfCubeX * xPosition;
        this.chunkToNormNumY = numOfCubeY * yPosition;

        createCubes();
    }

    public void drawMap(int x, int y, int z) {
        for (var i = -5; i < 5; i++) {
            for (var j = 0; j < 10; j++) {
                newCube(i, j, -1);
            }
        }
        for (var j = 0; j < 10; j++) {
            newCube(-1, j, 0);
            newCube(1, j, 0);
        }
        for (var i = 0; i < 10; i++) {
            for (var j = 0; j < 10; j++) {
                if (j >= i)
                    newCube(4, i, j - i);
            }
        }


    }

    public void newCube(int x, int y, int z) {
        cubes[x][y][z] = new Cube(x + xPosition * numOfCubeX, y + yPosition * numOfCubeY, z, GameGrid.depthRatio, this);
        cubePositions[x][y][z] = true;
    }

    public void drawLabyrinth() {

        //int[][] List1={}
        //comme dans square battle

        int[][] List1 = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1},
                {0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0},
                {1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                {1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 1, 1, 0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1, 1},
                {1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0}
        };

        for (var k = 1; k < 4; k++) {
            for (var i = 15; i >= 0; i--) {
                for (var j = 15; j >= 0; j--) {
                    if (List1[i][j] == 1) {
                        newCube(j, i, k);
                    }

                }
            }
        }
    }

    void drawLineY(int[] list, int numY) {
    }

    public void createCubes() {
        //drawMap(0,0,0);
        /*
        newCube(1,1,1);
        newCube(2,1,1);
        newCube(3,1,1);
        newCube(4,1,1);
        */
/*
        for(var i=0;i<numOfCubeX;i++){
            newCube(i,0,2);
            newCube(i,numOfCubeX-1,2);
            newCube(0,i,2);
            newCube(numOfCubeX-1,i,2);


        }

 */

    /*
        for(var i=0;i<numOfCubeX;i++) {
            for (var j = 0; j < numOfCubeX; j++){
                newCube(i, j, 0);
            }
        }



     */
        for (var i = 0; i < numOfCubeX; i++) {
            for (var j = 0; j < numOfCubeX; j++) {
                newCube(i, j, 0);
                newCube(i, j, 1);

            }
        }
/*
        newCube(0, 0, 3);
        newCube(1, 0, 3);
        newCube(0, 1, 3);
        newCube(1, 1, 3);
        newCube(0, 0, 4);
        newCube(1, 0, 4);
        newCube(0, 1, 4);
        newCube(1, 1, 4);


*/


        if (xPosition == 0 && yPosition == 0) {
           // newCube(5, 4, 3);
        }

        if (xPosition == 0 && yPosition == 0) {

           // newCube(5,0,5);

            //newCube(5,5,0);
            //newCube(6,5,0);
            // newCube(7,5,0);
            //newCube(8,5,0);

            //newCube(2,7,0);
            //newCube(8,7,0);

/*
            newCube(5, 5, 8);
            newCube(5, 5, 9);
            newCube(4, 5, 9);
            newCube(6, 5, 9);
            newCube(5, 6, 9);
            newCube(5, 4, 9);
            newCube(5, 5, 10);

 */


        }
        /*{

            for(var i=0;i<numOfCubeX;i++) {
                for (var j = 0; j < numOfCubeX; j++){
                    newCube(i, j, 2);

                }
            }

        }*/
        drawLabyrinth();
        /*
        for(var k=0;k<numOfCubeX;k++){
            for(var i=k;i<numOfCubeX-k;i++) {
                for (var j = k; j < numOfCubeX-k; j++){
                    newCube(i, j, k);
                }
            }

        }

 */
        /*
        newCube(6,6,12);
        newCube(6,6,13);
        newCube(5,6,13);
        newCube(7,6,13);
        newCube(6,6,14);
*/

    }

    public void updateData(double deltaTime) {
        // if (cubePositions[5][5][0]&&cubePositions[6][5][0])
        // System.out.println(cubes[5][5][0].newPosY-cubes[6][5][0].newPosY);

        for (var i = 0; i < numOfCubeX; i++) {
            for (var j = 0; j < numOfCubeY; j++) {
                for (var k = 0; k < numOfCubeZ; k++) {
                    if (cubePositions[i][j][k]) {
                        cubes[i][j][k].updateData(deltaTime);

                    }
                }
            }
        }


    }
    public void drawLayer(Graphics g,int z) {
        //if (xPosition == 0 && yPosition == 1) return;
        //if(Player.yPosition-Player.cubeAway<(yPosition)*numOfCubeY)System.out.println(yPosition);
        //if(Player.yPosition-Player.cubeAway>(yPosition+1)*numOfCubeY)System.out.println(yPosition);
      ///  System.out.println(Cube.angleForXRotation/Math.PI);
        int i=z;


            if(GameGrid.angleForXRotation<Math.PI/4){
                for(var j=0;j<numOfCubeY;j++){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=0;k<numOfCubeX;k++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI){
                for(var j=0;j<numOfCubeY;j++){
                    for(var k=0;k<numOfCubeX;k++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }

            if(GameGrid.angleForXRotation<Math.PI/4){
                for(var j=0;j<numOfCubeY;j++){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=0;k<numOfCubeX;k++){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY){
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI) {
                for (var j = 0; j < numOfCubeY; j++) {
                    for (var k = 0; k < numOfCubeX; k++) {
                        if (cubePositions[k][j][i] && cubes[k][j][i].newPosY - cubes[k][j][i].newHeight < GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
    }

    public void draw(Graphics g) {
        //if (xPosition == 0 && yPosition == 1) return;
        //if(Player.yPosition-Player.cubeAway<(yPosition)*numOfCubeY)System.out.println(yPosition);
        //if(Player.yPosition-Player.cubeAway>(yPosition+1)*numOfCubeY)System.out.println(yPosition);
        ///  System.out.println(Cube.angleForXRotation/Math.PI);


        for (var i = 0; i < numOfCubeZ; i++) {
            if(GameGrid.angleForXRotation<Math.PI/4){
                for(var j=0;j<numOfCubeY;j++){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=0;k<numOfCubeX;k++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI){
                for(var j=0;j<numOfCubeY;j++){
                    for(var k=0;k<numOfCubeX;k++){
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
        }
        for (var i = numOfCubeZ-1; i >=0 ; i--) {

            if(GameGrid.angleForXRotation<Math.PI/4){
                for(var j=0;j<numOfCubeY;j++){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k=numOfCubeX-1;k>=0;k--){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }

                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=numOfCubeX-1;k>=0;k--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j=numOfCubeY-1;j>=0;j--){
                    for(var k=0;k<numOfCubeX;k++){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY){
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=numOfCubeY-1;j>=0;j--){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k=0;k<numOfCubeX;k++){
                    for(var j=0;j<numOfCubeY;j++){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI){
                for(var j=0;j<numOfCubeY;j++){
                    for(var k=0;k<numOfCubeX;k++){
                        if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                            cubes[k][j][i].draw(g);
                        }
                    }
                }
            }
        }


    }
    public void draw(Graphics g,double PasTouche2) {
        //if (xPosition == 0 && yPosition == 1) return;
        //if(Player.yPosition-Player.cubeAway<(yPosition)*numOfCubeY)System.out.println(yPosition);
        //if(Player.yPosition-Player.cubeAway>(yPosition+1)*numOfCubeY)System.out.println(yPosition);
        int[] newChunkIn=new int[2];
        double[] cameraPos=new double[2];

        newChunkIn[0]=(int)((Player.xPosition-Math.sin(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5)/Chunk.numOfCubeX);
        newChunkIn[1]=(int)((Player.yPosition+Math.cos(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5)/Chunk.numOfCubeY);
        if(Player.xPosition-Player.cubeAway<0)newChunkIn[0]=(int)((Player.xPosition-Math.sin(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5)/Chunk.numOfCubeX-1);
        if(Player.yPosition-Player.cubeAway<0)newChunkIn[1]=(int)((Player.yPosition+Math.cos(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5)/Chunk.numOfCubeY-1);

        cameraPos[0]=(Player.xPosition-Math.sin(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5);
        cameraPos[1]=(Player.yPosition+Math.cos(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5);
        if(Player.xPosition-Player.cubeAway<0)cameraPos[0]=(Player.xPosition-Math.sin(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5)-1;
        if(Player.yPosition-Player.cubeAway<0)cameraPos[1]=(Player.yPosition+Math.cos(GameGrid.angleForXRotation)*(Player.cubeAway+0.5)+0.5)-1;

       // cameraPos[0]=(Player.xPosition);
        //cameraPos[1]=(Player.yPosition);


        // newChunkIn[0]=(int)((Player.xPosition+0.5)/Chunk.numOfCubeX);
          /*
                           for (var k = (int) (cameraPos[0] - newChunkIn[0] * numOfCubeX); k < numOfCubeX; k++) {
                               if (cubePositions[k][j][i]) {
                                   cubes[k][j][i].draw(g);
                               }
                           }
                           for (var k = (int) (cameraPos[0] - newChunkIn[0] * numOfCubeX); k >= 0; k--) {
                               if (cubePositions[k][j][i]) {
                                   cubes[k][j][i].draw(g);
                               }
                           }
                           for (var k = 0; k < cameraPos[0] - newChunkIn[0] * numOfCubeX; k++) {
                               if (cubePositions[k][j][i]) {
                                   cubes[k][j][i].draw(g);
                               }
                           }
                           for (var k = numOfCubeX - 1; k >= cameraPos[0] - newChunkIn[0] * numOfCubeX; k--) {
                               if (cubePositions[k][j][i]) {
                                   cubes[k][j][i].draw(g);
                               }
                           }
                           */


        for (var i = 0; i < numOfCubeZ; i++) {

           if(yPosition<newChunkIn[1]){
               for(var j=0;j<numOfCubeY;j++){
                   if(xPosition<newChunkIn[0]){
                       for(var k=0;k<numOfCubeX;k++){
                           if (cubePositions[k][j][i]) {
                               cubes[k][j][i].draw(g);
                           }
                       }
                   }
                   if(xPosition>newChunkIn[0]){
                       for(var k=numOfCubeX-1;k>=0;k--){
                           if (cubePositions[k][j][i]) {
                               cubes[k][j][i].draw(g);
                           }
                       }
                   }

                   if(xPosition==newChunkIn[0]){
                       if(GameGrid.angleForXRotation<=Math.PI) {
                           for (var k = numOfCubeX-1; k >=0; k--) {
                               if (cubePositions[k][j][i]) {
                                   cubes[k][j][i].draw(g);
                               }
                           }
                       }
                       else if(GameGrid.angleForXRotation<2*Math.PI) {
                           for (var k = 0; k < numOfCubeX; k++) {
                               if (cubePositions[k][j][i]) {
                                   cubes[k][j][i].draw(g);
                               }
                           }

                       }
                   }



               }
           }
           if(yPosition>newChunkIn[1]){
                for(var j=numOfCubeY-1;j>=0;j--){
                    if(xPosition<newChunkIn[0]){
                        for(var k=0;k<numOfCubeX;k++){
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                    }
                    if(xPosition>newChunkIn[0]){
                        for(var k=numOfCubeX-1;k>=0;k--){
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                    }
                    if(xPosition==newChunkIn[0]){
                        if(GameGrid.angleForXRotation<=Math.PI) {
                            for (var k = numOfCubeX-1; k >=0; k--) {
                                if (cubePositions[k][j][i]) {
                                    cubes[k][j][i].draw(g);
                                }
                            }
                        }
                        else if(GameGrid.angleForXRotation<2*Math.PI) {
                            for (var k = 0; k < numOfCubeX; k++) {
                                if (cubePositions[k][j][i]) {
                                    cubes[k][j][i].draw(g);
                                }
                            }

                        }
                    }
                }
            }


           /* if (Player.yPosition + 0.5 > (yPosition) * numOfCubeY) {
                if (Player.xPosition > (xPosition + 1) * numOfCubeX) {
                    for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = 0; k < numOfCubeX; k++) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }

                        }

                    }

                } else if (Player.xPosition < (xPosition) * numOfCubeX) {
                    for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = numOfCubeX - 1; k >= 0; k--) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }

                        }

                    }

                } else {
                    for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = 0; k < Player.xPosition - (xPosition) * numOfCubeX; k++) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                        for (var k = numOfCubeX - 1; k >= Player.xPosition - (xPosition) * numOfCubeX; k--) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                    }
                }
            }
            else if (Player.yPosition + 0.5 < (yPosition) * numOfCubeY) {
                if (Player.xPosition > (xPosition + 1) * numOfCubeX) {
                    for (var j = numOfCubeY - 1; j >= 0; j--) {
                        for (var k = 0; k < numOfCubeX; k++) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }

                        }

                    }

                } else if (Player.xPosition < (xPosition) * numOfCubeX) {
                    for (var j = numOfCubeY - 1; j >= 0; j--) {
                        for (var k = numOfCubeX - 1; k >= 0; k--) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }

                        }

                    }

                } else {
                    for (var j = numOfCubeY - 1; j >= 0; j--) {
                        for (var k = 0; k < Player.xPosition - (xPosition) * numOfCubeX; k++) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                        for (var k = numOfCubeX - 1; k >= Player.xPosition - (xPosition) * numOfCubeX; k--) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                    }
                }
            }
            else {
                for (var j = 0; j < Player.yPosition + 0.5 - (yPosition) * numOfCubeY; j++) {
                    for (var k = 0; k < numOfCubeX; k++) {
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }


                }
                for (var j = numOfCubeY - 1; j >= Player.yPosition + 0.5 - (yPosition) * numOfCubeY; j--) {
                    for (var k = numOfCubeX - 1; k >= 0; k--) {
                        if (cubePositions[k][j][i]) {
                            cubes[k][j][i].draw(g);
                        }

                    }


                }


            }

             */

        }

/*

        for (var i= numOfCubeZ-1; i >-1; i--) {
            for (var j = 0; j < numOfCubeY; j++) {
                for (var k = 0; k < numOfCubeX; k++) {
                    if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                        cubes[k][j][i].draw(g);
                    }
                }
            }
        }

 */
}
    public void draw(Graphics g,int PAsTouche){


       // if(Player.yPosition-Player.cubeAway<(yPosition)*numOfCubeY)System.out.println(yPosition);

        for (var i= 0; i < numOfCubeZ; i++) {
                for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = 0; k < numOfCubeX; k++) {
                            if (cubePositions[k][j][i]) {
                                cubes[k][j][i].draw(g);
                                if(Player.chunkIn[0]==xPosition&&Player.chunkIn[1]==yPosition)
                                /*if((Player.yPosition+1-(1-Player.depth/Cube.defaultSize)/2.0-(cubes[k][j][i].yPosition))>0){
                                    Player.draw(g);
                                }*/
                                if((Player.yPosition+1-(1-Player.depth/Cube.defaultSize)/2.0-(cubes[k][j][i].yPosition))>0){
                                    Player.draw(g);
                                }
                            }

                            /*
                            if(Player.chunkIn[0]==xPosition&&Player.chunkIn[1]==yPosition) {
                                if ((Player.yPosition + 1 - (1 - Player.depth / Cube.defaultSize) / 2.0 - (yPosition*numOfCubeY)) > 0) {
                                    Player.draw(g);
                                }
                            }

                             */
                        }
                }
        }

        for (var i= numOfCubeZ-1; i >-1; i--) {
             for (var j = 0; j < numOfCubeY; j++) {
                        for (var k = 0; k < numOfCubeX; k++) {
                            if (cubePositions[k][j][i]&&cubes[k][j][i].newPosY-cubes[k][j][i].newHeight<GameGrid.PFY) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                }
        }
    }
}
