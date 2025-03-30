import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {
    static int GAME_WIDTH=1000;
    static int GAME_HEIGHT=(int)(GAME_WIDTH*(9.0/16 ));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    Thread gameThread;
    static Image image;
    Graphics graphics;
    Stats stats;

    GameGrid gameGrid;


//    static BufferedImage smiley;
    static double xPos;
    static double yPos;

    static double FPS;
    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    // Create a new blank cursor.
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

    static Dictionary<String, Integer> GameStates = new Hashtable<>();
    static int gameState;

    static Rectangle[] rectForDraw=new Rectangle[100];


    GamePanel() {
        this.setFocusable(true); //read key strock
        this.addKeyListener(new AL());
        this.addMouseListener(new ML());
        this.addMouseMotionListener(new ML());
        this.setPreferredSize(SCREEN_SIZE);
        try{
        yPos=this.getLocationOnScreen().getY() ;
        xPos=this.getLocationOnScreen().getX();} catch (Exception ignored) {
        }
        stats=new Stats(GAME_WIDTH,GAME_HEIGHT);
        gameThread=new Thread(this);
        gameThread.start();
        GameStates.put("Menu",0);
        GameStates.put("Running",1);
        GameStates.put("Paused",2);
        GameStates.put("Dead",3);
        GameStates.put("Stats",4);
        gameState=GameStates.get("Menu");
        rectForDraw[0]=new Rectangle(GameGrid.GAME_WIDTH/2-100,GameGrid.GAME_HEIGHT/3,200,40);
        rectForDraw[1]=new Rectangle(15,200,100,20);




    }
    public void newGameGrid(){
        gameGrid = new GameGrid();
    }
    public void paint(Graphics g){
        image=createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }
    static public boolean isInisdeRect(int xPos,int yPos,Rectangle rect){

        if(xPos>rect.getX()&&xPos< rect.getX()+ rect.getWidth()&&yPos>rect.getY()&&yPos<rect.getY()+rect.getHeight())return true;
        return false;
    }
    static public void drawRect(Graphics g, Rectangle rect,Color color){
        g.setColor(color);
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }
    static public void drawRectWithBorder(Graphics g, Rectangle rect,Color color1,Color color2,int size){
        g.setColor(color2);
        g.fillRect((int) rect.getX()-size/2, (int) rect.getY()-size/2, (int) rect.getWidth()+size, (int) rect.getHeight()+size);
        g.setColor(color1);
        g.fillRect((int) rect.getX()+size/2, (int) rect.getY()+size/2, (int) rect.getWidth()-size, (int) rect.getHeight()-size);
    }

    static public void drawRectWithBorder2(Graphics g, Rectangle rect,Color color1,Color color2,int size){
        g.setColor(color2);
        g.fillRect((int) rect.getX()-size, (int) rect.getY()-size, (int) rect.getWidth()+size*2, (int) rect.getHeight()+size*2);
        g.setColor(color1);
        g.fillRect((int) rect.getX(), (int) rect.getY(), (int) rect.getWidth(), (int) rect.getHeight());
    }


    static public void drawRectWithContext(Graphics g, Rectangle rect,Color color1,Color color2,int size){
        if(isInisdeRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rect)){
            drawRectWithBorder(g,rect,color1,color2,size);
        }else{
            drawRect(g,rect,color1);
        }

    }
    static public void drawRectWithContext2(Graphics g, Rectangle rect,Color color1,Color color2,int size){
        if(isInisdeRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rect)){
            drawRectWithBorder(g,rect,color1,color2,size);
        }

    }
    static public void centerString(Graphics g, Rectangle r, String s,
                             Font font) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }
    public void draw(Graphics g){
        Rectangle r = getBounds();
        //r=new Rectangle(GAME_WIDTH,GAME_HEIGHT);

        if(GameGrid.GAME_HEIGHT!=r.height||GameGrid.GAME_WIDTH!=r.width) {

            GameGrid.setGameHeight(r.height);
            GameGrid.setGameWidth(r.width);
            Stats.setGameHeight(r.height);
            Stats.setGameWidth(r.width);
            Player.setGameHeight(r.height);
            Player.setGameWidth(r.width);
            Cube.setGameHeight(r.height);
            Cube.setGameWidth(r.width);
            CubeContainer.setGameHeight(r.height);
            CubeContainer.setGameWidth(r.width);
            /*
            Cube.defaultSize=r.width/10;
            Cube.width= Cube.defaultSize;
            Cube.height= Cube.defaultSize;
            Cube.depth= Cube.defaultSize;
            Player.width=.8 *Cube.defaultSize;
            Player.depth=.8 *Cube.defaultSize;
            Player.height=1.8* Cube.defaultSize;
            for(Enemy enemy :EntityContainer.enemies){
                enemy.height=2* Cube.defaultSize;
                enemy.width=.8* Cube.defaultSize;
                enemy.depth=.8* Cube.defaultSize;

            }
            System.out.println("test");

             */
            rectForDraw[0]=new Rectangle(GameGrid.GAME_WIDTH/2-100,GameGrid.GAME_HEIGHT/3,200,40);


        }
        gameGrid.draw(g);
        stats.draw(g);
        if(gameState==GameStates.get("Menu")){
            Player.draw(g);
            g.setColor(new Color(84, 84, 84,200));
            g.fillRect(0,0,GameGrid.GAME_WIDTH,GameGrid.GAME_HEIGHT);
            drawRectWithContext(g,rectForDraw[0],new Color(168, 113, 10),Color.yellow,4);
            g.setColor(Color.black);
            centerString(g,rectForDraw[0],"Start",new Font("Arial",Font.PLAIN,30));

        }
        if(gameState==GameStates.get("Paused")){
            g.setColor(new Color(84, 84, 84,200));
            g.fillRect(0,0, GameGrid.GAME_WIDTH,GameGrid.GAME_HEIGHT);
            drawRectWithContext(g,rectForDraw[0],new Color(168, 113, 10),Color.yellow,4);
            g.setColor(Color.black);
            centerString(g,rectForDraw[0],"Continue",new Font("Arial",Font.PLAIN,30));

        }
        if(gameState==GameStates.get("Dead")){
            g.setColor(new Color(84, 84, 84,200));
            g.fillRect(0,0, GameGrid.GAME_WIDTH,GameGrid.GAME_HEIGHT);
            drawRectWithContext(g,rectForDraw[0],new Color(168, 113, 10),Color.yellow,4);
            g.setColor(Color.black);
            centerString(g,rectForDraw[0],"Respawn",new Font("Arial",Font.PLAIN,30));

        }
        if(gameState==GameStates.get("Running")){
            drawRectWithContext(g,rectForDraw[1],new Color(168, 113, 10),Color.yellow,4);
            g.setColor(Color.black);
            centerString(g,rectForDraw[1],"Kill All entities",new Font("Arial",Font.PLAIN,16));

        }
        if(gameState==GameStates.get("Stats")){
            stats.drawE(g);
        }

    }
    public void updateData(double deltaTime) {
        try{
            yPos=this.getLocationOnScreen().getY() ;
            xPos=this.getLocationOnScreen().getX();} catch (Exception ignored) {
        }if(!Player.thirdPerspective&&GamePanel.gameState==GamePanel.GameStates.get("Running"))this.setCursor(blankCursor);
        else{this.setCursor(Cursor.getDefaultCursor());}



        if(gameState==GameStates.get("Menu")){
            if(isInisdeRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rectForDraw[0])){
                if(GameGrid.mouseLeftClickDown)gameState=GameStates.get("Running");
            }
            return;
        }
        if(gameState==GameStates.get("Paused")){
            if(isInisdeRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rectForDraw[0])){
                if(GameGrid.mouseLeftClickDown)gameState=GameStates.get("Running");
            }
            return;
        }
        if(gameState==GameStates.get("Dead")){
            if(isInisdeRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rectForDraw[0])){
                if(GameGrid.mouseLeftClickDown)gameGrid.player.respawn();
            }
            return;
        }
        if(gameState==GameStates.get("Stats")){
           stats.updateDataE(deltaTime);
           return;
        }
        if(gameState==GameStates.get("Running")){
            if(isInisdeRect(GameGrid.mousePositionX,GameGrid.mousePositionY,rectForDraw[1])){
                if(GameGrid.mouseLeftClickDown){
                    Stats.xp+= EntityContainer.enemies.size()*25;
                    EntityContainer.enemies=new ArrayList<Enemy>();
                }
            }
        }
        gameGrid.updateData(deltaTime);
        stats.updateData(deltaTime);




    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks=120;
        double ns=1000000000/amountOfTicks;
        double delta= 0;
        newGameGrid();
        double accumulator=0;
        while(true){
            long now =System.nanoTime();
            delta=(now-lastTime)/ns;
            lastTime=System.nanoTime();
            accumulator+=delta;
            //System.out.println(accumulator);
            while (accumulator>=0){
                //System.out.println("test");
                updateData(1/amountOfTicks);
                accumulator--;
                // count++;
            }
            paintImmediately(0,0,GameGrid.GAME_WIDTH,GameGrid.GAME_HEIGHT);
            FPS=amountOfTicks/((System.nanoTime()-(now))/ns);


            /*
                if(count>=3&&delta>=2){
                    repaint();
                    //removeAll();
                    count=0;

            }

             */


        }
    }

    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            //System.out.println(e.getKeyCode()+" = "+e.getKeyChar());
            gameGrid.player.keyPressed(e);
            gameGrid.player.SMH.keyPressed(e);
            gameGrid.keyPressed(e);
            if(e.getKeyCode()==27){
                togglePause();
            }
            if(e.getKeyCode()==69){
                toggleState();
            }


        }
        public void keyReleased(KeyEvent e){
            gameGrid.player.keyReleased(e);
            gameGrid.player.SMH.keyReleased(e);
            gameGrid.keyReleased(e);

        }
    }

    private void togglePause() {
        if(gameState==GameStates.get("Running")){
            gameState=GameStates.get("Paused");

        }
        else if(gameState==GameStates.get("Paused")){
            gameState=GameStates.get("Running");
        }
    }

    private void toggleState() {
        if(gameState==GameStates.get("Running")){
            gameState=GameStates.get("Stats");

        }
        else if(gameState==GameStates.get("Stats")){
            gameState=GameStates.get("Running");
        }
    }

    public class ML implements MouseListener,MouseMotionListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
           // System.out.println(e.getX());
           // System.out.println("test");
           // System.out.println(e.getButton());

            gameGrid.mousePressed(e);

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            gameGrid.mouseReleased(e);

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            gameGrid.mouseDragged(e);

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            gameGrid.mouseMoved(e);
        }
    }




}
