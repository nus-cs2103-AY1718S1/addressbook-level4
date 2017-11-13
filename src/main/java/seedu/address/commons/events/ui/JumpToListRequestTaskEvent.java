package seedu.address.commons.events.ui;
//@@author Esilocke
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of tasks
 */
public class JumpToListRequestTaskEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToListRequestTaskEvent(Index targetIndex) {
        //TODO optimize this class
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
