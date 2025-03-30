import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GameFrame extends JFrame {
    GamePanel panel;
    GameFrame(){
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("Video Game");
        this.setResizable(true);
        this.setBackground(new Color(163, 210, 227));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack(); //fit the size of the pannel
        this.setVisible(true);
        this.setLocationRelativeTo(null);


    }

}
