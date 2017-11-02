package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's home number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class HomePhone extends Phone {

    public HomePhone (String phone) throws IllegalValueException {
        super(phone);
    }

}
