package seedu.address.logic.commands;

import java.util.NoSuchElementException;

import seedu.address.logic.TextToSpeech;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Aliases;
import seedu.address.model.UserPrefs;

//@@author goweiwen
/**
 * Removes a previously defined alias.
 */
public class UnaliasCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unalias";
    public static final String MESSAGE_SUCCESS = "Deleted alias \"%1$s\".";
    public static final String MESSAGE_NO_SUCH_ALIAS = "Alias \"%1$s\" doesn't exist.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an alias previously defined."
            + "Parameters: ALIAS\n"
            + "Example: " + COMMAND_WORD + " create\n";

    private final String alias;

    public UnaliasCommand(String alias) {
        this.alias = alias;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Aliases aliases = UserPrefs.getInstance().getAliases();

        try {
            aliases.removeAlias(alias);
        } catch (NoSuchElementException e) {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_ALIAS, alias));
        }
        //Text to Speech
        new TextToSpeech(String.format(MESSAGE_SUCCESS, alias)).speak();;
        return new CommandResult(String.format(MESSAGE_SUCCESS, alias));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnaliasCommand // instanceof handles nulls
                && this.alias.equals(((UnaliasCommand) other).alias)); // state check
    }

}
