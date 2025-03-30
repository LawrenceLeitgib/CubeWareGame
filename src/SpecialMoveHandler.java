import java.awt.event.KeyEvent;

public class SpecialMoveHandler {
    private final double shoutTime=0.2;
    private double shoutCount=0;
    boolean lightningSprint=false;
    int lightningSprintCount=0;
    double lightningSprintTime =1;
    double lightningSprintCount2= lightningSprintTime;
    private final  GameGrid gameGrid;
    SpecialMoveHandler(GameGrid gameGrid){
     this.gameGrid=gameGrid;
    }
    public  void createFireBall(){
        Stats.mana-=1;
        if (Stats.mana<0){
            Stats.mana+=1;
            return;
        }
        gameGrid.projectileContainer.Projectiles.add(new FireBall(gameGrid.player.xPosition,gameGrid.player.yPosition,gameGrid.player.zPosition+ ProjectileContainer.ProjectileHeight, InputHandler.mouseAngleInGame,20,.2,Stats.strength,true));
    }
    public void attackSpecial1(){
        if(Stats.currentLevel<Stats.SpecialAttack.get("FBT")[0])return;
        Stats.mana-=Stats.SpecialAttack.get("FBT")[1];
        if(Stats.mana<0){
            Stats.mana+=Stats.SpecialAttack.get("FBT")[1];
            return;
        }
        for(var j=0;j<100;j++){
            double ang=2*Math.PI/100.0*j;
            gameGrid.projectileContainer.Projectiles.add(new FireBall(gameGrid.player.xPosition,gameGrid.player.yPosition,gameGrid.player.zPosition+ ProjectileContainer.ProjectileHeight,ang,20,.2,Stats.strength,true));
        }
    }

    public void updateData(double deltaTime){

        if(InputHandler.mouseLeftClickDown){
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

            gameGrid.player.yVelocity=0;
            gameGrid.player.yVelocity-=200.2*Math.cos(GameGrid.angleForHorizontalRotation)*deltaTime*20;
            gameGrid.player.xVelocity+=200.2*Math.sin(GameGrid.angleForHorizontalRotation)*deltaTime*20;
            lightningSprintCount--;
        }


    }
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case 81-> lightningSprint = true;

        }
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 81 -> lightningSprint = false;
        }

    }
}
