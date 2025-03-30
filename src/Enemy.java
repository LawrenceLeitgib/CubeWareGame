import java.awt.*;

public class Enemy {
    double xPosition;
    double yPosition;

    double zPosition;

    double height=200;
    double width=80;
    double depth=80;

    double newHeight;
    double newWidth;
    double newDepth;



    double newPosX;
    double newPosY;
    double speed;
    double MaxHP;
    double HP;
    double[][] corners;

    double xVelocity=1;
    double yVelocity=1;

    Enemy(int x,int y,int z){
        xPosition=x;
        yPosition=y;
        zPosition=z;
    }
    public void updateData(double deltaTime) {
        xPosition+=xVelocity/100;
        yPosition+=yVelocity/100;
        double correction=0.5;
        while(GameGrid.angleForXRotation>=Math.PI*2){
            GameGrid.angleForXRotation-=Math.PI*2;
        }
        while(GameGrid.angleForXRotation<0){
            GameGrid.angleForXRotation+=Math.PI*2;
        }
        double difPosXA=(xPosition-correction-Player.xPosition);
        double difPosYA= (yPosition+correction-Player.yPosition);
        double xPositionA= (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+correction);
        double yPositionA=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-correction);
        double difPosXR=((Player.xPosition-xPositionA)*Cube.defaultSize);
        double difPosYR= ((Player.yPosition-(yPositionA-Player.cubeAway))*Cube.defaultSize);
        double difPosZ=((Player.zPosition-zPosition)*Cube.defaultSize);
        double sizeRatio=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);

        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        newWidth=  (width*sizeRatio);
        newHeight=  (height*sizeRatio);
        newDepth=  (depth*sizeRatio);
        newPosX=  (GameGrid.PVX-((Player.xPosition-xPositionA)*width)*sizeRatio-newWidth/2);
        corners=getCorners(newPosX,newPosY,newWidth,newHeight,difPosZ,difPosXA,difPosYA);

    }

    public void draw(Graphics g){
        System.out.println(newPosX);
        g.setColor(Color.blue);
        //g.drawRect((int) (newPosX), (int)( newPosY-newHeight), (int) newWidth, (int) newHeight);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[1][0], (int) corners[1][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[2][0], (int) corners[2][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[3][0], (int) corners[3][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[0][0], (int) corners[0][1]);
        g.drawLine((int) corners[0][0], (int) corners[0][1], (int) corners[4][0], (int) corners[4][1]);
        g.drawLine((int) corners[1][0], (int) corners[1][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[2][0], (int) corners[2][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[3][0], (int) corners[3][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[4][0], (int) corners[4][1], (int) corners[5][0], (int) corners[5][1]);
        g.drawLine((int) corners[5][0], (int) corners[5][1], (int) corners[6][0], (int) corners[6][1]);
        g.drawLine((int) corners[6][0], (int) corners[6][1], (int) corners[7][0], (int) corners[7][1]);
        g.drawLine((int) corners[7][0], (int) corners[7][1], (int) corners[4][0], (int) corners[4][1]);

    }
    public double[][] getCorners(double newPosX,double newPosY,double newWidth,double newHeight,double difPosZ,double difPosXA,double difPosYA){
        double sizeRatioValue=(GameGrid.depthRatio-GameGrid.GAME_HEIGHT)/GameGrid.depthRatio;
        double xCorrectorForRotation=0.5;

        double difPosXARight=(xPosition+width/Cube.defaultSize-xCorrectorForRotation-Player.xPosition);
        double yPositionARight=  (Player.yPosition-difPosXARight*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-xCorrectorForRotation);
        double xPositionARight=  (Player.xPosition+difPosXARight*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+xCorrectorForRotation);;
        double difPosYRRight= ((Player.yPosition-(yPositionARight-Player.cubeAway))*depth);
        double sizeRatioRight=GameGrid.GAME_HEIGHT/(difPosYRRight*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);


        if(difPosYRRight<sizeRatioValue){
            sizeRatioRight=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRRight-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }
        double newPosYRight=((GameGrid.PVY-GameGrid.PFY)*sizeRatioRight+GameGrid.PFY+difPosZ*sizeRatioRight);
        double newPosXRight=  (GameGrid.PVX-((Player.xPosition-xPositionARight)*width)*sizeRatioRight-(width*sizeRatioRight)/2);

        double difPosYAFront=(yPosition-depth/Cube.defaultSize+xCorrectorForRotation-Player.yPosition);
        double yPositionAFront=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYAFront*Math.cos(GameGrid.angleForXRotation)-xCorrectorForRotation);
        double xPositionAFront=  (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYAFront*Math.sin(GameGrid.angleForXRotation)+xCorrectorForRotation);
        double difPosYRFront= ((Player.yPosition-(yPositionAFront-Player.cubeAway))*depth);
        double sizeRatioFront=GameGrid.GAME_HEIGHT/(difPosYRFront*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);

        if(difPosYRFront<sizeRatioValue){
            sizeRatioFront=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRFront-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }

        double newPosYFront=((GameGrid.PVY-GameGrid.PFY)*sizeRatioFront+GameGrid.PFY+difPosZ*sizeRatioFront);
        double newPosXFront=  (GameGrid.PVX-((Player.xPosition-xPositionAFront)*width)*sizeRatioFront-(width*sizeRatioFront)/2);


        double yPositionAFrontRight=  (Player.yPosition-difPosXARight*Math.sin(GameGrid.angleForXRotation)+difPosYAFront*Math.cos(GameGrid.angleForXRotation)-xCorrectorForRotation);
        double xPositionAFrontRight=  (Player.xPosition+difPosXARight*Math.cos(GameGrid.angleForXRotation)+difPosYAFront*Math.sin(GameGrid.angleForXRotation)+xCorrectorForRotation);
        double difPosYRFrontRight= ((Player.yPosition-(yPositionAFrontRight-Player.cubeAway))*depth);
        double sizeRatioFrontRight=GameGrid.GAME_HEIGHT/(difPosYRFrontRight*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        if(difPosYRFrontRight<sizeRatioValue){
            sizeRatioFrontRight=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRFrontRight-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

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


}
