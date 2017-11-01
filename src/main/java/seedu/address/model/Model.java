package seedu.address.model;

import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    //@@author Alim95
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ONLY_PINNED = person -> person.isPinned();
    //@@author
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_NOT_HIDDEN = person -> !person.isPrivate();

    Predicate<ReadOnlyTask> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    // ================ Related to Persons ==============================

    /**
     * Deletes the given person.
     */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Hides the given person.
     */
    void hidePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    //@@author Alim95
    /**
     * Pins the given person.
     */
    void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Unpins the given person.
     */
    void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    //@@author
    /**
     * Adds the given person
     */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author Alim95
    /**
     * Sorts the AddressBook.
     */
    void sortList(String toSort);
    //@@author

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    // ================ Related to AliasTokens ==============================

    /**
     * Adds the given AliasToken
     */
    void addAliasToken(ReadOnlyAliasToken target) throws DuplicateTokenKeywordException;

    /**
     * Removes the given AliasToken.
     */
    void deleteAliasToken(ReadOnlyAliasToken target) throws TokenKeywordNotFoundException;

    /**
     * Returns the number of Aliases
     */
    int getAliasTokenCount();

    /**
     * Returns an unmodifiable view of the filtered AliasToken list
     */
    ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList();

    // ================ Related to Tasks ==============================

    /**
     * Deletes the given task
     */
    void deleteTask(ReadOnlyTask target) throws TaskNotFoundException;

    /**
     * Adds the given task
     */
    void addTask(ReadOnlyTask target) throws DuplicateTaskException;

    /**
     * Updates the given task
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask updatedTask)
            throws TaskNotFoundException, DuplicateTaskException;

    /**
     * Marks the given tasks as completed
     */
    void markTasks(List<ReadOnlyTask> targets)
            throws TaskNotFoundException, DuplicateTaskException;

    /**
     * Unmarks the given tasks as completed
     */
    void unmarkTasks(List<ReadOnlyTask> targets)
            throws TaskNotFoundException, DuplicateTaskException;

    /**
     * Returns an unmodifiable view of the filtered Task list
     */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);
}
