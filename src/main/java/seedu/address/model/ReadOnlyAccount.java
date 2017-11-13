package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.user.ReadOnlyUser;

//@@author quanle1994

/**
 * Unmodifiable view of the accounts
 */

public interface ReadOnlyAccount {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<ReadOnlyUser> getUserList();
}
