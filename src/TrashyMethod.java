import java.awt.*;

public abstract class TrashyMethod extends CubeContainer {
    TrashyMethod(double depthRatio) {
        super();
    }
    //for cube container
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
    public void drawCircle2(int x, int y, int z, int r, byte type){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2))>=r-1){
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
    public void drawAllNul(Graphics g,int xNum,int yNum,int kNum,int jNum,int i,int x,int y){
        int xPos=Player.cubeIn[0]+x- GameGrid.numOfChunkToDraw*Chunk.numOfCubeX-Chunk.numOfCubeX/2;
        int yPos=Player.cubeIn[1]+y- GameGrid.numOfChunkToDraw*Chunk.numOfCubeY-Chunk.numOfCubeY/2;
        int[] xPosInfo=YAndXPositionToChunkPos(xPos);
        int[] yPosInfo=YAndXPositionToChunkPos(yPos);
        /*
        if( chunks[xNum- GameGrid.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum- GameGrid.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubePositions[kNum][jNum][i]){
            Cube cube=chunks[xNum- GameGrid.numOfChunkToDraw+numOfChunkX+newChunkIn[0]][yNum- GameGrid.numOfChunkToDraw+numOfChunkY+newChunkIn[1]].cubes[kNum][jNum][i];
            cube.draw(g,(xNum- GameGrid.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum,(yNum- GameGrid.numOfChunkToDraw+newChunkIn[1])*Chunk.numOfCubeY+jNum,i);
        }


*/
        if(i==2){
            //System.out.println(((xNum- GameGrid.numOfChunkToDraw+newChunkIn[0])*Chunk.numOfCubeX+kNum)+" "+xPos);
            //System.out.println(xPosInfo[0]);
        }


        if(chunks[xPosInfo[0]+numOfChunkX][yPosInfo[0]+numOfChunkY]==null)return;
        if( chunks[xPosInfo[0]+numOfChunkX][yPosInfo[0]+numOfChunkY].cubePositions[xPosInfo[1]][yPosInfo[1]][i]){
            Cube cube= chunks[xPosInfo[0]+numOfChunkX][yPosInfo[0]+numOfChunkY].cubes[xPosInfo[1]][yPosInfo[1]][i];
            cube.draw(g,xPos,yPos,i);
        }


        int[] newChunkIn={1,2,3,1000};
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


    }
    //For chunk Class:
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
    //For Cube
    private int[][][] getDivisionInPolygon(int[] listX,int[] listY,int num){
        int[][] newListX=new int[4][num];
        int[][] newListY=new int[4][num];

        double step=1.0/(num+1);

        for(int i=0;i<num;i++){
            newListX[0][i]= (int) ((step*(num-i))*listX[0]+(step*(i+1))*listX[1]+.5);
            newListY[0][i]= (int) ((step*(num-i))*listY[0]+(step*(i+1))*listY[1]+.5);

            newListX[1][i]= (int) ((step*(num-i))*listX[1]+(step*(i+1))*listX[2]+.5);
            newListY[1][i]= (int) ((step*(num-i))*listY[1]+(step*(i+1))*listY[2]+.5);

            newListX[2][i]= (int) ((step*(num-i))*listX[2]+(step*(i+1))*listX[3]+.5);
            newListY[2][i]= (int) ((step*(num-i))*listY[2]+(step*(i+1))*listY[3]+.5);

            newListX[3][i]= (int) ((step*(num-i))*listX[3]+(step*(i+1))*listX[0]+.5);
            newListY[3][i]= (int) ((step*(num-i))*listY[3]+(step*(i+1))*listY[0]+.5);

        }


        return new int[][][]{newListX, newListY};
    }
    private void fillPolygonB1(Graphics g, int[] listX, int[] listY, Color color){
        g.setColor(new Color(21, 92, 5));
        g.setPaintMode();
        Color newColor= GeneralsFunctions.darkenColor(color,30);
        double num=0.2;
        int[] newListX=new int[4];
        int[] newListY=new int[4];
        newListX[0]= (int) ((1-num)*listX[0]+num*listX[2]);
        newListX[1]= (int) ((1-num)*listX[1]+num*listX[3]);
        newListX[2]= (int) ((1-num)*listX[2]+num*listX[0]);
        newListX[3]= (int) ((1-num)*listX[3]+num*listX[1]);
        newListY[0]= (int) ((1-num)*listY[0]+num*listY[2]);
        newListY[1]= (int) ((1-num)*listY[1]+num*listY[3]);
        newListY[2]= (int) ((1-num)*listY[2]+num*listY[0]);
        newListY[3]= (int) ((1-num)*listY[3]+num*listY[1]);
        g.setColor(newColor);
        g.fillPolygon(listX,listY,4);
        g.setColor(color);
        g.fillPolygon(newListX,newListY,4);
        g.setColor(Color.BLACK);
        for(var i=0;i<4;i++) {
            int xNum = 1 + i;
            if (xNum == 4) xNum = 0;
            g.drawLine(listX[i],listY[i],listX[xNum],listY[xNum]);
        }

    }
}
