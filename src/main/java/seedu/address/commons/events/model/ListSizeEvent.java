package seedu.address.commons.events.model;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.events.BaseEvent;

public class ListSizeEvent extends BaseEvent {

    public int sizeOfList;

    public ListSizeEvent(int size) {
        requireNonNull(size);

        this.sizeOfList = size;
    }

    @Override
    public String toString() {
        return (" " + sizeOfList + " "); }

    public int getToggle() {
        return this.sizeOfList; }
}
