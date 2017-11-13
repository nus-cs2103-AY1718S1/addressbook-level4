package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author taojiashu
/**
 * Indicates a request to display the address of a person in Google Maps
 */
public class ShowLocationRequestEvent extends BaseEvent {

    public final String address;

    public ShowLocationRequestEvent(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
