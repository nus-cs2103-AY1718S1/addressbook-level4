//@@author Hailinx
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for switching between todoList and browser
 */
public class SwitchDisplayEvent extends BaseEvent {

    public final int mode;

    public SwitchDisplayEvent(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
