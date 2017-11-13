//@@author hxy0229
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.GroupCard;

/**
 * stub comment for checkstyle
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
