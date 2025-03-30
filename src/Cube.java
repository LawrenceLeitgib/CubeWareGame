import java.awt.*;

public class Cube {
    int xPosition;
    int yPosition;

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    int width=100;
    int height=100;
    int depth=100;
    double PFX;
    double PFY;



    Cube(int GAME_WIDTH,int GAME_HEIGHT,int xPosition,int yPosition ){
        this.xPosition=xPosition;
        this.yPosition=yPosition;
        this.GAME_WIDTH =GAME_WIDTH;
        this.GAME_HEIGHT=GAME_HEIGHT;
        PFX=GAME_WIDTH/2;
        PFY=GAME_HEIGHT/3;


    }

    public void draw(Graphics g, int[] grillCoord,double playerPosX,double playerPosY){
        double b=200;
        double a = Math.pow(b,1/2)*1000;
        double ratio=1/2.0;

        int difPosX=(int)(playerPosX-xPosition);
        int difPosY=(int)(playerPosY-yPosition+GAME_HEIGHT/3);

        if (difPosY+height<0)difPosY=-height;



        int distance=(int)Math.sqrt(Math.pow(difPosX,2)+Math.pow(difPosY,2));
        int newWidth= (int) (width*a/(distance+a));
        int newHeight= (int) (height*a/(distance+a));

        int newPosX=0;
        if (difPosX>0)
        newPosX=(int)(GAME_WIDTH/2-(GAME_WIDTH/2)*(difPosX/(difPosX+b)));
        else newPosX=(int)(GAME_WIDTH/2+(GAME_WIDTH/2)*(-difPosX/(-difPosX+b)));

        int newPosY=(int)(GAME_HEIGHT-(2*GAME_HEIGHT/3)*(difPosY/(difPosY+b)));

        newPosY=yPosition+grillCoord[1];
        newPosX=xPosition+grillCoord[0];
        //System.out.println(difPosX);
        g.setColor(new Color(7, 252, 3));
        g.fillRect( newPosX-newWidth/2,newPosY-newHeight/2,newWidth,newHeight);


        double[][] corners=getCorners(newPosX,newPosY,newWidth,newHeight);


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
        g.drawString(String.valueOf(angleList[0]),15,110);
        g.drawString(String.valueOf(angleList[1]),15,130);
        g.drawString(String.valueOf(angleList[2]),15,150);
        g.drawString(String.valueOf(angleList[3]),15,170);

        int[][] xPointsList = new int[4][4];
        int[][] yPointsList = new int[4][4];

        for(var i=0;i<4;i++){
            int xNum=1+i;
            if (xNum==4)xNum=0;
            xPointsList[i]=listDoubleToInt(new double[] {corners[i][0],
                    corners[i][0] -ratio*depth*Math.cos(angleList[i]),
                    corners[xNum][0]-ratio*depth*Math.cos(angleList[xNum]),
                    corners[xNum][0]});
            yPointsList[i]=listDoubleToInt(new double[]{corners[i][1],
                    corners[i][1] -ratio * depth * Math.sin(angleList[i]),
                    corners[xNum][1] -ratio * depth * Math.sin(angleList[xNum]),
                    corners[xNum][1]});
        }

        g.setColor(new Color(21, 92, 5));
        if (deltaYList[0]>0) {
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


        System.out.println(deltaYList[0]);
    }
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


}
