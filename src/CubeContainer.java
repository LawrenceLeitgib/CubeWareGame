import java.awt.*;

public class CubeContainer {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;



    static double depthRatio;

    boolean drawGrid=true;

    //Chunk chunk1=new Chunk(GAME_WIDTH,GAME_HEIGHT,0,0);

    static int numOfChunkX=1000;
    static int numOfChunkY=1000;

    double countChunkCreation=0;
    double timeChunkCreation=0.7;



    static Chunk[][] chunks=new Chunk[numOfChunkX*2][numOfChunkY*2];
    static boolean[][]chunksPosition=new boolean[numOfChunkX*2][numOfChunkY*2];

    CubeContainer(double depthRatio){
        CubeContainer.depthRatio=depthRatio;

    }
    public void newCube(int x,int y,int z){
        int xChunkNum=0;
        int yChunkNum=0;

        int newX=x;
        int newY=y;
        while(newX<0){
            newX+=Chunk.numOfCubeX;
            xChunkNum-=1;

        }
        while(newX>=Chunk.numOfCubeX){
            newX-=Chunk.numOfCubeX;
            xChunkNum+=1;

        }

        while(newY<0){
            newY+=Chunk.numOfCubeY;
            yChunkNum-=1;

        }
        while(newY>=Chunk.numOfCubeY){
            newY-=Chunk.numOfCubeY;
            yChunkNum+=1;

        }
        if(!chunksPosition[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum]||chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubePositions[newX][newY][z])return;
        chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubes[newX][newY][z]=new Cube(x,y,z,GameGrid.depthRatio,chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum]);
        chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubePositions[newX][newY][z]=true;
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
                newCube(i+x,j+y,z);
            }
        }
        for(var j=0;j<10;j++){
            newCube(-1+x,j+y,z+1);
            newCube(1+x,j+y,z+1);
        }
        for(var i=0;i<10;i++){
            for(var j=0;j<10;j++){
                if(j>=i)
                newCube(4+x,i+y,j-i+z+1);
            }
        }




    }
    public void drawDick(int x, int y,int z,int height,int ballSize){
        height=4*ballSize;
        for(var i=0;i<ballSize;i++){
            for(var j=0;j<ballSize;j++){
                for(var k=0;k<ballSize;k++){
                    newCube(i-ballSize+x,j+y,k+z);
                    newCube(i+ballSize+x,j+y,k+z);
                }
                for(var k=0;k<height;k++){
                    newCube(i+x,j+y,k+z);
                }

            }
        }



    }
    public void drawCircle(int x,int y,int z,int r){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<=r+1&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2))>=r-1){
                    newCube(x+i,y,z+k);
                }
            }
        }
    }
    public void drawCircle2(int x,int y,int z,int r){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<=r+1&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2))>=r-1){
                    newCube(x+i,y+k,z);
                }
            }
        }
    }
    public void fillircle(int x,int y,int z,int r){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r){
                    newCube(x+i,y,z+k);
                }
            }
        }

    }
    public void drawHeart2(int x, int y,int z,int size){
        for(int k=0;k<size;k++){
           for(int i=-k;i<k;i++){
               newCube(x+i,y,z+k);
           }
        }

    }
    public void drawBall(int x, int y, int z,int r){
        for(int i=-r;i<r;i++){
            for(int j=-r;j<r;j++){
                for(int k=-r;k<r;k++){
                    if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2)+Math.pow(j,2))<=r+1&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2)+Math.pow(j,2))>=r-1){
                        newCube(x+i,y+j,z+k);
                    }
                }
            }
        }

    }
    public void createCubes(){
       // drawHeart2(0,-10,4,20);
       // drawCircle(0,0,52+4,50);
        //fillircle(0,0,50+4,25);
        //drawBall(0,0,76,70);
       // drawBall(0,0,36,30);
        /*
        for(var i=0;i<20;i++){
            drawCircle(0,-5-i,20,i);

        }

         */

      //  drawBall(-25,0,25+4,25);
       // drawBall(25,0,25+4,25);

        for(int i=0;i<100;i++){
            //drawCircle2(0,0,25+i,25);
        }

       // int r2=20;
        /*
        for(var i=0;i<=r2;i++){
           {
            drawCircle(0,(r2-i),r2+10,Math.abs(i));
            drawCircle(0,(-r2+i),r2+10,Math.abs(i));
            }

        }

         */
       // drawDick(0,-20,5,20,20);
        //newCube(0,0,5);
        //newCube(1,0,5);
        /*
       for(var i=-20;i<20;i++){
           for(var j=-20;j<20;j++){
               newCube(i,j,5);

           }

       }

         */

     //   drawMap(0,0,4);
        /*
        drawMap(0,0,4);

        drawMap(0,-20,4);

        drawMap(0,-40,4);

        for(var k=0;k<100;k++){
            for(var i=k;i<100-k;i++){
                for(var j=k;j<100-k;j++){
                    newCube(i,j,k);

                }

            }
        }

         */









    }
    public void newChunk(int x,int y){
        chunks[numOfChunkX+x][numOfChunkY+y]=new Chunk(GAME_WIDTH,GAME_HEIGHT,x,y);
        chunksPosition[numOfChunkX+x][numOfChunkY+y]=true;

    }
    public void CreateNewChunks(){
        //System.out.println("test1");
        for(int i=Player.chunkIn[0]-Player.numOfChunkToDraw;i<=Player.chunkIn[0]+Player.numOfChunkToDraw;i++){
            for(int j=Player.chunkIn[1]-Player.numOfChunkToDraw;j<=Player.chunkIn[1]+Player.numOfChunkToDraw;j++){
                if(!chunksPosition[numOfChunkX+i][numOfChunkY+j])
                //System.out.println("test");
                newChunk(i,j);
            }
        }

    }
    public void updateData(double deltaTime){

        countChunkCreation+=deltaTime;
        if(countChunkCreation>=timeChunkCreation){
            CreateNewChunks();
            createCubes();
            countChunkCreation-=timeChunkCreation;
        }

        int xNum=0;
        for(var i=Player.cameraChunkIn[1]-Player.numOfChunkToDraw;i<=Player.cameraChunkIn[1]+Player.numOfChunkToDraw;i++){
            for(var j=0;j<=Player.numOfChunkToDraw*2;j++){
                if(j%2==0)xNum=-Player.numOfChunkToDraw+j/2+Player.cameraChunkIn[0];
                if(j%2==1)xNum=Player.numOfChunkToDraw-j/2+Player.cameraChunkIn[0];
                {
                    if(chunksPosition[numOfChunkX+xNum][i+numOfChunkY])
                        chunks[numOfChunkX+xNum][i+numOfChunkY].updateData(deltaTime);
                }

            }
        }

        }
    public void draw(Graphics g) {
        g.setColor(new Color(147, 196, 49));
        g.fillRect(0, (int) GameGrid.PFY, GAME_WIDTH, GAME_HEIGHT);
        int[] newChunkIn=new int[2];
        /*
        newChunkIn[0]=(int)((Player.xPosition-Math.sin(Cube.angleForXRotation)*(10+0.5)+0.5)/Chunk.numOfCubeX);
        newChunkIn[1]=(int)((Player.yPosition+Math.cos(Cube.angleForXRotation)*(10+0.5)+0.5)/Chunk.numOfCubeY);
        if(Player.xPosition-10<0)newChunkIn[0]=(int)((Player.xPosition-Math.sin(Cube.angleForXRotation)*(10)+0.5)/Chunk.numOfCubeX-1);
        if(Player.yPosition-10<0)newChunkIn[1]=(int)((Player.yPosition+Math.cos(Cube.angleForXRotation)*(10+0.5)+0.5)/Chunk.numOfCubeY-1);

         */
        newChunkIn[0]=Player.cameraChunkIn[0];
        newChunkIn[1]=Player.cameraChunkIn[1];
        if(GameGrid.angleForXRotation<Math.PI/4){
            for(var j=newChunkIn[1]-Player.numOfChunkToDraw;j<=newChunkIn[1]+Player.numOfChunkToDraw;j++){
                for(var k=newChunkIn[0]+Player.numOfChunkToDraw;k>=newChunkIn[0];k--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var k=newChunkIn[0]-Player.numOfChunkToDraw;k<=newChunkIn[0];k++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }

            }

        }
        else if(GameGrid.angleForXRotation<Math.PI/2){
            for(var k=newChunkIn[0]+Player.numOfChunkToDraw;k>=newChunkIn[0]-Player.numOfChunkToDraw;k--){
                for(var j=newChunkIn[1]+Player.numOfChunkToDraw;j>=newChunkIn[1];j--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var j=newChunkIn[1]-Player.numOfChunkToDraw;j<=newChunkIn[1];j++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }


            }
        }
        else if(GameGrid.angleForXRotation<3*Math.PI/4){
            for(var k=newChunkIn[0]+Player.numOfChunkToDraw;k>=newChunkIn[0]-Player.numOfChunkToDraw;k--){
                for(var j=newChunkIn[1]+Player.numOfChunkToDraw;j>=newChunkIn[1];j--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var j=newChunkIn[1]-Player.numOfChunkToDraw;j<=newChunkIn[1];j++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
            }
        }
        else if(GameGrid.angleForXRotation<Math.PI){
            for(var j=newChunkIn[1]+Player.numOfChunkToDraw;j>=newChunkIn[1]-Player.numOfChunkToDraw;j--){
                for(var k=newChunkIn[0]+Player.numOfChunkToDraw;k>=newChunkIn[0];k--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var k=newChunkIn[0]-Player.numOfChunkToDraw;k<=newChunkIn[0];k++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
            }
        }
        else if(GameGrid.angleForXRotation<5*Math.PI/4){
            for(var j=newChunkIn[1]+Player.numOfChunkToDraw;j>=newChunkIn[1]-Player.numOfChunkToDraw;j--){
                for(var k=newChunkIn[0]+Player.numOfChunkToDraw;k>=newChunkIn[0];k--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var k=newChunkIn[0]-Player.numOfChunkToDraw;k<=newChunkIn[0];k++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
            }
        }
        else if(GameGrid.angleForXRotation<3*Math.PI/2){
            for(var k=newChunkIn[0]-Player.numOfChunkToDraw;k<=newChunkIn[0]+Player.numOfChunkToDraw;k++){
                for(var j=newChunkIn[1]+Player.numOfChunkToDraw;j>=newChunkIn[1];j--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var j=newChunkIn[1]-Player.numOfChunkToDraw;j<=newChunkIn[1];j++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }


            }
        }
        else if(GameGrid.angleForXRotation<7*Math.PI/4){
            for(var k=newChunkIn[0]-Player.numOfChunkToDraw;k<=newChunkIn[0]+Player.numOfChunkToDraw;k++){
                for(var j=newChunkIn[1]+Player.numOfChunkToDraw;j>=newChunkIn[1];j--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var j=newChunkIn[1]-Player.numOfChunkToDraw;j<=newChunkIn[1];j++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }


            }
        }
        else if(GameGrid.angleForXRotation<2*Math.PI){
            for(var j=newChunkIn[1]-Player.numOfChunkToDraw;j<=newChunkIn[1]+Player.numOfChunkToDraw;j++){
                for(var k=newChunkIn[0]+Player.numOfChunkToDraw;k>=newChunkIn[0];k--){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }
                for(var k=newChunkIn[0]-Player.numOfChunkToDraw;k<=newChunkIn[0];k++){
                    if (chunksPosition[k+numOfChunkX][j+numOfChunkY]) {
                        chunks[k+numOfChunkX][j+numOfChunkY].draw(g);
                    }
                }

            }

        }


    }
    public void draw(Graphics g,int PasTOuche){

        g.setColor(new Color(147, 196, 49));
        g.fillRect(0, (int) GameGrid.PFY,GAME_WIDTH,GAME_HEIGHT);
        int[] newChunkIn=new int[2];
/*
       newChunkIn[0]=(int)((Player.xPosition-Math.sin(Cube.angleForXRotation)*(Player.initialCubeAway+0.5)+0.5)/Chunk.numOfCubeX);
       newChunkIn[1]=(int)((Player.yPosition+Math.cos(Cube.angleForXRotation)*(Player.initialCubeAway+0.5)+0.5)/Chunk.numOfCubeY);
        if(Player.xPosition-Player.initialCubeAway<0)newChunkIn[0]=(int)((Player.xPosition-Math.sin(Cube.angleForXRotation)*(Player.initialCubeAway+0.5)+0.5)/Chunk.numOfCubeX-1);
        if(Player.yPosition-Player.initialCubeAway<0)newChunkIn[1]=(int)((Player.yPosition+Math.cos(Cube.angleForXRotation)*(Player.initialCubeAway+0.5)+0.5)/Chunk.numOfCubeY-1);


 */
        newChunkIn[0]=Player.cameraChunkIn[0];
        newChunkIn[1]=Player.cameraChunkIn[1];
        // newChunkIn[0]=(int)((Player.xPosition+0.5)/Chunk.numOfCubeX);
       //  newChunkIn[1]=(int)((Player.yPosition+Player.cubeAway+2+0.5)/Chunk.numOfCubeY);
      //  newChunkIn[0]=Player.chunkIn[0];
        //newChunkIn[1]=Player.chunkIn[1];
        //System.out.println((int)((Player.xPosition+0.5)/Chunk.numOfCubeX)+" "+Player.chunkIn[0]+" ");

       // drawGrillage(g);
        int xNum=0;
        /*
        for(var i=Player.chunkIn[1]-Player.numOfChunkToDraw;i<=Player.chunkIn[1]+Player.numOfChunkToDraw;i++) {
            for (var j = 0; j <= Player.numOfChunkToDraw * 2; j++) {
                if (j % 2 == 0) xNum = -Player.numOfChunkToDraw + j / 2 + Player.chunkIn[0];
                if (j % 2 == 1) xNum = Player.numOfChunkToDraw - j / 2 + Player.chunkIn[0];
                {
                    if (chunksPosition[numOfChunkX + xNum][i + numOfChunkY])
                        chunks[numOfChunkX + xNum][i + numOfChunkY].draw(g);
                }

            }
        }*/
        for(var i=newChunkIn[1]-Player.numOfChunkToDraw;i< newChunkIn[1];i++) {
            for(var j= newChunkIn[0]-Player.numOfChunkToDraw;j< newChunkIn[0];j++) {
                if (chunksPosition[numOfChunkX + j][numOfChunkY+i]){
                    chunks[numOfChunkX + j][numOfChunkY+i].draw(g);
                }
            }
            for(var j= newChunkIn[0]+Player.numOfChunkToDraw;j> newChunkIn[0];j--) {
                if (chunksPosition[numOfChunkX + j][numOfChunkY+i]){
                    chunks[numOfChunkX + j][numOfChunkY+i].draw(g);
                }
            }
        }
        //System.out.println(Player.chunkIn[1]);
        for(var i= newChunkIn[1]+Player.numOfChunkToDraw;i> newChunkIn[1];i--) {
            for(var j= newChunkIn[0]-Player.numOfChunkToDraw;j< newChunkIn[0];j++) {
                if (chunksPosition[numOfChunkX + j][numOfChunkY+i]){
                    chunks[numOfChunkX + j][numOfChunkY+i].draw(g);
                }
            }
            for(var j= newChunkIn[0]+Player.numOfChunkToDraw;j> newChunkIn[0];j--) {
                if (chunksPosition[numOfChunkX + j][numOfChunkY+i]){
                    chunks[numOfChunkX + j][numOfChunkY+i].draw(g);
                }
            }

        }

        for(var i= newChunkIn[1]-Player.numOfChunkToDraw;i< newChunkIn[1];i++) {
            if (chunksPosition[numOfChunkX + newChunkIn[0]][numOfChunkY+i]){
                chunks[numOfChunkX +  newChunkIn[0]][numOfChunkY+i].draw(g);
            }

        }

        for(var i= newChunkIn[1]+Player.numOfChunkToDraw;i> newChunkIn[1];i--) {
            if (chunksPosition[numOfChunkX +newChunkIn[0]][numOfChunkY+i]){
                chunks[numOfChunkX +  newChunkIn[0]][numOfChunkY+i].draw(g);
            }
        }



        for(var i=newChunkIn[0]-Player.numOfChunkToDraw;i<newChunkIn[0];i++) {
            if (chunksPosition[numOfChunkX + i][numOfChunkY+newChunkIn[1]]){
                chunks[numOfChunkX +  i][numOfChunkY+newChunkIn[1]].draw(g);
            }

        }

        for(var i=newChunkIn[0]+Player.numOfChunkToDraw;i>newChunkIn[0];i--) {
            if (chunksPosition[numOfChunkX +i][numOfChunkY+newChunkIn[1]]){
                chunks[numOfChunkX +  i][numOfChunkY+newChunkIn[1]].draw(g);
            }
        }


        if (chunksPosition[numOfChunkX +newChunkIn[0]][numOfChunkY+newChunkIn[1]]){
            chunks[numOfChunkX + newChunkIn[0]][numOfChunkY+newChunkIn[1]].draw(g);
        }





       // System.out.println(newChunkIn[0]+" "+newChunkIn[1] );

        //Player.draw(g);

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
        double xGrillNumToAdd=  (Player.xPosition*Cube.defaultSize);
        double yGrillNumToAdd= ( Player.yPosition*Cube.defaultSize);
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
            g.drawLine((int) (GAME_WIDTH/2.0+sizeOfBlocks/2.0+i*sizeOfBlocks-xGrillNumToAdd), (int) (GAME_HEIGHT+Player.zPosition*Cube.defaultSize), (int) GameGrid.PFX, (int) GameGrid.PFY);
        }
        //double sum=0;
        for(var i=0;i<numOfBlocksYaxis;i++ ){
            //Player.zPosition
            //double sizeRatio=GAME_HEIGHT/((sizeOfBlocks*(i-1)+yGrillNumToAdd)*1.0*depthRatio+GAME_HEIGHT);
            //newPosY=(2*GAME_HEIGHT/3.0*sizeRatio+GAME_HEIGHT/3.0+difPosZ*sizeRatio);
            //yValue=  (2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((sizeOfBlocks*(i-10.0)+yGrillNumToAdd)*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0);
            yValue=  ((GameGrid.PVY-GameGrid.PFY+Player.zPosition*Cube.defaultSize)*((GAME_HEIGHT)/((sizeOfBlocks*(i-1)+yGrillNumToAdd)*depthRatio+GAME_HEIGHT))+GameGrid.PFY);
            //if ((int)yValue<GAME_HEIGHT/3)yValue=GAME_HEIGHT/3;
            //g.drawLine(0,(yValue),  GAME_WIDTH,  ( yValue));

            if (yValue<GameGrid.PFY){drawGrid=false;}
            else{drawGrid=true;}

            g.fillRect(0, (int)(yValue), GAME_WIDTH , 2);

        }

    }
}
