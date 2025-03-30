import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    static double xPosition;
    static double yPosition;
    static double zPosition;

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static int cubeAway=2;

    static int width=80;
    static int height=200;

    static int depth=80;
    double speed=30;
    double gravityAcceleration=5.0;

    double xVelocity;
    double yVelocity;
    double zVelocity;


    boolean isSlowing=false;
    boolean isFlying=true;

    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double positionZ){
        Player.xPosition=positionX;
        Player.yPosition=0;
        Player.zPosition=positionZ;
        Player.GAME_WIDTH =GAME_WIDTH;
        Player.GAME_HEIGHT =GAME_HEIGHT;



    }
    public void updateData(double deltaTime){


       // System.out.println(collisionLeft);







        if (isSlowing) {
            xPosition += this.xVelocity/10.0;
            yPosition += this.yVelocity/10.0;
                if(isFlying)
                    zPosition += this.zVelocity/10.0;
                else zPosition += this.zVelocity;



        }else{

            xPosition += this.xVelocity;
            yPosition += this.yVelocity;
            zPosition += this.zVelocity;
        }


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
                if((collisionLeft||collisionRight))System.out.println("test");
                if((collisionFront||collisionBack))
                    yPosition -= this.yVelocity;
                if((collisionTop||collisionBottom))
                    zPosition -= this.zVelocity;
            }
            setZVelocity(0);

        }



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


        int[] xPointsList= new int[]{(int) (corners[0][0]+0.5), (int) (corners[1][0]+0.5),
                (int) (corners[2][0]+0.5), (int) (corners[3][0]+0.5)};
        int[] yPointsList=new int[]{(int) (corners[0][1]+0.5), (int) (corners[1][1]+0.5),
                (int) (corners[2][1]+0.5), (int) (corners[3][1]+0.5)};

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
    }
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
        /*
        collision[1]=xPosition+Cube.defaultSize-(Cube.defaultSize-width)/2.0==(x)*Cube.defaultSize  ;//left
        collision[2]=xPosition+(Cube.defaultSize-width)/2.0==(x+1)*Cube.defaultSize;//right
        collision[3]=yPosition+Cube.defaultSize-(Cube.defaultSize-width)/2.0==y*Cube.defaultSize;//front
        collision[4]=yPosition+(Cube.defaultSize-width)==(y+1)*Cube.defaultSize;//back
        collision[5]=zPosition+height==z*Cube.defaultSize;//top
        collision[6]=zPosition==(z+1)*Cube.defaultSize;//bottom
        System.out.println(collision[1]);

        */




        if(xPosition+Cube.defaultSize-(Cube.defaultSize-width)/2.0>(x)*Cube.defaultSize&&xPosition+(Cube.defaultSize-width)/2.0<(x+1)*Cube.defaultSize){
            if(yPosition+Cube.defaultSize-(Cube.defaultSize-width)/2.0>y*Cube.defaultSize&& yPosition+(Cube.defaultSize-width)<(y+1)*Cube.defaultSize){
                if  (zPosition+height>z*Cube.defaultSize&&zPosition<(z+1)*Cube.defaultSize){
                    collision[0]=true;
                }

            }

        }
        collision[1]=xPosition+Cube.defaultSize-(Cube.defaultSize-width)/2.0>(x)*Cube.defaultSize&&collision[0] ;//left
        collision[2]=xPosition+(Cube.defaultSize-width)/2.0<(x+1)*Cube.defaultSize &&collision[0];//right
        collision[3]=yPosition+Cube.defaultSize-(Cube.defaultSize-width)/2.0>y*Cube.defaultSize&&collision[0];//front
        collision[4]=yPosition+(Cube.defaultSize-width)<(y+1)*Cube.defaultSize&&collision[0];//back
        collision[5]=zPosition+height>z*Cube.defaultSize&&collision[0];//top
        collision[6]=zPosition<(z+1)*Cube.defaultSize&&collision[0];//bottom
       // System.out.println(collision[1]);


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
                setZVelocity(30);
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
