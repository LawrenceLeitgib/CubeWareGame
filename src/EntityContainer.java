import java.awt.*;
import java.util.ArrayList;
public class EntityContainer {
    static ArrayList<Entity> entities = new ArrayList<Entity>();
    static int numOfEntities = Entity.diSpawnDistance+10;
    static ArrayList<Entity>[][][] entities3D = new ArrayList[numOfEntities *2][numOfEntities *2][200];
    boolean HaveBeenUpdated;
    static boolean[] zLayers=new boolean[Chunk.numOfCubeZ];
    EntityContainer(){
        //enemies[0] = new Enemy(0,-5, 2);
       //enemyList[0] = true;
    }
    double zombieCreationSpawnTime =3;
    double zombieCreationCount =3;

    double mageCreationSpawnTime =3;
    double mageCreationCount =3;

    static public void addZombie(double x,double y,double z,double s,double hp,double xp){
        entities.add(new Zombie(x, y, z,s,hp,xp));

    }
    static public void addMage(double x,double y,double z,double s,double hp,double xp){
        entities.add(new Mage(x, y, z,s,hp,xp));

    }

    public static void createEnemy(String type){
        double r=Math.random()*40+20;
        double a=Math.random()*Math.PI*2;
        if(Math.sqrt(Math.pow(Math.cos(a) * r + Player.xPosition,2)+Math.pow(Math.sin(a) * r + Player.yPosition,2))>GameGrid.safeZone) {
            if(type.equals("zombie"))addZombie(Math.cos(a) * r + Player.xPosition, Math.sin(a) * r + Player.yPosition, 2,1,100,25);
            if(type.equals("mage"))addMage(Math.cos(a) * r + Player.xPosition, Math.sin(a) * r + Player.yPosition, 2,9,200,200);
        }

    }
    public void updateEnemies3D(){
        if(entities.size()>0){
            HaveBeenUpdated=false;
        }
        if(HaveBeenUpdated)return;
        entities3D = new ArrayList[numOfEntities *2][numOfEntities *2][200];
        for (Entity entity : entities) {
            zLayers[entity.cubeIn[2]] = true;
            zLayers[entity.cubeIn[2] + 1] = true;
            zLayers[entity.cubeIn[2] + 2] = true;

            int x = entity.cubeIn[0];
            int y = entity.cubeIn[1];
            int z = entity.cubeIn[2];
            //System.out.println(x+100-Player.chunkIn[0]*Chunk.numOfCubeX+", "+(y+100-Player.chunkIn[1]*Chunk.numOfCubeY)+","+);
            if ((x + numOfEntities - Player.cubeIn[0] < 0) || (x + numOfEntities - Player.cubeIn[0] >= numOfEntities * 2))
                continue;
            if ((y + numOfEntities - Player.cubeIn[1] < 0) || (y + numOfEntities - Player.cubeIn[1] >= numOfEntities * 2))
                continue;

            if (z < 0) continue;
            if (entities3D[x + numOfEntities - Player.cubeIn[0]][y + numOfEntities - Player.cubeIn[1]][z] == null) {
                ArrayList<Entity> enemiesSub = new ArrayList<Entity>();
                enemiesSub.add(entity);
                entities3D[x + numOfEntities - Player.cubeIn[0]][y + numOfEntities - Player.cubeIn[1]][z] = enemiesSub;
            } else {
                entities3D[x + numOfEntities - Player.cubeIn[0]][y + numOfEntities - Player.cubeIn[1]][z].add(entity);
            }

        }
        if(entities.size()==0){
            HaveBeenUpdated=true;
        }

    }
    public void updateData(double deltaTime) {
        zombieCreationSpawnTime =0.00001*Math.pow(Player.distanceFromMiddle -500,2)+.2;
        mageCreationSpawnTime =0.00001*Math.pow(Player.distanceFromMiddle -800,2)+.3;

        if(Player.distanceFromMiddle>GameGrid.safeZone) {
            zombieCreationCount += deltaTime*0;
            if (zombieCreationCount >= zombieCreationSpawnTime) {
                if(entities.size()<800){
                    createEnemy("zombie");
                }
                zombieCreationCount -= zombieCreationSpawnTime;
            }
        }
        if(Player.distanceFromMiddle>300) {
            mageCreationCount += deltaTime*0;
            if (mageCreationCount >= mageCreationSpawnTime) {
                if(entities.size()<800){
                    createEnemy("mage");
                }
                mageCreationCount -= mageCreationSpawnTime;
            }
        }
        for(var i = 0; i< entities.size(); i++){
            entities.get(i).updateData(deltaTime);
            if(entities.get(i).marketForDeletion){
                entities.remove(i);
            }
        }
    }
    public void draw(Graphics g){
        updateEnemies3D();
    }
}
