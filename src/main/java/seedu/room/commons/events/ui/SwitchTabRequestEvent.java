package seedu.room.commons.events.ui;

import seedu.room.commons.events.BaseEvent;

//@@author sushinoya
/**
 * An event requesting to switch tabs between Residents List and Events List.
 */
public class SwitchTabRequestEvent extends BaseEvent {

    public final int targetIndex;

    public SwitchTabRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex - 1;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
