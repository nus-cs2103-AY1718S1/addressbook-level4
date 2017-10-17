package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to enable highlighting the command keywords in the application
 */
public class ColorKeywordEvent extends BaseEvent {
    public final boolean isEnabled;

    public ColorKeywordEvent(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
