package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

// @@author pwenzhe
/**
 * Indicates a request for theme change.
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
