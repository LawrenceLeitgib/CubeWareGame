import java.awt.*;

public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Player player;



    int squareLength=40;

    static double PFX;
    static double PFY;
    CubeContainer cubeContainer;

    static double depthRatio=1/1.0;




    GameGrid(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);

        cubeContainer=new CubeContainer(depthRatio);

    }

    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
        PFX=GAME_WIDTH/2.0;
    }

    public static void setGameWidth(int gameWidth) {GAME_WIDTH = gameWidth; PFY=GAME_HEIGHT/3.0;}

    public void newPlayer(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);

    }
    public void updateData(double deltaTime){
        player.updateData(deltaTime);
        cubeContainer.updateData(deltaTime, player.xPosition, player.yPosition, player.zPosition);
    }
    public void draw(Graphics g){

        int numOfBlocksXaxis=1000;
        int numOfBlocksYaxis=1000;
        int sizeOfBlocks=Cube.width;
        int xGrillNumToAdd= (int) player.xPosition;
        int yGrillNumToAdd= (int) player.yPosition;
        double yValue;

        while(xGrillNumToAdd>sizeOfBlocks){
            xGrillNumToAdd-=sizeOfBlocks;
        }

        while(xGrillNumToAdd<sizeOfBlocks){
            xGrillNumToAdd+=sizeOfBlocks;

        }

        while(yGrillNumToAdd>sizeOfBlocks){
            yGrillNumToAdd-=sizeOfBlocks;

        }
        while(yGrillNumToAdd<sizeOfBlocks){
            yGrillNumToAdd+=sizeOfBlocks;

        }
        g.setColor(new Color(147, 196, 49));
        g.fillRect(0, (int) PFY,GAME_WIDTH,2*GAME_HEIGHT/3);
        //g.fillOval();

        g.setColor(new Color(119, 133, 17));

        for(var i=-numOfBlocksXaxis+1;i<=numOfBlocksXaxis;i++ ){
            g.drawLine(GAME_WIDTH/2+sizeOfBlocks/2+i*sizeOfBlocks-xGrillNumToAdd,GAME_HEIGHT, (int) PFX, (int) PFY);
        }
        //double sum=0;
        for(var i=0;i<numOfBlocksYaxis;i++ ){

            yValue=  (2*GAME_HEIGHT/3.0*(GAME_HEIGHT/((sizeOfBlocks*(i-10.0)+yGrillNumToAdd)*depthRatio+GAME_HEIGHT))+GAME_HEIGHT/3.0);
            if ((int)yValue<GAME_HEIGHT/3)yValue=GAME_HEIGHT/3;
            //g.drawLine(0,(yValue),  GAME_WIDTH,  ( yValue));
            g.fillRect(0, (int)(yValue), GAME_WIDTH , 2);


        }



        cubeContainer.draw(g);

        g.setColor(Color.red);



        player.draw(g);

        g.setColor(Color.RED);
        g.fillOval(GAME_WIDTH/2-4,GAME_HEIGHT/3-4,8,8);

    }



}
