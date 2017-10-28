package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.mod.Mod;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").withMod("Friend").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(ReadOnlyPerson person) {
        try {
            addressBook.addPerson(person);
        } catch (DuplicatePersonException dpe) {
            throw new IllegalArgumentException("person is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code modName} into a {@code Mod} and adds it to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withMod(String modName) {
        try {
            addressBook.addMod(new Mod(modName));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("modName is expected to be valid.");
        }
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
