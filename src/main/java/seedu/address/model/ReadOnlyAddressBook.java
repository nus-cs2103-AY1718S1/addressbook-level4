package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.ReadOnlyTask;

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

    /**
     * Returns an unmodifiable view of the AliasTokens list.
     * This list will not contain any duplicate AliasTokens.
     */

    ObservableList<ReadOnlyAliasToken> getAliasTokenList();

    /**
     * Returns an unmodifiable view of the tasks list.
     * This list will not contain any duplicate tasks.
     */

    ObservableList<ReadOnlyTask> getTaskList();
}
