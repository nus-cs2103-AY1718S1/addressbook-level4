package seedu.room.commons.events.ui;

import seedu.room.commons.events.BaseEvent;

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
