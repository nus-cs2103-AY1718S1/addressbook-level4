package seedu.address.commons.events.ui;

//@@author jeffreygohkw
import seedu.address.commons.events.BaseEvent;

/**
 * An Event for the opening of a save file from a selected location.
 */
public class OpenRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
