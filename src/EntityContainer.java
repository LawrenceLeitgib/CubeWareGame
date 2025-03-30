import java.awt.*;
import java.util.ArrayList;
public class EntityContainer {
    static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    static int numOfEntities =Enemy.diSpawnDistance+20;
    static ArrayList<Enemy>[][][] enemies3D = new ArrayList[numOfEntities *2][numOfEntities *2][200];
    boolean HaveBeenUpdated;
    static boolean[] zLayers=new boolean[Chunk.numOfCubeZ];
    EntityContainer(){
        //enemies[0] = new Enemy(0,-5, 2);
       //enemyList[0] = true;
    }
    double EnemyCreationSpawnTime=3;
    double EnemyCreationCount=3;
    public static void createEnemy(){


        double r=Math.random()*32+12;
        double a=Math.random()*Math.PI*2;
        if(Math.sqrt(Math.pow(Math.cos(a) * r + Player.xPosition,2)+Math.pow(Math.sin(a) * r + Player.yPosition,2))>GameGrid.safeZone) {
            enemies.add(new Enemy(Math.cos(a) * r + Player.xPosition, Math.sin(a) * r + Player.yPosition, 2));
        }
       // enemies.add(new Enemy(10, 70, 2));

    }
    public void updateEnemies3D(){
        if(enemies.size()>0){
            HaveBeenUpdated=false;
        }
        if(HaveBeenUpdated)return;
        enemies3D = new ArrayList[numOfEntities *2][numOfEntities *2][200];
        for (Enemy enemy : enemies) {
            zLayers[enemy.cubeIn[2]] = true;
            zLayers[enemy.cubeIn[2] + 1] = true;
            zLayers[enemy.cubeIn[2] + 2] = true;

            int x = enemy.cubeIn[0];
            int y = enemy.cubeIn[1];
            int z = enemy.cubeIn[2];
            //System.out.println(x+100-Player.chunkIn[0]*Chunk.numOfCubeX+", "+(y+100-Player.chunkIn[1]*Chunk.numOfCubeY)+","+);
            if ((x + numOfEntities - Player.cubeIn[0] < 0) || (x + numOfEntities - Player.cubeIn[0] >= numOfEntities * 2))
                continue;
            if ((y + numOfEntities - Player.cubeIn[1] < 0) || (y + numOfEntities - Player.cubeIn[1] >= numOfEntities * 2))
                continue;

            if (z < 0) continue;
            if (enemies3D[x + numOfEntities - Player.cubeIn[0]][y + numOfEntities - Player.cubeIn[1]][z] == null) {
                ArrayList<Enemy> enemiesSub = new ArrayList<Enemy>();
                enemiesSub.add(enemy);
                enemies3D[x + numOfEntities - Player.cubeIn[0]][y + numOfEntities - Player.cubeIn[1]][z] = enemiesSub;
            } else {
                enemies3D[x + numOfEntities - Player.cubeIn[0]][y + numOfEntities - Player.cubeIn[1]][z].add(enemy);
            }

        }
        if(enemies.size()==0){
            HaveBeenUpdated=true;
        }

    }
    public void updateData(double deltaTime) {
        EnemyCreationSpawnTime=0.0005*Math.pow(Player.distanceFromMiddle -150,2)+2;
        if(Player.distanceFromMiddle>GameGrid.safeZone) {
            EnemyCreationCount += deltaTime*10;
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
        updateEnemies3D();
    }
}
