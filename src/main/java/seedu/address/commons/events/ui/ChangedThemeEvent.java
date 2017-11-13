//@@author keloysiusmak
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class ChangedThemeEvent extends BaseEvent {

    public final String theme;

    public ChangedThemeEvent(String setTheme) {
        this.theme = setTheme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
