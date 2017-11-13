package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final String themeToChangeTo;

    public ChangeThemeRequestEvent (String themeToChangeTo) {
        this.themeToChangeTo = themeToChangeTo;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
