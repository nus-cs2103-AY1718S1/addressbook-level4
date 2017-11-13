package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

//@@author zhangshuoyang
/**
 * Indicate a request to switch theme by the user
 */
public class SwitchThemeRequestEvent extends BaseEvent {

    public final Index index;

    public SwitchThemeRequestEvent(Index index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return this.getClass().toString();
    }
}
