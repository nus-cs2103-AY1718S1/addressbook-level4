package seedu.address.model;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.UpdateListForSelectionEvent;
import seedu.address.commons.events.model.UpdatePopularityCounterForSelectionEvent;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.reminders.exceptions.ReminderNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Clears existing backing model and replaces with the provided new reminders. */
    void resetReminders(UniqueReminderList newReminders);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns an unmodifiable view of the sorted list of reminders */
    ObservableList<ReadOnlyReminder> getSortedReminderList();

    /** Return the reminders */
    ReadOnlyUniqueReminderList getUniqueReminderList();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Adds the given reminder */
    void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException;

    /** Deletes the given reminder. */
    void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException;

    /** Reads and Stores the image */
    boolean addDisplayPicture(String path, int newPath) throws IOException;

    /** Shows location of given person */
    void showLocation(ReadOnlyPerson person) throws PersonNotFoundException;

    /** Shows webpage of given person */
    void showPersonWebpage(ReadOnlyPerson person) throws PersonNotFoundException;

    /** Creates String of valid recipients */
    String createEmailRecipients(Predicate<ReadOnlyPerson> predicate);

    /** Raises event to send email through Browser panel */
    void processEmailEvent(String recipients, Subject subject, Body body, Service service);
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
     * Replaces the given reminder {@code target} with {@code editedReminder}.
     *
     * @throws DuplicateReminderException if updating the reminder's details causes the reminder to be equivalent to
     *      another existing reminder in the list.
     * @throws ReminderNotFoundException if {@code target} could not be found in the list.
     */
    void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    void sortFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    void updateFilteredListToShowAll();

    void showDefaultPanel();

    /** Returns an unmodifiable view of the birthday panel filtered person list */
    ObservableList<ReadOnlyPerson> getBirthdayPanelFilteredPersonList();

    void updatePopularContactList();

    ObservableList<ReadOnlyPerson> getPopularContactList();

    void getOnlyTopFiveMaximum();

    void refreshWithPopulatingAddressBook();

    void updatePersonsPopularityCounterByOne(ReadOnlyPerson person) throws DuplicatePersonException,
            PersonNotFoundException;

    ReadOnlyPerson increaseCounterByOne(ReadOnlyPerson person);

    void increaseCounterByOneForATag(List<ReadOnlyPerson> filteredPersonsForEmail);

    void updateFilteredPersonListForViewTag(Predicate<ReadOnlyPerson> predicate);

    Index getIndexOfGivenPerson(ReadOnlyPerson person);

    void handleUpdateListForSelectionEvent(UpdateListForSelectionEvent updateListForSelectionEvent);

    void handleUpdatePopularityCounterForSelectionEvent(
            UpdatePopularityCounterForSelectionEvent updatePopularityCounterForSelectionEvent);

    void clearSelection();
}
