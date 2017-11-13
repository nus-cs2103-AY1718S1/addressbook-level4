//@@author thanhson16198
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to go to the list of places
 */
public class GotoRequestEvent extends BaseEvent {

    public final int targetIndex;

    public GotoRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
//@@author
