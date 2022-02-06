package OOADHW3.Q2;

/**
 * This class defines the person in the address book.
 */
public class AddressBookPerson extends AdressBookComponent {
    private final String name;
    private final String email;

    /**
     * This constructor method is used to create person.
     *
     * @param name  Person Name
     * @param email Person Emame
     */
    public AddressBookPerson(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Getter Name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Getter Email
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * It is used to print the person or group.
     *
     * @param indent number of space characters for indentation
     */
    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("-");
        }
        System.out.println(getEmail() + " " + getName());
    }


}
