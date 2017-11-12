package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author Alim95

/**
 * Indicates a request to jump to new task in task list
 */
public class JumpToNewTaskRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToNewTaskRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
