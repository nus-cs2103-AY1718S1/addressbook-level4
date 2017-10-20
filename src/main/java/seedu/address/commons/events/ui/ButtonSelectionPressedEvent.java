package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a selection change in the Person List Panel
 */
public class ButtonSelectionPressedEvent extends BaseEvent {


    private final String buttonPressed;

    public ButtonSelectionPressedEvent(String buttonPressed) {
        this.buttonPressed = buttonPressed;
    }

    public String getButtonPressed() {
        return this.buttonPressed;
    }

    @Override
    public String toString() {
        return this.buttonPressed;
    }
}
