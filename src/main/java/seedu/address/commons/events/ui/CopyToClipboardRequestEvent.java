package seedu.address.commons.events.ui;

import javafx.scene.input.ClipboardContent;
import seedu.address.commons.events.BaseEvent;

//@@author bladerail
/**
 * Indicates a request for copying a string to clipboard
 */
public class CopyToClipboardRequestEvent extends BaseEvent {

    public final ClipboardContent toCopy;

    public CopyToClipboardRequestEvent(String toCopy) {
        this.toCopy = new ClipboardContent();
        this.toCopy.putString(toCopy);
    }

    public ClipboardContent getToCopy() {
        return this.toCopy;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
