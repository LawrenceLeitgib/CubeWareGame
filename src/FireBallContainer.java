import java.awt.*;

public class FireBallContainer {
    FireBall[] fireBalls =new FireBall[1000];
    boolean[] fireBallsList =new boolean[1000];

    double shoutTime=0.0;
    double shoutCount=0;

    FireBallContainer(){

    }
    public void createFireBall(){
        for(var i=0;i<fireBallsList.length;i++){
            if(!fireBallsList[i]){
                fireBalls[i]=new FireBall(Player.xPosition,Player.yPosition,GameGrid.mouseAngleInGame,.00,20,0);
                fireBallsList[i]=true;
                return;
            }
        }

    }
    public void updateData(double deltaTime) {
        if(GameGrid.mouseClicked){
            shoutCount+=deltaTime;
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
