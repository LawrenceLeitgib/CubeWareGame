import java.awt.*;

public class Cube {
    int xPosition;
    int yPosition;

    int zPosition;
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

    Cube(int GAME_WIDTH,int GAME_HEIGHT,int xPosition,int yPosition,int zPosition ){
        this.xPosition=xPosition;
        this.yPosition=yPosition;
        this.zPosition=zPosition;
        Cube.GAME_WIDTH =GAME_WIDTH;
        Cube.GAME_HEIGHT =GAME_HEIGHT;
        PFX=GAME_WIDTH/2.0;
        PFY=GAME_HEIGHT/3.0;
        PVX=GAME_WIDTH/2.0;
        PVY=GAME_HEIGHT;
    }

    public void draw(Graphics g, int[] grillCoord,double playerPosX,double playerPosY){
        depthRatio=3.0/1.0;
        int difPosX=(int)(playerPosX-xPosition);
        int difPosY=(int)((playerPosY-yPosition));
        int distance=(int)Math.sqrt(Math.pow(difPosX,2.0)+Math.pow(difPosY,2.0));
        //if (difPosY<(int)a)difPosY=(int)a;
        if (difPosY<-GAME_HEIGHT*2/3)difPosY=-GAME_HEIGHT*2/3;

        //int newPosY= (int) ((1.0*GAME_HEIGHT)*(2*GAME_HEIGHT/3.0)/(difPosY*1.0+GAME_HEIGHT)+GAME_HEIGHT/3.0+0.5);


        //double newPosY= ((1.0*GAME_HEIGHT)*(2*GAME_HEIGHT/3.0)/(difPosY*1.0+GAME_HEIGHT)+GAME_HEIGHT/3.0);
        //double sizeRatio=(newPosY-GAME_HEIGHT/3.0)*(3/2.0)/GAME_HEIGHT;

        //newPosY-=zPosition*sizeRatio;

        double sizeRatio=GAME_HEIGHT/(difPosY*1.0*depthRatio+GAME_HEIGHT);
        double newPosY=(2*GAME_HEIGHT/3.0*sizeRatio+GAME_HEIGHT/3.0-zPosition*sizeRatio);
        //double newPosY=(2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((difPosY*1.0)/depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0);

        double newWidth= (int) (width*sizeRatio+0.5);
        double newHeight= (int) (height*sizeRatio+0.5);
        double newDepth= (int) (depth*sizeRatio+0.5);
        double newPosX= (int) (GAME_WIDTH/2.0-(playerPosX+xPosition)*sizeRatio+0.5);



        int distancePFToC=(int)Math.sqrt(Math.pow(newPosX-PFX,2.0)+Math.pow(newPosY-PFY,2.0));
        int distancePVToC=(int)Math.sqrt(Math.pow(newPosX-PVX,2.0)+Math.pow(newPosY-PVY,2.0));
        int distancePVToPF=(int)Math.sqrt(Math.pow(PFX-PVX,2.0)+Math.pow(PFY-PVY,2.0));


        //System.out.println(newPosY-GAME_HEIGHT);

        corners=getCorners(newPosX,(newPosY),newWidth,newHeight);
        g.setColor(new Color(7, 252, 3));
        //g.fillRect( newPosX-newWidth/2,newPosY-newHeight/2,newWidth,newHeight);
        g.fillRect((int) (corners[0][0]+0.5), (int) (corners[0][1]+0.5),(int)(newWidth+0.5),(int)(newHeight+0.5));
        //g.fill3DRect((int) (corners[0][0]+0.5), (int) (corners[0][1]+0.5), (int)(newWidth+0.5), (int)(newHeight+0.5),false);
        //g.setColor(Color.RED);
        //g.fillOval((int) (newPosX-5), (int) (newPosY-5),10,10);

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
        g.setColor(Color.red);
        g.setFont(new Font("Arial",Font.PLAIN,18));
        g.drawString(String.valueOf((int)newPosY),15,(int)newPosY*2-GAME_HEIGHT);
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
        double[][] newCorners=getCornersB(corners,angleList,closestCorner,depthRatio,difPosY,newWidth,newHeight,newDepth);
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
            fillPolygonB(g,xPointsList[0],yPointsList[0],closestCorner);
            g.setColor(Color.RED);
            //g.drawLine((int) corners[0][0], (int) corners[0][1], (int) PFX, (int) PFY);
            //g.drawLine((int) corners[1][0], (int) corners[1][1], (int) PFX, (int) PFY);

        }
        if (deltaYList[2]<0) {
            fillPolygonB(g, xPointsList[2], yPointsList[2], closestCorner);
            g.setColor(Color.RED);
            //g.drawLine((int) corners[2][0], (int) corners[2][1], (int) PFX, (int) PFY);
            //g.drawLine((int) corners[3][0], (int) corners[3][1], (int) PFX, (int) PFY);
        }
        if (deltaXList[0]>0) {
            fillPolygonB(g, xPointsList[3], yPointsList[3], closestCorner);
            g.setColor(Color.RED);
            //g.drawLine((int) corners[0][0], (int) corners[0][1], (int) PFX, (int) PFY);
            //g.drawLine((int) corners[3][0], (int) corners[3][1], (int) PFX, (int) PFY);
        }
        if (deltaXList[1]<0) {
            fillPolygonB(g,xPointsList[1],yPointsList[1],closestCorner);
            g.setColor(Color.RED);
            //g.drawLine((int) corners[1][0], (int) corners[1][1], (int) PFX, (int) PFY);
            //g.drawLine((int) corners[2][0], (int) corners[2][1], (int) PFX, (int) PFY);
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
        g.fillOval((int) (newCorners[0][0]-5), (int) (newCorners[0][1]-5),10,10);
        g.setColor(Color.red);
        g.fillOval((int) (newCorners[1][0]-5), (int) (newCorners[1][1]-5),10,10);
        g.fillOval((int) (newCorners[2][0]-5), (int) (newCorners[2][1]-5),10,10);
        g.fillOval((int) (newCorners[3][0]-5), (int) (newCorners[3][1]-5),10,10);
       
         */



        //g.fillOval((int) (corners[0][0]-5), (int) (corners[0][1]-5),10,10);

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

        corners[0][0]=newPosX-newWidth;
        corners[1][0]=newPosX;
        corners[2][0]=newPosX;
        corners[3][0]=newPosX-newWidth;
        corners[0][1]=newPosY-newHeight;
        corners[1][1]=newPosY-newHeight;
        corners[2][1]=newPosY;
        corners[3][1]=newPosY;

        corners[0][0]=newPosX-newWidth/2.0;
        corners[1][0]=newPosX+newWidth/2.0;
        corners[2][0]=newPosX+newWidth/2.0;
        corners[3][0]=newPosX-newWidth/2.0;
        /*
        corners[0][1]=newPosY-newHeight/2.0;
        corners[1][1]=newPosY-newHeight/2.0;
        corners[2][1]=newPosY+newHeight/2.0;
        corners[3][1]=newPosY+newHeight/2.0;
        */


        return corners;
    }
    public double[][] getCornersB(double[][] corners, double[] angleList, int closest, double depthRatio,double difPosY,double newWidth,double newHeight,double newDepth){
        double[][] newCorners = new double[4][2];
        int a1;
        int a2;
        int a3;
        int a4;

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
        double distanceP;


        //ratio=sizeRatio*depth/newDepth;
        //System.out.println(depthRatio +" | "+ratio);

        /*
        distanceP=Math.sqrt(Math.pow(corners[closest][0]-PFX,2)+Math.pow(corners[closest][1]-PFY,2));
        if (closest==1){a1=1;a2=2;a3=3;a4=0;}
        else if (closest==2){a1=2;a2=3;a3=0;a4=1;}
        else if (closest==3){a1=3;a2=0;a3=1;a4=2;}
        else{a1=0;a2=1;a3=2;a4=3;}
        newCorners[a1][0] = corners[a1][0] -ratio * newDepth * Math.cos(angleList[closest]);
        newCorners[a1][1] = corners[a1][1] - ratio * newDepth * Math.sin(angleList[closest]);
        newCorners[a2][0] = newCorners[a1][0]+upSign*xSign*newWidth*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a2][1] = newCorners[a1][1]+upSign*ySign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a3][0] =newCorners[a2][0]-upSign*ySign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a3][1] = newCorners[a2][1]+upSign*xSign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a4][0] =  newCorners[a3][0]-upSign*xSign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a4][1] = newCorners[a3][1]-upSign*ySign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        */
        if (closest==1){a1=2;a2=3;a3=0;a4=1;}
        else if (closest==2){a1=1;a2=2;a3=3;a4=0;}
        else if (closest==3){a1=0;a2=1;a3=2;a4=3;}
        else{a1=3;a2=0;a3=1;a4=2;}
        //System.out.println(ySign*newHeight*(distanceP-ratio*depth)/distanceP);
        double DeltaY=(2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((difPosY)*1.0*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0)-(2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((difPosY+depth)*1.0*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0);
        DeltaY=DeltaY;

       // System.out.println(DeltaY*depth);
        //System.out.println(DeltaY);

        double DeltaX=DeltaY/Math.tan(angleList[a1]);
        double ratio=Math.sqrt(Math.pow(DeltaX,2)+Math.pow(DeltaY,2));
        //System.out.println(ratio);

        distanceP=Math.sqrt(Math.pow(corners[a1][0]-PFX,2)+Math.pow(corners[a1][1]-PFY,2));

        /*
        distanceP=Math.sqrt(Math.pow(middleX-PFX,2)+Math.pow(middleY-PFY,2));
        double DeltaXM=middleX-PFX;
        double DeltaYM=middleY-PFY;
        double angleM=Math.atan(DeltaYM/DeltaXM);
        if(DeltaXM<0)angleM=Math.PI+Math.atan(DeltaYM/DeltaXM);
        System.out.println(ratio * newDepth * Math.cos(angleM));
        newCorners[a1][0] = corners[a1][0] -ratio * depth * Math.cos(angleList[a1]);
        newCorners[a1][1] = corners[a1][1] - ratio * depth * Math.sin(angleList[a1]);
        newCorners[a2][0] = newCorners[a1][0]- upSign*ySign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a2][1] = newCorners[a1][1]-upSign*xSign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a3][0] =newCorners[a2][0]+upSign*xSign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a3][1] = newCorners[a2][1]-upSign*ySign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a4][0] =  newCorners[a3][0]+upSign*ySign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a4][1] = newCorners[a3][1]+upSign*xSign*newHeight*(distanceP-ratio*newHeight)/distanceP;

        newCorners[a1][0] = corners[a1][0] -ratio *depth * Math.cos(angleList[a1]);
        newCorners[a1][1] = corners[a1][1] - ratio  *depth* Math.sin(angleList[a1]);
        newCorners[a2][0] = newCorners[a1][0]- upSign*ySign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a2][1] = newCorners[a1][1]-upSign*xSign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a3][0] =newCorners[a2][0]+upSign*xSign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a3][1] = newCorners[a2][1]-upSign*ySign*newHeight*(distanceP-ratio*newHeight)/distanceP;
        newCorners[a4][0] =  newCorners[a3][0]+upSign*ySign*newWidth*(distanceP-ratio*newWidth)/distanceP;
        newCorners[a4][1] = newCorners[a3][1]+upSign*xSign*newHeight*(distanceP-ratio*newHeight)/distanceP;

         */

        double sizeRatioAtPosition=GAME_HEIGHT/((difPosY+depth)*depthRatio+GAME_HEIGHT);

        System.out.println(sizeRatioAtPosition);


        newCorners[a1][0] = corners[a1][0] -ratio  * Math.cos(angleList[a1]);
        newCorners[a1][1] = corners[a1][1] - ratio  * Math.sin(angleList[a1]);
        newCorners[a2][0] = newCorners[a1][0]- upSign*ySign*sizeRatioAtPosition*width;
        newCorners[a2][1] = newCorners[a1][1]-upSign*xSign*sizeRatioAtPosition*height;
        newCorners[a3][0] =newCorners[a2][0]+upSign*xSign*sizeRatioAtPosition*width;
        newCorners[a3][1] = newCorners[a2][1]-upSign*ySign*sizeRatioAtPosition*height;
        newCorners[a4][0] =  newCorners[a3][0]+upSign*ySign*sizeRatioAtPosition*width;
        newCorners[a4][1] = newCorners[a3][1]+upSign*xSign*sizeRatioAtPosition*height;




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



        /*
        int xSign=1;
        int ySign=1;
        if(closest==3 || closest==1)xSign=0;
        if(closest==0 || closest==2)ySign=0;
        int upSign=1;
        if(closest==2 || closest==3)upSign=-1;
        int a1;
        int a2;
        int a3;
        int a4;
        if (closest==1){a1=1;a2=2;a3=3;a4=0;}
        else if (closest==2){a1=2;a2=3;a3=0;a4=1;}
        else if (closest==3){a1=3;a2=0;a3=1;a4=2;}
        else{a1=0;a2=1;a3=2;a4=3;}

         */

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
                distance=Math.sqrt(Math.pow(corners[i][0]-PFX,2)+Math.pow(corners[i][1]-PFY,2));
                closest=i;
            }
        }
        return closest;
    }
    public int topLeftCorner(double[][] corners){
        int topLeft=0;
        int top=0;
        double yDif=corners[0][1];
        for(var i=1;i<4;i++){
            if (corners[i][1]<yDif){
                yDif=corners[i][1];
                top=i;
            }
            else if (corners[i][1]==yDif){
                if (corners[i][0]<corners[top][0]){
                    topLeft=i;
                }
                else topLeft=top;
            }
        }





        return topLeft;
    }
}
