package OOADHW2.CharacterFactory;

/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This structure implements an interface for character types.
 */
public class RedFire implements IType {
    /**
     * Returns the strength of the character type.
     *
     * @return strength
     */
    @Override
    public double getTypeStrength() {
        return 100;
    }

    /**
     * Returns the agility of the character type.
     *
     * @return agility
     */
    @Override
    public double getTypeAgility() {
        return 125;
    }

    /**
     * Returns the health of the character type.
     *
     * @return health
     */
    @Override
    public double getTypeHealth() {
        return 75;
    }

    /**
     * Returns the character type.
     *
     * @return character enum type
     */
    @Override
    public Type getType() {
        return Type.Red;
    }
}
