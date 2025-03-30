import java.awt.*;

public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Player player;

    Cube cube;

    Cube cube2;

    int squareLength=40;
    GameGrid(int GAME_WIDTH,int GAME_HEIGHT){
        this.GAME_WIDTH=GAME_WIDTH;
        this.GAME_HEIGHT=GAME_HEIGHT;
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0);
        cube = new Cube(GAME_WIDTH,GAME_HEIGHT,0,0);
        cube2 = new Cube(GAME_WIDTH,GAME_HEIGHT,100,0);

    }

    public void newPlayer(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0);

    }
    public void updateData(double deltaTime){
        player.updateData(deltaTime);


    }
    public void draw(Graphics g){
        int[] grillCoord=playerCoordToGrillCoord(player.xPosition, player.yPosition);
        cube.draw(g,grillCoord,player.xPosition,player.yPosition);
        //cube2.draw(g,grillCoord,player.xPosition,player.yPosition);

        g.setColor(Color.black);

        /*
        for(var i=-1;i<1;i+=1){
            for(var j=-1;j<1;j+=1)
            g.fillRect(grillCoord[0]+100*i,grillCoord[1]+100*j,50,50);

        }
              */

        loadChunks(g,grillCoord);


        player.draw(g);

    }

    public int[] playerCoordToGrillCoord(double x,double y){
        int[] newCorrd = new int[2];

        newCorrd[0]= (int) (-x+GAME_WIDTH/2);
        newCorrd[1]=(int) (-y+2*GAME_HEIGHT/3);


        return newCorrd;
    }

    public void loadChunks(Graphics g,int[] grillCoord ){
        int chunkSize=200;
        int numOfChunkAround=3;
        double xPosChunk = ((player.xPosition+chunkSize/2)/chunkSize);
        double yPosChunk = ((player.yPosition+chunkSize/2)/chunkSize);


        if (xPosChunk<0)xPosChunk-=1;
        if (yPosChunk<0)yPosChunk-=1;

        int xPosChunkInt=(int)xPosChunk;
        int yPosChunkInt=(int)yPosChunk;

        /*
        drawChunk(g,grillCoord,xPosChunkInt,yPosChunkInt,chunkSize);

        for(var i=-numOfChunkAround;i<=numOfChunkAround;i+=1) {
            for (var j = -numOfChunkAround; j <= numOfChunkAround; j += 1) {
                drawChunk(g,grillCoord,xPosChunkInt+i,yPosChunkInt+j,chunkSize);
            }
        }

         */
        g.setColor(Color.RED);
        g.fillOval(GAME_WIDTH/2-4,GAME_HEIGHT/3-4,8,8);


    }
    public void drawChunk(Graphics g,int[] grillCoord, int xPosChunkInt,int yPosChunkInt,int chunkSize){
        int numOfDivision=3;
        int blockSize=chunkSize/numOfDivision;



        g.setColor(Color.black);
        /*for(var i=-1+xPosChunkInt*2;i<1+xPosChunkInt*2;i+=1){
            for(var j=-1+yPosChunkInt*2;j<1+yPosChunkInt*2;j+=1)
                g.fillRect(grillCoord[0]+chunkSize/2*i,grillCoord[1]+chunkSize/2*j,chunkSize/4,chunkSize/4);
        }*/

        if ((xPosChunkInt+yPosChunkInt)%2==0){
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt-chunkSize/2,
                    grillCoord[1]+chunkSize*yPosChunkInt-chunkSize/2,blockSize,blockSize);

            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt+chunkSize/2-blockSize,
                    grillCoord[1]+chunkSize*yPosChunkInt-chunkSize/2,blockSize,blockSize);
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt+chunkSize/2-blockSize,
                    grillCoord[1]+chunkSize*yPosChunkInt+chunkSize/2-blockSize,blockSize,blockSize);
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt-chunkSize/2,
                   grillCoord[1]+chunkSize*yPosChunkInt+chunkSize/2-blockSize,blockSize,blockSize);
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt-blockSize/2,
                    grillCoord[1]+chunkSize*yPosChunkInt-blockSize/2,blockSize,blockSize);

        }else{
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt-chunkSize/2+blockSize,
                    grillCoord[1]+chunkSize*yPosChunkInt-chunkSize/2,blockSize,blockSize);
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt-chunkSize/2,
                    grillCoord[1]+chunkSize*yPosChunkInt-chunkSize/2+blockSize,blockSize,blockSize);
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt+chunkSize/2-blockSize*2,
                    grillCoord[1]+chunkSize*yPosChunkInt+chunkSize/2-blockSize,blockSize,blockSize);
            g.fillRect(grillCoord[0]+chunkSize*xPosChunkInt+chunkSize/2-blockSize,
                    grillCoord[1]+chunkSize*yPosChunkInt+chunkSize/2-blockSize*2,blockSize,blockSize);

        }


        g.setColor(Color.red);
        g.drawLine(grillCoord[0]+xPosChunkInt*chunkSize-chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize-chunkSize/2,
                grillCoord[0]+xPosChunkInt*chunkSize+chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize-chunkSize/2);

        g.drawLine(grillCoord[0]+xPosChunkInt*chunkSize+chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize-chunkSize/2,
                grillCoord[0]+xPosChunkInt*chunkSize+chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize+chunkSize/2);

        g.drawLine(grillCoord[0]+xPosChunkInt*chunkSize+chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize+chunkSize/2,
                grillCoord[0]+xPosChunkInt*chunkSize-chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize+chunkSize/2);
        g.drawLine(grillCoord[0]+xPosChunkInt*chunkSize-chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize+chunkSize/2,
                grillCoord[0]+xPosChunkInt*chunkSize-chunkSize/2,
                grillCoord[1]+yPosChunkInt*chunkSize-chunkSize/2);
    }
}
