public abstract class TrashyMethod extends CubeContainer {
    TrashyMethod(double depthRatio) {
        super(depthRatio);
    }

    public void drawDick(int x, int y,int z){
        newCube(x,y,z+6);
        newCube(x,y,z+5);
        newCube(x,y,z+4);
        newCube(x,y,z+3);
        newCube(x,y,z+2);
        newCube(x,y,z+1);
        newCube(x,y,z);
        newCube(x-1,y,z);
        newCube(x+1,y,z);
        newCube(x-1,y,z+1);
        newCube(x+1,y,z+1);

        newCube(x,y-1,z+6);
        newCube(x,y-1,z+5);
        newCube(x,y-1,z+4);
        newCube(x,y-1,z+3);
        newCube(x,y-1,z+2);
        newCube(x,y-1,z+1);
        newCube(x,y-1,z);
        newCube(x-1,y-1,z);
        newCube(x+1,y-1,z);
        newCube(x-1,y-1,z+1);
        newCube(x+1,y-1,z+1);

    }
    public void drawHeart(int x, int y,int z){

        newCube(x,y,z);

        newCube(x-1,y,z+1);
        newCube(x,y,z+1);
        newCube(x+1,y,z+1);

        newCube(x-2,y,z+2);
        newCube(x-1,y,z+2);
        newCube(x,y,z+2);
        newCube(x+1,y,z+2);
        newCube(x+2,y,z+2);

        newCube(x-3,y,z+3);
        newCube(x-2,y,z+3);
        newCube(x-1,y,z+3);
        newCube(x,y,z+3);
        newCube(x+1,y,z+3);
        newCube(x+2,y,z+3);
        newCube(x+3,y,z+3);

        newCube(x-3,y,z+4);
        newCube(x-2,y,z+4);
        newCube(x-1,y,z+4);
        newCube(x+1,y,z+4);
        newCube(x+2,y,z+4);
        newCube(x+3,y,z+4);

        newCube(x-2,y,z+5);
        newCube(x+2,y,z+5);


    }
    public void drawMap(int x, int y,int z){
        for(var i=-5;i<5;i++){
            for(var j=0;j<10;j++){
                newCube(i+x,j+y,z);
            }
        }
        for(var j=0;j<10;j++){
            newCube(-1+x,j+y,z+1);
            newCube(1+x,j+y,z+1);
        }
        for(var i=0;i<10;i++){
            for(var j=0;j<10;j++){
                if(j>=i)
                    newCube(4+x,i+y,j-i+z+1);
            }
        }




    }
    public void drawDick(int x, int y,int z,int height,int ballSize){
        height=4*ballSize;
        for(var i=0;i<ballSize;i++){
            for(var j=0;j<ballSize;j++){
                for(var k=0;k<ballSize;k++){
                    newCube(i-ballSize+x,j+y,k+z);
                    newCube(i+ballSize+x,j+y,k+z);
                }
                for(var k=0;k<height;k++){
                    newCube(i+x,j+y,k+z);
                }

            }
        }



    }
    public void drawCircle(int x,int y,int z,int r){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<=r+1&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2))>=r-1){
                    newCube(x+i,y,z+k);
                }
            }
        }
    }
    public void drawCircle2(int x, int y, int z, int r, int type){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2))>=r-1){
                    newCube(x+i,y+k,z,type);
                }
            }
        }
    }
    public void fillircle(int x,int y,int z,int r){
        for(int i=-r;i<r;i++){
            for(int k=-r;k<r;k++){
                if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2))<r){
                    newCube(x+i,y,z+k);
                }
            }
        }

    }
    public void drawHeart2(int x, int y,int z,int size){
        for(int k=0;k<size;k++){
           for(int i=-k;i<k;i++){
               newCube(x+i,y,z+k);
           }
        }

    }
    public void drawBall(int x, int y, int z,int r){
        for(int i=-r;i<r;i++){
            for(int j=-r;j<r;j++){
                for(int k=-r;k<r;k++){
                    if(Math.sqrt(Math.pow(i,2)+Math.pow(k,2)+Math.pow(j,2))<=r+1&&Math.sqrt(Math.pow(i,2)+Math.pow(k,2)+Math.pow(j,2))>=r-1){
                        newCube(x+i,y+j,z+k);
                    }
                }
            }
        }

    }
    public void jump(int x,int y,int z){
        newCube(0+x,-5+y,2+z);
        newCube(-2+x,-7+y,3+z);
        newCube(-4+x,-9+y,4+z);
        newCube(-7+x,-11+y,4+z);

        newCube(-6+x,-14+y,5+z);
        newCube(-3+x,-16+y,6+z);
        newCube(-2+x,-13+y,7+z);
        newCube(1+x,-13+y,8+z);
        newCube(2+x,-10+y,9+z);
        newCube(4+x,-9+y,10+z);
        newCube(2+x,-7+y,11+z);
        newCube(0+x,-5+y,12+z);
    }

}
