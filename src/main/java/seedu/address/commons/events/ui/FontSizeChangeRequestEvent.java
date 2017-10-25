package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class FontSizeChangeRequestEvent extends BaseEvent {

    public final boolean isReset;
    public final int sizeChange;

    public FontSizeChangeRequestEvent() {
        isReset = true;
        this.sizeChange = 0;
    }

    public FontSizeChangeRequestEvent(int sizeChange) {
        isReset = false;
        this.sizeChange = sizeChange;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
