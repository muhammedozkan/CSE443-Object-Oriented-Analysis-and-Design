package OOADHW1.powerupdecorator;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This class is a concrete class that represents the corresponding class
 * for 10x Score Powerup operation.
 */
public class PowerUpC extends PowerDecorator {
    /**
     * @param powerUp object to keep inside
     */
    public PowerUpC(PowerUp powerUp) {
        super("10x Score Powerup", 10, powerUp);
    }
}