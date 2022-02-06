package OOADHW1;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Semaphore;

/**
 * All game objects are created in this class
 * and it is executed with the help of Threads.
 */
public class Game extends JPanel implements Runnable, KeyListener {


    private final Character character;
    private final CollisionController collisionController;
    private final Semaphore mutex;
    private Thread thread;
    private double fps;
    private BufferedImage background;
    private boolean isSpacePressed, isDPressed;


    /**
     * Creates a new <code>JPanel</code> with a double buffer
     * and a flow layout.
     */
    public Game(Semaphore mutex) {
        addKeyListener(this);
        character = new Character();
        collisionController = new CollisionController(character);

        ClassLoader cl = this.getClass().getClassLoader();

        try {
            background = ImageIO.read(cl.getResource("images/back.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {

                requestFocus();// while key listening to undo if focus is lost
            }
        });

        this.mutex = mutex;

    }

    /**
     * Getter thread
     *
     * @return
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Setter thread
     *
     * @param thread
     */
    public void setThread(Thread thread) {
        this.thread = thread;
    }

    /**
     * This method start game
     */
    public void startGame() {
        if (!thread.isAlive())
            thread.start();

        resetGame();
    }

    /**
     * Each time this method is called, it is responsible for
     * updating all objects and checking for collisions.
     */
    public void gameUpdate() {
        if (character.isAlive()) {

            if (isDPressed && isSpacePressed) {
                character.right();
                character.jump();
            } else if (isDPressed) {
                character.right();
            } else if (isSpacePressed) {
                character.jump();
            }

            character.update();
            collisionController.update();
            collisionController.isCollision();

            if (character.getLife() == 0) {
                character.dead(true);
            }
        } else {
            MainWindow.startButton.setEnabled(true);
        }
    }

    /**
     * This method draws the scene of the given graphic object.
     *
     * @param g graphics object to draw
     */
    public void paint(Graphics g) {

        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        Font font = new Font("Georgia", Font.PLAIN, 12);
        g.setFont(font);
        g.drawString("SCORE " + character.getScore(), 475, 15);
        g.drawString("LIFE " + character.getLife(), 250, 15);
        g.drawString("FPS " + (int) getFps(), 0, 15);
        g.drawImage(background, 0, 20, null);

        collisionController.draw(g);
        character.draw(g);

        if (!character.isAlive()) { //game over section
            g.setColor(Color.RED);
            font = new Font("Georgia", Font.PLAIN, 120);
            g.setFont(font);
            g.drawString("GAME", 110, 180);
            g.drawString("OVER", 130, 280);
        }


    }

    /**
     * This method resets all variables to initial value
     */
    private void resetGame() {
        collisionController.reset();
        character.reset();
        MainWindow.textArea.setText("Start Game\n");

    }

    /**
     * Getter FPS value
     *
     * @return fps value
     */
    public double getFps() {
        return fps;
    }

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (thread.isAlive())
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    isSpacePressed = true;
                    break;
                case KeyEvent.VK_D:
                    isDPressed = true;
                    break;
            }

    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link KeyEvent} for a definition of
     * a key released event.
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                isSpacePressed = false;
                break;
            case KeyEvent.VK_D:
                isDPressed = false;
                break;
        }
    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        while (true) {
            long lastTime = System.nanoTime();

            gameUpdate();
            repaint();

            try {
                mutex.acquire(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            fps = 1000000000.0 / (System.nanoTime() - lastTime); //one second divided by amount of time
        }
    }
}
