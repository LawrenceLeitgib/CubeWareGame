import java.awt.*;
public class Cube {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static int defaultSize=100;
    private final int type;
    Cube(int type){
        this.type=type;
    }
    Cube(){
        this(0);
    }
    public int getType() {
        return type;
    }
    static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }
    static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }
    void draw(Graphics g,int x,int y,int z){
        boolean drawCube=true;
        boolean[] sidesCheck=CheckToDraw(x,y,z);
        if(!sidesCheck[6]){
            return;
        }
        if(Math.sqrt(Math.pow(y-Player.yPosition,2)+Math.pow(x-Player.xPosition,2))>(Player.numOfChunkToDraw)*Chunk.numOfCubeX)return;
        double[][] corners=getCorners(x,y,z);
        if(corners[8][0]==-1)drawCube=false;
        if(drawCube) drawOnAngle(g,corners,sidesCheck,type);
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
        Color colorSide=CubeContainer.colorsList[type][1];
        Color colorLeft=colorSide;
        Color colorRight=colorSide;
        Color colorFront=colorSide;
        Color colorBack=colorSide;
        Color colorBottom=colorSide;
        //this.colorTop=CubeContainer.colorsList[type][0];
       // this.colorSide=CubeContainer.colorsList[type][1];


        if(GameGrid.angleForXRotation<Math.PI/4) {
            if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
            if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type  );
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
        }
        else if(GameGrid.angleForXRotation<Math.PI/2) {
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
            if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type  );
        }
        else if(GameGrid.angleForXRotation<3*Math.PI/4) {
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );
            if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type  );
        }
        else if(GameGrid.angleForXRotation<Math.PI) {
            if (blockRightEmpty)  fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
            if (blockLeftEmpty) fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type );
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );

        }
        else if(GameGrid.angleForXRotation<5*Math.PI/4) {

            if (blockLeftEmpty) fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft,type );
            if (blockRightEmpty)   fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront ,type);
        }
        else if(GameGrid.angleForXRotation<3*Math.PI/2) {
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type);
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type);
            if (blockRightEmpty)    fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );

        }
        else if(GameGrid.angleForXRotation<7*Math.PI/4) {
            if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront,type );
            if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack,type );
            if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight,type  );
        }
        else if(GameGrid.angleForXRotation<2*Math.PI) {
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

            if (CubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][thisPosCheckY[1]][z + 1]) {
                blockTopEmpty = false;
                countForDrawing++;
            }
            if (z != 0 && CubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][thisPosCheckY[1]][z - 1]) {
                blockBottomEmpty = false;
                countForDrawing++;
            }
            if (CubeContainer.chunks[leftPosCheck[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[leftPosCheck[1]][thisPosCheckY[1]][z]) {
                blockLeftEmpty = false;
                countForDrawing++;
            }
            if (CubeContainer.chunks[rightPosCheck[0] + CubeContainer.numOfChunkX][thisPosCheckY[0] + CubeContainer.numOfChunkY].cubePositions[rightPosCheck[1]][thisPosCheckY[1]][z]) {
                blockRightEmpty = false;
                countForDrawing++;
            }
            if (CubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][frontPosCheck[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][frontPosCheck[1]][z]) {
                blockBackEmpty = false;
                countForDrawing++;
            }
            if (CubeContainer.chunks[thisPosCheckX[0] + CubeContainer.numOfChunkX][backPosCheck[0] + CubeContainer.numOfChunkY].cubePositions[thisPosCheckX[1]][backPosCheck[1]][z]) {
                blockFrontEmpty = false;
                countForDrawing++;
            }
        return new boolean[]{blockTopEmpty,blockBottomEmpty, blockLeftEmpty, blockRightEmpty,blockFrontEmpty, blockBackEmpty, countForDrawing < 6};
    }
    private int[][][] setAllPolygon(double[][] corners){
        int[][][] allPolygon=new int[6][2][4];
        allPolygon[0][0]=listDoubleToInt(new double[] {corners[0][0],corners[1][0],
                corners[2][0],corners[3][0]});
        allPolygon[0][1]=listDoubleToInt(new double[]{corners[0][1],corners[1][1],
                corners[2][1],corners[3][1]});

        allPolygon[1][0]=listDoubleToInt(new double[] {corners[4][0],corners[5][0],
                corners[6][0],corners[7][0]});
        allPolygon[1][1]=listDoubleToInt(new double[]{corners[4][1],corners[5][1],
                corners[6][1],corners[7][1]});

        allPolygon[2][0]=listDoubleToInt(new double[] {corners[0][0],corners[1][0],
                corners[5][0],corners[4][0]});
        allPolygon[2][1]=listDoubleToInt(new double[]{corners[0][1],corners[1][1],
                corners[5][1],corners[4][1]});

        allPolygon[3][0]=listDoubleToInt(new double[] {corners[3][0],corners[2][0],
                corners[6][0],corners[7][0]});
        allPolygon[3][1]=listDoubleToInt(new double[]{corners[3][1],corners[2][1],
                corners[6][1],corners[7][1]});

        allPolygon[4][0]=listDoubleToInt(new double[] {corners[0][0],corners[3][0],
                corners[7][0],corners[4][0]});
        allPolygon[4][1]=listDoubleToInt(new double[]{corners[0][1],corners[3][1],
                corners[7][1],corners[4][1]});

        allPolygon[5][0]=listDoubleToInt(new double[] {corners[1][0],corners[2][0],
                corners[6][0],corners[5][0]});
        allPolygon[5][1]=listDoubleToInt(new double[]{corners[1][1],corners[2][1],
                corners[6][1],corners[5][1]});
    return allPolygon;
    }
    private int[] listDoubleToInt(double[] l){
        int[] newList= new int[l.length];
        for(var i=0;i<l.length;i++){
            newList[i]= (int) l[i];
        }
        return newList;
    }
    static double[] getObjectScreenPos(double x, double y, double z){
       return getObjectScreenPos(x,y,z,GameGrid.angleForXRotation);
    }
    static double[] getObjectScreenPos(double x, double y, double z,double angle){
        double xCorrectorForRotation=.5;
        double yCorrectorForRotation=.5;
        double sizeRatioValue=(GameGrid.depthRatio-GAME_HEIGHT)/GameGrid.depthRatio;
        double difPosXA=(x-xCorrectorForRotation-Player.xPosition);
        double difPosYA= (y+yCorrectorForRotation-Player.yPosition);
        double difPosZ=((Player.zPosition-z)*defaultSize);
        double xPositionA=  (Player.xPosition+difPosXA*Math.cos(angle)+difPosYA*Math.sin(angle)+xCorrectorForRotation);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(angle)+difPosYA*Math.cos(angle)-yCorrectorForRotation);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*defaultSize);
        double difPosXR=((Player.xPosition-xPositionA)*defaultSize);
        double sizeRatio=GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+GAME_HEIGHT/GameGrid.depthRatio;
        }
        double newPosYR=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newPosXR=  (GameGrid.PVX-(difPosXR)*sizeRatio-(defaultSize*sizeRatio)/2);
        return new double[]{newPosXR, newPosYR, sizeRatio};
    }
    private double[][] getCorners(int x, int y,int z){

        double[][] corners=new double[9][2];
        corners[8][0]=0;
        double[] info0= getObjectScreenPos(x,y,z);
        double[] info1= getObjectScreenPos(x+1,y,z);
        double[] info2= getObjectScreenPos(x+1,y-1,z);
        double[] info3= getObjectScreenPos(x,y-1,z);
        if(info0[1]+info0[2]*defaultSize<0){
            corners[8][0]=-1;
            return corners;
        }
        if(info0[1]>GAME_HEIGHT*2){
            corners[8][0]=-1;
            return corners;
        }
        if(info0[0] >GAME_WIDTH*2){
            corners[8][0]=-1;
            return corners;
        }
        if(info0[0] <-GAME_WIDTH){
            corners[8][0]=-1;
            return corners;
        }
        corners[0][1]=info0[1];
        corners[1][1]=info1[1];
        corners[2][1]=info2[1];
        corners[3][1]=info3[1];
        corners[4][1]=info0[1]-defaultSize*info0[2];
        corners[5][1]=info1[1]-defaultSize*info1[2];
        corners[6][1]=info2[1]-defaultSize*info2[2];
        corners[7][1]=info3[1]-defaultSize*info3[2];
        corners[0][0]=info0[0];
        corners[1][0]=info1[0];
        corners[2][0]=info2[0];
        corners[3][0]=info3[0];
        corners[4][0]=info0[0];
        corners[5][0]=info1[0];
        corners[6][0]=info2[0];
        corners[7][0]=info3[0];
        return corners;
    }

    private void fillPolygonB1(Graphics g,int[] listX,int[] listY,Color color){
        g.setColor(new Color(21, 92, 5));
        g.setPaintMode();
        Color newColor=darkenColor(color,30);
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
    private void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color,int type){
        fillPolygonB(g, listX,listY, color,"side",type);
    }
    private  void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color,String side,int type){
//        g.setPaintMode();
        Color colorDarken=darkenColor(color,30);
        Color colorBrighter=darkenColor(color,-50);
        Color colorDarkenForLines=darkenColor(colorDarken,100);
        Color colorBrighterForLines=darkenColor(colorBrighter,100);
        Color colorForLines=darkenColor(color,100);

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
            g.setColor(darkenColor(colorBrighter,100));
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
    public static Color darkenColor(Color color, int num){
        int b= color.getBlue();
        int g= color.getGreen();
        int r= color.getRed();

        if(r<num)r=num;
        if(b<num)b=num;
        if(g<num)g=num;
        if(r>255+num)r=255+num;
        if(b>255+num)b=255+num;
        if(g>255+num)g=255+num;

        return new Color(r-num,g-num,b-num);

    }
}
