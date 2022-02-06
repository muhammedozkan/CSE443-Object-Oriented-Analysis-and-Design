package OOADHW3.Q1;

import java.util.ArrayList;
import java.util.List;

/**
 * This class supports linear complexity
 * insertion/deletion/random access.
 */
public class BestDSEver implements IBestDSEver {
    //for simulate BestDSEver class linear complexity access
    private final List<Object> _arr;

    /**
     * Constructor method
     */
    public BestDSEver() {
        this._arr = new ArrayList<>();
    }

    /**
     * Inserting object
     *
     * @param o Object
     */
    @Override
    public void insert(Object o) {
        _arr.add(o);
    }

    /**
     * Deleting object
     *
     * @param o Object
     */
    @Override
    public void remove(Object o) {
        _arr.remove(o);
    }

    /**
     * Getting object
     *
     * @param index value
     * @return object
     */
    @Override
    public Object get(int index) {
        return _arr.get(index);
    }
}
