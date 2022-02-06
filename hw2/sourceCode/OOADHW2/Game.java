package OOADHW2;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import OOADHW2.CharacterFactory.*;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.*;

/**
 * This class is the part where the game algorithm is run.
 * Some of the methods of this class use some pieces
 * of code from https://github.com/sriniketh923/Candy-Crush.
 */
public class Game {
    private Color[][] board;
    private ArrayList<Color> colorName;
    private Random rand;
    private GameScreen controlGameScreen;
    private Point selectedFirst;
    private Point selectedSecond;
    private int player;//0 user 1 computer

    /**
     * Constructor method that takes the size of the game board
     *
     * @param rows row size
     * @param cols col size
     */
    public Game(int rows, int cols) {
        player = 0;

        do {
            board = new Color[rows][cols];
            rand = new Random();
            colorName = new ArrayList<Color>();

            colorName.add(Color.RED);
            colorName.add(Color.GREEN);
            colorName.add(Color.BLUE);
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    board[r][c] = colorName.get(rand.nextInt(colorName.size()));
                }
            }
        } while ((matched().size() > 0) && legalMove(board) == true);

        findpossibleMove(board);
        clearSelections();
    }

    /**
     * The method from which the method to be called for the update operation is taken when there is a change in this structure.
     *
     * @param gameScreen update function
     */
    public void addControl(GameScreen gameScreen) {
        controlGameScreen = gameScreen;
        controlGameScreen.update();
    }

    /**
     * The method that is called when a tile is selected on the board.
     *
     * @param p coordinate x and y
     */
    public void select(Point p) {
        if (selectedFirst == null) {
            selectedFirst = p;
        } else if (selectedFirst.equals(p)) {
            clearSelections();
        } else {
            selectedSecond = p;
            if (isClose(selectedFirst, selectedSecond)) {

                swap(selectedFirst, selectedSecond);
                if (!(matched().size() > 0)) {
                    swap(selectedFirst, selectedSecond);
                    exchangeExtension();
                } else {
                    player = 0;
                    GameScreen.messageTxt.append("\n");
                    GameScreen.messageTxt.append("User Play X" + selectedFirst.x + " Y" + selectedFirst.y + " to X" + selectedSecond.x + " Y" + selectedSecond.y + "\n");

                    exchangeExtension();
                    clearSelections();

                    GameScreen.messageTxt.append("E1 " + GameScreen.enemy1.toString() + "\n");
                    GameScreen.messageTxt.append("E2 " + GameScreen.enemy2.toString() + "\n");
                    GameScreen.messageTxt.append("E3 " + GameScreen.enemy3.toString() + "\n");

                    if (GameScreen.enemy1.getCharacterHealth() == 0 && GameScreen.enemy2.getCharacterHealth() == 0 && GameScreen.enemy3.getCharacterHealth() == 0) {

                        GameScreen.char1 = getRandomCharacter();
                        GameScreen.char2 = getRandomCharacter();
                        GameScreen.char3 = getRandomCharacter();
                        GameScreen.enemy1 = getRandomCharacter();
                        GameScreen.enemy2 = getRandomCharacter();
                        GameScreen.enemy3 = getRandomCharacter();

                        GameScreen.lblchar1.setIcon(new ImageIcon(GameScreen.char1.getImage()));
                        GameScreen.lblchar2.setIcon(new ImageIcon(GameScreen.char2.getImage()));
                        GameScreen.lblchar3.setIcon(new ImageIcon(GameScreen.char3.getImage()));
                        GameScreen.lblenemy1.setIcon(new ImageIcon(GameScreen.enemy1.getImage()));
                        GameScreen.lblenemy2.setIcon(new ImageIcon(GameScreen.enemy2.getImage()));
                        GameScreen.lblenemy3.setIcon(new ImageIcon(GameScreen.enemy3.getImage()));

                        GameScreen.messageTxt.append("\n");
                        GameScreen.messageTxt.append("All computers characters are dead. Replenished all character.\n");
                        GameScreen.messageTxt.append("C1 " + GameScreen.char1.toString() + "\n");
                        GameScreen.messageTxt.append("C2 " + GameScreen.char2.toString() + "\n");
                        GameScreen.messageTxt.append("C3 " + GameScreen.char3.toString() + "\n");
                        GameScreen.messageTxt.append("E1 " + GameScreen.enemy1.toString() + "\n");
                        GameScreen.messageTxt.append("E2 " + GameScreen.enemy2.toString() + "\n");
                        GameScreen.messageTxt.append("E3 " + GameScreen.enemy3.toString() + "\n");

                    } else {
                        playComputer();
                    }
                }
            }
            clearSelections();
        }
    }

    /**
     * Getter method that returns the first selected point.
     *
     * @return first selected point
     */
    public Point selectedFirst() {
        return selectedFirst;
    }

    /**
     * Method that checks if the dots are next to each other.
     *
     * @param p first point
     * @param q second point
     * @return true if close, false
     */
    private boolean isClose(Point p, Point q) {
        return Math.abs(p.x - q.x) + Math.abs(p.y - q.y) == 1;
    }

    /**
     * Method that clears selected points.
     */
    private void clearSelections() {
        selectedFirst = null;
        selectedSecond = null;
    }

    /**
     * The point-selecting method also refreshes the screen.
     *
     * @param r x value
     * @param c y value
     */
    public void select(int r, int c) {
        select(new Point(r, c));
        controlGameScreen.update();
    }

    /**
     * When the turn of the game is switched to the computer,
     * the working method determines the move to be made and makes the move.
     */
    public void playComputer() {
        player = 1;
        List<Pair> possibleMove = findpossibleMove(board);
        GameScreen.messageTxt.append("\n");
        GameScreen.messageTxt.append("Computer Play X" + possibleMove.get(possibleMove.size() - 1).first.x + " Y" + possibleMove.get(possibleMove.size() - 1).first.y + " to X" + possibleMove.get(possibleMove.size() - 1).second.x + " Y" + possibleMove.get(possibleMove.size() - 1).second.y + "\n");

        selectedFirst = possibleMove.get(possibleMove.size() - 1).first;
        selectedSecond = possibleMove.get(possibleMove.size() - 1).second;
        if (isClose(selectedFirst, selectedSecond)) {
            swap(selectedFirst, selectedSecond);
            if (!(matched().size() > 0)) {
                swap(selectedFirst, selectedSecond);
            }
            exchangeExtension();
        }
        clearSelections();

        GameScreen.messageTxt.append("C1 " + GameScreen.char1.toString() + "\n");
        GameScreen.messageTxt.append("C2 " + GameScreen.char2.toString() + "\n");
        GameScreen.messageTxt.append("C3 " + GameScreen.char3.toString() + "\n");

        if (GameScreen.char1.getCharacterHealth() == 0 && GameScreen.char2.getCharacterHealth() == 0 && GameScreen.char3.getCharacterHealth() == 0) {

            for (int r = 0; r < rows(); r++) {
                for (int c = 0; c < cols(); c++) {
                    GameScreen.viewBoard.get(r).get(c).setEnabled(false);
                }
            }
            GameScreen.menuStart.setEnabled(true);
            GameScreen.messageTxt.append("\n");
            GameScreen.messageTxt.append("GAME OVER!!! All user characters are dead. The computer WINS.\n");
            GameScreen.start = true;
            GameScreen.messageTxt.append("Please Start Game\n");
        }
    }

    /**
     * Getter rows size
     *
     * @return
     */
    public int rows() {
        return board.length;
    }

    /**
     * Getter cols size
     *
     * @return
     */
    public int cols() {
        return board[0].length;
    }

    /**
     * Getter color of the point
     *
     * @param p point
     * @return color of the point
     */
    public Color get(Point p) {
        return board[p.x][p.y];
    }

    /**
     * Setter color of the point
     *
     * @param p point
     * @param c color
     * @return color of the point
     */
    private Color set(Point p, Color c) {
        return board[p.x][p.y] = c;
    }

    /**
     * Swap operation point to point
     *
     * @param p first point
     * @param q second point
     */
    public void swap(Point p, Point q) {
        Color temp = get(p);
        set(p, get(q));
        set(q, temp);

    }

    /**
     * A method that checks for matches on the board and performs operations, if any.
     */
    public void exchangeExtension() {
        while (matched().size() > 0) {
            if (player == 0) {
                GameScreen.messageTxt.append("Player Matches or Cascaded Matches  \n");
            } else {
                GameScreen.messageTxt.append("Computer Matches or Cascaded Matches\n");
            }
            for (Point p : matched()) {
                GameScreen.messageTxt.append("X" + p.x + " Y" + p.y + " ");
                if ((p.y / 3) == 0) {
                    if (get(p).getRed() == 255) {
                        if (player == 0) {
                            GameScreen.enemy1.setCharacterHealth(GameScreen.enemy1.getCharacterHealth() - calculateDamage(GameScreen.char1, GameScreen.enemy1, Type.Red));
                            GameScreen.messageTxt.append(" E1 H" + new DecimalFormat("##.##").format(GameScreen.enemy1.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char1.setCharacterHealth(GameScreen.char1.getCharacterHealth() - calculateDamage(GameScreen.enemy1, GameScreen.char1, Type.Red));
                            GameScreen.messageTxt.append(" C1 H" + new DecimalFormat("##.##").format(GameScreen.char1.getCharacterHealth()) + "\n");
                        }
                    } else if (get(p).getGreen() == 255) {
                        if (player == 0) {
                            GameScreen.enemy1.setCharacterHealth(GameScreen.enemy1.getCharacterHealth() - calculateDamage(GameScreen.char1, GameScreen.enemy1, Type.Green));
                            GameScreen.messageTxt.append(" E1 H" + new DecimalFormat("##.##").format(GameScreen.enemy1.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char1.setCharacterHealth(GameScreen.char1.getCharacterHealth() - calculateDamage(GameScreen.enemy1, GameScreen.char1, Type.Green));
                            GameScreen.messageTxt.append(" C1 H" + new DecimalFormat("##.##").format(GameScreen.char1.getCharacterHealth()) + "\n");
                        }
                    } else if (get(p).getBlue() == 255) {
                        if (player == 0) {
                            GameScreen.enemy1.setCharacterHealth(GameScreen.enemy1.getCharacterHealth() - calculateDamage(GameScreen.char1, GameScreen.enemy1, Type.Blue));
                            GameScreen.messageTxt.append(" E1 H" + new DecimalFormat("##.##").format(GameScreen.enemy1.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char1.setCharacterHealth(GameScreen.char1.getCharacterHealth() - calculateDamage(GameScreen.enemy1, GameScreen.char1, Type.Blue));
                            GameScreen.messageTxt.append(" C1 H" + new DecimalFormat("##.##").format(GameScreen.char1.getCharacterHealth()) + "\n");
                        }
                    }
                } else if ((p.y / 3) == 1) {
                    if (get(p).getRed() == 255) {
                        if (player == 0) {
                            GameScreen.enemy2.setCharacterHealth(GameScreen.enemy2.getCharacterHealth() - calculateDamage(GameScreen.char2, GameScreen.enemy2, Type.Red));
                            GameScreen.messageTxt.append(" E2 H" + new DecimalFormat("##.##").format(GameScreen.enemy2.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char2.setCharacterHealth(GameScreen.char2.getCharacterHealth() - calculateDamage(GameScreen.enemy2, GameScreen.char2, Type.Red));
                            GameScreen.messageTxt.append(" C2 H" + new DecimalFormat("##.##").format(GameScreen.char2.getCharacterHealth()) + "\n");
                        }
                    } else if (get(p).getGreen() == 255) {
                        if (player == 0) {
                            GameScreen.enemy2.setCharacterHealth(GameScreen.enemy2.getCharacterHealth() - calculateDamage(GameScreen.char2, GameScreen.enemy2, Type.Green));
                            GameScreen.messageTxt.append(" E2 H" + new DecimalFormat("##.##").format(GameScreen.enemy2.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char2.setCharacterHealth(GameScreen.char2.getCharacterHealth() - calculateDamage(GameScreen.enemy2, GameScreen.char2, Type.Green));
                            GameScreen.messageTxt.append(" C2 H" + new DecimalFormat("##.##").format(GameScreen.char2.getCharacterHealth()) + "\n");
                        }
                    } else if (get(p).getBlue() == 255) {
                        if (player == 0) {
                            GameScreen.enemy2.setCharacterHealth(GameScreen.enemy2.getCharacterHealth() - calculateDamage(GameScreen.char2, GameScreen.enemy2, Type.Blue));
                            GameScreen.messageTxt.append(" E2 H" + new DecimalFormat("##.##").format(GameScreen.enemy2.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char2.setCharacterHealth(GameScreen.char2.getCharacterHealth() - calculateDamage(GameScreen.enemy2, GameScreen.char2, Type.Blue));
                            GameScreen.messageTxt.append(" C2 H" + new DecimalFormat("##.##").format(GameScreen.char2.getCharacterHealth()) + "\n");
                        }
                    }
                } else if ((p.y / 3) == 2) {
                    if (get(p).getRed() == 255) {
                        if (player == 0) {
                            GameScreen.enemy3.setCharacterHealth(GameScreen.enemy3.getCharacterHealth() - calculateDamage(GameScreen.char3, GameScreen.enemy3, Type.Red));
                            GameScreen.messageTxt.append(" E3 H" + new DecimalFormat("##.##").format(GameScreen.enemy3.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char3.setCharacterHealth(GameScreen.char3.getCharacterHealth() - calculateDamage(GameScreen.enemy3, GameScreen.char3, Type.Red));
                            GameScreen.messageTxt.append(" C3 H" + new DecimalFormat("##.##").format(GameScreen.char3.getCharacterHealth()) + "\n");
                        }
                    } else if (get(p).getGreen() == 255) {
                        if (player == 0) {
                            GameScreen.enemy3.setCharacterHealth(GameScreen.enemy3.getCharacterHealth() - calculateDamage(GameScreen.char3, GameScreen.enemy3, Type.Green));
                            GameScreen.messageTxt.append(" E3 H" + new DecimalFormat("##.##").format(GameScreen.enemy3.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char3.setCharacterHealth(GameScreen.char3.getCharacterHealth() - calculateDamage(GameScreen.enemy3, GameScreen.char3, Type.Green));
                            GameScreen.messageTxt.append(" C3 H" + new DecimalFormat("##.##").format(GameScreen.char3.getCharacterHealth()) + "\n");
                        }
                    } else if (get(p).getBlue() == 255) {
                        if (player == 0) {
                            GameScreen.enemy3.setCharacterHealth(GameScreen.enemy3.getCharacterHealth() - calculateDamage(GameScreen.char3, GameScreen.enemy3, Type.Blue));
                            GameScreen.messageTxt.append(" E3 H" + new DecimalFormat("##.##").format(GameScreen.enemy3.getCharacterHealth()) + "\n");
                        } else {
                            GameScreen.char3.setCharacterHealth(GameScreen.char3.getCharacterHealth() - calculateDamage(GameScreen.enemy3, GameScreen.char3, Type.Blue));
                            GameScreen.messageTxt.append(" C3 H" + new DecimalFormat("##.##").format(GameScreen.char3.getCharacterHealth()) + "\n");
                        }
                    }
                }
            }
            removeTiles();
        }
    }

    /**
     * The method that removes and replaces these tiles with other tiles if there is a match.
     */
    public void removeTiles() {
        for (Point p : matched()) {
            Point p1 = new Point(p.x, p.y);
            Point p2 = new Point();
            set(p, null);
            while (p1.x + 1 != rows()) {
                p2.setLocation(p1.x + 1, p1.y);
                Color temp = get(p1);
                set(p1, get(p2));
                set(p2, temp);
                p1 = new Point(p1.x + 1, p1.y);
            }
            newTile(p1);
        }
    }

    /**
     * Random new tile method
     *
     * @param p point
     */
    public void newTile(Point p) {
        set(p, colorName.get(rand.nextInt(colorName.size())));
    }

    /**
     * Method that checks horizontal and vertical match
     *
     * @return set of point
     */
    public Set<Point> matched() {
        Set<Point> matches = verticalMatch();
        matches.addAll(horizontalMatch());
        return matches;
    }

    /**
     * The method of finding the horizontal and vertical movements to be made by the computer.
     *
     * @param board board
     * @return list of pair
     */
    public List<Pair> findpossibleMove(Color[][] board) {
        List<Pair> possibleMove = new ArrayList<Pair>();
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols() - 3; col++) {
                Point a = new Point(row, col);
                Point b = new Point(row, col + 1);
                Point c = new Point(row, col + 2);
                Point d = new Point(row, col + 3);

                if (matches(c, d, board) && matches(a, d, board)) {
                    possibleMove.add(new Pair(a, b));
                }
                if (matches(a, b, board) && matches(a, d, board)) {
                    possibleMove.add(new Pair(c, d));
                }
            }
        }

        for (int col = 0; col < cols(); col++) {
            for (int row = 0; row < rows() - 3; row++) {

                Point a = new Point(row, col);
                Point b = new Point(row + 1, col);
                Point c = new Point(row + 2, col);
                Point d = new Point(row + 3, col);

                if (matches(c, d, board) && matches(a, d, board)) {
                    possibleMove.add(new Pair(a, b));
                }

                if (matches(a, b, board) && matches(a, d, board)) {
                    possibleMove.add(new Pair(c, d));
                }
            }
        }
        return possibleMove;
    }

    /**
     * Method that checks whether there is movement that can be made on the board.
     *
     * @param board board
     * @return True if there is movement, false if there is no movement
     */
    private boolean legalMove(Color[][] board) {
        for (int row = 0; row < rows(); row++) {
            for (int col = 0; col < cols(); col++) {
                Point a = new Point(row - 1, col);
                Point b = new Point(row + 1, col);
                Point c = new Point(row, col - 1);
                Point d = new Point(row, col + 1);
                Point e = new Point(row + 1, col + 1);
                Point f = new Point(row - 1, col + 1);
                Point g = new Point(row + 1, col - 1);
                Point h = new Point(row - 1, col - 1);
                Point i = new Point(row + 2, col);
                Point j = new Point(row, col + 2);
                Point k = new Point(row - 2, col);
                Point l = new Point(row, col - 2);

                if (isBounds(a, board) && isBounds(b, board) && isBounds(c, board) && matches(a, b, board) && matches(a, c, board)) {
                    return true;
                }
                if (isBounds(a, board) && isBounds(b, board) && isBounds(d, board) && matches(a, b, board) && matches(a, d, board)) {
                    return true;
                }
                if (isBounds(c, board) && isBounds(b, board) && isBounds(d, board) && matches(c, b, board) && matches(c, d, board)) {
                    return true;
                }
                if (isBounds(c, board) && isBounds(a, board) && isBounds(d, board) && matches(c, a, board) && matches(c, d, board)) {
                    return true;
                }
                if (isBounds(a, board) && isBounds(c, board) && isBounds(g, board) && matches(a, c, board) && matches(a, g, board)) {
                    return true;
                }
                if (isBounds(a, board) && isBounds(d, board) && isBounds(e, board) && matches(a, d, board) && matches(a, e, board)) {
                    return true;
                }
                if (isBounds(c, board) && isBounds(a, board) && isBounds(f, board) && matches(c, a, board) && matches(c, f, board)) {
                    return true;
                }
                if (isBounds(c, board) && isBounds(b, board) && isBounds(e, board) && matches(c, b, board) && matches(c, e, board)) {
                    return true;
                }
                if (isBounds(b, board) && isBounds(d, board) && isBounds(f, board) && matches(b, d, board) && matches(b, f, board)) {
                    return true;
                }
                if (isBounds(b, board) && isBounds(c, board) && isBounds(h, board) && matches(b, c, board) && matches(b, h, board)) {
                    return true;
                }
                if (isBounds(d, board) && isBounds(a, board) && isBounds(h, board) && matches(d, a, board) && matches(d, h, board)) {
                    return true;
                }
                if (isBounds(d, board) && isBounds(b, board) && isBounds(g, board) && matches(d, b, board) && matches(d, g, board)) {
                    return true;
                }
                if (isBounds(a, board) && isBounds(b, board) && isBounds(i, board) && matches(a, b, board) && matches(a, i, board)) {
                    return true;
                }
                if (isBounds(a, board) && isBounds(k, board) && isBounds(b, board) && matches(a, k, board) && matches(a, b, board)) {
                    return true;
                }
                if (isBounds(j, board) && isBounds(d, board) && isBounds(c, board) && matches(j, d, board) && matches(j, c, board)) {
                    return true;
                }
                if (isBounds(d, board) && isBounds(c, board) && isBounds(l, board) && matches(d, c, board) && matches(l, d, board)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Random character created method.
     *
     * @return gamecharacter
     */
    public GameCharacter getRandomCharacter() {
        Random rand = new Random();
        int random = rand.nextInt(9);

        switch (random) {
            case 0:
                return new GameCharacter(new BlueIceAtlantis());
            case 1:
                return new GameCharacter(new BlueIceValhalla());
            case 2:
                return new GameCharacter(new BlueIceUnderwild());
            case 3:
                return new GameCharacter(new RedFireAtlantis());
            case 4:
                return new GameCharacter(new RedFireValhalla());
            case 5:
                return new GameCharacter(new RedFireUnderwild());
            case 6:
                return new GameCharacter(new GreenNatureAtlantis());
            case 7:
                return new GameCharacter(new GreenNatureValhalla());
            case 8:
                return new GameCharacter(new GreenNatureUnderwild());
            default:
                return null;
        }
    }

    /**
     * The method by which the damage was calculated.
     *
     * @param giveDamage given gamecharacter
     * @param takeDamage taken gamecharacter
     * @param tileColor  matched tile color
     * @return damage value
     */
    public double calculateDamage(GameCharacter giveDamage, GameCharacter takeDamage, Type tileColor) {
        double temp = ((100 * Math.pow((giveDamage.getCharacterStrength() / takeDamage.getCharacterAgility()), 1.35)) * getXYZrule(tileColor, takeDamage.getType()));
        GameScreen.messageTxt.append(tileColor + " tile match " + giveDamage.getType() + "" + giveDamage.getStyle() + " did " + new DecimalFormat("##.##").format(temp) + " damaged to " + takeDamage.getType() + "" + takeDamage.getStyle());
        return temp;
    }

    /**
     * The method that calculates the XYZ rule.
     *
     * @param tileColor      matched tile color
     * @param characterColor character color
     * @return multiplier value
     */
    public double getXYZrule(Type tileColor, Type characterColor) {
        if (tileColor == Type.Red) {
            if (characterColor == Type.Red) {
                return 1;
            } else if (characterColor == Type.Green) {
                return 2;
            } else if (characterColor == Type.Blue) {
                return 0.5;
            }
        } else if (tileColor == Type.Green) {
            if (characterColor == Type.Red) {
                return 0.5;
            } else if (characterColor == Type.Green) {
                return 1;
            } else if (characterColor == Type.Blue) {
                return 2;
            }
        } else if (tileColor == Type.Blue) {
            if (characterColor == Type.Red) {
                return 2;
            } else if (characterColor == Type.Green) {
                return 0.5;
            } else if (characterColor == Type.Blue) {
                return 1;
            }
        }

        return 0;
    }

    /**
     * The function find horizontal matches.
     *
     * @return set of point
     */
    private Set<Point> horizontalMatch() {
        Set<Point> matches = new HashSet<Point>();
        int minCol = 0;
        int maxCol = cols() - 3;
        for (int r = 0; r < rows(); r++) {
            for (int c = minCol; c <= maxCol; c++) {

                Set<Color> values = new HashSet<Color>();
                Set<Point> points = new HashSet<Point>();
                for (int offset = 0; offset < 3; offset++) {
                    Point p = new Point(r, c + offset);
                    points.add(p);
                    Color s = get(p);
                    values.add(s);
                }
                if (values.size() == 1) {
                    matches.addAll(points);
                }
            }
        }
        return matches;
    }

    /**
     * the function find vertical matches.
     *
     * @return set of point
     */
    private Set<Point> verticalMatch() {
        Set<Point> matches = new HashSet<Point>();
        int minRow = 0;
        int maxRow = rows() - 3;
        for (int c = 0; c < cols(); c++) {
            for (int r = minRow; r <= maxRow; r++) {

                Set<Color> values = new HashSet<Color>();
                Set<Point> points = new HashSet<Point>();
                for (int offset = 0; offset < 3; offset++) {
                    Point p = new Point(r + offset, c);
                    points.add(p);
                    Color s = get(p);
                    values.add(s);
                }
                if (values.size() == 1) {
                    matches.addAll(points);
                }
            }
        }
        return matches;
    }

    /**
     * A method that finds whether the color of two given points is the same.
     *
     * @param p     first point
     * @param q     second point
     * @param board board
     * @return same if true, false if not same.
     */
    private boolean matches(Point p, Point q, Color[][] board) {
        return board[p.x][p.y].equals(board[q.x][q.y]);
    }

    /**
     * A method that finds whether the point is inside the board.
     *
     * @param p     point
     * @param board board
     * @return it's true if it's in it, if not it's false.
     */
    private boolean isBounds(Point p, Color[][] board) {
        return p.x >= 0 && p.x < board.length && p.y >= 0 && p.y < board[0].length;
    }
}
