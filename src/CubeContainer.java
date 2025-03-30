import java.awt.*;

public class CubeContainer {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static int numberOfCubes=100;
    static int numberOfCubesZ=100;
    Cube[][][] cubes=new Cube[2*numberOfCubes+1][2*numberOfCubes+1][2*numberOfCubesZ+1];

    static boolean[][][] cubePosition=new boolean[2*numberOfCubes+1][2*numberOfCubes+1][2*numberOfCubesZ+1];
    static boolean[] cubePositionX=new boolean[2*numberOfCubes+1];
    static boolean[] cubePositionY=new boolean[2*numberOfCubes+1];
    static boolean[] cubePositionZ=new boolean[2*numberOfCubesZ+1];
    static double depthRatio;

    CubeContainer(double depthRatio){
        CubeContainer.depthRatio=depthRatio;
        createCubes();

    }
    public void newCube(int x,int y,int z){
        cubes[numberOfCubes+x][numberOfCubes+y][numberOfCubesZ+z]=new Cube(x,y,z, depthRatio);
        cubePosition[numberOfCubes+x][numberOfCubes+y][numberOfCubesZ+z]=true;
        cubePositionX[numberOfCubes+x]=true;
        cubePositionY[numberOfCubes+y]=true;
        cubePositionZ[numberOfCubesZ+z]=true;



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
        //newCube(0,0,0);
        //newCube(0,0,1);
        /*

        newCube(0,0,0);
        newCube(0,-10,0);
        newCube(0,0,-1);
        newCube(0,-1,-1);
        newCube(0,-2,-1);
        newCube(0,0,-2);
        newCube(0,0,-3);
        newCube(0,0,-4);

         */

        /*
        newCube(0,0,0);
       newCube(0,0,1);
       newCube(0,0,2);
       newCube(0,0,3);


        newCube(0,0,4);
        newCube(0,0,0);
        newCube(0,0,6);

         */
        newCube(40,0,3);
        newCube(40,0,2);
        newCube(40,0,1);
        newCube(40,0,0);
        newCube(40,0,-1);
        newCube(40,0,-2);
        newCube(40,0,-3);
        newCube(39,0,-3);
        newCube(41,0,-3);
        newCube(39,0,-2);
        newCube(41,0,-2);

        newCube(40,-1,3);
        newCube(40,-1,2);
        newCube(40,-1,1);
        newCube(40,-1,0);
        newCube(40,-1,-1);
        newCube(40,-1,-2);
        newCube(40,-1,-3);
        newCube(39,-1,-3);
        newCube(41,-1,-3);
        newCube(39,-1,-2);
        newCube(41,-1,-2);




        for(var i=0;i<10;i++){
            for(var j=0;j<10;j++){
                for(var k=-10;k<10;k++){
                newCube(i-5,-j,k);
                }
         }
        }





        //newCube(0,0,2);

        //newCube(0,-1,0);




    }
    public void updateData(double deltaTime,double playerPosX,double playerPosy,double playerPosZ){
        for(var i=0;i<2*numberOfCubes+1;i++){
            if(cubePositionX[i])
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if(cubePositionY[j])
                    for (var k = 0; k < 2 * numberOfCubesZ + 1; k++) {
                        if (cubePosition[i][j][k]) {
                            cubes[i][j][k].updateData(deltaTime);
                        }
                    }
                }
            }
        }


    public void draw(Graphics g){

        g.setColor(new Color(147, 196, 49));
        g.fillRect(0, (int) GameGrid.PFY,GAME_WIDTH,2*GAME_HEIGHT/3);


        /*
        for(var i=0;i<2*numberOfCubes+1;i++){
            if(cubePositionY[i])
            for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                if(cubePositionX[j])
                for (var k = 0; k <  numberOfCubesZ ; k++) {
                    if (cubePosition[j][i][k]){
                        cubes[j][i][k].draw(g);
                    }
                }
            }
        }
        drawGrillage(g);
        for(var i=0;i<2*numberOfCubes+1;i++){
            if(cubePositionY[i])
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if(cubePositionX[j])
                        for (var k = numberOfCubesZ; k < 2 * numberOfCubesZ + 1; k++) {
                            if (cubePosition[j][i][k]){
                                cubes[j][i][k].draw(g);
                            }
                        }
                }
        }
        */
        for (var i = 0; i <  numberOfCubesZ ; i++) {
            if(cubePositionZ[i])
            for(var j=0;j<2*numberOfCubes+1;j++){
                if(cubePositionY[j])
                for (var k = 0; k < 2 * numberOfCubes + 1; k++) {
                    if (cubePosition[k][j][i]){
                        cubes[k][j][i].draw(g);
                    }
                }
            }
        }
        drawGrillage(g);

        for (var i = numberOfCubesZ; i < 2 * numberOfCubesZ + 1; i++) {
            if(cubePositionZ[i])
                for(var j=0;j<2*numberOfCubes+1;j++){
                    if(cubePositionY[j])
                        for (var k = 0; k < 2 * numberOfCubes + 1; k++) {
                            if (cubePosition[k][j][i]){
                                cubes[k][j][i].draw(g);
                            }
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
    public void drawGrillage(Graphics g ){

        int numOfBlocksXaxis=1000;
        int numOfBlocksYaxis=1000;
        int sizeOfBlocks=Cube.width;
        int xGrillNumToAdd= (int) Player.xPosition;
        int yGrillNumToAdd= (int) Player.yPosition;
        double yValue;

        while(xGrillNumToAdd>sizeOfBlocks){
            xGrillNumToAdd-=sizeOfBlocks;
        }

        while(xGrillNumToAdd<sizeOfBlocks){
            xGrillNumToAdd+=sizeOfBlocks;

        }

        while(yGrillNumToAdd>sizeOfBlocks){
            yGrillNumToAdd-=sizeOfBlocks;

        }
        while(yGrillNumToAdd<sizeOfBlocks){
            yGrillNumToAdd+=sizeOfBlocks;

        }


        g.setColor(new Color(119, 133, 17));

        for(var i=-numOfBlocksXaxis+1;i<=numOfBlocksXaxis;i++ ){
            g.drawLine(GAME_WIDTH/2+sizeOfBlocks/2+i*sizeOfBlocks-xGrillNumToAdd,GAME_HEIGHT, (int) GameGrid.PFX, (int) GameGrid.PFY);
        }
        //double sum=0;
        for(var i=0;i<numOfBlocksYaxis;i++ ){

            yValue=  (2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((sizeOfBlocks*(i-10.0)+yGrillNumToAdd)*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0);
            if ((int)yValue<GAME_HEIGHT/3)yValue=GAME_HEIGHT/3;
            //g.drawLine(0,(yValue),  GAME_WIDTH,  ( yValue));
            g.fillRect(0, (int)(yValue), GAME_WIDTH , 2);

        }

    }
}
