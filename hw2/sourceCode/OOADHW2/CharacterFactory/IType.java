package OOADHW2.CharacterFactory;

/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This structure defines an interface for character types.
 */
public interface IType {
    /**
     * Returns the strength of the character type.
     *
     * @return strength
     */
    double getTypeStrength();

    /**
     * Returns the agility of the character type.
     *
     * @return agility
     */
    double getTypeAgility();

    /**
     * Returns the health of the character type.
     *
     * @return health
     */
    double getTypeHealth();

    /**
     * Returns the character type.
     *
     * @return character enum type
     */
    Type getType();
}
