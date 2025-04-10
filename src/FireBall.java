import java.awt.*;

public class FireBall extends Projectile {

    FireBall(double xPos, double yPos, double zPos, double angle, double speed, double size, double damage, boolean isFriendly) {
        super(xPos, yPos, zPos, angle, speed, size, damage, isFriendly);
    }
    public void draw(Graphics g) {
        super.draw(g);

        double[] info=getScreenPosAndSize();
        double newPosX=info[0];
        double newPosY=info[1];
        double newSize=info[2];
        double newPosX2=info[3];
        double newPosY2=info[4];
        double newSize2=info[5];
        g.setColor(new Color(10, 10, 10, 60));
        g.fillOval((int) (newPosX2), (int) (newPosY2 - newSize2 / 2), (int) newSize2, (int) newSize2);

        if (isFriendly()) {
            g.setColor(Color.yellow);
            g.fillOval((int) (newPosX - newSize / 5), (int) (newPosY - newSize / 2 - newSize / 5), (int) (newSize + newSize / 2.5), (int) (newSize + newSize / 2.5));
            g.setColor(Color.orange);
            g.fillOval((int) (newPosX - newSize / 10), (int) (newPosY - newSize / 2 - newSize / 10), (int) (newSize + newSize / 5), (int) (newSize + newSize / 5));
            g.setColor(Color.red);
            g.fillOval((int) (newPosX), (int) (newPosY - newSize / 2), (int) newSize, (int) newSize);
        }
        else {
            g.setColor(new Color(218, 12, 12));
            g.fillOval((int) (newPosX - newSize / 5), (int) (newPosY - newSize / 2 - newSize / 5), (int) (newSize + newSize / 2.5), (int) (newSize + newSize / 2.5));
            g.setColor(new Color(75, 0, 59));
            g.fillOval((int) (newPosX - newSize / 10), (int) (newPosY - newSize / 2 - newSize / 10), (int) (newSize + newSize / 5), (int) (newSize + newSize / 5));
            g.setColor(new Color(124, 16, 100));
            g.fillOval((int) (newPosX), (int) (newPosY - newSize / 2), (int) newSize, (int) newSize);
        }
    }

}
