package OOADHW3.Q3;

import java.util.concurrent.Semaphore;

/**
 * This class is used for DFT calculation of a
 * 2-dimensional complex number array using threads.
 * It uses Mutex structures for synchronization.
 * <p>
 * The following sites were used for the algorithm.
 * https://github.com/TTrojanovich/2D_Discrete_Fourier
 * https://www.nayuki.io/page/how-to-implement-the-discrete-fourier-transform
 */
public class DFTMatrixMutexSync implements Runnable {

    private final Complex[][] _mat1;
    private final Complex[][] _mat2;
    private final int _x0;
    private final int _y0;
    private final int _x1;
    private final int _y1;
    private final int _N;
    private final Semaphore _sync;
    private final int _threadSize;

    /**
     * Constructor Method
     *
     * @param mat1       matrix 1
     * @param mat2       matrix 2
     * @param x0         row starting point
     * @param y0         col starting point
     * @param x1         row ending point
     * @param y1         col ending point
     * @param N          number of elements of the array
     * @param sync       object to be used for synchronization
     * @param threadSize thread size
     */
    public DFTMatrixMutexSync(Complex[][] mat1, Complex[][] mat2, int x0, int y0, int x1, int y1, int N, Semaphore sync, int threadSize) {
        this._mat1 = mat1;
        this._mat2 = mat2;
        this._x0 = x0;
        this._y0 = y0;
        this._x1 = x1;
        this._y1 = y1;
        this._N = N;
        this._sync = sync;
        this._threadSize = threadSize;
    }

    /**
     * Performs the sum operation of  1D given array at given intervals
     */
    public void sum() {
        for (int i = _x0; i < _x1; i++) {
            for (int j = _y0; j < _y1; j++) {
                _mat1[i][j].sum(_mat2[i][j]);
                //for DFT calc result
                _mat2[i][j].setReal(0);
                _mat2[i][j].setImag(0);
            }
        }
    }

    /**
     * Performs DFT computation of 1D given array at given intervals
     *
     * @param in  input Complex Number array
     * @param out output Complex Number array
     */
    public void computeDFT1D(Complex[] in, Complex[] out) {

        for (int k = _x0; k < _x1; k++) {  // For each output element
            Complex sum = new Complex(0.0, 0.0);
            for (int t = _y0; t < _y1; t++) {  // For each input element
                double angle = 2 * Math.PI * t * k / _N;
                sum.sum(new Complex(in[t - _y0].getReal() * Math.cos(angle) + in[t - _y0].getImag() * Math.sin(angle),
                        -in[t - _y0].getReal() * Math.sin(angle) + in[t - _y0].getImag() * Math.cos(angle)));
            }
            out[k - _x0].setReal(sum.getReal());
            out[k - _x0].setImag(sum.getImag());

        }
    }

    /**
     * Performs DFT computation of 2D given array at given intervals
     */
    public void computeDFT2D() {
        int n = _x1 - _x0;
        // process the rows
        for (int i = _x0; i < _x1; i++) {
            computeDFT1D(_mat1[i], _mat2[i]);
        }

        // process the columns
        Complex[] ca = new Complex[n];
        Complex[] cb = new Complex[n];

        for (int i = 0; i < n; i++) {
            ca[i] = new Complex(0, 0);
            cb[i] = new Complex(0, 0);
        }


        for (int i = _x0; i < _x1; i++) {
            for (int j = _y0; j < _y1; j++) {
                // extract column
                ca[j - _y0].setReal(_mat2[j][i].getReal());
                ca[j - _y0].setImag(_mat2[j][i].getImag());
            }

            // perform 1D DFT on this column
            computeDFT1D(ca, cb);

            for (int j = _y0; j < _y1; j++) {
                // store result back in the array
                _mat2[j][i].setReal(cb[j - _y0].getReal());
                _mat2[j][i].setImag(cb[j - _y0].getImag());
            }
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

        sum();

        _sync.release();

        synchronized (_sync) {
            while (_sync.availablePermits() < _threadSize) {
                try {
                    _sync.wait(); // (Releases lock, and reacquires on wakeup)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            _sync.notifyAll();
        }


        computeDFT2D();


        _sync.release();
        synchronized (_sync) {
            _sync.notifyAll();
        }

    }
}
