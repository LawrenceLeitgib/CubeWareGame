import java.awt.*;

public class GameGrid {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    Player player;

    int squareLength=40;
    GameGrid(int GAME_WIDTH,int GAME_HEIGHT){
        this.GAME_WIDTH=GAME_WIDTH;
        this.GAME_HEIGHT=GAME_HEIGHT;
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0);
    }

    public void newPlayer(){
        player = new Player(GAME_WIDTH,GAME_HEIGHT,0,0);

    }
    public void updateData(double deltaTime){
        player.updateData(deltaTime);


    }
    public void draw(Graphics g){
        int[] grillCoord=playerCoordToGrillCoord(player.xPosition, player.yPosition);

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
        newCorrd[1]=(int) (-y+GAME_HEIGHT/2);


        return newCorrd;
    }

    public void loadChunks(Graphics g,int[] grillCoord ){
        int chunkSize=200;
        double xPosChunk = ((player.xPosition+chunkSize/2)/chunkSize);
        double yPosChunk = ((player.yPosition+chunkSize/2)/chunkSize);


        if (xPosChunk<0)xPosChunk-=1;
        if (yPosChunk<0)yPosChunk-=1;

        int xPosChunkInt=(int)xPosChunk;
        int yPosChunkInt=(int)yPosChunk;
        drawChunk(g,grillCoord,xPosChunk,yPosChunk,chunkSize);

    }
    public void drawChunk(Graphics g,int[] grillCoord, double xPosChunk,double yPosChunk,int chunkSize){
        int xPosChunkInt=(int)xPosChunk;
        int yPosChunkInt=(int)yPosChunk;
        for(var i=-1+xPosChunkInt*2;i<1+xPosChunkInt*2;i+=1){
            for(var j=-1+yPosChunkInt*2;j<1+yPosChunkInt*2;j+=1)
                g.fillRect(grillCoord[0]+chunkSize/2*i,grillCoord[1]+chunkSize/2*j,chunkSize/4,chunkSize/4);
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
