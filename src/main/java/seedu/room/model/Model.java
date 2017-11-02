package seedu.room.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.commands.exceptions.TagNotFoundException;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.tag.Tag;


/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;


    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyResidentBook newData);

    /**
     * Returns the ResidentBook
     */
    ReadOnlyResidentBook getResidentBook();

    /**
     * Deletes the given person.
     */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Adds the given person
     */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /**
     * Delete all persons with the given tag
     */
    void deleteByTag(Tag tag) throws IllegalValueException, CommandException;

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

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

    /**
     * Remove tag inside the Resident Book
     */
    void removeTag(Tag t) throws TagNotFoundException;
    /**
     * Sorts the Resident Book by name, phone, room or phone depending on the sortCriteria
     */
    void sortBy(String sortCriteria) throws AlreadySortedException;

    //@@author shitian007
    /**
     * Updates the highlight status of persons with the specified tag
     *
     * @throws TagNotFoundException if no specified tag exists
     */
    void updateHighlightStatus(String highlightTag) throws TagNotFoundException;
    //@@author

    /**
     * Swaps two residents' rooms
     */
    void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2) throws PersonNotFoundException;

}
