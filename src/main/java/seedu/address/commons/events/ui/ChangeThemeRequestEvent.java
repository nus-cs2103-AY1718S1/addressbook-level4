package seedu.address.commons.events.ui;

//@@author itsdickson

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the theme.
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final String theme;

    public ChangeThemeRequestEvent(String theme) {
        this.theme = theme;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
