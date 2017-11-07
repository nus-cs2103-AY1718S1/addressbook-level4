package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
//@@author ChenXiaoman
/**
 * Indicates a request to change the tag color of the application
 */
public class ChangeTagColorEvent extends BaseEvent {
    private String color;

    public ChangeTagColorEvent(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public String getColor() {
        return color;
    }
}
