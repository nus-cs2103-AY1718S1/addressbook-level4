package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_KEYWORD_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_REPRESENTATION_MONDAY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;

//@@author deep4k
/**
 * A utility class containing a list of {@code AliasTokens} objects to be used in tests.
 */
public class TypicalAliasTokens {

    public static final ReadOnlyAliasToken AKA = new AliasTokenBuilder().withKeyword("aka")
            .withRepresentation("Also Known As").build();

    public static final ReadOnlyAliasToken DIY = new AliasTokenBuilder().withKeyword("diy")
            .withRepresentation("Do It Yourself").build();

    public static final ReadOnlyAliasToken HTH = new AliasTokenBuilder().withKeyword("hth")
            .withRepresentation("Hope It Helps").build();

    public static final ReadOnlyAliasToken TTYL = new AliasTokenBuilder().withKeyword("ttyl")
            .withRepresentation("Talk To You Later").build();

    public static final ReadOnlyAliasToken TGIF = new AliasTokenBuilder().withKeyword("tgif")
            .withRepresentation("Thank God Its Friday").build();

    public static final ReadOnlyAliasToken TQ = new AliasTokenBuilder().withKeyword("tq")
            .withRepresentation("Thank You").build();

    // Manually added - Alias' details found in {@code CommandTestUtil}
    public static final ReadOnlyAliasToken MON = new AliasTokenBuilder().withKeyword(VALID_ALIAS_KEYWORD_MONDAY)
            .withRepresentation(VALID_ALIAS_REPRESENTATION_MONDAY).build();

    private TypicalAliasTokens() {
        // prevents instantiation
    }

    public static List<ReadOnlyAliasToken> getTypicalAliasTokens() {
        return new ArrayList<>(Arrays.asList(AKA, DIY, HTH, TTYL, TGIF, TQ, MON));
    }

    /**
     * Returns an {@code AddressBook} with all the typical aliases.
     */
    public static AddressBook getTypicalAddressBookWithAlias() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyAliasToken aliasToken : getTypicalAliasTokens()) {
            try {
                ab.addAliasToken(aliasToken);
            } catch (DuplicateTokenKeywordException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }
}
