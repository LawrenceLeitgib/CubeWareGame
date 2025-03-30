import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH=1000;
    static final int GAME_HEIGHT=(int)(GAME_WIDTH*(9.0/16 ));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    Thread gameThread;
    static Image image;
    Graphics graphics;
    Stats stats;

    GameGrid gameGrid;

//    static BufferedImage smiley;







    GamePanel() {
        this.setFocusable(true); //read key strock
        this.addKeyListener(new AL());
        this.addMouseListener(new ML());
        this.addMouseMotionListener(new ML());
        this.setPreferredSize(SCREEN_SIZE);

        //smiley =new ImageIcon("pictures/smiley.png");
        stats=new Stats(GAME_WIDTH,GAME_HEIGHT);

        gameThread=new Thread(this);
        gameThread.start();
/*
        try{
            smiley= ImageIO.read(getClass().getResourceAsStream("/pictures/smiley.png"));
        }catch (IOException e){
            e.printStackTrace();
        }

 */



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
    public void draw(Graphics g){
        Rectangle r = getBounds();
        //r=new Rectangle(GAME_WIDTH,GAME_HEIGHT);
        if(GameGrid.GAME_HEIGHT!=r.height) {

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
            Chunk.GAME_WIDTH = r.width;
            Chunk.GAME_HEIGHT = r.height;
        }




         gameGrid.draw(g);
        stats.draw(g);

    }




    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            //System.out.println(e.getKeyCode()+" = "+e.getKeyChar());
            gameGrid.player.keyPressed(e);
            gameGrid.keyPressed(e);


        }
        public void keyReleased(KeyEvent e){
            gameGrid.player.keyReleased(e);
            gameGrid.keyReleased(e);

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

            if(e.getButton()==1)
            GameGrid.mouseLeftClickDown =true;

            if(e.getButton()==3)GameGrid.mouseRightClickDown =true;

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(e.getButton()==1)
            GameGrid.mouseLeftClickDown =false;

            if(e.getButton()==3)GameGrid.mouseRightClickDown =false;


        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {
            GameGrid.mousePositionX =e.getX();
            GameGrid.mousePositionY =e.getY();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            GameGrid.mousePositionX =e.getX();
            GameGrid.mousePositionY =e.getY();


        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks=72;
        double ns=1000000000/amountOfTicks;
        double delta= 0;
        newGameGrid();
        int count=0;
        while(true){
            long now =System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=System.nanoTime();
            //System.out.println(delta);
            if(delta >=1){
                updateData(1/amountOfTicks);
                count++;
                if(count>=2){
                    removeAll();
                    repaint();
                    removeAll();
                    count=0;
                }

                delta--;
            }
        }
    }

    public void updateData(double deltaTime) {
        gameGrid.updateData(deltaTime);
        stats.updateData(deltaTime);


    }
}
