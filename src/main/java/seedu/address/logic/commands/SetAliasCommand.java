//@@author keloysiusmak
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_ALIAS;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMMAND;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.alias.exceptions.DuplicateAliasException;
import seedu.address.model.alias.exceptions.UnknownCommandException;

/**
 * Sets an alias for a particular command
 */
public class SetAliasCommand extends Command {
    public static final String COMMAND_WORD = "setalias";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets an alias for a command. "
            + "Parameters: "
            + PREFIX_COMMAND + "COMMAND "
            + PREFIX_ALIAS + "ALIAS";

    public static final String MESSAGE_SUCCESS = "Alias has been set.";

    private final String commandAdd;
    private final String toAdd;

    /**
     * Creates a SetAliasCommand to with the specified {@code command} and {@code alias}
     */
    public SetAliasCommand(String command, String alias) {

        //invalid alias, both cnanot be null
        if (command == null || alias == null) {
            throw new NullPointerException();
        }
        commandAdd = command;
        toAdd = alias;
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        try {
            model.setAlias(commandAdd, toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UnknownCommandException e) {
            //unable to set alias for unknown command
            throw new CommandException(MESSAGE_UNKNOWN_COMMAND);
        } catch (DuplicateAliasException e) {
            //unable to set alias if the alias has been set for another command
            throw new CommandException(MESSAGE_DUPLICATE_ALIAS);
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetAliasCommand // instanceof handles nulls
                && toAdd.equals(((SetAliasCommand) other).toAdd));
    }
}
