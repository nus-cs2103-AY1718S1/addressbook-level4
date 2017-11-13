//@@author Hailinx
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for clearing list selection
 */
public class ClearListSelectionEvent extends BaseEvent {

    public ClearListSelectionEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
