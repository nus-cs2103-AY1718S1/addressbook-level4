package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author jelneo
/**
 * Indicate request to change to command box view.
 */
public class ChangeToCommandBoxView extends BaseEvent {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
