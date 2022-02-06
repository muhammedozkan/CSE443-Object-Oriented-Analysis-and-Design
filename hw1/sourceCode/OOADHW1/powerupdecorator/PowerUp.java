package OOADHW1.powerupdecorator;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This abstract class defines some methods for powerup operations.
 */
public abstract class PowerUp {
    /**
     * Power-up Message
     */
    protected final String message;

    /**
     * Power-up score multiplier
     */
    protected final int multiplier;

    /**
     * @param message    message of the power-up operation
     * @param multiplier amount of power up multiplier
     */
    public PowerUp(String message, int multiplier) {
        this.message = message;
        this.multiplier = multiplier;
    }

    /**
     * This method returns the message of the powerup operation from type string.
     *
     * @return message of the powerup operation
     */
    public String getMessage() {
        return message;
    }

    /**
     * This abstract method returns the multiplier of the powerup operation.
     *
     * @return amount of powerup multiplier
     */
    public abstract long getMultiplier();
}