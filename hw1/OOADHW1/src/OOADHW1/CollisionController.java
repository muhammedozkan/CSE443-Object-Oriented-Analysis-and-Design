package OOADHW1;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import OOADHW1.jumpstrategy.HighJump;
import OOADHW1.jumpstrategy.LowJump;
import OOADHW1.powerupdecorator.PowerUpA;
import OOADHW1.powerupdecorator.PowerUpB;
import OOADHW1.powerupdecorator.PowerUpC;
import OOADHW1.powerupdecorator.PowerUpD;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * This class handles the collisions and rendering of objects on the screen.
 */
public class CollisionController {

    private final Random rand;
    private final Character character;
    private ObstaclesView obstacle;

    private BufferedImage powerA;
    private BufferedImage powerB;
    private BufferedImage powerC;
    private BufferedImage powerD;

    private BufferedImage obstacle1;
    private BufferedImage obstacle2;
    private BufferedImage obstacle3;
    private BufferedImage obstacle4;


    public CollisionController(Character character) {
        rand = new Random();
        ClassLoader cl = this.getClass().getClassLoader();

        try {
            powerA = ImageIO.read(cl.getResource("images/triangle.png"));
            powerB = ImageIO.read(cl.getResource("images/star.png"));
            powerC = ImageIO.read(cl.getResource("images/circle.png"));
            powerD = ImageIO.read(cl.getResource("images/umbrella.png"));

            obstacle1 = ImageIO.read(cl.getResource("images/1.png"));
            obstacle2 = ImageIO.read(cl.getResource("images/2.png"));
            obstacle3 = ImageIO.read(cl.getResource("images/3.png"));
            obstacle4 = ImageIO.read(cl.getResource("images/4.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.character = character;
        obstacle = createObstacles();
    }

    /**
     * This method updates the obstacle and score.
     */
    public void update() {
        obstacle.update();

        if (obstacle.isOutOfScreen()) {
            if (obstacle.isPowerup == null)
                character.upScore();

            obstacle = null;
            obstacle = createObstacles();
        }
    }

    /**
     * This method draws the object of the given graphic object.
     *
     * @param g graphics object to draw
     */
    public void draw(Graphics g) {
        obstacle.draw(g);
    }

    /**
     * The method by which random rewards and enemies are generated
     *
     * @return object to draw
     */
    private ObstaclesView createObstacles() {

        int type = rand.nextInt(3);

        if (type % 2 == 0) // 2/3 chance
        {
            type = rand.nextInt(4);
            switch (type) {
                case 0:
                    return new ObstaclesView(character, 600, obstacle1.getWidth(), obstacle1.getHeight(), obstacle1, null);
                case 1:
                    return new ObstaclesView(character, 600, obstacle2.getWidth(), obstacle2.getHeight(), obstacle2, null);
                case 2:
                    return new ObstaclesView(character, 600, obstacle3.getWidth(), obstacle3.getHeight(), obstacle3, null);
                case 3:
                    return new ObstaclesView(character, 600, obstacle4.getWidth(), obstacle4.getHeight(), obstacle4, null);
                default:
                    return null;
            }
        } else {  // 1/3 chance
            type = rand.nextInt(4);
            switch (type) {
                case 0:
                    return new ObstaclesView(character, 600, powerA.getWidth(), powerA.getHeight(), powerA, new PowerUpA(character.getMultipliers()));
                case 1:
                    return new ObstaclesView(character, 600, powerB.getWidth(), powerB.getHeight(), powerB, new PowerUpB(character.getMultipliers()));
                case 2:
                    return new ObstaclesView(character, 600, powerC.getWidth(), powerC.getHeight(), powerC, new PowerUpC(character.getMultipliers()));
                case 3:
                    return new ObstaclesView(character, 600, powerD.getWidth(), powerD.getHeight(), powerD, new PowerUpD());
                default:
                    return null;
            }
        }
    }

    /**
     * This method checks for the main character to collide with the other object.
     */
    public boolean isCollision() {
        if (obstacle != null) {
            if (character.getBound().intersects(obstacle.getRect())) {
                if (obstacle.isPowerup != null) {
                    if (!(obstacle.isPowerup instanceof PowerUpD)) {
                        character.setMultipliers(obstacle.isPowerup);
                        MainWindow.textArea.append(character.getMultipliers().getMessage() + " Gained. Total Multiplier " + character.getMultipliers().getMultiplier() + "x\n");
                    } else {
                        //jump power
                        if (character.getJump() instanceof LowJump) {
                            character.setJump(new HighJump());
                        } else {
                            character.setJump(new LowJump());
                        }
                        MainWindow.textArea.append(obstacle.isPowerup.getMessage() + " " + character.getJump().getMessage() + " Gained." + "\n");
                    }
                } else {
                    character.setLife(character.getLife() - 1);
                    MainWindow.textArea.append("A Collision. Remaining life: " + character.getLife() + "\n");
                }
                obstacle = null;
                obstacle = createObstacles();

                return true;
            }
        }
        return false;
    }

    /**
     * This method resets all variables to initial value
     */
    public void reset() {
        obstacle = null;
        obstacle = createObstacles();
    }


}
