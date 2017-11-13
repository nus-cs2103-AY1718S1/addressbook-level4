//@@author tshradheya
package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;

/**
 * Triggers event to read and store image
 */
public class DisplayPictureChangedEvent extends BaseEvent {

    public final String path;
    public final int newPath;
    private boolean isRead;

    public DisplayPictureChangedEvent(String path, int newPath) {
        this.path = path;
        this.newPath = newPath;
        isRead = true;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return newPath + " stored";
    }
}
