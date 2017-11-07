package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * event to unselect a person card in the case of a delete
 */
public class PersonPanelUnselectEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
