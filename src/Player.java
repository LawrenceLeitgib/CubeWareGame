import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {
    static double xPosition;
    static double yPosition;
    static double zPosition;

    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static int cubeAway=5;

    int width=100;
    int height=100;

    double speed=10.0;

    double xVelocity;
    double yVelocity;
    double zVelocity;

    boolean isSlowing=false;

    Player(int GAME_WIDTH,int GAME_HEIGHT,double positionX,double positionY,double positionZ){
        Player.xPosition=positionX;
        Player.yPosition=positionY;
        Player.yPosition=positionZ;
        Player.zPosition=positionZ;
        Player.GAME_WIDTH =GAME_WIDTH;
        Player.GAME_HEIGHT =GAME_HEIGHT;



    }
    public void updateData(double deltaTime){
        if (isSlowing) {
            xPosition += this.xVelocity/10.0;
            yPosition += this.yVelocity/10.0;
            zPosition += this.zVelocity/10.0;
        }else{
            xPosition += this.xVelocity;
            yPosition += this.yVelocity;
            zPosition += this.zVelocity;
        }

        for(var i=0;i<CubeContainer.cubePositionX.length;i++){
            if(CubeContainer.cubePositionX[i])
                for (var j = 0; j < CubeContainer.cubePositionY.length; j++) {
                    if(CubeContainer.cubePositionY[j])
                        for (var k = 0; k <CubeContainer.cubePositionZ.length ; k++) {
                            if (CubeContainer.cubePosition[i][j][k]) {
                                detectionCollision(i-CubeContainer.numberOfCubes,j-CubeContainer.numberOfCubes,k-CubeContainer.numberOfCubesZ);
                                //System.out.println(detectionCollision(i,j,k));


                                }

                            }
                        }
                }
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

    public void draw(Graphics g){
        double sizeRatio=GAME_HEIGHT/(cubeAway*GameGrid.depthRatio+GAME_HEIGHT);

        double newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio);
        //newPosY+=zPosition*depth*sizeRatio;
        double newWidth=  (width*sizeRatio);
        double newHeight=  (height*sizeRatio);
        double newPosX=  (GAME_WIDTH/2.0);



        g.setColor(new Color(3, 40, 252));
        //g.fillRect(GAME_WIDTH/2-width/2,2*GAME_HEIGHT/3-height/2,width,height);
        //g.fillOval(GAME_WIDTH/2-width/2, (int) (3*GAME_HEIGHT/4-height*CubeContainer.depthRatio/4),width, (int) (height*CubeContainer.depthRatio/2));
        g.fillRect((int) (newPosX-newWidth/2.0), (int) (newPosY-newHeight), (int) newWidth, (int) newHeight);

        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.PLAIN,16));
        g.setColor(Color.red);
        g.drawString(String.valueOf(yPosition),15,90);
        g.drawString(Double.toString(xPosition),15,70);
        g.drawString(Double.toString(zPosition),15,110);
    }

    public boolean detectionCollision(int x,int y,int z){
        double playerLeft=xPosition;
        double playerRight=xPosition+width;
        double playerFront=yPosition;
        double playerBack=yPosition+height;
        //System.out.println(yPosition>(y)*Cube.depth&&yPosition<(y+1)*Cube.depth);
        //System.out.println( xPosition+width>(x-0.5)*Cube.width&&xPosition-width<(x+0.5)*Cube.width);
        if(xPosition+width>(x-0.5)*Cube.width&&xPosition-width<(x+0.5)*Cube.width){
            if(yPosition>y*Cube.depth&&yPosition<(y+1)*Cube.depth){
                return true;
            }

        }
        return false;
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
                setZVelocity(-speed);
                break;

            case 88:
                setZVelocity(speed);
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
