//@@author conantteo
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event to request for sending email to one or more email addresses
 */
public class EmailRequestEvent extends BaseEvent {

    private final String allEmailAddresses;

    public EmailRequestEvent(String allEmailAddresses) {
        this.allEmailAddresses = allEmailAddresses;
    }

    public String getAllEmailAddresses() {
        return allEmailAddresses;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
