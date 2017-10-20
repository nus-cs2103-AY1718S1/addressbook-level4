package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_KEYWORD;

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
            + PREFIX_ALIAS_KEYWORD + " ttsh";

    public static final String MESSAGE_SUCCESS = "Alias removed: %1$s";
    public static final String MESSAGE_UNKNOWN_ALIAS = "This alias is not in use";

    private final String keyword;
    private ReadOnlyAliasToken toRemove;

    /**
     * Creates an UnaliasCommand to add the specified {@code ReadOnlyAliasToken}
     */
    public UnaliasCommand(String keyword) {
        this.keyword = keyword;
        this.toRemove = null;
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);

        ReadOnlyAliasToken toDelete = null;
        for (ReadOnlyAliasToken token : model.getFilteredAliasTokenList()) {
            if (token.getKeyword().keyword.equals(this.keyword)) {
                toDelete = token;
                break;
            }
        }

        try {
            if (toDelete == null) {
                return new CommandResult(MESSAGE_UNKNOWN_ALIAS);
            }
            toRemove = toDelete;
            model.deleteAliasToken(toRemove);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
        } catch (TokenKeywordNotFoundException e) {
            return new CommandResult(MESSAGE_UNKNOWN_ALIAS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnaliasCommand // instanceof handles nulls
                && toRemove.equals(((UnaliasCommand) other).toRemove));
    }

}

