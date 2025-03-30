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

    boolean[] zLayer =new boolean[numOfCubeZ];



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
        cubes[x][y][z] = new Cube(x + xPosition * numOfCubeX, y + yPosition * numOfCubeY, z, this);
        cubePositions[x][y][z] = true;
        zLayer[z]=true;
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
                if((xPosition+yPosition)%2==0){
                    //for(var k=0;k<3;k++) newCube(i, j, 7+k);

                }



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
/*

        if (xPosition == 0 && yPosition == 0) {
            for(var i=0;i<15;i++){
                newCube(0, 0, 2+i);
                newCube(1, 0, 2+i);
                newCube(1, 1, 2+i);
                newCube(0, 1, 2+i);
            }
        }

 */

        if (xPosition == 0 && yPosition == 0) {
           // cubes[3][3][0]=null;
            //cubes[3][3][1]=null;
            //cubePositions[3][3][0]=false;
            //cubePositions[3][3][1 ]=false;





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
      //  drawLabyrinth();
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
}
