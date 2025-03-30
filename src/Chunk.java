public class Chunk {
    static int numberOfCube;
    static final byte numOfCubeX = 16;
    static final byte numOfCubeY = 16;
    static final short numOfCubeZ = 200;
    final private Cube[][][] cubes = new Cube[numOfCubeX][numOfCubeY][numOfCubeZ];
    final boolean[][][] cubePositions = new boolean[numOfCubeX][numOfCubeY][numOfCubeZ];
    final boolean[] zLayer =new boolean[numOfCubeZ];
    Chunk() {
        createCubes();
    }

    public Cube[][][] getCubes() {
        return cubes;
    }

    private void newCube(int x, int y, int z) {
        cubes[x][y][z] = new Cube();
        cubePositions[x][y][z] = true;
        zLayer[z]=true;
        GameGrid.bigZLayer[z]=true;
        numberOfCube++;
    }
    private void createCubes() {
        for (var i = 0; i < numOfCubeX; i++) {
            for (var j = 0; j < numOfCubeX; j++) {
                newCube(i, j, 0);
               newCube(i, j, 1);
            }
        }
    }
}
