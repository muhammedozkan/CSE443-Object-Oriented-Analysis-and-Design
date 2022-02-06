package OOADHW2;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This event handler handles the clicks of the buttons on the board.
 */
public class EventHandler implements ActionListener {

    private final Game board;
    private final int row;
    private final int col;

    /**
     * Constructor to which events are attached.
     *
     * @param board
     * @param row
     * @param col
     */
    public EventHandler(Game board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
    }

    /**
     * The function to run when the event occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        board.select(row, col);
    }

}
