//@@author cqhchan
package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalAccounts.PRIVATE;
import static seedu.address.testutil.TypicalAccounts.getTypicalDatabase;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;

public class DatabaseTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Database database = new Database();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), database.getAccountList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        database.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyDatabase_replacesData() {
        Database newData = getTypicalDatabase();
        database.resetData(newData);
        assertEquals(newData, database);
    }

    @Test
    public void resetData_withDuplicateAccounts_throwsAssertionError() {
        // Repeat ALICE twice
        List<Account> newAccounts = Arrays.asList(new Account(PRIVATE), new Account(PRIVATE));
        DatabaseStub newData = new DatabaseStub(newAccounts);

        thrown.expect(AssertionError.class);
        database.resetData(newData);
    }

    @Test
    public void getAccountList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        database.getAccountList().remove(0);
    }


    /**
     * A stub ReadOnlyAddressBook whose persons and tags lists can violate interface constraints.
     */
    private static class DatabaseStub implements ReadOnlyDatabase {
        private final ObservableList<ReadOnlyAccount> accounts = FXCollections.observableArrayList();
        DatabaseStub(Collection<? extends ReadOnlyAccount> accounts) {
            this.accounts.setAll(accounts);
        }

        @Override
        public ObservableList<ReadOnlyAccount> getAccountList() {
            return accounts;
        }

    }

}
