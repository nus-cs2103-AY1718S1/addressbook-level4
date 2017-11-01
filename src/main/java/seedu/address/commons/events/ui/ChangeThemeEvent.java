package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to change theme.
 */
public class ChangeThemeEvent extends BaseEvent {

    public final String theme;
    public ChangeThemeEvent(String theme)
    {
        this.theme = theme;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();

    }
    public String getTheme()
    {
        return this.theme;
    }

}
