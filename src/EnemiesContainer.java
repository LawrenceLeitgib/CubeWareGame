import java.awt.*;

public class EnemiesContainer {
    static Enemy[] enemies=new Enemy[100];
    static boolean[] enemyList =new boolean[100];
    EnemiesContainer(){
        //enemies[0] = new Enemy(0,0, 2);
      //  enemyList[0] = true;
    }

    double EnemyCreationTime=3;
    double EnemyCreationCount=3;

    public static void createEnemy(){
        for(var i=0;i<enemyList.length;i++){
           // System.out.println(Math.random());


            if(!enemyList[i]){
                double r=Math.random()*16+4;
                double a=Math.random()*Math.PI*2;
                if(Math.sqrt(Math.pow(Math.cos(a) * r + Player.xPosition,2)+Math.pow(Math.sin(a) * r + Player.yPosition,2))>40) {
                    enemies[i] = new Enemy(Math.cos(a) * r + Player.xPosition, Math.sin(a) * r + Player.yPosition, 2);
                    enemyList[i] = true;
                }
                return;
            }
        }


    }
    public void updateData(double deltaTime) {


        if(Math.sqrt(Math.pow(Player.xPosition,2)+Math.pow(Player.yPosition,2))>40) {
            EnemyCreationCount += deltaTime;
            if (EnemyCreationCount >= EnemyCreationTime) {
                createEnemy();
                EnemyCreationCount -= EnemyCreationTime;

            }
        }

        for(var i=0;i<enemyList.length;i++){
            if(enemyList[i]&&enemies[i].marketForDeletion){
                enemies[i]=null;
                enemyList[i]=false;
            }
            if(enemyList[i]){
                enemies[i].updateData(deltaTime);
            }
        }


    }
    public void draw(Graphics g){
        for(var i=0;i<enemyList.length;i++){
            if(enemyList[i]){
                enemies[i].draw(g);
            }
        }
    }

}
