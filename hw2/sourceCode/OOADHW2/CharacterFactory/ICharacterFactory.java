package OOADHW2.CharacterFactory;

/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

/**
 * This structure defines the interface with a factory design pattern to create characters.
 */
public interface ICharacterFactory {
    /**
     * Creates a character in the desired style.
     *
     * @return character style
     */
    IStyle createStyle();

    /**
     * Creates a character in the desired type.
     *
     * @return character type
     */
    IType createType();
}
