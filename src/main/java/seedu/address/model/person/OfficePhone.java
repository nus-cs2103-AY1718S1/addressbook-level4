package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's office phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class OfficePhone extends Phone {

    public OfficePhone (String phone) throws IllegalValueException {
        super(phone);
    }

}
