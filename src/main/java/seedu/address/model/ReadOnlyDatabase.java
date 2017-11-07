//@@author cqhchan
package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.account.ReadOnlyAccount;

/**
 *
 */
public interface ReadOnlyDatabase {

    ObservableList<ReadOnlyAccount> getAccountList();
}
