import java.awt.*;
public class Cube {
    private final int xPosition;
    private final int yPosition;
    private final int zPosition;
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    static int defaultSize=100;
    static int width=defaultSize;
    static int height=defaultSize;
    static int depth=defaultSize;
    static double depthRatio;
    private boolean blockLeftEmpty=true;
    private boolean blockRightEmpty=true;
    private boolean blockFrontEmpty=true;
    private boolean blockBackEmpty=true;
    private boolean blockTopEmpty=true;
    private boolean blockBottomEmpty=true;
    private double newPosY;
    private double newHeight;
    double sizeRatio;
    private final double[][] corners=new double[8][2];
    int countForDrawing=0;
    Chunk chunk;
    private double xPositionA;
    private double yPositionA;
    private final int[][] polygonTop=new int[2][4];
    private final int[][] polygonBottom=new int[2][4];
    private final int[][] polygonLeft=new int[2][4];
    private final int[][] polygonRight=new int[2][4];
    private final int[][] polygonBack =new int[2][4];
    private final int[][] polygonFront =new int[2][4];
    static double xCorrectorForRotation=.5;
    static double yCorrectorForRotation=.5;
    int type;
    private Color colorTop;
    private Color colorSide;
    private Color colorLeft;
    private Color colorRight;
    private Color colorFront;
    private Color colorBack;
    private Color colorBottom;
    private boolean drawCube=true;

    Cube(int xPosition, int yPosition, int zPosition,Chunk chunk,Color colorTop,Color colorSide,int type){
        this.xPosition=xPosition;
        this.yPosition=yPosition;
        this.zPosition=zPosition;
        Cube.depthRatio=GameGrid.depthRatio;
        //this.colorTop=colorTop;
        //this.colorSide=colorSide;
        this.chunk=chunk;
        this.xPositionA=xPosition;
        this.yPositionA=yPosition;
        this.type=type;
        this.colorTop=CubeContainer.colorsList[type][0];
        this.colorSide=CubeContainer.colorsList[type][1];
        colorLeft=colorSide;
        colorRight=colorSide;
        colorFront=colorSide;
        colorBack=colorSide;
        colorBottom=colorSide;
    }
    Cube(int xPosition, int yPosition, int zPosition, Chunk chunk){
        this(xPosition,  yPosition, zPosition, chunk, new Color(5, 168, 30),new Color(95, 43, 1),0);
    }
    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }
    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }
    public void draw(Graphics g){
        if(!CheckToDraw()){
            //chunk.cubes[xPosition- chunk.chunkToNormNumX][yPosition- chunk.chunkToNormNumY][zPosition]=null;
           // chunk.cubePositions[xPosition- chunk.chunkToNormNumX][yPosition- chunk.chunkToNormNumY][zPosition]=false;
            return;
        }
        if(Math.sqrt(Math.pow(yPosition-Player.yPosition,2)+Math.pow(xPosition-Player.xPosition,2))>(Player.numOfChunkToDraw)*Chunk.numOfCubeX)return;
        drawCube=true;
        getCorners();
        if(drawCube)
        drawOnAngle(g);
    }
    public void drawOnAngle(Graphics g){
        setAllPolygon(corners);

        //(Player.zPosition-zPosition-1.3)
       /* double deltaXWithCamera=xPosition-GameGrid.cameraPos[0]-Math.sin(GameGrid.angleForXRotation)*(Player.zPosition-zPosition-1.2);
        double deltaYWithCamera=yPosition-GameGrid.cameraPos[1]+Math.cos(GameGrid.angleForXRotation)*(Player.zPosition-zPosition-1.2);
        System.out.println(((int) (deltaXWithCamera*1000+0.5)/1000.0)+","+((int) (deltaYWithCamera*1000+0.5)/1000.0));

        */

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


       /*

        if (blockBackEmpty&&deltaYWithCamera<0)fillPolygonB(g, polygonBack[0], polygonBack[1],colorBack );
        if (blockFrontEmpty&&deltaYWithCamera>0)fillPolygonB(g, polygonFront[0], polygonFront[1],colorFront );
        if (blockRightEmpty&&deltaXWithCamera<0) fillPolygonB(g,polygonRight[0],polygonRight[1],colorRight  );
        if (blockLeftEmpty&&deltaXWithCamera>0)fillPolygonB(g,polygonLeft[0],polygonLeft[1],colorLeft );



        */

        if (blockBottomEmpty&&newPosY<GameGrid.PFY) {
            fillPolygonB(g,polygonBottom[0],polygonBottom[1] ,colorBottom,type);
        }
        if (blockTopEmpty&&newPosY-newHeight>GameGrid.PFY) {
            fillPolygonB(g,polygonTop[0],polygonTop[1],colorTop,"top",type);
        }


    }
    private boolean CheckToDraw(){
        int leftPosCheck=xPosition-1- chunk.chunkToNormNumX;
        int rightPosCheck=xPosition+1- chunk.chunkToNormNumX;
        int frontPosCheck=yPosition-1- chunk.chunkToNormNumY;
        int backPosCheck=yPosition+1- chunk.chunkToNormNumY;
        int topPosCheck=zPosition+1;
        int bottomPosCheck=zPosition-1;
        blockLeftEmpty=true;
        blockTopEmpty=true;
        blockBottomEmpty=true;
        blockRightEmpty=true;
        blockFrontEmpty=true;
        blockBackEmpty=true;
        countForDrawing=0;
        if(leftPosCheck>=0) {
            if (chunk.cubePositions[leftPosCheck][yPosition - chunk.chunkToNormNumY][zPosition]) {
                blockLeftEmpty = false;
                countForDrawing++;

            }
        }
        else {
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition - 1][CubeContainer.numOfChunkY + chunk.yPosition]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition - 1][CubeContainer.numOfChunkY + chunk.yPosition].cubePositions[leftPosCheck + Chunk.numOfCubeX][yPosition - chunk.chunkToNormNumY][zPosition]) {
                    blockLeftEmpty = false;
                    countForDrawing++;
                }
            }
        }
        if(rightPosCheck<Chunk.numOfCubeX) {
            if(chunk.cubePositions[rightPosCheck][yPosition- chunk.chunkToNormNumY][zPosition]){
                blockRightEmpty=false;
                countForDrawing++;
            }
        }
        else {
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition +1][CubeContainer.numOfChunkY + chunk.yPosition]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition +1][CubeContainer.numOfChunkY + chunk.yPosition].cubePositions[rightPosCheck - Chunk.numOfCubeX][yPosition - chunk.chunkToNormNumY][zPosition]) {
                    blockRightEmpty = false;
                    countForDrawing++;
                }
            }
        }
        if(frontPosCheck>=0) {
            if (chunk.cubePositions[xPosition - chunk.chunkToNormNumX][frontPosCheck][zPosition]) {
                blockFrontEmpty = false;
                countForDrawing++;
            }
        }
        else{
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition-1]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition-1].cubePositions[xPosition - chunk.chunkToNormNumX][frontPosCheck+Chunk.numOfCubeY][zPosition]) {
                    blockFrontEmpty = false;
                    countForDrawing++;
                }
            }

        }
        if(backPosCheck<Chunk.numOfCubeY){
            if(chunk.cubePositions[xPosition- chunk.chunkToNormNumX][backPosCheck][zPosition]) {
                blockBackEmpty = false;
                countForDrawing++;
            }
        }
        else{
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition+1]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition+1].cubePositions[xPosition - chunk.chunkToNormNumX][backPosCheck-Chunk.numOfCubeY][zPosition]) {
                    blockBackEmpty = false;
                    countForDrawing++;
                }
            }

        }
        if(topPosCheck<Chunk.numOfCubeZ){
            if(chunk.cubePositions[xPosition- chunk.chunkToNormNumX][yPosition- chunk.chunkToNormNumY][topPosCheck]){
                blockTopEmpty=false;
                countForDrawing++;
            }
        }
        if(bottomPosCheck>=0){
            if(chunk.cubePositions[xPosition- chunk.chunkToNormNumX][yPosition- chunk.chunkToNormNumY][bottomPosCheck]){
                blockBottomEmpty=false;
                countForDrawing++;
            }
        }
        //if(countForDrawing==6)System.out.println(xPosition+","+yPosition+","+zPosition);

        return countForDrawing < 6;
    }
    private void setAllPolygon(double[][] corners){
        polygonBottom[0]=listDoubleToInt(new double[] {corners[0][0],corners[1][0],
                corners[2][0],corners[3][0]});
        polygonBottom[1]=listDoubleToInt(new double[]{corners[0][1],corners[1][1],
                corners[2][1],corners[3][1]});

        polygonTop[0]=listDoubleToInt(new double[] {corners[4][0],corners[5][0],
                corners[6][0],corners[7][0]});
        polygonTop[1]=listDoubleToInt(new double[]{corners[4][1],corners[5][1],
                corners[6][1],corners[7][1]});

        polygonBack[0]=listDoubleToInt(new double[] {corners[0][0],corners[1][0],
                corners[5][0],corners[4][0]});
        polygonBack[1]=listDoubleToInt(new double[]{corners[0][1],corners[1][1],
                corners[5][1],corners[4][1]});

        polygonFront[0]=listDoubleToInt(new double[] {corners[3][0],corners[2][0],
                corners[6][0],corners[7][0]});
        polygonFront[1]=listDoubleToInt(new double[]{corners[3][1],corners[2][1],
                corners[6][1],corners[7][1]});

        polygonLeft[0]=listDoubleToInt(new double[] {corners[0][0],corners[3][0],
                corners[7][0],corners[4][0]});
        polygonLeft[1]=listDoubleToInt(new double[]{corners[0][1],corners[3][1],
                corners[7][1],corners[4][1]});

        polygonRight[0]=listDoubleToInt(new double[] {corners[1][0],corners[2][0],
                corners[6][0],corners[5][0]});
        polygonRight[1]=listDoubleToInt(new double[]{corners[1][1],corners[2][1],
                corners[6][1],corners[5][1]});


    }
    static int[] listDoubleToInt(double[] l){
        int[] newList= new int[l.length];
        for(var i=0;i<l.length;i++){
            newList[i]= (int) l[i];
        }
        return newList;
    }


    private double[] getPosOtherBlock(int x, int y,double sizeRatioValue){
        double difPosXA=(xPosition+x-xCorrectorForRotation-Player.xPosition);
        double difPosYA= (yPosition+y+yCorrectorForRotation-Player.yPosition);
        double difPosZ=((Player.zPosition-zPosition)*height);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-yCorrectorForRotation);
        double xPositionA=  (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+xCorrectorForRotation);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*depth);
        double difPosXR=((Player.xPosition-xPositionA)*width);
        double sizeRatio=GAME_HEIGHT/(difPosYR*1.0*depthRatio+GAME_HEIGHT);
        if(difPosYR<sizeRatioValue){
            sizeRatio=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosYR-sizeRatioValue)+GAME_HEIGHT/depthRatio;

        }
        double newPosYR=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        double newPosXR=  (GameGrid.PVX-((Player.xPosition-xPositionA)*width)*sizeRatio-(width*sizeRatio)/2);

        if (Math.abs(difPosXR)<=width/2.0&&newPosY>GAME_HEIGHT*2&&difPosZ<0){
            {drawCube=false;return new double[3];}
        }

        return new double[]{newPosXR, newPosYR, sizeRatio};

    }

    private void getCorners(){
        double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;
        depthRatio=GameGrid.depthRatio;
        double[] info0=getPosOtherBlock(0,0,sizeRatioValue);
        double[] info1=getPosOtherBlock(1,0,sizeRatioValue);
        double[] info2=getPosOtherBlock(1,-1,sizeRatioValue);
        double[] info3=getPosOtherBlock(0,-1,sizeRatioValue);
        sizeRatio=info0[2];
        newPosY=info0[1];
        newHeight=info0[2]*height;
        if(info0[1]+newHeight<0){drawCube=false;return;}
        if(info0[1]>GAME_HEIGHT*2){drawCube=false;return;}
        if(info0[0] >GAME_WIDTH*2){drawCube=false;return;}
        if(info0[0] <-GAME_WIDTH){drawCube=false;return;}
        corners[0][1]=newPosY;
        corners[1][1]=info1[1];
        corners[2][1]=info2[1];;
        corners[3][1]=info3[1];;
        corners[4][1]=newPosY-height*sizeRatio;
        corners[5][1]=info1[1]-height*info1[2];
        corners[6][1]=info2[1]-height*info2[2];;
        corners[7][1]=info3[1]-height*info3[2];;

        corners[0][0]=info0[0];
        corners[1][0]=info1[0];
        corners[2][0]=info2[0];
        corners[3][0]=info3[0];
        corners[4][0]=info0[0];
        corners[5][0]=info1[0];
        corners[6][0]=info2[0];
        corners[7][0]=info3[0];
    }
    static public void fillPolygonB1(Graphics g,int[] listX,int[] listY,Color color){
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

/*
        double[][] cornersP= new double[4][2];
        for(var i=0;i<4;i++){
            cornersP[i][0]=listX[i];
            cornersP[i][1]=listY[i];
        }

 */






        for(var i=0;i<4;i++) {
            int xNum = 1 + i;
            if (xNum == 4) xNum = 0;
            g.drawLine(listX[i],listY[i],listX[xNum],listY[xNum]);
        }

    }
    private  void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color,int type){
        fillPolygonB(g, listX,listY, color,"side",type);
    }
    static private  void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color,String side,int type){

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


        // g.setColor(Color.red);
        /*
        g.fillOval( newListX2[0]-5, newListY2[0]-5,10,10);
        g.fillOval( newListX2[1]-5, newListY2[1]-5,10,10);
        g.fillOval( newListX2[2]-5, newListY2[2]-5,10,10);
        g.fillOval( newListX2[3]-5, newListY2[3]-5,10,10);

        */
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
        else if(type!=0||!side.equals("top")){
            g.setColor(colorDarken);
            g.fillPolygon(listX,listY,4);
            g.setColor(color);
            g.fillPolygon(newListX,newListY,4);

            g.setColor(colorDarkenForLines);
            g.drawPolygon(listX,listY,4);
            g.setColor(colorForLines);
            g.drawPolygon(newListX,newListY,4);


        }else{
           // if(true)return;
            int[][][] l3D=getDivisionInPolygon(listX,listY,4);
            g.setColor(color);
            g.fillPolygon(listX,listY,4);


            g.setColor(darkenColor(colorBrighter,100));
            g.drawPolygon(listX,listY,4);
            /*
              g.setColor(colorBrighter);
            g.fillPolygon(new int[]{listX[0], l3D[0][0][0], l3D[0][2][3],listX[3]},new int[]{listY[0], l3D[1][0][0], l3D[1][2][3],listY[3]},4);
            g.fillPolygon(new int[]{ l3D[0][0][1], l3D[0][0][2], l3D[0][2][1],l3D[0][2][2]},new int[]{ l3D[1][0][1], l3D[1][0][2], l3D[1][2][1],l3D[1][2][2]},4);
            g.fillPolygon(new int[]{l3D[0][0][3], l3D[0][2][0], listX[2],listX[1]},new int[]{l3D[1][0][3], l3D[1][2][0], listY[2],listY[1]},4);
            g.setColor(color);
            g.fillPolygon(new int[]{l3D[0][3][3], l3D[0][1][0],l3D[0][1][1],l3D[0][3][2]},new int[]{l3D[1][3][3], l3D[1][1][0],l3D[1][1][1],l3D[1][3][2]},4);
            g.fillPolygon(new int[]{l3D[0][3][1], l3D[0][1][2],l3D[0][1][3],l3D[0][3][0]},new int[]{l3D[1][3][1], l3D[1][1][2],l3D[1][1][3],l3D[1][3][0]},4);

             */
           // g.fillPolygon(new int[]{listX[0], l3D[0][0][0], l3D[0][2][2],listX[3]},new int[]{listY[0], l3D[1][0][0], l3D[1][2][2],listY[3]},4);
           // g.fillPolygon(new int[]{ l3D[0][0][0], l3D[0][2][2], l3D[0][2][1],l3D[0][0][1]},new int[]{l3D[1][0][0], l3D[1][2][2], l3D[1][2][1],l3D[1][0][1]},4);
           // g.fillPolygon(new int[]{l3D[0][0][2], l3D[0][2][0], listX[2],listX[1]},new int[]{l3D[1][0][2], l3D[1][2][0], listY[2],listY[1]},4);
           // g.fillPolygon(new int[]{ l3D[0][0][1], l3D[0][0][2], l3D[0][2][0],l3D[0][2][1]},new int[]{ l3D[1][0][1], l3D[1][0][2], l3D[1][2][0],l3D[1][2][1]},4);

            //g.fillPolygon(new int[]{l3D[0][3][2], l3D[0][1][0],l3D[0][1][1],l3D[0][3][1]},new int[]{l3D[1][3][2], l3D[1][1][0],l3D[1][1][1],l3D[1][3][1]},4);
            //g.fillPolygon(new int[]{l3D[0][3][0],l3D[0][1][2],listX[2],listX[3]},new int[]{l3D[1][3][0],l3D[1][1][2],listY[2],listY[3]},4);

        }
    }
    /*static private  void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color,String side,int type){
        g.setColor(color);
        g.fillPolygon(listX,listY,4);
        g.setColor(Color.black);
        g.drawPolygon(listX,listY,4);

    }

     */
    static int[][] getSmallerCorners(int[] listX,int[] listY,double num){
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

    static int[][][] getDivisionInPolygon(int[] listX,int[] listY,int num){
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
    static  Color darkenColor(Color color, int num){
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
