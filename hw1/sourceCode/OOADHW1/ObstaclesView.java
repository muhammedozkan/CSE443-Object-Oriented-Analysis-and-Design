package OOADHW1;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import OOADHW1.powerupdecorator.PowerUp;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This class is used to display enemies and powerups on the screen.
 */
public class ObstaclesView {

    public static final int Y_SIZE = 350;
    private final int width;
    private final int height;
    private final Character character;
    private final BufferedImage image;
    public PowerUp isPowerup;
    private double posX;
    private Rectangle rectCoordinate;

    /**
     * @param character to get the speed of the shape shifting from right to left.
     * @param posX      the position of the shape on the x-axis
     * @param width     width of shape
     * @param height    height of shape
     * @param image     shape
     * @param isPowerup powerup type, if any. otherwise it can be null.
     */
    public ObstaclesView(Character character, int posX, int width, int height, BufferedImage image, PowerUp isPowerup) {
        this.posX = posX;
        this.width = width;
        this.height = height;
        this.character = character;
        this.image = image;
        this.isPowerup = isPowerup;
        rectCoordinate = new Rectangle();
    }


    /**
     * This method updates the coordinates of the shape according to speedX
     */
    public void update() {
        posX -= character.getSpeedX();
    }


    /**
     * This method draws the shape according to the
     * coordinates of the given graphic object.
     *
     * @param g graphics object to draw the shape
     */
    public void draw(Graphics g) {
        g.drawImage(image, (int) posX, Y_SIZE - image.getHeight(), null);
    }


    /**
     * Creates and returns a rectangle object based
     * on the height and width of the image object held in it.
     *
     * @return returns a rectangle of the shape
     */
    public Rectangle getRect() {
        rectCoordinate = new Rectangle();
        rectCoordinate.x = (int) posX + (image.getWidth() - width) / 2;
        rectCoordinate.y = Y_SIZE - image.getHeight() + (image.getHeight() - height) / 2;
        rectCoordinate.width = width;
        rectCoordinate.height = height;
        return rectCoordinate;
    }


    /**
     * This method that controls the visibility of the shape on the screen
     *
     * @return If the shape is visible on the screen, it returns false if it is not true.
     */
    public boolean isOutOfScreen() {
        return posX < -image.getWidth();
    }
}
