import java.awt.*;
import java.util.*;
public class ProjectileContainer {
    static ArrayList<Projectile> Projectiles = new ArrayList<Projectile>();
    static int numOfProjectile = Projectile.diSpawnDistance+20;
    static ArrayList<Projectile>[][][] projectile3D = new ArrayList[numOfProjectile *2][numOfProjectile *2][200];
    static boolean[] zLayers =new boolean[Chunk.numOfCubeZ];
    static double ProjectileHeight =1.1;
    private boolean HaveBeenUpdated;
    ProjectileContainer(){
    }
    public void updateFireBalls3D(){
        zLayers =new boolean[Chunk.numOfCubeZ];
        if(Projectiles.size()>0){
            HaveBeenUpdated=false;
        }
        if(HaveBeenUpdated)return;
        projectile3D = new ArrayList[numOfProjectile *2][numOfProjectile *2][200];
        for(var i = 0; i< Projectiles.size(); i++){
            zLayers[Projectiles.get(i).cubeIn[2]]=true;
            int x= Projectiles.get(i).cubeIn[0];
            int y= Projectiles.get(i).cubeIn[1];
            int z= Projectiles.get(i).cubeIn[2];
            //System.out.println(x+100-Player.chunkIn[0]*Chunk.numOfCubeX+", "+(y+100-Player.chunkIn[1]*Chunk.numOfCubeY)+","+);
            if(z<0)continue;
            if(projectile3D[x+ numOfProjectile -Player.cubeIn[0]][y+ numOfProjectile -Player.cubeIn[1]][z]==null)   {
                 ArrayList<Projectile> fireBallsSub = new ArrayList<Projectile>();
                 fireBallsSub.add(Projectiles.get(i));
                 projectile3D[x+ numOfProjectile -Player.cubeIn[0]][y+ numOfProjectile -Player.cubeIn[1]][z] = fireBallsSub;
            } else{
                projectile3D[x+ numOfProjectile -Player.cubeIn[0]][y+ numOfProjectile -Player.cubeIn[1]][z].add(Projectiles.get(i));
            }

        }
        if(Projectiles.size()==0){
            HaveBeenUpdated=true;
        }

    }
    public void updateData(double deltaTime) {

        for(var i = 0; i< Projectiles.size(); i++){
            Projectiles.get(i).updateData(deltaTime);
            if(Projectiles.get(i).marketForDeletion){
                Projectiles.remove(i);
            }

        }

    }
    public void draw(Graphics g){
        updateFireBalls3D();
    }
}
