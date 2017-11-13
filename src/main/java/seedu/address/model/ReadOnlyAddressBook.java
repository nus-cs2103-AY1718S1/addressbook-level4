package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.ReadOnlyPerson;
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

    //@@author jaivigneshvenugopal
    /**
     * Returns an unmodifiable view of the blacklisted persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getBlacklistedPersonList();

    /**
     * Returns an unmodifiable view of the whitelisted persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getWhitelistedPersonList();

    //@@author
    /**
     * Returns an unmodifiable view of the list of persons with overdue debt.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyPerson> getOverduePersonList();

    /**
     * Returns an unmodifiable view of the tags list.
     * This list will not contain any duplicate tags.
     */
    ObservableList<Tag> getTagList();

}
