package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.logic.parser.Theme;

//@@author chuaweiwen
/**
 * Indicates a request to change the theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    public final Theme theme;

    public ChangeThemeRequestEvent(Theme theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
