package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.GroupCard;

//@@author cjianhui
/**
 * Represents a selection change in the Person List Panel
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
