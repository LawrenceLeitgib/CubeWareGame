public class Chunk {
    static final int numOfCubeX = 16;
    static final int numOfCubeY = 16;
    static final int numOfCubeZ = 200;
    final int xPosition;
    final int yPosition;
    Cube[][][] cubes = new Cube[numOfCubeX][numOfCubeY][numOfCubeZ];
    boolean[][][] cubePositions = new boolean[numOfCubeX][numOfCubeY][numOfCubeZ];
    boolean[] zLayer =new boolean[numOfCubeZ];
    Chunk(int positionX, int positionY) {
        this.xPosition = positionX;
        this.yPosition = positionY;
        createCubes();
    }
    public void newCube(int x, int y, int z) {
        cubes[x][y][z] = new Cube();
        cubePositions[x][y][z] = true;
        zLayer[z]=true;
        CubeContainer.bigZLayer[z]=true;
    }
    public void createCubes() {
        for (var i = 0; i < numOfCubeX; i++) {
            for (var j = 0; j < numOfCubeX; j++) {
                newCube(i, j, 0);
               newCube(i, j, 1);
            }
        }
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
}
