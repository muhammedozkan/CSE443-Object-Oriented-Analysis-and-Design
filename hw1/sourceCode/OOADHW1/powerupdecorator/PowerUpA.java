package OOADHW1.powerupdecorator;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This class is a concrete class that represents the corresponding class
 * for 2x Score Powerup operation.
 */
public class PowerUpA extends PowerDecorator {

    /**
     * @param powerUp object to keep inside
     */
    public PowerUpA(PowerUp powerUp) {
        super("2x Score Powerup", 2, powerUp);
    }
}