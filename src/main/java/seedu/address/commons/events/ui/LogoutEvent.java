package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to logout
 */
public class LogoutEvent extends BaseEvent {
    @Override
    public String toString() {
        return "logout";
    }
}
