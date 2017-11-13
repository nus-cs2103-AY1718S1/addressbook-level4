package seedu.room.commons.events.ui;

import seedu.room.commons.events.BaseEvent;

//@@author Haozhe321

/**
 * This event will be raised during the PrevCommand or the NextCommand
 */
public class ChangeMonthRequestEvent extends BaseEvent {
    public final int targetIndex;

    public ChangeMonthRequestEvent(int targetIndex) {
        this.targetIndex = targetIndex;
    }

    public int getTargetIndex() {
        return targetIndex;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
