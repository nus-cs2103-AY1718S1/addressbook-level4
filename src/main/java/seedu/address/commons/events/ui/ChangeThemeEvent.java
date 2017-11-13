package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to open mail app.
 */

public class ChangeThemeEvent extends BaseEvent {
    public final String themeName;

    public ChangeThemeEvent(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
