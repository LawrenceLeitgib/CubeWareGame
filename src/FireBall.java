import java.awt.*;

public class FireBall {

    //double intialPosX;
    //double intialPosY;

    double xPosition;
    double yPosition;

    double zPosition;


    double newPosX;
    double newPosY;
    double newSize;

    double newPosX2;
    double newPosY2;
    double newSize2;
    double angleOfShout;
    double speed;
    int size;
    double damage;

    boolean marketForDeletion=false;

    double yVelocity;
    double xVelocity;





    FireBall(double xPos, double yPos, double angle, double speed, int size, double damage ){
        xPosition=xPos;
        yPosition=yPos-0.5;
        angleOfShout=angle;
        this.speed=speed;
        this.size=size;
        this.damage=damage;
        zPosition=Player.zPosition;


    }

    public void updateData(double deltaTime) {
        xVelocity=speed * Math.cos(angleOfShout);
        yVelocity=speed * Math.sin(angleOfShout);
        xPosition += xVelocity*deltaTime;
        yPosition +=yVelocity*deltaTime;
        double correctionY=0.5;
        double correctionX=0;
        double difPosXA = (xPosition - correctionX - Player.xPosition);
        double difPosYA = (yPosition + correctionY - Player.yPosition);

        if(Math.sqrt(Math.pow(difPosYA,2)+Math.pow(difPosXA,2))>20)marketForDeletion=true;
        if(damage<=0)marketForDeletion=true;


        double difPosZ=((Player.zPosition-zPosition)*Cube.defaultSize);

        double xPositionA = (Player.xPosition + difPosXA * Math.cos(GameGrid.angleForXRotation) + difPosYA * Math.sin(GameGrid.angleForXRotation) + correctionX);
        double yPositionA = (Player.yPosition - difPosXA * Math.sin(GameGrid.angleForXRotation) + difPosYA * Math.cos(GameGrid.angleForXRotation) - correctionY);
        double difPosXR=((Player.xPosition-xPositionA)*Cube.width);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*Cube.depth);
        double sizeRatio=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        double sizeRatio2=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio-80*sizeRatio);
        newSize=  (size*sizeRatio);
        newPosX=  (GameGrid.PVX-((Player.xPosition-xPositionA)*Cube.depth)*sizeRatio-newSize/2);
        newPosY2=((GameGrid.PVY-GameGrid.PFY)*sizeRatio2+GameGrid.PFY+difPosZ*sizeRatio+(Player.zPosition-2)*sizeRatio*Cube.defaultSize);
        newSize2=  (size*sizeRatio2);
        newPosX2=  (GameGrid.PVX-((Player.xPosition-xPositionA)*Cube.depth)*sizeRatio-newSize2/2);



    }
    public void draw(Graphics g){
        g.setColor(new Color(10,10,10,60));
        g.fillOval((int) (newPosX2), (int) (newPosY2-newSize2/2), (int) newSize2, (int) newSize2);
        g.setColor(Color.yellow);
        g.fillOval((int) (newPosX-newSize/5), (int) (newPosY-newSize/2-newSize/5), (int)( newSize+newSize/2.5), (int)(newSize+newSize/2.5));
        g.setColor(Color.orange);
        g.fillOval((int) (newPosX-newSize/10), (int) (newPosY-newSize/2-newSize/10), (int) ( newSize+newSize/5), (int) (newSize+newSize/5));
        g.setColor(Color.red);
        g.fillOval((int) (newPosX), (int) (newPosY-newSize/2), (int) newSize, (int) newSize);
    }

}
