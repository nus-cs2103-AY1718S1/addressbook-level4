package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to delete person of targeted index from UI menu
 */
//@@author Choony93
public class PersonPanelDeleteEvent extends BaseEvent {

    public final int targetIndex;

    public PersonPanelDeleteEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getOneBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
