import java.awt.*;

public class Cube {
    int xPosition;
    int yPosition;
    int zPosition;
    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static int defaultSize=50;
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
    double[][] corners;
    int[][] xPointsList;
    int[][] yPointsList;
    int closestCorner;
    double[][] newCorners;



    boolean drawCube=true;

    Color color;

    Chunk chunk;


    Cube(int xPosition, int yPosition, int zPosition, double depthRatio,Chunk chunk,Color color,int height){
        this.xPosition=xPosition;
        this.yPosition=yPosition;
        this.zPosition=zPosition;
        Cube.depthRatio=depthRatio;
        this.color=color;
        this.height=height;
        this.chunk=chunk;

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
        double difPosX=((Player.xPosition-xPosition)*width);
        double difPosY= ((Player.yPosition-(yPosition-Player.cubeAway))*depth);
        double difPosZ=((Player.zPosition-zPosition)*height);
        double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;
         sizeRatio=GAME_HEIGHT/(difPosY*1.0*depthRatio+GAME_HEIGHT);
         if(difPosY<sizeRatioValue){
             sizeRatio=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosY-sizeRatioValue)+GAME_HEIGHT/depthRatio;

         }
         newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
         newWidth=  (width*sizeRatio);
         newHeight=  (height*sizeRatio);
         newDepth=  (depth*sizeRatio);
         newPosX=  (GameGrid.PVX-((Player.xPosition-xPosition)*width)*sizeRatio);

        if (Math.abs(difPosX)<=width/2&&newPosY>GAME_HEIGHT*2&&difPosZ<0){
            drawCube=false;
        }else(drawCube)=true;
        if(newPosY>GAME_HEIGHT*10)drawCube=false;


        corners=getCorners(newPosX,newPosY,newWidth,newHeight);
        angleList=getAngleList();

        xPointsList = new int[4][4];
        yPointsList = new int[4][4];
        closestCorner=closestCorner(corners);
        newCorners=getCornersB(corners,angleList,closestCorner,depthRatio,difPosZ,difPosY);
        for(var i=0;i<4;i++){
            int xNum=1+i;
            if (xNum==4)xNum=0;
            xPointsList[i]=listDoubleToInt(new double[] {corners[i][0],newCorners[i][0],
                    newCorners[xNum][0],corners[xNum][0]});
            yPointsList[i]=listDoubleToInt(new double[]{corners[i][1],newCorners[i][1],
                    newCorners[xNum][1],corners[xNum][1]});
        }
        int leftPosCheck=xPosition-1-chunk.chunkToNormNumX;
        int rightPosCheck=xPosition+1-chunk.chunkToNormNumX;
        int frontPosCheck=yPosition-1-chunk.chunkToNormNumY;
        int backPosCheck=yPosition+1-chunk.chunkToNormNumY;
        int topPosCheck=zPosition+1;
        int bottomPosCheck=zPosition-1;
        if(leftPosCheck>=0) {
            if (chunk.cubePositions[leftPosCheck][yPosition - chunk.chunkToNormNumY][zPosition]) {
                blockLeftEmpty = false;

            }
        }
        else {
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition - 1][CubeContainer.numOfChunkY + chunk.yPosition]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition - 1][CubeContainer.numOfChunkY + chunk.yPosition].cubePositions[leftPosCheck + Chunk.numOfCubeX][yPosition - chunk.chunkToNormNumY][zPosition]) {
                    blockLeftEmpty = false;
                }
            }
        }

        if(rightPosCheck<Chunk.numOfCubeX) {
            if(chunk.cubePositions[rightPosCheck][yPosition-chunk.chunkToNormNumY][zPosition]){
                blockRightEmpty=false;
            }
        }
        else {
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition +1][CubeContainer.numOfChunkY + chunk.yPosition]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition +1][CubeContainer.numOfChunkY + chunk.yPosition].cubePositions[rightPosCheck - Chunk.numOfCubeX][yPosition - chunk.chunkToNormNumY][zPosition]) {
                    blockRightEmpty = false;
                }
            }
        }

        if(frontPosCheck>=0) {
            if (chunk.cubePositions[xPosition - chunk.chunkToNormNumX][frontPosCheck][zPosition]) {
                blockFrontEmpty = false;
            }
        }
        else{
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition-1]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition-1].cubePositions[xPosition - chunk.chunkToNormNumX][frontPosCheck+Chunk.numOfCubeY][zPosition]) {
                    blockFrontEmpty = false;
                }
            }

        }
        if(backPosCheck<Chunk.numOfCubeY){
            if(chunk.cubePositions[xPosition-chunk.chunkToNormNumX][backPosCheck][zPosition]) {
                blockBackEmpty = false;
            }
        }
        else{
            if (CubeContainer.chunksPosition[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition+1]) {
                if (CubeContainer.chunks[CubeContainer.numOfChunkX + chunk.xPosition][CubeContainer.numOfChunkY + chunk.yPosition+1].cubePositions[xPosition - chunk.chunkToNormNumX][backPosCheck-Chunk.numOfCubeY][zPosition]) {
                    blockBackEmpty = false;
                }
            }

        }

        if(topPosCheck<Chunk.numOfCubeZ){
            if(chunk.cubePositions[xPosition-chunk.chunkToNormNumX][yPosition-chunk.chunkToNormNumY][topPosCheck]){
                blockTopEmpty=false;
            }
        }

        if(bottomPosCheck>=0){
            if(chunk.cubePositions[xPosition-chunk.chunkToNormNumX][yPosition-chunk.chunkToNormNumY][bottomPosCheck]){
                blockBottomEmpty=false;
            }
        }
    }
    public void draw(Graphics g) {



        if (blockBackEmpty && drawCube) {
            g.setColor(Color.BLACK);
            g.fillRect((int) (corners[0][0] - 1), (int) (corners[0][1] - 1), (int) (newWidth + 2), (int) (newHeight + 2));
            g.setColor(color);
            g.fillRect((int) (corners[0][0]), (int) (corners[0][1]), (int) (newWidth), (int) (newHeight));

        }

        /*
        g.setColor(Color.red);
        g.setFont(new Font("Arial",Font.PLAIN,18));
        g.drawString(String.valueOf((int)newPosY),15,(int)newPosY*2-GAME_HEIGHT);

        g.setColor(Color.red);
        g.drawString(String.valueOf(angleList[0]),15,110);
        g.drawString(String.valueOf(angleList[1]),15,130);
        g.drawString(String.valueOf(angleList[2]),15,150);
        g.drawString(String.valueOf(angleList[3]),15,170);

         */
        /*
        int[][] xPointsList = new int[4][4];
        int[][] yPointsList = new int[4][4];
        int closestCorner=closestCorner(corners);
        double[][] newCorners=getCornersB(corners,angleList,closestCorner,depthRatio,difPosY,newWidth,newHeight,newDepth);
        for(var i=0;i<4;i++){
            int xNum=1+i;
            if (xNum==4)xNum=0;
            xPointsList[i]=listDoubleToInt(new double[] {corners[i][0],newCorners[i][0],
                    newCorners[xNum][0],corners[xNum][0]});
            yPointsList[i]=listDoubleToInt(new double[]{corners[i][1],newCorners[i][1],
                    newCorners[xNum][1],corners[xNum][1]});
        }

         */

        g.setColor(new Color(21, 92, 5));
        if (drawCube){
            if (deltaYList[0] > 0 && blockTopEmpty) {
                fillPolygonB(g, xPointsList[0], yPointsList[0], closestCorner);
                g.setColor(Color.RED);
                //g.drawLine((int) corners[0][0], (int) corners[0][1], (int) PFX, (int) PFY);
                //g.drawLine((int) corners[1][0], (int) corners[1][1], (int) PFX, (int) PFY);

            }
        if (deltaYList[2] < 0 && blockBottomEmpty) {
            fillPolygonB(g, xPointsList[2], yPointsList[2], closestCorner);
            g.setColor(Color.RED);
            //g.drawLine((int) corners[2][0], (int) corners[2][1], (int) PFX, (int) PFY);
            //g.drawLine((int) corners[3][0], (int) corners[3][1], (int) PFX, (int) PFY);
        }
        if (deltaXList[0] > 0 && blockLeftEmpty) {
            fillPolygonB(g, xPointsList[3], yPointsList[3], closestCorner);
            g.setColor(Color.RED);
            //g.drawLine((int) corners[0][0], (int) corners[0][1], (int) PFX, (int) PFY);
            //g.drawLine((int) corners[3][0], (int) corners[3][1], (int) PFX, (int) PFY);
        }
        if (deltaXList[1] < 0 && blockRightEmpty) {
            fillPolygonB(g, xPointsList[1], yPointsList[1], closestCorner);
            g.setColor(Color.RED);
            //g.drawLine((int) corners[1][0], (int) corners[1][1], (int) PFX, (int) PFY);
            //g.drawLine((int) corners[2][0], (int) corners[2][1], (int) PFX, (int) PFY);
        }
    }
        /*
        int[] newCornersListX= new int[4];
        int[] newCornersListY=new int[4];

        for(int i=0;i<4;i++){
            newCornersListX[i]= (int) newCorners[i][0];
            newCornersListY[i]= (int) newCorners[i][1];
        }
        g.setColor(Color.YELLOW);
        g.fillPolygon(newCornersListX,newCornersListY,4);
        g.fillOval((int) (corners[closestCorner][0]-5), (int) (corners[closestCorner][1]-5),10,10);
    */
        /*
        g.setColor(Color.yellow);
        g.setColor(Color.red);
        g.fillOval((int) (newCorners[0][0]-5), (int) (newCorners[0][1]-5),10,10);
        g.fillOval((int) (newCorners[1][0]-5), (int) (newCorners[1][1]-5),10,10);
        g.fillOval((int) (newCorners[2][0]-5), (int) (newCorners[2][1]-5),10,10);
        g.fillOval((int) (newCorners[3][0]-5), (int) (newCorners[3][1]-5),10,10);

         */



    }
    public int[] listDoubleToInt(double[] l){
        int[] newList= new int[l.length];
        for(var i=0;i<l.length;i++){
            newList[i]= (int) l[i];
        }
        return newList;
    }
    public double[][] getCorners(double newPosX,double newPosY,double newWidth,double newHeight){
        double [][] corners=new double[4][2];
        corners[0][1]=newPosY-newHeight;
        corners[1][1]=newPosY-newHeight;
        corners[2][1]=newPosY;
        corners[3][1]=newPosY;


        corners[0][0]=newPosX-newWidth/2.0;
        corners[1][0]=newPosX+newWidth/2.0;
        corners[2][0]=newPosX+newWidth/2.0;
        corners[3][0]=newPosX-newWidth/2.0;




        return corners;
    }
    public double[] getAngleList() {
        for (var i = 0; i < 4; i++) {
            deltaXList[i] = corners[i][0] - GameGrid.PFX;
            deltaYList[i] = corners[i][1] - GameGrid.PFY;
            angleList[i] = Math.atan(deltaYList[i] / deltaXList[i]);

            if (deltaXList[i] > 0 && deltaYList[i] < 0) {
                angleList[i] = Math.PI * 2 + Math.atan(deltaYList[i] / deltaXList[i]);
            } else if (deltaXList[i] < 0) angleList[i] = Math.PI + Math.atan(deltaYList[i] / deltaXList[i]);
            if (deltaXList[i] == 0 && deltaYList[i] < 0) angleList[i] = 3 * Math.PI / 2;
            if (deltaXList[i] == 0 && deltaYList[i] > 0) angleList[i] = Math.PI / 2;
        }
        return angleList;
    }

    public double[][] getCornersB(double[][] corners, double[] angleList, int closest, double depthRatio,double difPosZ,double difPosY){
            double[][] newCorners = new double[4][2];
            //double[][] newGroundCorners = new double[4][2];
            int a1;
            int a2;
            int a3;
            int a4;

            int xSign=1;
            int ySign=1;
            if(closest==2 || closest==1)xSign=0;
            if(closest==0 || closest==3)ySign=0;
            int upSign=1;
            //if(closest==2 || closest==3)upSign=-1;



            if (closest==1||closest==2){a1=2;a2=3;a3=0;a4=1;}
            //else if (closest==2){a1=1;a2=2;a3=3;a4=0;interestingCorner=2;}
            //else if (closest==3){a1=0;a2=1;a3=2;a4=3;interestingCorner=3;}
            else  {a1=3;a2=0;a3=1;a4=2;}




            //double DeltaY=(2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((difPosY)*1.0*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0)-(2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((difPosY+depth)*1.0*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0);


            double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;



            double newSizeRatio=GAME_HEIGHT/((difPosY+depth)*1.0*depthRatio+GAME_HEIGHT);

            if(difPosY<sizeRatioValue){
                newSizeRatio=(-GAME_HEIGHT*depthRatio)/(Math.pow(sizeRatioValue*depthRatio+GAME_HEIGHT,2))*(difPosY-sizeRatioValue)+GAME_HEIGHT/depthRatio;

            }

            double DeltaY=(newPosY+zPosition*depth*sizeRatio)-(2*GAME_HEIGHT/3.0*(newSizeRatio)+GAME_HEIGHT/3.0);
            DeltaY=(newPosY-difPosZ*sizeRatio)-((GameGrid.PVY-GameGrid.PFY)*(newSizeRatio)+GameGrid.PFY);
            //difPosZ
            //double sizeRatioValue=(depthRatio-GAME_HEIGHT)/depthRatio;
            //if(difPosY<sizeRatioValue)System.out.println("testW");



            //DeltaX=1;
            double groundAngle;
            double CornerForAngleX=corners[a1][0];
            double CornerForAngleY=corners[a1][1]+zPosition*depth*sizeRatio;
            CornerForAngleY=corners[a1][1]-difPosZ*sizeRatio;
            //difPosZ
            double deltaXAngle=CornerForAngleX-GameGrid.PFX;
            double deltaYAngle=CornerForAngleY-GameGrid.PFY;

            groundAngle=Math.atan(deltaYAngle/deltaXAngle);

            if (deltaXAngle>0&&deltaYAngle<0){
                groundAngle=Math.PI*2.0+Math.atan(deltaYAngle/deltaXAngle);
            }
            else  if(deltaXAngle<0) groundAngle=Math.PI+Math.atan(deltaYAngle/deltaXAngle);
            if (deltaXAngle==0 && deltaYAngle<0)groundAngle=3*Math.PI/2;
            if (deltaXAngle==0 && deltaYAngle>0)groundAngle=Math.PI/2;


            double GroundDeltaX=DeltaY/Math.tan(groundAngle);
            double ratio=DeltaY/Math.sin(angleList[a1]);
            double groundRatio=DeltaY/Math.sin(groundAngle);


            double sizeRatioAtPosition=GAME_HEIGHT/((difPosY+depth)*depthRatio+GAME_HEIGHT);


            /*
            double LimitValue=groundRatio * Math.cos(groundAngle)*Math.tan(angleList[a1]);
            groundRatio*Math.cos(Math.atan(deltaYAngle/deltaXAngle))*(GameGrid.PFY-corners[a1][1])/(GameGrid.PFX-corners[a1][0]);
            double LimitValue=groundRatio*Math.cos(groundAngle)*(GameGrid.PFY-corners[a1][1])/(GameGrid.PFX-corners[a1][0]);
            if (zPosition==0)LimitValue=groundRatio * Math.cos(groundAngle)*Math.tan(angleList[a1]);
            */
            double LimitValue=groundRatio * Math.cos(groundAngle)*Math.tan(angleList[a1]);
            if(groundAngle==Math.PI/2 )LimitValue=-groundRatio*(GameGrid.PFY-corners[a1][1])/(Math.abs(deltaYAngle));
            if(groundAngle==3*Math.PI/2)LimitValue=-groundRatio*(GameGrid.PFY-corners[a1][1])/(Math.abs(deltaYAngle));;

            newCorners[a1][0] = corners[a1][0] -groundRatio  * Math.cos(groundAngle);
            newCorners[a1][1] = corners[a1][1] -LimitValue;
            newCorners[a2][0] = newCorners[a1][0]- upSign*ySign*sizeRatioAtPosition*width;
            newCorners[a2][1] = newCorners[a1][1]-upSign*xSign*sizeRatioAtPosition*height;
            newCorners[a3][0] =newCorners[a2][0]+upSign*xSign*sizeRatioAtPosition*width;
            newCorners[a3][1] = newCorners[a2][1]-upSign*ySign*sizeRatioAtPosition*height;
            newCorners[a4][0] =  newCorners[a3][0]+upSign*ySign*sizeRatioAtPosition*width;
            newCorners[a4][1] = newCorners[a3][1]+upSign*xSign*sizeRatioAtPosition*height;
            //System.out.println(newCorners[a1][1]+" x: "+xPosition+" y: "+yPosition+" z: "+zPosition);




            return newCorners;

    }
    public void fillPolygonB(Graphics g,int[] listX,int[] listY,int closest){
        g.setColor(new Color(21, 92, 5));
        g.fillPolygon(listX,listY,4);
        g.setColor(Color.BLACK);
        double[][] cornersP= new double[4][2];
        for(var i=0;i<4;i++){
            cornersP[i][0]=listX[i];
            cornersP[i][1]=listY[i];
        }


        //int TLCorner=topLeftCorner(cornersP);
        //g.fillOval((int) (cornersP[TLCorner][0]-5), (int) (cornersP[TLCorner][1]-5),10,10);





        for(var i=0;i<4;i++) {
            int xNum = 1 + i;
            if (xNum == 4) xNum = 0;
            g.drawLine(listX[i],listY[i],listX[xNum],listY[xNum]);
        }

    }
    public int closestCorner(double[][] corners){
        /*
        int closest=0;
        double distance=Math.sqrt(Math.pow(corners[0][0]-GameGrid.PFX,2)+Math.pow(corners[0][1]-GameGrid.PFY,2));
        for(var i=0;i<4;i++){
            if(Math.sqrt(Math.pow(corners[i][0]-GameGrid.PFX,2)+Math.pow(corners[i][1]-GameGrid.PFY,2))<distance){
                distance=Math.sqrt(Math.pow(corners[i][0]-GameGrid.PFX,2)+Math.pow(corners[i][1]-GameGrid.PFY,2));
                closest=i;
            }
        }
        */
        int closest=0;
        if(newPosX<GameGrid.PFX)closest=2;
        else closest=1;
        return closest;
    }


}
