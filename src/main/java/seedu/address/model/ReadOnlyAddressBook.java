package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getPersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

    //@@author liuhang0213
    /**
     * Returns an unmodifiable view of a person by the given internal index
     *
     * @param i internal index of the person
     */
    ReadOnlyPerson getPersonByInternalIndex(int i) throws PersonNotFoundException;

    /**
     * Returns the maximum index of persons in the address book.
     */
    int getMaxInternalIndex();

}
