package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_REPRESENTATION;

import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;

/**
 * Command to create aliases
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates an alias for a command or shortcut. "
            + "Parameters: "
            + PREFIX_ALIAS_KEYWORD + "KEYWORD "
            + PREFIX_ALIAS_REPRESENTATION + "REPRESENTATION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ALIAS_KEYWORD + "TTSH "
            + PREFIX_ALIAS_REPRESENTATION + " Tan Tock Seng Hospital";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s";
    public static final String MESSAGE_DUPLICATE_ALIAS = "This alias already exists";
    public static final String MESSAGE_INVALID_KEYWORD = "Unable to use a command name as a keyword!";

    private final AliasToken toAdd;

    /**
     * Creates an AliasCommand to add the specified {@code ReadOnlyAliasToken}
     */
    public AliasCommand(ReadOnlyAliasToken aliasToken) {
        toAdd = new AliasToken(aliasToken);
    }

    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        try {
            model.addAliasToken(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateTokenKeywordException e) {
            return new CommandResult(MESSAGE_DUPLICATE_ALIAS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && toAdd.equals(((AliasCommand) other).toAdd));
    }

}
