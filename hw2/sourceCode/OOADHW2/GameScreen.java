package OOADHW2;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import OOADHW2.CharacterFactory.GameCharacter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * This class creates the window in which the game will be played and the GUI processes.
 * It is executed with the help of a thread.
 * Some of the methods of this class use some pieces
 * of code from https://github.com/sriniketh923/Candy-Crush.
 */
public class GameScreen implements Runnable {

    public static JTextArea messageTxt;
    public static GameCharacter char1;
    public static GameCharacter char2;
    public static GameCharacter char3;
    public static GameCharacter enemy1;
    public static GameCharacter enemy2;
    public static GameCharacter enemy3;
    public static JLabel lblchar1;
    public static JLabel lblchar2;
    public static JLabel lblchar3;
    public static JLabel lblenemy1;
    public static JLabel lblenemy2;
    public static JLabel lblenemy3;
    public static ArrayList<ArrayList<JButton>> viewBoard;
    public static JMenuItem menuStart;
    public static boolean start = false;

    private final Game game;
    private JFrame jFrame;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuPause;
    private JMenuItem menuExit;
    private boolean isPause = false;

    /**
     * Game screen constructor method
     */
    public GameScreen() {

        messageTxt = new JTextArea(10, 25);
        messageTxt.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                messageTxt.setCaretPosition(messageTxt.getDocument().getLength());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        game = new Game(6, 9);
        viewBoard = new ArrayList<ArrayList<JButton>>();

    }

    /**
     * Codes to run when thread runs
     */
    @Override
    public void run() {
        jFrame = new JFrame("Az Laf, Cok Is A.S.");
        jFrame.setLocation(200, 100);
        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);
        menuStart = new JMenuItem("Start");
        menuPause = new JMenuItem("Pause");
        menuExit = new JMenuItem("Exit");
        menuStart.setEnabled(false);

        menuStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char1 = game.getRandomCharacter();
                char2 = game.getRandomCharacter();
                char3 = game.getRandomCharacter();
                enemy1 = game.getRandomCharacter();
                enemy2 = game.getRandomCharacter();
                enemy3 = game.getRandomCharacter();

                lblchar1.setIcon(new ImageIcon(char1.getImage()));
                lblchar2.setIcon(new ImageIcon(char2.getImage()));
                lblchar3.setIcon(new ImageIcon(char3.getImage()));
                lblenemy1.setIcon(new ImageIcon(enemy1.getImage()));
                lblenemy2.setIcon(new ImageIcon(enemy2.getImage()));
                lblenemy3.setIcon(new ImageIcon(enemy3.getImage()));

                messageTxt.setText("");
                messageTxt.append("Start New Game\n");
                messageTxt.append("C1 " + char1.toString() + "\n");
                messageTxt.append("C2 " + char2.toString() + "\n");
                messageTxt.append("C3 " + char3.toString() + "\n");
                messageTxt.append("E1 " + enemy1.toString() + "\n");
                messageTxt.append("E2 " + enemy2.toString() + "\n");
                messageTxt.append("E3 " + enemy3.toString() + "\n");


                for (int r = 0; r < game.rows(); r++) {
                    for (int c = 0; c < game.cols(); c++) {
                        GameScreen.viewBoard.get(r).get(c).setEnabled(true);
                    }
                }
                start = false;
                menuStart.setEnabled(false);
            }
        });

        menuPause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!start) {
                    if (isPause) {
                        messageTxt.append("Resume Game\n");
                        for (int r = 0; r < game.rows(); r++) {
                            for (int c = 0; c < game.cols(); c++) {
                                GameScreen.viewBoard.get(r).get(c).setEnabled(true);
                            }
                        }
                        isPause = false;
                    } else {
                        messageTxt.append("Pause Game\n");
                        for (int r = 0; r < game.rows(); r++) {
                            for (int c = 0; c < game.cols(); c++) {
                                GameScreen.viewBoard.get(r).get(c).setEnabled(false);
                            }
                        }
                        isPause = true;
                    }
                }
            }
        });

        menuExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        menu.add(menuStart);
        menu.add(menuPause);
        menu.add(menuExit);
        jFrame.setJMenuBar(menuBar);
        jFrame.setResizable(false);
        JPanel board = new JPanel();
        JPanel characters = new JPanel();
        JScrollPane jScrollPane;
        messageTxt.setEditable(false);
        characters.setLayout(new GridLayout(2, 3, 2, 2));
        ClassLoader cl = this.getClass().getClassLoader();

        char1 = game.getRandomCharacter();
        char2 = game.getRandomCharacter();
        char3 = game.getRandomCharacter();
        enemy1 = game.getRandomCharacter();
        enemy2 = game.getRandomCharacter();
        enemy3 = game.getRandomCharacter();

        messageTxt.append("C1 " + char1.toString() + "\n");
        messageTxt.append("C2 " + char2.toString() + "\n");
        messageTxt.append("C3 " + char3.toString() + "\n");
        messageTxt.append("E1 " + enemy1.toString() + "\n");
        messageTxt.append("E2 " + enemy2.toString() + "\n");
        messageTxt.append("E3 " + enemy3.toString() + "\n");

        lblchar1 = new JLabel(new ImageIcon(char1.getImage()));
        characters.add(lblchar1);
        lblchar2 = new JLabel(new ImageIcon(char2.getImage()));
        characters.add(lblchar2);
        lblchar3 = new JLabel(new ImageIcon(char3.getImage()));
        characters.add(lblchar3);
        lblenemy1 = new JLabel(new ImageIcon(enemy1.getImage()));
        characters.add(lblenemy1);
        lblenemy2 = new JLabel(new ImageIcon(enemy2.getImage()));
        characters.add(lblenemy2);
        lblenemy3 = new JLabel(new ImageIcon(enemy3.getImage()));
        characters.add(lblenemy3);

        board.setLayout(new GridLayout(game.rows(), game.cols(), 2, 2));

        jFrame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints d = new GridBagConstraints();
        d.fill = GridBagConstraints.HORIZONTAL;
        d.weightx = 1;
        d.insets = new Insets(5, 5, 5, 5);
        d.gridy = 1;
        jFrame.add(characters, d);
        d.gridy++;
        jFrame.add(board, d);
        d.gridy++;

        jScrollPane = new JScrollPane(messageTxt);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        jFrame.add(jScrollPane, d);
        viewBoard = new ArrayList<ArrayList<JButton>>();
        for (int r = 0; r < game.rows(); r++) {
            viewBoard.add(new ArrayList<JButton>());
            for (int c = 0; c < game.cols(); c++) {
                JButton button = new JButton();
                button.setOpaque(true);
                button.setPreferredSize((new Dimension(55, 55)));
                viewBoard.get(r).add(button);
                board.add(button);
                button.addActionListener(new EventHandler(game, r, c));
            }
        }
        game.addControl(this);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    /**
     * Codes to run when updating is required on the screen
     */
    public void update() {
        for (int r = 0; r < game.rows(); r++) {
            for (int c = 0; c < game.cols(); c++) {
                viewBoard.get(r).get(c).setBackground(game.get(new Point(r, c)));
            }
        }

        Point p = game.selectedFirst();
        if (p != null) {
            if (game.get(p) == Color.RED) {
                viewBoard.get(p.x).get(p.y).setBackground(Color.decode("#AA0000").darker());
            }
            if (game.get(p) == Color.GREEN) {
                viewBoard.get(p.x).get(p.y).setBackground(Color.decode("#00AA00").darker());
            }
            if (game.get(p) == Color.BLUE) {
                viewBoard.get(p.x).get(p.y).setBackground(Color.decode("#0000AA").darker());
            }

        }
        jFrame.repaint();
    }
}
