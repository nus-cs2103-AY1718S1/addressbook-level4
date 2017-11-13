package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author vivekscl
/**
 * Indicates a request for a change in window size.
 */
public class ChangeWindowSizeRequestEvent extends BaseEvent {

    private double windowWidth;
    private double windowHeight;

    public ChangeWindowSizeRequestEvent(double windowWidth, double windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
