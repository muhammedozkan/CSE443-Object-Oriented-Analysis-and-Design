package OOADHW1.jumpstrategy;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This interface is a contract for jumping operation.
 */
public interface JumpStrategy {
    /**
     * This method returns the jump amount from type double.
     *
     * @return jump amount
     */
    double doJump();

    /**
     * This method returns the message of the jump operation from type string.
     *
     * @return message of the jump operation
     */
    String getMessage();
}
