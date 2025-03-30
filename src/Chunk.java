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



        for (var i = 0; i < numOfCubeX; i++) {
            for (var j = 0; j < numOfCubeX; j++) {
                newCube(i, j, 0);
               newCube(i, j, 1);
                if((xPosition+yPosition)%2==0){
                    //for(var k=0;k<3;k++) newCube(i, j, 7+k);

                }



            }
        }


        if (xPosition == 0 && yPosition == 0) {
        }
    }
}
