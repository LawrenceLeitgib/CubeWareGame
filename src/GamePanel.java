import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class GamePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH=1000;
    static final int GAME_HEIGHT=(int)(GAME_WIDTH*(9.0/16 ));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    Thread gameThread;
    Image image;
    Graphics graphics;
    Stats stats;

    GameGrid gameGrid;



    GamePanel(){
        this.setFocusable(true); //read key strock
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);
        stats=new Stats(GAME_WIDTH,GAME_HEIGHT);


        gameThread=new Thread(this);
        gameThread.start();


    }
    public void newGameGrid(){
        gameGrid = new GameGrid(GAME_WIDTH,GAME_HEIGHT);

    }




    public void paint(Graphics g){
        image=createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);

    }
    public void draw(Graphics g){
        gameGrid.draw(g);
        stats.draw(g);

    }
    public class AL extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            //System.out.println(e.getKeyCode()+" = "+e.getKeyChar());
            gameGrid.player.keyPressed(e);


        }
        public void keyReleased(KeyEvent e){
            gameGrid.player.keyReleased(e);

        }
    }
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks=60;
        double ns=1000000000/amountOfTicks;
        double delta= 0;
        newGameGrid();
        while(true){
            long now =System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime=System.nanoTime();
            if(delta >=1){
                updateData(1/amountOfTicks);
                repaint();
                delta--;
            }

        }
    }

    public void updateData(double deltaTime) {
        gameGrid.updateData(deltaTime);
        stats.updateData(deltaTime);


    }
}
