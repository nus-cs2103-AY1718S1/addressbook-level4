package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.schedule.ReadOnlySchedule;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    /**
     * Returns an unmodifiable view of the groups list.
     * This list will not contain any duplicate groups.
     */
    ObservableList<ReadOnlyGroup> getGroupList();

    /**
     * Returns an unmodifiable view of the schedules list.
     * This list will not contain any duplicate schedules.
     */
    ObservableList<ReadOnlySchedule> getScheduleList();

}
