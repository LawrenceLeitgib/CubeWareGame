import java.awt.*;

public class Stats {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    int strength;
    int speed;
    int maxHealth;
    int health;
    int maxMana;
    int mana;
    Stats(int GAME_WIDTH, int GAME_HEIGHT){

        this.GAME_WIDTH=GAME_WIDTH;
        this.GAME_HEIGHT=GAME_HEIGHT;

        strength=10;
        speed=10;
        maxHealth=100;
        health=100;
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
        g.fillRect(10,10,200*health/maxHealth,15);
        drawRectWithBorder(g,10,30,200,15,2,new Color(131, 201, 201),Color.black);
        g.setColor(new Color(0,0,255));
        g.fillRect(10,30,200*mana/maxMana,15);
        //g.setColor(Color.black);
        //g.setFont(new Font("Arial",Font.PLAIN,18));
        //g.drawString("Health",15,22);




    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void updateData(double deltaTime){


    }

    public void drawRectWithBorder(Graphics g, int x, int y, int width, int height, int borderSize, Color c1, Color c2){
        g.setColor(c2);
        g.fillRect(x-borderSize,y-borderSize,width+2*borderSize,height+2*borderSize);
        g.setColor(c1 );
        g.fillRect(x,y,width,height);


    }

}
