//@@author cqhchan
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Database;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;

/**
 *
 */
public class TypicalAccounts {

    public static final ReadOnlyAccount PRIVATE = new AccountBuilder().withUsername("private")
            .withPassword("password").build();
    public static final ReadOnlyAccount BROTHER = new AccountBuilder().withUsername("BROTHER")
            .withPassword("password").build();


    public static Database getTypicalDatabase() {
        Database ab = new Database();
        for (ReadOnlyAccount account : getTypicalAccounts()) {
            try {
                ab.addAccount(account);
            } catch (DuplicateAccountException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyAccount> getTypicalAccounts() {
        return new ArrayList<>(Arrays.asList(BROTHER, PRIVATE));
    }

}
