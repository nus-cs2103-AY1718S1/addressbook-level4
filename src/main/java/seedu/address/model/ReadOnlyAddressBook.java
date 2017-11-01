package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.reminder.ReadOnlyReminder;
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
     * Returns an unmodifiable view of the events list.
     * This list will not contain any duplicate events.
     */
    ObservableList<ReadOnlyEvent> getEventList();

    /**
     * Returns an unmodifiable view of the reminders list.
     * This list will not contain any duplicate reminders.
     */
    ObservableList<ReadOnlyReminder> getReminderList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
