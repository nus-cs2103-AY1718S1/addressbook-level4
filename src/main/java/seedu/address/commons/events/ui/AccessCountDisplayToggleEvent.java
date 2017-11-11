package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

//@@author Zzmobie
/** Toggles access count display as necessary*/
public class AccessCountDisplayToggleEvent extends BaseEvent {

    private final boolean isDisplayed;

    public AccessCountDisplayToggleEvent(boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
