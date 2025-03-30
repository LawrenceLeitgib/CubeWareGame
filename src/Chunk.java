public class Chunk {
    static final byte numOfCubeX = 16;
    static final byte numOfCubeY = 16;
    static final short numOfCubeZ = 200;
    final Cube[][][] cubes = new Cube[numOfCubeX][numOfCubeY][numOfCubeZ];
    final boolean[][][] cubePositions = new boolean[numOfCubeX][numOfCubeY][numOfCubeZ];
    final boolean[] zLayer =new boolean[numOfCubeZ];
    Chunk() {
        createCubes();
    }
    private void newCube(int x, int y, int z) {
        cubes[x][y][z] = new Cube();
        cubePositions[x][y][z] = true;
        zLayer[z]=true;
        CubeContainer.bigZLayer[z]=true;
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
