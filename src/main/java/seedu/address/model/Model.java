package seedu.address.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    //@@author keithsoc
    /** {@code Predicate} that consists of all ReadOnlyPerson who has been favorited */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_FAV_PERSONS = p -> p.getFavorite().isFavorite();
    //@@author

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(ReadOnlyAddressBook newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Deletes the given person. */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Adds the given person */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    /** Adds all persons in the given collection */
    void addPersons(Collection<ReadOnlyPerson> persons);

    //@@author marvinchin
    /** Sorts the {@code Person}s in the address book based on the input {@code comparator}. */
    void sortPersons(Comparator<ReadOnlyPerson> comparator);
    //@@author

    //@@author keithsoc
    /** Favorites or unfavorites the given person. Should update the last accessed time of the person. */
    void toggleFavoritePerson(ReadOnlyPerson target, String type)
            throws DuplicatePersonException, PersonNotFoundException;

    //@@author marvinchin
    /** Selects the given {@code Person}. Should update the {@code LastAccessDate} of the person. */
    void selectPerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /**
     * Gets the {@code Index} of the {@code target} in the filtered person list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    Index getPersonIndex(ReadOnlyPerson target) throws PersonNotFoundException;

    //@@author
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * Should update the last accessed time of the person.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException;

    void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException;

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author marvinchin
    /**
     * Returns a defensive copy of the {@code model}.
     */
    Model makeCopy();
}
