package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author fongwz
/**
 * Indicates a request to jump to the list of browser panels
 */
public class ChangeThemeEvent extends BaseEvent {

    public final String theme;

    public ChangeThemeEvent(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
