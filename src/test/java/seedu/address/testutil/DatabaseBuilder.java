//@@author cqhchan
package seedu.address.testutil;

import seedu.address.model.Database;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 *
 */
public class DatabaseBuilder {
    private Database database;

    public DatabaseBuilder() {
        database = new Database();
    }

    public DatabaseBuilder(Database database) {
        this.database = database;
    }

    /**
     * Adds a new {@code Account} to the {@code Database} that we are building.
     */
    public DatabaseBuilder withAccount(ReadOnlyAccount account) {
        try {
            database.addAccount(account);
        } catch (DuplicateAccountException dpe) {
            throw new IllegalArgumentException("account is expected to be unique.");
        }
        return this;
    }

    /**
     * Parses {@code tagName} into a {@code Tag} and adds it to the {@code Database} that we are building.
     */

    public Database build() {
        return database;
    }
}

