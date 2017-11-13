package seedu.address.commons.events.ui;

//@@author jeffreygohkw
import seedu.address.commons.events.BaseEvent;

/**
 * An Event for the saving of data to a selected location.
 */
public class SaveAsRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
