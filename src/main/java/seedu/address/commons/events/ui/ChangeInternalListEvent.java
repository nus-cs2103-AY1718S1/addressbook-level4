package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a change of internal list in the Person List Panel
 */
public class ChangeInternalListEvent extends BaseEvent{

    private String listName;

    public ChangeInternalListEvent(String listName) {
        this.listName = listName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
