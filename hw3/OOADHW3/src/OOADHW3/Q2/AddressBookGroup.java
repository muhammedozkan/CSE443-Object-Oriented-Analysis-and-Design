package OOADHW3.Q2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class defines the groups in the address book.
 */
public class AddressBookGroup extends AdressBookComponent {

    private final ArrayList<AdressBookComponent> _groupMembers;

    private final String _groupEmail;

    /**
     * This constructor method is used to create group.
     *
     * @param email Group Email
     */
    public AddressBookGroup(String email) {
        this._groupEmail = email;
        this._groupMembers = new ArrayList<AdressBookComponent>();
    }

    /**
     * It is used to add a person or group to the address book.
     *
     * @param adressbookcomponent Person or Group
     */
    public void add(AdressBookComponent adressbookcomponent) {
        this._groupMembers.add(adressbookcomponent);
    }

    /**
     * Getter Email
     *
     * @return
     */
    public String getEmail() {
        return _groupEmail;
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
        System.out.println(getEmail());

        indent += 2;


        Iterator iterator = _groupMembers.iterator();
        while (iterator.hasNext()) {
            AdressBookComponent menuComponent = (AdressBookComponent) iterator.next();

            menuComponent.print(indent);
        }


        /*for (AdressBookComponent item : _adressbookcomponent
        ) {
            item.print(indent);
        }*/
    }


}
