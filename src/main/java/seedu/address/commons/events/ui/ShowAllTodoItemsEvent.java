package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

public class ShowAllTodoItemsEvent extends BaseEvent {

    public ShowAllTodoItemsEvent() {
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
