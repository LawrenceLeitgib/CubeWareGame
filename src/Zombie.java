
public class Zombie extends Entity {
    static int detectionDistance=30;
    static int UnAgroDistance=60;
    boolean aggro;

    double changeDirectionTime=3;
    double changeDirectionCount=0;

    double angleWalking;
    Zombie(double x, double y, double z, double strength, double hp, double xp) {
        super(x, y, z, strength, hp, xp);
        angleWalking=Math.random()*Math.PI*2;
    }
    public void setAggro(){
        if(distanceWithPlayer<detectionDistance){
            aggro=true;
        }
        if(distanceWithPlayer>UnAgroDistance){
            aggro=false;
        }
    }
    @Override
    public void setNewPositions(double deltaTime){
        setAggro();
        changeDirectionCount=+deltaTime;
        if(changeDirectionCount>changeDirectionTime){
            changeDirectionCount-=changeDirectionTime;
            angleWalking=Math.random()*Math.PI*2;

        }
        if(aggro){
            xVelocity=-Math.cos(angleWithPlayer)*getSpeed()*getRunningMultiplier();
            yVelocity=-Math.sin(angleWithPlayer)*getSpeed()*getRunningMultiplier();
        }else{
            xVelocity=-Math.cos(angleWalking)*getSpeed();
            yVelocity=-Math.sin(angleWalking)*getSpeed();
        }
         super.setNewPositions(deltaTime);
    }
}
