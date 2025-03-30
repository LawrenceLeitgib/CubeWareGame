import java.awt.*;
import java.util.*;
public class ProjectileContainer {
    static double ProjectileHeight =1.1;
    int numOfProjectile = Projectile.diSpawnDistance+20;
    boolean[] zLayers =new boolean[Chunk.numOfCubeZ];
    ArrayList<Projectile> Projectiles = new ArrayList<>();
    ArrayList<Projectile>[][][] projectile3D = new ArrayList[numOfProjectile *2][numOfProjectile *2][200];
    private final  GameGrid gameGrid;
    ProjectileContainer(GameGrid gameGrid){
        this.gameGrid=gameGrid;
    }
    public void updateFireBalls3D(){
        zLayers =new boolean[Chunk.numOfCubeZ];
        projectile3D = new ArrayList[numOfProjectile *2][numOfProjectile *2][200];
        if(Projectiles.size()==0)return;
        for (Projectile projectile : Projectiles) {
            zLayers[projectile.getCubeIn()[2]] = true;
            int x = projectile.getCubeIn()[0];
            int y = projectile.getCubeIn()[1];
            int z = projectile.getCubeIn()[2];
            //System.out.println(x+100-Player.chunkIn[0]*Chunk.numOfCubeX+", "+(y+100-Player.chunkIn[1]*Chunk.numOfCubeY)+","+);
            if (z < 0) continue;
            if (projectile3D[x + numOfProjectile - gameGrid.player.cubeIn[0]][y + numOfProjectile - gameGrid.player.cubeIn[1]][z] == null) {
                ArrayList<Projectile> fireBallsSub = new ArrayList<Projectile>();
                fireBallsSub.add(projectile);
                projectile3D[x + numOfProjectile - gameGrid.player.cubeIn[0]][y + numOfProjectile - gameGrid.player.cubeIn[1]][z] = fireBallsSub;
            } else {
                projectile3D[x + numOfProjectile - gameGrid.player.cubeIn[0]][y + numOfProjectile - gameGrid.player.cubeIn[1]][z].add(projectile);
            }

        }
    }
    public void updateData(double deltaTime) {

        for(var i = 0; i< Projectiles.size(); i++){
            Projectiles.get(i).updateData(deltaTime);
            if(Projectiles.get(i).isMarketForDeletion()){
                Projectiles.remove(i);
            }

        }

    }
    public void draw(){
        updateFireBalls3D();
    }
}
