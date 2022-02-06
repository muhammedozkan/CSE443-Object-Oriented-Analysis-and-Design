package OOADHW3.Q3;

import java.util.Random;

/**
 * This class represent Complex Number
 */
public class Complex {
    private double real;
    private double imag;

    /**
     * Constructor Method
     *
     * @param real real value
     * @param imag imaginer value
     */
    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }


    /**
     * Method that fills with random value (-5 to 5) in given array at given intervals
     *
     * @param mat given array
     * @param x0  row starting point
     * @param y0  col starting point
     * @param x1  row ending point
     * @param y1  col ending point
     */
    public static void fillRandomComplex(Complex[][] mat, int x0, int y0, int x1, int y1) {
        Random rnd = new Random();
        for (int i = x0; i < x1; i++) {
            for (int j = y0; j < y1; j++) {
                mat[i][j] = new Complex(rnd.nextDouble(-5, 5), rnd.nextDouble(-10, 10));
            }
        }
    }


    /**
     * Method that returns number as string
     *
     * @return String
     */
    @Override
    public String toString() {
        return "(" + real + " " + imag + "i)";
    }

    /**
     * Method that sum the given complex number
     *
     * @param num complex number to sum
     */
    public void sum(Complex num) {
        this.real += num.getReal();
        this.imag += num.getImag();

    }

    /**
     * Getter real
     *
     * @return real value
     */
    public double getReal() {
        return real;
    }

    /**
     * Setter real
     *
     * @param real real value
     */
    public void setReal(double real) {
        this.real = real;
    }

    /**
     * Getter imaginer
     *
     * @return imaginer value
     */
    public double getImag() {
        return imag;
    }

    /**
     * Setter imaginer
     *
     * @param imag imaginer value
     */
    public void setImag(double imag) {
        this.imag = imag;
    }
}
