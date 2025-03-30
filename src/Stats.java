import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class Stats {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;

    static double strength;
    int speed;
    static double maxHealth;
    static double health;

    static double healthRecovery;
    static double maxMana;
    static double mana;

    static double manaRecovery;

    static double xp=0;
    static double xpUntilNextLevelBase=100;
    static double xpUntilNextLevel=xpUntilNextLevelBase;
    static int currentLevel=0;

    static int numOfPFS=10;

    static double[] LastPFSList =new double[numOfPFS];

    int LastPFSCount=0;
    static Rectangle bigRect;

    static Dictionary<String, Integer> StatsStates = new Hashtable<>();
    static int statsState;

    static int numOfRectForStat=6;
    static Rectangle[] rectForStats=new Rectangle[numOfRectForStat];
    double PFSMean;


    Stats(int GAME_WIDTH, int GAME_HEIGHT){

        Stats.GAME_WIDTH =GAME_WIDTH;
        Stats.GAME_HEIGHT =GAME_HEIGHT;
        bigRect=centerRectangle(GAME_WIDTH/2,GAME_HEIGHT/2,GAME_WIDTH-100,GAME_HEIGHT-100);
        StatsStates.put("General",0);
        statsState=StatsStates.get("General");
        currentLevel=0;
        setRectForStats();
        setPlayerStats();
    }

    public void setPlayerStats(){
        xpUntilNextLevel=xpUntilNextLevelBase*Math.pow(1.1 ,currentLevel);
        maxMana=10+currentLevel;
        mana=maxMana;
        maxHealth=10+currentLevel;
        health=maxHealth;
        strength=10+currentLevel;
        healthRecovery=1+.05*currentLevel;
        manaRecovery=1+.05*currentLevel;
    }

    public void regenerate(double deltaTime){
        mana+=deltaTime*manaRecovery;
        health+=deltaTime*healthRecovery;
        if(Player.distanceFromMiddle<GameGrid.regenZone){
            mana+=deltaTime*(maxMana);
            health+=deltaTime*(maxHealth);
        }
        if(mana>=maxMana)mana=maxMana;
        if(health>=maxHealth)health=maxHealth;
    }

    public void levelUpHandler(){
        if(xp>=xpUntilNextLevel){
            xp-=xpUntilNextLevel;
            currentLevel+=1;
            setPlayerStats();
        }
    }
    public static void setRectForStats(){
        for(var i=0;i<numOfRectForStat;i++){
            rectForStats[i]=new Rectangle(bigRect.x+5+i*(bigRect.width-10)/numOfRectForStat,bigRect.y+5,(bigRect.width-10)/numOfRectForStat,30);
        }

    }
    public static void setGameWidth(int gameWidth) {
        GAME_WIDTH = gameWidth;
        bigRect=centerRectangle(GAME_WIDTH/2,GAME_HEIGHT/2,GAME_WIDTH-100,GAME_HEIGHT-100);
        setRectForStats();

    }

    public void deathHandle(){
        if(health<=0){
            health=0;
            GamePanel.gameState=GamePanel.GameStates.get("Dead");
        }
    }

    public void FPSDisplay(){

        if(LastPFSCount>=numOfPFS)LastPFSCount=0;
        LastPFSList[LastPFSCount]=GamePanel.FPS;
        LastPFSCount++;

        PFSMean=0;

        for(var i=0;i<numOfPFS;i++){
            PFSMean+=LastPFSList[i];
        }
        PFSMean/=numOfPFS;
    }
    public static void setGameHeight(int gameHeight) {
        GAME_HEIGHT = gameHeight;
        bigRect=centerRectangle(GAME_WIDTH/2,GAME_HEIGHT/2,GAME_WIDTH-100,GAME_HEIGHT-100);
        setRectForStats();

    }

    public void drawBar(Graphics g,Rectangle rect,Color c1,Color c2,Color c3,double a,double b){
        GamePanel.drawRectWithBorder2(g,rect,c1,c3,2);
        g.setColor(c2);
        g.fillRect(rect.x,rect.y, (int) (rect.width*a/b),rect.height);
    }

    public void draw(Graphics g){

        FPSDisplay();

        Rectangle HealthRect=new Rectangle(10,10,200,15);


        /*drawRectWithBorder(g,10,10,200,15,2,Color.RED,Color.black);
        g.setColor(Color.green);
        g.fillRect(10,10, (int) (200*health/maxHealth),15);
          GamePanel.drawRectWithBorder2(g,HealthRect,Color.RED,Color.BLACK,2);
        GamePanel.drawRect(g,HealthRect,Color.green);

         */

        drawBar(g,HealthRect, Color.red,Color.green,Color.BLACK,  health,  maxHealth);

        drawRectWithBorder(g,10,30,200,15,2,new Color(131, 201, 201),Color.black);
        g.setColor(new Color(0,0,255));
        g.fillRect(10,30, (int) (200*mana/maxMana),15);

        if(GamePanel.gameState==GamePanel.GameStates.get("Running")){
        g.setColor(new Color(100,100,100,200));
        g.fillRect(10,50,200,200);
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.PLAIN,24));
        g.setColor(Color.red);
        g.drawString("x: "+Player.xPosition,15,70);
        g.drawString("y: "+Player.yPosition,15,90);
        g.drawString("z: "+Player.zPosition,15,110);
        g.drawString("FPS: "+(int)(PFSMean+.5),15,130);
        g.drawString("entities: "+ EntityContainer.enemies.size(),15,150);
        g.drawString("level: "+Stats.currentLevel,15,170);
        g.drawString("ball: "+ ProjectileContainer.Projectiles.size(),15,190);}




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
        regenerate(deltaTime);
        levelUpHandler();
        deathHandle();
    }
    static public void drawRectWithBorder(Graphics g, int x, int y, int width, int height, int borderSize, Color c1, Color c2){
        g.setColor(c2);
        g.fillRect(x-borderSize,y-borderSize,width+2*borderSize,height+2*borderSize);
        g.setColor(c1 );
        g.fillRect(x,y,width,height);
    }

    public static Rectangle centerRectangle(int x, int y, int w, int h){

        return new Rectangle(x-w/2,y-h/2,w,h);
    }
    public void updateDataE(double deltaTime) {

        for(var i=0;i<numOfRectForStat;i++){
            if(GamePanel.isInisdeRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rectForStats[i])){
                if(GameGrid.mouseLeftClickDown)statsState=i;
            }
        }
    }

    public void drawE(Graphics g) {
        GamePanel.drawRectWithBorder(g, bigRect, new Color(0, 0, 0, 200), new Color(255, 255, 255, 20), 2);
        for (var i = 0; i < numOfRectForStat; i++) {
            GamePanel.drawRectWithBorder(g, rectForStats[i], new Color(79, 40, 0, 255), new Color(129, 124, 0), 2);
        }
        for (var i = 0; i < numOfRectForStat; i++) {
            GamePanel.drawRectWithContext2(g, rectForStats[i], new Color(79, 40, 0, 255), new Color(255, 245, 0), 4);
        }
        GamePanel.drawRectWithBorder(g, rectForStats[statsState], new Color(79, 40, 0, 255), new Color(255, 245, 0), 4);
        GamePanel.drawRectWithContext2(g, rectForStats[statsState], new Color(79, 40, 0, 255), new Color(255, 245, 0), 4);
        g.setColor(Color.BLACK);
        GamePanel.centerString(g, rectForStats[StatsStates.get("General")], "General",new Font("Arial", Font.PLAIN, 22));

        if(statsState==StatsStates.get("General")){
            g.setFont(new Font("Arial",Font.PLAIN,20));
            g.setColor(Color.white);
            g.drawString("Level: "+currentLevel, bigRect.x+10, bigRect.y+70);
            g.drawString("Strength: "+strength+" ("+10+" + "+currentLevel+")", bigRect.x+10, bigRect.y+95);
            g.drawString("Mana: "+maxMana+" ("+10+" + "+currentLevel+")", bigRect.x+10, bigRect.y+120);
            g.drawString("ManaRecovery: "+manaRecovery+" ("+1+" + "+currentLevel*.05+")", bigRect.x+10, bigRect.y+145);
            g.drawString("Health: "+maxHealth+" ("+10+" + "+currentLevel+")", bigRect.x+10, bigRect.y+170);
            g.drawString("HealthRecovery: "+healthRecovery+" ("+1+" + "+currentLevel*.05+")", bigRect.x+10, bigRect.y+195);







        }
    }
}
