//@@author zenghou
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates that UserCreds isValidated flag has changed.
 */
public class UserCredsChangedEvent extends BaseEvent {

    @Override
    public String toString() {
        return UserCredsChangedEvent.class.getSimpleName();
    }
}
