package seedu.address.testutil;

import seedu.address.model.Database;
import seedu.address.model.credentials.ReadOnlyAccount;
import seedu.address.model.credentials.exceptions.DuplicateAccountException;
import seedu.address.model.person.ReadOnlyPerson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TypicalAccounts {

    public static final ReadOnlyAccount CHAN = new AccountBuilder().withUsername("Chan")
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
        return new ArrayList<>(Arrays.asList(BROTHER, CHAN));
    }

}
