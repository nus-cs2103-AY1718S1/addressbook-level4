package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyTask> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Adds the given task */
    void addTask(ReadOnlyTask task) throws DuplicateTaskException;

    /** Deletes the given task */
    /** For further implementation */
    void deleteTask (ReadOnlyTask task) throws TaskNotFoundException;

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

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author zhangshuoyang
    /**
     * Replaces the given person {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *     another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     *
     * For further implementation.
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
        throws DuplicateTaskException, TaskNotFoundException;

    /** Returns an unmodifiable view of the filtered task list */
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);


    /**
     * Clears the filters that have been applied to the filterd person list
     */

    void clearFiltersOnPersonList();

    /**
     * Updates the filter of the filtered tag list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonByTagList(Predicate<ReadOnlyPerson> predicate);

    /** Returns an unmodifiable view of the filtered tag list */
    ObservableList<ReadOnlyPerson> getFilteredPersonByTagList();

    /**
     * Deletes a specified tag from contacts in AddressBook
     *
     * @param t a tag object containing the information of the tag to delete
     */
    void deleteTag(Tag t) throws DuplicatePersonException, PersonNotFoundException;

}
