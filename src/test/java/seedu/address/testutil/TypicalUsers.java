package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.digestutil.HashDigest;
import seedu.address.commons.util.digestutil.HexCode;
import seedu.address.model.Account;
import seedu.address.model.user.ReadOnlyUser;
import seedu.address.model.user.User;
import seedu.address.model.user.exceptions.DuplicateUserException;

/**
 * A utility class containing a list of {@code User} objects to be used in tests.
 */
public class TypicalUsers {

    public static final ReadOnlyUser A = userBuilder("a", "1111111111111111111111", "a");
    public static final ReadOnlyUser B = userBuilder("b", "2222222222222222222222", "b");
    public static final ReadOnlyUser C = userBuilder("c", "3333333333333333333333", "c");
    public static final ReadOnlyUser D = userBuilder("d", "4444444444444444444444", "d");

    private TypicalUsers() {
    } // prevents instantiation

    /**
     * Returns an {@code Account} with all the typical users.
     */
    public static Account getTypicalAccount() {
        Account ac = new Account();
        for (ReadOnlyUser person : getTypicalUsers()) {
            try {
                ac.addUser(person);
            } catch (DuplicateUserException e) {
                assert false : "not possible";
            }
        }
        return ac;
    }

    /**
     * Buid a user
     *
     * @param userName
     * @param salt
     * @param password
     * @return
     */
    public static ReadOnlyUser userBuilder(String userName, String salt, String password) {
        userName = new String(new HashDigest().getHashDigest(userName));
        userName = new HexCode().getHexFormat(userName);
        return new User(userName, salt, password);
    }

    public static List<ReadOnlyUser> getTypicalUsers() {
        return new ArrayList<>(Arrays.asList(A, B, C, D));
    }
}
