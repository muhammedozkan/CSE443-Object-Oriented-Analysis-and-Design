package OOADHW2.CharacterFactory;

/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This structure defines an interface for character styles.
 */
public interface IStyle {
    /**
     * Returns the strength of the character style.
     *
     * @return strength
     */
    double getStyleStrength();

    /**
     * Returns the agility of the character style.
     *
     * @return agility
     */
    double getStyleAgility();

    /**
     * Returns the health of the character style.
     *
     * @return health
     */
    double getStyleHealth();

    /**
     * Returns the character Style.
     *
     * @return character enum Style
     */
    Style getStyle();
}
