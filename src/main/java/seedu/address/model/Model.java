package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicateTodoItemException;
//@@author qihao27
import seedu.address.model.person.exceptions.NoPersonFoundException;
//@@author
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author aaronyhsoh
    /**
     * Replaces the given person {@code target} with {@code favouritedPerson}.
     *
     * @throws DuplicatePersonException if favouriting the person causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author Hailinx
    /** Adds the given todoItem to target person */
    void addTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException, DuplicateTodoItemException;

    /** Deletes the given todoItem from target person */
    void deleteTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException;

    /** Resets all todoItem for target person */
    void resetTodoItem(ReadOnlyPerson target)
            throws DuplicatePersonException, PersonNotFoundException;
    //@@author

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author Hailinx
    /** Updates the UI to show all todoItems for all persons */
    void updateTodoItemList();

    //@@author qihao27
    /**
     * Sorts the list by the specified @param parameter.
     * @throws NoPersonFoundException if no persons found in {@code AddressBook}.
     */
    void sortPerson (String option) throws NoPersonFoundException;
    //@@author
}
