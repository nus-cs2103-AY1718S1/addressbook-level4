package seedu.address.commons.events.ui;
//@@author zhoukai07
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request for theme change
 */
public class ChangeThemeRequestEvent extends BaseEvent {
    public final String themeUrl;
    public ChangeThemeRequestEvent (String themeUrl) {
        this.themeUrl = themeUrl;
    }
    public String getThemeUrl() {
        return this.themeUrl;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
