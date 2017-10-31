package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.group.ReadOnlyGroup;

public class NewGroupListEvent extends BaseEvent {
    private ObservableList<ReadOnlyGroup> groups;

    public NewGroupListEvent(ObservableList<ReadOnlyGroup> groups) {
        this.groups = groups;
    }

    public ObservableList<ReadOnlyGroup> getGroupsList() {
        return groups;
    }
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
