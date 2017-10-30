package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.TagCard;

/**
 * Represents a selection change in the Tag List Panel
 */
public class TagPanelSelectionChangedEvent extends BaseEvent {

    private final TagCard newSelection;

    public TagPanelSelectionChangedEvent(TagCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public TagCard getNewSelection() {
        return newSelection;
    }
}
