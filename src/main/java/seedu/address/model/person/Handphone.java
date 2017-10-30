package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's handphone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Handphone extends Phone {

    public Handphone(String phone) throws IllegalValueException {
        super(phone);
    }

}
