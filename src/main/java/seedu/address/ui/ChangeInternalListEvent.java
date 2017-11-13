//@@author siri99
package seedu.address.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Creates an event to change the Internal List
 */
public class ChangeInternalListEvent extends BaseEvent {

    //private String listName;
    private final String listName;

    public ChangeInternalListEvent(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author siri99
