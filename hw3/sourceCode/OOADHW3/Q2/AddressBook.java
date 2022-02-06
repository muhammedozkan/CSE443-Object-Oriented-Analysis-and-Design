package OOADHW3.Q2;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class creates a notebook by listing
 * the AdressBookComponent objects through composition.
 */
public class AddressBook {
    private final ArrayList<AdressBookComponent> _adressbook;

    /**
     * Constructor Method
     */
    public AddressBook() {
        this._adressbook = new ArrayList<AdressBookComponent>();
    }

    /**
     * It is used to add a person or group to the address book.
     *
     * @param adressbookcomponent Person or Group
     */
    public void add(AdressBookComponent adressbookcomponent) {
        this._adressbook.add(adressbookcomponent);
    }

    /**
     * It is used to print the list.
     */
    public void print() {
        int indent = 0;
        System.out.println("### Address Book ###");


        Iterator iterator = _adressbook.iterator();
        while (iterator.hasNext()) {
            AdressBookComponent menuComponent = (AdressBookComponent) iterator.next();
            menuComponent.print(indent);
        }

        /*for (AdressBookComponent item : _adressbook
        ) {
            item.print(indent);
        }*/
    }
}
