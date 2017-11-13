package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author Choony93

/**
 * Indicates a request to display Google Map of targeted index
 */
public class DisplayGmapEvent extends BaseEvent {

    public final int targetIndex;

    public DisplayGmapEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
