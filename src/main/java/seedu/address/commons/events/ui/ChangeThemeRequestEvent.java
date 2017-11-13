package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author teclu
/**
 * Indicates a request for theme change.
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final String theme;

    public ChangeThemeRequestEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }

}
