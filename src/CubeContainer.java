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

    static boolean hasBeenDrawnFrontPlayer=false;
    static boolean hasBeenDrawnBelowGrid=false;
    boolean drawGrid=true;

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
    public void drawDick(int x, int y,int z){
        newCube(x,y,z+6);
        newCube(x,y,z+5);
        newCube(x,y,z+4);
        newCube(x,y,z+3);
        newCube(x,y,z+2);
        newCube(x,y,z+1);
        newCube(x,y,z);
        newCube(x-1,y,z);
        newCube(x+1,y,z);
        newCube(x-1,y,z+1);
        newCube(x+1,y,z+1);

        newCube(x,y-1,z+6);
        newCube(x,y-1,z+5);
        newCube(x,y-1,z+4);
        newCube(x,y-1,z+3);
        newCube(x,y-1,z+2);
        newCube(x,y-1,z+1);
        newCube(x,y-1,z);
        newCube(x-1,y-1,z);
        newCube(x+1,y-1,z);
        newCube(x-1,y-1,z+1);
        newCube(x+1,y-1,z+1);

    }
    public void drawHeart(int x, int y,int z){

        newCube(x,y,z);

        newCube(x-1,y,z+1);
        newCube(x,y,z+1);
        newCube(x+1,y,z+1);

        newCube(x-2,y,z+2);
        newCube(x-1,y,z+2);
        newCube(x,y,z+2);
        newCube(x+1,y,z+2);
        newCube(x+2,y,z+2);

        newCube(x-3,y,z+3);
        newCube(x-2,y,z+3);
        newCube(x-1,y,z+3);
        newCube(x,y,z+3);
        newCube(x+1,y,z+3);
        newCube(x+2,y,z+3);
        newCube(x+3,y,z+3);

        newCube(x-3,y,z+4);
        newCube(x-2,y,z+4);
        newCube(x-1,y,z+4);
        newCube(x+1,y,z+4);
        newCube(x+2,y,z+4);
        newCube(x+3,y,z+4);

        newCube(x-2,y,z+5);
        newCube(x+2,y,z+5);


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
    public void createCubes(){
        drawMap(0,0,0);
        /*
        newCube(-10,0,-1);

        newCube(-10,0,-2);
        newCube(-10,0,-3);
        newCube(-10,0,-4);

        newCube(-5,0,0);

        newCube(-5,0,1);
        newCube(-5,0,2);
        newCube(-4,0,2);
        newCube(-6,0,2);
        newCube(-5,0,3);
        */
        newCube(-5,0,4);
       // newCube(-5,0,5);
        //newCube(-5,0,6);


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
        //System.out.println(drawGrid);
        drawGrillage(g);//DO NOT DELET
        Player.draw(g);

        if(drawGrid){
            /*
            drawCubes(g ,0,numberOfCubesZ,0);
            drawGrillage(g);
            drawCubes(g ,numberOfCubesZ,2*numberOfCubesZ+1,1);
             */
            drawCubes(g ,0,2*numberOfCubesZ+1,0);


        }
        //System.out.println(drawGrid);

        if(!drawGrid){
            /*drawCubes(g ,numberOfCubesZ,2*numberOfCubesZ+1,3);
            drawGrillage(g);
            drawCubes(g ,0,numberOfCubesZ,4);
             */
            drawCubes(g ,0,2*numberOfCubesZ+1,0);

        }




    }
    public void drawCubes(Graphics g,int beginning,int end,int num){

        //System.out.println( cubePosition[k][j][numberOfCubesZ-1-i]);
        //if((numberOfCubesZ-1-i)*Cube.height<Player.zPosition && cubePosition[k][j][numberOfCubesZ-1-i])System.out.println("test");
        //cubes[k][j][numberOfCubesZ-1-i].draw(g);
        //System.out.println(num);

        hasBeenDrawnBelowGrid=false;
        for (var i= beginning; i < end; i++) {
            if(i==numberOfCubesZ){
                hasBeenDrawnBelowGrid=true;
                if(drawGrid)drawGrillage(g);
            }
            if (cubePositionZ[i])
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePositionY[j])
                        for (var k = 0; k < 2 * numberOfCubes + 1; k++) {
                            if (cubePosition[k][j][i]) {
                                cubes[k][j][i].draw(g);
                                if(Player.yPosition-(cubes[k][j][i].yPosition)*cubes[k][j][i].depth>=0){
                                    hasBeenDrawnFrontPlayer=true;
                                    Player.draw(g);
                                }

                            }
                        }
                }
        }

        for (var i= end-1; i > beginning-1; i--) {
            if(i+1==numberOfCubesZ){
                hasBeenDrawnBelowGrid=true;
                if(!drawGrid)drawGrillage(g);
            }

            if (cubePositionZ[i])
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePositionY[j])
                        for (var k = 0; k < 2 * numberOfCubes + 1; k++) {
                            if (cubePosition[k][j][i]&&(i-numberOfCubesZ+1)*Cube.defaultSize-GameGrid.PFY>Player.zPosition) {
                                cubes[k][j][i].draw(g);
                            }
                        }
                }
        }

        /*

        for (var i= beginning; i < end; i++) {
            if (cubePositionZ[i])
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePositionY[j])
                        for (var k = 0; k < 2 * numberOfCubes + 1; k++) {
                            if (cubePosition[k][j][i]) {
                                //cubes[k][j][i].draw(g);
                                if((Player.yPosition-(cubes[k][j][i].yPosition)*cubes[k][j][i].depth>=0)){
                                    cubes[k][j][i].draw(g);
                                }
                                if(i+1==numberOfCubesZ)drawGrillage(g);

                            }
                        }
                }
        }
        if(num==0){
            Player.draw(g);
        }
        for (var i= beginning; i < end; i++) {
                if(cubePositionZ[i]);
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePositionY[j])
                        for (var k = 0; k < 2 * numberOfCubes + 1; k++) {
                            if (cubePosition[k][j][i]) {
                                if((Player.yPosition-(cubes[k][j][i].yPosition)*cubes[k][j][i].depth<0)){
                                    cubes[k][j][i].draw(g);
                                }
                                if(i+1==numberOfCubesZ)drawGrillage(g);

                            }
                        }

                }
        }

         */
        //cubes[CubeContainer.numberOfCubes][CubeContainer.numberOfCubes][CubeContainer.numberOfCubesZ-1].draw(g);

/*
        for (var i= end-1; i > beginning-1; i--) {
            if (cubePositionZ[i])
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePositionY[j])
                        for (var k = 0; k < 2 * numberOfCubes + 1; k++) {

                            if (cubePosition[k][j][i]&&(i-numberOfCubesZ+1)*Cube.defaultSize-GameGrid.PFY>Player.zPosition) {
                                if((Player.yPosition-(cubes[k][j][i].yPosition)*cubes[k][j][i].depth>=0)){
                                    cubes[k][j][i].draw(g);
                                }

                            }
                        }
                }
        }
        if(num==3||num==4){
            Player.draw(g);
        }
        for (var i= end-1; i > beginning-1; i--) {
            if (cubePositionZ[i])
                for (var j = 0; j < 2 * numberOfCubes + 1; j++) {
                    if (cubePositionY[j])
                        for (var k = 0; k < 2 * numberOfCubes + 1; k++) {

                            if (cubePosition[k][j][i]&&(i-numberOfCubesZ+1)*Cube.defaultSize-GameGrid.PFY>Player.zPosition) {
                                if((Player.yPosition-(cubes[k][j][i].yPosition)*cubes[k][j][i].depth<0)){
                                    cubes[k][j][i].draw(g);
                                }
                            }
                        }
                }
        }

 */




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
        int sizeOfBlocks=Cube.defaultSize;
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


        g.setColor(new Color(119, 133, 17, 50));


        for(var i=-numOfBlocksXaxis+1;i<=numOfBlocksXaxis;i++ ){
            g.drawLine(GAME_WIDTH/2+sizeOfBlocks/2+i*sizeOfBlocks-xGrillNumToAdd, (int) (GAME_HEIGHT+Player.zPosition), (int) GameGrid.PFX, (int) GameGrid.PFY);
        }
        //double sum=0;
        for(var i=0;i<numOfBlocksYaxis;i++ ){
            //Player.zPosition
            //double sizeRatio=GAME_HEIGHT/((sizeOfBlocks*(i-1)+yGrillNumToAdd)*1.0*depthRatio+GAME_HEIGHT);
            //newPosY=(2*GAME_HEIGHT/3.0*sizeRatio+GAME_HEIGHT/3.0+difPosZ*sizeRatio);
            //yValue=  (2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((sizeOfBlocks*(i-10.0)+yGrillNumToAdd)*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0);
            yValue=  ((GameGrid.PVY-GameGrid.PFY+Player.zPosition)*((GAME_HEIGHT)/((sizeOfBlocks*(i-1)+yGrillNumToAdd)*depthRatio+GAME_HEIGHT))+GameGrid.PFY);
            //if ((int)yValue<GAME_HEIGHT/3)yValue=GAME_HEIGHT/3;
            //g.drawLine(0,(yValue),  GAME_WIDTH,  ( yValue));

            if (yValue<GameGrid.PFY){drawGrid=false;}
            else{drawGrid=true;}

            g.fillRect(0, (int)(yValue), GAME_WIDTH , 2);

        }

    }
}
