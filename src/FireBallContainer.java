import java.awt.*;
import java.util.*;

public class FireBallContainer {

    static ArrayList<FireBall> fireBalls = new ArrayList<FireBall>();
    double shoutTime=0.2;
    double shoutCount=0;

    FireBallContainer(){

    }
    public void createFireBall(){
        Stats.mana-=1;
        if (Stats.mana<0){
            Stats.mana+=1;
            return;
        }
        fireBalls.add(new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+1.2,GameGrid.mouseAngleInGame,20,20,Stats.strength));

    }
    public void attackSpecial1(){
        Stats.mana-=10;
        if(Stats.mana<0){
            Stats.mana+=10;
            return;
        }
        for(var j=0;j<100;j++){
            double ang=2*Math.PI/100.0*j;
            fireBalls.add(new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+1.2,ang,20,20,Stats.strength));

        }

    }
    public void updateData(double deltaTime) {
        //attackSpecial();
        //if(Player.yPosition<0)GameGrid.oneDown=true;
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
        for(var i=0;i<fireBalls.size();i++){

            fireBalls.get(i).draw(g);

        }
    }
}
