package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class ChangeSearchEvent extends BaseEvent {

    public final int searchMode;

    public ChangeSearchEvent(int searchMode) {
        this.searchMode = searchMode;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
