package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author LimYangSheng-unused
// Code was not used as requesting user permission before execution was not advised for CLI applications.
/**
 * Indicates a request to accept user permission before execution of command
 */
public class RequestingUserPermissionEvent extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
