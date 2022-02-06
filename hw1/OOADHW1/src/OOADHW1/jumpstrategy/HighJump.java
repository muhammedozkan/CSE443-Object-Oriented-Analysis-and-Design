package OOADHW1.jumpstrategy;

/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This class is a concrete class that implements
 * the corresponding class for the high jump operation.
 */
public class HighJump implements JumpStrategy {

    /**
     * Jump Speed
     */
    private final double speed = -6.0;

    /**
     * Jump Message
     */
    private final String message = "High";

    /**
     * This method returns the jump amount from type double.
     *
     * @return jump amount
     */
    @Override
    public double doJump() {
        return speed;
    }

    /**
     * This method returns the message of the jump operation from type string.
     *
     * @return message of the jump operation
     */
    @Override
    public String getMessage() {
        return message;
    }

}
