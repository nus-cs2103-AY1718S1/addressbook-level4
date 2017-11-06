package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author newalter
/**
 * An event requesting to change the size of the main window.
 */
public class ResizeMainWindowEvent extends BaseEvent {

    private int width;
    private int height;

    public ResizeMainWindowEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return null;
    }
}
