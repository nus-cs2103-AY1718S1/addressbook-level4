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

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_WHITELISTED_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the name of current displayed list */
    String getCurrentList();

    /** Sets the name of current displayed list */
    void setCurrentList(String currentList);

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Deletes the given person from blacklist and returns the deleted person */
    ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Removes the given person from whitelist and returns the updated person */
    ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Adds the given person into blacklist and returns the added person*/
    ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson person);

    /** Adds the given person into whitelist and returns the added person */
    ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson person);

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

    /** Returns an unmodifiable view of the blacklisted filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList();

    /** Returns an unmodifiable view of the whitelisted filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Updates the filter of the filtered blacklisted person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate);

    /**
     * Updates the filter of the filtered whitelisted person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    int updateFilteredWhitelistedPersonList(Predicate<ReadOnlyPerson> predicate);

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

    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException;

    /**
     * Authenticates user
     * @throws UserNotFoundException if user is not found
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

    /**
     * Retrieves the full list of persons.
     */
    ObservableList<ReadOnlyPerson> getAllPersons();

    /**
     * Sorts the master list by specified order.
     */
    void sortBy(String order) throws IllegalArgumentException;

    /**
     * Increase the debt of a person by the amount indicated
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void addDebtToPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException;

    /**
     * Decrease the debt of a person by the amount indicated
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     * @throws IllegalValueException if {@code amount} that is repaid by the person is more than the debt owed.
     */
    ReadOnlyPerson deductDebtFromPerson(ReadOnlyPerson target, Debt amount)
            throws PersonNotFoundException, IllegalValueException;

    void updateDebtFromInterest(ReadOnlyPerson person, int differenceInMonths);
}
