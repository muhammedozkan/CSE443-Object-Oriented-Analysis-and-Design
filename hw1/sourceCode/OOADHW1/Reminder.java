package OOADHW1;
/**
 * @author Muhammed ÖZKAN <muhammedozkan@windowslive.com>
 */

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

/**
 * This class releases the given semaphore with
 * a timer that repeats for the given period.
 */
public class Reminder {

    Timer timer;
    Semaphore mutex;

    /**
     * This constructor sets a timer with the given
     * time to release the given semaphore
     *
     * @param period repeat time in milliseconds
     * @param mutex  semaphore to be allowed on each iteration
     */
    public Reminder(long period, Semaphore mutex) {
        this.mutex = mutex;

        timer = new Timer();
        timer.schedule(new ReminderTask(), 0, period);
    }

    class ReminderTask extends TimerTask {
        /**
         * The action to be performed by this ReminderTask.
         */
        public void run() {
            if (mutex.availablePermits() == 0)
                mutex.release();
        }
    }
}