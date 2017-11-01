package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class ClearListSelectionEvent extends BaseEvent {

    public ClearListSelectionEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
