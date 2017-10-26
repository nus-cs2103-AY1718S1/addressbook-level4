package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
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
    Predicate<ReadOnlyTask> PREDICATE_SHOW_ALL_TASKS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the TaskBook */
    ReadOnlyTaskBook getTaskBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Deletes the given task. */
    void deleteTask(ReadOnlyTask task) throws TaskNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    void addTask(ReadOnlyTask task) throws DuplicateTaskException;
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException;

    void updateTaskPriority(ReadOnlyTask task, Integer value) throws TaskNotFoundException, DuplicateTaskException;

    void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException;

    void markTask(ReadOnlyTask task) throws TaskNotFoundException;
    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();
    ObservableList<ReadOnlyTask> getFilteredTaskList();

    SortedList<ReadOnlyTask> getSortedTaskList();
    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);

    void taskByEnd();
}
