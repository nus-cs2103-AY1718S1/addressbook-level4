package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.UserNotFoundException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.model.person.Debt;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    //@@author jaivigneshvenugopal
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_WHITELISTED_PERSONS = unused -> true;
    //@@author

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_OVERDUE_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    //@@author jaivigneshvenugopal
    /** Returns the name of current displayed list */
    String getCurrentListName();

    /** Sets the name of current displayed list */
    void setCurrentListName(String currentList);
    //@@author

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    //@@author jaivigneshvenugopal
    /** Deletes the given person from blacklist and returns the deleted person */
    ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Removes the given person from whitelist and returns the updated person */
    ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson target) throws PersonNotFoundException;
    //@@author

    /** Removes the given person from overdue list and returns the updated person */
    ReadOnlyPerson removeOverdueDebtPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    //@@author jaivigneshvenugopal
    /** Adds the given person into blacklist and returns the added person*/
    ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson person);

    /** Adds the given person into whitelist and returns the added person */
    ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson person);
    //@@author

    /** Adds the given person into overdue list and returns the added person */
    ReadOnlyPerson addOverdueDebtPerson(ReadOnlyPerson person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    //@@author jaivigneshvenugopal
    /** Returns an unmodifiable view of the blacklisted filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList();

    /** Returns an unmodifiable view of the whitelisted filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList();
    //@@author

    /** Returns an unmodifiable view of the filtered person list with overdue debt */
    ObservableList<ReadOnlyPerson> getFilteredOverduePersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @return size of current displayed filtered list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author jaivigneshvenugopal
    /**
     * Updates the filter of the filtered blacklisted person list to filter by the given {@code predicate}.
     * @return size of current displayed filtered list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Updates the filter of the filtered whitelisted person list to filter by the given {@code predicate}.
     * @return size of current displayed filtered list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredWhitelistedPersonList(Predicate<ReadOnlyPerson> predicate);
    //@@author

    /**
     * Updates the filter of the filtered person list with overdue debt to filter by the given {@code predicate}.
     * @return size of current displayed filtered list.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredOverduePersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Retrieves the full list of persons nearby a particular person.
     */
    ObservableList<ReadOnlyPerson> getNearbyPersons();

    /**
     * Retrieves the currently selected person.
     */
    ReadOnlyPerson getSelectedPerson();

    /**
     * Obtains and updates the list of persons that share the same cluster as {@param person}.
     */
    void updateSelectedPerson(ReadOnlyPerson person);

    /**
     * Deselects the currently selected person.
     */
    void deselectPerson();

    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException;

    /**
     * Authenticates user.
     * @throws UserNotFoundException if user is not found.
     * @throws IllegalValueException if username and password do not follow username and password requirements.
     */
    void authenticateUser(Username username, Password password) throws UserNotFoundException, IllegalValueException;

    /**
     * Logs user out
     */
    void logout();

    /**
     * Updates the list shown in Person List Panel to the requested list.
     */
    void changeListTo(String listName);
    //@@author

    /**
     * Retrieves the full list of persons.
     */
    ObservableList<ReadOnlyPerson> getAllPersons();

    /**
     * Sorts the master list by specified order.
     */
    void sortBy(String order) throws IllegalArgumentException;

    /**
     * Increase the debt of a person by the amount indicated.
     * @return the person whose debt was increased.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    ReadOnlyPerson addDebtToPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException;

    /**
     * Decrease the debt of a person by the amount indicated.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     * @throws IllegalValueException if {@code amount} that is repaid by the person is more than the debt owed.
     */
    ReadOnlyPerson deductDebtFromPerson(ReadOnlyPerson target, Debt amount)
            throws PersonNotFoundException, IllegalValueException;

    void updateDebtFromInterest(ReadOnlyPerson person, int differenceInMonths);

    //@@author jaivigneshvenugopal
    /**
     * Adds the picture of the person into app database and sets the person's display picture boolean status to true
     * @return true if person's picture is successfully added.
     */
    boolean addProfilePicture(ReadOnlyPerson person);

    /**
     * Sets the person's display picture boolean status to false.
     */
    void removeProfilePicture(ReadOnlyPerson person);
    //@@author
}
