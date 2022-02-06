package OOADHW1;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;


public class MainWindow extends JFrame {

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 600;
    public static JTextArea textArea;
    public static JButton startButton;
    private final Game screen;
    private final JScrollPane jScrollPane;
    private final JButton pauseButton;
    private final JButton exitButton;
    private final Semaphore mutex;
    private final Reminder timer;
    private boolean isPause = false;

    public MainWindow() {
        super("Ucan Tekme A.S.");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocation(200, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        textArea = new JTextArea(10, 40);
        textArea.setEditable(false);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        startButton = new JButton("Start");
        startButton.setBounds(MainWindow.SCREEN_WIDTH - 103, 395, 80, 30);

        pauseButton = new JButton("Pause");
        pauseButton.setBounds(MainWindow.SCREEN_WIDTH - 103, 445, 80, 30);
        pauseButton.setEnabled(false);

        exitButton = new JButton("Exit");
        exitButton.setBounds(MainWindow.SCREEN_WIDTH - 103, 495, 80, 30);


        jScrollPane = new JScrollPane(textArea);
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setBounds(15, 375, 475, 170);

        add(jScrollPane);
        add(startButton);
        add(pauseButton);
        add(exitButton);

        textArea.append("About the game\n" +
                "Other than the round candies, anything else coming towards the character\n" +
                "is the enemy. In the event of a collision, it takes our lives.\n" +
                "Candies give us some power-ups.\n" +
                "The game is played with SPACE and D keys.\n" +
                "Hold down the D key to move right.\n" +
                "Press START to start the game.\n" +
                "Press PAUSE to pause and continue.\n" +
                "Press EXIT to exit.");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                screen.startGame();
                startButton.setEnabled(false);
                pauseButton.setEnabled(true);
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (isPause) {
                    textArea.append("Resume Game\n");
                    screen.getThread().resume();
                    isPause = false;
                } else {
                    textArea.append("Pause Game\n");
                    screen.getThread().suspend();
                    isPause = true;
                }
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        mutex = new Semaphore(1);
        timer = new Reminder(10, mutex);//10ms

        screen = new Game(mutex);
        screen.setBounds(0, 0, SCREEN_WIDTH, 350);
        screen.setFocusable(true);
        screen.requestFocusInWindow();

        addKeyListener(screen);
        add(screen);


        screen.setThread(new Thread(screen));
    }


    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.showWindow();
    }

    public void showWindow() {
        setVisible(true);
    }
}
