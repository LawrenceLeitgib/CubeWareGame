import java.util.ArrayList;
public class EntityContainer {
    ArrayList<Entity> entities = new ArrayList<>();
    int numOfEntities = 50;
    ArrayList<Entity>[][][] entities3D = new ArrayList[numOfEntities *2][numOfEntities *2][200];
    boolean[] zLayers=new boolean[Chunk.numOfCubeZ];
    private double zombieCreationSpawnTime =3;
    private double zombieCreationCount =0;
    private double mageCreationSpawnTime =3;
    private double mageCreationCount =0;
    EntityContainer(){

    }

    public void addZombie(double x,double y,double z,double s,double hp,double xp){
        entities.add(new Zombie(x, y, z,s,hp,xp));

    }
    public void addMage(double x,double y,double z,double s,double hp,double xp){
        entities.add(new Mage(x, y, z,s,hp,xp));

    }

    public  void createEnemy(String type){
        double r=Math.random()*40+20;
        double a=Math.random()*Math.PI*2;
        if(Math.sqrt(Math.pow(Math.cos(a) * r + GameGrid.player.xPosition,2)+Math.pow(Math.sin(a) * r + GameGrid.player.yPosition,2))>GameGrid.safeZone) {
            if(type.equals("zombie"))addZombie(Math.cos(a) * r + GameGrid.player.xPosition, Math.sin(a) * r + GameGrid.player.yPosition, 2,1,100,25);
            if(type.equals("mage"))addMage(Math.cos(a) * r + GameGrid.player.xPosition, Math.sin(a) * r + GameGrid.player.yPosition, 2,9,200,200);
        }
    }
    public void updateEnemies3D(){
        entities3D = new ArrayList[numOfEntities *2][numOfEntities *2][200];
        zLayers =new boolean[Chunk.numOfCubeZ];

        if(entities.size()==0)return;
        for (Entity entity : entities) {


            int x = entity.cubeIn[0];
            int y = entity.cubeIn[1];
            int z = entity.cubeIn[2];
            //System.out.println(x+100-Player.chunkIn[0]*Chunk.numOfCubeX+", "+(y+100-Player.chunkIn[1]*Chunk.numOfCubeY)+","+);
            if ((x + numOfEntities - GameGrid.player.cubeIn[0] < 0) || (x + numOfEntities - GameGrid.player.cubeIn[0] >= numOfEntities * 2))
                continue;
            if ((y + numOfEntities - GameGrid.player.cubeIn[1] < 0) || (y + numOfEntities - GameGrid.player.cubeIn[1] >= numOfEntities * 2))
                continue;
            zLayers[entity.cubeIn[2]] = true;
            zLayers[entity.cubeIn[2] + 1] = true;
            zLayers[entity.cubeIn[2] + 2] = true;
            if (entities3D[x + numOfEntities - GameGrid.player.cubeIn[0]][y + numOfEntities - GameGrid.player.cubeIn[1]][z] == null) {
                ArrayList<Entity> enemiesSub = new ArrayList<>();
                enemiesSub.add(entity);
                entities3D[x + numOfEntities - GameGrid.player.cubeIn[0]][y + numOfEntities - GameGrid.player.cubeIn[1]][z] = enemiesSub;
            } else {
                entities3D[x + numOfEntities - GameGrid.player.cubeIn[0]][y + numOfEntities - GameGrid.player.cubeIn[1]][z].add(entity);
            }

        }

    }
    public void updateData(double deltaTime) {
        zombieCreationSpawnTime =0.00001*Math.pow(GameGrid.player.distanceFromMiddle -500,2)+.2;
        mageCreationSpawnTime =0.00001*Math.pow(GameGrid.player.distanceFromMiddle -800,2)+.3;

        if(GameGrid.player.distanceFromMiddle>GameGrid.safeZone) {
            zombieCreationCount += deltaTime;
            if (zombieCreationCount >= zombieCreationSpawnTime) {
                if(entities.size()<800){
                    createEnemy("zombie");
                }
                zombieCreationCount -= zombieCreationSpawnTime;
            }
        }
        if(GameGrid.player.distanceFromMiddle>300) {
            mageCreationCount += deltaTime;
            if (mageCreationCount >= mageCreationSpawnTime) {
                if(entities.size()<800){
                    createEnemy("mage");
                }
                mageCreationCount -= mageCreationSpawnTime;
            }
        }
        for(var i = 0; i< entities.size(); i++){
            entities.get(i).updateData(deltaTime);
            if(entities.get(i).isMarketForDeletion()){
                entities.remove(i);
            }
        }
    }
    public void draw(){
        updateEnemies3D();
    }
}
