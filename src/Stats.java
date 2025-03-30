import java.awt.*;

public class Stats {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static double strength;
    int speed;
    static double maxHealth;
    static double health;
    static double maxMana;
    static double mana;

    static double xp=0;
    static double xpUntilNextLevelBase=100;
    static double xpUntilNextLevel=xpUntilNextLevelBase;
    static double currentLevel=0;
    Stats(int GAME_WIDTH, int GAME_HEIGHT){

        this.GAME_WIDTH=GAME_WIDTH;
        this.GAME_HEIGHT=GAME_HEIGHT;

        strength=10;
        maxHealth=20;
        health=20;
        maxMana=20;
        mana=20;
    }
    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
    }

    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
    }
    public void draw(Graphics g){




        drawRectWithBorder(g,10,10,200,15,2,Color.RED,Color.black);
        g.setColor(Color.green);
        g.fillRect(10,10, (int) (200*health/maxHealth),15);
        drawRectWithBorder(g,10,30,200,15,2,new Color(131, 201, 201),Color.black);
        g.setColor(new Color(0,0,255));
        g.fillRect(10,30, (int) (200*mana/maxMana),15);

        drawRectWithBorder(g,GAME_WIDTH-10-200,10,200,15,2,new Color(0, 150, 80),Color.black);

        g.setColor(new Color(0, 255, 140));
        g.fillRect(GAME_WIDTH-10-200,10, (int) (200*xp/xpUntilNextLevel),15);

        g.setFont(new Font("Arial",Font.PLAIN,16));
        g.setColor(Color.black);
        g.drawString("XP:"+String.valueOf((int)(xp*10+0.5)/10.0)+" / "+String.valueOf((int)(xpUntilNextLevel*10+0.5)/10.0),GAME_WIDTH-200,23);


        //g.setColor(Color.black);
        //g.setFont(new Font("Arial",Font.PLAIN,18));
        //g.drawString("Health",15,22);




    }



    public void updateData(double deltaTime){
        mana+=deltaTime*1000;
        if(mana>=maxMana)mana=maxMana;

        if(xp>=xpUntilNextLevel){
            xp-=xpUntilNextLevel;
            currentLevel+=1;
            xpUntilNextLevel=xpUntilNextLevelBase*Math.pow(1.1 ,currentLevel);
            System.out.println(xpUntilNextLevel);
            maxMana=20+currentLevel;
            mana=maxMana;
            maxHealth=20+currentLevel;
            health=maxHealth;
            strength=10+currentLevel;
        }


    }

    public void drawRectWithBorder(Graphics g, int x, int y, int width, int height, int borderSize, Color c1, Color c2){
        g.setColor(c2);
        g.fillRect(x-borderSize,y-borderSize,width+2*borderSize,height+2*borderSize);
        g.setColor(c1 );
        g.fillRect(x,y,width,height);


    }

}
