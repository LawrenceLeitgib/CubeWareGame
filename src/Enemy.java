import java.awt.*;

public class Enemy {
    boolean marketForDeletion;
    double xPosition;
    double yPosition;

    double zPosition;

    double height=180;
    double width=100;
    double depth=100;

    double newHeight;
    double newWidth;
    double newDepth;

    double speed=.5;


    double newPosX;
    double newPosY;
    double MaxHP=100;
    double HP=100;
    double[][] corners;

    double xVelocity=0;
    double yVelocity=0;
    double sizeRatio;
    Enemy(double x,double y,double z){
        xPosition=x;
        yPosition=y;
        zPosition=z;
    }
    public void updateData(double deltaTime) {



        double angleWithPlayer=0;
        angleWithPlayer=Math.atan((Player.yPosition-yPosition)/(Player.xPosition-xPosition));

        if(Player.xPosition-xPosition>0){
            angleWithPlayer=Math.PI+angleWithPlayer;
        }else  if(Player.xPosition-xPosition<0&&Player.yPosition-yPosition>0){
            angleWithPlayer=2*Math.PI+angleWithPlayer;
        }
        if(Player.xPosition-xPosition==0){
            if(Player.yPosition-yPosition<=0)angleWithPlayer=Math.PI/2;
            if(Player.yPosition-yPosition>0)angleWithPlayer=3*Math.PI/2;

        }



        xPosition-=Math.cos(angleWithPlayer)*speed*deltaTime;
         yPosition-=Math.sin(angleWithPlayer)*speed*deltaTime;

         for(var i=0;i<GameGrid.fireBallContainer.fireBallsList.length;i++){
             if(GameGrid.fireBallContainer.fireBallsList[i]){

             if(detectionCollisionWithBall(GameGrid.fireBallContainer.fireBalls[i].xPosition,GameGrid.fireBallContainer.fireBalls[i].yPosition,GameGrid.fireBallContainer.fireBalls[i].zPosition,GameGrid.fireBallContainer.fireBalls[i].size)){
                 yPosition+=GameGrid.fireBallContainer.fireBalls[i].yVelocity/2*deltaTime;
                 xPosition+=GameGrid.fireBallContainer.fireBalls[i].xVelocity/2*deltaTime;
                 if(HP<GameGrid.fireBallContainer.fireBalls[i].damage){
                     GameGrid.fireBallContainer.fireBalls[i].damage-=HP;
                     HP=0;
                 }else{
                     HP-=GameGrid.fireBallContainer.fireBalls[i].damage;
                     GameGrid.fireBallContainer.fireBalls[i]=null;
                     GameGrid.fireBallContainer.fireBallsList[i]=false;
                 }



             }
             }
         }

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
        sizeRatio=GameGrid.GAME_HEIGHT/(difPosYR*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);

        newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        newWidth=  (width*sizeRatio);
        newHeight=  (height*sizeRatio);
        newDepth=  (depth*sizeRatio);
        newPosX=  (GameGrid.PVX-((Player.xPosition-xPositionA)*Cube.defaultSize)*sizeRatio-Cube.defaultSize*sizeRatio/2);
        corners=getCorners(newPosX,newPosY,newWidth,newHeight,difPosZ,difPosXA,difPosYA);


        if(Math.sqrt(Math.pow(Player.xPosition-xPosition,2)+Math.pow(Player.yPosition-yPosition,2))>40)  this.marketForDeletion=true;
        if(HP<=0){
            HP=0;
            Stats.xp+=25;
            this.marketForDeletion=true;
        }

    }

    public void draw(Graphics g){
       // width-=.4;
       // depth-=.4;
       // width=50;
       // depth=50;
        //System.out.println(width);

        if(newPosY+newHeight<0)return;
        if(newPosY>GameGrid.GAME_HEIGHT*2)return;
        if(newPosX>GameGrid.GAME_WIDTH*2)return;
        if(newPosX<-GameGrid.GAME_WIDTH)return;

        for(var i=0;i<8;i++){
            if(corners[i][1]+newHeight<0)return;
            if(corners[i][1]>GameGrid.GAME_HEIGHT*2)return;
            if(corners[i][0]>GameGrid.GAME_WIDTH*2)return;
            if(corners[i][0]<-GameGrid.GAME_WIDTH)return;

        }
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

        double difPosXARight=(xPosition+Cube.defaultSize/Cube.defaultSize-xCorrectorForRotation-Player.xPosition);
        double yPositionARight=  (Player.yPosition-difPosXARight*Math.sin(GameGrid.angleForXRotation)+difPosYA*Math.cos(GameGrid.angleForXRotation)-xCorrectorForRotation);
        double xPositionARight=  (Player.xPosition+difPosXARight*Math.cos(GameGrid.angleForXRotation)+difPosYA*Math.sin(GameGrid.angleForXRotation)+xCorrectorForRotation);;
        double difPosYRRight= ((Player.yPosition-(yPositionARight-Player.cubeAway))*Cube.defaultSize);
        double sizeRatioRight=GameGrid.GAME_HEIGHT/(difPosYRRight*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);


        if(difPosYRRight<sizeRatioValue){
            sizeRatioRight=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRRight-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }
        double newPosYRight=((GameGrid.PVY-GameGrid.PFY)*sizeRatioRight+GameGrid.PFY+difPosZ*sizeRatioRight);
        double newPosXRight=  (GameGrid.PVX-((Player.xPosition-xPositionARight)*Cube.defaultSize)*sizeRatioRight-(Cube.defaultSize*sizeRatioRight)/2);

        double difPosYAFront=(yPosition-Cube.defaultSize/Cube.defaultSize+xCorrectorForRotation-Player.yPosition);
        double yPositionAFront=  (Player.yPosition-difPosXA*Math.sin(GameGrid.angleForXRotation)+difPosYAFront*Math.cos(GameGrid.angleForXRotation)-xCorrectorForRotation);
        double xPositionAFront=  (Player.xPosition+difPosXA*Math.cos(GameGrid.angleForXRotation)+difPosYAFront*Math.sin(GameGrid.angleForXRotation)+xCorrectorForRotation);
        double difPosYRFront= ((Player.yPosition-(yPositionAFront-Player.cubeAway))*Cube.defaultSize);
        double sizeRatioFront=GameGrid.GAME_HEIGHT/(difPosYRFront*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);

        if(difPosYRFront<sizeRatioValue){
            sizeRatioFront=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRFront-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }

        double newPosYFront=((GameGrid.PVY-GameGrid.PFY)*sizeRatioFront+GameGrid.PFY+difPosZ*sizeRatioFront);
        double newPosXFront=  (GameGrid.PVX-((Player.xPosition-xPositionAFront)*Cube.defaultSize)*sizeRatioFront-(Cube.defaultSize*sizeRatioFront)/2);


        double yPositionAFrontRight=  (Player.yPosition-difPosXARight*Math.sin(GameGrid.angleForXRotation)+difPosYAFront*Math.cos(GameGrid.angleForXRotation)-xCorrectorForRotation);
        double xPositionAFrontRight=  (Player.xPosition+difPosXARight*Math.cos(GameGrid.angleForXRotation)+difPosYAFront*Math.sin(GameGrid.angleForXRotation)+xCorrectorForRotation);
        double difPosYRFrontRight= ((Player.yPosition-(yPositionAFrontRight-Player.cubeAway))*Cube.defaultSize);
        double sizeRatioFrontRight=GameGrid.GAME_HEIGHT/(difPosYRFrontRight*1.0*GameGrid.depthRatio+GameGrid.GAME_HEIGHT);
        if(difPosYRFrontRight<sizeRatioValue){
            sizeRatioFrontRight=(-GameGrid.GAME_HEIGHT*GameGrid.depthRatio)/(Math.pow(sizeRatioValue*GameGrid.depthRatio+GameGrid.GAME_HEIGHT,2))*(difPosYRFrontRight-sizeRatioValue)+GameGrid.GAME_HEIGHT/GameGrid.depthRatio;

        }


        double newPosYFrontRight=((GameGrid.PVY-GameGrid.PFY)*sizeRatioFrontRight+GameGrid.PFY+difPosZ*sizeRatioFrontRight);
        double newPosXFrontRight=  (GameGrid.PVX-((Player.xPosition-xPositionAFrontRight)*Cube.defaultSize)*sizeRatioFrontRight-(Cube.defaultSize*sizeRatioFrontRight)/2);







        //if(xPosition==5)System.out.println(deltaYRight);






        double [][] corners=new double[8][2];
        corners[0][1]=newPosY;
        corners[1][1]=newPosYRight;
        corners[2][1]=newPosYFrontRight;
        corners[3][1]=newPosYFront;




        corners[0][0]=newPosX;
        corners[1][0]=newPosXRight;
        corners[2][0]=newPosXFrontRight;
        corners[3][0]=newPosXFront;



        double [][] corners2=new double[4][2];
        double [][] corners3=new double[4][2];



        corners2[0][0]=(corners[0][0]*(width/2+Cube.defaultSize/2.0)+corners[1][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners2[0][1]=(corners[0][1]*(width/2+Cube.defaultSize/2.0)+corners[1][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);

        corners2[1][0]=(corners[1][0]*(depth/2+Cube.defaultSize/2.0)+corners[2][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners2[1][1]=(corners[1][1]*(depth/2+Cube.defaultSize/2.0)+corners[2][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners2[2][0]=(corners[2][0]*(width/2+Cube.defaultSize/2.0)+corners[3][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners2[2][1]=(corners[2][1]*(width/2+Cube.defaultSize/2.0)+corners[3][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);

        corners2[3][0]=(corners[3][0]*(depth/2+Cube.defaultSize/2.0)+corners[0][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners2[3][1]=(corners[3][1]*(depth/2+Cube.defaultSize/2.0)+corners[0][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);


        corners3[0][0]=(corners[0][0]*(depth/2+Cube.defaultSize/2.0)+corners[3][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners3[0][1]=(corners[0][1]*(depth/2+Cube.defaultSize/2.0)+corners[3][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners3[1][0]=(corners[1][0]*(width/2+Cube.defaultSize/2.0)+corners[0][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners3[1][1]=(corners[1][1]*(width/2+Cube.defaultSize/2.0)+corners[0][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);

        corners3[2][0]=(corners[2][0]*(depth/2+Cube.defaultSize/2.0)+corners[1][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners3[2][1]=(corners[2][1]*(depth/2+Cube.defaultSize/2.0)+corners[1][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners3[3][0]=(corners[3][0]*(width/2+Cube.defaultSize/2.0)+corners[2][0]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);
        corners3[3][1]=(corners[3][1]*(width/2+Cube.defaultSize/2.0)+corners[2][1]*(Cube.defaultSize/2.0-width/2))/(Cube.defaultSize);



        /*

        corners[0][0]=corners2[0][0];
        corners[0][1]=corners2[0][1];
        corners[1][0]=corners2[1][0];
        corners[1][1]=corners2[1][1];
        corners[2][0]=corners2[2][0];
        corners[2][1]=corners2[2][1];
        corners[3][0]=corners2[3][0];
        corners[3][1]=corners2[3][1];
*/
        /*
        corners[0][0]=corners3[0][0];
        corners[0][1]=corners3[0][1];
        corners[1][0]=corners3[1][0];
        corners[1][1]=corners3[1][1];
        corners[2][0]=corners3[2][0];
        corners[2][1]=corners3[2][1];
        corners[3][0]=corners3[3][0];
        corners[3][1]=corners3[3][1];

         */

        corners[0][0]=(corners2[0][0]*(depth/2+Cube.defaultSize/2.0)+corners3[3][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[0][1]=(corners2[0][1]*(depth/2+Cube.defaultSize/2.0)+corners3[3][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);


        corners[1][0]=(corners3[1][0]*(depth/2+Cube.defaultSize/2.0)+corners2[2][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[1][1]=(corners3[1][1]*(depth/2+Cube.defaultSize/2.0)+corners2[2][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);

        corners[2][0]=(corners2[2][0]*(depth/2+Cube.defaultSize/2.0)+corners3[1][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[2][1]=(corners2[2][1]*(depth/2+Cube.defaultSize/2.0)+corners3[1][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);


        corners[3][0]=(corners3[3][0]*(depth/2+Cube.defaultSize/2.0)+corners2[0][0]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);
        corners[3][1]=(corners3[3][1]*(depth/2+Cube.defaultSize/2.0)+corners2[0][1]*(Cube.defaultSize/2.0-depth/2))/(Cube.defaultSize);



        /*
        corners[4][1]=corners[0][1]-height*sizeRatio;
        corners[5][1]=corners[1][1]-height*sizeRatioRight;
        corners[6][1]=corners[2][1]-height*sizeRatioFrontRight;
        corners[7][1]=corners[3][1]-height*sizeRatioFront;

         */

        //newPosY=((GameGrid.PVY-GameGrid.PFY)*sizeRatio+GameGrid.PFY+difPosZ*sizeRatio);
        //newPosY-GameGrid.PFY=(GameGrid.PVY-GameGrid.PFY+difPosZ)*sizeRatio

        double sizeRatio1=(corners[0][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY+(Player.zPosition-zPosition)*Cube.defaultSize);
        double sizeRatio2=(corners[1][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY+(Player.zPosition-zPosition)*Cube.defaultSize);
        double sizeRatio3=(corners[2][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY+(Player.zPosition-zPosition)*Cube.defaultSize);
        double sizeRatio4=(corners[3][1]-GameGrid.PFY)/(GameGrid.PVY-GameGrid.PFY+(Player.zPosition-zPosition)*Cube.defaultSize);



        corners[4][0]= corners[0][0];
        corners[5][0]=corners[1][0];
        corners[6][0]= corners[2][0];
        corners[7][0]=corners[3][0];
        corners[4][1]=corners[0][1]-height*sizeRatio1;
        corners[5][1]=corners[1][1]-height*sizeRatio2;
        corners[6][1]=corners[2][1]-height*sizeRatio3;
        corners[7][1]=corners[3][1]-height*sizeRatio4;



        return corners;
    }

    public double[][] getCorners(double newPosX,double newPosY,double newWidth,double newHeight,double difPosZ,double difPosXA,double difPosYA,String PASTOUCHE){
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

    public boolean detectionCollisionWithBall(double ballXPos,double ballYPos,double ballzPos,double ballSize){
        /*
        if(ballXPos<xPosition+0.5&&ballXPos>xPosition-0.5)
            if(ballYPos>yPosition-1&&ballYPos<yPosition){
                return true;
            }
        */
        if(ballXPos<xPosition+(width/2)/Cube.defaultSize &&ballXPos>xPosition-(width/2)/Cube.defaultSize)
            if(ballYPos>yPosition-(depth/2+Cube.defaultSize/2.0)/Cube.defaultSize&&ballYPos<yPosition+(depth/2-Cube.defaultSize/2.0)/Cube.defaultSize){
                if(ballzPos>zPosition&&ballzPos<zPosition+height/Cube.defaultSize){

                    return true;
                }

            }
       // System.out.println(xPosition+(width/2-Cube.defaultSize/2.0)+","+(xPosition-(width/2-Cube.defaultSize/2.0)));
       // System.out.println(yPosition-(depth/2+Cube.defaultSize/2.0)/Cube.defaultSize+","+((depth/2-Cube.defaultSize/2.0)/Cube.defaultSize));
        // System.out.println((ballXPos<xPosition-(width/2-Cube.defaultSize/2.0)/Cube.defaultSize &&ballXPos>xPosition+(width/2-Cube.defaultSize/2.0)/Cube.defaultSize)+", "+(ballYPos>yPosition-(depth/2+Cube.defaultSize/2.0)/Cube.defaultSize&&ballYPos<yPosition+(depth/2-Cube.defaultSize/2.0)/Cube.defaultSize));

        return false;
    }

}
