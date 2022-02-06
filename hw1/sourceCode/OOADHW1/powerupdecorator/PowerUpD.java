package OOADHW1.powerupdecorator;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This class is a concrete class that represents the corresponding class
 * for low-to-high high-to-low Powerup operation.
 */
public class PowerUpD extends PowerUp {

    /**
     * Constructor
     */
    public PowerUpD() {
        super("Jump Powerup", 1);
    }

    /**
     * This method returns the message of the powerup operation from type string.
     *
     * @return message of the powerup operation
     */
    @Override
    public long getMultiplier() {
        return multiplier;
    }
}