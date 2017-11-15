//@@author shuang-yang
package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Request to display a photo selection window in the main window.
 */
public class ShowPhotoSelectionEvent extends BaseEvent {

    public final Index index;

    public ShowPhotoSelectionEvent(Index index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
