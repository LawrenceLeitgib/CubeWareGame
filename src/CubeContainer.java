import java.awt.*;

public class CubeContainer {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    double PFX;
    double PFY;
    static int numberOfCubes=1000;
    Cube[][] cubes=new Cube[2*numberOfCubes+1][2*numberOfCubes+1];
    static boolean[] cubePositionX =new boolean[2*numberOfCubes+1];
    static boolean[] cubePositionY =new boolean[2*numberOfCubes+1];

    static boolean[][] cubePosition=new boolean[2*numberOfCubes+1][2*numberOfCubes+1];
    static double depthRatio;

    CubeContainer(double depthRatio){
        CubeContainer.depthRatio=depthRatio;
        createCubes();

    }
    public void newCube(int x,int y,int z){
        cubes[numberOfCubes+x][numberOfCubes+y]=new Cube(x,y,z, depthRatio);
        cubePositionX[numberOfCubes+x]=true;
        cubePositionY[numberOfCubes+y]=true;
        cubePosition[numberOfCubes+x][numberOfCubes+y]=true;

    }
    public void createCubes(){
        /*

        for(var i=0;i<10;i++){
            for(var j=0;j<30;j++){
                newCube(i-5,-j,0);

            }
        }
        */
        /*
        for(var i=0;i<30;i++) {
            newCube(-1, -i, 0);
            newCube(1, -i, 0);
        }

         */

        /*
        newCube(0,0,0);
        newCube(0,-1,0);
        newCube(0,5,0);
        newCube(-1,0,0);
        newCube(1,0,0);
        */
        newCube(0,0,0);




    }
    public void updateData(double deltaTime,double playerPosX,double playerPosy,double playerPosZ){
        for(var i=0;i<2*numberOfCubes+1;i++){
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePosition[i][j]){
                        cubes[i][j].updateData(deltaTime);
                    }
                }
            }
        }


    public void draw(Graphics g){
/*
        for(var i=0;i<2*numberOfCubes+1;i++){
            if (cubePositionX[i]){
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePositionY[j]){
                        cubes[i][j].draw(g);
                    }
                }
            }
        }*/

        for(var i=0;i<2*numberOfCubes+1;i++){
            for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                if (cubePosition[j][i]){
                    cubes[j][i].draw(g);
                }
            }
        }

    }

    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }

    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }
}
