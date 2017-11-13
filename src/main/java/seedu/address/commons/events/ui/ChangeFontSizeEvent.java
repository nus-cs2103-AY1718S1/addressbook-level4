package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Represents a font size change triggered by a command or the UI
 */
//author charlesgoh
public class ChangeFontSizeEvent extends BaseEvent {

    private static int increaseSizeEventIndex = 0;
    private static int decreaseSizeEventIndex = 1;
    private static int resetSizeEventIndex = 2;

    private int triggerOption;

    public ChangeFontSizeEvent(int triggerOption) {
        this.triggerOption = triggerOption;
    }

    @Override
    public String toString() {
        return "New Event: Font size change";
    }

    public static int getIncreaseSizeEventIndex() {
        return increaseSizeEventIndex;
    }

    public static int getDecreaseSizeEventIndex() {
        return decreaseSizeEventIndex;
    }

    public static int getResetSizeEventIndex() {
        return resetSizeEventIndex;
    }

    public int getTriggerOption() {
        return triggerOption;
    }

    public void setTriggerOption(int triggerOption) {
        this.triggerOption = triggerOption;
    }
}
