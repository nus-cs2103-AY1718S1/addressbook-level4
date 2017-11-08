//@@author ChenXiaoman
package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the font size of the application
 */
public class ChangeFontSizeEvent extends BaseEvent {

    public final String message;
    private String fontSize;

    public ChangeFontSizeEvent(String message, String fontSize) {
        this.fontSize = fontSize;
        this.message = message;
    }

    public String getFontSize() {
        return fontSize;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
