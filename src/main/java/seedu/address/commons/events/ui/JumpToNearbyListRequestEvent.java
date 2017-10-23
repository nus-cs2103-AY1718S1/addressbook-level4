package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author khooroko
/**
 * Indicates a request to jump to the nearby list of persons
 */
public class JumpToNearbyListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToNearbyListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
