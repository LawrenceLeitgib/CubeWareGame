import java.awt.*;
public class CubeContainer {
    static final int numOfChunkX=1000;
    static final int numOfChunkY=1000;
    Chunk[][] chunks=new Chunk[numOfChunkX*2][numOfChunkY*2];
    boolean[][]chunksPosition=new boolean[numOfChunkX*2][numOfChunkY*2];
    boolean[][]chunksGenerate=new boolean[numOfChunkX*2][numOfChunkY*2];
    boolean[][]chunksGenerateGround=new boolean[numOfChunkX*2][numOfChunkY*2];
    private final int[]  newChunkIn={0,0};
    public static Color[][] colorsList=new  Color[7][2];
    CubeContainer(){
        setColorsList();
        CreateNewChunks();
        //CreateNewGround();
        //CreateNewStructure();
        drawSpawn();
        newChunkIn[0] = GameGrid.player.chunkIn[0];
        newChunkIn[1] = GameGrid.player.chunkIn[1];
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
    public void newCube(int x, int y, int z){
        newCube(x,y,z,0);
    }
    public void newCube(int x,int y,int z,int type){
        int[] xInfo=YAndXPositionToChunkPos(x);
        int[] yInfo=YAndXPositionToChunkPos(y);
        if(!chunksPosition[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]]){
           chunksPosition[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]]=true;
           chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]]=new Chunk();
        }
        if(!chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].cubePositions[xInfo[1]][yInfo[1]][z])Chunk.numberOfCube++;
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].getCubes()[xInfo[1]][yInfo[1]][z]=new Cube(type);
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].cubePositions[xInfo[1]][yInfo[1]][z]=true;
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].zLayer[z]=true;
        GameGrid.bigZLayer[z]=true;
    }
    public void removeCube(int x,int y,int z){
        int[] xInfo=YAndXPositionToChunkPos(x);
        int[] yInfo=YAndXPositionToChunkPos(y);
        chunks[numOfChunkX+xInfo[0]][numOfChunkY+yInfo[0]].getCubes()[xInfo[1]][yInfo[1]][z]=null;
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
    private void fillCircle2(int x, int y, int z, int r, int type){
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
    }
    public void drawTree(int x,int y, int z,int height){
        int[] xInfo=YAndXPositionToChunkPos(x);
        int[] yInfo=YAndXPositionToChunkPos(y);

        int xChunkNum=xInfo[0];
        int yChunkNum=yInfo[0];

        int newX=xInfo[1];
        int newY=yInfo[1];

        if(chunks[numOfChunkX+xChunkNum][numOfChunkY+yChunkNum].getCubes()[newX][newY][z-1].getType()!=0)return;
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
    public void drawMountain(int x,int y, int z,int r,int step){
        for(var i=0;i<r*step;i+=step){
            if(r-i<=2)return;
            fillCircle2(x,y,i/step+z,r-i,0);
        }
    }
    public void drawtor(int x, int y, int z,int r,int R){
        int dIter=r+R;
        for(int i=-dIter;i<dIter;i++){
            for(int j=-dIter;j<dIter;j++){
                for(int k=-dIter;k<dIter;k++){
                    double sqrt = Math.pow(Math.sqrt(Math.pow(i, 2) + Math.pow(j, 2))-R,2)+ Math.pow(k, 2);
                    if(sqrt <=Math.pow(r,2)+10&& sqrt >=Math.pow(r,2)-10){
                        newCube(x+i,y+j,z+k,Math.abs(i%5));
                    }
                }
            }
        }

    }
    public void createCubes(){
       //drawBasicStructure(-10,-10,0,20,20,5);
       // drawtor(0,0,30,10,30);
/*
        int type=2;
        int temp=GameGrid.numOfChunkToDraw;
        GameGrid.numOfChunkToDraw=2;
        double angle=-GameGrid.angleForHorizontalRotation/8;
        for(int y=-GameGrid.numOfChunkToDraw*Chunk.numOfCubeY;y<GameGrid.numOfChunkToDraw*Chunk.numOfCubeY;y++){
            for(int x=-GameGrid.numOfChunkToDraw*Chunk.numOfCubeX;x<GameGrid.numOfChunkToDraw*Chunk.numOfCubeX;x++){
                removeCube(x+GameGrid.player.cubeIn[0],y+GameGrid.player.cubeIn[1],3);
            }
        }
        GameGrid.numOfChunkToDraw=1;
        for(int y=-GameGrid.numOfChunkToDraw*Chunk.numOfCubeY;y<GameGrid.numOfChunkToDraw*Chunk.numOfCubeY;y++){
            if(y==GameGrid.numOfChunkToDraw*Chunk.numOfCubeY-1)type=6;
            else if(y==-GameGrid.numOfChunkToDraw*Chunk.numOfCubeY)type=1;
            else type=4;
            for(int x=-GameGrid.numOfChunkToDraw*Chunk.numOfCubeX;x<GameGrid.numOfChunkToDraw*Chunk.numOfCubeX;x++){
                //newCube(x,y,3,type);
               // newCube((int) (Math.cos(angle)*x+.5)+(int)(Math.sin(angle)*y+.5)+GameGrid.player.cubeIn[0], (int) (-Math.sin(angle)*x+.5)+(int)(Math.cos(angle)*y+.5)+GameGrid.player.cubeIn[1],3,type);

               newCube((int) (Math.cos(angle)*x+Math.sin(angle)*y+.5)+GameGrid.player.cubeIn[0], (int) (-Math.sin(angle)*x+Math.cos(angle)*y+.5)+GameGrid.player.cubeIn[1],3,type);
            }
            type++;
            if(type>=5)type=2;
        }
        GameGrid.numOfChunkToDraw=temp;

 */



        /*
        int distance = (int)16;

        double angle=Math.PI/16;
        for(int i=distance;i>0;i--){
            for(int j=distance;j>0;j--){
                newCube((int) (i*Math.cos(angle*j)), (int) (i*Math.sin(angle*j)),3,type);
            }
            type++;
            if(type>=6)type=0;
        }

         */

    }
    public void newChunk(int x,int y){
        if((!chunksPosition[numOfChunkX+x][numOfChunkY+y])){
            chunks[numOfChunkX + x][numOfChunkY + y] = new Chunk();
            chunksPosition[numOfChunkX + x][numOfChunkY + y] = true;
        }
    }
    public void CreateNewChunks(){
        for(int i = GameGrid.player.chunkIn[0]- GameGrid.numOfChunkToDraw -2; i<=GameGrid.player.chunkIn[0]+ GameGrid.numOfChunkToDraw +2; i++){
            for(int j = GameGrid.player.chunkIn[1]- GameGrid.numOfChunkToDraw -2; j<=GameGrid.player.chunkIn[1]+ GameGrid.numOfChunkToDraw +2; j++){
                if(!chunksPosition[numOfChunkX+i][numOfChunkY+j]) newChunk(i,j);
            }
        }
    }
    public void CreateNewGround(){
        for(int i = GameGrid.player.chunkIn[0]- GameGrid.numOfChunkToDraw -2; i<=GameGrid.player.chunkIn[0]+ GameGrid.numOfChunkToDraw +2; i++){
            for(int j = GameGrid.player.chunkIn[1]- GameGrid.numOfChunkToDraw -2; j<=GameGrid.player.chunkIn[1]+ GameGrid.numOfChunkToDraw +2; j++){
                if(!chunksGenerateGround[numOfChunkX+i][numOfChunkY+j]){
                    generateGround(i,j);
                    chunksGenerateGround[numOfChunkX+i][numOfChunkY+j]=true;
                }
            }
        }

    }
    public void CreateNewStructure(){
        for(int i = GameGrid.player.chunkIn[0]- GameGrid.numOfChunkToDraw; i<=GameGrid.player.chunkIn[0]+ GameGrid.numOfChunkToDraw; i++){
            for(int j = GameGrid.player.chunkIn[1]- GameGrid.numOfChunkToDraw; j<=GameGrid.player.chunkIn[1]+ GameGrid.numOfChunkToDraw; j++){
                if(!chunksGenerate[numOfChunkX+i][numOfChunkY+j]){
                    generateChunk(i,j);
                    chunksGenerate[numOfChunkX+i][numOfChunkY+j]=true;
                }
            }
        }

    }
    private void generateGround(int x, int y) {
        int numX= (int) (Math.random()*16);
        int numY= (int) (Math.random()*16);
        int stepNum=(int) (Math.random()*5+3);
        int rNum=(int)(Math.random()*20+20);
        if(Math.sqrt(Math.pow(x*Chunk.numOfCubeX+numX,2)+Math.pow(y*Chunk.numOfCubeY+numY,2))> GameGrid.safeZone*3)
            drawMountain(x*Chunk.numOfCubeX+numX,y*Chunk.numOfCubeY+numY,2,rNum,stepNum);
    }
    private void generateChunk(int x, int y) {
        int numX= (int) (Math.random()*16);
        int numY= (int) (Math.random()*16);
        int heightNum=(int) (Math.random()*3);
        if(Math.sqrt(Math.pow(x*Chunk.numOfCubeX+numX,2)+Math.pow(y*Chunk.numOfCubeY+numY,2))>50)
            drawTree(x*Chunk.numOfCubeX+numX,y*Chunk.numOfCubeY+numY,2,4+heightNum);

    }
    public void updateData(double deltaTime){
        if((newChunkIn[0]!=GameGrid.player.chunkIn[0])||(newChunkIn[1]!=GameGrid.player.chunkIn[1])){
            CreateNewChunks();
            CreateNewGround();
            CreateNewStructure();
        }
        //createCubes();
        newChunkIn[0] = GameGrid.player.chunkIn[0];
        newChunkIn[1] = GameGrid.player.chunkIn[1];
    }
    static int[] YAndXPositionToChunkPos(int pos){
        //if(Chunk.numOfCubeX!=Chunk.numOfCubeX)System.out.println("there is a problem");
        if(pos>=0) return new int[]{pos/Chunk.numOfCubeX, pos%Chunk.numOfCubeX};
        else {
            if (pos % Chunk.numOfCubeX == 0) return new int[]{pos / Chunk.numOfCubeX - 2, 0};
            else return new int[]{pos / Chunk.numOfCubeX - 1, Chunk.numOfCubeX + pos % Chunk.numOfCubeX};

        }


    }
}
