package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author JasmineSee

/**
 * Indicates that a theme change is occurring.
 */
public class ThemeChangeEvent extends BaseEvent {

    private String theme;

    public ThemeChangeEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getTheme() {
        return theme;
    }
}
