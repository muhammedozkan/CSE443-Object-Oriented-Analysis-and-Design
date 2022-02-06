package OOADHW2;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import java.awt.*;

/**
 * This structure serves to keep the coordinate points of movements on the board in pairs.
 */
public class Pair {
    Point first;
    Point second;

    /**
     * Construct get first and second Point
     *
     * @param first  Point
     * @param second Point
     */
    public Pair(Point first, Point second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Getter first point
     *
     * @return Point
     */
    public Point getFirst() {
        return first;
    }

    /**
     * Setter first point
     *
     * @param first Point
     */
    public void setFirst(Point first) {
        this.first = first;
    }

    /**
     * Getter second point
     *
     * @return Point
     */
    public Point getSecond() {
        return second;
    }

    /**
     * Setter second point
     *
     * @param second Point
     */
    public void setSecond(Point second) {
        this.second = second;
    }
}
