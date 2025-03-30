import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    static double xPosition;
    static double yPosition;
    static double zPosition;

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static int cubeAway=2;

    static double width=80;
    static double height=180;

    static double depth=80;
    double speed=0.4;
    double gravityAcceleration=0.05;
    double xVelocity;
    double yVelocity;
    double zVelocity;

    boolean isSlowing=false;
    boolean isFlying=true;

    double slowMultiplier=0.1;
    double runningMultiplier=5;

    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double positionZ){
        Player.xPosition=positionX;
        Player.yPosition=0;
        Player.zPosition=0;
        Player.GAME_WIDTH =GAME_WIDTH;
        Player.GAME_HEIGHT =GAME_HEIGHT;



    }
    public void updateData(double deltaTime){

        boolean[]collision=detectionCollision();
       // System.out.println(collisionLeft);
        //!isFlying&&!collision[1]
        if(!isFlying){
            setZVelocity(zVelocity-gravityAcceleration);
        }
        if (isSlowing) {
            xPosition += this.xVelocity/10.0;
            yPosition += this.yVelocity/10.0;

                if (isFlying)
                    zPosition += this.zVelocity / 10.0;
                else zPosition += this.zVelocity;


        }else{

            xPosition += this.xVelocity;
            yPosition += this.yVelocity;
            zPosition += this.zVelocity;
        }
        collision=detectionCollision();
        /*
        if(collision[0]){
            if (isSlowing) {
                if(collision[3]&&this.xVelocity<0||collision[4]&&this.xVelocity>0)xPosition -= this.xVelocity/10.0;
                if(collision[5]&&this.yVelocity<0||collision[6]&&this.yVelocity>0)yPosition -= this.yVelocity/10.0;

                if(collision[1]&&this.zVelocity<0||collision[2]&&this.zVelocity>0){
                    if(isFlying)zPosition -= this.zVelocity/10.0;
                    else zPosition -= this.zVelocity;
                }

            }else{
                if(collision[3]&&this.xVelocity<0||collision[4]&&this.xVelocity>0)xPosition -= this.xVelocity;
                if(collision[5]&&this.yVelocity<0||collision[6]&&this.yVelocity>0)yPosition -= this.yVelocity;
                if(collision[1]&&this.zVelocity<0||collision[2]&&this.zVelocity>0)
                zPosition -= this.zVelocity;
            }
        }
        detectionCollision();
*/
        /*
        boolean[][] collisionListe=detectionCollisionForAllCube();
        boolean collisionAll=false;
        boolean collisionLeft = false;
        boolean collisionRight=false;
        boolean collisionFront=false;
        boolean collisionBack=false;
        boolean collisionTop=false;
        boolean collisionBottom=false;
        for(var i=0;i<collisionListe.length;i++){
            if (collisionListe[i][0])collisionAll=true;
            if (collisionListe[i][1])collisionLeft=true;
            if (collisionListe[i][2])collisionRight=true;
            if (collisionListe[i][3])collisionFront=true;
            if (collisionListe[i][4])collisionBack=true;
            if (collisionListe[i][5])collisionTop=true;
            if (collisionListe[i][6])collisionBottom=true;

        }

        if(!isFlying&&!collisionBottom){
            setZVelocity(zVelocity-gravityAcceleration);
        }



        if(collisionAll){
            if (isSlowing) {
                if((collisionLeft||collisionRight))
                    xPosition -= this.xVelocity/10.0;
                if((collisionFront||collisionBack))
                    yPosition -= this.yVelocity/10.0;
                if((collisionTop||collisionBottom)){
                    if(isFlying)
                        zPosition -= this.zVelocity/10.0;
                    else zPosition -= this.zVelocity;

                }
            }else{
                if((collisionLeft||collisionRight))
                    xPosition -= this.xVelocity;
                if((collisionFront||collisionBack))
                    yPosition -= this.yVelocity;
                if((collisionTop||collisionBottom))
                    zPosition -= this.zVelocity;
            }
            setZVelocity(0);

        }*/
        xPosition=  (int)((xPosition)*1000)/1000.0;
        yPosition=  (int)((yPosition)*1000)/1000.0;
        zPosition=  (int)((zPosition)*1000)/1000.0;
       // System.out.println(zVelocity);

        }
    public void setXVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }
    public void setYVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
    public void setZVelocity(double zVelocity) {

        this.zVelocity = zVelocity;
    }
    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }
    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }
    public static void draw(Graphics g){


        double sizeRatio=GAME_HEIGHT/((cubeAway*Cube.defaultSize+(Cube.defaultSize-depth)/2.0)*GameGrid.depthRatio+GAME_HEIGHT);
        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio)+GameGrid.PFY;
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        double newPosX=  (GAME_WIDTH/2.0);
        //double corners[][]=new double[4][2];
         /*
        corners[3][0]=newPosX-newWidth/2.0;
        corners[3][1]=newPosY;
        corners[2][0]=corners[3][0]+newWidth;
        corners[2][1]=corners[3][1];
        double deltaXAngle=corners[3][0]-GameGrid.PFX;
        double deltaYAngle= corners[3][1]-GameGrid.PFY;
        groundAngle=Math.atan(deltaYAngle/deltaXAngle);
        if (deltaXAngle>0&&deltaYAngle<0){
            groundAngle=Math.PI*2.0+Math.atan(deltaYAngle/deltaXAngle);
        }
        else  if(deltaXAngle<0) groundAngle=Math.PI+Math.atan(deltaYAngle/deltaXAngle);
        double sizeRatioAtPosition=GAME_HEIGHT/((cubeAway*depth+depth)*GameGrid.depthRatio+GAME_HEIGHT);
        double DeltaY=(newPosY)-((GameGrid.PVY-GameGrid.PFY)*(sizeRatioAtPosition)+GameGrid.PFY);
        double groundRatio=DeltaY/Math.sin(groundAngle);
        corners[0][0] = corners[3][0] -groundRatio  * Math.cos(groundAngle);
        corners[0][1] = corners[3][1] -groundRatio* Math.sin(groundAngle);
        corners[1][0] = corners[0][0] +sizeRatioAtPosition*width;
        corners[1][1] = corners[0][1] ;



        */

        double[][] corners=getCorners(newPosX,newPosY,newWidth,newHeight);
        double[] angleList=getAngleList(corners);
        double[][] newCorners=getCornersB(corners,angleList,newPosY);
        /*g.setColor(Color.red);
        g.fillOval((int) (corners[0][0]-5), (int) (corners[0][1]-5),10,10);
        g.fillOval((int) (corners[1][0]-5), (int) (corners[1][1]-5),10,10);
        g.fillOval((int) (corners[2][0]-5), (int) (corners[2][1]-5),10,10);
        g.fillOval((int) (corners[3][0]-5), (int) (corners[3][1]-5),10,10);




*/
        for(var i=0;i<4;i++) {
            g.setColor(Color.BLACK);
            if (i < 3) {
                g.drawLine((int) corners[i][0], (int) corners[i][1], (int) corners[i + 1][0], (int) corners[i + 1][1]);
                g.drawLine((int) newCorners[i][0], (int) newCorners[i][1], (int) newCorners[i + 1][0], (int) newCorners[i + 1][1]);

            } else {
                g.drawLine((int) corners[i][0], (int) corners[i][1], (int) corners[0][0], (int) corners[0][1]);
                g.drawLine((int) newCorners[i][0], (int) newCorners[i][1], (int) newCorners[0][0], (int) newCorners[0][1]);

            }
            g.drawLine((int) corners[i][0], (int) corners[i][1], (int) newCorners[i][0], (int) newCorners[i][1]);

        }









        g.setColor(new Color(3, 40, 252));
        //g.fillRect((int) corners[0][0], (int) corners[0][1], (int) newWidth, (int) newHeight);
        //g.fillPolygon(xPointsList,yPointsList,4);
        //g.fillRect(GAME_WIDTH/2-width/2,2*GAME_HEIGHT/3-height/2,width,height);
        //g.fillOval(GAME_WIDTH/2-width/2, (int) (3*GAME_HEIGHT/4-height*CubeContainer.depthRatio/4),width, (int) (height*CubeContainer.depthRatio/2));
       // g.fillRect((int) (newPosX-newWidth/2.0), (int) (newPosY-newHeight), (int) newWidth, (int) newHeight);



        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.PLAIN,16));
        g.setColor(Color.red);
        g.drawString(String.valueOf(yPosition),15,90);
        g.drawString(Double.toString(xPosition),15,70);
        g.drawString(Double.toString(zPosition),15,110);
    }
   /*
    public boolean[][] detectionCollisionForAllCube(){
        boolean[][] listOfCollision=new boolean[10][7];

        int collisionCount=0;



        for(var i=0;i<CubeContainer.cubePositionX.length;i++){
            if(CubeContainer.cubePositionX[i])
                for (var j = 0; j < CubeContainer.cubePositionY.length; j++) {
                    if(CubeContainer.cubePositionY[j])
                        for (var k = 0; k <CubeContainer.cubePositionZ.length ; k++) {
                            if (CubeContainer.cubePosition[i][j][k]) {
                                if(detectionCollision(i-CubeContainer.numberOfCubes,j-CubeContainer.numberOfCubes,k-CubeContainer.numberOfCubesZ)[0]){
                                    listOfCollision[collisionCount]=detectionCollision(i-CubeContainer.numberOfCubes,j-CubeContainer.numberOfCubes,k-CubeContainer.numberOfCubesZ);
                                    collisionCount++;
                                }
                            }
                        }
                }
        }

        return listOfCollision;
    }*/
    /*
    public boolean[] detectionCollision(int x,int y,int z){
        double playerLeft=xPosition;
        double playerRight=xPosition+width;
        double playerFront=yPosition;
        double playerBack=yPosition+height;
        boolean[] collision=new boolean[7];

        //System.out.println(yPosition+height>y*Cube.defaultSize&&yPosition<(y+1)*Cube.defaultSize);
        //System.out.println(xPosition+width>(x)*Cube.defaultSize&&xPosition<(x+1)*Cube.defaultSize);
        //System.out.println(yPosition+height>y*Cube.defaultSize&&yPosition<(y+1)*Cube.defaultSize);
        //System.out.println(xPosition+width>(x)*Cube.width&&xPosition<(x+1)*Cube.width);





        if(xPosition+1-(1-width/Cube.defaultSize)/2.0>x&&xPosition+(1-width/Cube.defaultSize)/2.0<(x+1)){
            if((yPosition+1)-(1-depth/Cube.defaultSize)/2.0>y&&yPosition+(1-depth/Cube.defaultSize)/2.0<(y+1)){
                if  (zPosition+height/Cube.defaultSize>z&&zPosition<(z+1)){
                    collision[0]=true;
                }

            }

        }
        collision[1]=xPosition+1-(1-width/Cube.defaultSize)/2.0>x&&collision[0] ;//left
        collision[2]=xPosition+(1-width/Cube.defaultSize)/2.0<(x+1) &&collision[0];//right
        collision[3]=yPosition+1-(1-depth/Cube.defaultSize)/2.0>y&&collision[0];//front
        collision[4]=yPosition+(1-depth/Cube.defaultSize)/2.0<(y+1)&&collision[0];//back
        collision[5]=zPosition+height/Cube.defaultSize>z&&collision[0];//top
        collision[6]=zPosition<(z+1)&&collision[0];//bottom
       // System.out.println(collision[1]);


        return collision;
    }
    */
   public boolean[] detectionCollision(){
       boolean[] collision=new boolean[7];

       int xPosUnder=(int)(xPosition+0.5);
       if(xPosition<0)xPosUnder=(int)(xPosition-0.5);
       int yPosUnder=(int)(yPosition+0.5);
       if(yPosition<0)yPosUnder=(int)(yPosition-0.5);
       int zPosUnder=(int)(zPosition);
       if(zPosition<0)zPosUnder=(int)(zPosition-1);


       int zPosAbove=(int)(zPosition+height/Cube.defaultSize);
       if(zPosition<0)zPosAbove=(int)(zPosition+height/Cube.defaultSize-1);


       double forOtherSensitivity=0.10;




       int xLeftPos=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2);
       if(xPosition<0)xLeftPos=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2-1);

       int xRightPos=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2+1);
       if(xPosition<0)xRightPos=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2);

       int xLeftPosForOther=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2+forOtherSensitivity);
       if(xPosition<0.5)xLeftPosForOther=(int)(xPosition+(Cube.defaultSize-width)/Cube.defaultSize/2-1+forOtherSensitivity);

       int xRightPosForOther=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2+1-forOtherSensitivity);
       if(xPosition<-0.5)xRightPosForOther=(int)(xPosition-(Cube.defaultSize-width)/Cube.defaultSize/2-forOtherSensitivity);

       int yFrontPos=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2);
       if(yPosition<0)yFrontPos=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2-1);

       int yBackPos=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2+1);
       if(yPosition<0)yBackPos=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2);

       int yFrontPosForOther=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2+forOtherSensitivity);
       if(yPosition<0.5)yFrontPosForOther=(int)(yPosition+(Cube.defaultSize-depth)/Cube.defaultSize/2-1+forOtherSensitivity);

       int yBackPosForOther=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2+1-forOtherSensitivity);
       if(yPosition<-0.5)yBackPosForOther=(int)(yPosition-(Cube.defaultSize-depth)/Cube.defaultSize/2-forOtherSensitivity);

       int zPosUnderForOther=(int)(zPosition+forOtherSensitivity*3);
       if(zPosition<0)zPosUnderForOther=(int)(zPosition-1+forOtherSensitivity*3);

       int zPosAboveForOther=(int)(zPosition+height/Cube.defaultSize-forOtherSensitivity*3);
       if(zPosition<0)zPosAboveForOther=(int)(zPosition+height/Cube.defaultSize-1-forOtherSensitivity*3);

       //System.out.println(zPosUnder);



       //System.out.println(xLeftPos+" | "+yFrontPos+" | "+zPosAbove);
      //System.out.println(zPosUnderForOther+" | "+zPosAboveForOther);
       int numCub=CubeContainer.numberOfCubes;
       int numCubZ=CubeContainer.numberOfCubesZ;


       collision[1]= CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPosForOther][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPosForOther][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPosForOther][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPosForOther][numCubZ+zPosUnder];


       collision[2]= CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPosForOther][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPosForOther][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPosForOther][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPosForOther][numCubZ+zPosAbove];


       /*
       collision[1]= CubeContainer.cubePosition[numCub+xPosUnder][numCub+yPosUnder][numCubZ+zPosUnder];
       collision[2]= CubeContainer.cubePosition[numCub+xPosUnder][numCub+yPosUnder][numCubZ+zPosAbove];

       collision[3]= CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPos][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPos][numCubZ+zPosAbove];
       collision[4]= CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPos][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPos][numCubZ+zPosAbove];

       collision[5]= CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPos][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPos][numCubZ+zPosAbove];
       collision[6]= CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPos][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPos][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPos][numCubZ+zPosAbove];

       */
       collision[3]= CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPosForOther][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPosForOther][numCubZ+zPosAboveForOther];
       collision[4]= CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPosForOther][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPosForOther][numCubZ+zPosAboveForOther];

       collision[5]= CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPos][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPos][numCubZ+zPosAboveForOther];
       collision[6]= CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPos][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPos][numCubZ+zPosAboveForOther];


       double sensitivity=0.7;

      // System.out.println(zPosUnder+1-zPosition);


       if(collision[1]&&zPosUnder+1-zPosition<sensitivity){
           zPosition=zPosUnder+1.00000001;
           zVelocity=0;
       }
       if(collision[2]&&zPosition-zPosAbove+height/Cube.defaultSize<sensitivity){
           zPosition=zPosAbove-height/Cube.defaultSize;
           zVelocity=0;
       }

       //System.out.println(collision[3]);

       if(collision[3]&&(xLeftPos+1-(1-width/Cube.defaultSize)/2)-xPosition<sensitivity){

           xPosition=xLeftPos+1-(1-width/Cube.defaultSize)/2;
           if(xVelocity<0)
           xVelocity=0;

       }
       if(collision[4]&&xPosition-(xRightPos-1+(1-width/Cube.defaultSize)/2)<sensitivity){

           xPosition=xRightPos-1+(1-width/Cube.defaultSize)/2;
           if(xVelocity>0)
           xVelocity=0;
       }
       if(collision[5]&&(yFrontPos+1-(1-depth/Cube.defaultSize)/2)-yPosition<sensitivity){

           yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;
           yVelocity=0;
       }
       if(collision[6]&&yPosition-(yBackPos-1+(1-depth/Cube.defaultSize)/2)<sensitivity){

           yPosition=yBackPos-1+(1-depth/Cube.defaultSize)/2;
           yVelocity=0;
       }
       /*
       if(collision[2])zPosition=zPosAbove;
       if(collision[3])zPosition=xLeftPos;
       if(collision[4])zPosition=xRightPos;
       if(collision[5])zPosition=yFrontPos;
       if(collision[6])zPosition=yBackPos;

        */


       for(var i=1;i<7;i++){
           if( collision[i])collision[0]=true;
       }
       return collision;
   }
    static public double[][] getCorners(double newPosX,double newPosY,double newWidth,double newHeight){
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
    static public double[] getAngleList(double[][] corners) {
        double[] angleList=new double[4];
        double[] deltaXList=new double[4];
        double[] deltaYList=new double[4];

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
    static public double[][] getCornersB(double[][] corners, double[] angleList,double newPosY){
        double[][] newCorners = new double[4][2];
        double groundAngle;
        double deltaXAngle=corners[3][0]-GameGrid.PFX;
        double deltaYAngle= corners[3][1]-GameGrid.PFY;
        groundAngle=Math.atan(deltaYAngle/deltaXAngle);
        if (deltaXAngle>0&&deltaYAngle<0){
            groundAngle=Math.PI*2.0+Math.atan(deltaYAngle/deltaXAngle);
        }
        else  if(deltaXAngle<0) groundAngle=Math.PI+Math.atan(deltaYAngle/deltaXAngle);
        double sizeRatioAtPosition=GAME_HEIGHT/((cubeAway*Cube.defaultSize+depth)*GameGrid.depthRatio+GAME_HEIGHT);
        double DeltaY=(newPosY)-((GameGrid.PVY-GameGrid.PFY)*(sizeRatioAtPosition)+GameGrid.PFY);
        double groundRatio=DeltaY/Math.sin(groundAngle);




        newCorners[3][0] = corners[3][0] -groundRatio  * Math.cos(groundAngle);
        newCorners[3][1] = corners[3][1] -groundRatio  * Math.sin(groundAngle);
        newCorners[0][0] = newCorners[3][0];
        newCorners[0][1] = newCorners[3][1]-sizeRatioAtPosition*height;
        newCorners[1][0] =newCorners[0][0]+sizeRatioAtPosition*width;
        newCorners[1][1] = newCorners[0][1];
        newCorners[2][0] =  newCorners[1][0];
        newCorners[2][1] = newCorners[1][1]+sizeRatioAtPosition*height;
        //System.out.println(newCorners[a1][1]+" x: "+xPosition+" y: "+yPosition+" z: "+zPosition);




        return newCorners;

    }

    public void flySwicth(){
        if(isFlying){
            isFlying=false;
            setZVelocity(0);
        }
        else {
            isFlying=true;
            setZVelocity(0);
        }
    }




    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 65:
                setXVelocity(-speed);
                break;


            case 68:
                setXVelocity(speed);
                break;
            case 83:
                setYVelocity(speed);
                break;

            case 87:
                setYVelocity(-speed);
                break;
            case 89:
                if(isFlying)
                setZVelocity(-speed);
                break;

            case 88:
                if(isFlying)
                setZVelocity(speed);
                break;
            case 70:
                flySwicth();
                break;
            case 32:
                if(!isFlying)
                setZVelocity(.4);
                break;

            case 16:
                isSlowing=true;
                break;


        }

    }

    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case 65:
                if(xVelocity<0)
                setXVelocity(0);
                break;

            case 68:
                if(xVelocity>0)
                setXVelocity(0);
                break;
            case 83:
                if(yVelocity>0)
                setYVelocity(0);
                break;

            case 87:
                if(yVelocity<0)
                setYVelocity(0);
                break;
            case 89:
                if(zVelocity<0)
                    setZVelocity(0);
                break;

            case 88:
                if(zVelocity>0)
                    setZVelocity(0);
                break;
            case 16:
                isSlowing=false;
                break;
        }
    }
}
