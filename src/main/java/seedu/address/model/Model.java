package seedu.address.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.ImportAnalysis;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.AddressBookData;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<ReadOnlyPerson> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** Clears existing backing model and replaces with the provided new data. */
    void resetData(AddressBookData newData);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /** Returns the RecycleBin */
    ReadOnlyAddressBook getRecycleBin();

    /** Deletes the given person from addressbook */
    void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException, DuplicatePersonException;

    /** Adds the given person to addressbook */
    void addPerson(ReadOnlyPerson person) throws DuplicatePersonException;

    //@@author frozventus
    /** Restores the given person from recycle bin */
    void restorePerson(ReadOnlyPerson target) throws PersonNotFoundException, DuplicatePersonException;

    /** Deletes the given person from bin */
    void deleteFromBin(ReadOnlyPerson target) throws PersonNotFoundException;

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

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate);

    //@@author frozventus
    /** Toggle display to show list */
    void setListDisplay();

    /** Toggle display to show bin */
    void setBinDisplay();

    /** Returns an unmodifiable view of the address book person list */
    ObservableList<ReadOnlyPerson> getAddressBookList();

    /** Returns an unmodifiable view of the recycle bin list */
    ObservableList<ReadOnlyPerson> getRecycleBinList();

    //@@author stan789
    /**
     * sorts the list in the addressbook by alphabetical order.
     * @throws EmptyAddressBookException if addressbook is empty
     */
    void executeSort(String sortType) throws EmptyAddressBookException;

    //@@author frozventus-reused
    void executeBinSort(String sortType) throws EmptyAddressBookException;

    //@@author stan789
    /** imports a VCard file to OneBook.
     * @throws IOException if file cannot be read.
     */
    void importFile(Path fileLocation, ImportAnalysis importAnalysis) throws IOException;

    //@@author stan789
    /** Exports the current contacts in OneBook to VCard or Csv file*/
    void exportFile(String fileLocation, String extension) throws IOException;
}
