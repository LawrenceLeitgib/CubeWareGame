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
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0);
        cube = new Cube(GAME_WIDTH,GAME_HEIGHT,0,0);
        //cube2 = new Cube(GAME_WIDTH,GAME_HEIGHT,0,-120);
        //cube3=new Cube(GAME_WIDTH,GAME_HEIGHT,0,-240);
        PFX=GAME_WIDTH/2.0;
        PFY=GAME_HEIGHT/3.0;

    }

    public void newPlayer(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0);

    }
    public void updateData(double deltaTime){
        player.updateData(deltaTime);


    }
    public void draw(Graphics g){
        int[] grillCoord=playerCoordToGrillCoord(player.xPosition, player.yPosition);
        int numOfBlocksXaxis=100;
        int SizeOfBlocks=100;
        int xGrillNumToAdd= (int) player.xPosition;
        //xGrillNumToAdd= 0;
        int yGrillNumToAdd= (int)- player.yPosition;
        //System.out.println( grillCoord[0]);
        while(xGrillNumToAdd>SizeOfBlocks){
            xGrillNumToAdd-=SizeOfBlocks;

        }
        while(xGrillNumToAdd<SizeOfBlocks){
            xGrillNumToAdd+=SizeOfBlocks;

        }



        g.setColor(Color.BLACK);

        for(var i=-numOfBlocksXaxis+1;i<=numOfBlocksXaxis;i++ ){

            g.drawLine(GAME_WIDTH/2+SizeOfBlocks/2+i*SizeOfBlocks-xGrillNumToAdd,GAME_HEIGHT, (int) PFX, (int) PFY);
        }
        Cube[] cubes=new Cube[100];
        //cube3.draw(g,grillCoord,player.xPosition,player.yPosition);
        //cube2.draw(g,grillCoord,player.xPosition,player.yPosition);
        cube.draw(g,grillCoord,player.xPosition,player.yPosition);
        for(var i=0;i<10;i++){
            for(var j=0;j<10;j++){
                cubes[j*10+i]=new Cube(GAME_WIDTH,GAME_HEIGHT,100*i,100*j);
                //System.out.println(j*10+i);
            }
        }

        for(var i=0;i<100;i++){
            cubes[i].draw(g,grillCoord, player.xPosition, player.yPosition);
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
