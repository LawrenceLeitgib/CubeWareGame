import java.awt.*;
import java.util.*;

public class FireBallContainer {

    static ArrayList<FireBall> fireBalls = new ArrayList<FireBall>();
    double shoutTime=0.2;
    double shoutCount=0;

    double update3DTime=0.05;
    double update3DCount=0;

    static int numOfFireBall=FireBall.diSpawnDistance+20;
    static ArrayList<FireBall>[][][] fireBalls3D = new ArrayList[numOfFireBall*2][numOfFireBall*2][200];
    static boolean[] zLayers =new boolean[Chunk.numOfCubeZ];


    static double fireBallHeight=1.2;

    // ArrayList<Integer>[] al = new ArrayList[n];
    private boolean HaveBeenUpdated;


    FireBallContainer(){

    }
    public void createFireBall(){
        Stats.mana-=1;
        if (Stats.mana<0){
            Stats.mana+=1;
            return;
        }
        fireBalls.add(new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+fireBallHeight,GameGrid.mouseAngleInGame,20,20,Stats.strength));
        //updateFireBalls3D();
    }

    public void updateFireBalls3D(){
        zLayers =new boolean[Chunk.numOfCubeZ];


        if(fireBalls.size()>0){
            HaveBeenUpdated=false;
        }
        if(HaveBeenUpdated)return;
        fireBalls3D = new ArrayList[numOfFireBall*2][numOfFireBall*2][200];
        for(var i=0;i<fireBalls.size();i++){
            zLayers[fireBalls.get(i).cubeIn[2]]=true;
            int x=fireBalls.get(i).cubeIn[0];
            int y=fireBalls.get(i).cubeIn[1];
            int z=fireBalls.get(i).cubeIn[2];
            //System.out.println(x+100-Player.chunkIn[0]*Chunk.numOfCubeX+", "+(y+100-Player.chunkIn[1]*Chunk.numOfCubeY)+","+);
            if(z<0)continue;
            if(fireBalls3D[x+numOfFireBall-Player.cubeIn[0]][y+numOfFireBall-Player.cubeIn[1]][z]==null)   {
                 ArrayList<FireBall> fireBallsSub = new ArrayList<FireBall>();
                 fireBallsSub.add(fireBalls.get(i));
                 fireBalls3D[x+numOfFireBall-Player.cubeIn[0]][y+numOfFireBall-Player.cubeIn[1]][z] = fireBallsSub;
            } else{
                fireBalls3D[x+numOfFireBall-Player.cubeIn[0]][y+numOfFireBall-Player.cubeIn[1]][z].add(fireBalls.get(i));
            }

        }
        if(fireBalls.size()==0){
            HaveBeenUpdated=true;
        }

    }

    public void attackSpecial1(){
        Stats.mana-=10;
        if(Stats.mana<0){
            Stats.mana+=10;
            return;
        }
        for(var j=0;j<100;j++){
            double ang=2*Math.PI/100.0*j;
            fireBalls.add(new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+fireBallHeight,ang,20,20,Stats.strength));

        }
        //updateFireBalls3D();

    }
    public void updateData(double deltaTime) {
        //attackSpecial();
        //if(Player.yPosition<0)GameGrid.oneDown=true;
/*
        update3DCount+=deltaTime;
        if(update3DCount>=update3DTime){
            updateFireBalls3D();
            update3DCount-=update3DTime;
        }

 */


        if(GameGrid.oneDown){
            attackSpecial1();
            GameGrid.oneDown=false;

        }
        if(GameGrid.mouseLeftClickDown){
            shoutCount+=deltaTime;
           // attackSpecial();
            if(shoutCount>=shoutTime){
                shoutCount-=shoutTime;
                createFireBall();
            }
        }else{
            shoutCount=shoutTime;
        }
        for(var i=0;i<fireBalls.size();i++){
            fireBalls.get(i).updateData(deltaTime);
            if(fireBalls.get(i).marketForDeletion){
                fireBalls.remove(i);
            }

        }

    }
    public void draw(Graphics g){
        updateFireBalls3D();
        /*
        for(var i=0;i<fireBalls.size();i++){
            fireBalls.get(i).draw(g);
        }

         */
    }
}
