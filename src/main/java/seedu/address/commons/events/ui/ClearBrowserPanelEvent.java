package seedu.address.commons.events.ui;
//@@author RonakLakhotia
import seedu.address.commons.events.BaseEvent;

/**
 * Raises a ClearBrowserPanelEvent to clear the browser panel when the clear command is executed
 */
public class ClearBrowserPanelEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
