package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_REPRESENTATION;

import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.model.alias.ReadOnlyAliasToken;

//@@author deep4k
/**
 * A utility class for AliasToken.
 */
public class AliasTokenUtil {

    /**
     * Returns an alias command string for adding the {@code AliasToken}.
     */
    public static String getAliasCommand(ReadOnlyAliasToken aliasToken) {
        return AliasCommand.COMMAND_WORD + " " + getAliasDetails(aliasToken);
    }

    /**
     * Returns the part of command string for the given {@code AliasToken}'s details.
     */
    public static String getAliasDetails(ReadOnlyAliasToken aliasToken) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_ALIAS_KEYWORD + aliasToken.getKeyword().keyword + " ");
        sb.append(PREFIX_ALIAS_REPRESENTATION + aliasToken.getRepresentation().representation + " ");
        return sb.toString();
    }
}
