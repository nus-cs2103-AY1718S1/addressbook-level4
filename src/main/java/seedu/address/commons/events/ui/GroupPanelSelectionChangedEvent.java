//@@author hthjthtrh
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.GroupCard;

/**
 * Represents a selection change in the Group List Panel
 */
public class GroupPanelSelectionChangedEvent extends BaseEvent {


    private final GroupCard newSelection;

    public GroupPanelSelectionChangedEvent(GroupCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public GroupCard getNewSelection() {
        return newSelection;
    }
}
//@@author
