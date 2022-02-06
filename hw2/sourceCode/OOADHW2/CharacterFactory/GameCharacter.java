package OOADHW2.CharacterFactory;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This structure corresponds to the client structure in the factory design pattern.
 * Keeps game characters in composition shape. Thus, loose coupling is ensured.
 */
public class GameCharacter {

    private final IStyle style;
    private final IType type;
    private final double characterStrength;
    private final double characterAgility;
    private double characterHealth;
    private BufferedImage image;

    /**
     * Game character constructor method
     *
     * @param character
     */
    public GameCharacter(ICharacterFactory character) {
        this.style = character.createStyle();
        this.type = character.createType();

        //character power calculate
        this.characterStrength = style.getStyleStrength() * type.getTypeStrength();
        this.characterAgility = style.getStyleAgility() * type.getTypeAgility();
        this.characterHealth = style.getStyleHealth() * type.getTypeHealth();


        ClassLoader cl = this.getClass().getClassLoader();

        try {
            if (character instanceof BlueIceAtlantis) {
                image = ImageIO.read(cl.getResource("images/blueatlantis.png"));
            } else if (character instanceof BlueIceUnderwild) {
                image = ImageIO.read(cl.getResource("images/blueunderwild.png"));
            } else if (character instanceof BlueIceValhalla) {
                image = ImageIO.read(cl.getResource("images/bluevalhalla.png"));
            } else if (character instanceof GreenNatureAtlantis) {
                image = ImageIO.read(cl.getResource("images/greenatlantis.png"));
            } else if (character instanceof GreenNatureUnderwild) {
                image = ImageIO.read(cl.getResource("images/greenunderwild.png"));
            } else if (character instanceof GreenNatureValhalla) {
                image = ImageIO.read(cl.getResource("images/greenvalhalla.png"));
            } else if (character instanceof RedFireAtlantis) {
                image = ImageIO.read(cl.getResource("images/redatlantis.png"));
            } else if (character instanceof RedFireUnderwild) {
                image = ImageIO.read(cl.getResource("images/redunderwild.png"));
            } else if (character instanceof RedFireValhalla) {
                image = ImageIO.read(cl.getResource("images/redvalhalla.png"));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter character image
     *
     * @return character image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Getter character information as string.
     *
     * @return character information
     */
    @Override
    public String toString() {
        return type.getType() + "" + style.getStyle() +
                ",S " + characterStrength +
                ",A " + characterAgility +
                ",H " + characterHealth;
    }

    /**
     * Getter character style
     *
     * @return character style
     */
    public Style getStyle() {
        return style.getStyle();
    }

    /**
     * Getter character type
     *
     * @return character type
     */
    public Type getType() {
        return type.getType();
    }

    /**
     * Getter character strength
     *
     * @return character strength
     */
    public double getCharacterStrength() {
        return characterStrength;
    }

    /**
     * Getter character agility
     *
     * @return character agility
     */
    public double getCharacterAgility() {
        return characterAgility;
    }

    /**
     * Getter character health.
     *
     * @return character health
     */
    public double getCharacterHealth() {
        return characterHealth;
    }

    /**
     * Setter character health. If health is below 0, it is considered 0.
     *
     * @param characterHealth new character health
     */
    public void setCharacterHealth(double characterHealth) {
        if (characterHealth <= 0)
            this.characterHealth = 0;
        else
            this.characterHealth = characterHealth;
    }
}
