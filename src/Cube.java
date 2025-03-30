import java.awt.*;

public class Cube {
    int xPosition;
    int yPosition;
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int width=100;
    int height=100;
    int depth=100;
    double depthRatio=1/3.0;
    double PFX;
    double PFY;
    double PVX;
    double PVY;

    double[][] corners;



    Cube(int GAME_WIDTH,int GAME_HEIGHT,int xPosition,int yPosition ){
        this.xPosition=xPosition;
        this.yPosition=yPosition;
        Cube.GAME_WIDTH =GAME_WIDTH;
        Cube.GAME_HEIGHT =GAME_HEIGHT;
        PFX=GAME_WIDTH/2.0;
        PFY=GAME_HEIGHT/3.0;
        PVX=GAME_WIDTH/2.0;
        PVY=GAME_HEIGHT;


    }

    public void draw(Graphics g, int[] grillCoord,double playerPosX,double playerPosY){

        double b=200;
        double a = Math.pow(b,1/2.0)*10;
        int difPosX=(int)(playerPosX-xPosition);
        int difPosY=(int)(playerPosY-yPosition+GAME_HEIGHT/3);
        if (difPosY+height<0)difPosY=-height;
        int distance=(int)Math.sqrt(Math.pow(difPosX,2.0)+Math.pow(difPosY,2.0));

        depthRatio=1/3.0;

        //System.out.println(newWidth);

        double angleCenter;
        //int deltaXCenter=corners[i][0]-PFX;
        //int deltaYCenter=corners[i][1]-PFY;

        int newPosX;
        int newPosY;


        newPosX=xPosition+grillCoord[0];
        newPosY=yPosition+grillCoord[1];





        int distancePFToC=(int)Math.sqrt(Math.pow(newPosX-PFX,2.0)+Math.pow(newPosY-PFY,2.0));
        int distancePVToC=(int)Math.sqrt(Math.pow(newPosX-PVX,2.0)+Math.pow(newPosY-PVY,2.0));
        int distancePVToPF=(int)Math.sqrt(Math.pow(PFX-PVX,2.0)+Math.pow(PFY-PVY,2.0));

        int newWidth= (int) (width*(distancePFToC)/distancePVToPF);
        int newHeight= (int) (height*(distancePFToC)/distancePVToPF);
        int newDepth= (int) (depth*(distancePFToC)/distancePVToPF);


        corners=getCorners(newPosX,newPosY,newWidth,newHeight);

        g.setColor(new Color(7, 252, 3));
        g.fillRect( newPosX-newWidth/2,newPosY-newHeight/2,newWidth,newHeight);



        double[] deltaXList=new double[4];
        double[] deltaYList=new double[4];
        double[] angleList=new double[4];
        for(var i=0;i<4;i++){
            deltaXList[i]=corners[i][0]-PFX;
            deltaYList[i]=corners[i][1]-PFY;
            if (deltaXList[i]>0)
            angleList[i]=Math.atan(deltaYList[i]/deltaXList[i]);
            else angleList[i]=Math.PI+Math.atan(deltaYList[i]/deltaXList[i]);
            if (deltaXList[i]==0 && deltaYList[i]>0)angleList[i]=Math.PI/2;
            if (deltaXList[i]==0 && deltaYList[i]<0)angleList[i]=-Math.PI/2;
        }
        /*
        g.setColor(Color.red);
        g.drawString(String.valueOf(angleList[0]),15,110);
        g.drawString(String.valueOf(angleList[1]),15,130);
        g.drawString(String.valueOf(angleList[2]),15,150);
        g.drawString(String.valueOf(angleList[3]),15,170);

         */



        int[][] xPointsList = new int[4][4];
        int[][] yPointsList = new int[4][4];
        int closestCorner=closestCorner(corners);
        double[][] newCorners=getCornersB(corners,angleList,closestCorner,depthRatio,newWidth,newHeight,newDepth);
        for(var i=0;i<4;i++){
            int xNum=1+i;
            if (xNum==4)xNum=0;
            xPointsList[i]=listDoubleToInt(new double[] {corners[i][0],newCorners[i][0],
                    newCorners[xNum][0],corners[xNum][0]});
            yPointsList[i]=listDoubleToInt(new double[]{corners[i][1],newCorners[i][1],
                    newCorners[xNum][1],corners[xNum][1]});
        }
        g.setColor(new Color(21, 92, 5));
        if (deltaYList[0]>0 ) {
            fillPolygonB(g,xPointsList[0],yPointsList[0],4);
            g.setColor(Color.RED);
            g.drawLine((int) corners[0][0], (int) corners[0][1], (int) PFX, (int) PFY);
            g.drawLine((int) corners[1][0], (int) corners[1][1], (int) PFX, (int) PFY);

        }
        if (deltaYList[2]<0) {
            fillPolygonB(g, xPointsList[2], yPointsList[2], 4);
            g.setColor(Color.RED);
            g.drawLine((int) corners[2][0], (int) corners[2][1], (int) PFX, (int) PFY);
            g.drawLine((int) corners[3][0], (int) corners[3][1], (int) PFX, (int) PFY);
        }
        if (deltaXList[0]>0) {
            fillPolygonB(g, xPointsList[3], yPointsList[3], 4);
            g.setColor(Color.RED);
            g.drawLine((int) corners[0][0], (int) corners[0][1], (int) PFX, (int) PFY);
            g.drawLine((int) corners[3][0], (int) corners[3][1], (int) PFX, (int) PFY);
        }
        if (deltaXList[1]<0) {
            fillPolygonB(g,xPointsList[1],yPointsList[1],4);
            g.setColor(Color.RED);
            g.drawLine((int) corners[1][0], (int) corners[1][1], (int) PFX, (int) PFY);
            g.drawLine((int) corners[2][0], (int) corners[2][1], (int) PFX, (int) PFY);
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
         */}
    public int[] listDoubleToInt(double[] l){
        int[] newList= new int[l.length];
        for(var i=0;i<l.length;i++){
            newList[i]= (int) l[i];
        }
        return newList;
    }
    public double[][] getCorners(int newPosX,int newPosY,int newWidth,int newHeight){
        double [][] corners=new double[4][2];
        corners[0][0]=newPosX-newWidth/2.0;
        corners[1][0]=newPosX+newWidth/2.0;
        corners[2][0]=newPosX+newWidth/2.0;
        corners[3][0]=newPosX-newWidth/2.0;
        corners[0][1]=newPosY-newHeight/2.0;
        corners[1][1]=newPosY-newHeight/2.0;
        corners[2][1]=newPosY+newHeight/2.0;
        corners[3][1]=newPosY+newHeight/2.0;


        return corners;
    }
    public double[][] getCornersB(double[][] corners, double[] angleList, int closest, double ratio,double newWidth,double newHeight,double newDepth){
        double[][] newCorners = new double[4][2];

        int xSign=1;
        int ySign=1;
        if(closest==3 || closest==1)xSign=0;
        if(closest==0 || closest==2)ySign=0;
        int upSign=1;
        if(closest==2 || closest==3)upSign=-1;

        /*
        for(var i=0;i<4;i++) {
            newCorners[i][0] = corners[i][0] - xSign * ratio * depth * Math.cos(angleList[i]);
            newCorners[i][1] = corners[i][1] - ySign * ratio * depth * Math.sin(angleList[i]);
        }
        */
        double distanceP=Math.sqrt(Math.pow(corners[closest][0]-PFX,2)+Math.pow(corners[closest][1]-PFY,2));

        int a1;
        int a2;
        int a3;
        int a4;
        if (closest==1){a1=1;a2=2;a3=3;a4=0;}
        else if (closest==2){a1=2;a2=3;a3=0;a4=1;}
        else if (closest==3){a1=3;a2=0;a3=1;a4=2;}
        else{a1=0;a2=1;a3=2;a4=3;}
        //System.out.println(ySign*newHeight*(distanceP-ratio*depth)/distanceP);

        newCorners[a1][0] = corners[a1][0] -ratio * newDepth * Math.cos(angleList[closest]);
        newCorners[a1][1] = corners[a1][1] - ratio * newDepth * Math.sin(angleList[closest]);
        newCorners[a2][0] = newCorners[a1][0]+upSign*xSign*newWidth*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a2][1] = newCorners[a1][1]+upSign*ySign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a3][0] =newCorners[a2][0]-upSign*ySign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a3][1] = newCorners[a2][1]+upSign*xSign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a4][0] =  newCorners[a3][0]-upSign*xSign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a4][1] = newCorners[a3][1]-upSign*ySign*newHeight*(distanceP-ratio*newHeight)/distanceP;


        return newCorners;

    }
    public void fillPolygonB(Graphics g,int[] listX,int[] listY,int n){
        g.setColor(new Color(21, 92, 5));
        g.fillPolygon(listX,listY,4);
        g.setColor(Color.BLACK);
        for(var i=0;i<4;i++) {
            int xNum = 1 + i;
            if (xNum == 4) xNum = 0;
            g.drawLine(listX[i],listY[i],listX[xNum],listY[xNum]);
        }


    }
    public int closestCorner(double[][] corners){
        int closest=0;
        double distance=Math.sqrt(Math.pow(corners[0][0]-PFX,2)+Math.pow(corners[0][1]-PFY,2));
        for(var i=0;i<4;i++){
            if(Math.sqrt(Math.pow(corners[i][0]-PFX,2)+Math.pow(corners[i][1]-PFY,2))<distance){
                distance=distance=Math.sqrt(Math.pow(corners[i][0]-PFX,2)+Math.pow(corners[i][1]-PFY,2));
                closest=i;
            }
        }
        return closest;
    }
}
