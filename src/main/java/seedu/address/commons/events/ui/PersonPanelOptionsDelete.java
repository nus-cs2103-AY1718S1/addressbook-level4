package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to display Google Map of targeted index
 */
public class PersonPanelOptionsDelete extends BaseEvent {

    public final int targetIndex;

    public PersonPanelOptionsDelete(Index targetIndex) {
        this.targetIndex = targetIndex.getOneBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
