//@@author ChenXiaoman
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to show a person's address in Google Map
 */
public class ShowPersonAddressEvent extends BaseEvent {
    private String address;

    public ShowPersonAddressEvent(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    /**
     * Get the address from the event
     */
    public String getAddress() {
        return address;
    }
}
