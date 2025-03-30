import java.awt.*;

public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Player player;

    Cube cube;

    Cube cube2;

    Cube cube3;

    int squareLength=40;

    double PFX;
    double PFY;


    GameGrid(int GAME_WIDTH,int GAME_HEIGHT){
        this.GAME_WIDTH=GAME_WIDTH;
        this.GAME_HEIGHT=GAME_HEIGHT;
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);
        cube = new Cube(GAME_WIDTH,GAME_HEIGHT,0,0,0);
        cube2 = new Cube(GAME_WIDTH,GAME_HEIGHT,0,-100,0);
        cube3=new Cube(GAME_WIDTH,GAME_HEIGHT,0,-200,0);
        PFX=GAME_WIDTH/2.0;
        PFY=GAME_HEIGHT/3.0;

    }

    public void newPlayer(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0,0);

    }
    public void updateData(double deltaTime){
        player.updateData(deltaTime);


    }
    public void draw(Graphics g){
        int[] grillCoord=playerCoordToGrillCoord(player.xPosition, player.yPosition);
        int numOfBlocksXaxis=100;
        int numOfBlocksYaxis=100;
        int sizeOfBlocks=100;
        int xGrillNumToAdd= (int) player.xPosition;
        int yGrillNumToAdd= (int) player.yPosition;
        int yValue;
        double depthRatio=1/3.0;


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
        g.setColor(Color.BLACK);

        for(var i=-numOfBlocksXaxis+1;i<=numOfBlocksXaxis;i++ ){
            g.drawLine(GAME_WIDTH/2+sizeOfBlocks/2+i*sizeOfBlocks-xGrillNumToAdd,GAME_HEIGHT, (int) PFX, (int) PFY);
        }
        //double sum=0;
        for(var i=0;i<numOfBlocksYaxis;i++ ){
            yValue=(int)(2*GAME_HEIGHT/((i+3.0))/depthRatio+GAME_HEIGHT/3);
            g.drawLine(0,(int)(yValue), (int) GAME_WIDTH, (int) ( yValue));
            //if(i==1)System.out.println(yValue);
            //g.drawLine(0,(int)(GAME_HEIGHT-sum), (int) GAME_WIDTH, (int) ( GAME_HEIGHT-sum));
            //sum+=sizeOfBlocks*depthRatio*(GAME_HEIGHT/(i*1.0*sizeOfBlocks+GAME_HEIGHT));
            //yValue= (int) (GAME_HEIGHT-2/3.0*sizeOfBlocks*(i+1)*(GAME_HEIGHT/(i*1.0*sizeOfBlocks+GAME_HEIGHT)));
            //g.drawLine(0,(int)(yValue), (int) GAME_WIDTH, (int) ( yValue));


        }
        //sizeRatio=GAME_HEIGHT/(i*sizeOfBlocks*1.0+GAME_HEIGHT);
        //g.drawLine(0,(int)(GAME_HEIGHT-sizeOfBlocks*depthRatio), (int) GAME_WIDTH, (int) ( GAME_HEIGHT-sizeOfBlocks*depthRatio));
        //g.drawLine(0,(int)(GAME_HEIGHT-sizeOfBlocks*2*depthRatio*(GAME_HEIGHT/(1.0*sizeOfBlocks+GAME_HEIGHT))), (int) GAME_WIDTH, (int) (GAME_HEIGHT-sizeOfBlocks*2*depthRatio*(GAME_HEIGHT/(1.0*sizeOfBlocks+GAME_HEIGHT))));
        //g.drawLine(0,(int)(GAME_HEIGHT-sizeOfBlocks*3*depthRatio*(GAME_HEIGHT/(2.0*sizeOfBlocks+GAME_HEIGHT))), (int) GAME_WIDTH, (int) (GAME_HEIGHT-sizeOfBlocks*3*depthRatio*(GAME_HEIGHT/(2.0*sizeOfBlocks+GAME_HEIGHT))));
        //System.out.println(GAME_HEIGHT);

        //Cube[] cubes=new Cube[100];
        cube3.draw(g,grillCoord,player.xPosition,player.yPosition);
        cube2.draw(g,grillCoord,player.xPosition,player.yPosition);
        cube.draw(g,grillCoord,player.xPosition,player.yPosition);

        g.setColor(Color.red);


        for(var i=0;i<10;i++){
            for(var j=0;j<10;j++){
                //cubes[j*10+i]=new Cube(GAME_WIDTH,GAME_HEIGHT,100*i,100*j,0);
                //System.out.println(j*10+i);
            }
        }

        for(var i=0;i<100;i++){
            //cubes[i].draw(g,grillCoord, player.xPosition, player.yPosition);
        }




        player.draw(g);

        g.setColor(Color.RED);
        g.fillOval(GAME_WIDTH/2-4,GAME_HEIGHT/3-4,8,8);

    }

    public int[] playerCoordToGrillCoord(double x,double y){
        int[] newCorrd = new int[2];

        newCorrd[0]= (int) (-x+GAME_WIDTH/2);
        newCorrd[1]=(int) (-y+2*GAME_HEIGHT/3);


        return newCorrd;
    }

    public void loadChunks(Graphics g,int[] grillCoord ){





    }
    public void drawChunk(Graphics g,int[] grillCoord, int xPosChunkInt,int yPosChunkInt,int chunkSize){

    }
}
