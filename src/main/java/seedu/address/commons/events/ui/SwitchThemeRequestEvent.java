package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * An event requesting to switch current theme.
 */
public class SwitchThemeRequestEvent extends BaseEvent {

    public final boolean isLight;

    public SwitchThemeRequestEvent(boolean isLight) {
        this.isLight = isLight;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
