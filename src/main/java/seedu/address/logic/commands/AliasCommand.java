//@@author danielbrzn
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;

/**
 * Adds an alias for a command to the user preferences.
 */
public class AliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "alias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an alias for a command. "
            + "Parameters: "
            + "ALIAS "
            + "COMMAND "
            + "Example: " + COMMAND_WORD + " "
            + "a "
            + "add ";

    public static final String MESSAGE_SUCCESS = "New alias added: %1$s for %2$s";
    public static final String MESSAGE_OVERRIDE = "Alias %1$s is now mapped to %2$s instead of %3$s";
    public static final String MESSAGE_INVALID_COMMAND = "Command entered is invalid.";
    public static final String MESSAGE_RESTRICTED_ALIAS = "Alias entered is a command name and cannot be mapped. "
            + "Choose a different alias.";
    private final String alias;
    private final String actualCommand;

    /**
     * Creates an AliasCommand to add the specified alias
     */
    public AliasCommand(String alias, String actualCommand) {
        this.alias = alias;
        this.actualCommand = actualCommand;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        if (AddressBookParser.checkValidCommand(actualCommand)) {
            if (AddressBookParser.checkValidCommand(alias)) {
                throw new CommandException(MESSAGE_RESTRICTED_ALIAS);
            }
            String mapping = model.getAlias(this.alias);
            model.addAlias(this.alias, actualCommand);
            if (mapping == null) {
                return new CommandResult(String.format(MESSAGE_SUCCESS, this.alias, actualCommand));
            } else {
                return new CommandResult(String.format(MESSAGE_OVERRIDE, this.alias, actualCommand, mapping));
            }
        } else {
            throw new CommandException(MESSAGE_INVALID_COMMAND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AliasCommand // instanceof handles nulls
                && alias.equals(((AliasCommand) other).alias)
                && actualCommand.equals(((AliasCommand) other).actualCommand));
    }
}
