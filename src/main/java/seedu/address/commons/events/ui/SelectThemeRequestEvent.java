package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the theme.
 */
public class SelectThemeRequestEvent extends BaseEvent {

    public final String theme;

    public SelectThemeRequestEvent(String theme) {
        this.theme = theme;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

