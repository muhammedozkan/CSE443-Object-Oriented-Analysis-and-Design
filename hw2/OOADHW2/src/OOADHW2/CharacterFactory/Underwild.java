package OOADHW2.CharacterFactory;

/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This structure implements an interface for character styles.
 */
public class Underwild implements IStyle {
    /**
     * Returns the strength of the character style.
     *
     * @return strength
     */
    @Override
    public double getStyleStrength() {
        return 0.8;
    }

    /**
     * Returns the agility of the character style.
     *
     * @return agility
     */
    @Override
    public double getStyleAgility() {
        return 1.6;
    }

    /**
     * Returns the health of the character style.
     *
     * @return health
     */
    @Override
    public double getStyleHealth() {
        return 0.8;
    }

    /**
     * Returns the character Style.
     *
     * @return character enum Style
     */
    @Override
    public Style getStyle() {
        return Style.Underwild;
    }
}
