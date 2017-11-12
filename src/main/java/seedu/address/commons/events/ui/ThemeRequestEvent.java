//@@author vmlimshimin
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the theme.
 */
public class ThemeRequestEvent extends BaseEvent {

    public final String theme;

    public ThemeRequestEvent(String theme) {
        this.theme = theme;
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
