package seedu.address.model;

import java.util.function.Predicate;
import java.util.regex.PatternSyntaxException;

import javafx.collections.ObservableList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ReadOnlyEvent> PREDICATE_SHOW_ALL_EVENTS = unused -> true;
    Predicate<ReadOnlyReminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    //=========== Model support for property component =============================================================

    /** Adds a new customize property */
    void addProperty(String shortName, String fullName, String message, String regex)
            throws DuplicatePropertyException, PatternSyntaxException;

    //=========== Model support for contact component =============================================================

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Replaces the given person {@code target} with {@code editedPerson} */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    //=========== Model support for tag component =============================================================

    /** Removes the specific tag (from all persons with that tag) */
    void removeTag(Tag tags) throws DuplicatePersonException, PersonNotFoundException;

    /** Checks whether there exists a tag (with the same tagName) */
    boolean hasTag(Tag tag);

    /** Changes the color of an existing tag (through TagColorManager) */
    void setTagColor(Tag tag, String color);

    //=========== Model support for activity component =============================================================

    /** Adds an event */
    void addEvent(ReadOnlyEvent event) throws DuplicateEventException;

    /** Updates the given event */
    void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException;
    /** Deletes the given event */
    void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException;


    //=========== Filtered Person/Activity List support =============================================================

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered event list */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /** Updates the filter of the filtered person list to filter by the given {@code predicate}. */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /** Updates the filter of the filtered event list to filter by the given {@code predicate}. */
    void updateFilteredEventsList(Predicate<ReadOnlyEvent> predicate);
}
