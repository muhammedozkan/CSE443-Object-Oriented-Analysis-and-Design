package OOADHW1;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import OOADHW1.jumpstrategy.JumpStrategy;
import OOADHW1.jumpstrategy.LowJump;
import OOADHW1.powerupdecorator.PowerUp;
import OOADHW1.powerupdecorator.PowerUpD;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class is used to display character on the screen.
 */
public class Character {
    public static final int LAND_POSY = 250;
    /**
     * gravitational-like force for Y-axis
     */
    public static final double PULL_FORCE = 0.0981;
    public static final int LIFE = 3;

    private boolean state;
    private double posY;
    private double posX;
    private double speedY;
    private double speedX;
    private Rectangle rectEdge;
    private long score = 0;
    private int life;
    private PowerUp multipliers;
    private JumpStrategy jump;
    private BufferedImage figure;


    public Character() {
        posX = 20;
        posY = LAND_POSY;

        rectEdge = new Rectangle();
        multipliers = new PowerUpD();
        jump = new LowJump();

        speedX = 0;
        state = true;
        ClassLoader cl = this.getClass().getClassLoader();

        try {
            figure = ImageIO.read(cl.getResource("images/figure.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter SpeedX
     *
     * @return actual speedX value
     */
    public double getSpeedX() {
        double tmp = speedX;
        speedX = 0;
        return tmp;
    }

    /**
     * Setter SpeedX
     *
     * @param speedX new x travel length
     */
    public void setSpeedX(double speedX) {
        this.speedX += speedX;
    }

    /**
     * Getter score
     *
     * @return game score
     */
    public long getScore() {
        return score;
    }

    /**
     * Getter remaining life value
     *
     * @return life value
     */
    public int getLife() {
        return life;
    }

    /**
     * Setter new life value
     *
     * @param life new life value
     */
    public void setLife(int life) {
        this.life = life;
    }

    /**
     * Getter JumpStrategy
     *
     * @return JumpStrategy object
     */
    public JumpStrategy getJump() {
        return jump;
    }

    /**
     * Setter JumpStrategy
     *
     * @param jump new JumpStrategy object
     */
    public void setJump(JumpStrategy jump) {
        this.jump = jump;
    }

    /**
     * Getter PowerUp
     *
     * @return PowerUp object
     */
    public PowerUp getMultipliers() {
        return multipliers;
    }

    /**
     * Setter PowerUp
     *
     * @param multipliers new PowerUp object
     */
    public void setMultipliers(PowerUp multipliers) {
        this.multipliers = multipliers;
    }


    /**
     * This method draws the character according to the
     * coordinates of the given graphic object.
     *
     * @param g graphics object to draw the character
     */
    public void draw(Graphics g) {
        g.drawImage(figure, (int) posX, (int) posY, null);
    }

    /**
     * This method updates the coordinates of the character according to speedY PULL_FORCE
     */
    public void update() {
        if (posY >= LAND_POSY) {
            posY = LAND_POSY;
        } else {
            speedY += PULL_FORCE;
            posY += speedY;
        }
    }

    /**
     * Creates and returns a rectangle object based
     * on the height and width of the image object held in it.
     *
     * @return returns a rectangle of the character
     */
    public Rectangle getBound() {
        rectEdge = new Rectangle();

        rectEdge.x = (int) posX;
        rectEdge.y = (int) posY - 10;
        rectEdge.width = figure.getWidth() - 10;
        rectEdge.height = figure.getHeight();

        return rectEdge;
    }

    /**
     * Setter state
     *
     * @param isDeath true if dead false if alive
     */
    public void dead(boolean isDeath) {
        state = !isDeath;
    }

    /**
     * Getter state
     *
     * @return true if alive false if dead
     */
    public boolean isAlive() {
        return state;
    }

    /**
     * This method resets all variables to initial value
     */
    public void reset() {
        posY = LAND_POSY;
        life = LIFE;
        score = 0;
        posX = 20;
        dead(false);
        multipliers = new PowerUpD();
        jump = new LowJump();

    }

    /**
     * This method makes the character jump according to the jump type
     */
    public void jump() {
        if (state) {
            if (posY >= LAND_POSY) {
                MainWindow.textArea.append(jump.getMessage() + " Jump\n");
                speedY = jump.doJump();
                posY += speedY;
            }
        }
    }

    /**
     * This method makes the character rightmove
     */
    public void right() {
        if (state) {
            setSpeedX(4);
        }
    }

    /**
     * This method updates the score based on the multiplier.
     */
    public void upScore() {
        score += multipliers.getMultiplier();
        MainWindow.textArea.append("Score: " + score + "\n");
    }

}
