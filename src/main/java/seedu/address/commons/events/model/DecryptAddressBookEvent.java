package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to decrypt AddressBook.
 */
public class DecryptAddressBookEvent extends BaseEvent {

    public final String password;

    public DecryptAddressBookEvent(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "request to decrypt AddressBook";
    }
}
