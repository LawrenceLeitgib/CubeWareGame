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

    boolean isMovingUp=false;
    boolean isMovingDown=false;
    boolean isMovingForward =false;
    boolean isMovingBackward =false;
    boolean isMovingLeft=false;
    boolean isMovingRight=false;

    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double positionZ){
        Player.xPosition=0;
        Player.yPosition=0;
        Player.zPosition=0;
        Player.GAME_WIDTH =GAME_WIDTH;
        Player.GAME_HEIGHT =GAME_HEIGHT;

    }
    public void updateData(double deltaTime){

       // boolean[]collision=detectionCollision();
        //System.out.println(collision[5]);
       // System.out.println(collisionLeft);
        //!isFlying&&!collision[1]
        double multiplierOfSpeed=1;
        if(isSlowing)multiplierOfSpeed=slowMultiplier;




        //double zSpeed=;
        if(!isFlying){
           setZVelocity(zVelocity-gravityAcceleration);
          // System.out.println(zPosition);
           zPosition+=zVelocity;
           detectionCollision(1);

        }
        else{
            if(isMovingUp) {
                zPosition+=speed*multiplierOfSpeed;
                detectionCollision(2);
            }
            if(isMovingDown){
                zPosition-=speed*multiplierOfSpeed;
                detectionCollision(1);
            }

        }

        if(isMovingRight) {
            xPosition+=speed*multiplierOfSpeed;
            detectionCollision(4);
        }
       if(isMovingLeft){
           xPosition-=speed*multiplierOfSpeed;
           detectionCollision(3);
       }
       if(isMovingBackward){
           yPosition+=speed*multiplierOfSpeed;
           detectionCollision(6);
       }
       if(isMovingForward) {
           yPosition-=speed*multiplierOfSpeed;
           detectionCollision(5);

       }


      // System.out.println(xPosition);



        detectionCollision(0);
       // System.out.println(collision[5]);
        xPosition=  (int)((xPosition)*1000)/1000.0;
        yPosition=  (int)((yPosition)*1000)/1000.0;
        zPosition=  (int)((zPosition)*1000)/1000.0;
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
   public boolean[] detectionCollision(int num){
       boolean[] collision=new boolean[7];

       int xPosUnder=(int)(xPosition+0.5);
       if(xPosition<0)xPosUnder=(int)(xPosition-0.5);
       int yPosUnder=(int)(yPosition+0.5);
       if(yPosition<0)yPosUnder=(int)(yPosition-0.5);
       int zPosUnder=(int)(zPosition);
       if(zPosition<0)zPosUnder=(int)(zPosition-1);
       int zPosAbove=(int)(zPosition+height/Cube.defaultSize);
       if(zPosition<0)zPosAbove=(int)(zPosition+height/Cube.defaultSize-1);


       double forOtherSensitivity=0.04;




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

       int zPosUnderForOther=(int)(zPosition+forOtherSensitivity);
       if(zPosition<0)zPosUnderForOther=(int)(zPosition-1+forOtherSensitivity);

       int zPosAboveForOther=(int)(zPosition+height/Cube.defaultSize-forOtherSensitivity);
       if(zPosition<0)zPosAboveForOther=(int)(zPosition+height/Cube.defaultSize-1-forOtherSensitivity);

       //System.out.println(zPosUnder);



       //System.out.println(xLeftPos+" | "+yFrontPos+" | "+zPosAbove);
      //System.out.println(zPosUnderForOther+" | "+zPosAboveForOther);
       int numCub=CubeContainer.numberOfCubes;
       int numCubZ=CubeContainer.numberOfCubesZ;


       if(Math.abs(xLeftPosForOther)>numCub-5)xLeftPosForOther=numCub-5;
       if(Math.abs(xRightPosForOther)>numCub-5)xRightPosForOther=numCub-5;
       if(Math.abs(yFrontPosForOther)>numCub-5)yFrontPosForOther=numCub-5;
       if(Math.abs(yBackPosForOther)>numCub-5)yBackPosForOther=numCub-5;
       if(Math.abs(zPosUnderForOther)>numCubZ-5)zPosUnderForOther=numCub-5;
       if(Math.abs(zPosAboveForOther)>numCubZ-5)zPosAboveForOther=numCub-5;

       if(Math.abs(xLeftPos)>numCub-5)xLeftPos=numCub-5;
       if(Math.abs(xRightPos)>numCub-5)xRightPos=numCub-5;
       if(Math.abs(yFrontPos)>numCub-5)yFrontPos=numCub-5;
       if(Math.abs(yBackPos)>numCub-5)yBackPos=numCub-5;
       if(Math.abs(zPosUnder)>numCubZ-5)zPosUnder=numCub-5;
       if(Math.abs(zPosAbove)>numCubZ-5)zPosAbove=numCub-5;





    if(num==1 ||num==0)
       collision[1]= CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPosForOther][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPosForOther][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPosForOther][numCubZ+zPosUnder]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPosForOther][numCubZ+zPosUnder];

    if(num==2 ||num==0)
       collision[2]= CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPosForOther][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPosForOther][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPosForOther][numCubZ+zPosAbove]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPosForOther][numCubZ+zPosAbove];


       if(num==3 ||num==0)
       collision[3]= CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yFrontPosForOther][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xLeftPos][numCub+yBackPosForOther][numCubZ+zPosAboveForOther];
       if(num==4 ||num==0)

           collision[4]= CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPosForOther][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yFrontPosForOther][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xRightPos][numCub+yBackPosForOther][numCubZ+zPosAboveForOther];
       if(num==5 ||num==0)

           collision[5]= CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yFrontPos][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yFrontPos][numCubZ+zPosAboveForOther];
       if(num==6 ||num==0)
           collision[6]= CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPos][numCubZ+zPosUnderForOther]||
               CubeContainer.cubePosition[numCub+xRightPosForOther][numCub+yBackPos][numCubZ+zPosAboveForOther]||
               CubeContainer.cubePosition[numCub+xLeftPosForOther][numCub+yBackPos][numCubZ+zPosAboveForOther];


       double sensitivity=0.5;
       //System.out.println( collision[5]);
      // System.out.println(collision[5]+" "+xRightPosForOther+" "+xPosition);


       if(collision[1]&&zPosUnder+1-zPosition<sensitivity&&(isMovingDown||zVelocity<0)){
           zPosition=zPosUnder+1.00000001;
           zVelocity=0;
          // isMovingDown=false;
       }
       if(collision[2]&&zPosition-zPosAbove+height/Cube.defaultSize<sensitivity&&(isMovingUp||zVelocity>0)){
           zPosition=zPosAbove-height/Cube.defaultSize;
           zVelocity=0;
          //isMovingUp=false;
       }
       if(collision[3]&&(xLeftPos+1-(1-width/Cube.defaultSize)/2)-xPosition<sensitivity&&isMovingLeft){

           xPosition=xLeftPos+1-(1-width/Cube.defaultSize)/2;
         // isMovingLeft=false;

       }
       if(collision[4]&&xPosition-(xRightPos-1+(1-width/Cube.defaultSize)/2)<sensitivity&&isMovingRight){

           xPosition=xRightPos-1+(1-width/Cube.defaultSize)/2;
        //   isMovingRight=false;
       }
       if(collision[5]&&(yFrontPos+1-(1-depth/Cube.defaultSize)/2)-yPosition<sensitivity&&isMovingForward){

           yPosition=yFrontPos+1-(1-depth/Cube.defaultSize)/2;
         // isMovingForward=false;
       }
       if(collision[6]&&yPosition-(yBackPos-1+(1-depth/Cube.defaultSize)/2)<sensitivity&&isMovingBackward){

           yPosition=yBackPos-1+(1-depth/Cube.defaultSize)/2;
        //   isMovingBackward=false;
       }




       for(var i=1;i<7;i++){
           if (collision[i]) {
               collision[0] = true;
               break;
           }
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

    public void flySwitch(){
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
                isMovingLeft=true;
                break;

            case 68:
                isMovingRight = true;
                break;
            case 83:
                isMovingBackward=true;
                break;

            case 87:
                isMovingForward=true;
                break;
            case 89:
                if(isFlying)isMovingDown=true;
                break;

            case 88:
                if(isFlying)isMovingUp=true;
                break;
            case 70:
                flySwitch();
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
                isMovingLeft=false;
                break;

            case 68:
                isMovingRight=false;
                break;
            case 83:
                isMovingBackward=false;
                break;

            case 87:
                isMovingForward=false;
                break;
            case 89:
                if(isFlying)isMovingDown=false;
                break;

            case 88:
                if(isFlying)isMovingUp=false;
                break;
            case 16:
                isSlowing=false;
                break;
        }
    }
}
