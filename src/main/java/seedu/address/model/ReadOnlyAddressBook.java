package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.timeslot.Date;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.Relationship;
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

    //@@author reginleiff
    /**
     * Returns an unmodifiable view of the events list.
     */
    ObservableList<ReadOnlyEvent> getEventList();

    /**
     * Returns an unmodifiable view of the schedule.
     */
    ObservableList<ReadOnlyEvent> getTimetable(Date currentDate);

    //@@author

    //@@author huiyiiih
    /**
     * Returns an unmodifiable view of the relationships list.
     * This list will not contain any duplicate relationships.
     */
    ObservableList<Relationship> getRelList();
    //@@author
    //@@author shuang-yang
    /**
     * Returns the last changed event.
     */
    ReadOnlyEvent getLastChangedEvent();

    /**
     * Returns the last changed event.
     */
    ReadOnlyEvent getNewlyAddedEvent();

    Date getCurrentDate();
}
