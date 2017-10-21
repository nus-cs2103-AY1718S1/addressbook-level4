package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class JumpToBrowserListRequestEvent extends BaseEvent {

    public final String browserItem;

    public JumpToBrowserListRequestEvent(String item) {
        this.browserItem = item;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
