package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author syy94
/**
 * Represents the password is accepted.
 */
public class PasswordAcceptedEvent extends BaseEvent {
    /**
     * All Events should have a clear unambiguous custom toString message so that feedback message creation
     * stays consistent and reusable.
     * <p>
     * For example, the event manager post method will call any posted event's toString and print it in the console.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
