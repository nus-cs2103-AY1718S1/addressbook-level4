package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_KEYWORD;

import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;

/**
 * Command to remove aliases
 */
public class UnaliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes an alias for a command or shortcut. "
            + "Parameters: "
            + PREFIX_ALIAS_KEYWORD + "KEYWORD "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALIAS_KEYWORD + "ph";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_UNKNOWN_ALIAS = "This alias is not in use";

    private Keyword keyword;
    private ReadOnlyAliasToken toRemove;

    /**
     * Creates an UnaliasCommand to add the specified {@code ReadOnlyAliasToken}
     */

    public UnaliasCommand(Keyword keyword) {
        this.keyword = keyword;
        this.toRemove = null;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);

        for (ReadOnlyAliasToken token : model.getAddressBook().getAliasTokenList()) {
            if (token.getKeyword().keyword.equalsIgnoreCase(keyword.keyword)) {
                toRemove = token;
                break;
            }
        }
        try {
            if (toRemove == null) {
                return new CommandResult(MESSAGE_UNKNOWN_ALIAS);
            }
            model.deleteAliasToken(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toRemove));
        } catch (TokenKeywordNotFoundException e) {
            return new CommandResult(MESSAGE_UNKNOWN_ALIAS);
        }

    }
}


