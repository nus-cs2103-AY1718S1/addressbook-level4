package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_NOT_HIDDEN = person -> !person.isPrivate();
    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyAddressBook newData);

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Sorts the AddressBook.
     */
    void sortList(String toSort);

    /**
     * Deletes the given person.
     */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException;

    /** Hides the given person. */
    void hidePerson(ReadOnlyPerson target) throws PersonNotFoundException;

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

}
