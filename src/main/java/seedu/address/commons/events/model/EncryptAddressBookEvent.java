package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to encrypt AddressBook.
 */
public class EncryptAddressBookEvent extends BaseEvent {

    public final String password;

    public EncryptAddressBookEvent(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "request to encrypt AddressBook";
    }
}
