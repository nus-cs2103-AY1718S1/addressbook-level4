package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.MeetingCard;

//@@author Melvin-leo
/**
 * Represents a selection change in the Meeting List Panel
 */
public class MeetingPanelSelectionChangedEvent extends BaseEvent {
    private final MeetingCard newSelection;

    public MeetingPanelSelectionChangedEvent(MeetingCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public MeetingCard getNewSelection() {
        return newSelection;
    }
}
