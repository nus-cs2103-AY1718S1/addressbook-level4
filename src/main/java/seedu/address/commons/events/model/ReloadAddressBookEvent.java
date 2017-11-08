//@@author Hailinx
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates the request to reload Address Book.
 */
public class ReloadAddressBookEvent extends BaseEvent {

    public ReloadAddressBookEvent() {
    }

    @Override
    public String toString() {
        return "Request to reload Address Book.";
    }
}
