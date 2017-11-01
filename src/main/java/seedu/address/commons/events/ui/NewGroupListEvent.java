package seedu.address.commons.events.ui;

import javafx.collections.ObservableList;
import seedu.address.commons.events.BaseEvent;
import seedu.address.model.group.ReadOnlyGroup;

import seedu.address.model.person.ReadOnlyPerson;

//@@author eldonng
/**
 * Indicates that the group list has been changed.
 */
public class NewGroupListEvent extends BaseEvent {

    private ObservableList<ReadOnlyGroup> groups;
    private ObservableList<ReadOnlyPerson> persons;

    public NewGroupListEvent(ObservableList<ReadOnlyGroup> groups, ObservableList<ReadOnlyPerson> persons) {
        this.groups = groups;
        this.persons = persons;
    }

    public ObservableList<ReadOnlyPerson> getPersonsList() {
        return persons;
    }

    public ObservableList<ReadOnlyGroup> getGroupsList() {
        return groups;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
