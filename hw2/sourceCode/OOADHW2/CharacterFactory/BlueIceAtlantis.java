package OOADHW2.CharacterFactory;

/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This structure implements the interface with a factory design pattern to create the characters.
 */
public class BlueIceAtlantis implements ICharacterFactory {
    /**
     * Creates a character in the desired style.
     *
     * @return character style
     */
    @Override
    public IStyle createStyle() {
        return new Atlantis();
    }

    /**
     * Creates a character in the desired type.
     *
     * @return character type
     */
    @Override
    public IType createType() {
        return new BlueIce();
    }
}
