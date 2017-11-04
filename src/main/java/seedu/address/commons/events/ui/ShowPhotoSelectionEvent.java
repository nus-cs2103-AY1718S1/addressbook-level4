//@@author shuangyang
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Request to display a photo selection window in the main window.
 */
public class ShowPhotoSelectionEvent extends BaseEvent {

    public final int index;

    public ShowPhotoSelectionEvent(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
