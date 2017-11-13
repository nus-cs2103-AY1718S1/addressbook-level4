package seedu.address.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ReadOnlyReminder> PREDICATE_SHOW_ALL_REMINDERS = unused->true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    void sendMailToContacts(String tagName, String subject, List<ReadOnlyPerson> lastShownList) throws
            IOException, URISyntaxException, IllegalValueException;

    /** Deletes the given reminder. */
    void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException;

    /** Clear the browser panel */
    void clearBrowserPanel();

    /** Adds the given reminder */
    void addReminder(ReadOnlyReminder person) throws DuplicateReminderException;

    /** Adds photo to person */
    void addPhotoPerson(ReadOnlyPerson person, String filePath, Index targetIndex)
            throws PersonNotFoundException,
            IOException, IllegalValueException;

    /** Adds remark to person */
    void addRemarkPerson(ReadOnlyPerson person, String remark, Index targetIndex);

    /** Searches for a person on facebook */
    void faceBook(ReadOnlyPerson person) throws PersonNotFoundException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /**
     * Replaces the given reminder {@code target} with {@code changedReminder}.
     *
     * @throws DuplicateReminderException if updating the reminder's details causes the reminder to be equivalent to
     *      another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    void updateReminder(ReadOnlyReminder target, ReadOnlyReminder changedReminder)
            throws DuplicateReminderException, ReminderNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered reminders list */
    ObservableList<ReadOnlyReminder> getFilteredReminderList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);
    //@@author ChenXiaoman
    /**
     * Update color of tags
     * @param tagList
     * @param color
     */
    void updateTagColorPair(Set<Tag> tagList, TagColor color) throws IllegalValueException;
    //@@author
    /**
     * Updates the filter of the filtered reminder list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate);



}
