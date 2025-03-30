import java.awt.event.KeyEvent;

public class SpecialMoveHandler {
    double shoutTime=0.2;
    double shoutCount=0;
    static boolean oneDown=false;
    static boolean oneHaveBeenRelesed=true;
    boolean lightningSprint=false;
    int lightningSprintCount=0;
    double lightningSprintTime =1;
    double lightningSprintCount2= lightningSprintTime;
    SpecialMoveHandler(){

    }
    public static void createFireBall(){
        Stats.mana-=1;
        if (Stats.mana<0){
            Stats.mana+=1;
            return;
        }
        ProjectileContainer.Projectiles.add(new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+ ProjectileContainer.ProjectileHeight,GameGrid.mouseAngleInGame,20,.2,Stats.strength,true));
    }
    public static void attackSpecial1(){
        if(Stats.currentLevel<Stats.SpecialAttack.get("FBT")[0])return;
        Stats.mana-=Stats.SpecialAttack.get("FBT")[1];
        if(Stats.mana<0){
            Stats.mana+=Stats.SpecialAttack.get("FBT")[1];
            return;
        }
        for(var j=0;j<100;j++){
            double ang=2*Math.PI/100.0*j;
            ProjectileContainer.Projectiles.add(new FireBall(Player.xPosition,Player.yPosition,Player.zPosition+ ProjectileContainer.ProjectileHeight,ang,20,.2,Stats.strength,true));

        }
    }

    public void updateData(double deltaTime){
        if(oneDown){
            attackSpecial1();
            oneDown=false;

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
    }
    public void lightningSprintHandler(double deltaTime){
        if(Stats.currentLevel<Stats.SpecialAttack.get("Dash")[0])return;
        lightningSprintCount2+=deltaTime;
        if(lightningSprint){
            if(lightningSprintCount2>= lightningSprintTime){
                lightningSprintCount2=0;
                Stats.mana-=Stats.SpecialAttack.get("Dash")[1];
                lightningSprintCount=30;
                if(Stats.mana<0){
                    Stats.mana+=Stats.SpecialAttack.get("Dash")[1];
                    lightningSprintCount=0;
                }
            }
        }
        if(lightningSprintCount>0){

            Player.yVelocity=0;
            Player.yVelocity-=200.2*Math.cos(GameGrid.angleForXRotation)*deltaTime*20;
            Player.xVelocity+=200.2*Math.sin(GameGrid.angleForXRotation)*deltaTime*20;
            lightningSprintCount--;
        }


    }
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 49:
            if(oneHaveBeenRelesed){
                oneDown=true;
                oneHaveBeenRelesed=false;
            }
            break;
            case 81:
                lightningSprint=true;
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case 49:
                oneDown=false;
                oneHaveBeenRelesed=true;
                break;

            case 81:
                lightningSprint=false;
                break;
        }

    }
}
