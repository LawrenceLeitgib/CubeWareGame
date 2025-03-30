import java.awt.*;
public class CubeContainer {
    static int numOfChunkX=1000;
    static int numOfChunkY=1000;
    static Chunk[][] chunks=new Chunk[numOfChunkX*2][numOfChunkY*2];
    static boolean[][]chunksPosition=new boolean[numOfChunkX*2][numOfChunkY*2];
    static boolean[][]chunksGenerate=new boolean[numOfChunkX*2][numOfChunkY*2];
    static boolean[][]chunksGenerateGround=new boolean[numOfChunkX*2][numOfChunkY*2];
    static boolean[] bigZLayer=new boolean[Chunk.numOfCubeZ];
    private final int[]  newChunkIn={0,0};
    static Color[][] colorsList=new  Color[7][2];
    CubeContainer(){
        setColorsList();
        CreateNewChunks();
        CreateNewGround();
        CreateNewStructure();
        drawSpawn();
        newChunkIn[0] = Player.chunkIn[0];
        newChunkIn[1] = Player.chunkIn[1];
    }
    public void setColorsList(){
        colorsList[0][0]= new Color(5, 168, 30);
        colorsList[0][1]=new Color(110, 45, 0);
        colorsList[1][0]=new Color(119, 119, 119);
        colorsList[1][1]=new Color(119, 119, 119);
        colorsList[2][0]=new Color(51, 206, 179);
        colorsList[2][1]=new Color(51, 206, 179);
        colorsList[3][0]= new Color(59, 59, 59);
        colorsList[3][1]= new Color(59, 59, 59);
        colorsList[4][0]=new Color(255, 211, 13);
        colorsList[4][1]=new Color(255, 211, 13);
        colorsList[5][0]=new Color(218, 176, 101);
        colorsList[5][1]=new Color(79, 40, 0);
        colorsList[6][0]=new Color(29, 82, 0);
        colorsList[6][1]=new Color(29, 82, 0);
    }
    public static void newCube(int x, int y, int z){
        newCube(x,y,z,0);
    }
    static public void newCube(int x,int y,int z,int type){
        int[] xInfo=YAndXPositionToChunkPos(x);
        int[] yInfo=YAndXPositionToChunkPos(y);

        if(!chunksPosition[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]]){
           chunksPosition[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]]=true;
           chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]]=new Chunk();
        }
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].cubes[xInfo[1]][yInfo[1]][z]=new Cube(type);
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].cubePositions[xInfo[1]][yInfo[1]][z]=true;
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].zLayer[z]=true;
        bigZLayer[z]=true;
    }
    static public void removeCube(int x,int y,int z){
        int[] xInfo=YAndXPositionToChunkPos(x);
        int[] yInfo=YAndXPositionToChunkPos(y);
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].cubes[xInfo[1]][yInfo[1]][z]=null;
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].cubePositions[xInfo[1]][yInfo[1]][z]=false;
    }
    private void drawCircle3(int x, int y, int z, int r, int type){
        for(int i=-r;i<r;i++){
            if(Math.abs(i)<=3)continue;
            for(int k=-r;k<r;k++){
                if(Math.abs(k)<=3)continue;
                double distance = Math.sqrt(Math.pow(i, 2) + Math.pow(k, 2));
                if(distance <r&& distance >=r-1){
                    newCube(x+i,y+k,z,type);
                }
            }
        }
    }
    static void fillCircle2(int x, int y, int z, int r, int type){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r){
                    newCube(x+i,y+k,z,type);
                }
            }
        }
    }
    private void removeFillCircle2(int x, int y, int z, int r){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r){
                    removeCube(x+i,y+k,z);
                }
            }
        }
    }
    private void drawBasicStructure(int x, int y, int z,int xSize,int ySize, int height) {
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
        removeFillCircle2(0,0,2,GameGrid.safeZone);
        removeFillCircle2(0,0,3,GameGrid.safeZone);
        fillCircle2(0,0,2,GameGrid.regenZone,2);
        drawCircle3(0,0,2,GameGrid.safeZone,1);
        drawCircle3(0,0,3,GameGrid.safeZone,1);
        drawPortal(19,0,2,0);
        drawPortal(-19,0,2,0);
        drawPortal(0,19,2,1);
        drawPortal(0,-19,2,1);
        createCubes();





        /*
        int a=0;
        for(var i=0;i<=21;i++){
            if(a>6)a=0;
            for(var j=i;j<=21-i;j++){
                newCube(i,j,2,a);
                newCube(j,i,2,a);
            }
            for(var j=i;j<=21-i;j++){
                newCube(21-i,21-j,2,a);
                newCube(21-j,21-i,2,a);
            }
            a++;
        }
         */
        /*
        drawBasicStructure(-7,-40,2,15,15,2);
        drawBasicStructure(-15,-30,2,3,3,1);
        drawBasicStructure(-20,-30,2,3,7,1);
        drawBasicStructure(-30,-30,2,7,3,1);
         */
    }
    static public void drawTree(int x,int y, int z,int height){
        int[] xInfo=YAndXPositionToChunkPos(x);
        int[] yInfo=YAndXPositionToChunkPos(y);

        int xChunkNum=xInfo[0];
        int yChunkNum=yInfo[0];

        int newX=xInfo[1];
        int newY=yInfo[1];

        if(chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubes[newX][newY][z-1].getType()!=0)return;
        if(chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].cubePositions[newX][newY][z]){
            drawTree(x,y,z+1, height);
            return;
        }
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
    static public void drawMountain(int x,int y, int z,int r,int step){
        for(var i=0;i<r*step;i+=step){
            if(r-i<=2)return;
            fillCircle2(x,y,i/step+z,r-i,0);
        }
    }

    public void drawtor(int x, int y, int z,int r,int R,int type){
        int dIter=r+R;
        for(int i=-dIter;i<dIter;i++){
            for(int j=-dIter;j<dIter;j++){
                for(int k=-dIter;k<dIter;k++){
                    double sqrt = Math.pow(Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2))-R,2)+ Math.pow(k, 2);
                    if(sqrt <=Math.pow(r,2)+2&& sqrt >=Math.pow(r,2)-10){
                        newCube(x+i,y+j,z+k,Math.abs(i%5));
                    }
                }
            }
        }

    }
    public void createCubes(){

        //drawtor(0,0,40,5,20,4);
        //drawHeart(0,0,30);
        //drawBall(0,20,30,20);
       // drawCircle2(0,0,10,30,5);
        //drawCircle(0,0,10,29,1);

        /*
        drawCircle3(0,0,2,29,1);
        drawCircle3(0,0,2,30,1);
        drawCircle3(0,0,3,30,1);
        drawCircle3(0,0,2,31,1);


        for(var i=0;i<20;i++){
            for(var j=i*2;j<40-i*2;j++){
                for(var k=i*2;k<40-i*2;k++){
                    newCube(k+5,j+50,i+2,1);
                    newCube(k-40,j+50,i+2,0);

                }
            }
        }

         */

       // drawBasicStructure(0,-70,2,15,15,2);
       // drawBasicStructure(1,-69,2,13,13,1);
        //drawBasicStructure(-1,-71,2,17,17,1);
        /*
        drawMountain(0,60,2,30,4);
        drawMountain(20,60,2,30,5);
        drawMountain(30,40,2,20,2);
        drawMountain(40,60,2,20,3);

        */
        //drawTree(0,-30,2,5);
    }
    static public void newChunk(int x,int y){
        if((!chunksPosition[numOfChunkX+x][numOfChunkY+y])){
            chunks[numOfChunkX + x][numOfChunkY + y] = new Chunk();
            chunksPosition[numOfChunkX + x][numOfChunkY + y] = true;
        }
    }
    static public void CreateNewChunks(){
        for(int i = Player.chunkIn[0]- GameGrid.numOfChunkToDraw-2; i<=Player.chunkIn[0]+ GameGrid.numOfChunkToDraw+2; i++){
            for(int j = Player.chunkIn[1]- GameGrid.numOfChunkToDraw-2; j<=Player.chunkIn[1]+ GameGrid.numOfChunkToDraw+2; j++){
                if(!chunksPosition[numOfChunkX+i][numOfChunkY+j])
                    newChunk(i,j);
            }
        }
    }
    static public void CreateNewGround(){
        for(int i = Player.chunkIn[0]- GameGrid.numOfChunkToDraw-2; i<=Player.chunkIn[0]+ GameGrid.numOfChunkToDraw+2; i++){
            for(int j = Player.chunkIn[1]- GameGrid.numOfChunkToDraw-2; j<=Player.chunkIn[1]+ GameGrid.numOfChunkToDraw+2; j++){
                if(!chunksGenerateGround[numOfChunkX+i][numOfChunkY+j]){
                    GenerateGround(i,j);
                    chunksGenerateGround[numOfChunkX+i][numOfChunkY+j]=true;
                }
            }
        }

    }
    static public void CreateNewStructure(){
        for(int i = Player.chunkIn[0]- GameGrid.numOfChunkToDraw; i<=Player.chunkIn[0]+ GameGrid.numOfChunkToDraw; i++){
            for(int j = Player.chunkIn[1]- GameGrid.numOfChunkToDraw; j<=Player.chunkIn[1]+ GameGrid.numOfChunkToDraw; j++){
                if(!chunksGenerate[numOfChunkX+i][numOfChunkY+j]){
                    generateChunk(i,j);
                    chunksGenerate[numOfChunkX+i][numOfChunkY+j]=true;
                }
            }
        }

    }
    private static void GenerateGround(int x, int y) {
        int numX= (int) (Math.random()*16);
        int numY= (int) (Math.random()*16);
        int stepNum=(int) (Math.random()*5+3);
        int rNum=(int)(Math.random()*20+20);
        if(Math.sqrt(Math.pow(x*Chunk.numOfCubeX+numX,2)+Math.pow(y*Chunk.numOfCubeY+numY,2))> GameGrid.safeZone*3)
            drawMountain(x*Chunk.numOfCubeX+numX,y*Chunk.numOfCubeY+numY,2,rNum,stepNum);
    }
    private static void generateChunk(int x, int y) {
        int numX= (int) (Math.random()*16);
        int numY= (int) (Math.random()*16);
        int heightNum=(int) (Math.random()*3);

        if(Math.sqrt(Math.pow(x*Chunk.numOfCubeX+numX,2)+Math.pow(y*Chunk.numOfCubeY+numY,2))>50)
            drawTree(x*Chunk.numOfCubeX+numX,y*Chunk.numOfCubeY+numY,2,4+heightNum);

    }
    public void updateData(double deltaTime){
        if((newChunkIn[0]!=Player.chunkIn[0])||(newChunkIn[1]!=Player.chunkIn[1])){
            CreateNewChunks();
            CreateNewGround();
            CreateNewStructure();
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

        for(int i = Player.chunkIn[0]- GameGrid.numOfChunkToDraw; i<=Player.chunkIn[0]+ GameGrid.numOfChunkToDraw; i++){
            for(int j = Player.chunkIn[1]- GameGrid.numOfChunkToDraw; j<=Player.chunkIn[1]+ GameGrid.numOfChunkToDraw; j++){
                for(var k=0;k<Chunk.numOfCubeZ;k++){
                    if(chunks[i+numOfChunkX][j+numOfChunkY].zLayer[k]){
                        bigZLayer[k]=true;

                    }
                }
            }
        }
    }
    public void drawAll(Graphics g,int xNum,int yNum,int kNum,int jNum,int i,int x,int y){
        int xPos=Player.cubeIn[0]+x- GameGrid.numOfChunkToDraw*Chunk.numOfCubeX-Chunk.numOfCubeX/2;
        int yPos=Player.cubeIn[1]+y- GameGrid.numOfChunkToDraw*Chunk.numOfCubeY-Chunk.numOfCubeY/2;
        int[] xPosInfo=YAndXPositionToChunkPos(xPos);
        int[] yPosInfo=YAndXPositionToChunkPos(yPos);
        if( chunks[xPosInfo[0]+numOfChunkX][yPosInfo[0]+numOfChunkY].cubePositions[xPosInfo[1]][yPosInfo[1]][i]){
            Cube cube= chunks[xPosInfo[0]+numOfChunkX][yPosInfo[0]+numOfChunkY].cubes[xPosInfo[1]][yPosInfo[1]][i];
            cube.draw(g,xPos,yPos,i);
        }
        if(xPos==Player.cubeIn[0]&&yPos==Player.cubeIn[1]){
            if(Player.cubeIn[2]==i)Player.draw1(g);
            if(Player.cubeIn[2]+1==i)Player.draw2(g);
            if(Player.cubeIn[2]+2==i)Player.draw3(g);
        }
        int xNumForEnemy=xPos+EntityContainer.numOfEntities-Player.cubeIn[0];
        int yNumForEnemy=yPos+EntityContainer.numOfEntities-Player.cubeIn[1];
        if(xNumForEnemy>=0 && xNumForEnemy<EntityContainer.numOfEntities*2 && yNumForEnemy>=0 && yNumForEnemy<EntityContainer.numOfEntities*2) {
            if (EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).cubeIn[2]==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).draw1(g);
                }
            }
            if (i>0&& EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).cubeIn[2]+1==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).draw2(g);
                }
            }
            if (i>1&& EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).cubeIn[2]+2==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).draw3(g);
                }
            }
        }

        int xNumForFireBall=xPos+ ProjectileContainer.numOfProjectile -Player.cubeIn[0];
        int yNumForFireBall=yPos+ ProjectileContainer.numOfProjectile -Player.cubeIn[1];
        if(xNumForFireBall>=0 &&xNumForFireBall< ProjectileContainer.numOfProjectile *2&&yNumForFireBall>=0 &&yNumForFireBall< ProjectileContainer.numOfProjectile *2) {
            if (ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i] != null) {
                for (var l = 0; l < ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].size(); l++) {
                    ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].get(l).draw(g);
                }
            }
        }
        /*
        if( chunks[xNum- GameGrid.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum- GameGrid.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubePositions[kNum][jNum][i]){
            Cube cube=chunks[xNum- GameGrid.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum- GameGrid.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i];
            cube.draw(g,(xNum- GameGrid.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum,(yNum- GameGrid.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum,i);
        }
          if((xNum- GameGrid.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum==Player.cubeIn[0]&&(yNum- GameGrid.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum==Player.cubeIn[1]){
            if(Player.cubeIn[2]==i)Player.draw1(g);
            if(Player.cubeIn[2]+1==i)Player.draw2(g);
            if(Player.cubeIn[2]+2==i)Player.draw3(g);
        }
        int xNumForEnemy=(xNum- GameGrid.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum+ EntityContainer.numOfEntities -Player.cubeIn[0];
        int yNumForEnemy=(yNum- GameGrid.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum+ EntityContainer.numOfEntities -Player.cubeIn[1];
        try {
        if(xNumForEnemy>=0 &&xNumForEnemy< EntityContainer.numOfEntities *2&&yNumForEnemy>=0 &&yNumForEnemy< EntityContainer.numOfEntities *2) {
            if (EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).cubeIn[2]==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i].get(l).draw1(g);
                }
            }
            if (i>0&& EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).cubeIn[2]+1==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-1].get(l).draw2(g);
                }
            }
            if (i>1&& EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2] != null) {
                for (var l = 0; l < EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].size(); l++) {
                    if( EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).cubeIn[2]+2==i) EntityContainer.entities3D[xNumForEnemy][yNumForEnemy][i-2].get(l).draw3(g);
                }
            }
        }
        }catch (NullPointerException ignored){
        }

        int xNumForFireBall=(xNum- GameGrid.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum+ ProjectileContainer.numOfProjectile -Player.cubeIn[0];
        int yNumForFireBall=(yNum- GameGrid.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum+ ProjectileContainer.numOfProjectile -Player.cubeIn[1];
        if(xNumForFireBall>=0 &&xNumForFireBall< ProjectileContainer.numOfProjectile *2&&yNumForFireBall>=0 &&yNumForFireBall< ProjectileContainer.numOfProjectile *2) {
            if (ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i] != null) {
                for (var l = 0; l < ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].size(); l++) {
                    ProjectileContainer.projectile3D[xNumForFireBall][yNumForFireBall][i].get(l).draw(g);
                }
            }
        }

         */
    }
    public void drawAll2(Graphics g,int xNum,int yNum,int kNum,int jNum,int i,int x,int y){
        int xPosForCube=(xNum- GameGrid.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum;
        int yPosForCube=(yNum- GameGrid.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum;
        double difPosXA=(xPosForCube-0.5-Player.xPosition);
        double difPosYA= (xPosForCube+0.5-Player.yPosition);
        double xPositionA= (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+0.5);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-0.5);
        double difPosXR=((Player.xPosition-xPositionA)* GameGrid.defaultSize);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))* GameGrid.defaultSize);
        double difPosZ=((Player.zPosition-i)* GameGrid.defaultSize);
        double sizeRatioValue=(GameGrid.depthRatio- GameGrid.GAME_HEIGHT)/ GameGrid.depthRatio;
        double sizeRatio= GameGrid.GAME_HEIGHT/(difPosYR*1.0* GameGrid.depthRatio+ GameGrid.GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GameGrid.GAME_HEIGHT* GameGrid.depthRatio)/(Math.pow(sizeRatioValue* GameGrid.depthRatio+ GameGrid.GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+ GameGrid.GAME_HEIGHT/ GameGrid.depthRatio;
        }
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newHeight=  (GameGrid.defaultSize*sizeRatio);
        if(newPosY-newHeight<GameGrid.PFY){
            drawAll(g, xNum,yNum, kNum, jNum,i,x,y);
        }

    }
    public boolean checkToSkip(int i){
        int xPosForCube=Player.cubeIn[0];
        double yPosForCube=  (Player.cubeIn[1]-Player.cubeAway);

        double difPosXA=(xPosForCube-0.5-Player.xPosition);
        double difPosYA= (xPosForCube+0.5-Player.yPosition);
        double xPositionA= (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+0.5);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-0.5);
        double difPosXR=((Player.xPosition-xPositionA)* GameGrid.defaultSize);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))* GameGrid.defaultSize);
        double difPosZ=((Player.zPosition-i)* GameGrid.defaultSize);
        double sizeRatioValue=(GameGrid.depthRatio- GameGrid.GAME_HEIGHT)/ GameGrid.depthRatio;
        double sizeRatio= GameGrid.GAME_HEIGHT/(difPosYR*1.0* GameGrid.depthRatio+ GameGrid.GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GameGrid.GAME_HEIGHT* GameGrid.depthRatio)/(Math.pow(sizeRatioValue* GameGrid.depthRatio+ GameGrid.GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+ GameGrid.GAME_HEIGHT/ GameGrid.depthRatio;
        }
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newHeight=  (GameGrid.defaultSize*sizeRatio);
        if(newPosY-newHeight<GameGrid.PFY){
            return true;
        }
        return false;

    }
    public boolean checkToSkip2(int i){
        int xPosForCube=Player.cubeIn[0];
        int yPosForCube=Player.cubeIn[1];
        double difPosXA=(xPosForCube-0.5-Player.xPosition);
        double difPosYA= (xPosForCube+0.5-Player.yPosition);
        double xPositionA= (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+0.5);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-0.5);
        double difPosXR=((Player.xPosition-xPositionA)* GameGrid.defaultSize);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))* GameGrid.defaultSize);
        double difPosZ=((Player.zPosition-i)* GameGrid.defaultSize);
        double sizeRatioValue=(GameGrid.depthRatio- GameGrid.GAME_HEIGHT)/ GameGrid.depthRatio;
        double sizeRatio= GameGrid.GAME_HEIGHT/(difPosYR*1.0* GameGrid.depthRatio+ GameGrid.GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GameGrid.GAME_HEIGHT* GameGrid.depthRatio)/(Math.pow(sizeRatioValue* GameGrid.depthRatio+ GameGrid.GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+ GameGrid.GAME_HEIGHT/ GameGrid.depthRatio;
        }
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newHeight=  (GameGrid.defaultSize*sizeRatio);
        if(newPosY-newHeight>=GameGrid.PFY ){
            return true;
        }

        return false;

    }
    public void draw(Graphics g) {
        g.setColor(new Color(14, 172, 204));
        g.fillRect(0, 0, GameGrid.GAME_WIDTH, GameGrid.GAME_HEIGHT);

        int[] yInfo;
        int[] xInfo;


        for(var i=0;i<Chunk.numOfCubeZ;i++){
            if(!bigZLayer[i]&&!(Player.cubeIn[2]==i)&&!(Player.cubeIn[2]+1==i)&&!(Player.cubeIn[2]+2==i))continue;
            if(!checkToSkip2(i))continue;
            int num=Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2);
            if(GameGrid.angleForXRotation<Math.PI/4){
                for(var j = 0; j<Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2); j++){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = Chunk.numOfCubeX*(GameGrid.numOfChunkToDraw*2+1)-1; k>=0; k--){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k>=0; k--) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = 0; j < Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2); j++) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k>=0; k--) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j = Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2)-1; j>=0; j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2)-1; k>=0; k--){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j = Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2)-1; j>=0; j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = 0; j <Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2); j++) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI){
                for(var j = 0; j<Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2); j++){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);
                    }
                }
            }
        }
        for(var i=Chunk.numOfCubeZ-1;i>=0;i--){
            if(GameGrid.PFY<-30)break;
            if(!bigZLayer[i])continue;
            if(!checkToSkip(i))continue;
            if(GameGrid.angleForXRotation<Math.PI/4){
                for(var j = 0; j<Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2); j++){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = Chunk.numOfCubeX*(GameGrid.numOfChunkToDraw*2+1)-1; k>=0; k--){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI/2){
                for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k>=0; k--) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = 0; j < Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2); j++) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/4){
                for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k>=0; k--) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
            else if(GameGrid.angleForXRotation<Math.PI){
                for(var j = Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2)-1; j>=0; j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2)-1; k>=0; k--){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
            else if(GameGrid.angleForXRotation<5*Math.PI/4){
                for(var j = Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2)-1; j>=0; j--){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
            else if(GameGrid.angleForXRotation<3*Math.PI/2){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2)-1; j >=0; j--) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
            else if(GameGrid.angleForXRotation<7*Math.PI/4){
                for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++) {
                    xInfo=YAndXPositionToChunkPos(k);
                    for (var j = 0; j <Chunk.numOfCubeY * (1 + GameGrid.numOfChunkToDraw * 2); j++) {
                        yInfo= YAndXPositionToChunkPos(j);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
            else if(GameGrid.angleForXRotation<2*Math.PI){
                for(var j = 0; j<Chunk.numOfCubeY*(1+ GameGrid.numOfChunkToDraw*2); j++){
                    yInfo= YAndXPositionToChunkPos(j);
                    for(var k = 0; k<Chunk.numOfCubeX*(1+ GameGrid.numOfChunkToDraw*2); k++){
                        xInfo=YAndXPositionToChunkPos(k);
                        drawAll2(g, xInfo[0],yInfo[0], xInfo[1], yInfo[1],i,k,j);                    }
                }
            }
        }
    }
    static int[] YAndXPositionToChunkPos(int pos){
        if(Chunk.numOfCubeX!=Chunk.numOfCubeX)System.out.println("there is a problem");

        int chunkPos=0;
        int posInChunk=pos;

        while(posInChunk>=Chunk.numOfCubeX){
            chunkPos+=1;
            posInChunk-=Chunk.numOfCubeX;
        }
        while(posInChunk<0){
            chunkPos-=1;
            posInChunk+=Chunk.numOfCubeX;
        }

        return new int[]{chunkPos, posInChunk};
    }
}
