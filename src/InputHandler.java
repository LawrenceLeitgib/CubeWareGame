import java.awt.*;
import java.awt.event.*;

public class InputHandler {
    GameGrid gameGrid;

    double flyingCount=0;
    double flyingTime=.2;
    boolean spaceHasBeenClick;

    boolean spaceHasBeenReleased;
    public void update(double deltaTime){
        flySwitchHandler(deltaTime);

    }
    private void flySwitchHandler(double deltaTime){
        if(flyingCount>0){
            flyingCount+=deltaTime;
            if(spaceHasBeenClick){
                gameGrid.player.toggleFly();
                spaceHasBeenClick=false;
            }
        }
        if(spaceHasBeenClick){
            flyingCount+=deltaTime;
            spaceHasBeenClick=false;
        }
        if(flyingCount>flyingTime){
            flyingCount=0;
        }
    }
    public InputHandler(GameGrid gameGrid) {
        this.gameGrid=gameGrid;
    }

    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode()+" = "+e.getKeyChar());
        gameGrid.player.keyPressed(e);
        gameGrid.player.SMH.keyPressed(e);
        gameGrid.keyPressed(e);

        switch(e.getKeyCode()){
            case 32:
                if(spaceHasBeenReleased){
                    spaceHasBeenClick=true;
                    spaceHasBeenReleased=false;
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        gameGrid.player.keyReleased(e);
        gameGrid.player.SMH.keyReleased(e);
        gameGrid.keyReleased(e);
        switch(e.getKeyCode()){
            case 32:
                spaceHasBeenReleased=true;
                break;
        }
    }
    public void mousePressed(MouseEvent e){
        gameGrid.mousePressed(e);
    }
    public void mouseReleased(MouseEvent e){
        gameGrid.mouseReleased(e);
    }
    public void mouseDragged(MouseEvent e){
        gameGrid.mouseDragged(e);

    }
    public void mouseMoved(MouseEvent e){
        gameGrid.mouseMoved(e);

    }
}
