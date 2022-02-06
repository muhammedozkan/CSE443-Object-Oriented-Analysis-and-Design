package OOADHW3.Q1;

/**
 * This interface supports
 * inserting/deleting/getting object
 */
public interface IBestDSEver {
    /**
     * Inserting object
     *
     * @param o Object
     */
    void insert(Object o);

    /**
     * Deleting object
     *
     * @param o Object
     */
    void remove(Object o);

    /**
     * Getting object
     *
     * @param index value
     * @return object
     */
    Object get(int index);
}
