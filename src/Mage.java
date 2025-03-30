import java.awt.*;

public class Mage extends Entity{
    double shoutCount=0;
    double shoutTime=1.5;
    double shoutDistance=10;

    Mage(GameGrid gameGrid,double x, double y, double z, double strength, double hp, double xp) {
        super(gameGrid,x, y, z, strength, hp, xp);
        color= new Color(75, 0, 59);
    }

    @Override
    public void updateData(double deltaTime) {
        super.updateData(deltaTime);
        shoutingHandler(deltaTime);
    }
    public void shoutingHandler(double deltaTime){
        if (xVelocity == 0 && yVelocity == 0) {
            shoutCount += deltaTime;
            if (shoutCount > shoutTime) {
                createFireBall();
                shoutCount -= shoutTime;
            }
        }

    }

    public void createFireBall(){
        gameGrid.projectileContainer.Projectiles.add(new FireBall(gameGrid,getxPosition(),getyPosition(),getzPosition()+ ProjectileContainer.ProjectileHeight,Math.PI+angleWithPlayer,13,.2,strength,false));
    }

    public void setNewPositions(double deltaTime){
        if(distanceWithPlayer>shoutDistance){
        xVelocity=-Math.cos(angleWithPlayer)*getSpeed()*getRunningMultiplier();
        yVelocity=-Math.sin(angleWithPlayer)*getSpeed()*getRunningMultiplier();
        }else{
            xVelocity=0;
            yVelocity=0;
        }

        super.setNewPositions(deltaTime);
    }

}
