package OOADHW1.powerupdecorator;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This abstract class defines some methods for abstract powerup class.
 */
public abstract class PowerDecorator extends PowerUp {

    /**
     * Composition Power-up object
     */
    protected final PowerUp power;

    /**
     * @param message    message of the powerup operation
     * @param multiplier amount of powerup multiplier
     * @param power      internal boost powerup(recursively)
     */
    public PowerDecorator(String message, int multiplier, PowerUp power) {
        super(message, multiplier);
        this.power = power;
    }

    /**
     * This abstract method returns the multiplier of the powerup operation.
     *
     * @return amount of power up multiplier
     */
    @Override
    public long getMultiplier() {
        return multiplier * power.getMultiplier();
    }
}