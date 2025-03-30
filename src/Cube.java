import java.awt.*;
public class Cube {
    private final byte type;
    private final GameGrid gameGrid;
    Cube(int type,GameGrid gameGrid){
        this.type=(byte)type;
        this.gameGrid=gameGrid;
    }
    Cube(GameGrid gameGrid){
        this(0,gameGrid);
    }
    public int getType() {
        return type;
    }
    public void draw(Graphics g,int x,int y,int z){
        if(Math.sqrt(Math.pow(y-gameGrid.player.yPosition,2)+Math.pow(x-gameGrid.player.xPosition,2))>(2*GameGrid.numOfChunkToDraw+1)*Chunk.numOfCubeX/2.0)return;
        boolean[] sidesCheck=CheckToDraw(x,y,z);
        if(!sidesCheck[6]){
            return;
        }
        double[][] corners=getCorners(x,y,z);
        if(corners[8][0]>8)return;
        int xCountForCorners=0;
        int yCountForCorners=0;
        for(int i=0;i<8;i++){
            if(corners[i][0]<0||corners[i][0]>GameGrid.GAME_WIDTH)xCountForCorners++;
            if(corners[i][1]<0||corners[i][1]>GameGrid.GAME_HEIGHT)yCountForCorners++;
        }
        if(xCountForCorners>=8||yCountForCorners>=8)return;
        drawOnAngle(g,corners,sidesCheck,type);
    }
    private void drawOnAngle(Graphics g,double[][] corners,boolean[] sidesCheck,int type){
        int[][][] allPolygon=setAllPolygon(corners);
        final int[][] polygonBottom=allPolygon[0];
        final int[][] polygonTop=allPolygon[1];
        final int[][] polygonLeft=allPolygon[4];
        final int[][] polygonRight=allPolygon[5];
        final int[][] polygonBack =allPolygon[2];
        final int[][] polygonFront =allPolygon[3];
        boolean blockTopEmpty=sidesCheck[0];
        boolean blockBottomEmpty=sidesCheck[1];
        boolean blockLeftEmpty=sidesCheck[2];
        boolean blockRightEmpty=sidesCheck[3];
        boolean blockFrontEmpty=sidesCheck[4];
        boolean blockBackEmpty=sidesCheck[5];
        Color colorTop=CubeContainer.colorsList[type][0];
        Color colorLeft=CubeContainer.colorsList[type][1];
        Color colorRight=CubeContainer.colorsList[type][1];
        Color colorFront=CubeContainer.colorsList[type][1];
        Color colorBack=CubeContainer.colorsList[type][1];
        Color colorBottom=CubeContainer.colorsList[type][1];

        if(GameGrid.angleForHorizontalRotation <Math.PI/4) {
            if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
            if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type  );
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
        }
        else if(GameGrid.angleForHorizontalRotation <Math.PI/2) {
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
            if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type  );
        }
        else if(GameGrid.angleForHorizontalRotation <3*Math.PI/4) {
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );
            if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type  );
        }
        else if(GameGrid.angleForHorizontalRotation <Math.PI) {
            if (blockRightEmpty)  fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
            if (blockLeftEmpty) fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type );
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );

        }
        else if(GameGrid.angleForHorizontalRotation <5*Math.PI/4) {

            if (blockLeftEmpty) fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type );
            if (blockRightEmpty)   fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront ,type);
        }
        else if(GameGrid.angleForHorizontalRotation <3*Math.PI/2) {
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type);
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type);
            if (blockRightEmpty)    fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );

        }
        else if(GameGrid.angleForHorizontalRotation <7*Math.PI/4) {
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
            if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
        }
        else if(GameGrid.angleForHorizontalRotation <2*Math.PI) {
            if (blockLeftEmpty)fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type );
            if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
        }
        if (blockBottomEmpty&&corners[0][1]<GameGrid.PFY) {
            fillPolygonB(g,polygonBottom[0],polygonBottom[1] ,colorBottom,type);
        }
        if (blockTopEmpty&&corners[4][1]>GameGrid.PFY) {
            fillPolygonB(g,polygonTop[0],polygonTop[1],colorTop,"top",type);
        }

    }
    private boolean[] CheckToDraw(int x,int y,int z){
        int[] leftPosCheck=CubeContainer.YAndXPositionToChunkPos(x-1);
        int[] rightPosCheck=CubeContainer.YAndXPositionToChunkPos(x+1);
        int[] frontPosCheck=CubeContainer.YAndXPositionToChunkPos(y+1);
        int[] backPosCheck=CubeContainer.YAndXPositionToChunkPos(y-1);
        int[] thisPosCheckX=CubeContainer.YAndXPositionToChunkPos(x);
        int[] thisPosCheckY=CubeContainer.YAndXPositionToChunkPos(y);
        boolean blockTopEmpty=true;
        boolean blockBottomEmpty=true;
        boolean blockLeftEmpty=true;
        boolean blockRightEmpty=true;
        boolean blockFrontEmpty=true;
        boolean blockBackEmpty=true;
        int countForDrawing=0;
        if (gameGrid.cubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][thisPosCheckY[1]][z + 1]) {
            blockTopEmpty = false;
            countForDrawing++;
        }
        if (z != 0 && gameGrid.cubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][thisPosCheckY[1]][z - 1]) {
            blockBottomEmpty = false;
            countForDrawing++;
        }
        if (gameGrid.cubeContainer.chunks[leftPosCheck[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[leftPosCheck[1]][thisPosCheckY[1]][z]) {
            blockLeftEmpty = false;
            countForDrawing++;
        }
        if (gameGrid.cubeContainer.chunks[rightPosCheck[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[rightPosCheck[1]][thisPosCheckY[1]][z]) {
            blockRightEmpty = false;
            countForDrawing++;
        }
        if (gameGrid.cubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][frontPosCheck[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][frontPosCheck[1]][z]) {
            blockBackEmpty = false;
            countForDrawing++;
        }
        if (gameGrid.cubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][backPosCheck[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][backPosCheck[1]][z]) {
            blockFrontEmpty = false;
            countForDrawing++;
        }
        return new boolean[]{blockTopEmpty,blockBottomEmpty, blockLeftEmpty, blockRightEmpty,blockFrontEmpty, blockBackEmpty, countForDrawing < 6};
    }
    private int[][][] setAllPolygon(double[][] corners){
        int[][][] allPolygon=new int[6][2][4];
        allPolygon[0][0]= GeneralsFunctions.listDoubleToInt(new double[] {corners[0][0],corners[1][0],
                corners[2][0],corners[3][0]});
        allPolygon[0][1]= GeneralsFunctions.listDoubleToInt(new double[]{corners[0][1],corners[1][1],
                corners[2][1],corners[3][1]});

        allPolygon[1][0]= GeneralsFunctions.listDoubleToInt(new double[] {corners[4][0],corners[5][0],
                corners[6][0],corners[7][0]});
        allPolygon[1][1]= GeneralsFunctions.listDoubleToInt(new double[]{corners[4][1],corners[5][1],
                corners[6][1],corners[7][1]});

        allPolygon[2][0]= GeneralsFunctions.listDoubleToInt(new double[] {corners[0][0],corners[1][0],
                corners[5][0],corners[4][0]});
        allPolygon[2][1]= GeneralsFunctions.listDoubleToInt(new double[]{corners[0][1],corners[1][1],
                corners[5][1],corners[4][1]});

        allPolygon[3][0]= GeneralsFunctions.listDoubleToInt(new double[] {corners[3][0],corners[2][0],
                corners[6][0],corners[7][0]});
        allPolygon[3][1]= GeneralsFunctions.listDoubleToInt(new double[]{corners[3][1],corners[2][1],
                corners[6][1],corners[7][1]});

        allPolygon[4][0]= GeneralsFunctions.listDoubleToInt(new double[] {corners[0][0],corners[3][0],
                corners[7][0],corners[4][0]});
        allPolygon[4][1]= GeneralsFunctions.listDoubleToInt(new double[]{corners[0][1],corners[3][1],
                corners[7][1],corners[4][1]});

        allPolygon[5][0]= GeneralsFunctions.listDoubleToInt(new double[] {corners[1][0],corners[2][0],
                corners[6][0],corners[5][0]});
        allPolygon[5][1]= GeneralsFunctions.listDoubleToInt(new double[]{corners[1][1],corners[2][1],
                corners[6][1],corners[5][1]});
    return allPolygon;
    }
    private double[][] getCorners(int x, int y,int z){
        double[][] corners=new double[9][2];
        corners[8][0]=0;
        double[] info0= GameGrid.getObjectScreenPos(x,y,z);
        double[] info1= GameGrid.getObjectScreenPos(x+1,y,z);
        double[] info2= GameGrid.getObjectScreenPos(x+1,y-1,z);
        double[] info3= GameGrid.getObjectScreenPos(x,y-1,z);
        double[][] info= {info0,info1,info2,info3};
        corners[8][0]=info[0][2];
        for(int i=0;i<4;i++){
            corners[i][1]=info[i][1];
            corners[i+4][1]=info[i][1]- GameGrid.defaultSize*info[i][2];
            corners[i][0]=info[i][0];
            corners[i+4][0]=info[i][0];
            if(info[i][2]>corners[8][0]){
                corners[8][0]=info[i][2];
            }
        }
        return corners;
    }
    private void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color,int type){
        fillPolygonB(g, listX,listY, color,"side",type);
    }
    private  void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color,String side,int type){
        //g.setPaintMode();
        Color colorDarken= GeneralsFunctions.darkenColor(color,30);
        Color colorBrighter= GeneralsFunctions.darkenColor(color,-50);
        Color colorDarkenForLines= GeneralsFunctions.darkenColor(colorDarken,100);
        Color colorBrighterForLines= GeneralsFunctions.darkenColor(colorBrighter,100);
        Color colorForLines= GeneralsFunctions.darkenColor(color,100);

        //int[][]l1=getSmallerCorners(listX,listY,.1);
        int[][]l2=getSmallerCorners(listX,listY,.2);
        //int[][]l3=getSmallerCorners(listX,listY,.3);
        int[][]l4=getSmallerCorners(listX,listY,.4);
        int[] newListX=l2[0];
        int[] newListY=l2[1];
        int[] newListX1=l4[0];
        int[] newListY1=l4[1];
        if(type==2||type==4){
            g.setColor(colorDarken);
            g.fillPolygon(listX,listY,4);
            g.setColor(color);
            g.fillPolygon(newListX,newListY,4);
            g.setColor(colorBrighter);
            g.fillPolygon(newListX1,newListY1,4);
            g.setColor(colorDarkenForLines);
            g.drawPolygon(listX,listY,4);
            g.setColor(colorForLines);
            g.drawPolygon(newListX,newListY,4);
            g.setColor(colorBrighterForLines);
            g.drawPolygon(newListX1,newListY1,4);
        }
        else if(!(type==0&&side.equals("top"))){
            g.setColor(colorDarken);
            g.fillPolygon(listX,listY,4);
            g.setColor(color);
            g.fillPolygon(newListX,newListY,4);
            g.setColor(colorDarkenForLines);
            g.drawPolygon(listX,listY,4);
            g.setColor(colorForLines);
            g.drawPolygon(newListX,newListY,4);
        }
        else{
            g.setColor(color);
            g.fillPolygon(listX,listY,4);
            g.setColor(GeneralsFunctions.darkenColor(colorBrighter,100));
            g.drawPolygon(listX,listY,4);
        }
    }
    private int[][] getSmallerCorners(int[] listX,int[] listY,double num){
        int[] newListX=new int[4];
        int[] newListY=new int[4];

        double[] newListX2=new double[8];
        double[] newListY2=new double[8];

        newListX2[0]=  ((1-num)*listX[0]+num*listX[1]);
        newListX2[1]=  ((1-num)*listX[1]+num*listX[2]);
        newListX2[2]=  ((1-num)*listX[2]+num*listX[3]);
        newListX2[3]=  ((1-num)*listX[3]+num*listX[0]);

        newListX2[4]=  ((1-num)*listX[0]+num*listX[3]);
        newListX2[5]=  ((1-num)*listX[1]+num*listX[0]);
        newListX2[6]=  ((1-num)*listX[2]+num*listX[1]);
        newListX2[7]=  ((1-num)*listX[3]+num*listX[2]);

        newListY2[0]=  ((1-num)*listY[0]+num*listY[1]);
        newListY2[1]=  ((1-num)*listY[1]+num*listY[2]);
        newListY2[2]=  ((1-num)*listY[2]+num*listY[3]);
        newListY2[3]=  ((1-num)*listY[3]+num*listY[0]);

        newListY2[4]=  ((1-num)*listY[0]+num*listY[3]);
        newListY2[5]= ((1-num)*listY[1]+num*listY[0]);
        newListY2[6]=  ((1-num)*listY[2]+num*listY[1]);
        newListY2[7]=  ((1-num)*listY[3]+num*listY[2]);

        newListX[0]= (int) ((1-num)*newListX2[0]+num*newListX2[7]);
        newListX[1]= (int) ((1-num)*newListX2[5]+num*newListX2[2]);
        newListX[2]= (int) ((1-num)*newListX2[2]+num*newListX2[5]);
        newListX[3]= (int) ((1-num)*newListX2[7]+num*newListX2[0]);

        newListY[0]= (int) ((1-num)*newListY2[0]+num*newListY2[7]);
        newListY[1]= (int) ((1-num)*newListY2[5]+num*newListY2[2]);
        newListY[2]= (int) ((1-num)*newListY2[2]+num*newListY2[5]);
        newListY[3]= (int) ((1-num)*newListY2[7]+num*newListY2[0]);


        return new int[][]{newListX, newListY};
    }
}
