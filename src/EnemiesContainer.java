import java.awt.*;
import java.util.ArrayList;

public class EnemiesContainer {


    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    EnemiesContainer(){
        //enemies[0] = new Enemy(0,-5, 2);
       // enemyList[0] = true;
    }

    double EnemyCreationSpawnTime=3;
    double EnemyCreationCount=3;

    public static void createEnemy(){


        double r=Math.random()*32+8;
        double a=Math.random()*Math.PI*2;
        if(Math.sqrt(Math.pow(Math.cos(a) * r + Player.xPosition,2)+Math.pow(Math.sin(a) * r + Player.yPosition,2))>GameGrid.safeZone) {
            enemies.add(new Enemy(Math.cos(a) * r + Player.xPosition, Math.sin(a) * r + Player.yPosition, 2));
        }


    }
    public void updateData(double deltaTime) {

        EnemyCreationSpawnTime=0.002*Math.pow(Player.distanceFromMiddle -150,2)+2;



        if(Player.distanceFromMiddle>GameGrid.safeZone) {
            EnemyCreationCount += deltaTime*1000;
            if (EnemyCreationCount >= EnemyCreationSpawnTime) {
                createEnemy();
                EnemyCreationCount -= EnemyCreationSpawnTime;

            }
        }
        for(var i=0;i<enemies.size();i++){
            enemies.get(i).updateData(deltaTime);
            if(enemies.get(i).marketForDeletion){
                enemies.remove(i);
            }

        }


    }
    public void draw(Graphics g){
        for(var i=0;i<enemies.size();i++){
            enemies.get(i).draw(g);


        }
    }

}
