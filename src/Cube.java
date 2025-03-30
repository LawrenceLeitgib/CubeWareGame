import java.awt.*;

public class Cube {
    int xPosition;
    int yPosition;


    int zPosition;
    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static int defaultSize=100;
    int width=defaultSize;
    int height=defaultSize;
    int depth=defaultSize;
    static double depthRatio=1/2.0;

    boolean blockLeftEmpty=true;
    boolean blockRightEmpty=true;
    boolean blockFrontEmpty=true;
    boolean blockBackEmpty=true;

    boolean blockTopEmpty=true;
    boolean blockBottomEmpty=true;
    double[] deltaXList=new double[4];
    double[] deltaYList=new double[4];
    double[] angleList=new double[4];
    double newPosY;
    double newWidth;
    double newHeight;
    double newDepth;
    double newPosX;
    double sizeRatio;
    double[][] corners=new double[8][2];
    int[][] xPointsList;
    int[][] yPointsList;
    int closestCorner;
    double[][] newCorners=new double[4][2];

    static double angleForXRotation=0.0;


    int countForDrawing=0;



    boolean drawCube=true;

    Color color;

    Chunk chunk;

    static int xAddingNumber=0;

    double xPositionA;
    double yPositionA;

    int[][] polygonTop=new int[2][4];
    int[][] polygonBottom=new int[2][4];
    int[][] polygonLeft=new int[2][4];
    int[][] polygonRight=new int[2][4];
    int[][] polygonBack =new int[2][4];
    int[][] polygonFront =new int[2][4];

    double xCorrectorForRotation=.5;
    double yCorrectorForRotation=.5;








    Cube(int xPosition, int yPosition, int zPosition, double depthRatio,Chunk chunk,Color color,int height){
        this.xPosition=xPosition;
        this.yPosition=yPosition;
        this.zPosition=zPosition;
        Cube.depthRatio=depthRatio;
        this.color=color;
        this.height=height;
        this.chunk=chunk;
        this.xPositionA=xPosition;
        this.yPositionA=yPosition;

    }
    Cube(int xPosition, int yPosition, int zPosition, double depthRatio, Chunk chunk){
        this(xPosition,  yPosition, zPosition, depthRatio, chunk,new Color(7, 252, 3),defaultSize);
    }

    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }
    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;

    }



    public void updateData(double deltaTime) {
        depthRatio=GameGrid.depthRatio;
        // angleForXRotation+=0.06;
        //System.out.println(angleForXRotation);

      // angleForXRotation=Math.PI/2;
        while(angleForXRotation>=Math.PI*2){
            angleForXRotation-=Math.PI*2;
        }
        while(angleForXRotation<0){
            angleForXRotation+=Math.PI*2;
        }
        //System.out.println(angleForXRotation);

        double difPosXA=(xPosition-xCorrectorForRotation-Player.xPosition);
        double difPosYA= (yPosition+yCorrectorForRotation-Player.yPosition);



        xPositionA= (Player.xPosition+difPosXA*Math.cos(angleForXRotation)+difPosYA*Math.sin(angleForXRotation)+xCorrectorForRotation);
        yPositionA=  (Player.yPosition-difPosXA*Math.sin(angleForXRotation)+difPosYA*Math.cos(angleForXRotation)-yCorrectorForRotation);
        //System.out.println(difPosXA+" "+difPosYA);
      //  System.out.println(xPositionA+" "+yPositionA);
        //System.out.println(xPosition+" "+yPosition);
        //System.out.println(angleForXRotation);



        double difPosX=((Player.xPosition-xPosition)*width);
        double difPosY= ((Player.yPosition-(yPosition-Player.cubeAway))*depth);

        double difPosXR=((Player.xPosition-xPositionA)*width);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*depth);

       // System.out.println(xPositionA);
       // System.out.println(Math.sqrt(Math.pow(difPosXA*Math.cos(angleForXRotation)+difPosYA*Math.sin(angleForXRotation),2)+Math.pow(difPosXA*Math.sin(angleForXRotation)+difPosYA*Math.cos(angleForXRotation),2)));
        //difPosX=((Player.xPosition-xPosition)*width)*Math.cos(angleForXRotation)+((Player.yPosition-(yPosition-Player.cubeAway))*depth)*Math.sin(angleForXRotation);
        //difPosY= ((Player.yPosition-(yPosition-Player.cubeAway))*depth)*Math.cos(angleForXRotation)+((Player.xPosition-xPosition)*width)*Math.sin(angleForXRotation);


        double difPosZ=((Player.zPosition-zPosition)*height);
        double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;

      //sizeRatioValue=(depthRatio+(GameGrid.PVY-GameGrid.PFY))/depthRatio;
        sizeRatio=GAME_HEIGHT/(difPosYR*1.0*depthRatio+GAME_HEIGHT);
         if(difPosYR<sizeRatioValue){
            sizeRatio=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosY-sizeRatioValue)+GAME_HEIGHT/depthRatio;

         }
         newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);

         //System.out.println(newPosY);

         newWidth=  (width*sizeRatio);
         newHeight=  (height*sizeRatio);
         newDepth=  (depth*sizeRatio);
         newPosX=  (GameGrid.PVX-((Player.xPosition-xPositionA)*width)*sizeRatio-newWidth/2);
        //newPosX=  (GameGrid.PVX*2*Math.cos(angleForXRotation)-((Player.xPosition-xPosition)*width)*sizeRatio);


       // double deltaXForRotation=Player.newPosX-newPosX;
       // double deltaYForRotation=Player.newPosY-newPosY;

      //   double deltaXForRotation=GAME_WIDTH/2.0-newPosX;
      //   double deltaYForRotation=GAME_HEIGHT-newPosY;

   //     double distanceForRotation=Math.sqrt(Math.pow(deltaXForRotation,2)+Math.pow(deltaYForRotation,2));


    //    double AngleForRotation=Math.atan(deltaXForRotation/deltaYForRotation);
    //    if(deltaYForRotation>0)AngleForRotation=-Math.PI+AngleForRotation;
      //  if(deltaYForRotation<0)AngleForRotation=Math.PI+AngleForRotation;

      //  if(deltaYForRotation>0&&deltaXForRotation<0)AngleForRotation=2*Math.PI+AngleForRotation;

      //  System.out.println(AngleForRotation+" "+deltaYForRotation+" "+deltaXForRotation);

       // System.out.println(distanceForRotation);

        //newPosX=GAME_WIDTH/2.0+Math.sin(AngleForRotation+angleForXRotation)*distanceForRotation;
        //newPosY=GAME_HEIGHT+Math.cos(AngleForRotation+angleForXRotation)*distanceForRotation;



         if(newPosY+newHeight<0)return;
        if(newPosY>GAME_HEIGHT*2)return;
        if(newPosX>GAME_WIDTH*2)return;
        if(newPosX<-GAME_WIDTH)return;

        if (Math.abs(difPosX)<=width/2.0&&newPosY>GAME_HEIGHT*2&&difPosZ<0){
            drawCube=false;
        }else(drawCube)=true;

        if(newPosY>GAME_HEIGHT*10)drawCube=false;

        corners=getCorners(newPosX,newPosY,newWidth,newHeight,difPosZ,difPosXA,difPosYA);





        int leftPosCheck=xPosition-1-chunk.chunkToNormNumX;
        int rightPosCheck=xPosition+1-chunk.chunkToNormNumX;
        int frontPosCheck=yPosition-1-chunk.chunkToNormNumY;
        int backPosCheck=yPosition+1-chunk.chunkToNormNumY;
        int topPosCheck=zPosition+1;
        int bottomPosCheck=zPosition-1;

        //countForDrawing=0;
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
            if(chunk.cubePositions[rightPosCheck][yPosition-chunk.chunkToNormNumY][zPosition]){
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
            if(chunk.cubePositions[xPosition-chunk.chunkToNormNumX][backPosCheck][zPosition]) {
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
            if(chunk.cubePositions[xPosition-chunk.chunkToNormNumX][yPosition-chunk.chunkToNormNumY][topPosCheck]){
                blockTopEmpty=false;
                countForDrawing++;
            }
        }
        if(bottomPosCheck>=0){
            if(chunk.cubePositions[xPosition-chunk.chunkToNormNumX][yPosition-chunk.chunkToNormNumY][bottomPosCheck]){
                blockBottomEmpty=false;
                countForDrawing++;
            }
        }
    }
    public void draw(Graphics g) {

       // g.setColor(Color.WHITE);
       // g.fillOval((int) (newPosX-5+newWidth/2), (int) (newPosY-5),10,10);
        /*
        g.setColor(Color.yellow);
        g.fillOval((int) (corners[0][0]-5), (int) (corners[0][1]-5),10,10);
        g.setColor(Color.green);
        g.fillOval((int) (corners[1][0]-5), (int) (corners[1][1]-5),10,10);
        g.setColor(Color.red);
        g.fillOval((int) (corners[2][0]-5), (int) (corners[2][1]-5),10,10);
        g.setColor(Color.blue);
        g.fillOval((int) (corners[3][0]-5), (int) (corners[3][1]-5),10,10);


         */

        if(newPosY+newHeight<0)return;
        if(newPosY>GAME_HEIGHT*2)return;
        if(newPosX>GAME_WIDTH*2)return;
        if(newPosX<-GAME_WIDTH)return;

        if(countForDrawing==6)return;

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

/*

        g.setColor(Color.blue);
        g.fillOval((int) (corners[0][0]-5), (int) (corners[0][1]-5),10,10);
        g.fillOval((int) (corners[1][0]-5), (int) (corners[1][1]-5),10,10);
        g.fillOval((int) (corners[2][0]-5), (int) (corners[2][1]-5),10,10);
        g.fillOval((int) (corners[3][0]-5), (int) (corners[3][1]-5),10,10);

        g.setColor(Color.black);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[1][0], (int) corners[1][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[2][0], (int) corners[2][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[3][0], (int) corners[3][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[0][0], (int) corners[0][1]);



        g.setColor(Color.black);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[4][0], (int) corners[4][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[7][0], (int) corners[7][1]);

        g.setColor(Color.red);
        g.fillOval((int) (corners[4][0]-5), (int) (corners[4][1]-5),10,10);
        g.fillOval((int) (corners[5][0]-5), (int) (corners[5][1]-5),10,10);
        g.fillOval((int) (corners[6][0]-5), (int) (corners[6][1]-5),10,10);
        g.fillOval((int) (corners[7][0]-5), (int) (corners[7][1]-5),10,10);


        g.setColor(Color.BLACK);
        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);
*/
/*
        fillPolygonB(g,polygonBottom[0],polygonBottom[1] );
        fillPolygonB(g,polygonBack[0],polygonBack[1] );
        fillPolygonB(g,polygonLeft[0],polygonLeft[1] );
        fillPolygonB(g,polygonRight[0],polygonRight[1] );
        fillPolygonB(g,polygonTop[0],polygonTop[1] );
*/




        g.setColor(new Color(21, 92, 5));
        if (drawCube){

            if(angleForXRotation<Math.PI/4) {
                if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],Color.PINK  );
                if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],Color.YELLOW  );
                if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],Color.CYAN );
            }
            else if(angleForXRotation<Math.PI/2) {
                if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],Color.blue );
                if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],Color.CYAN );
                if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],Color.YELLOW  );
            }
            else if(angleForXRotation<3*Math.PI/4) {
                if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],Color.CYAN );
                if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],Color.blue );
                if (blockLeftEmpty)   fillPolygonB(g,polygonLeft[0],polygonLeft[1],Color.YELLOW  );
            }
            else if(angleForXRotation<Math.PI) {
                if (blockRightEmpty)  fillPolygonB(g,polygonRight[0],polygonRight[1],Color.PINK  );
                if (blockLeftEmpty) fillPolygonB(g,polygonLeft[0],polygonLeft[1],Color.YELLOW  );
                if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],Color.blue );

            }
            else if(angleForXRotation<5*Math.PI/4) {

                if (blockLeftEmpty) fillPolygonB(g,polygonLeft[0],polygonLeft[1],Color.YELLOW  );
                if (blockRightEmpty)   fillPolygonB(g,polygonRight[0],polygonRight[1],Color.PINK  );
                if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],Color.blue );
            }
            else if(angleForXRotation<3*Math.PI/2) {
                if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],Color.CYAN );
                if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],Color.blue );
                if (blockRightEmpty)    fillPolygonB(g,polygonRight[0],polygonRight[1],Color.PINK  );

            }
            else if(angleForXRotation<7*Math.PI/4) {
                if (blockFrontEmpty)fillPolygonB(g, polygonFront[0], polygonFront[1],Color.blue );
                if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],Color.CYAN );
                if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],Color.PINK  );
            }
            else if(angleForXRotation<2*Math.PI) {
                if (blockLeftEmpty)fillPolygonB(g,polygonLeft[0],polygonLeft[1],Color.YELLOW  );
                if (blockRightEmpty) fillPolygonB(g,polygonRight[0],polygonRight[1],Color.PINK  );
                if (blockBackEmpty)fillPolygonB(g, polygonBack[0], polygonBack[1],Color.CYAN );


            }

            if (blockTopEmpty&&newPosY<GameGrid.PFY) {
                fillPolygonB(g,polygonBottom[0],polygonBottom[1] ,Color.darkGray );
            }

            if (blockTopEmpty&&newPosY-newHeight>GameGrid.PFY) {
                fillPolygonB(g,polygonTop[0],polygonTop[1],Color.GREEN  );

            }


    }

        g.setColor(Color.WHITE);
       // System.out.println(angleForXRotation);
      //  g.fillOval((int) (newPosX-5+newWidth/2), (int) (newPosY-5),10,10);

        /*
        //  g.setColor(Color.red);
        //g.fillOval((int) (newPosX-5), (int) (newPosY-5),10,10);
       // g.setColor(Color.yellow);
      //  g.fillOval((int) (corners[closestCorner][0]-5), (int) (corners[closestCorner][1]-5),10,10);
        g.fillOval((int) (corners[0][0]-5), (int) (corners[0][1]-5),10,10);
        g.fillOval((int) (corners[1][0]-5), (int) (corners[1][1]-5),10,10);
        g.fillOval((int) (corners[2][0]-5), (int) (corners[2][1]-5),10,10);
        g.fillOval((int) (corners[3][0]-5), (int) (corners[3][1]-5),10,10);

        g.setColor(Color.yellow);
        g.fillOval((int) (newCorners[2][0]-5), (int) (newCorners[2][1]-5),10,10);
        g.fillOval((int) (newCorners[3][0]-5), (int) (newCorners[3][1]-5),10,10);
        g.fillOval((int) (newCorners[0][0]-5), (int) (newCorners[0][1]-5),10,10);
        g.fillOval((int) (newCorners[1][0]-5), (int) (newCorners[1][1]-5),10,10);

       // g.setColor(Color.yellow);

        //g.fillOval((int) (corners[0][0]-5), (int) (corners[0][1]-5),10,10);
        //g.fillOval((int) (corners[1][0]-5), (int) (corners[1][1]-5),10,10);
     //   g.fillOval((int) (corners[2][0]-5), (int) (corners[2][1]-5),10,10);
      //  g.fillOval((int) (corners[3][0]-5), (int) (corners[3][1]-5),10,10);

         */



    }
    public int[] listDoubleToInt(double[] l){
        int[] newList= new int[l.length];
        for(var i=0;i<l.length;i++){
            newList[i]= (int) l[i];
        }
        return newList;
    }
    public double[][] getCorners(double newPosX,double newPosY,double newWidth,double newHeight,double difPosZ,double difPosXA,double difPosYA){
        double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;

        double difPosXARight=(xPosition+1-xCorrectorForRotation-Player.xPosition);
        double yPositionARight=  (Player.yPosition-difPosXARight*Math.sin(angleForXRotation)+difPosYA*Math.cos(angleForXRotation)-yCorrectorForRotation);
        double xPositionARight=  (Player.xPosition+difPosXARight*Math.cos(angleForXRotation)+difPosYA*Math.sin(angleForXRotation)+xCorrectorForRotation);;
        double difPosYRRight= ((Player.yPosition-(yPositionARight-Player.cubeAway))*depth);
        double sizeRatioRight=GAME_HEIGHT/(difPosYRRight*1.0*depthRatio+GAME_HEIGHT);


        if(difPosYRRight<sizeRatioValue){
            sizeRatioRight=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosYRRight-sizeRatioValue)+GAME_HEIGHT/depthRatio;

        }
        double newPosYRight=((GameGrid.PVY-GameGrid.PFY)*sizeRatioRight+GameGrid.PFY+difPosZ*sizeRatioRight);
        double newPosXRight=  (GameGrid.PVX-((Player.xPosition-xPositionARight)*width)*sizeRatioRight-(width*sizeRatioRight)/2);

        double difPosYAFront=(yPosition-1+yCorrectorForRotation-Player.yPosition);
        double yPositionAFront=  (Player.yPosition-difPosXA*Math.sin(angleForXRotation)+difPosYAFront*Math.cos(angleForXRotation)-yCorrectorForRotation);
        double xPositionAFront=  (Player.xPosition+difPosXA*Math.cos(angleForXRotation)+difPosYAFront*Math.sin(angleForXRotation)+xCorrectorForRotation);
        double difPosYRFront= ((Player.yPosition-(yPositionAFront-Player.cubeAway))*depth);
        double sizeRatioFront=GAME_HEIGHT/(difPosYRFront*1.0*depthRatio+GAME_HEIGHT);

        if(difPosYRFront<sizeRatioValue){
            sizeRatioFront=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosYRFront-sizeRatioValue)+GAME_HEIGHT/depthRatio;

        }

        double newPosYFront=((GameGrid.PVY-GameGrid.PFY)*sizeRatioFront+GameGrid.PFY+difPosZ*sizeRatioFront);
        double newPosXFront=  (GameGrid.PVX-((Player.xPosition-xPositionAFront)*width)*sizeRatioFront-(width*sizeRatioFront)/2);


        double yPositionAFrontRight=  (Player.yPosition-difPosXARight*Math.sin(angleForXRotation)+difPosYAFront*Math.cos(angleForXRotation)-yCorrectorForRotation);
        double xPositionAFrontRight=  (Player.xPosition+difPosXARight*Math.cos(angleForXRotation)+difPosYAFront*Math.sin(angleForXRotation)+xCorrectorForRotation);
        double difPosYRFrontRight= ((Player.yPosition-(yPositionAFrontRight-Player.cubeAway))*depth);
        double sizeRatioFrontRight=GAME_HEIGHT/(difPosYRFrontRight*1.0*depthRatio+GAME_HEIGHT);
        if(difPosYRFrontRight<sizeRatioValue){
            sizeRatioFrontRight=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosYRFrontRight-sizeRatioValue)+GAME_HEIGHT/depthRatio;

        }


        double newPosYFrontRight=((GameGrid.PVY-GameGrid.PFY)*sizeRatioFrontRight+GameGrid.PFY+difPosZ*sizeRatioFrontRight);
        double newPosXFrontRight=  (GameGrid.PVX-((Player.xPosition-xPositionAFrontRight)*width)*sizeRatioFrontRight-(width*sizeRatioFrontRight)/2);







        //if(xPosition==5)System.out.println(deltaYRight);






        double [][] corners=new double[8][2];
        corners[0][1]=newPosY;
        corners[1][1]=newPosYRight;
        corners[2][1]=newPosYFrontRight;
        corners[3][1]=newPosYFront;

        corners[4][1]=newPosY-newHeight;
        corners[5][1]=newPosYRight-height*sizeRatioRight;
        corners[6][1]=newPosYFrontRight-height*sizeRatioFrontRight;
        corners[7][1]=newPosYFront-height*sizeRatioFront;


        corners[0][0]=newPosX;
        corners[1][0]=newPosXRight;
        corners[2][0]=newPosXFrontRight;
        corners[3][0]=newPosXFront;

        corners[4][0]=newPosX;
        corners[5][0]=newPosXRight;
        corners[6][0]=newPosXFrontRight;
        corners[7][0]=newPosXFront;




        return corners;
    }
    public void fillPolygonB(Graphics g,int[] listX,int[] listY,Color color){
        g.setColor(new Color(21, 92, 5));
        g.setColor(color);
        g.fillPolygon(listX,listY,4);
        g.setColor(Color.BLACK);
        double[][] cornersP= new double[4][2];
        for(var i=0;i<4;i++){
            cornersP[i][0]=listX[i];
            cornersP[i][1]=listY[i];
        }






        for(var i=0;i<4;i++) {
            int xNum = 1 + i;
            if (xNum == 4) xNum = 0;
            g.drawLine(listX[i],listY[i],listX[xNum],listY[xNum]);
        }

    }



}
