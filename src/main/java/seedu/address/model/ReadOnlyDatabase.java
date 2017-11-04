package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.credentials.ReadOnlyAccount;

/**
 *
 */
public interface ReadOnlyDatabase {

    ObservableList<ReadOnlyAccount> getAccountList();
}
