package OOADHW1.powerupdecorator;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This class is a concrete class that represents the corresponding class
 * for 5x Score Powerup operation.
 */
public class PowerUpB extends PowerDecorator {
    /**
     * @param powerUp object to keep inside
     */
    public PowerUpB(PowerUp powerUp) {
        super("5x Score Powerup", 5, powerUp);
    }
}