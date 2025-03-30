import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Stats {
    static double strength;
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
    static Dictionary<String, int[]> SpecialAttack = new Hashtable<String, int[]>();
    static int statsState;
    static int numOfRectForStat =6;
    static Rectangle[] rectForStats=new Rectangle[numOfRectForStat];
    static int numOfRectAS=2;
    static Rectangle[][] rectForAS=new Rectangle[numOfRectAS][2];
    double PFSMean;
    Stats(){
        bigRect=centerRectangle(GameGrid.GAME_WIDTH/2,GameGrid.GAME_HEIGHT/2,GameGrid.GAME_WIDTH-100,GameGrid.GAME_HEIGHT-100);
        StatsStates.put("General",0);
        StatsStates.put("Special_Attack",1);
        SpecialAttack.put("Dash", new int[]{5,4});
        SpecialAttack.put("FBT", new int[]{10,10});
        statsState=StatsStates.get("General");
        currentLevel=10;
        setRectForStats();
        setPlayerStats();
        setRectForAS();

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
    public static void setRectForAS(){
        for(var i=0;i<numOfRectAS;i++){
            rectForAS[i][0]=new Rectangle(bigRect.x+5,bigRect.y+40+35*i,200,30);
            rectForAS[i][1]=new Rectangle(bigRect.x+5+200+5,bigRect.y+40+35*i,bigRect.width-220,30);

        }
    }
    public static void setGameWidth(int gameWidth) {
        bigRect=centerRectangle(GameGrid.GAME_WIDTH/2,GameGrid.GAME_HEIGHT/2,GameGrid.GAME_WIDTH-100,GameGrid.GAME_HEIGHT-100);
        setRectForStats();
    }
    public static void setGameHeight(int gameHeight) {
        bigRect=centerRectangle(GameGrid.GAME_WIDTH/2,GameGrid.GAME_HEIGHT/2,GameGrid.GAME_WIDTH-100,GameGrid.GAME_HEIGHT-100);
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


    public void drawBar(Graphics g,Rectangle rect,Color c1,Color c2,Color c3,double a,double b){
        GamePanel.drawRectWithBorder2(g,rect,c1,c3,2);
        g.setColor(c2);
        g.fillRect(rect.x,rect.y, (int) (rect.width*a/b),rect.height);
    }
    public void drawGameInfo(Graphics g){
        if(!GameGrid.F3Down)return;
        g.setColor(new Color(100,100,100,200));
        g.fillRect(10,50,200,200);
        g.setColor(Color.black);
        g.setFont(new Font("Arial",Font.PLAIN,24));
        g.setColor(Color.red);
        g.drawString("x: "+Player.xPosition,15,70);
        g.drawString("y: "+Player.yPosition,15,90);
        g.drawString("z: "+Player.zPosition,15,110);
        g.drawString("FPS: "+(int)(PFSMean+.5),15,130);
        g.drawString("entities: "+ EntityContainer.entities.size(),15,150);
        g.drawString("level: "+Stats.currentLevel,15,170);
        g.drawString("ball: "+ ProjectileContainer.Projectiles.size(),15,190);
        GamePanel.drawRectWithContext(g,GamePanel.rectForDraw[1],new Color(168, 113, 10),Color.yellow,4);
        g.setColor(Color.black);
        GamePanel.centerString(g,GamePanel.rectForDraw[1],"Kill All entities",new Font("Arial",Font.PLAIN,16));

        if(GamePanel.isInsideRect(GameGrid.mousePositionX,GameGrid.mousePositionY,GamePanel.rectForDraw[1])){
            if(GameGrid.mouseLeftClickDown){
                Stats.xp+= EntityContainer.entities.size()*25;
                EntityContainer.entities =new ArrayList<Entity>();
            }
        }
    }

    public void draw(Graphics g){
        FPSDisplay();
        Rectangle healthRect=new Rectangle(10,10,200,15);
        Rectangle manaRect=new Rectangle(10,30,200,15);
        Rectangle xpRect=new Rectangle(GameGrid.GAME_WIDTH-10-200,10,200,15);

        drawBar(g,healthRect, Color.red,Color.green,Color.BLACK,  health,  maxHealth);
        drawBar(g,manaRect, new Color(131, 201, 201),new Color(0,0,255),Color.BLACK,  mana,  maxMana);
        drawBar(g,xpRect, new Color(0, 150, 80),new Color(0, 255, 140),Color.BLACK,  xp,  xpUntilNextLevel);


        g.setFont(new Font("Arial",Font.PLAIN,16));
        g.setColor(Color.black);
        g.drawString("XP:"+String.valueOf((int)(xp*10+0.5)/10.0)+" / "+String.valueOf((int)(xpUntilNextLevel*10+0.5)/10.0),GameGrid.GAME_WIDTH-200,23);


        if(GamePanel.gameState==GamePanel.GameStates.get("Running")){
            drawGameInfo(g);
        }
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
            if(GamePanel.isInsideRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rectForStats[i])){
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
        GamePanel.centerString(g, rectForStats[StatsStates.get("Special_Attack")], "Special Attack",new Font("Arial", Font.PLAIN, 22));

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
        if(statsState==StatsStates.get("Special_Attack")){
            for (var i = 0; i < numOfRectAS; i++) {
                GamePanel.drawRectWithBorder(g, rectForAS[i][0], new Color(176, 91, 0, 255), new Color(129, 124, 0), 2);
                GamePanel.drawRectWithBorder(g, rectForAS[i][1], new Color(176, 91, 0, 255), new Color(129, 124, 0), 2);

            }
            drawRectWithLevel(g, rectForAS[0][0],SpecialAttack.get("Dash")[0]);
            drawRectWithLevel(g, rectForAS[1][0],SpecialAttack.get("FBT")[0]);
            drawRectWithLevel(g, rectForAS[0][1],SpecialAttack.get("Dash")[0]);
            drawRectWithLevel(g, rectForAS[1][1],SpecialAttack.get("FBT")[0]);
            g.setColor(Color.black);
            if(currentLevel>=SpecialAttack.get("Dash")[0])GamePanel.centerString(g, rectForAS[0][0], "Dash",new Font("Arial", Font.PLAIN, 18));
            if(currentLevel>=SpecialAttack.get("FBT")[0]) GamePanel.centerString(g, rectForAS[1][0], "Fire tornado",new Font("Arial", Font.PLAIN, 18));
            if(currentLevel>=SpecialAttack.get("Dash")[0])GamePanel.centerString(g, rectForAS[0][1], "Cost: "+SpecialAttack.get("Dash")[1]+" mana, use the touch Q to dash",new Font("Arial", Font.PLAIN, 18));
            if(currentLevel>=SpecialAttack.get("FBT")[0]) GamePanel.centerString(g, rectForAS[1][1], "Cost: "+SpecialAttack.get("FBT")[1]+" mana, use the touch 1 to create a tornado of fire ball",new Font("Arial", Font.PLAIN, 18));

        }

    }
    public void drawRectWithLevel(Graphics g,Rectangle rect,int level){
        if(currentLevel<level){
            GamePanel.drawRect(g,rect,new Color(100,100,100,200));
            g.setColor(Color.black);
            GamePanel.centerString(g, rect, "Unlock at level:"+level,new Font("Arial", Font.PLAIN, 18));
        }
    }
}
