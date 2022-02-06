package OOADHW3.Q2;

/**
 * This class provides an abstract contract for the address book.
 */
public abstract class AdressBookComponent {
    /**
     * It is used to add a person or group to the address book.
     *
     * @param adressbookcomponent Person or Group
     */
    public void add(AdressBookComponent adressbookcomponent) {
        throw new UnsupportedOperationException();
    }

    /**
     * Getter Name
     *
     * @return
     */
    public String getName() {
        throw new UnsupportedOperationException();
    }

    /**
     * Getter Email
     *
     * @return
     */
    public String getEmail() {
        throw new UnsupportedOperationException();
    }

    /**
     * It is used to print the person or group.
     *
     * @param indent number of space characters for indentation
     */
    public void print(int indent) {
        throw new UnsupportedOperationException();
    }
}
