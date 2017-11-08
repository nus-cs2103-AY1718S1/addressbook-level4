package seedu.address.commons.events.model;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.BaseEvent;

/**
 *  Represents an event where displayed list size is updated
 *  Find, List, Delete multiple.
 *
 */
public class ListSizeEvent extends BaseEvent {

    private int sizeOfList;

    public ListSizeEvent(int size) {
        requireNonNull(size);

        this.sizeOfList = size;
    }

    @Override
    public String toString() {
        return (" " + sizeOfList + " ");
    }

    public int getToggle() {
        return this.sizeOfList;
    }
}
