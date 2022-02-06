package OOADHW3.Q1;

/**
 * this class acts as a proxy for
 * the BestDSever class to be thread-safe.
 */
public class BestDSEverProxy implements IBestDSEver {
    private final BestDSEver subject;

    /**
     * Proxy Constructer Method
     *
     * @param subject BestDSEver object
     */
    public BestDSEverProxy(BestDSEver subject) {
        this.subject = subject;
    }

    /**
     * Inserting object
     *
     * @param o Object
     */
    @Override
    public void insert(Object o) {
        synchronized (subject) {
            subject.insert(o);
        }
    }

    /**
     * Deleting object
     *
     * @param o Object
     */
    @Override
    public void remove(Object o) {
        synchronized (subject) {
            subject.remove(o);
        }
    }

    /**
     * Getting object
     *
     * @param index value
     * @return object
     */
    @Override
    public Object get(int index) {
        synchronized (subject) {
            return subject.get(index);
        }
    }
}
