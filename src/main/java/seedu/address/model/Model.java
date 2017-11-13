package seedu.address.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Prefix;
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

    //@@author Esilocke
    /** Clears only part of the existing backing model and replaces with the provided new data. */
    void resetPartialData(ReadOnlyAddressBook newData, Prefix type);
    //@@author

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;
    //@@author wangyiming1019
    /** Deletes given tag from specific persons */
    void deleteTag(Tag toDelete, ArrayList<Index> targetIndexes) throws PersonNotFoundException,
            DuplicatePersonException;
    //@@author
    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;
    //@@author wangyiming1019
    /** Adds given tag to specific persons */
    void addTag(Tag toAdd, ArrayList<Index> targetIndexes) throws PersonNotFoundException,
            DuplicatePersonException;
    //@@author
    /** Edits the specified tag, and updates all instances in the address book */
    void editTag(Tag toChange, Tag newTag, ArrayList<Index> affectedIndexes) throws PersonNotFoundException,
            DuplicatePersonException;
    //@@author wangyiming1019
    /** Favourites the given person */
    void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Unfavourites the given person */
    void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException;
    //@@author
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author Esilocke
    /** Adds the given task */
    void addTask(ReadOnlyTask task) throws DuplicateTaskException;

    /** Deletes the given task */
    void deleteTask(ReadOnlyTask toDelete) throws TaskNotFoundException;

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException;

    /** Assigns all specified persons to the specified task */
    void assignToTask(ArrayList<ReadOnlyPerson> personsToAssign, ReadOnlyTask assignedTask)
            throws TaskNotFoundException, DuplicateTaskException;

    /** Assigns all specified persons to the specified task */
    void dismissFromTask(ArrayList<ReadOnlyPerson> personsToDismiss, ReadOnlyTask dismissedTask)
            throws TaskNotFoundException, DuplicateTaskException;

    /** Changes the state of the specified task */
    void setAsComplete(ReadOnlyTask toSet, boolean isComplete) throws TaskNotFoundException, DuplicateTaskException;

    /** Displays all persons assigned to the specified task */
    void viewAssignees(ReadOnlyTask task);
    //@@author

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    //@@author Esilocke
    /** Returns an unmodifiable view of the filtered tasks list */
    ObservableList<ReadOnlyTask> getFilteredTaskList();
    //@@author

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author Esilocke
    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate);

    //@@author charlesgoh
    /**
     * Sorts all persons in person list by chosen field in ascending (asc) or descending (desc) order
     * @param field
     * @param order
     */
    void sortPersons(String field, String order);

    /**
     * Sorts all tasks in task list by chosen field in ascending (asc) or descending (desc) order
     * @param field
     * @param order
     */
    void sortTasks(String field, String order);

    /**
     * Returns lock state. True means locked. False means unlocked
     */
    boolean getLockState();

    /**
     * Locks address book from model
     */
    void lockAddressBookFromModel();

    /**
     * Unlock address book from model
     */
    void unlockAddressBookFromModel();

    /**
     * Returns a copy of the UserPrefs
     */
    UserPrefs getUserPrefs();
    //@@author

    //@@author jeffreygohkw
    void setPrivacyLevel(int level);

    int getPrivacyLevel();

    ReadOnlyPerson getPersonAtIndexFromAddressBook(int index);

    void setTheme(String theme);

    String getTheme();

    HashMap<String, String> getStyleMap();
}
