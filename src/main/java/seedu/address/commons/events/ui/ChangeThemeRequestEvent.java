package seedu.address.commons.events.ui;

//@@author jeffreygohkw
import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    private final String styleSheet;

    public ChangeThemeRequestEvent(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getStyleSheet() {
        return styleSheet;
    }
}
