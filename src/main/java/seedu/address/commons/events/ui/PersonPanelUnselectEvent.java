package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author archthegit
/**
 * Represents an unselection in the Person List Panel
 */
public class PersonPanelUnselectEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
