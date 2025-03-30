import java.awt.*;
public class CubeContainer {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static double depthRatio;
    static int numOfChunkX=1000;
    static int numOfChunkY=1000;
    static Chunk[][] chunks=new Chunk[numOfChunkX*2][numOfChunkY*2];
    static boolean[][]chunksPosition=new boolean[numOfChunkX*2][numOfChunkY*2];
    static boolean[] bigZLayer=new boolean[Chunk.numOfCubeZ];
    int[] newChunkIn={0,0};

    static Color[][] colorsList=new  Color[7][2];
    CubeContainer(double depthRatio){
        setColorsList();
        CubeContainer.depthRatio=depthRatio;
        CreateNewChunks();
        createCubes();
        newChunkIn[0] = Player.chunkIn[0];
        newChunkIn[1] = Player.chunkIn[1];
    }
    public void setColorsList(){
        colorsList[0][0]= new Color(5, 168, 30);
        colorsList[0][1]=new Color(95, 43, 1);
        colorsList[1][0]=new Color(119, 119, 119);
        colorsList[1][1]=new Color(119, 119, 119);
        colorsList[2][0]=new Color(51, 206, 179);
        colorsList[2][1]=new Color(51, 206, 179);
        colorsList[3][0]= new Color(59, 59, 59);
        colorsList[3][1]= new Color(59, 59, 59);
        colorsList[4][0]=new Color(255, 211, 13);
        colorsList[4][1]=new Color(255, 211, 13);
        colorsList[5][0]=new Color(218, 176, 101);
        colorsList[5][1]=new Color(89, 46, 0);
        colorsList[6][0]=new Color(29, 82, 0);
        colorsList[6][1]=new Color(29, 82, 0);
    }
    public void newCube(int x,int y,int z){
        newCube(x,y,z,0);
    }
    static public void newCube(int x,int y,int z,int type){
        int[] xInfo=YAndXPositionToChunkPos(x);
        int[] yInfo=YAndXPositionToChunkPos(y);

        int xChunkNum=xInfo[0];
        int yChunkNum=yInfo[0];

        int newX=xInfo[1];;
        int newY=yInfo[1];
        if(!chunksPosition[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum]){
            chunksPosition[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum]=true;
            chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum]=new Chunk(xChunkNum,yChunkNum);

        }
        //if(!chunksPosition[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum]||chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubePositions[newX][newY][z])return;
        Cube cube=new Cube(x,y,z,chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum],colorsList[type][0],colorsList[type][1],type);

        chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubes[newX][newY][z]=cube;
        chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubePositions[newX][newY][z]=true;
        chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].zLayer[z]=true;
        bigZLayer[z]=true;
        bigZLayer[z+1]=true;
        bigZLayer[z+2]=true;
        bigZLayer[z+3]=true;
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
    public void drawCircle2(int x, int y, int z, int r, int type){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2))>=r-1){
                    newCube(x+i,y+k,z,type);
                }
            }
        }
    }
    public void drawCircle3(int x, int y, int z, int r, int type){
        for(int i=-r;i<r;i++){
            if(Math.abs(i)<=3)continue;
            for(int k=-r;k<r;k++){
                if(Math.abs(k)<=3)continue;
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2))>=r-1){
                    newCube(x+i,y+k,z,type);
                }
            }
        }
    }
    public void fillCircle2(int x, int y, int z, int r, int type){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r){
                    newCube(x+i,y+k,z,type);
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
    public void drawBasicStructure(int x, int y, int z,int xSize,int ySize, int height) {

        for(var i=0;i<height;i++){
            for(var j=0;j<ySize;j++){
                newCube(x,y+j,z+i);
                newCube(x+xSize-1,y+j,z+i);
            }

            for(var j=0;j<xSize;j++){
                newCube(x+j,y,z+i);
                newCube(x+j,y+ySize-1,z+i);
            }
        }
    }
    public void drawPortal(int x, int y, int z,int type){
        if(type==0){
            newCube(x,-3+y,z,3);
            newCube(x,-3+y,1+z,3);
            newCube(x,-3+y,2+z,3);
            newCube(x,-3+y,3+z,3);
            newCube(x,-3+y,4+z,3);
            newCube(x,-3+y,5+z,3);
            newCube(x,-2+y,5+z,3);
            newCube(x,-2+y,6+z,3);
            newCube(x,-1+y,6+z,3);
            newCube(x,y,6+z,3);
            newCube(x,y,7+z,4);
            newCube(x,3+y,z,3);
            newCube(x,3+y,1+z,3);
            newCube(x,3+y,2+z,3);
            newCube(x,3+y,3+z,3);
            newCube(x,3+y,4+z,3);
            newCube(x,3+y,5+z,3);
            newCube(x,2+y,5+z,3);
            newCube(x,2+y,6+z,3);
            newCube(x,1+y,6+z,3);

        }
        if(type==1){
            newCube(x-3,y,z,3);
            newCube(x-3,y,1+z,3);
            newCube(x-3,y,2+z,3);
            newCube(x-3,y,3+z,3);
            newCube(x-3,y,4+z,3);
            newCube(x-3,y,5+z,3);
            newCube(x-2,y,5+z,3);
            newCube(x-2,y,6+z,3);
            newCube(x-1,y,6+z,3);
            newCube(x,y,6+z,3);
            newCube(x,y,7+z,4);
            newCube(x+3,y,z,3);
            newCube(x+3,y,1+z,3);
            newCube(x+3,y,2+z,3);
            newCube(x+3,y,3+z,3);
            newCube(x+3,y,4+z,3);
            newCube(x+3,y,5+z,3);
            newCube(x+2,y,5+z,3);
            newCube(x+2,y,6+z,3);
            newCube(x+1,y,6+z,3);


        }
    }
    public void drawSpawn(){
        fillCircle2(0,0,2,GameGrid.regenZone,2);
        drawCircle3(0,0,2,GameGrid.safeZone,1);
        drawCircle3(0,0,3,GameGrid.safeZone,1);
        drawPortal(19,0,2,0);
        drawPortal(-19,0,2,0);
        drawPortal(0,19,2,1);
        drawPortal(0,-19,2,1);




    }

    public void jump(int x,int y,int z){
        newCube(0+x,-5+y,2+z);
        newCube(-2+x,-7+y,3+z);
        newCube(-4+x,-9+y,4+z);
        newCube(-7+x,-11+y,4+z);

        newCube(-6+x,-14+y,5+z);
        newCube(-3+x,-16+y,6+z);
        newCube(-2+x,-13+y,7+z);
        newCube(1+x,-13+y,8+z);
        newCube(2+x,-10+y,9+z);
        newCube(4+x,-9+y,10+z);
        newCube(2+x,-7+y,11+z);
        newCube(0+x,-5+y,12+z);
    }

    static public void drawTree(int x,int y, int z,int height){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                newCube (x+i-2,y+j-2,z+height-2,6);
                newCube (x+i-2,y+j-2,z+height-1,6);
            }
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                newCube (x+i-1,y+j-1,z+height,6);
            }
        }
        newCube (x,y,z+height+1,6);
        newCube (x+1,y,z+height+1,6);
        newCube (x-1,y,z+height+1,6);
        newCube (x,y+1,z+height+1,6);
        newCube (x,y-1,z+height+1,6);

        for(int i=0;i<height;i++){
            newCube (x,y,z+i,5);
        }

    }
    public void createCubes(){
        drawSpawn();
        drawCircle3(0,0,2,29,1);
        drawCircle3(0,0,2,30,1);
        drawCircle3(0,0,3,30,1);
        drawCircle3(0,0,2,31,1);
        for(var i=0;i<20;i++){
            for(var j=i;j<20-i;j++){
                for(var k=i;k<20-i;k++){
                    newCube(k,j+60,i,1);
                }
            }
        }
        drawBasicStructure(0,-70,2,15,15,2);



        drawTree(0,-30,2,5);
    }
    static public void newChunk(int x,int y){
        System.out.println(x+", "+y);
        drawTree(0,0,2,5);
        chunks[numOfChunkX+x][numOfChunkY+y]=new Chunk(x,y);
        chunksPosition[numOfChunkX+x][numOfChunkY+y]=true;





    }
    static public void CreateNewChunks(){
        for(int i=Player.chunkIn[0]-Player.numOfChunkToDraw-1;i<=Player.chunkIn[0]+Player.numOfChunkToDraw+1;i++){
            for(int j=Player.chunkIn[1]-Player.numOfChunkToDraw-1;j<=Player.chunkIn[1]+Player.numOfChunkToDraw+1;j++){
                if(!chunksPosition[numOfChunkX+i][numOfChunkY+j])
                newChunk(i,j);
            }
        }

    }
    public void updateData(double deltaTime){
        if((newChunkIn[0]!=Player.chunkIn[0])||(newChunkIn[1]!=Player.chunkIn[1])){
            CreateNewChunks();
        }
        newChunkIn[0] = Player.chunkIn[0];
        newChunkIn[1] = Player.chunkIn[1];
        for(var k=0;k<Chunk.numOfCubeZ;k++){
                bigZLayer[k]=false;

        }
        for(var k=0;k<Chunk.numOfCubeZ;k++){
            if(ProjectileContainer.zLayers[k]|| EntityContainer.zLayers[k]){
                bigZLayer[k]=true;
            }

        }

        for(int i=Player.chunkIn[0]-Player.numOfChunkToDraw;i<=Player.chunkIn[0]+Player.numOfChunkToDraw;i++){
            for(int j=Player.chunkIn[1]-Player.numOfChunkToDraw;j<=Player.chunkIn[1]+Player.numOfChunkToDraw;j++){
                for(var k=0;k<Chunk.numOfCubeZ;k++){
                    if(chunks[i+numOfChunkX][j+numOfChunkY].zLayer[k]){
                        bigZLayer[k]=true;

                    }
                }
            }
        }
    }
    public void drawAll(Graphics g,int xNum,int yNum,int kNum,int jNum,int i){
        if( chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubePositions[kNum][jNum][i]){
            chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].draw(g);
        }
        if((xNum-Player.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum==Player.cubeIn[0]&&(yNum-Player.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum==Player.cubeIn[1]){
            if(Player.cubeIn[2]==i)Player.draw1(g);
            if(Player.cubeIn[2]+1==i)Player.draw2(g);
            if(Player.cubeIn[2]+2==i)Player.draw3(g);
        }
        int xNumForEnemy=(xNum-Player.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum+ EntityContainer.numOfEntities -Player.cubeIn[0];
        int yNumForEnemy=(yNum-Player.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum+ EntityContainer.numOfEntities -Player.cubeIn[1];
        if(xNumForEnemy>=0 &&xNumForEnemy< EntityContainer.numOfEntities *2&&yNumForEnemy>=0 &&yNumForEnemy< EntityContainer.numOfEntities *2) {
            if (EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i] != null) {
                for (var l = 0; l < EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i].size(); l++) {
                    if( EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i].get(l).cubeIn[2]==i) EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i].get(l).draw1(g);
                }
            }
            if (i>0&& EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-1] != null) {
                for (var l = 0; l < EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-1].size(); l++) {
                    if( EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-1].get(l).cubeIn[2]+1==i) EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-1].get(l).draw2(g);
                }
            }
            if (i>1&& EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-2] != null) {
                for (var l = 0; l < EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-2].size(); l++) {
                    if( EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-2].get(l).cubeIn[2]+2==i) EntityContainer.enemies3D[xNumForEnemy][yNumForEnemy][i-2].get(l).draw3(g);
                }
            }
        }
        int xNumForFireBall=(xNum-Player.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum+ ProjectileContainer.numOfProjectile -Player.cubeIn[0];
        int yNumForFireBall=(yNum-Player.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum+ ProjectileContainer.numOfProjectile -Player.cubeIn[1];
        if(xNumForFireBall>=0 &&xNumForFireBall< ProjectileContainer.numOfProjectile *2&&yNumForFireBall>=0 &&yNumForFireBall< ProjectileContainer.numOfProjectile *2) {
            if (ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i] != null) {
                for (var l = 0; l < ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].size(); l++) {
                    ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].get(l).draw(g);
                }
            }
        }
    }
    public void drawAll2(Graphics g,int xNum,int yNum,int kNum,int jNum,int i){
        int xPosForCube=(xNum-Player.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum;
        int yPosForCube=(yNum-Player.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum;
        double difPosXA=(xPosForCube-0.5-Player.xPosition);
        double difPosYA= (xPosForCube+0.5-Player.yPosition);
        double xPositionA= (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+0.5);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-0.5);
        double difPosXR=((Player.xPosition-xPositionA)*Cube.defaultSize);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*Cube.defaultSize);
        double difPosZ=((Player.zPosition-i)*Cube.defaultSize);
        double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;
        double sizeRatio=GAME_HEIGHT/(difPosYR*1.0*depthRatio+GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+GAME_HEIGHT/depthRatio;
        }
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newHeight=  (Cube.defaultSize*sizeRatio);
                        /*
                        if( chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubePositions[kNum][jNum][i]){
                            if (chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].newPosY-chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].newHeight<GameGrid.PFY){
                                drawAll(g, xNum,yNum, kNum, jNum,i);

                            }

                        }

                         */
        // System.out.println(i);
        //   if(i==3)System.out.println(newPosY-newHeight<GameGrid.PFY);
        // if(i==0&&k==0&&j==0)System.out.println("test");
        if(newPosY-newHeight<GameGrid.PFY){

            drawAll(g, xNum,yNum, kNum, jNum,i);
            //chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].draw(g);

        }

    }
    public boolean checkToSkip(int i){
        int xPosForCube=Player.cubeIn[0];
        double yPosForCube=  (Player.cubeIn[1]-Player.cubeAway);

        double difPosXA=(xPosForCube-0.5-Player.xPosition);
        double difPosYA= (xPosForCube+0.5-Player.yPosition);
        double xPositionA= (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+0.5);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-0.5);
        double difPosXR=((Player.xPosition-xPositionA)*Cube.defaultSize);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*Cube.defaultSize);
        double difPosZ=((Player.zPosition-i)*Cube.defaultSize);
        double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;
        double sizeRatio=GAME_HEIGHT/(difPosYR*1.0*depthRatio+GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+GAME_HEIGHT/depthRatio;
        }
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newHeight=  (Cube.defaultSize*sizeRatio);
                        /*
                        if( chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubePositions[kNum][jNum][i]){
                            if (chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].newPosY-chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].newHeight<GameGrid.PFY){
                                drawAll(g, xNum,yNum, kNum, jNum,i);

                            }

                        }

                         */
        // System.out.println(i);
        //   if(i==3)System.out.println(newPosY-newHeight<GameGrid.PFY);
        // if(i==0&&k==0&&j==0)System.out.println("test");
        if(newPosY-newHeight<GameGrid.PFY){
           // System.out.println(newPosY);

            return true;

        }
       // return true;

        return false;

    }
    public boolean checkToSkip2(int i){
        int xPosForCube=Player.cubeIn[0];
        int yPosForCube=Player.cubeIn[1];
        double difPosXA=(xPosForCube-0.5-Player.xPosition);
        double difPosYA= (xPosForCube+0.5-Player.yPosition);
        double xPositionA= (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+0.5);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-0.5);
        double difPosXR=((Player.xPosition-xPositionA)*Cube.defaultSize);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*Cube.defaultSize);
        double difPosZ=((Player.zPosition-i)*Cube.defaultSize);
        double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;
        double sizeRatio=GAME_HEIGHT/(difPosYR*1.0*depthRatio+GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+GAME_HEIGHT/depthRatio;
        }
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newHeight=  (Cube.defaultSize*sizeRatio);
                        /*
                        if( chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubePositions[kNum][jNum][i]){
                            if (chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].newPosY-chunks[xNum-Player.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum-Player.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i].newHeight<GameGrid.PFY){
                                drawAll(g, xNum,yNum, kNum, jNum,i);

                            }

                        }

                         */
        // System.out.println(i);
        //   if(i==3)System.out.println(newPosY-newHeight<GameGrid.PFY);
        // if(i==0&&k==0&&j==0)System.out.println("test");
        if(newPosY-newHeight>=GameGrid.PFY ){
            return true;
        }

        return false;

    }

    public void draw(Graphics g) {
        g.setColor(new Color(14, 172, 204));
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);


        int[] yInfo;
        int[] xInfo;


        for(var i=0;i<Chunk.numOfCubeZ;i++){
            if(!bigZLayer[i]&&!(Player.cubeIn[2]==i)&&!(Player.cubeIn[2]+1==i)&&!(Player.cubeIn[2]+2==i))continue;
            if(!checkToSkip2(i))continue;
            if(GameGrid.angleForXRotation<Math.PI/4){
            for(var j=0;j<Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2);j++){
                yInfo= YAndXPositionToChunkPos(j);
                    for(var k=Chunk.numOfCubeX*(Player.numOfChunkToDraw*2+1)-1;k>=0;k--){
                    xInfo=YAndXPositionToChunkPos(k);
                    drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                }
            }
        }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                    for(var k=Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k>=0;k--) {
                        xInfo=YAndXPositionToChunkPos(k);
                        for (var j = 0; j < Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2); j++) {
                            yInfo= YAndXPositionToChunkPos(j);
                            drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                        }
                    }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k=Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k>=0;k--) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j =  Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j=Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2)-1;j>=0;j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k=Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2)-1;k>=0;k--){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j=Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2)-1;j>=0;j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j =  Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j =  0; j <Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2); j++) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI){
                for(var j=0;j<Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2);j++){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
                    }
                }
            }
        }
//                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);
        for(var i=Chunk.numOfCubeZ-1;i>=0;i--){
            if(GameGrid.PFY<-30)break;
            if(!bigZLayer[i])continue;
            if(!checkToSkip(i))continue;
            if(GameGrid.angleForXRotation<Math.PI/4){
                for(var j=0;j<Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2);j++){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k=Chunk.numOfCubeX*(Player.numOfChunkToDraw*2+1)-1;k>=0;k--){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                for(var k=Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k>=0;k--) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = 0; j < Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2); j++) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k=Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k>=0;k--) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j =  Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j=Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2)-1;j>=0;j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k=Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2)-1;k>=0;k--){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j=Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2)-1;j>=0;j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j =  Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j =  0; j <Chunk.numOfCubeY * (1 + Player.numOfChunkToDraw * 2); j++) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI){
                for(var j=0;j<Chunk.numOfCubeY*(1+Player.numOfChunkToDraw*2);j++){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k=0;k<Chunk.numOfCubeX*(1+Player.numOfChunkToDraw*2);k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i);                    }
                }
            }
        }
    }
    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }

    static int[] YAndXPositionToChunkPos(int x){
        if(Chunk.numOfCubeX!=Chunk.numOfCubeY)System.out.println("there is a problem");

        int xNum=0;
        int xNumInChunk=x;

        while(xNumInChunk>=Chunk.numOfCubeY){
            xNum+=1;
            xNumInChunk-=Chunk.numOfCubeY;
        }
        while(xNumInChunk<0){
            xNum-=1;
            xNumInChunk+=Chunk.numOfCubeY;
        }

        return new int[]{xNum, xNumInChunk};






    }
    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }
}
