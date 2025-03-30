import java.awt.*;

public class FireBallContainer {
    static int numOfBall=500000;
    static FireBall[] fireBalls =new FireBall[numOfBall];
    static boolean[] fireBallsList =new boolean[numOfBall];

    double shoutTime=0.2;
    double shoutCount=0;

    FireBallContainer(){

    }
    public void createFireBall(){
        for(var i=0;i<fireBallsList.length;i++){
            if(!fireBallsList[i]){
                Stats.mana-=1;
                if (Stats.mana<0){
                    Stats.mana+=1;
                    return;
                }
                fireBalls[i]=new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+1.2,GameGrid.mouseAngleInGame,20,20,4*Stats.strength);
                fireBallsList[i]=true;
                //fireBalls[i+1]=new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+.2,GameGrid.mouseAngleInGame,20,20,4*Stats.strength);
                //fireBallsList[i+1]=true;

                return;
            }
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
            for(var i=0;i<fireBallsList.length;i++){
                if(!fireBallsList[i]){
                    fireBalls[i]=new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+1.2,ang,20,20,Stats.strength*4);
                    fireBallsList[i]=true;
                   // fireBalls[i+1]=new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+.2,ang,20,20,Stats.strength*4);
                    //fireBallsList[i+1]=true;
                    break;

                }
            }
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
        for(var i=0;i<fireBallsList.length;i++){
            if(fireBallsList[i]&&fireBalls[i].marketForDeletion){
                fireBalls[i]=null;
                fireBallsList[i]=false;
            }
            if(fireBallsList[i]){
                fireBalls[i].updateData(deltaTime);
            }
        }

    }
    public void draw(Graphics g){
        for(var i=0;i<fireBallsList.length;i++){
            if(fireBallsList[i]){
                fireBalls[i].draw(g);
            }
        }
    }
}
